package com.taoerxue.web.controller;

import com.taoerxue.common.bean.PageResult;
import com.taoerxue.common.bean.Result;
import com.taoerxue.common.constant.CommonConstant;
import com.taoerxue.common.sms.SMSPlatform;
import com.taoerxue.web.dto.institution.EducationUserAdd;
import com.taoerxue.pojo.EducationType;
import com.taoerxue.pojo.EducationUser;
import com.taoerxue.util.StringUtils;
import com.taoerxue.vo.EducationTypeWithoutStatus;
import com.taoerxue.web.service.EducationInstitutionService;
import com.taoerxue.web.service.EducationUserService;
import com.taoerxue.web.service.SmsService;
import org.dozer.DozerBeanMapper;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 机构相关的 controller
 * Created by lizhihui on 2017-03-17 13:07.
 */

@RestController
@RequestMapping("/educationInstitution")
public class EducationInstitutionController extends BaseController {

    @Resource
    private EducationInstitutionService educationInstitutionService;
    @Resource
    private EducationUserService educationUserService;

    @Resource
    private DozerBeanMapper dozerBeanMapper;

    @Resource
    private SmsService smsService;

    @Resource
    private SMSPlatform smsPlatform;
    //机构课程列表类型
    @RequestMapping("/courseType")
    public Result getCourseTypeLevelTwo(Integer id) {
        if (id == null)
            id = 0;
        //这个是从数据库里查出来的,字段比较全,前段不需要这么多字段
        List<EducationType> educationTypeList = educationInstitutionService.listCourseType(id);
        //所以我进行了复制
        List<EducationTypeWithoutStatus> educationTypeWithoutStatusList = new ArrayList<>();
        for (EducationType educationType : educationTypeList) {
            EducationTypeWithoutStatus educationTypeWithoutStatus = dozerBeanMapper.map(educationType, EducationTypeWithoutStatus.class);
            educationTypeWithoutStatusList.add(educationTypeWithoutStatus);
        }
        //然后返回给前端
        return Result.ok(educationTypeWithoutStatusList);
    }

    /**
     * 获取管理短信密码
     *
     * @return 发送结果
     */
    @RequestMapping("/sendVerificationCode")
    public Result sendVerificationCode(HttpServletRequest request) {
        EducationUser user = (EducationUser) request.getSession().getAttribute("user");
        if (!user.getRole().equals(1))
            return Result.build(500, "不是管理员无法获取该短信");
        return smsService.sendVerificationCode(user.getPhone());
    }

    @RequestMapping("/checkVerificationCode")
    public Result checkVerificationCode(HttpServletRequest request, String verificationCode) {
        EducationUser user = (EducationUser) request.getSession().getAttribute("user");
        if (!user.getRole().equals(1))
            return Result.build(500, "不是管理员无法进行校验");
        if (StringUtils.isEmpty(verificationCode))
            return Result.build(500, CommonConstant.VERIFICATION_CODE_NULL_ERROR);
        boolean result = smsService.checkVerificationCode(user.getPhone(), verificationCode);
        if (result) {
            smsService.deleteVerificationCode(user.getPhone());
            return Result.ok();
        }
        return Result.build(500, "验证码错误");
    }


    /**
     * 机构用户列表
     *
     * @return 机构用户列表
     */

    @RequestMapping("/userList")
    public PageResult userList(HttpServletRequest request) {

        EducationUser user = (EducationUser) request.getSession().getAttribute("user");

        if (!user.getRole().equals(1))
            return new PageResult();
        return educationUserService.list(user.geteId());
    }

    /**
     * 禁用机构用户
     *
     * @param id 用户 id
     * @return 禁用结果
     */
    @RequestMapping("/disableUser")
    public Result disableUser(HttpServletRequest request, Integer id) {
        EducationUser user = (EducationUser) request.getSession().getAttribute("user");
        if (id == null)
            return Result.build(500, "请选择你要禁用的用户");
        if (!user.getRole().equals(1) || !educationUserService.doesBelongTo(id, user.getId()))
            return Result.build(500, "你没有权利禁用该用户");
        educationUserService.changeStatus(id, 0);
        return Result.build(500, "禁用成功");
    }

    /**
     * 启用机构用户
     *
     * @param id 用户 id
     * @return 启用结果
     */
    @RequestMapping("/enableUser")
    public Result enableUser(HttpServletRequest request, Integer id) {
        if (id == null)
            return Result.build(500, "请选择你要启用的用户");
        EducationUser user = (EducationUser) request.getSession().getAttribute("user");
        if (!user.getRole().equals(1) || !educationUserService.doesBelongTo(id, user.getId()))
            return Result.build(500, "你没有权利启用该用户");
        educationUserService.changeStatus(id, 1);
        return Result.build(500, "启用成功");
    }

    /**
     * 删除机构用户
     *
     * @param id 用户 id
     * @return 删除结果
     */
    @RequestMapping("/deleteUser")
    public Result deleteUser(HttpServletRequest request, Integer id) {
        EducationUser user = (EducationUser) request.getSession().getAttribute("user");
        if (id == null)
            return Result.build(500, "请选择你要删除的用户");
        if (!user.getRole().equals(1) || !educationUserService.doesBelongTo(id, user.getId()))
            return Result.build(500, "你没有权利删除该用户");
        educationUserService.delete(id);
        // TODO 发送短信通知下
        return Result.build(500, "删除成功");
    }


    /**
     * 添加机构用户
     *
     * @param userAdd 用户信息
     * @return 添加结果
     */
    @RequestMapping("/addUser")
    public Result addUser(@Valid EducationUserAdd userAdd, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors())
            return Result.build(500, getErrors(bindingResult));
        EducationUser user = (EducationUser) request.getSession().getAttribute("user");

        if (!user.getRole().equals(1))
            return Result.build(500, "不是管理员无法添加用户");

        boolean b = smsService.checkVerificationCode(user.getPhone(), userAdd.getVerificationCode());
        if (!b)
            return Result.build(500, CommonConstant.VERIFICATION_CODE_ERROR);
        boolean can = educationUserService.canBeAdded(userAdd.getPhone());
        if (!can) {
            return Result.build(500, "该用户已经属于其他教育机构,无法添加");
        }


        EducationUser educationUser = dozerBeanMapper.map(userAdd, EducationUser.class);
        //设置用户所属的机构
        educationUser.seteId(user.geteId());

        //设置用户的角色,因为是被添加的.所以固定角色是2-普通员工
        educationUser.setRole(2);

        //设置用户的帐号状态,正常
        educationUser.setStatus(true);

        //添加
        educationUserService.add(educationUser);
        smsService.deleteVerificationCode(userAdd.getPhone());
        return Result.ok();
    }


}
