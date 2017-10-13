package com.taoerxue.web.controller;

import com.taoerxue.common.bean.Result;
import com.taoerxue.web.service.EducationTypeService;
import com.taoerxue.web.vo.institution.EducationParentTypeWithoutPhoto;
import com.taoerxue.web.vo.institution.EducationTypes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lizhihui on 2017-03-21 12:35.
 */
@RestController
@RequestMapping("/educationType")
public class EducationTypeController {
    @Resource
    private EducationTypeService educationTypeService;

    @RequestMapping("/list")
    public Result list() {
        List<EducationTypes> typeList = educationTypeService.list();
        return Result.ok(typeList);
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
