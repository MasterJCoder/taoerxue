package com.taoerxue.web.controller;

import com.taoerxue.common.bean.Result;
import com.taoerxue.web.dto.classinfo.ClassInfoAdd;
import com.taoerxue.web.dto.classinfo.ClassInfoDetail;
import com.taoerxue.web.dto.classinfo.ClassInfoEdit;
import com.taoerxue.pojo.ClassInfo;
import com.taoerxue.pojo.EducationUser;
import com.taoerxue.web.service.ClassInfoService;
import com.taoerxue.web.service.CourseInfoService;
import org.dozer.DozerBeanMapper;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lizhihui on 2017-03-22 13:14.
 */
@RestController
@RequestMapping("/class")
public class ClassInfoController extends BaseController {

    @Resource
    private ClassInfoService classInfoService;
    @Resource
    private CourseInfoService courseInfoService;
    @Resource
    private DozerBeanMapper dozerBeanMapper;


    /**
     * 添加班级
     *
     * @param classInfoAdd  班级信息
     * @param bindingResult 错误信息
     * @return 返回添加结果
     */
    @RequestMapping("/add")
    public Result add(@Valid ClassInfoAdd classInfoAdd, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors())
            return Result.build(500, getErrors(bindingResult));

        EducationUser user = (EducationUser) request.getSession().getAttribute("user");
        boolean editable = courseInfoService.isEditable(classInfoAdd.getCourseId(), user.geteId());
        if (!editable)
            return Result.build(500, "您无法未该课程添加班级信息,该课程已经通过审核");
        ClassInfo classInfo = dozerBeanMapper.map(classInfoAdd, ClassInfo.class);
        classInfoService.add(classInfo);

        return Result.build(200, "添加成功");
    }

    /**
     * 删除班级
     *
     * @param id 班级 id
     * @return 删除结果
     */
    @RequestMapping("/delete")
    public Result delete(Integer id, HttpServletRequest request) {
        if (id == null)
            return Result.build(500, "请选择你要删除的班级");

        //判断该班级是否属于这个机构
        EducationUser user = (EducationUser) request.getSession().getAttribute("user");
        boolean result = classInfoService.doesBelongToEducationInstitution(id, user.geteId());
        if (!result)
            return Result.build(500, "该班级不存在或者你没权利删除班级信息");

        //判断该班级信息是否可删除
        boolean editable = classInfoService.isEditable(id);
        if (!editable)
            return Result.build(500, "课程已经上架,无法删除班级信息");
        classInfoService.delete(id);
        return Result.ok();


    }

    /**
     * 获取课程的班级列表
     *
     * @param id 课程 id
     * @return 该课程对应的班级列表
     */
    @RequestMapping("/detail")
    public Result getDetail(Integer id) {
        if (id == null || id == 0)
            return Result.build(500, "请选择你要查看的课程");
        List<ClassInfo> classInfoList = classInfoService.listByCourseId(id);
        List<ClassInfoDetail> classInfoDetailList = classInfoList.stream()
                .map(classInfo -> dozerBeanMapper.map(classInfo, ClassInfoDetail.class))
                .collect(Collectors.toList());
        return Result.ok(classInfoDetailList);
    }

    @RequestMapping("/edit")
    public Result edit(@Valid ClassInfoEdit classEdit, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors())
            return Result.build(500, getErrors(bindingResult));


        //判断该班级是否属于这个机构
        EducationUser user = (EducationUser) request.getSession().getAttribute("user");
        boolean result = classInfoService.doesBelongToEducationInstitution(classEdit.getId(), user.geteId());
        if (!result)
            return Result.build(500, "该班级不存在或者你没权利修改班级信息");

        //判断该班级信息是否可编辑
        boolean editable = classInfoService.isEditable(classEdit.getId());
        if (!editable)
            return Result.build(500, "该班级不存在或者课程已经上架,无法修改班级信息");

        ClassInfo classInfo = dozerBeanMapper.map(classEdit, ClassInfo.class);
        classInfoService.edit(classInfo);
        return Result.build(200, "修改成功");

    }
}
