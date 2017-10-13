package com.taoerxue.app.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taoerxue.app.qo.CourseQuery;
import com.taoerxue.app.vo.course.CourseCollect;
import com.taoerxue.app.vo.course.CourseDetail;
import com.taoerxue.app.vo.course.CourseRecommend;
import com.taoerxue.app.vo.course.CourseSimple;
import com.taoerxue.common.bean.PageResult;
import com.taoerxue.common.enums.CourseStatusEnum;
import com.taoerxue.common.exception.SQLException;
import com.taoerxue.common.os.StoragePlatform;
import com.taoerxue.mapper.*;
import com.taoerxue.po.CourseInfoMongoPOJO;
import com.taoerxue.pojo.*;
import com.taoerxue.util.LocationUtils;
import com.taoerxue.util.MathUtils;
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
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lizhihui on 2017-03-27 10:31.
 */
@Service
public class CourseService extends BaseService {
    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private DozerBeanMapper dozerBeanMapper;
    @Resource
    private StoragePlatform storagePlatform;
    @Resource
    private CourseInfoMapper courseInfoMapper;
    @Resource
    private CourseTeacherMapper courseTeacherMapper;
    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private ClassInfoMapper classInfoMapper;
    @Resource
    private MyCourseInfoMapper myCourseInfoMapper;
    @Resource
    private AppUserCourseCollectionMapper appUserCourseCollectionMapper;

    public PageResult<CourseSimple> list(CourseQuery courseQuery, AppUser user, Integer pageNum, Integer pageSize) {
        Query query = getQueryByConditions(courseQuery, user);
        query.skip((pageNum - 1) * pageSize).limit(pageSize);
        List<CourseInfoMongoPOJO> courseInfoMongoPOJOS = mongoTemplate.find(query, CourseInfoMongoPOJO.class);
        List<CourseSimple> simpleList = new ArrayList<>();
        for (CourseInfoMongoPOJO courseInfoMongoPOJO : courseInfoMongoPOJOS) {
            CourseSimple courseSimple = dozerBeanMapper.map(courseInfoMongoPOJO, CourseSimple.class);
            if (courseQuery.getDisplay())
                courseSimple.setDistance(LocationUtils.calculateDistance(LocationUtils.calculateDistance(courseQuery.getLng(), courseQuery.getLat(), courseInfoMongoPOJO.getLocation()[0], courseInfoMongoPOJO.getLocation()[1])));

            courseSimple.setPhoto(storagePlatform.getImageURL(courseSimple.getPhoto()));
            simpleList.add(courseSimple);
        }
        PageResult<CourseSimple> pageResult = new PageResult<>();
        pageResult.setRows(simpleList);
        pageResult.setTotal(mongoTemplate.count(query, CourseInfoMongoPOJO.class));
        return pageResult;
    }


    /**
     * 根据条件获取查询语句
     *
     * @param courseQuery 查询封装
     * @param appUser     app 用户
     * @return mongo 的查询 (Query) 对象
     */
    private Query getQueryByConditions(CourseQuery courseQuery, AppUser appUser) {

        Query query = new Query();

        //用户选择的类型大于用户自身期望类型
        //所以先判断用户有没有选择类型
        if (courseQuery.getTypeId() != null)
            query.addCriteria(Criteria.where("typeId").is(courseQuery.getTypeId()));
        else if (courseQuery.getParentTypeId() != null)
            query.addCriteria(Criteria.where("parentTypeId").is(courseQuery.getParentTypeId()));
        else {
            //到这里,说明用户没选择,那就判断用户有没有登录吧
            if (appUser != null && !appUser.getExpectation().equals(7)) {
                List<Integer> typeList = expectationToEducationType(MathUtils.convert(appUser.getExpectation()));
                query.addCriteria(Criteria.where("typeId").in(typeList));
            }
        }

        if (courseQuery.getCityId() != null)
            query.addCriteria(Criteria.where("cityId").is(courseQuery.getCityId()));
        else
            query.addCriteria(Criteria.where("areaId").is(courseQuery.getAreaId()));

        String order = courseQuery.getOrder();
        if (!StringUtils.isEmpty(order)) {
            switch (order) {
                case "collections":
                    query.with(new Sort(Sort.Direction.DESC, order));
                    break;
                case "distance":
                    query.addCriteria(Criteria.where("location").nearSphere(new Point(courseQuery.getLng(), courseQuery.getLat())));
                    break;
                case "smart":
                    query.with(new Sort(Sort.Direction.DESC, "collections"));
                    query.with(new Sort(Sort.Direction.DESC, "_id"));
                    break;
            }
        }
        String like = courseQuery.getLike();
        if (!StringUtils.isEmpty(like)) {
            query.addCriteria(Criteria.where("name").regex("^.*" + like + ".*$"));
        }
        return query;
    }

