package com.taoerxue.app.controller;

import com.taoerxue.app.service.SmsService;
import com.taoerxue.app.service.StudentTypeService;
import com.taoerxue.app.service.UserService;
import com.taoerxue.common.bean.Result;
import com.taoerxue.common.cache.redis.JedisClient;
import com.taoerxue.common.constant.CommonConstant;
import com.taoerxue.common.constant.RegexConstant;
import com.taoerxue.common.im.ChatPlatform;
import com.taoerxue.common.os.StoragePlatform;
import com.taoerxue.app.dto.APPUserRegister;
import com.taoerxue.pojo.AppUser;
import com.taoerxue.util.*;
import com.taoerxue.app.vo.user.UserInfo;
import org.dozer.DozerBeanMapper;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by lizhihui on 2017-03-23 09:25.
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;
    @Resource
    private DozerBeanMapper dozerBeanMapper;
    @Resource
    private JedisClient jedisClient;
    @Resource
    private SmsService smsService;
    @Resource
    private StudentTypeService studentTypeService;
    @Resource
    private StoragePlatform storagePlatform;
    @Resource
    private ChatPlatform chatPlatform;


    @RequestMapping("/register")
    public Result register(@Valid APPUserRegister appUserRegister, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {

        if (bindingResult.hasErrors())
            return Result.build(500, getErrors(bindingResult));
        String phone = appUserRegister.getPhone();
        boolean right = smsService.checkVerificationCode(phone, appUserRegister.getVerificationCode());
        if (!right)
            return Result.build(500, CommonConstant.VERIFICATION_CODE_ERROR);
        AppUser appUser = dozerBeanMapper.map(appUserRegister, AppUser.class);
        String token = userService.register(appUser);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        smsService.deleteVerificationCode(phone);
        return Result.ok(map);
    }

    @RequestMapping("/addChildrenInfo")
    public Result addChildrenInfo(HttpServletRequest request, Integer studentTypeId, @RequestParam("expectation[]") Integer expectation[]) {
        AppUser appUser = (AppUser) request.getSession().getAttribute("user");

        //判断学生类型是否正确
        if (studentTypeId == null)
            return Result.build(500, "请选择孩子的年龄段");
        boolean exists = studentTypeService.isExists(studentTypeId);
        if (!exists)
            return Result.build(500, "不存在的年龄段");

        //判断期望是否争取
        if (expectation == null)
            return Result.build(500, "请选择您希望孩子哪方面得到发展");
        int sum = 0;
        for (Integer i : expectation) {
            sum += i;
        }
        if (sum <= 0 || sum > 7)
            return Result.build(500, "错误的希望");

        //更新
        AppUser appUserUpdate = new AppUser();
        appUser.setId(appUser.getId());
        appUser.setExpectation(sum);
        appUserUpdate.setExpectation(sum);
        appUser.setStudentTypeId(studentTypeId);
        appUserUpdate.setStudentTypeId(studentTypeId);
        userService.addChildrenInfo(appUser);

        //把兴趣爱好设置到缓存中
        jedisClient.set(jedisClient.get("app:user:phone:" + appUser.getPhone()), JsonUtils.objectToJson(appUser));
        return Result.ok();
    }

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

        //3.判断该用户是否存在
        AppUser appUser = userService.getByPhone(phone);
        if (appUser == null)
            return Result.build(500, CommonConstant.USER_NO_REGISTER_ERROR);

        //4.判断该用户的帐号密码是否正确
        if (!userService.checkLogin(appUser, password.trim()))
            return Result.build(500, CommonConstant.PASSWORD_ERROR);

        //登录成功,更新登录时间,并检查孩子信息是否完善返回 token
        userService.updateLogin(phone);
        boolean whetherChildrenInfoComplete = userService.whetherChildrenInfoComplete(appUser);
        Map<String, Object> map = new HashMap<>();
//        map.put("token", generateToken(appUser));
        //孩子信息是否完善
        map.put("whetherChildrenInfoComplete", whetherChildrenInfoComplete);

        String token = userService.generateToken(appUser);
        if (!chatPlatform.updateToken("app_" + phone, token))
            return Result.build(500, "登录失败,请重新登录");
//        CookieUtils.setCookie(request, response, "token", token);
        map.put("token", token);
        return Result.ok(map);
    }


    @RequestMapping("/resetPasswordCheck")
    public Result resetPasswordCheck(String phone, String verificationCode, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(phone))
            return Result.build(500, CommonConstant.PHONE_NULL_ERROR);
        if (!RegexUtils.isPhone(phone))
            return Result.build(500, CommonConstant.PHONE_FORMAT_ERROR);
        if (StringUtils.isEmpty(verificationCode))
            return Result.build(500, CommonConstant.VERIFICATION_CODE_NULL_ERROR);
        boolean right = smsService.checkVerificationCode(phone, verificationCode);
        if (!right)
            return Result.build(500, CommonConstant.VERIFICATION_CODE_ERROR);
        String resetPasswordToken = UUID.randomUUID().toString().replace("-", "");
        jedisClient.setAndExpire("resetPasswordToken:" + resetPasswordToken, phone, 5, TimeUnit.MINUTES);
        Map<String, Object> map = new HashMap<>();
        map.put("resetPasswordToken", resetPasswordToken);
        //CookieUtils.setCookie(request, response, "resetPasswordToken", resetPasswordToken);
        smsService.deleteVerificationCode(phone);
        return Result.ok(map);
    }

    @RequestMapping("/resetPassword")
    public Result resetPassword(String password, String token) {
        if (StringUtils.isEmpty(token))
            return Result.build(500, "缺少重置密码凭证:token");
        if (StringUtils.isEmpty(password))
            return Result.build(500, CommonConstant.PASSWORD_NULL_ERROR);
        String phone = jedisClient.get("resetPasswordToken:" + token);
        if (!RegexUtils.isPhone(phone))
            return Result.build(500, "错误的token凭证");
        if (!RegexUtils.checkRegex(password, RegexConstant.PASSWORD))
            return Result.build(500, CommonConstant.PASSWORD_FORMAT_ERROR);
        userService.resetPassword(phone, password);
        jedisClient.del("resetPasswordToken:" + token);
        return Result.build(200, "重置成功");
    }


    @RequestMapping("/sendVerificationCode")
    public Result sendVerificationCode(HttpServletRequest request, HttpServletResponse response, String phone/*, String token, String verificationCode*/) {
        //1.校验手机号码
        if (StringUtils.isEmpty(phone))
            return Result.build(500, CommonConstant.PHONE_NULL_ERROR);
        if (!RegexUtils.isPhone(phone))
            return Result.build(500, CommonConstant.PHONE_FORMAT_ERROR);

        //2.查看用户是否已经注册
        /*if (educationUserService.doesRegister(phone))
            return Result.build(1001, "您已经注册,请直接登录");*/

        /*//3.校验剩余两个参数
        if (StringUtils.isEmpty(verificationCode))
            return Result.build(500, "请输入图形验证码");
        if (StringUtils.isEmpty(token))
            return Result.build(400, "缺少token参数");

        //4.根据前端传过来的 token,校验验证码是否输入正确
        String code = jedisClient.get("verificationCode:" + token);
        if (code == null)
            return Result.build(500, "请刷新图形验证码");
        if (!code.equalsIgnoreCase(verificationCode))
            return Result.build(500, "图形验证码错误");*/

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
        /*if (result.getStatus() == 200) {
            CookieUtils.deleteCookie(request, response, "token");
            jedisClient.del("verificationCode:" + token);
        }*/
        return result;
    }


    @RequestMapping("/info")
    public Result info(HttpServletRequest request) {
        AppUser user = (AppUser) request.getSession().getAttribute("user");
        UserInfo userInfo = dozerBeanMapper.map(user, UserInfo.class);
        userInfo.setPhoto(storagePlatform.getImageURL(userInfo.getPhoto()));
        String typeName = studentTypeService.getName(user.getStudentTypeId());
        userInfo.setStudentTypeName(typeName);
        List<Integer> integerList = MathUtils.convert(user.getExpectation());
        userInfo.setExpectations(integerList);
        return Result.ok(userInfo);
    }

    @RequestMapping("/changeNickname")
    public Result changeNickname(HttpServletRequest request, String nickname) {
        if (StringUtils.isEmpty(nickname))
            return Result.ok();
        if (nickname.length() > 10)
            return Result.build(500, "昵称长度不能超过10个字符");

        AppUser jsonUser = (AppUser) request.getSession().getAttribute("user");
        jsonUser.setNickname(nickname);
        Integer id = jsonUser.getId();
        AppUser appUser = new AppUser();
        appUser.setId(id);
        appUser.setNickname(nickname);
        updateUser(appUser, jsonUser);
        return Result.ok();
    }

    @RequestMapping("/changePhoto")
    public Result changePhoto(HttpServletRequest request, String photo) {
        if (StringUtils.isEmpty(photo))
            return Result.ok();
        if (!storagePlatform.exists(photo))
            return Result.build(500, "上传的头像不存在");
        AppUser jsonUser = (AppUser) request.getSession().getAttribute("user");
        jsonUser.setPhoto(photo);

        Integer id = jsonUser.getId();
        AppUser appUser = new AppUser();

        appUser.setId(id);
        appUser.setPhoto(photo);
        updateUser(appUser, jsonUser);
        Map<String, String> map = new HashMap<>();
        map.put("photo", storagePlatform.getImageURL(photo));
        return Result.ok(map);

    }

    @RequestMapping("/changeExpectation")
    public Result changeExpectation(HttpServletRequest request, @RequestParam("expectation[]") Integer expectation[]) {
        if (expectation == null)
            return Result.build(500, "请选择您希望孩子哪方面得到发展");
        int sum = 0;
        for (Integer i : expectation) {
            sum += i;
        }
        if (sum <= 0 || sum > 7)
            return Result.build(500, "错误的希望");
        AppUser jsonUser = (AppUser) request.getSession().getAttribute("user");
        if (jsonUser.getExpectation().equals(sum))
            return Result.ok();

        jsonUser.setExpectation(sum);

        Integer id = jsonUser.getId();
        AppUser appUser = new AppUser();

        appUser.setId(id);
        appUser.setExpectation(sum);
        updateUser(appUser, jsonUser);
        return Result.ok();
    }

    @RequestMapping("/changeStudentType")
    public Result changeStudentType(HttpServletRequest request, Integer studentTypeId) {
        if (studentTypeId == null)
            return Result.build(500, "请选择孩子的年龄段");
        boolean exists = studentTypeService.isExists(studentTypeId);
        if (!exists)
            return Result.build(500, "不存在的年龄段");
        AppUser jsonUser = (AppUser) request.getSession().getAttribute("user");
        if (jsonUser.getStudentTypeId().equals(studentTypeId))
            return Result.ok();
        jsonUser.setStudentTypeId(studentTypeId);

        Integer id = jsonUser.getId();
        AppUser appUser = new AppUser();

        appUser.setId(id);
        appUser.setStudentTypeId(studentTypeId);

        updateUser(appUser, jsonUser);
        return Result.ok();
    }

    private void updateUser(AppUser appUser, AppUser jsonUser) {
        userService.updateInfo(appUser);
        jedisClient.set(jedisClient.get("app:user:phone:" + jsonUser.getPhone()), JsonUtils.objectToJson(jsonUser));
    }
}
