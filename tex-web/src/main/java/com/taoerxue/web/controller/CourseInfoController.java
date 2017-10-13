package com.taoerxue.web.controller;

import com.taoerxue.common.bean.PageResult;
import com.taoerxue.common.bean.Result;
import com.taoerxue.common.constant.CommonConstant;
import com.taoerxue.common.constant.CourseConstant;
import com.taoerxue.common.enums.CourseStatusEnum;
import com.taoerxue.common.os.StoragePlatform;
import com.taoerxue.dto.FullEducationType;
import com.taoerxue.web.dto.course.CourseEdit;
import com.taoerxue.web.dto.course.CoursePublish;
import com.taoerxue.web.dto.StudentTypeInfo;
import com.taoerxue.pojo.CourseInfo;
import com.taoerxue.pojo.EducationInstitution;
import com.taoerxue.pojo.EducationUser;
import com.taoerxue.pojo.StudentLevel;
import com.taoerxue.web.vo.course.CourseDetail;
import com.taoerxue.web.vo.course.CourseDetailWithClassInfo;
import com.taoerxue.web.vo.course.CourseTeacherSimple;
import com.taoerxue.web.service.*;
import org.dozer.DozerBeanMapper;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程相关的 controller
 * Created by lizhihui on 2017-03-21 10:31.
 */

@RequestMapping("/course")
@RestController()
public class CourseInfoController extends BaseController {

    @Resource
    private TeacherService teacherService;
    @Resource
    private StoragePlatform storagePlatform;
    @Resource
    private EducationTypeService educationTypeService;
    @Resource
    private StudentTypeService studentTypeService;
    @Resource
    private EducationInstitutionService educationInstitutionService;
    @Resource
    private DozerBeanMapper dozerBeanMapper;
    @Resource
    private CourseInfoService courseInfoService;
    @Resource
    private StudentLevelService studentLevelService;


    /**
     * 发布课程
     *
     * @param publish       封装了课程信息
     * @param bindingResult 错误信息
     * @return 发布课程结果
     */
    @RequestMapping("/publish")
    public Result add(@Valid CoursePublish publish, BindingResult bindingResult, HttpServletRequest request,
                      @RequestParam("teachers[]") Integer[] teachers, @RequestParam("studentType[]") Integer[] studentType) {
        if (bindingResult.hasErrors())
            return Result.build(500, getErrors(bindingResult));

        //判断老师是否添加,将数组转换成 List
        List<Integer> teacherList = teacherService.isNull(teachers);
        if (teacherList.size() == 0)
            return Result.build(500, CourseConstant.TEACHER_NULL_ERROR);
        //判断学生类型是否为空
        List<Integer> typeList = studentTypeService.isNull(studentType);
        if (typeList.size() == 0)
            return Result.build(500, CourseConstant.STUDENT_TYPE_NULL_ERROR);

        //判断学生类型是否合法
        StudentTypeInfo studentTypeInfo = studentTypeService.isRight(typeList);
        if (studentTypeInfo == null)
            return Result.build(500, CourseConstant.STUDENT_TYPE_ERROR);

        //判断课程类型是否存在
        FullEducationType fullEducationType = educationTypeService.getFullTypeById(publish.getTypeId());
        if (fullEducationType == null)
            return Result.build(500, CommonConstant.EDUCATION_TYPE_NOT_EXISTS_ERROR);

        //判断添加的老师是否属于该机构
        EducationUser user = (EducationUser) request.getSession().getAttribute("user");
        Integer eId = user.geteId();//获取机构 id
        boolean doesBelong = teacherService.belongTo(teacherList, eId);
        if (!doesBelong)
            return Result.build(500, "你未添加教师或者添加了被禁用的教师,请重新选择");

        //判断图片是否存在
        Boolean exists = storagePlatform.exists(publish.getPhoto());
        if (!exists)
            return Result.build(500, CourseConstant.PHOTO_NOT_EXISTS_ERROR);

        //以上校验都通过了,准备插入数据库,先获取机构信息
        EducationInstitution educationInstitution = educationInstitutionService.get(eId);
        CourseInfo courseInfo = dozerBeanMapper.map(educationInstitution, CourseInfo.class);
        courseInfo.setCreateTime(null);
        courseInfo.setUpdateTime(null);
        courseInfo.seteId(eId);
        dozerBeanMapper.map(publish, courseInfo);
        dozerBeanMapper.map(fullEducationType, courseInfo);
        dozerBeanMapper.map(studentTypeInfo, courseInfo);
        StudentLevel studentLevel = studentLevelService.get(courseInfo.getStudentLevelId());
        courseInfo.seteName(educationInstitution.getName());
        courseInfo.setStudentLevelName(studentLevel.getName());
        courseInfo.setId(null);
        courseInfo.setStatus(0);
        Integer courseId = courseInfoService.publish(courseInfo, teacherList);
        Map<String, Integer> map = new HashMap<>();
        map.put("courseId", courseId);
        return Result.ok(map);
    }


