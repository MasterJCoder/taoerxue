package com.taoerxue.app.service;

import com.taoerxue.mapper.StudentTypeMapper;
import com.taoerxue.pojo.StudentType;
import com.taoerxue.pojo.StudentTypeExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lizhihui on 2017-03-30 10:23.
 */
@Service
public class StudentTypeService {

    @Resource
    private StudentTypeMapper studentTypeMapper;

    public boolean isExists(Integer id) {
        StudentTypeExample studentTypeExample = new StudentTypeExample();
        studentTypeExample.createCriteria().andIdEqualTo(id);
        return studentTypeMapper.countByExample(studentTypeExample) == 1;
    }

    public String getName(Integer studentTypeId) {
        return studentTypeMapper.selectByPrimaryKey(studentTypeId).getName();
    }

    public List<StudentType> list() {
        return studentTypeMapper.selectByExample(new StudentTypeExample());
    }
}
