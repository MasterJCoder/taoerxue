package com.taoerxue.manager.service;

import com.taoerxue.common.exception.SQLException;
import com.taoerxue.mapper.EducationInstitutionMapper;
import com.taoerxue.mapper.MyEducationInstitutionMapper;
import com.taoerxue.mapper.MyEducationUserMapper;
import com.taoerxue.pojo.EducationInstitution;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 教育机构相关的 service
 * Created by lizhihui on 2017-03-18 09:38.
 */

@Service
public class EducationInstitutionUserService {


    @Resource
    private MyEducationInstitutionMapper myEducationInstitutionMapper;
    @Resource
    private EducationInstitutionMapper educationInstitutionMapper;
    @Resource
    private MyEducationUserMapper myEducationUserMapper;

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
    }

    public boolean disable(Integer eid) {
        //先从教育机构的课程开始禁用

        //接着是员工状态

        //最后是机构状态
        return true;
    }

    public EducationInstitution get(Integer eid) {
        return educationInstitutionMapper.selectByPrimaryKey(eid);
    }

    public String getAdminPhone(Integer eid) {
       return myEducationUserMapper.getPhone(eid);
    }
}
