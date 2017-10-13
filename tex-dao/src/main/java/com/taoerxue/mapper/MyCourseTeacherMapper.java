package com.taoerxue.mapper;

import com.taoerxue.pojo.CourseTeacher;

import java.util.List;

public interface MyCourseTeacherMapper {
    int insert(List<CourseTeacher> courseTeacherList);

    List<CourseTeacher> listByCourseIdAndEId(Integer id);
}