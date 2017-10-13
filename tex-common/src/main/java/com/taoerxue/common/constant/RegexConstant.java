package com.taoerxue.common.constant;

/**
 * Created by lizhihui on 2017-03-14 12:44.
 */
public class RegexConstant {
    private RegexConstant() {
    }

    public static final String PHONE = "^1[3|5|7|8]\\d{9}$";
    public static final String PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
    public static final String LICENSE_NUMBER = "[0-9a-zA-z]{18}|[0-9a-zA-z]{15}";
}
