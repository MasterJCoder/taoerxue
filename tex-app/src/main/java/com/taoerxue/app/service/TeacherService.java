package com.taoerxue.app.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taoerxue.app.vo.teacher.TeacherDetail;
import com.taoerxue.common.bean.PageResult;
import com.taoerxue.common.os.StoragePlatform;
import com.taoerxue.mapper.MyCourseInfoMapper;
import com.taoerxue.mapper.TeacherMapper;
import com.taoerxue.pojo.CourseInfo;
import com.taoerxue.pojo.Teacher;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhihui on 2017-03-29 16:56.
 */
@Service
public class TeacherService {


    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private DozerBeanMapper dozerBeanMapper;
    @Resource
    private MyCourseInfoMapper myCourseInfoMapper;
    @Resource
    private StoragePlatform storagePlatform;

    public TeacherDetail detail(Integer id) {
        Teacher teacher = teacherMapper.selectByPrimaryKey(id);
        if (teacher == null)
            return null;
        TeacherDetail teacherDetail = dozerBeanMapper.map(teacher, TeacherDetail.class);
        teacherDetail.setPhoto(storagePlatform.getImageURL(teacherDetail.getPhoto()));


        List<TeacherDetail.course> courseList = courseList(id, 1, 2).getRows();

        //获取总的课程数


        teacherDetail.setCourseCount(myCourseInfoMapper.countByTeacherId(id));
        teacherDetail.setCourseList(courseList);
        return teacherDetail;
    }

    public PageResult<TeacherDetail.course> courseList(Integer id, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CourseInfo> courseInfoList = myCourseInfoMapper.listByTeacherId(id);
        PageInfo<CourseInfo> pageInfo = new PageInfo<>(courseInfoList);
        List<TeacherDetail.course> courseList = new ArrayList<>();
        for (CourseInfo courseInfo : courseInfoList) {
            TeacherDetail.course course = dozerBeanMapper.map(courseInfo, TeacherDetail.course.class);
            course.setPhoto(storagePlatform.getImageURL(course.getPhoto()));
            courseList.add(course);
        }
        PageResult<TeacherDetail.course> pageResult = new PageResult<>();
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setRows(courseList);
        return pageResult;
    }
}
