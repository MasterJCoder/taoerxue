package com.taoerxue.manager.controller;

import com.taoerxue.common.bean.Result;
import com.taoerxue.common.cache.redis.JedisClient;
import com.taoerxue.common.constant.CommonConstant;
import com.taoerxue.common.constant.RegexConstant;
import com.taoerxue.manager.service.SmsService;
import com.taoerxue.manager.service.UserService;
import com.taoerxue.pojo.AdminUser;
import com.taoerxue.util.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by lizhihui on 2017-03-18 09:34.
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private JedisClient jedisClient;
    @Resource
    private SmsService smsService;

    @RequestMapping("/login")
    public Result login(String phone, String password) {
        if (StringUtils.isEmpty(phone))
            return Result.build(500, CommonConstant.PHONE_NULL_ERROR);
        if (!RegexUtils.isPhone(phone))
            return Result.build(500, CommonConstant.PHONE_FORMAT_ERROR);
        if (password == null)
            return Result.build(500, CommonConstant.PASSWORD_NULL_ERROR);


        Result checkResult = userService.check(phone, password);
        if (checkResult.getStatus() != 200)
            return checkResult;

        AdminUser adminUser = (AdminUser) checkResult.getData();
        String token = userService.generateToken(adminUser);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        return Result.ok(map);
    }
    @RequestMapping("/sendVerificationCode")
    public Result sendVerificationCode(HttpServletRequest request, HttpServletResponse response, String phone, String token, String verificationCode) {
        //1.校验手机号码
        if (org.springframework.util.StringUtils.isEmpty(phone))
            return Result.build(500, CommonConstant.PHONE_NULL_ERROR);
        if (!RegexUtils.isPhone(phone))
            return Result.build(500, CommonConstant.PHONE_FORMAT_ERROR);

        //2.查看用户是否已经注册
        /*if (educationUserService.doesRegister(phone))
            return Result.build(1001, "您已经注册,请直接登录");*/

        //3.校验剩余两个参数
        if (org.springframework.util.StringUtils.isEmpty(verificationCode))
            return Result.build(500, "请输入图形验证码");
        if (org.springframework.util.StringUtils.isEmpty(token))
            return Result.build(400, "缺少token参数");

        //4.根据前端传过来的 token,校验验证码是否输入正确
        String code = jedisClient.get("verificationCode:" + token);
        if (code == null)
            return Result.build(500, "请刷新图形验证码");
        if (!code.equalsIgnoreCase(verificationCode))
            return Result.build(500, "图形验证码错误");

        //5.所有校验通过,获取真实 ip 地址,防止同一个 ip 恶意盗刷短信
        String ip = IPUtils.getClientIp(request);
        String key = "count:" + ip;
        long increment = jedisClient.increment(key, 1);
        //5.1规定所有的 ip 每天只能发送6次短信验证码
        if (increment > CommonConstant.SEND_SMS_TIMES) {
            return Result.build(500, "每日获取短信次数已达上线");
        }
        //第一次调用才设置过期时间,避免多次连接 redis
        if (increment == 1)
            jedisClient.expire(key, TimeUtils.theRestMillisOfToday(), TimeUnit.MILLISECONDS);
        //6.真正的发送验证码
        Result result = smsService.sendVerificationCode(phone);
        //如果发送发送成功,删除 redis 的验证码,同时并删除 cookie
        if (result.getStatus() == 200) {
            CookieUtils.deleteCookie(request, response, "token");
            jedisClient.del("verificationCode:" + token);
        }
        return result;
    }
    @RequestMapping("/checkVerificationCode")
    public Result checkVerificationCode(String phone,String verificationCode,HttpServletRequest request,HttpServletResponse response){
        if (org.springframework.util.StringUtils.isEmpty(phone))
            return Result.build(500, CommonConstant.PHONE_NULL_ERROR);
        if (!RegexUtils.isPhone(phone))
            return Result.build(500, CommonConstant.PHONE_FORMAT_ERROR);
        if (org.springframework.util.StringUtils.isEmpty(verificationCode))
            return Result.build(500, CommonConstant.VERIFICATION_CODE_NULL_ERROR);
        if (!smsService.checkVerificationCode(phone, verificationCode))
            return Result.build(500, CommonConstant.VERIFICATION_CODE_ERROR);
        String resetPasswordToken = UUID.randomUUID().toString().replace("-", "");
        jedisClient.setAndExpire("resetPasswordToken:" + resetPasswordToken, phone, 5, TimeUnit.MINUTES);
        CookieUtils.setCookie(request, response, "resetPasswordToken", resetPasswordToken);
        smsService.deleteVerificationCode(phone);
        return Result.ok();
    }
    @RequestMapping("/resetPassword")
    public Result resetPassword(String password, String token) {
        if (org.springframework.util.StringUtils.isEmpty(token))
            return Result.build(500, "缺少重置密码凭证:token");
        if (org.springframework.util.StringUtils.isEmpty(password))
            return Result.build(500, CommonConstant.PASSWORD_NULL_ERROR);
        String phone = jedisClient.get("resetPasswordToken:" + token);
        if (phone==null||!RegexUtils.isPhone(phone))
            return Result.build(500, "错误的token凭证");
        if (!RegexUtils.checkRegex(password, RegexConstant.PASSWORD))
            return Result.build(500, CommonConstant.PASSWORD_FORMAT_ERROR);
        userService.resetPassword(phone, password);
        jedisClient.del("resetPasswordToken:" + token);
        return Result.build(200, "重置成功");
    }
}
