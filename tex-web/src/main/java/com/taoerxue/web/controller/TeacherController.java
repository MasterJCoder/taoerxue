package com.taoerxue.web.controller;

import com.taoerxue.common.bean.PageResult;
import com.taoerxue.common.bean.Result;
import com.taoerxue.common.constant.TeacherConstant;
import com.taoerxue.common.enums.TeacherStatusEnum;
import com.taoerxue.common.os.StoragePlatform;
import com.taoerxue.dto.FullEducationType;
import com.taoerxue.web.dto.teacher.TeacherAdd;
import com.taoerxue.web.dto.teacher.TeacherEdit;
import com.taoerxue.pojo.EducationUser;
import com.taoerxue.pojo.Teacher;
import com.taoerxue.web.vo.teacher.TeacherDetail;
import com.taoerxue.web.vo.teacher.TeacherInfo;
import com.taoerxue.web.service.EducationInstitutionService;
import com.taoerxue.web.service.EducationTypeService;
import com.taoerxue.web.service.TeacherService;
import org.dozer.DozerBeanMapper;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 和教师有段的 controller
 * Created by lizhihui on 2017-03-18 15:37.
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController extends BaseController {

    @Resource
    private EducationInstitutionService educationInstitutionService;
    @Resource
    private StoragePlatform storagePlatform;
    @Resource
    private DozerBeanMapper dozerBeanMapper;
    @Resource
    private TeacherService teacherService;
    @Resource
    private EducationTypeService educationTypeService;

    /**
     * 添加老师
     *
     * @param teacherAdd    封装了教师信息的 dto
     * @param bindingResult 封装了错误信息
     * @return 添加老师的返回结果, 返回了老师的名字和 id
     */
    @RequestMapping("/add")
    public Result add(@Valid TeacherAdd teacherAdd, BindingResult bindingResult, HttpServletRequest request) {
        //判断是否包含错误
        if (bindingResult.hasErrors()) {
            return Result.build(500, getErrors(bindingResult));
        }
        //判断该授课类型是否存在
        FullEducationType fullEducationType = educationTypeService.getFullTypeById(teacherAdd.getTypeId());
        if (fullEducationType == null)
            return Result.build(500, TeacherConstant.EDUCATION_TYPE_ERROR);

        // 查看上传的图片是否存在
        Boolean exists = storagePlatform.exists(teacherAdd.getPhoto());
        if (!exists) {
            return Result.build(500, TeacherConstant.PHOTO_NULL_ERROR);
        }
        //如果都没问题,准备插入数据库
        Teacher teacher = dozerBeanMapper.map(teacherAdd, Teacher.class);
        dozerBeanMapper.map(fullEducationType, teacher);
        EducationUser user = (EducationUser) request.getSession().getAttribute("user");
        teacher.seteId(user.geteId());

        //插入数据库
        teacher = educationInstitutionService.addTeacher(teacher);

        //插入后将老师的信息返回
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", teacher.getId());
        map.put("name", teacher.getName());
        return Result.ok(map);
    }

    @RequestMapping("/update")
    public Result update(TeacherEdit teacherEdit, HttpServletRequest request) {
        if (teacherEdit.getId() == null)
            return Result.build(500, "请选择你要修改的教师");
        EducationUser user = (EducationUser) request.getSession().getAttribute("user");

        educationInstitutionService.update(teacherEdit, user.geteId());
        return Result.ok();
    }

    /**
     * 禁用教师
     *
     * @param id 教师 id
     * @return 禁用结果
     */
    @RequestMapping("/disable")
    public Result disable(Integer id, HttpServletRequest request) {
        if (id == null)
            return Result.build(500, "请选择你要禁用的教师");
        EducationUser user = (EducationUser) request.getSession().getAttribute("user");
        boolean result = teacherService.changeStatus(id, user.geteId(), TeacherStatusEnum.NORMAL.getStatus(), TeacherStatusEnum.DISABLED.getStatus());
        if (result)
            return Result.build(200, "禁用成功");
        return Result.build(500, "禁用失败");
    }

    /**
     * 启用教师
     *
     * @param id 教师 id
     * @return 启用结果
     */
    @RequestMapping("/enable")
    public Result enable(Integer id, HttpServletRequest request) {
        if (id == null)
            return Result.build(500, "请选择你要禁用的教师");
        EducationUser user = (EducationUser) request.getSession().getAttribute("user");
        boolean result = teacherService.changeStatus(id, user.geteId(), TeacherStatusEnum.DISABLED.getStatus(), TeacherStatusEnum.NORMAL.getStatus());
        if (result)
            return Result.build(200, "禁用成功");
        return Result.build(500, "禁用失败");
    }

    /**
     * @param id 教师 id
     * @return 详情
     */
    @RequestMapping("/detail")
    public Result detail(Integer id, HttpServletRequest request) {
        if (id == null)
            return Result.build(500, "请选择你要查看的教师");
        EducationUser user = (EducationUser) request.getSession().getAttribute("user");

        TeacherDetail teacherDetail = teacherService.getDetail(id, user.geteId());
        return Result.ok(teacherDetail);
    }

    @RequestMapping("/list")
    public PageResult list(Integer pageNum, Integer pageSize, HttpServletRequest request) {
        if (pageNum == null || pageNum == 0)
            pageNum = 1;
        if (pageSize == null || pageSize == 0)
            pageNum = 10;
        EducationUser user = (EducationUser) request.getSession().getAttribute("user");
        PageResult<Teacher> teacherPageResult = teacherService.list(user.geteId(), pageNum, pageSize);
        if (teacherPageResult.getTotal() > 0) {
            List<Teacher> rows = teacherPageResult.getRows();
            List<TeacherInfo> teacherInfoList = new ArrayList<>();
            for (Teacher teacher : rows) {
                TeacherInfo teacherInfo = dozerBeanMapper.map(teacher, TeacherInfo.class);
                teacherInfo.setPhoto(storagePlatform.getImageURL(teacherInfo.getPhoto()));
                teacherInfoList.add(teacherInfo);
            }
            PageResult<TeacherInfo> pageResult = new PageResult<>();
            pageResult.setTotal(teacherPageResult.getTotal());
            pageResult.setRows(teacherInfoList);
            return pageResult;
        }
        return teacherPageResult;
    }
}
