package com.taoerxue.rest.service;

import com.taoerxue.common.bean.Result;

/**
 * Created by lizhihui on 2017-03-09 14:59.
 */
public interface UserService {
    Result addAppUser(String phone,String password);
}
