package com.taoerxue.mapper;

import com.taoerxue.pojo.AppUserInstitutionCollection;
import com.taoerxue.pojo.AppUserInstitutionCollectionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppUserInstitutionCollectionMapper {
    int countByExample(AppUserInstitutionCollectionExample example);

    int deleteByExample(AppUserInstitutionCollectionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AppUserInstitutionCollection record);

    int insertSelective(AppUserInstitutionCollection record);

    List<AppUserInstitutionCollection> selectByExample(AppUserInstitutionCollectionExample example);

    AppUserInstitutionCollection selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AppUserInstitutionCollection record, @Param("example") AppUserInstitutionCollectionExample example);

    int updateByExample(@Param("record") AppUserInstitutionCollection record, @Param("example") AppUserInstitutionCollectionExample example);

    int updateByPrimaryKeySelective(AppUserInstitutionCollection record);

    int updateByPrimaryKey(AppUserInstitutionCollection record);
}