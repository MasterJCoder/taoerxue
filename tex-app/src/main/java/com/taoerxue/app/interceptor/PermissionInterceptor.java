package com.taoerxue.app.interceptor;

import com.taoerxue.app.service.UserService;
import com.taoerxue.common.bean.Result;
import com.taoerxue.common.enums.EducationUserStatusEnum;
import com.taoerxue.pojo.AppUser;
import com.taoerxue.util.JsonUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lizhihui on 2017-03-18 12:35.
 */
public class PermissionInterceptor extends MyHandlerInterceptor {

    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object var3) throws Exception {
        AppUser user = (AppUser) request.getSession().getAttribute("user");
        if (!userService.getUserStatus(user).equals(EducationUserStatusEnum.SUCCESS.getStatus())) {
            // TODO 日志记录
            writerResponse(response, JsonUtils.objectToJson(Result.build(500, "你没有权限进行该操作")));
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object var3, ModelAndView var4) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object var3, Exception var4) throws Exception {

    }
}
