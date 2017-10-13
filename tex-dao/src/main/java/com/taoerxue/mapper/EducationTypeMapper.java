package com.taoerxue.mapper;

import com.taoerxue.pojo.EducationType;
import com.taoerxue.pojo.EducationTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EducationTypeMapper {
    int countByExample(EducationTypeExample example);

    int deleteByExample(EducationTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EducationType record);

    int insertSelective(EducationType record);

    List<EducationType> selectByExample(EducationTypeExample example);

    EducationType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EducationType record, @Param("example") EducationTypeExample example);

    int updateByExample(@Param("record") EducationType record, @Param("example") EducationTypeExample example);

    int updateByPrimaryKeySelective(EducationType record);

    int updateByPrimaryKey(EducationType record);
}