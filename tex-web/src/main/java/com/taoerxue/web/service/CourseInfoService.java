package com.taoerxue.web.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taoerxue.common.bean.PageResult;
import com.taoerxue.common.exception.SQLException;
import com.taoerxue.common.enums.CourseStatusEnum;
import com.taoerxue.common.enums.TeacherStatusEnum;
import com.taoerxue.common.os.StoragePlatform;
import com.taoerxue.mapper.*;
import com.taoerxue.po.CourseInfoMongoPOJO;
import com.taoerxue.pojo.*;
import com.taoerxue.web.vo.course.*;
import org.dozer.DozerBeanMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by lizhihui on 2017-03-21 14:06.
 */
@Service
public class CourseInfoService {
    @Resource
    private CourseInfoMapper courseInfoMapper;
    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private DozerBeanMapper dozerBeanMapper;
    @Resource
    private MyCourseTeacherMapper myCourseTeacherMapper;
    @Resource
    private StoragePlatform storagePlatform;
    @Resource
    private MyCourseInfoMapper myCourseInfoMapper;
    @Resource
    private CourseTeacherMapper courseTeacherMapper;
    @Resource
    private ClassInfoMapper classInfoMapper;
    @Resource
    private MongoTemplate mongoTemplate;

    @Transactional
    public Integer publish(CourseInfo courseInfo, List<Integer> teacherList) {

        //插入课程
        courseInfoMapper.insertSelective(courseInfo);

        //获取老师信息
        TeacherExample teacherExample = new TeacherExample();
        teacherExample.createCriteria().andIdIn(teacherList);
        List<Teacher> teachers = teacherMapper.selectByExample(teacherExample);

        List<CourseTeacher> courseTeacherList = getCourseTeacher(teachers, courseInfo);
        //插入课程对应的教师信息
        myCourseTeacherMapper.insert(courseTeacherList);
        return courseInfo.getId();
    }

    public CourseDetail getDetail(Integer id, Integer eid) {
        CourseInfo courseInfo = courseInfoMapper.selectByPrimaryKey(id);
        if (courseInfo == null || !courseInfo.geteId().equals(eid))
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
        courseDetail.setStudentType(typeList);

        List<CourseTeacher> teacherList = myCourseTeacherMapper.listByCourseIdAndEId(id);
        List<CourseDetail.TeacherInfo> teacherInfoList = new ArrayList<>();
        for (CourseTeacher courseTeacher : teacherList) {
            CourseDetail.TeacherInfo teacherInfo = new CourseDetail.TeacherInfo();
            teacherInfo.setId(courseTeacher.getTeacherId());
            teacherInfo.setName(courseTeacher.getTeacherName());
            teacherInfoList.add(teacherInfo);
        }
        courseDetail.setTeacherList(teacherInfoList);
        courseDetail.setPhoto(storagePlatform.getImageURL(courseDetail.getPhoto()));

        return courseDetail;
    }

    public boolean isEditable(Integer courseId, Integer eid) {

        // TODO 以后做个记录,看下有没有恶意用户添加课程
        CourseInfo courseInfo = courseInfoMapper.selectByPrimaryKey(courseId);
        return !(courseInfo == null || !courseInfo.geteId().equals(eid) || !courseInfo.getStatus().equals(0));
    }

    /**
     * 查看这个课程信息是否存在
     *
     * @param courseId 课程 id
     * @param eid      机构 id
     * @return 是否存在
     */
    public boolean isExists(Integer courseId, Integer eid) {
        CourseInfoExample courseInfoExample = new CourseInfoExample();
        courseInfoExample.createCriteria().andIdEqualTo(courseId).andEIdEqualTo(eid);
        return courseInfoMapper.countByExample(courseInfoExample) == 1;
    }

    /**
     * 查看该课程是否添加了班级
     *
     * @param courseId 课程 id
     * @return 是否添加班级信息
     */
    public boolean doesAddClassInfo(Integer courseId) {
        ClassInfoExample classInfoExample = new ClassInfoExample();
        classInfoExample.createCriteria().andCourseIdEqualTo(courseId).andStatusEqualTo(1);
        return classInfoMapper.countByExample(classInfoExample) > 0;
    }

