package com.taoerxue.app.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class MyHandlerInterceptor implements HandlerInterceptor {
    public abstract boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object var3) throws Exception;

    public abstract void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object var3, ModelAndView var4) throws Exception;

    public abstract void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object var3, Exception var4) throws Exception;


    /**
     * 把复用的代码抽取来了.
     *
     * @param json 要返回的结果
     */
    void writerResponse(HttpServletResponse httpServletResponse, String json) {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = httpServletResponse.getWriter();
            writer.append(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}
