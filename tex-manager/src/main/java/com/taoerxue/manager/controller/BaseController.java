package com.taoerxue.manager.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * Created by lizhihui on 2017-04-27 14:26.
 */
public class BaseController {
    protected String getErrors(BindingResult result) {
        List<FieldError> list = result.getFieldErrors();
        return list.get(list.size() - 1).getDefaultMessage();
    }
}
