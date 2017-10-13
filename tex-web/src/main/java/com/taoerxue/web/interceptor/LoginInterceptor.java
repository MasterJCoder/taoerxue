package com.taoerxue.web.interceptor;

import com.taoerxue.common.bean.Result;
import com.taoerxue.common.cache.redis.JedisClient;
import com.taoerxue.common.constant.CacheConstant;
import com.taoerxue.pojo.EducationUser;
import com.taoerxue.util.JsonUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

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
        String educationUserJson = jedisClient.get(userToken);
        EducationUser educationUser = JsonUtils.jsonToPojo(educationUserJson, EducationUser.class);
        if (StringUtils.isEmpty(educationUserJson) || educationUser == null) {
            writerResponse(response, JsonUtils.objectToJson(Result.build(500, "登录过期,请重新登录")));
            return false;
        }

        //检查完之后重新设置过期时间
        jedisClient.expire(userToken, CacheConstant.LOGIN_EXPIRE_HOURS, TimeUnit.HOURS);

        // TODO 之后考虑单用户在线问题
        //将用户信息放入 session 中
        request.getSession().setAttribute("user", educationUser);
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object var3, ModelAndView var4) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object var3, Exception var4) throws Exception {

    }
}
