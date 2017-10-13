package com.taoerxue.manager.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taoerxue.common.bean.PageResult;
import com.taoerxue.common.exception.SQLException;
import com.taoerxue.common.im.ChatPlatform;
import com.taoerxue.common.enums.EducationInstitutionStatusEnum;
import com.taoerxue.common.os.StoragePlatform;
import com.taoerxue.common.sms.SMSPlatform;
import com.taoerxue.manager.vo.institution.EducationInstitutionNewest;
import com.taoerxue.mapper.EducationInstitutionMapper;
import com.taoerxue.mapper.EducationUserMapper;
import com.taoerxue.mapper.MyEducationInstitutionMapper;
import com.taoerxue.mapper.MyEducationUserMapper;
import com.taoerxue.pojo.*;
import com.taoerxue.qo.EducationInstitutionQuery;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 教育机构相关的 service
 * Created by lizhihui on 2017-03-18 09:38.
 */

@Service
public class EducationInstitutionService {


    @Resource
    private MyEducationInstitutionMapper myEducationInstitutionMapper;
    @Resource
    private EducationInstitutionMapper educationInstitutionMapper;
    @Resource
    private ChatPlatform chatPlatform;
    @Resource
    private StoragePlatform storagePlatform;
    @Resource
    private DozerBeanMapper dozerBeanMapper;
    @Resource
    private SMSPlatform smsPlatform;
    @Resource
    private MyEducationUserMapper myEducationUserMapper;
    @Resource
    private EducationUserMapper educationUserMapper;

    /**
     * 更新教育机构的状态,从老状态变为新状态
     *
     * @param eid       教育机构的 id
     * @param newStatus 新的状态
     * @param oldStatus 老的状态
     * @return 更新成功的条数
     */
    @Transactional
    public void changeStatus(Integer eid, Integer newStatus, Integer oldStatus) {
        int i = myEducationInstitutionMapper.updateStatus(eid, newStatus, oldStatus);
        if (i != 1)
            throw new SQLException("状态更新失败");
        if (oldStatus.equals(EducationInstitutionStatusEnum.AUDITING.getStatus()) && newStatus.equals(EducationInstitutionStatusEnum.AUDIT_FAILURE.getStatus())) {
            EducationUser educationUser = new EducationUser();
            educationUser.seteId(0);
            EducationUserExample educationUserExample = new EducationUserExample();
            educationUserExample.createCriteria().andEIdEqualTo(eid);
            educationUserMapper.updateByExampleSelective(educationUser, educationUserExample);
        }


    }

    @Transactional
    public void throughAudit(Integer eid, String phone, String name) {
        changeStatus(eid, EducationInstitutionStatusEnum.THROUGH_AUDIT.getStatus(), EducationInstitutionStatusEnum.AUDITING.getStatus());

        if (!chatPlatform.register("web_" + phone, name + "-负责人", "123456", storagePlatform.getImageURL("pc-portrait.png"))) {
            throw new SQLException("审核失败,请刷新后重试");
        }
    }

    public boolean disable(Integer eid) {
        // TODO 功能暂时没想好,就先不写了
        //先从教育机构的课程开始禁用

        //接着是员工状态

        //最后是机构状态
        if (false) {

            smsPlatform.send(myEducationUserMapper.getPhone(eid), "您的机构[" + myEducationInstitutionMapper.getName(eid) + "]因相关原因被平台封禁，所有平台功能将无法正常使用。如需有任何疑问请联系平台客服0571-28120182");
        }

        return true;
    }

    public EducationInstitution get(Integer eid) {
        return educationInstitutionMapper.selectByPrimaryKey(eid);
    }


    public PageResult<EducationInstitution> list(EducationInstitutionQuery query) {
        EducationInstitutionExample educationInstitutionExample = new EducationInstitutionExample();
        educationInstitutionExample.createCriteria().andStatusEqualTo(query.getStatus());
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<EducationInstitution> educationInstitutionList = educationInstitutionMapper.selectByExample(educationInstitutionExample);
        PageInfo<EducationInstitution> pageInfo = new PageInfo<>(educationInstitutionList);
        PageResult<EducationInstitution> pageResult = new PageResult<>();
        pageResult.setRows(educationInstitutionList);
        pageResult.setTotal(pageInfo.getTotal());
        return pageResult;
    }

    public Integer allCount() {
        EducationInstitutionExample educationInstitutionExample = new EducationInstitutionExample();
        educationInstitutionExample.createCriteria().andStatusEqualTo(EducationInstitutionStatusEnum.THROUGH_AUDIT.getStatus());
        return educationInstitutionMapper.countByExample(educationInstitutionExample);
    }

    public Integer monthlyCount() {
        return myEducationInstitutionMapper.monthlyCount();
    }

    public PageResult newestEducationInstitutionList() {
        PageHelper.startPage(1, 8);
        EducationInstitutionExample educationInstitutionExample = new EducationInstitutionExample();
        educationInstitutionExample.setOrderByClause("create_time desc");
        educationInstitutionExample.createCriteria().andStatusEqualTo(EducationInstitutionStatusEnum.AUDITING.getStatus());
        List<EducationInstitution> educationInstitutionList = educationInstitutionMapper.selectByExample(educationInstitutionExample);
        PageInfo<EducationInstitution> pageInfo = new PageInfo<>(educationInstitutionList);
        PageResult<EducationInstitutionNewest> pageResult = new PageResult<>();
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setRows(educationInstitutionList
                .stream()
                .map(educationInstitution -> dozerBeanMapper.map(educationInstitution, EducationInstitutionNewest.class))
                .collect(Collectors.toList()));
        return pageResult;
    }

    public String getName(Integer eid) {
        return myEducationInstitutionMapper.getName(eid);
    }
}
