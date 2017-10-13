package com.taoerxue.manager.service;

import com.taoerxue.common.bean.Result;
import com.taoerxue.common.cache.redis.JedisClient;
import com.taoerxue.common.exception.SQLException;
import com.taoerxue.mapper.AdminUserMapper;
import com.taoerxue.pojo.AdminUser;
import com.taoerxue.pojo.AdminUserExample;
import com.taoerxue.util.JsonUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by lizhihui on 2017-04-17 11:47.
 */
@Service
public class UserService {

    @Resource
    private JedisClient jedisClient;
    @Resource
    private AdminUserMapper adminUserMapper;

    public String generateToken(AdminUser adminUser) {
        String oldToken = jedisClient.get("manager:user:phone:" + adminUser.getPhone());
        if (oldToken != null) {
            jedisClient.del(oldToken);
        }

        //6.向 redis 中添加登录成功凭证
        String token = UUID.randomUUID().toString().replace("-", "");
        jedisClient.set("manager:user:phone:" + adminUser.getPhone(), token);
        jedisClient.set(token, JsonUtils.objectToJson(adminUser));
        return token;
    }

    public Result check(String phone, String password) {
        AdminUserExample adminUserExample = new AdminUserExample();
        adminUserExample.createCriteria().andPhoneEqualTo(phone);
        List<AdminUser> adminUsers = adminUserMapper.selectByExample(adminUserExample);
        if (adminUsers.size() < 1)
            return Result.build(500, "帐号不存在");
        AdminUser adminUser = adminUsers.get(0);
        if (adminUser == null || !DigestUtils.md5DigestAsHex(password.trim().getBytes()).equals(adminUser.getPassword()))
            return Result.build(500, "帐号不存在或密码错误");
        if (adminUser.getStatus().equals(false))
            return Result.build(500, "该帐号已经被禁用,请联系管理员");

        //修改登录信息
        changeLoginTime(phone);
        //返回管理员信息
        return Result.ok(adminUser);
    }


    private void changeLoginTime(String phone) {
        AdminUserExample adminUserExample = new AdminUserExample();
        adminUserExample.createCriteria().andPhoneEqualTo(phone);
        AdminUser adminUser = new AdminUser();
        adminUser.setLoginTime(new Date());

        adminUserMapper.updateByExampleSelective(adminUser, adminUserExample);

    }

    public void resetPassword(String phone, String password) {
        AdminUserExample adminUserExample = new AdminUserExample();
        adminUserExample.createCriteria().andPhoneEqualTo(phone);
        List<AdminUser> adminUsers = adminUserMapper.selectByExample(adminUserExample);
        if (adminUsers.size() == 0)
            throw new SQLException("帐号不存在");

        AdminUser adminUser = adminUsers.get(0);
        String newPassword = DigestUtils.md5DigestAsHex(password.trim().getBytes());
        Integer id = adminUser.getId();
        adminUser = new AdminUser();
        adminUser.setId(id);
        adminUser.setPassword(newPassword);
        int i = adminUserMapper.updateByPrimaryKeySelective(adminUser);
        if (i != 1)
            throw new SQLException("重置密码失败");
    }
}
