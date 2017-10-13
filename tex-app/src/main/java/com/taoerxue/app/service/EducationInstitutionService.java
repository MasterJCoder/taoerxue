package com.taoerxue.app.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taoerxue.app.vo.institution.EducationInstitutionCollect;
import com.taoerxue.common.bean.PageResult;
import com.taoerxue.common.enums.CourseStatusEnum;
import com.taoerxue.common.enums.TeacherStatusEnum;
import com.taoerxue.common.exception.SQLException;
import com.taoerxue.common.os.StoragePlatform;
import com.taoerxue.mapper.*;
import com.taoerxue.po.EducationInstitutionMongoPOJO;
import com.taoerxue.po.CourseInfoMongoPOJO;
import com.taoerxue.pojo.*;
import com.taoerxue.app.vo.EducationInstitutionQuery;
import com.taoerxue.util.LocationUtils;
import com.taoerxue.util.MathUtils;
import com.taoerxue.app.vo.CustomerServiceStaff;
import com.taoerxue.app.vo.institution.EducationInstitutionDetail;
import com.taoerxue.app.vo.institution.EducationInstitutionRecommend;
import com.taoerxue.app.vo.institution.EducationInstitutionSimple;
import com.taoerxue.util.StringUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lizhihui on 2017-03-23 11:02.
 */
@Service
public class EducationInstitutionService extends BaseService {
    @Resource
    private EducationInstitutionMapper educationInstitutionMapper;
    @Resource
    private DozerBeanMapper dozerBeanMapper;
    @Resource
    private StoragePlatform storagePlatform;
    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private CourseInfoMapper courseInfoMapper;
    @Resource
    private MyEducationInstitutionMapper myEducationInstitutionMapper;
    @Resource
    private MyEducationUserMapper myEducationUserMapper;
    @Resource
    private AppUserInstitutionCollectionMapper appUserInstitutionCollectionMapper;

    @Resource
    private MongoTemplate mongoTemplate;

    public EducationInstitutionDetail getDetail(Integer id, Double lng, Double lat) {
        //从数据库中获取机构信息
        EducationInstitution educationInstitution = get(id);
        if (educationInstitution == null) {
            return null;
        }
        EducationInstitutionDetail detail = dozerBeanMapper.map(educationInstitution, EducationInstitutionDetail.class);
        String photo = educationInstitution.getPhoto();
        String[] split = photo.split(":");
        List<String> photos = Arrays.asList(split);
        List<String> newPhotos = new ArrayList<>();
        for (String string : photos) {
            String newPhoto = storagePlatform.getImageURL(string);
            newPhotos.add(newPhoto);
        }
        detail.setPhotoList(newPhotos);

        //设置距离
        detail.setDistance(LocationUtils.calculateDistance(LocationUtils.calculateDistance(lng, lat, educationInstitution.getLng().doubleValue(), educationInstitution.getLat().doubleValue())));

        //获取课程信息,默认取三个,按照发布时间排序吧.
        CourseInfoExample courseInfoExample = new CourseInfoExample();
        courseInfoExample.setOrderByClause("create_time desc");
        courseInfoExample.createCriteria().andEIdEqualTo(id).andStatusEqualTo(CourseStatusEnum.THROUGH_AUDIT.getStatus());
        PageHelper.startPage(1, 3);
        List<CourseInfo> courseInfoList = courseInfoMapper.selectByExample(courseInfoExample);
        List<EducationInstitutionDetail.Course> courseList = new ArrayList<>();
        for (CourseInfo courseInfo : courseInfoList) {
            EducationInstitutionDetail.Course course = dozerBeanMapper.map(courseInfo, EducationInstitutionDetail.Course.class);
            course.setPhoto(storagePlatform.getImageURL(course.getPhoto()));
            courseList.add(course);
        }
        PageInfo<CourseInfo> courseInfoPageInfo = new PageInfo<>(courseInfoList);
        detail.setCourseCount(courseInfoPageInfo.getTotal());
        detail.setCourseList(courseList);

        //获取教师信息,默认获取3个,按照教龄排序吧.
        TeacherExample teacherExample = new TeacherExample();
        //排序规则就从教龄从高到低
        teacherExample.setOrderByClause("experience_age desc");
        teacherExample.createCriteria().andEIdEqualTo(id).andStatusEqualTo(TeacherStatusEnum.NORMAL.getStatus());
        PageHelper.startPage(1, 3);
        List<Teacher> teachers = teacherMapper.selectByExample(teacherExample);
        List<EducationInstitutionDetail.Teacher> teacherList = new ArrayList<>();
        for (Teacher teacher : teachers) {
            EducationInstitutionDetail.Teacher detailTeacher = dozerBeanMapper.map(teacher, EducationInstitutionDetail.Teacher.class);
            detailTeacher.setPhoto(storagePlatform.getImageURL(detailTeacher.getPhoto()));
            teacherList.add(detailTeacher);
        }

        PageInfo<Teacher> teacherPageInfo = new PageInfo<>(teachers);

        detail.setTeacherCount(teacherPageInfo.getTotal());
        detail.setTeacherList(teacherList);

        return detail;
    }


