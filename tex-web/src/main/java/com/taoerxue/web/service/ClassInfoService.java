package com.taoerxue.web.service;

import com.taoerxue.common.exception.SQLException;
import com.taoerxue.mapper.ClassInfoMapper;
import com.taoerxue.mapper.CourseInfoMapper;
import com.taoerxue.po.CourseInfoMongoPOJO;
import com.taoerxue.pojo.ClassInfo;
import com.taoerxue.pojo.ClassInfoExample;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lizhihui on 2017-03-22 13:20.
 */
@Service
public class ClassInfoService {
    @Resource
    private ClassInfoMapper classInfoMapper;
    @Resource
    private CourseInfoMapper courseInfoMapper;
    @Resource
    private MongoTemplate mongoTemplate;


    public List<ClassInfo> listByCourseId(Integer id) {
        ClassInfoExample classInfoExample = new ClassInfoExample();
        classInfoExample.createCriteria().andCourseIdEqualTo(id).andStatusEqualTo(1);
        return classInfoMapper.selectByExample(classInfoExample);
    }

    public void add(ClassInfo classInfo) {
        classInfoMapper.insertSelective(classInfo);
        ClassInfo classInfo1 = classInfoMapper.selectByPrimaryKey(5);
        System.out.println(classInfo1);
    }

    public boolean doesBelongToEducationInstitution(Integer classId, Integer eid) {
        //获取班级信息
        ClassInfo classInfo = classInfoMapper.selectByPrimaryKey(classId);
        return classInfo != null && eid.equals(courseInfoMapper.selectByPrimaryKey(classInfo.getCourseId()).geteId());

    }

    public boolean isEditable(Integer classId) {
        //获取班级信息是为了获取课程是否处于上架状态
        ClassInfo classInfo = classInfoMapper.selectByPrimaryKey(classId);

        //班级信息存在,并且上架课程中为空,那就是可以修改咯.
        return classInfo != null && mongoTemplate.findById(classInfo.getId(), CourseInfoMongoPOJO.class) == null;

       /* if (classInfo==null)
            return false;
        CourseInfoMongoPOJO byId = mongoTemplate.findById(classInfo.getId(), CourseInfoMongoPOJO.class);
        return byId == null;*/
    }

    @Transactional
    public void edit(ClassInfo classInfo) {
        int i = classInfoMapper.updateByPrimaryKeySelective(classInfo);
        if (i != 1)
            throw new SQLException("修改班级信息失败,请稍候重试");
    }

    @Transactional
    public void delete(Integer id) {
        ClassInfoExample classInfoExample = new ClassInfoExample();
        classInfoExample.createCriteria().andIdEqualTo(id).andStatusEqualTo(1);
        ClassInfo classInfo = new ClassInfo();
        classInfo.setStatus(0);
        if (classInfoMapper.updateByExampleSelective(classInfo, classInfoExample) != 1)
            throw new SQLException("删除班级失败");
    }
}
