package com.taoerxue.manager.controller;

import com.taoerxue.common.bean.Result;
import com.taoerxue.manager.service.EducationTypeService;
import com.taoerxue.manager.vo.institution.EducationTypesWithStatus;
import com.taoerxue.pojo.EducationType;
import com.taoerxue.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lizhihui on 2017-04-20 17:33.
 */
@RestController
@RequestMapping("/educationType")
public class EducationTypeController {


    @Resource
    private EducationTypeService educationTypeService;

    @RequestMapping("/list")
    public Result list() {
        List<EducationTypesWithStatus> list = educationTypeService.list();
        return Result.ok(list);
    }

    @RequestMapping("/add")
    public Result add(String name, Integer parentId) {
        if (StringUtils.isEmpty(name))
            return Result.build(500, "请输入二级分类名");
        if (!StringUtils.lengthRangeIn(name, 2, 6))
            return Result.build(500, "二级分类名称的长度位2-6个字符");
        if (parentId == null)
            return Result.build(500, "请选择一级分类");
        EducationType educationType = educationTypeService.get(parentId);
        if (educationType == null || !educationType.getParentId().equals(0) || !educationType.getStatus())
            return Result.build(500, "不存在的一级分类");
        educationTypeService.add(name, parentId);
        return Result.ok();
    }

    @RequestMapping("/disable")
    public Result disable(Integer id) {
        if (id == null) {
            return Result.build(500, "请选择你要禁用的二级分类");
        }
        educationTypeService.disable(id);
        return Result.build(200, "禁用成功");
    }

    @RequestMapping("/enable")
    public Result enable(Integer id) {
        if (id == null) {
            return Result.build(500, "请选择你要启用的二级分类");
        }
        educationTypeService.enable(id);
        return Result.build(200, "启用成功");
    }

}
