package com.taoerxue.common.constant;

/**
 * Created by lizhihui on 2017-03-17 12:18.
 */
public class CacheConstant {
    //登录过期时间
    public static final int LOGIN_EXPIRE_HOURS = 3;
    public static final int LOGIN_EXPIRE_MINUTES = LOGIN_EXPIRE_HOURS * 60;
    public static final int LOGIN_EXPIRE_SECONDS = LOGIN_EXPIRE_MINUTES * 60;
    public static final int LOGIN_EXPIRE_MILLISECONDS = LOGIN_EXPIRE_SECONDS * 1000;


    //验证码过期时间
    public static final int VERIFICATION_CODE_EXPIRE_MINUTES = 5;
    public static final int VERIFICATION_CODE_EXPIRE_SECONDS = VERIFICATION_CODE_EXPIRE_MINUTES * 60;
    public static final int VERIFICATION_CODE_EXPIRE_MILLISECONDS = VERIFICATION_CODE_EXPIRE_SECONDS * 60;


}
