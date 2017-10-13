package com.taoerxue.web.controller;

import com.taoerxue.common.bean.Result;
import com.taoerxue.common.cache.redis.JedisClient;
import com.taoerxue.common.constant.CacheConstant;
import com.taoerxue.common.constant.CommonConstant;
import com.taoerxue.common.constant.EducationInstitutionConstant;
import com.taoerxue.common.constant.RegexConstant;
import com.taoerxue.common.im.ChatPlatform;
import com.taoerxue.common.map.MapPlatform;
import com.taoerxue.common.map.gaode.AddressDetailInfo;
import com.taoerxue.common.enums.EducationUserStatusEnum;
import com.taoerxue.common.os.StoragePlatform;
import com.taoerxue.util.*;
import com.taoerxue.web.dto.institution.EducationInstitutionRegister;
import com.taoerxue.web.dto.institution.EducationUserRegister;
import com.taoerxue.pojo.EducationInstitution;
import com.taoerxue.pojo.EducationType;
import com.taoerxue.pojo.EducationUser;
import com.taoerxue.web.service.EducationInstitutionService;
import com.taoerxue.web.service.EducationUserService;
import com.taoerxue.web.service.SmsService;
import org.dozer.DozerBeanMapper;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 用户登录注册 Controller
 * Created by lizhihui on 2017-03-14 12:57.
 */
@RestController
@RequestMapping("/user")
public class EducationUserController extends BaseController {
    @Resource
    private JedisClient jedisClient;
    @Resource
    private SmsService smsService;
    @Resource
    private EducationUserService educationUserService;
    @Resource
    private DozerBeanMapper dozerBeanMapper;
    @Resource
    private MapPlatform mapPlatform;
    @Resource
    private EducationInstitutionService educationInstitutionService;
    @Resource
    private StoragePlatform storagePlatform;

    @Resource
    private ChatPlatform chatPlatform;

    /**
     * 机构端用户注册
     *
     * @param userRegister 封装注册的 dto
     * @param result       校验结果
     * @return 返回接口调用信息
     */
    @RequestMapping("/register")
    public Result register(@Valid EducationUserRegister userRegister, BindingResult result, HttpServletResponse response, HttpServletRequest request) {
        //1.判断是否有错误信息
        if (result.hasErrors()) {
            return Result.build(500, getErrors(result));
        }

        //2.判断是否已经注册过
        if (educationUserService.doesRegister(userRegister.getPhone()))
            return Result.build(1001, "该手机号已经注册,请直接登录");

        //2.1检查验证码是否正确
        if (StringUtils.isEmpty(userRegister.getVerificationCode()))
            return Result.build(500, CommonConstant.VERIFICATION_CODE_NULL_ERROR);
        if (!smsService.checkVerificationCode(userRegister.getPhone(), userRegister.getVerificationCode()))
            return Result.build(500, CommonConstant.VERIFICATION_CODE_ERROR);


        //3.将 dto 转换成 存储对象
        EducationUser educationUser = dozerBeanMapper.map(userRegister, EducationUser.class);

        //4.注册,并返回结果
        Result registerResult = educationUserService.register(educationUser);

        if (registerResult.getStatus() == 200) {
            //4.1注册成功后删除缓存里的验证码
            smsService.deleteVerificationCode(userRegister.getPhone());

            //4.2返回登录凭证,保存到 cookie 里
            //4.2.1生成 token,根据手机号保存,
            String token = UUID.randomUUID().toString().replace("-", "");
            jedisClient.set("education:user:phone:" + userRegister.getPhone(), token);
            jedisClient.setAndExpire(token, JsonUtils.objectToJson(registerResult.getData()), CacheConstant.LOGIN_EXPIRE_HOURS, TimeUnit.HOURS);
            //4.2.2删除 registerResult 中用户信息
            registerResult.setData(null);

            //4.3将 token 写入 cookie ,可以让前端调用
            CookieUtils.setCookie(request, response, "userToken", token);
        }
        return registerResult;
    }

