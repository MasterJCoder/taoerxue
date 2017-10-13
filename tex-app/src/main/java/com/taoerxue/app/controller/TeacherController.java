package com.taoerxue.app.controller;

import com.taoerxue.app.service.TeacherService;
import com.taoerxue.app.vo.teacher.TeacherDetail;
import com.taoerxue.common.bean.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by lizhihui on 2017-03-29 16:55.
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {


    @Resource
    private TeacherService teacherService;

    @RequestMapping("/detail")
    public Result detail(Integer id) {
        if (id == null)

            return Result.build(500, "缺少教师 id");

        TeacherDetail teacherDetail = teacherService.detail(id);
        return Result.ok(teacherDetail);
    }

    @RequestMapping("/courseList")
    public Result courseList(Integer id, Integer pageNum, Integer pageSize) {

        if (id == null)
            return Result.build(500, "缺少教师 id");
        if (pageNum == null || pageNum == 0)
            pageNum = 1;
        if (pageSize == null || pageSize == 0)
            pageSize = 10;

        return Result.ok(teacherService.courseList(id, pageNum, pageSize));
    }
}
