package com.taoerxue.mapper;

import com.taoerxue.pojo.AppUser;
import com.taoerxue.qo.AppUserQuery;

import java.util.List;

public interface MyAppUserMapper {
    List<AppUser> list(AppUserQuery query);

    AppUser getByPhone(String phone);

    Integer monthlyCount();
}