    /**
     * 发送验证码
     *
     * @param phone            手机号码
     * @param token            发送验证码凭证
     * @param verificationCode 图形验证码
     * @return 返回接口调用信息
     */
    @RequestMapping("/sendVerificationCode")
    public Result sendVerificationCode(HttpServletRequest request, HttpServletResponse response, String phone, String token, String verificationCode) {
        //1.校验手机号码
        if (StringUtils.isEmpty(phone))
            return Result.build(500, CommonConstant.PHONE_NULL_ERROR);
        if (!RegexUtils.isPhone(phone))
            return Result.build(500, CommonConstant.PHONE_FORMAT_ERROR);

        //2.查看用户是否已经注册
        /*if (educationUserService.doesRegister(phone))
            return Result.build(1001, "您已经注册,请直接登录");*/

        //3.校验剩余两个参数
        if (StringUtils.isEmpty(verificationCode))
            return Result.build(500, "请输入图形验证码");
        if (StringUtils.isEmpty(token))
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

    /**
     * 机构信息注册
     *
     * @param educationInstitutionRegister 封装了机构信息的 dto
     * @param result                       校验结果
     * @return 返回接口调用信息
     */
    @RequestMapping("/registerEducationInstitution")
    public Result registerEducationInstitution(@Valid EducationInstitutionRegister educationInstitutionRegister, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return Result.build(500, getErrors(result));
        }
        //判断该课程类型是否存在
        EducationType educationType = educationInstitutionService.getCourseType(educationInstitutionRegister.getTypeId());
        if (educationType == null || educationType.getParentId() != 0)
            return Result.build(500, CommonConstant.EDUCATION_TYPE_NOT_EXISTS_ERROR);

        //解析地址,
        AddressDetailInfo addressDetailInfo = mapPlatform.addressToLngLat(educationInstitutionRegister.getAddress());
        if (addressDetailInfo == null)
            return Result.build(500, "找不到该地址呢,请输入详细点的试试?");

        //判断机构图片不存在
        Boolean exists = storagePlatform.exists(educationInstitutionRegister.getPhoto());
        if (!exists)
            return Result.build(500, EducationInstitutionConstant.PHOTO_NULL_ERROR);
        //完善注册信息,copy
        EducationInstitution educationInstitution = dozerBeanMapper.map(educationInstitutionRegister, EducationInstitution.class);
        dozerBeanMapper.map(addressDetailInfo, educationInstitution);
        educationInstitution.setTypeName(educationType.getName());

        //提交
        educationInstitutionService.register(educationInstitution, ((EducationUser) request.getSession().getAttribute("user")).getId());
        return Result.build(200, "提交成功");
    }

    /**
     * 登录接口
     *
     * @param phone    手机号码
     * @param password 密码
     * @return 返回登录接口调用情况
     */
    @RequestMapping("/login")
    public Result login(String phone, String password, HttpServletRequest request, HttpServletResponse response) {
        //1.校验手机格式
        if (StringUtils.isEmpty(phone))
            return Result.build(500, CommonConstant.PLEASE_INPUT_PHONE);
        if (!RegexUtils.isPhone(phone))
            return Result.build(500, CommonConstant.PHONE_FORMAT_ERROR);

        //1.1校验是否输入了密码
        if (StringUtils.isEmpty(password))
            return Result.build(500, CommonConstant.PLEASE_INPUT_PASSWORD);

        //2.获取该手机号对应的账户信息
        EducationUser educationUser = educationUserService.getByPhone(phone);

        //3.判断该用户是否存在
        if (educationUser == null)
            return Result.build(500, CommonConstant.USER_NO_REGISTER_ERROR);
        //4.判断该用户的帐号密码是否正确
        if (!educationUserService.checkLogin(educationUser, password.trim()))
            return Result.build(500, CommonConstant.PASSWORD_ERROR);

        //判断用户状态
        Integer status = educationUserService.getUserStatus(educationUser);

        //5.登录成功后,返回登录信息
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("phone", phone);

        //看下之前是否有登录过,有的话把之前的登录信息删除
        String oldToken = jedisClient.get("education:user:phone:" + educationUser.getPhone());
        if (oldToken != null) {
            jedisClient.del(oldToken);
        }

        //6.向 redis 中添加登录成功凭证
        String token = UUID.randomUUID().toString().replace("-", "");

        jedisClient.set("education:user:phone:" + educationUser.getPhone(), token);
        jedisClient.setAndExpire(token, JsonUtils.objectToJson(educationUser), CacheConstant.LOGIN_EXPIRE_HOURS, TimeUnit.HOURS);
        CookieUtils.setCookie(request, response, "userToken", token);

        if (status.equals(EducationUserStatusEnum.SUCCESS.getStatus()))
            if (!chatPlatform.updateToken("web_" + phone, token))
                return Result.build(500, "`登录失败,请稍后重试");

        return Result.ok(map);
    }


    /**
     * 使用短信验证码修改密码
     *
     * @param phone            手机号码
     * @param password         新密码
     * @param verificationCode 短信验证码
     * @return 修改密码的结果
     */
    @RequestMapping("/resetPassword")
    public Result resetPassword(String phone, String password, String verificationCode) {
        //1.校验手机格式
        if (StringUtils.isEmpty(phone))
            return Result.build(500, CommonConstant.PLEASE_INPUT_PHONE);
        if (!RegexUtils.isPhone(phone))
            return Result.build(500, CommonConstant.PHONE_FORMAT_ERROR);

        //2.校验密码格式
        if (StringUtils.isEmpty(password))
            return Result.build(500, CommonConstant.PLEASE_INPUT_PASSWORD);
        if (!RegexUtils.checkRegex(password, RegexConstant.PASSWORD))
            return Result.build(500, CommonConstant.PASSWORD_FORMAT_ERROR);

        //3.判断是否已经注册
        if (!educationUserService.doesRegister(phone))
            return Result.build(1001, CommonConstant.USER_NO_REGISTER_ERROR);

        //3.校验短信验证码是否正确
        if (StringUtils.isEmpty(verificationCode))
            return Result.build(500, CommonConstant.VERIFICATION_CODE_NULL_ERROR);
        if (!smsService.checkVerificationCode(phone, verificationCode))
            return Result.build(500, CommonConstant.VERIFICATION_CODE_ERROR);

        Result result = educationUserService.resetPassword(phone, password);
        if (result.getStatus() == 200) {
            smsService.deleteVerificationCode(phone);
        }
        return result;

    }


}