    public EducationInstitution get(Integer id) {
        return educationInstitutionMapper.selectByPrimaryKey(id);
    }


    public PageResult<EducationInstitutionSimple> list(boolean display, Double lng, Double lat, AppUser appUser, EducationInstitutionQuery educationInstitutionQuery, Integer pageNum, Integer pageSize) {
        Query query = new Query();


        //如果要显示距离,说明定位在城市,只要看他有没有传区域 id
        if (display) {
            Integer areaId = educationInstitutionQuery.getAreaId();
            Integer cityId = educationInstitutionQuery.getCityId();
            if (areaId == null) {
                query.addCriteria(Criteria.where("cityId").is(cityId));
            } else {
                query.addCriteria(Criteria.where("areaId").is(educationInstitutionQuery.getAreaId()));
            }
        }
        String order = educationInstitutionQuery.getOrder();
        if (!StringUtils.isEmpty(order)) {
            switch (order) {
                case "collections":
                    query.with(new Sort(Sort.Direction.DESC, order));
                    break;
                case "distance":
                    query.addCriteria(Criteria.where("location").nearSphere(new Point(lng, lat)));
                    break;
                case "smart":
                    query.with(new Sort(Sort.Direction.DESC, "collections"));
                    query.with(new Sort(Sort.Direction.DESC, "_id"));
//                    query.addCriteria(Criteria.where("location").nearSphere(new Point(lng, lat)));
                default:
                    break;
            }
        }
        //判断用户是否登
        if (appUser != null) {
            Integer expectation = appUser.getExpectation();
            if (!expectation.equals(7)) {
                List<Integer> typeList = expectationToEducationType(MathUtils.convert(expectation));
                query.addCriteria(Criteria.where("typeId").in(typeList));
            }
        }
        String like = educationInstitutionQuery.getLike();
        if (!StringUtils.isEmpty(like)) {
            query.addCriteria(Criteria.where("name").regex("^.*" + like + ".*$"));
        }


        query.skip((pageNum - 1) * pageSize).limit(pageSize);

        List<EducationInstitutionMongoPOJO> educationInstitutionMongoPOJOS = mongoTemplate.find(query, EducationInstitutionMongoPOJO.class);
        List<EducationInstitutionSimple> simpleList = new ArrayList<>();
        for (EducationInstitutionMongoPOJO educationInstitutionMongoPOJO : educationInstitutionMongoPOJOS) {
            EducationInstitutionSimple simple = dozerBeanMapper.map(educationInstitutionMongoPOJO, EducationInstitutionSimple.class);
            String[] split = educationInstitutionMongoPOJO.getPhoto().split(":");
            simple.setPhoto(storagePlatform.getImageURL(split[0]));
            if (display)
                simple.setDistance(LocationUtils.calculateDistance(LocationUtils.calculateDistance(lng, lat, educationInstitutionMongoPOJO.getLocation()[0], educationInstitutionMongoPOJO.getLocation()[1])));
            simpleList.add(simple);
        }
        PageResult<EducationInstitutionSimple> pageResult = new PageResult<>();
        pageResult.setTotal(mongoTemplate.count(query, EducationInstitutionMongoPOJO.class));
        pageResult.setRows(simpleList);

        return pageResult;
    }