    /**
     * 改变课程的状态
     *
     * @param courseId  课程 id
     * @param oldStatus 老状态
     * @param newStatus 新状态
     */
    @Transactional
    public void changeStatus(Integer courseId, Integer oldStatus, Integer newStatus) {
        int i = myCourseInfoMapper.updateStatus(courseId, oldStatus, newStatus);
        if (i != 1)
            throw new SQLException("请求提交失败");

        //如果是取消上架,去删除 mongo
        if (oldStatus.equals(CourseStatusEnum.THROUGH_AUDIT.getStatus()) && newStatus.equals(CourseStatusEnum.NOT_SUBMIT.getStatus())) {
            mongoTemplate.findAndRemove(new Query(Criteria.where("_id").is(courseId)), CourseInfoMongoPOJO.class);
        }
    }

    public CourseDetailWithClassInfo getDetailWithClassInfo(Integer id, Integer eid) {
        CourseDetail courseDetail = getDetail(id, eid);
        if (courseDetail == null)
            return null;
        CourseDetailWithClassInfo courseDetailWithClassInfo = dozerBeanMapper.map(courseDetail, CourseDetailWithClassInfo.class);
        ClassInfoExample classInfoExample = new ClassInfoExample();
        classInfoExample.createCriteria().andCourseIdEqualTo(id).andStatusEqualTo(1);
        List<ClassInfo> classInfoList = classInfoMapper.selectByExample(classInfoExample);

        List<CourseDetailWithClassInfo.ClassInfo> classInfos = classInfoList.stream()
                .map(classInfo -> dozerBeanMapper.map(classInfo, CourseDetailWithClassInfo.ClassInfo.class)).collect(Collectors.toList());

        courseDetailWithClassInfo.setClassInfoList(classInfos);
        return courseDetailWithClassInfo;
    }

