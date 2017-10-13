package com.taoerxue.web.service;

import com.taoerxue.mapper.StudentLevelMapper;
import com.taoerxue.pojo.StudentLevel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lizhihui on 2017-03-21 14:50.
 */
@Service
public class StudentLevelService {
    @Resource
    private StudentLevelMapper studentLevelMapper;

    /**
     * @param id 等级 id
     * @return 学生等级
     */

    public StudentLevel get(Integer id) {
        return studentLevelMapper.selectByPrimaryKey(id);
    }

}