    public List<CourseRecommend> recommendList(CourseQuery courseQuery, AppUser appUser) {
        Query query = getQueryByConditions(courseQuery, appUser);
        query.limit(6);
        List<CourseInfoMongoPOJO> courseInfoMongoPOJOS = mongoTemplate.find(query, CourseInfoMongoPOJO.class);
        List<CourseRecommend> recommendList = new ArrayList<>();
        for (CourseInfoMongoPOJO courseInfoMongoPOJO : courseInfoMongoPOJOS) {
            CourseRecommend courseRecommend = dozerBeanMapper.map(courseInfoMongoPOJO, CourseRecommend.class);
            if (courseQuery.getDisplay())
                courseRecommend.setDistance(LocationUtils.calculateDistance(LocationUtils.calculateDistance(courseQuery.getLng(), courseQuery.getLat(), courseInfoMongoPOJO.getLocation()[0], courseInfoMongoPOJO.getLocation()[1])));
            courseRecommend.setPhoto(storagePlatform.getImageURL(courseRecommend.getPhoto()));

            recommendList.add(courseRecommend);
        }
        return recommendList;
    }


    public CourseDetail detail(Integer id) {
        CourseInfo courseInfo = courseInfoMapper.selectByPrimaryKey(id);
        if (courseInfo == null || !courseInfo.getStatus().equals(CourseStatusEnum.THROUGH_AUDIT.getStatus()))
            return null;
        CourseDetail courseDetail = dozerBeanMapper.map(courseInfo, CourseDetail.class);
        Integer studentTypeIds = courseInfo.getStudentTypeIds();
        char[] chars = Integer.toBinaryString(studentTypeIds).toCharArray();
        List<Integer> typeList = new ArrayList<>();
        for (int i = chars.length; i > 0; i--) {
            int o = Integer.parseInt(String.valueOf(chars[i - 1])) * (1 << chars.length - i);
            if (o != 0)
                typeList.add(o);
        }
        String[] split = courseInfo.getPhoto().split(":");
        List<String> photoList = new ArrayList<>();
        for (String photo : split) {
            photoList.add(storagePlatform.getImageURL(photo));
        }
        courseDetail.setPhotoList(photoList);
        courseDetail.setStudentType(typeList);

        //获取教师列表
        CourseTeacherExample courseTeacherExample = new CourseTeacherExample();
        courseTeacherExample.createCriteria().andCourseIdEqualTo(id);
        PageHelper.startPage(1, 2);
        List<CourseTeacher> courseTeacherList = courseTeacherMapper.selectByExample(courseTeacherExample);
        courseDetail.setTeacherCount(new PageInfo<CourseTeacher>(courseTeacherList).getTotal());
        List<Integer> teacherIdList = new ArrayList<>();
        for (CourseTeacher courseTeacher : courseTeacherList) {
            teacherIdList.add(courseTeacher.getTeacherId());
        }
        TeacherExample teacherExample = new TeacherExample();
        teacherExample.createCriteria().andIdIn(teacherIdList);
        List<Teacher> teachers = teacherMapper.selectByExample(teacherExample);
        List<CourseDetail.Teacher> teacherList = new ArrayList<>();
        for (Teacher teacher : teachers) {
            CourseDetail.Teacher teacher1 = dozerBeanMapper.map(teacher, CourseDetail.Teacher.class);
            teacher1.setPhoto(storagePlatform.getImageURL(teacher1.getPhoto()));
            teacherList.add(teacher1);
        }
        courseDetail.setTeacherList(teacherList);

        //获取班级列表
        ClassInfoExample classInfoExample = new ClassInfoExample();
        classInfoExample.createCriteria().andCourseIdEqualTo(id).andStatusEqualTo(1);
        List<ClassInfo> infoList = classInfoMapper.selectByExample(classInfoExample);
        List<CourseDetail.ClassInfo> classInfoList = new ArrayList<>();
        for (ClassInfo classInfo : infoList) {
            CourseDetail.ClassInfo classInfo1 = dozerBeanMapper.map(classInfo, CourseDetail.ClassInfo.class);
            classInfoList.add(classInfo1);
        }
        courseDetail.setClassList(classInfoList);
        return courseDetail;
    }

