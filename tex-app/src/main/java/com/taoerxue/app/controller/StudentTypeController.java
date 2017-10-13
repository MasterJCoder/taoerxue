package com.taoerxue.app.controller;

import com.taoerxue.app.service.StudentTypeService;
import com.taoerxue.common.bean.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by lizhihui on 2017-03-30 12:17.
 */

@RestController
@RequestMapping("/studentType")
public class StudentTypeController {

    @Resource
    private StudentTypeService studentTypeService;


    @RequestMapping("/list")
    public Result list() {
        return Result.ok(studentTypeService.list());
    }
}
