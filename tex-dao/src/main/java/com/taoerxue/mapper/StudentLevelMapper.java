package com.taoerxue.mapper;

import com.taoerxue.pojo.StudentLevel;
import com.taoerxue.pojo.StudentLevelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StudentLevelMapper {
    int countByExample(StudentLevelExample example);

    int deleteByExample(StudentLevelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StudentLevel record);

    int insertSelective(StudentLevel record);

    List<StudentLevel> selectByExample(StudentLevelExample example);

    StudentLevel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StudentLevel record, @Param("example") StudentLevelExample example);

    int updateByExample(@Param("record") StudentLevel record, @Param("example") StudentLevelExample example);

    int updateByPrimaryKeySelective(StudentLevel record);

    int updateByPrimaryKey(StudentLevel record);
}