    public List<EducationInstitutionRecommend> recommendList(boolean displayDistance, Integer cityId, Integer areaId, Double lng, Double lat, AppUser user) {
        Query query = new Query();
        query.limit(2);
        //判断用户是否登录

        if (user != null) {
            Integer expectation = user.getExpectation();
            if (!expectation.equals(7)) {
                List<Integer> typeList = expectationToEducationType(MathUtils.convert(expectation));
                query.addCriteria(Criteria.where("typeId").in(typeList));
            }
        }

        //如果要显示距离,说明定位在城市,只要看他有没有传区域 id
        if (displayDistance) {
            query.addCriteria(Criteria.where("location").nearSphere(new Point(lng, lat)));
            if (areaId == null) {
                query.addCriteria(Criteria.where("cityId").is(cityId));
            } else {
                query.addCriteria(Criteria.where("areaId").is(areaId));
            }
        }
        List<EducationInstitutionMongoPOJO> educationInstitutionMongoPOJOS = mongoTemplate.find(query, EducationInstitutionMongoPOJO.class);
        List<EducationInstitutionRecommend> recommendList = new ArrayList<>();
        for (EducationInstitutionMongoPOJO educationInstitutionMongoPOJO : educationInstitutionMongoPOJOS) {
            EducationInstitutionRecommend recommend = dozerBeanMapper.map(educationInstitutionMongoPOJO, EducationInstitutionRecommend.class);
            String[] split = educationInstitutionMongoPOJO.getPhoto().split(":");
            recommend.setPhoto(storagePlatform.getImageURL(split[0]));
            if (displayDistance)
                recommend.setDistance(LocationUtils.calculateDistance(LocationUtils.calculateDistance(lng, lat, educationInstitutionMongoPOJO.getLocation()[0], educationInstitutionMongoPOJO.getLocation()[1])));
            //获取两个课程
            // TODO 先从数据库获取,以后看下要不要改成从 mongodb 里面拿

            CourseInfoExample courseInfoExample = new CourseInfoExample();
            courseInfoExample.createCriteria().andEIdEqualTo(recommend.getId()).andStatusEqualTo(2);
            PageHelper.startPage(1, 2);
            List<CourseInfo> courseInfoList = courseInfoMapper.selectByExample(courseInfoExample);
            PageInfo<CourseInfo> pageInfo = new PageInfo<>(courseInfoList);

            List<EducationInstitutionRecommend.Course> courseList = courseInfoList.stream()
                    .map(courseInfo -> dozerBeanMapper.map(courseInfo, EducationInstitutionRecommend.Course.class))
                    .collect(Collectors.toList());

            recommend.setCourseCount(pageInfo.getTotal());
            recommend.setCourseList(courseList);
            recommendList.add(recommend);
        }

        return recommendList;
    }

    public PageResult<EducationInstitutionDetail.Teacher> teacherList(Integer eId, Integer pageNum, Integer pageSize) {
        TeacherExample teacherExample = new TeacherExample();
        teacherExample.createCriteria().andEIdEqualTo(eId).andStatusEqualTo(TeacherStatusEnum.NORMAL.getStatus());
        PageHelper.startPage(pageNum, pageSize);
        List<Teacher> teachers = teacherMapper.selectByExample(teacherExample);
        List<EducationInstitutionDetail.Teacher> teacherList = new ArrayList<>();
        for (Teacher teacher : teachers) {
            EducationInstitutionDetail.Teacher detailTeacher = dozerBeanMapper.map(teacher, EducationInstitutionDetail.Teacher.class);
            detailTeacher.setPhoto(storagePlatform.getImageURL(detailTeacher.getPhoto()));
            teacherList.add(detailTeacher);
        }
        PageResult<EducationInstitutionDetail.Teacher> pageResult = new PageResult<>();
        pageResult.setTotal(new PageInfo<>(teachers).getTotal());
        pageResult.setRows(teacherList);
        return pageResult;
    }

