package com.taoerxue.manager.interceptor;

import com.taoerxue.common.bean.Result;
import com.taoerxue.common.cache.redis.JedisClient;
import com.taoerxue.pojo.AdminUser;
import com.taoerxue.util.JsonUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 * Created by lizhihui on 2017-03-16 17:30.
 */
public class LoginInterceptor extends MyHandlerInterceptor {

    @Resource
    private JedisClient jedisClient;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object var3) throws Exception {
        //检查是否缺少 token 参数
        String userToken = request.getParameter("token");
        if (StringUtils.isEmpty(userToken)) {
            writerResponse(response, JsonUtils.objectToJson(Result.build(500, "请先登录")));
            return false;
        }

        //检查 token 是否过期
        String  appUserJson = jedisClient.get(userToken);
        AdminUser adminUser = JsonUtils.jsonToPojo(appUserJson, AdminUser.class);
        if (StringUtils.isEmpty(appUserJson) || adminUser == null) {
            writerResponse(response, JsonUtils.objectToJson(Result.build(500, "登录过期,请重新登录")));
            return false;
        }
        if (!adminUser.getStatus()){
            writerResponse(response, JsonUtils.objectToJson(Result.build(500, "帐号被禁用,无法进行操作")));
            return false;
        }

        // TODO 之后考虑单用户在线问题
        //将用户信息放入 session 中
        request.getSession().setAttribute("user", adminUser);
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object var3, ModelAndView var4) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object var3, Exception var4) throws Exception {

    }
}
