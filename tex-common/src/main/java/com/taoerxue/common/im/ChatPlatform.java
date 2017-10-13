package com.taoerxue.common.im;

/**
 * Created by lizhihui on 2017-04-14 09:38.
 */
public interface ChatPlatform {
    boolean register(String account, String name, String token,String icon);

    boolean updateToken(String account, String token);

    boolean disable(String account);
    boolean enable(String account);
}
