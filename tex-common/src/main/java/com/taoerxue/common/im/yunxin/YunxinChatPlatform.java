package com.taoerxue.common.im.yunxin;

import com.taoerxue.common.im.ChatPlatform;
import com.taoerxue.util.JsonUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.*;

/**
 * Created by lizhihui on 2017-04-14 09:43.
 */
public class YunxinChatPlatform implements ChatPlatform {


    @Value("${PRODUCT}")
    Boolean product;

    private final String APP_KEY_TEST = "bd96fbfa0cf18f6a466c645ce3dffbc9";
    private final String APP_KEY_PRODUCT = "6aa6c4faa0e89031809824296f93d6a8";
    private final String APP_SECRET_TEST = "a748fae3043c";
    private final String APP_SECRET_PRODUCT = "e4c1065d66bd";
    private final String DEFAULT_CHARSET = "UTF-8";


    private final String registerURL = "https://api.netease.im/nimserver/user/create.action";
    private final String updateURL = "https://api.netease.im/nimserver/user/update.action";
    private final String blockURL = "https://api.netease.im/nimserver/user/block.action";
    private final String unblockURL = "https://api.netease.im/nimserver/user/unblock.action";

    @Override
    public boolean register(String account, String name, String token, String icon) {
        Map<String, String> param = new HashMap<>();
        param.put("accid", account);
        param.put("name", name);
        param.put("token", token);
        if (icon != null)
            param.put("icon", icon);
        String result = post(registerURL, param);
        RegisterResult registerResult = JsonUtils.jsonToPojo(result, RegisterResult.class);
        return registerResult != null && (registerResult.getCode() == 200 || registerResult.getCode() == 414);
    }

    public boolean updateToken(String account, String token) {
        Map<String, String> param = new HashMap<>();
        param.put("accid", account);
        param.put("token", token);
        String result = post(updateURL, param);
        RegisterResult registerResult = JsonUtils.jsonToPojo(result, RegisterResult.class);
        return registerResult != null && registerResult.getCode() == 200;
    }

    @Override
    public boolean disable(String account) {
        Map<String, String> param = new HashMap<>();
        param.put("accid", account);
        String result = post(blockURL, param);
        RegisterResult registerResult = JsonUtils.jsonToPojo(result, RegisterResult.class);
        return registerResult != null && registerResult.getCode() == 200;
    }

    @Override
    public boolean enable(String account) {
        Map<String, String> param = new HashMap<>();
        param.put("accid", account);
        String result = post(unblockURL, param);
        RegisterResult registerResult = JsonUtils.jsonToPojo(result, RegisterResult.class);
        return registerResult != null && registerResult.getCode() == 200;
    }

    private String post(String url, Map<String, String> param) {


        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(url);
        String nonce = Integer.toString(new Random(100000).nextInt());
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(getAppSecret(), nonce, curTime);//参考 计算CheckSum的java代码
        // 设置请求的header
        httpPost.addHeader("AppKey", getAppKey());
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 设置请求的参数


        CloseableHttpResponse response = null;
        String result = "";
        try {

            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, DEFAULT_CHARSET);
                httpPost.setEntity(entity);
            }
            response = httpClient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(result);
        return result;

    }

    private String getAppKey() {
        return product ? APP_KEY_PRODUCT : APP_KEY_TEST;
    }

    private String getAppSecret() {
        return product ? APP_SECRET_PRODUCT : APP_SECRET_TEST;
    }
}