    @RequestMapping("/edit")
    public Result edit(@Valid CourseEdit edit, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors())
            return Result.build(500, getErrors(bindingResult));
        //先获取原课程信息吧,方便做对比
        EducationUser user = (EducationUser) request.getSession().getAttribute("user");
        CourseInfo courseInfo = courseInfoService.get(edit.getId());
        if (courseInfo == null || !courseInfo.geteId().equals(user.geteId()) || courseInfo.getStatus().equals(CourseStatusEnum.THROUGH_AUDIT.getStatus()))
            return Result.build(500, "该课程可能已经上架,无法修改");

        //复制
        CourseInfo newCourseInfo = dozerBeanMapper.map(edit, CourseInfo.class);


        //将提交的信息和原信息做对比.其他信息不对比了.主要对比复杂的数据
        //比对课程类型变了没
        Integer typeId = edit.getTypeId();
        if (typeId != null && !typeId.equals(courseInfo.getTypeId())) {
            //进到这里说明变化了.获取全类型,看下该类型是否存在,如果不存在.就报错
            FullEducationType fullEducationType = educationTypeService.getFullTypeById(typeId);
            if (fullEducationType == null)
                return Result.build(500, CommonConstant.EDUCATION_TYPE_NOT_EXISTS_ERROR);
            //将新的类型赋值给要
            dozerBeanMapper.map(fullEducationType, newCourseInfo);
        } else {
            //把 type 消除掉
            newCourseInfo.setTypeId(null);
        }

        //对比图片是否变化
        String photo = edit.getPhoto();
        if (photo != null && photo.equals(courseInfo.getPhoto())) {
            //进到这里说明图片变化了,先判断下图片是否存在
            if (!storagePlatform.exists(photo))
                return Result.build(500, "上传的图片不存在,请重新上传");

            //图片存在.而且发生变化了.那就设置进去吧
            newCourseInfo.setPhoto(photo);
        }

        //判断学生类型是否发生变化
        List<Integer> typeList = studentTypeService.isNull(edit.getStudentType());
        if (typeList.size() != 0) {
            //到这里只能说明前端把学生类型也上传上来了.但还不能知道有没有变化
            //先判断学生是否合法
            StudentTypeInfo studentTypeInfo = studentTypeService.isRight(typeList);
            if (studentTypeInfo == null)
                return Result.build(500, CourseConstant.STUDENT_TYPE_ERROR);

            //合法的话.就和原来的数据进行对比.发生是否有变化
            if (!studentTypeInfo.getStudentTypeIds().equals(courseInfo.getStudentTypeIds())) {
                //到这里才是真的发生变化了.既然变化了.那就赋值吧
                dozerBeanMapper.map(studentTypeInfo, newCourseInfo);
            }
        }

        //最烦的就是老师的判断了.接下来判断老师的变化,先判断前端有没有传过来
        //算了,不判断了,如果前端有老师传过来.我删除老师,再加,只能这么干了.以后再优化吧.
        //那先判断前段传过来的老师是否合法吧
        List<Integer> teacherList = teacherService.isNull(edit.getTeachers());
        if (teacherList.size() != 0) {
            //前段如果传过来老师了.校验老师的合法性
            Integer eId = user.geteId();//获取机构 id
            boolean doesBelong = teacherService.belongTo(teacherList, eId);
            if (!doesBelong)
                return Result.build(500, "你未添加教师或者添加了被禁用的教师,请重新选择");
        }

        //判断学生基础是否也发生变化了
        Integer studentLevelId = edit.getStudentLevelId();
        if (studentLevelId != null && !studentLevelId.equals(courseInfo.getStudentLevelId())) {
            StudentLevel studentLevel = studentLevelService.get(courseInfo.getStudentLevelId());
            courseInfo.setStudentLevelName(studentLevel.getName());
        } else {
            //消除等级 id
            courseInfo.setStudentLevelId(null);
        }


