package com.taoerxue.mapper;

import com.taoerxue.pojo.EducationUser;
import com.taoerxue.pojo.EducationUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EducationUserMapper {
    int countByExample(EducationUserExample example);

    int deleteByExample(EducationUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EducationUser record);

    int insertSelective(EducationUser record);

    List<EducationUser> selectByExample(EducationUserExample example);

    EducationUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EducationUser record, @Param("example") EducationUserExample example);

    int updateByExample(@Param("record") EducationUser record, @Param("example") EducationUserExample example);

    int updateByPrimaryKeySelective(EducationUser record);

    int updateByPrimaryKey(EducationUser record);
}