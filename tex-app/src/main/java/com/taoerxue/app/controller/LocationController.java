package com.taoerxue.app.controller;

import com.taoerxue.app.service.LocationService;
import com.taoerxue.common.bean.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by lizhihui on 2017-04-25 10:56.
 */
@RestController
@RequestMapping("/location")
public class LocationController {


    @Resource
    private LocationService locationService;

    @RequestMapping("/areaList")
    public Result areaList(Integer cityId){
        return Result.ok(locationService.areaList(cityId));
    }
}