    public CourseInfo get(Integer id) {
        return courseInfoMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public void update(CourseInfo courseInfo, List<Integer> teacherList) {
        //课程信息不为空,更新课程信息
        if (!isNull(courseInfo))
            courseInfoMapper.updateByPrimaryKeySelective(courseInfo);
        //判断教师列表是否为空
        if (teacherList.size() != 0) {
            //先删除所有该课程所有老师信息
            CourseTeacherExample courseTeacherExample = new CourseTeacherExample();
            courseTeacherExample.createCriteria().andCourseIdEqualTo(courseInfo.getId());
            courseTeacherMapper.deleteByExample(courseTeacherExample);
            //然后再添加
            //获取老师信息
            TeacherExample teacherExample = new TeacherExample();
            teacherExample.createCriteria().andIdIn(teacherList);
            List<Teacher> teachers = teacherMapper.selectByExample(teacherExample);
            List<CourseTeacher> courseTeacherList = getCourseTeacher(teachers, courseInfo);
            myCourseTeacherMapper.insert(courseTeacherList);
        } else {
            //那就说明没传老师...因为是单表.所以更新是件很麻烦的事.如果课程名改了.课程-老师这张表也得改课程名...f**k
            if (courseInfo.getName() != null) {
                CourseTeacher courseTeacher = new CourseTeacher();
                courseTeacher.setCourseName(courseInfo.getName());
                CourseTeacherExample courseTeacherExample = new CourseTeacherExample();
                courseTeacherExample.createCriteria().andCourseIdEqualTo(courseInfo.getId());
                courseTeacherMapper.updateByExampleSelective(courseTeacher, courseTeacherExample);
            }
        }
    }

    /**
     * 通过老师列表和对应的课程信息获取老师和课程的组合信息
     *
     * @param teachers   老师列表
     * @param courseInfo 课程信息
     * @return 哦
     */
    private List<CourseTeacher> getCourseTeacher(List<Teacher> teachers, CourseInfo courseInfo) {
        List<CourseTeacher> courseTeacherList = new ArrayList<>();
        for (Teacher teacher : teachers) {
            CourseTeacher courseTeacher = dozerBeanMapper.map(teacher, CourseTeacher.class);
            courseTeacher.setCourseId(courseInfo.getId());
            courseTeacher.setCourseName(courseInfo.getName());
            courseTeacher.setTeacherId(teacher.getId());
            courseTeacher.setTeacherName(teacher.getName());
            courseTeacherList.add(courseTeacher);
        }
        return courseTeacherList;
    }

    private boolean isNull(CourseInfo courseInfo) {
        return courseInfo.getName() == null &&
                courseInfo.getCount() == null &&
                courseInfo.getPhoto() == null &&
                courseInfo.getTarget() == null &&
                courseInfo.getCharacteristic() == null &&
                courseInfo.getDuration() == null &&
                courseInfo.getPrice() == null &&
                courseInfo.getTypeId() == null &&
                courseInfo.getStudentTypeIds() == null &&
                courseInfo.getStudentLevelId() == null;

    }

    public List<CourseTeacherSimple> getTeacherList(Integer eId) {
        TeacherExample teacherExample = new TeacherExample();
        teacherExample.createCriteria().andStatusEqualTo(TeacherStatusEnum.NORMAL.getStatus()).andEIdEqualTo(eId);
        List<Teacher> teachers = teacherMapper.selectByExample(teacherExample);
        return teachers.stream().map((teacher -> dozerBeanMapper.map(teacher, CourseTeacherSimple.class))).collect(toList());
    }

    public PageResult<CourseThroughAudit> throughAuditList(Integer eId, Integer pageNum, Integer pageSize) {
        List<CourseInfoMongoPOJO> courseInfoMongoPOJOList = mongoTemplate.find(new Query(Criteria.where("eId").is(eId)).skip((pageNum - 1) * pageSize).limit(pageSize), CourseInfoMongoPOJO.class);
        List<CourseThroughAudit> courseThroughAuditList = courseInfoMongoPOJOList.stream().map(courseInfoMongoPOJO -> dozerBeanMapper.map(courseInfoMongoPOJO, CourseThroughAudit.class)).collect(Collectors.toList());
        PageResult<CourseThroughAudit> pageResult = new PageResult<>();
        pageResult.setTotal(mongoTemplate.count(new Query(Criteria.where("eId").is(eId)), CourseInfoMongoPOJO.class));
        pageResult.setRows(courseThroughAuditList);
        return pageResult;
    }

    public PageResult auditingList(Integer eId, Integer pageNum, Integer pageSize) {
        CourseInfoExample courseInfoExample = new CourseInfoExample();
        courseInfoExample.createCriteria().andEIdEqualTo(eId).andStatusEqualTo(CourseStatusEnum.AUDITING.getStatus());
        PageHelper.startPage(pageNum, pageSize);
        List<CourseInfo> courseInfoList = courseInfoMapper.selectByExample(courseInfoExample);
        List<CourseAuditing> courseAuditingList = courseInfoList.stream().map(courseInfo -> dozerBeanMapper.map(courseInfo, CourseAuditing.class)).collect(Collectors.toList());
        PageInfo<CourseInfo> pageInfo = new PageInfo<>(courseInfoList);
        PageResult<CourseAuditing> pageResult = new PageResult<>();
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setRows(courseAuditingList);
        return pageResult;
    }

    public PageResult newPublishList(Integer eId, Integer pageNum, Integer pageSize) {
        CourseInfoExample courseInfoExample = new CourseInfoExample();
        courseInfoExample.createCriteria().andEIdEqualTo(eId).andStatusEqualTo(CourseStatusEnum.NOT_SUBMIT.getStatus());
        PageHelper.startPage(pageNum, pageSize);
        List<CourseInfo> courseInfoList = courseInfoMapper.selectByExample(courseInfoExample);
        List<CourseNewPublish> courseNewPublishList = courseInfoList.stream().map(courseInfo -> dozerBeanMapper.map(courseInfo, CourseNewPublish.class)).collect(Collectors.toList());
        PageInfo<CourseInfo> pageInfo = new PageInfo<>(courseInfoList);
        PageResult<CourseNewPublish> pageResult = new PageResult<>();
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setRows(courseNewPublishList);
        return pageResult;
    }
}
