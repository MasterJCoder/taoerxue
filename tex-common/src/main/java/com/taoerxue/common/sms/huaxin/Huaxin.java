package com.taoerxue.common.sms.huaxin;


import com.taoerxue.common.cache.redis.JedisClient;
import com.taoerxue.common.sms.SMSPlatform;
import com.taoerxue.util.HttpClientUtil;
import com.taoerxue.util.JsonUtils;

import java.util.HashMap;
import java.util.Map;


public class Huaxin implements SMSPlatform {

    private final String account = "jksw036";
    private final String password = "631958";
    private JedisClient jedisClient;


    public void setJedisClient(JedisClient jedisClient) {
        this.jedisClient = jedisClient;
    }

    @Override
    public int send(String phone, String content) {
        content = "【淘儿学】" + content;
        Map<String, String> map = new HashMap<>();
        map.put("action", "send");
        map.put("userid", "");
        map.put("account", account);
        map.put("password", password);
        map.put("mobile", phone);
        map.put("content", content);
        map.put("sendTime", "");
        map.put("extno", "");
        String url = "http://sh2.ipyy.com/smsJson.aspx";
        String result = HttpClientUtil.doPost(url, map);
        HuaXinResult huaXinResult = JsonUtils.jsonToPojo(result, HuaXinResult.class);
        if (huaXinResult != null && huaXinResult.getReturnstatus().equals("Success"))
            return 200;
        return 500;
    }

    @Override
    public String getSendResult(int code) {
        if (code == 500)
            return "短息服务异常：请联系管理员";
        return null;
    }


}
