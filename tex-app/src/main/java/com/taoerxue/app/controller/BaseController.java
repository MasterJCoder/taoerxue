package com.taoerxue.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * Created by lizhihui on 2017-03-14 13:13.
 */
public class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    String getErrors(BindingResult result) {
        List<FieldError> list = result.getFieldErrors();
        return list.get(list.size() - 1).getDefaultMessage();
    }


    /**
     * @param cityId         前端传来的城市id
     * @param areaId         前端传过来的区域 id
     * @param locationCityId 用户所在的城市 id
     * @return 是否显示距离
     */

    Boolean displayDistance(Integer cityId, Integer areaId, Integer locationCityId) {
        //如果城市和区域 id 都没传.那就显示距离
        if (cityId == null && areaId == null)
            return true;

        //如果传了区域 id,要判断是否同城
        if (areaId != null) {
            String cityCode = locationCityId.toString().substring(0, 4);
            String inCityCode = areaId.toString().substring(0, 4);
            return cityCode.equals(inCityCode);
        }


        //如果传了城市 id,并且同城,那也显示
        return locationCityId.equals(cityId);

    }
}
