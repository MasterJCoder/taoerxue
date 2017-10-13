package com.taoerxue.web.controller;

import com.taoerxue.common.bean.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lizhihui on 2017-03-16 10:32.
 */
@RestController
@RequestMapping("/error")
public class ExceptionController {


    /*
        @ExceptionHandler(MaxUploadSizeExceededException.class)
        public Result handleException() {
            return Result.build(500, "文件上传过大");
        }*/


    @RequestMapping("/413")
    public Result error413() {
        return Result.build(500, "上传文件过大");
    }

    @RequestMapping("/404")
    public Result error404() {
        return Result.build(500, "找不到该请求");
    }

    @RequestMapping("/400")

    public Result error400() {
        return Result.build(500, "提交的参数为空值,或格式错误");
    }
}
