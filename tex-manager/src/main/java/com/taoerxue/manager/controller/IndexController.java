package com.taoerxue.manager.controller;

import com.taoerxue.common.bean.PageResult;
import com.taoerxue.common.bean.Result;
import com.taoerxue.manager.service.AppUserService;
import com.taoerxue.manager.service.CourseService;
import com.taoerxue.manager.service.EducationInstitutionService;
import com.taoerxue.manager.service.TeacherService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lizhihui on 2017-04-28 13:08.
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    @Resource
    private AppUserService appUserService;
    @Resource
    private EducationInstitutionService educationInstitutionService;
    @Resource
    private CourseService courseService;
    @Resource
    private TeacherService teacherService;

    @RequestMapping("/allCount")
    public Result count() {
        Integer appUserCount = appUserService.allCount();
        Integer educationInstitutionCount = educationInstitutionService.allCount();
        Integer courseCount = courseService.allCount();
        Integer teacherCount = teacherService.allCount();
        Map<String, Integer> map = new HashMap<>();
        map.put("appUserCount", appUserCount);
        map.put("educationInstitutionCount", educationInstitutionCount);
        map.put("courseCount", courseCount);
        map.put("teacherCount", teacherCount);
        return Result.ok(map);
    }
    @RequestMapping("/monthlyCount")
    public Result monthlyCount() {
        Integer appUserCount = appUserService.monthlyCount();
        Integer educationInstitutionCount = educationInstitutionService.monthlyCount();
        Integer courseCount = courseService.monthlyCount();
        Integer teacherCount = teacherService.monthlyCount();
        Map<String, Integer> map = new HashMap<>();
        map.put("appUserMonthlyCount", appUserCount);
        map.put("educationInstitutionMonthlyCount", educationInstitutionCount);
        map.put("courseMonthlyCount", courseCount);
        map.put("teacherMonthlyCount", teacherCount);
        return Result.ok(map);
    }
    @RequestMapping("/newestEducationInstitutionList")
    public PageResult newestEducationInstitutionList(){
        return educationInstitutionService.newestEducationInstitutionList();
    }
    @RequestMapping("/newestCourseList")
    public PageResult newestCourseList(){
        return courseService.newestCourseList();
    }
}
