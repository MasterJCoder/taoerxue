package com.taoerxue.mapper;

import com.taoerxue.pojo.StudentType;
import com.taoerxue.pojo.StudentTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StudentTypeMapper {
    int countByExample(StudentTypeExample example);

    int deleteByExample(StudentTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StudentType record);

    int insertSelective(StudentType record);

    List<StudentType> selectByExample(StudentTypeExample example);

    StudentType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StudentType record, @Param("example") StudentTypeExample example);

    int updateByExample(@Param("record") StudentType record, @Param("example") StudentTypeExample example);

    int updateByPrimaryKeySelective(StudentType record);

    int updateByPrimaryKey(StudentType record);
}