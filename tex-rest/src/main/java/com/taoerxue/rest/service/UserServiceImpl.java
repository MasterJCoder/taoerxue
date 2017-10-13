package com.taoerxue.rest.service;

import com.taoerxue.common.bean.Result;
import com.taoerxue.mapper.AppUserMapper;
import com.taoerxue.pojo.AppUser;
import com.taoerxue.util.RandomUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

/**
 * Created by lizhihui on 2017-03-09 14:59.
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private AppUserMapper appUserMapper;

    public Result addAppUser(String phone, String password) {
        String salt = RandomUtils.generateString(8);
        AppUser appUser = new AppUser();
        appUser.setPhone(phone);
        appUser.setPassword(DigestUtils.md5DigestAsHex((DigestUtils.md5DigestAsHex(password.getBytes()) + salt).getBytes()));
        appUser.setSalt(salt);
        try {
            appUserMapper.insertSelective(appUser);
        } catch (Exception e) {
            return Result.build(500, "该用户已经注册");
        }
        return Result.ok();
    }
}
