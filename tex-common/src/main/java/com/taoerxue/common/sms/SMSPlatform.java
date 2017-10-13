package com.taoerxue.common.sms;

/**
 * Created by lizhihui on 2017-03-14 13:51.
 */
public interface SMSPlatform {
    int send(String phoneNumber, String content);

    String getSendResult(int code);
}