    public List<CourseDetail.Teacher> teacherList(Integer id) {
        CourseTeacherExample courseTeacherExample = new CourseTeacherExample();
        courseTeacherExample.createCriteria().andCourseIdEqualTo(id);
        List<CourseTeacher> courseTeacherList = courseTeacherMapper.selectByExample(courseTeacherExample);
        List<Integer> teacherIdList = new ArrayList<>();
        for (CourseTeacher courseTeacher : courseTeacherList) {
            teacherIdList.add(courseTeacher.getTeacherId());
        }

        TeacherExample teacherExample = new TeacherExample();
        teacherExample.createCriteria().andIdIn(teacherIdList);
        List<Teacher> teachers = teacherMapper.selectByExample(teacherExample);
        List<CourseDetail.Teacher> teacherDetails = new ArrayList<>();
        for (Teacher teacher : teachers) {
            CourseDetail.Teacher teacherDetail = dozerBeanMapper.map(teacher, CourseDetail.Teacher.class);
            teacherDetail.setPhoto(storagePlatform.getImageURL(teacherDetail.getPhoto()));
            teacherDetails.add(teacherDetail);
        }
        return teacherDetails;
    }

    public List<String> search(String like) {
        return myCourseInfoMapper.listNameLike(like);
    }

    public void collect(Integer courseId, Integer id) {
        AppUserCourseCollection appUserCourseCollection = new AppUserCourseCollection();
        appUserCourseCollection.setCourseId(courseId);
        appUserCourseCollection.setUserId(id);
        try {

            appUserCourseCollectionMapper.insertSelective(appUserCourseCollection);
        } catch (Exception e) {
            throw new SQLException("您已经收藏过该课程");
        }
    }

    public void unCollect(Integer courseId, Integer id) {
        AppUserCourseCollectionExample appUserCourseCollectionExample = new AppUserCourseCollectionExample();
        appUserCourseCollectionExample.createCriteria().andCourseIdEqualTo(courseId).andUserIdEqualTo(id);
        try {
            appUserCourseCollectionMapper.deleteByExample(appUserCourseCollectionExample);

        } catch (Exception e) {
            throw new SQLException("取消收藏失败");
        }
    }

    public PageResult<CourseCollect> collectList(Integer id, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CourseInfo> courseInfoList = myCourseInfoMapper.collectList(id);
        PageInfo<CourseInfo> pageInfo = new PageInfo<>(courseInfoList);
        List<CourseCollect> collectList = courseInfoList
                .stream()
                .map(courseInfo -> {
                    CourseCollect courseCollect = dozerBeanMapper.map(courseInfo, CourseCollect.class);
                    courseCollect.setPhoto(storagePlatform.getImageURL(courseCollect.getPhoto()));
                    return courseCollect;
                })
                .collect(Collectors.toList());
        PageResult<CourseCollect> pageResult = new PageResult<>();
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setRows(collectList);
        return pageResult;
    }

    public Boolean doesCollect(AppUser appUser, Integer id) {
        if (appUser == null)
            return false;
        AppUserCourseCollectionExample appUserCourseCollectionExample = new AppUserCourseCollectionExample();
        appUserCourseCollectionExample.createCriteria().andUserIdEqualTo(appUser.getId()).andCourseIdEqualTo(id);
        return appUserCourseCollectionMapper.selectByExample(appUserCourseCollectionExample).size() == 1;
    }
}
