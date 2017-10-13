package com.taoerxue.web.service;

import com.taoerxue.web.dto.StudentTypeInfo;
import com.taoerxue.mapper.StudentTypeMapper;
import com.taoerxue.pojo.StudentType;
import com.taoerxue.pojo.StudentTypeExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lizhihui on 2017-03-21 13:53.
 */
@Service
public class StudentTypeService {
    @Resource
    private StudentTypeMapper studentTypeMapper;


    public List<Integer> isNull(Integer[] typeIds) {
        if (typeIds == null)
            return new ArrayList<>();
        ArrayList<Integer> typeList = new ArrayList<>(Arrays.asList(typeIds));
        while (typeList.contains(null)) {
            typeList.remove(null);
        }
        return typeList;
    }

    /**
     * 判断 学生类型是否合法,如果不合法,返回空
     *
     * @param typeList 学生类型ID列表
     * @return 学生类型信息
     */

    public StudentTypeInfo isRight(List<Integer> typeList) {
        StudentTypeExample studentTypeExample = new StudentTypeExample();
        studentTypeExample.createCriteria().andIdIn(typeList);
        List<StudentType> studentTypeList = studentTypeMapper.selectByExample(studentTypeExample);
        if (studentTypeList.size() != typeList.size())
            return null;
        StudentTypeInfo studentTypeInfo = new StudentTypeInfo();
        studentTypeInfo.setStudentTypeIds(getSumId(typeList));
        StringBuilder sb = new StringBuilder();
        for (StudentType studentType : studentTypeList) {
            sb.append(studentType.getName()).append(":");
        }
        String typeNames = sb.toString();
        typeNames = typeNames.substring(0, typeNames.length() - 1);
        studentTypeInfo.setStudentTypeNames(typeNames);
        return studentTypeInfo;
    }

    private int getSumId(List<Integer> typeList) {
        int sum = 0;
        for (Integer i : typeList) {
            sum += 1 << i - 1;
        }
        return sum;

    }
}
