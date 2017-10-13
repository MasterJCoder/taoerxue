package com.taoerxue.app.service;

import com.taoerxue.common.cache.redis.JedisClient;
import com.taoerxue.common.constant.CommonConstant;
import com.taoerxue.common.exception.SQLException;
import com.taoerxue.common.im.ChatPlatform;
import com.taoerxue.common.enums.AppUserStatusEnum;
import com.taoerxue.common.os.StoragePlatform;
import com.taoerxue.mapper.AppUserMapper;
import com.taoerxue.mapper.MyAppUserMapper;
import com.taoerxue.pojo.AppUser;
import com.taoerxue.pojo.AppUserExample;
import com.taoerxue.util.JsonUtils;
import com.taoerxue.util.RandomUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * Created by lizhihui on 2017-03-23 09:47.
 */
@Service
public class UserService {

    @Resource
    private AppUserMapper appUserMapper;
    @Resource
    private MyAppUserMapper myAppUserMapper;
    @Resource
    private ChatPlatform chatPlatform;
    @Resource
    private JedisClient jedisClient;
    @Resource
    private StoragePlatform storagePlatform;


    @Transactional
    public String register(AppUser appUser) {


        //1.为用户的密码生成salt(盐),并设置
        String salt = RandomUtils.generateString(8);
        appUser.setSalt(salt);

        //2.获取加密后的密码,并设置
        appUser.setPassword(getPasswordAfterEncrypt(appUser.getPassword(), salt));

        //设置默认用户名,默认  用户+手机尾号4位
        appUser.setNickname("用户" + appUser.getPhone().substring(7, 11));
        //4.插入数据库
        try {
            appUserMapper.insertSelective(appUser);

        } catch (Exception e) {
            throw new SQLException(CommonConstant.USER_ALREADY_REGISTER_ERROR);
        }
        String token = generateToken(appUser);
        if (!chatPlatform.register("app_" + appUser.getPhone(), appUser.getNickname(), token, storagePlatform.getImageURL("portrait.png")))
            throw new SQLException("注册聊天服务器失败,请重新注册");
        return token;
    }


    private String getPasswordAfterEncrypt(String originalPassword, String salt) {
        return DigestUtils.md5DigestAsHex((DigestUtils.md5DigestAsHex(originalPassword.getBytes()) + salt).getBytes());

    }

    public AppUser getByPhone(String phone) {
        return myAppUserMapper.getByPhone(phone);
    }

    public boolean checkLogin(AppUser appUser, String password) {
        return appUser.getPassword().equals(getPasswordAfterEncrypt(password, appUser.getSalt()));
    }

    public Integer getUserStatus(AppUser appUser) {
        // TODO 获取 app用户状态
        if (appUser.getStudentTypeId() == 0)
            return AppUserStatusEnum.NO_SUBMIT_CHILDREN_INFO.getStatus();
        return AppUserStatusEnum.NORMAL.getStatus();
    }

    @Transactional
    public void addChildrenInfo(AppUser appUser) {
        int i = appUserMapper.updateByPrimaryKeySelective(appUser);
        if (i != 1)
            throw new SQLException("添加孩子信息失败");
    }

    public boolean whetherChildrenInfoComplete(AppUser appUser) {
        return appUser.getExpectation() != 0 && appUser.getStudentTypeId() != 0;
    }

    @Transactional
    public void updateLogin(String phone) {
        AppUser appUser = new AppUser();
        appUser.setLoginTime(new Date());
        AppUserExample appUserExample = new AppUserExample();
        appUserExample.createCriteria().andPhoneEqualTo(phone);
        int i = appUserMapper.updateByExampleSelective(appUser, appUserExample);
        if (i != 1)
            throw new SQLException("登录失败");
    }

    @Transactional
    public void updateInfo(AppUser appUser) {
        int i = appUserMapper.updateByPrimaryKeySelective(appUser);
        if (i != 1)
            throw new SQLException("更改信息失败");

    }

    public String generateToken(AppUser appUser) {
        String oldToken = jedisClient.get("app:user:phone:" + appUser.getPhone());
        if (oldToken != null) {
            jedisClient.del(oldToken);
        }

        //6.向 redis 中添加登录成功凭证
        String token = UUID.randomUUID().toString().replace("-", "");
        jedisClient.set("app:user:phone:" + appUser.getPhone(), token);
        jedisClient.set(token, JsonUtils.objectToJson(appUser));
        return token;
    }

    @Transactional
    public void resetPassword(String phone, String password) {
        AppUser appUser = myAppUserMapper.getByPhone(phone);
        String salt = appUser.getSalt();
        String newPassword = getPasswordAfterEncrypt(password, salt);
        Integer id = appUser.getId();
        appUser = new AppUser();
        appUser.setId(id);
        appUser.setPassword(newPassword);
        int i = appUserMapper.updateByPrimaryKeySelective(appUser);
        if (i != 1)
            throw new SQLException("重置密码失败");
    }
}