        //老师的校验也通过了.那么.就可以更新了吧...
        courseInfoService.update(newCourseInfo, teacherList);
        return Result.build(200, "修改成功");
    }

    /**
     * 课程详情,不包含班级信息
     *
     * @param id 课程 id
     * @return 课程详情
     */
    @RequestMapping("/detail")
    public Result getDetail(Integer id, HttpServletRequest request) {
        if (id == null || id == 0)
            return Result.build(500, "请选择你要查看的课程");
        EducationUser user = (EducationUser) request.getSession().getAttribute("user");
        CourseDetail courseDetail = courseInfoService.getDetail(id, user.geteId());
        return Result.ok(courseDetail);
    }

    /**
     * 课程详情,包含班级信息
     *
     * @param id 课程 id
     * @return 课程详情, 包含班级
     */
    @RequestMapping("/detailWithClassInfo")
    public Result getDetailWithClassInfo(Integer id, HttpServletRequest request) {
        if (id == null || id == 0)
            return Result.build(500, "请选择你要查看的课程");
        EducationUser user = (EducationUser) request.getSession().getAttribute("user");
        CourseDetailWithClassInfo courseDetailWithClassInfo = courseInfoService.getDetailWithClassInfo(id, user.geteId());
        return Result.ok(courseDetailWithClassInfo);
    }

    /**
     * 提交审核
     */
    @RequestMapping("/submitAudit")
    public Result submitAudit(Integer id, HttpServletRequest request) {
        if (id == null)
            return Result.build(500, "请选择要提交审核的课程");
        EducationUser user = (EducationUser) request.getSession().getAttribute("user");
        if (!courseInfoService.isExists(id, user.geteId()))
            return Result.build(500, "你要审核的课程不存在");
        boolean doesAddClassInfo = courseInfoService.doesAddClassInfo(id);
        if (!doesAddClassInfo)
            return Result.build(500, "请先添加班级信息再提交审核");
        courseInfoService.changeStatus(id, CourseStatusEnum.NOT_SUBMIT.getStatus(), CourseStatusEnum.AUDITING.getStatus());
        return Result.build(200, "提交审核成功");
    }


    @RequestMapping("/teacherList")
    public Result teacherList(HttpServletRequest request) {
        EducationUser user = (EducationUser) request.getSession().getAttribute("user");

        List<CourseTeacherSimple> teacherSimpleList = courseInfoService.getTeacherList(user.geteId());
        return Result.ok(teacherSimpleList);
    }

    @RequestMapping("/throughAuditList")
    public PageResult throughAuditList(HttpServletRequest request, Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum == 0)
            pageNum = 1;
        if (pageSize == null || pageSize == 0)
            pageSize = 10;
        EducationUser user = (EducationUser) request.getSession().getAttribute("user");
        return courseInfoService.throughAuditList(user.geteId(), pageNum, pageSize);
    }


    @RequestMapping("/auditingList")
    public PageResult auditingList(HttpServletRequest request, Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum == 0)
            pageNum = 1;
        if (pageSize == null || pageSize == 0)
            pageSize = 10;
        EducationUser user = (EducationUser) request.getSession().getAttribute("user");
        return courseInfoService.auditingList(user.geteId(), pageNum, pageSize);
    }

    @RequestMapping("/newPublishList")
    public PageResult newPublishList(HttpServletRequest request, Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum == 0)
            pageNum = 1;
        if (pageSize == null || pageSize == 0)
            pageSize = 10;
        EducationUser user = (EducationUser) request.getSession().getAttribute("user");
        return courseInfoService.newPublishList(user.geteId(), pageNum, pageSize);
    }

    @RequestMapping("/cancelAudit")
    public Result cancelAudit(Integer id, HttpServletRequest request) {
        if (id == null)
            return Result.build(500, "请选择你要取消审核的课程");
        EducationUser user = (EducationUser) request.getSession().getAttribute("user");
        if (!courseInfoService.isExists(id, user.geteId()))
            return Result.build(500, "你没有权利取消上架课程");
        courseInfoService.changeStatus(id, CourseStatusEnum.AUDITING.getStatus(), CourseStatusEnum.NOT_SUBMIT.getStatus());
        return Result.ok();
    }

    @RequestMapping("/cancelGrounding")
    public Result cancelGrounding(Integer id, HttpServletRequest request) {
        EducationUser user = (EducationUser) request.getSession().getAttribute("user");
        if (!courseInfoService.isExists(id, user.geteId()))
            return Result.build(500, "你没有权利取消上架课程");
        courseInfoService.changeStatus(id, CourseStatusEnum.THROUGH_AUDIT.getStatus(), CourseStatusEnum.NOT_SUBMIT.getStatus());

        return Result.ok();
    }
}