    public PageResult<EducationInstitutionDetail.Course> courseList(Integer eId, Integer pageNum, Integer pageSize) {
        Query query = new Query(Criteria.where("eId").is(eId));
        List<CourseInfoMongoPOJO> courseInfoMongoPOJOList = mongoTemplate.find(query.skip((pageNum - 1) * pageSize).limit(pageSize), CourseInfoMongoPOJO.class);
        List<EducationInstitutionDetail.Course> courseList = new ArrayList<>();
        for (CourseInfoMongoPOJO courseInfoMongoPOJO : courseInfoMongoPOJOList) {
            EducationInstitutionDetail.Course course = dozerBeanMapper.map(courseInfoMongoPOJO, EducationInstitutionDetail.Course.class);
            course.setPhoto(storagePlatform.getImageURL(course.getPhoto()));
            courseList.add(course);
        }
        PageResult<EducationInstitutionDetail.Course> pageResult = new PageResult<>();
        pageResult.setTotal(mongoTemplate.count(query, EducationInstitutionMongoPOJO.class));
        pageResult.setRows(courseList);
        return pageResult;
    }

    public List<CustomerServiceStaff> customList(Integer eId) {
        String name = myEducationInstitutionMapper.getName(eId);
        if (name == null)
            return new ArrayList<>();
        List<EducationUser> phoneList = myEducationUserMapper.listNameAndPhoneByEId(eId);
        List<CustomerServiceStaff> customerServiceStaffList = new ArrayList<>();
        for (EducationUser user : phoneList) {
            CustomerServiceStaff customerServiceStaff = new CustomerServiceStaff();
            customerServiceStaff.setAccount("web_" + user.getPhone());
            customerServiceStaff.setName(name + "-" + user.getAlias());
            customerServiceStaff.setPhoto(storagePlatform.getImageURL("pc-portrait.png"));
            customerServiceStaffList.add(customerServiceStaff);
        }
        return customerServiceStaffList;
    }

    public List<String> search(String like) {
        return myEducationInstitutionMapper.listNameLike(like);
    }

    public void collect(Integer eId, Integer id) {
        AppUserInstitutionCollection appUserInstitutionCollection = new AppUserInstitutionCollection();
        appUserInstitutionCollection.seteId(eId);
        appUserInstitutionCollection.setUserId(id);
        try {

            appUserInstitutionCollectionMapper.insertSelective(appUserInstitutionCollection);
        } catch (Exception e) {
            throw new SQLException("您已经收藏过该机构");
        }
    }

    public void unCollect(Integer eId, Integer id) {
        AppUserInstitutionCollectionExample appUserInstitutionCollectionExample = new AppUserInstitutionCollectionExample();
        appUserInstitutionCollectionExample.createCriteria().andEIdEqualTo(eId).andUserIdEqualTo(id);
        try {
            appUserInstitutionCollectionMapper.deleteByExample(appUserInstitutionCollectionExample);
        } catch (Exception e) {
            throw new SQLException("取消收藏错误");
        }
    }

    public PageResult<EducationInstitutionCollect> collectList(Integer id, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<EducationInstitution> educationInstitutionList = myEducationInstitutionMapper.collectList(id);
        List<EducationInstitutionCollect> educationInstitutionCollectList = educationInstitutionList
                .stream()
                .map(educationInstitution -> {
                    EducationInstitutionCollect educationInstitutionCollect = dozerBeanMapper.map(educationInstitution, EducationInstitutionCollect.class);
                    educationInstitutionCollect.setPhoto(storagePlatform.getImageURL(educationInstitutionCollect.getPhoto()));
                    return educationInstitutionCollect;
                })
                .collect(Collectors.toList());
        PageResult<EducationInstitutionCollect> pageResult = new PageResult<>();
        pageResult.setTotal(new PageInfo<>(educationInstitutionList).getTotal());
        pageResult.setRows(educationInstitutionCollectList);
        return pageResult;
    }

    public Boolean doesCollect(AppUser appUser, Integer id) {
        if (appUser == null)
            return false;
        AppUserInstitutionCollectionExample appUserInstitutionCollectionExample = new AppUserInstitutionCollectionExample();
        appUserInstitutionCollectionExample.createCriteria().andEIdEqualTo(id).andUserIdEqualTo(appUser.getId());
        return appUserInstitutionCollectionMapper.selectByExample(appUserInstitutionCollectionExample).size() == 1;
    }
}
