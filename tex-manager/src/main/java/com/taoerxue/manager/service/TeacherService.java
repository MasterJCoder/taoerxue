package com.taoerxue.manager.service;

import com.taoerxue.common.enums.TeacherStatusEnum;
import com.taoerxue.mapper.MyTeacherMapper;
import com.taoerxue.mapper.TeacherMapper;
import com.taoerxue.pojo.TeacherExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lizhihui on 2017-04-28 13:12.
 */
@Service
public class TeacherService {
    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private MyTeacherMapper myTeacherMapper;

    public Integer allCount() {
        TeacherExample teacherExample = new TeacherExample();
        teacherExample.createCriteria().andStatusEqualTo(TeacherStatusEnum.NORMAL.getStatus());
        return teacherMapper.countByExample(teacherExample);
    }

    public Integer monthlyCount() {
        return myTeacherMapper.monthlyCount();
    }
}
