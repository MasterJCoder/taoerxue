package com.taoerxue.web.service;

import com.github.pagehelper.PageInfo;
import com.taoerxue.common.bean.PageResult;
import com.taoerxue.common.bean.Result;
import com.taoerxue.common.constant.CommonConstant;
import com.taoerxue.common.exception.SQLException;
import com.taoerxue.common.im.ChatPlatform;
import com.taoerxue.common.enums.EducationUserStatusEnum;
import com.taoerxue.common.os.StoragePlatform;
import com.taoerxue.web.vo.institution.EducationUserInfo;
import com.taoerxue.mapper.EducationUserMapper;
import com.taoerxue.mapper.MyEducationInstitutionMapper;
import com.taoerxue.mapper.MyEducationUserMapper;
import com.taoerxue.pojo.EducationUser;
import com.taoerxue.pojo.EducationUserExample;
import com.taoerxue.util.RandomUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EducationUserService {
    @Resource
    private EducationUserMapper educationUserMapper;
    @Resource
    private MyEducationUserMapper myEducationUserMapper;
    @Resource
    private MyEducationInstitutionMapper myEducationInstitutionMapper;
    @Resource
    private DozerBeanMapper dozerBeanMapper;
    @Resource
    private ChatPlatform chatPlatform;
    @Resource
    private StoragePlatform storagePlatform;

    public boolean checkLogin(EducationUser educationUser, String password) {
        return educationUser.getPassword().equals(getPasswordAfterEncrypt(password, educationUser.getSalt()));
    }

    public boolean doesRegister(String phone) {
        return myEducationUserMapper.countByPhone(phone) == 1;
    }

    public EducationUser getByPhone(String phone) {
        EducationUserExample educationUserExample = new EducationUserExample();
        educationUserExample.createCriteria().andPhoneEqualTo(phone);
        List<EducationUser> educationUserList = educationUserMapper.selectByExample(educationUserExample);
        if (educationUserList.size() == 0)
            return null;
        return educationUserList.get(0);
    }


    @Transactional
    public Result register(EducationUser educationUser) {
       /* //1.为用户的密码生成salt(盐),并设置
        String salt = RandomUtils.generateString(8);
        educationUser.setSalt(salt);

        //2.获取加密后的密码,并设置
        educationUser.setPassword(getPasswordAfterEncrypt(educationUser.getPassword(), salt));*/


        //重构如下
        educationUser = initEducationUser(educationUser);

        //4.插入数据库
        try {
            educationUserMapper.insertSelective(educationUser);
            //注册成功后,用户的机构 id 为0,表明没有任何机构
            educationUser.seteId(0);
            //注册成功后,用户的状态为0,表明没有提交任何机构信息
            educationUser.setStatus(true);
        } catch (Exception e) {
            throw new SQLException(CommonConstant.USER_ALREADY_REGISTER_ERROR);
        }
        return Result.build(200, "注册成功", educationUser);
    }

    /**
     * 重置用户密码
     *
     * @param phone    手机号码
     * @param password 新的密码(明文)
     * @return
     */
    public Result resetPassword(String phone, String password) {
        //1.先获取盐
        String salt = myEducationUserMapper.getSaltByPhone(phone);

        //2.获取新密码
        String newPassword = getPasswordAfterEncrypt(password, salt);

        //3.更新密码
        myEducationUserMapper.updatePasswordByPhone(phone, newPassword);

        return Result.ok();
    }

    /**
     * 获取加密后的密码
     *
     * @param originalPassword 初始密码
     * @param salt             盐
     * @return 两次 md5 加密后的密码
     */
    private String getPasswordAfterEncrypt(String originalPassword, String salt) {
        return DigestUtils.md5DigestAsHex((DigestUtils.md5DigestAsHex(originalPassword.getBytes()) + salt).getBytes());

    }

    public Integer getUserStatus(EducationUser educationUser) {
        //获取教育机构 id
        Integer educationInstitutionId = educationUser.geteId();
        Boolean userStatus = educationUser.getStatus();

        //如果有教育机构,但是帐号被禁用了,返回禁用状态
        if (educationInstitutionId != 0 && !userStatus)
            return EducationUserStatusEnum.DISABLED.getStatus();

        //如果教育机构 id 为0 说明还没提交任何机构
        if (educationInstitutionId == 0)
            return EducationUserStatusEnum.NO_SUBMIT.getStatus();

        //获取教育机构状态
        Integer status = myEducationInstitutionMapper.getStatus(educationInstitutionId);

        //如果机构状态为1,用户已经提交机构信息,正在审核中
        if (status == 1)
            return EducationUserStatusEnum.SUBMITTED.getStatus();

        //如果机构状态为2,说明审核通过
        if (status == 2)
            return EducationUserStatusEnum.SUCCESS.getStatus();

        //如果机构状态为3,说明审核不通过
        if (status == 3)
            return EducationUserStatusEnum.FAILED.getStatus();

        //以上情况都不符合.说明机构异常,直接做禁用处理
        return EducationUserStatusEnum.DISABLED.getStatus();
    }


    /**
     * 判断用户是否能被机构添加
     *
     * @param phone 手机号
     * @return 是否能被添加
     */
    public boolean canBeAdded(String phone) {
        EducationUser educationUser = myEducationUserMapper.getByPhone(phone);
        return educationUser == null || educationUser.geteId() == 0;
    }

    /**
     * 添加机构用户
     *
     * @param educationUser 机构用户信息
     */
    @Transactional
    public void add(EducationUser educationUser) {
        //不管用户是否存在,都先执行一下操作,初始化
        educationUser = initEducationUser(educationUser);

        //判断该用户是否存在
        String phone = educationUser.getPhone();
        long count = myEducationUserMapper.countByPhone(phone);
        if (count == 1) {
            //存在,则更新覆盖
            EducationUserExample educationUserExample = new EducationUserExample();
            educationUserExample.createCriteria().andEIdEqualTo(0);
            int i = educationUserMapper.updateByExampleSelective(educationUser, educationUserExample);
            if (i != 1)
                throw new SQLException("用户添加失败");
            chatPlatform.enable("web_" + educationUser.getPhone());
        } else {
            //不存在,则插入
            educationUserMapper.insertSelective(educationUser);
            if (chatPlatform.register("web_" + educationUser.getPhone(), educationUser.getAlias(), "123", storagePlatform.getImageURL("pc-portrait.png"))) {
                throw new SQLException("用户添加失败");
            }
        }

    }

    /**
     * 初始化用户 1.生成盐 2.设置密码
     *
     * @param educationUser 用户信息
     * @return 用户信息
     */

    private EducationUser initEducationUser(EducationUser educationUser) {
        String salt = RandomUtils.generateString(8);
        educationUser.setSalt(salt);

        //2.获取加密后的密码,并设置
        educationUser.setPassword(getPasswordAfterEncrypt(educationUser.getPassword(), salt));
        return educationUser;
    }

    public PageResult list(Integer eid) {
        EducationUserExample educationUserExample = new EducationUserExample();
        educationUserExample.createCriteria().andEIdEqualTo(eid);
        List<EducationUser> educationUserList = educationUserMapper.selectByExample(educationUserExample);
        List<EducationUserInfo> educationUserInfoList = new ArrayList<>();
        for (EducationUser educationUser : educationUserList) {
            EducationUserInfo educationUserInfo = dozerBeanMapper.map(educationUser, EducationUserInfo.class);
            educationUserInfoList.add(educationUserInfo);
        }
        PageInfo<EducationUser> pageInfo = new PageInfo<>(educationUserList);
        PageResult<EducationUserInfo> pageResult = new PageResult<>();
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setRows(educationUserInfoList);
        return pageResult;
    }

    @Transactional
    public void changeStatus(Integer id, int status) {
        EducationUser educationUserOld = educationUserMapper.selectByPrimaryKey(id);
        boolean result = status == 1;
        EducationUser educationUser = new EducationUser();
        educationUser.setId(id);
        educationUser.setStatus(result);
        int i = educationUserMapper.updateByPrimaryKeySelective(educationUser);
        if (i != 1)
            throw new SQLException("更新用户状态失败");
        if (result) {
            if (!chatPlatform.enable("web_" + educationUserOld.getPhone()))
                throw new SQLException("解封IM帐号失败");
        } else {
            if (!chatPlatform.disable("web_" + educationUserOld.getPhone()))
                throw new SQLException("启用IM帐号失败");
        }
    }

    public boolean doesBelongTo(Integer id, Integer eid) {
        EducationUser educationUser = educationUserMapper.selectByPrimaryKey(id);
        return educationUser != null && educationUser.geteId().equals(eid);
    }

    @Transactional
    public void delete(Integer id) {
        EducationUser educationUser = new EducationUser();
        educationUser.seteId(0);
        educationUser.setId(id);
        int i = educationUserMapper.updateByPrimaryKeySelective(educationUser);
        if (i != 1)
            throw new SQLException("删除失败");
        educationUser = educationUserMapper.selectByPrimaryKey(id);

        if (chatPlatform.disable("web_" + educationUser.getPhone()))
            throw new SQLException("禁用IM帐号失败");
    }
}
