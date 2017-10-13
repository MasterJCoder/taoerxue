package com.taoerxue.rest.controller;

import com.taoerxue.common.bean.Result;
import com.taoerxue.rest.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by lizhihui on 2017-03-09 14:45.
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/app/addUser", method = RequestMethod.POST)
    public Result addAppUser(String phone, String password) {
        return userService.addAppUser(phone, password);
    }

}
