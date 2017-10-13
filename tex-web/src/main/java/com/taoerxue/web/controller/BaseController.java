package com.taoerxue.web.controller;

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



    protected String getErrors(BindingResult result) {
        List<FieldError> list = result.getFieldErrors();
        return list.get(list.size()-1).getDefaultMessage();
    }
}
