package com.taoerxue.manager.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taoerxue.common.bean.PageResult;
import com.taoerxue.common.enums.CourseStatusEnum;
import com.taoerxue.common.enums.EducationInstitutionStatusEnum;
import com.taoerxue.common.exception.SQLException;
import com.taoerxue.common.os.StoragePlatform;
import com.taoerxue.manager.vo.course.CourseDetail;
import com.taoerxue.manager.vo.course.CourseInfoNewest;
import com.taoerxue.mapper.*;
import com.taoerxue.pojo.*;
import com.taoerxue.qo.CourseInfoQuery;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lizhihui on 2017-03-23 14:46.
 */
@Service
public class CourseService {

    @Resource
    private CourseInfoMapper courseInfoMapper;
    @Resource
    private DozerBeanMapper dozerBeanMapper;
    @Resource
    private ClassInfoMapper classInfoMapper;
    @Resource
    private EducationInstitutionMapper educationInstitutionMapper;
    @Resource
    private StoragePlatform storagePlatform;
    @Resource
    private MyCourseInfoMapper myCourseInfoMapper;

    @Resource
    private CourseTeacherMapper courseTeacherMapper;

    public PageResult<CourseInfo> list(CourseInfoQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());

        List<CourseInfo> courseInfoList = myCourseInfoMapper.list(query);

        PageInfo<CourseInfo> pageInfo = new PageInfo<>(courseInfoList);
        PageResult<CourseInfo> result = new PageResult<>();
        result.setRows(courseInfoList);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    public boolean isExists(Integer courseId) {
        CourseInfoExample courseInfoExample = new CourseInfoExample();
        courseInfoExample.createCriteria().andIdEqualTo(courseId);
        return courseInfoMapper.countByExample(courseInfoExample) == 1;
    }

    @Transactional
    public void throughAudit(Integer id, Integer oldStatus, Integer newStatus) {
        CourseInfo courseInfo = new CourseInfo();
        CourseInfoExample courseInfoExample = new CourseInfoExample();
        courseInfo.setStatus(newStatus);
        courseInfo.setThroughTime(new Date());
        courseInfoExample.createCriteria().andIdEqualTo(id).andStatusEqualTo(oldStatus);
        int i = courseInfoMapper.updateByExampleSelective(courseInfo, courseInfoExample);
        if (i != 1)
            throw new SQLException("审核通过失败,请刷新列表");
    }

    public CourseInfo get(Integer id) {
        return courseInfoMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public void failToAudit(Integer id, Integer oldStatus, Integer newStatus) {
        CourseInfo courseInfo = new CourseInfo();
        CourseInfoExample courseInfoExample = new CourseInfoExample();
        courseInfo.setStatus(newStatus);
        courseInfoExample.createCriteria().andIdEqualTo(id).andStatusEqualTo(oldStatus);
        int i = courseInfoMapper.updateByExampleSelective(courseInfo, courseInfoExample);
        if (i != 1)
            throw new SQLException("课程状态更新失败,请刷新列表");
    }

    public CourseDetail getDetail(Integer id) {
        //获取机构信息
        CourseInfo courseInfo = courseInfoMapper.selectByPrimaryKey(id);
        CourseDetail courseDetail = dozerBeanMapper.map(courseInfo, CourseDetail.class);
        courseDetail.setPhoto(storagePlatform.getImageURL(courseDetail.getPhoto()));

        //获取班级信息
        ClassInfoExample classInfoExample = new ClassInfoExample();
        classInfoExample.createCriteria().andCourseIdEqualTo(id).andStatusEqualTo(1);
        List<ClassInfo> classInfoList = classInfoMapper.selectByExample(classInfoExample);
        List<CourseDetail.ClassInfo> classInfoVOList = classInfoList.stream()
                .map(classInfo -> dozerBeanMapper.map(classInfo, CourseDetail.ClassInfo.class))
                .collect(Collectors.toList());
        courseDetail.setClassList(classInfoVOList);
        //获取老师详情
        CourseTeacherExample courseTeacherExample = new CourseTeacherExample();
        courseTeacherExample.createCriteria().andCourseIdEqualTo(id);
        List<String> nameList = courseTeacherMapper.selectByExample(courseTeacherExample)
                .stream()
                .map(CourseTeacher::getTeacherName)
                .collect(Collectors.toList());
        courseDetail.setTeacherList(nameList);

        //获取机构信息
        EducationInstitution educationInstitution = educationInstitutionMapper.selectByPrimaryKey(courseInfo.geteId());
        CourseDetail.EducationInfo educationInfo = dozerBeanMapper.map(educationInstitution, CourseDetail.EducationInfo.class);
        courseDetail.setEducationInfo(educationInfo);
        return courseDetail;

    }

    public Integer allCount() {
        CourseInfoExample courseInfoExample = new CourseInfoExample();
        courseInfoExample.createCriteria().andStatusEqualTo(CourseStatusEnum.THROUGH_AUDIT.getStatus());

        return courseInfoMapper.countByExample(courseInfoExample);
    }

    public Integer monthlyCount() {
        return myCourseInfoMapper.monthlyCount();
    }

    public PageResult newestCourseList() {
        PageHelper.startPage(1, 8);
        CourseInfoExample courseInfoExample = new CourseInfoExample();
        courseInfoExample.setOrderByClause("create_time desc");
        courseInfoExample.createCriteria().andStatusEqualTo(EducationInstitutionStatusEnum.AUDITING.getStatus());
        List<CourseInfo> courseInfoList = courseInfoMapper.selectByExample(courseInfoExample);
        PageInfo<CourseInfo> pageInfo = new PageInfo<>(courseInfoList);
        PageResult<CourseInfoNewest> pageResult = new PageResult<>();
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setRows(courseInfoList
                .stream()
                .map(courseInfo -> dozerBeanMapper.map(courseInfo, CourseInfoNewest.class))
                .collect(Collectors.toList()));
        return pageResult;
    }
}
