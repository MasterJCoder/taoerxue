package com.taoerxue.web.service;

import com.taoerxue.common.exception.SQLException;
import com.taoerxue.web.dto.teacher.TeacherEdit;
import com.taoerxue.mapper.EducationInstitutionMapper;
import com.taoerxue.mapper.EducationTypeMapper;
import com.taoerxue.mapper.MyEducationUserMapper;
import com.taoerxue.mapper.TeacherMapper;
import com.taoerxue.pojo.EducationInstitution;
import com.taoerxue.pojo.EducationType;
import com.taoerxue.pojo.EducationTypeExample;
import com.taoerxue.pojo.Teacher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 教育机构相关的 service
 * Created by lizhihui on 2017-03-17 13:12.
 */
@Service
public class EducationInstitutionService {

    @Resource
    private EducationTypeMapper educationTypeMapper;

    @Resource
    private EducationInstitutionMapper educationInstitutionMapper;

    @Resource
    private MyEducationUserMapper myEducationUserMapper;
    @Resource
    private TeacherMapper teacherMapper;

    /**
     * 获取机构课程一级分类
     *
     * @return 返回机构课程一级分类
     */
    public List<EducationType> listCourseType(Integer id) {
        EducationTypeExample educationTypeExample = new EducationTypeExample();
        educationTypeExample.createCriteria().andParentIdEqualTo(id).andStatusEqualTo(true);
        return educationTypeMapper.selectByExample(educationTypeExample);
    }


    @Transactional
    public void register(EducationInstitution educationInstitution, Integer userId) {
        //插入后可以获取主键
        educationInstitutionMapper.insertSelective(educationInstitution);
        Integer eid = educationInstitution.getId();

        //更新用户信息
        int updateCount = myEducationUserMapper.updateAfterRegister(userId, eid);
        if (updateCount != 1)
            throw new SQLException("你已经属于某家教育机构,无法提交机构信息");
    }


    public EducationType getCourseType(Integer typeId) {
        // TODO 缓存优化
        return educationTypeMapper.selectByPrimaryKey(typeId);
    }


    public Teacher addTeacher(Teacher teacher) {
        teacherMapper.insertSelective(teacher);
        return teacher;
    }

    public void update(TeacherEdit teacherEdit, Integer eid) {
        Teacher teacher = teacherMapper.selectByPrimaryKey(teacherEdit.getId());
        Teacher newTeacher = new Teacher();
        if (!teacherEdit.getName().equals(teacher.getName())) ;
//            newTeacher.
    }

    /**
     * 获取机构信息
     *
     * @param id 机构 id
     * @return 机构信息
     */
    public EducationInstitution get(Integer id) {
        return educationInstitutionMapper.selectByPrimaryKey(id);
    }
}
