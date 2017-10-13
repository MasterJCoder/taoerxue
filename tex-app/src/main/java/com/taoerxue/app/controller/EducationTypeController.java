package com.taoerxue.app.controller;

import com.taoerxue.app.service.EducationTypeService;
import com.taoerxue.common.bean.Result;
import com.taoerxue.app.vo.institution.EducationParentTypeWithPhoto;
import com.taoerxue.app.vo.institution.EducationParentTypeWithoutPhoto;
import com.taoerxue.app.vo.institution.EducationTypes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类型相关
 * Created by lizhihui on 2017-03-27 09:50.
 */

@RestController
@RequestMapping("/type")
public class EducationTypeController {


    @Resource
    private EducationTypeService educationTypeService;

    /**
     * 返回一级二级混合类别
     *
     * @return 哦
     */
    @RequestMapping("/list")
    public Result list() {
        List<EducationTypes> typeList = educationTypeService.list();
        return Result.ok(typeList);
    }

    /**
     * 返回一级列表
     *
     * @return 哦
     */
    @RequestMapping("/parentList")
    public Result parentList() {
        List<EducationParentTypeWithPhoto> educationParentTypeWithPhotoList = educationTypeService.parentList();
        return Result.ok(educationParentTypeWithPhotoList);
    }

    /**
     * 返回二级列表
     *
     * @param id 一级列表的 id
     * @return 哦
     */
    @RequestMapping("/childList")
    public Result childList(Integer id) {
        if (id == null)
            return Result.build(500, "缺少一级分类 id");
        List<EducationParentTypeWithoutPhoto> educationParentTypeWithoutPhotoList = educationTypeService.childList(id);
        return Result.ok(educationParentTypeWithoutPhotoList);
    }
}
