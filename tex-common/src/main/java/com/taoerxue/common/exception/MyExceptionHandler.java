package com.taoerxue.common.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class MyExceptionHandler implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView mv = new ModelAndView();/*模型视图*/
        MappingJackson2JsonView view = new MappingJackson2JsonView();/*json视图*/
        Map<String, Object> attributes = new HashMap<>();
        String errorMessage;
        if (e instanceof CustomException) {
            errorMessage = e.getMessage();
        } else if (e instanceof NullPointerException) {
            errorMessage = "空指针异常";
        } else {
            errorMessage = "服务器内部错误";
        }
        attributes.put("status", 500);
        attributes.put("msg", errorMessage);
        attributes.put("data", null);
        view.setAttributesMap(attributes);
        mv.setView(view);
        return mv;
    }
}
