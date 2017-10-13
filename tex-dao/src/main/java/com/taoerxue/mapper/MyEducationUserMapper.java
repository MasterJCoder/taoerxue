package com.taoerxue.mapper;

import com.taoerxue.pojo.EducationUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyEducationUserMapper {
    EducationUser getByPhone(String phone);

    long countByPhone(String phone);


    String getSaltByPhone(String phone);

    int updatePasswordByPhone(@Param("phone") String phone, @Param("password") String password);

    int updateAfterRegister(@Param("id") Integer id, @Param("eid") Integer eid);

    List<EducationUser> listNameAndPhoneByEId(Integer eId);

    String getPhone(Integer eid);
}