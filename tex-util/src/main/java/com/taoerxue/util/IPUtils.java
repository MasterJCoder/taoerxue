package com.taoerxue.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lizhihui on 2017-04-26 11:31.
 */
public class IPUtils {
    public static String getClientIp(HttpServletRequest request) {

        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }

}
