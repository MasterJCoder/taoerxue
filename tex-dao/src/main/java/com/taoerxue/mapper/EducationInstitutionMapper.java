package com.taoerxue.mapper;

import com.taoerxue.pojo.EducationInstitution;
import com.taoerxue.pojo.EducationInstitutionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EducationInstitutionMapper {
    int countByExample(EducationInstitutionExample example);

    int deleteByExample(EducationInstitutionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EducationInstitution record);

    int insertSelective(EducationInstitution record);

    List<EducationInstitution> selectByExample(EducationInstitutionExample example);

    EducationInstitution selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EducationInstitution record, @Param("example") EducationInstitutionExample example);

    int updateByExample(@Param("record") EducationInstitution record, @Param("example") EducationInstitutionExample example);

    int updateByPrimaryKeySelective(EducationInstitution record);

    int updateByPrimaryKey(EducationInstitution record);
}