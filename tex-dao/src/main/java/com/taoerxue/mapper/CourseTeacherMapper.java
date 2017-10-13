package com.taoerxue.mapper;

import com.taoerxue.pojo.CourseTeacher;
import com.taoerxue.pojo.CourseTeacherExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourseTeacherMapper {
    int countByExample(CourseTeacherExample example);

    int deleteByExample(CourseTeacherExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CourseTeacher record);

    int insertSelective(CourseTeacher record);

    List<CourseTeacher> selectByExample(CourseTeacherExample example);

    CourseTeacher selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CourseTeacher record, @Param("example") CourseTeacherExample example);

    int updateByExample(@Param("record") CourseTeacher record, @Param("example") CourseTeacherExample example);

    int updateByPrimaryKeySelective(CourseTeacher record);

    int updateByPrimaryKey(CourseTeacher record);
}