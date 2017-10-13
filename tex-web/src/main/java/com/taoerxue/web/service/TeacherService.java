package com.taoerxue.web.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taoerxue.common.bean.PageResult;
import com.taoerxue.common.exception.SQLException;
import com.taoerxue.common.os.StoragePlatform;
import com.taoerxue.mapper.CourseTeacherMapper;
import com.taoerxue.mapper.TeacherMapper;
import com.taoerxue.pojo.CourseTeacher;
import com.taoerxue.pojo.CourseTeacherExample;
import com.taoerxue.pojo.Teacher;
import com.taoerxue.pojo.TeacherExample;
import com.taoerxue.web.vo.teacher.TeacherDetail;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 和教师相关的业务逻辑
 * Created by lizhihui on 2017-03-18 15:47.
 */
@Service
public class TeacherService {

    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private DozerBeanMapper dozerBeanMapper;
    @Resource
    private CourseTeacherMapper courseTeacherMapper;
    @Resource
    private StoragePlatform storagePlatform;

    /**
     * @param teacherId 教师 id
     * @param eid       机构 id
     * @param oldStatus 老状态
     * @param newStatus 新状态
     * @return 返回修改结果
     */
    @Transactional
    public boolean changeStatus(Integer teacherId, Integer eid, Integer oldStatus, Integer newStatus) {
        //获取教师信息
        Teacher teacher = teacherMapper.selectByPrimaryKey(teacherId);

        //如果教师信息为空,则修改失败
        if (teacher == null)
            return false;

        //如果该教师不属于该机构,则修改失败
        if (!teacher.geteId().equals(eid))
            return false;

        //修改教师状态
        teacher = new Teacher();
        teacher.setStatus(newStatus);

        //修改教师的前置条件
        TeacherExample teacherExample = new TeacherExample();
        teacherExample.createCriteria().andIdEqualTo(teacherId).andEIdEqualTo(eid).andStatusEqualTo(oldStatus);

        //更新
        int i = teacherMapper.updateByExampleSelective(teacher, teacherExample);
        if (i != 1) {
            throw new SQLException("更新异常");
        }
        return true;
    }

    /**
     * @param id  教师 id
     * @param eid 机构 id
     * @return 教师详情
     */
    public TeacherDetail getDetail(Integer id, Integer eid) {
        //获取教师信息
        Teacher teacher = teacherMapper.selectByPrimaryKey(id);
        if (teacher == null || !teacher.geteId().equals(eid))
            return null;


        TeacherDetail teacherDetail = dozerBeanMapper.map(teacher, TeacherDetail.class);
        teacherDetail.setPhoto(storagePlatform.getImageURL(teacherDetail.getPhoto()));

        //获取教师授课信息
        CourseTeacherExample courseTeacherExample = new CourseTeacherExample();
        courseTeacherExample.createCriteria().andTeacherIdEqualTo(id);
        List<CourseTeacher> courseTeacherList = courseTeacherMapper.selectByExample(courseTeacherExample);

        List<TeacherDetail.course> courseList = courseTeacherList.stream().
                map(courseTeacher -> dozerBeanMapper.map(courseTeacher, TeacherDetail.course.class)).collect(Collectors.toList());
        teacherDetail.setCourseList(courseList);
        return teacherDetail;
    }


    public boolean belongTo(List<Integer> teacherList, Integer eid) {
        TeacherExample teacherExample = new TeacherExample();
        teacherExample.createCriteria().andIdIn(teacherList).andStatusEqualTo(1).andEIdEqualTo(eid);
        return teacherMapper.countByExample(teacherExample) == teacherList.size();
    }

    /**
     * 我解释下下面这个代码,前端恶意用户传来可能包含位空的数组,比如 [null,null,2,null,3]这样的数字,
     * 我先把他转换成 Collection对象,使用他的 contains方法,是否包含 null,如果有,将其删除
     *
     * @param teachers 教师 id 数组
     */
    public List<Integer> isNull(Integer[] teachers) {
        if (teachers == null)
            return new ArrayList<>();
        ArrayList<Integer> teacherList = new ArrayList<>(Arrays.asList(teachers));
        while (teacherList.contains(null)) {
            teacherList.remove(null);
        }
        return teacherList;
    }

    public PageResult<Teacher> list(Integer eid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        TeacherExample teacherExample = new TeacherExample();
        teacherExample.createCriteria().andEIdEqualTo(eid).andStatusEqualTo(1);

        List<Teacher> teacherList = teacherMapper.selectByExample(teacherExample);
        PageInfo<Teacher> pageInfo = new PageInfo<>(teacherList);
        PageResult<Teacher> pageResult = new PageResult<>();
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setRows(teacherList);
        return pageResult;
    }
}
