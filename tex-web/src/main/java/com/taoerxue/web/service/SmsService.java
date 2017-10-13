package com.taoerxue.web.service;

import com.taoerxue.common.bean.Result;
import com.taoerxue.common.cache.redis.JedisClient;
import com.taoerxue.common.sms.SMSPlatform;
import com.taoerxue.util.RandomUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class SmsService {
    @Resource
    private JedisClient jedisClient;
    @Resource
    private SMSPlatform smsPlatform;
    @Value("${PRODUCT}")
    Boolean PRODUCT;

    public boolean checkVerificationCode(String phone, String verify) {
        String code = jedisClient.get("verificationCode:" + phone);
        return !(code == null || !code.equals(verify));
    }

    public Result sendVerificationCode(String phone) {
        String verificationCode = RandomUtils.generateNumberString(6);
        if (!PRODUCT)
            verificationCode = RandomUtils.generateZeroString(6);
        String newContent = "您的验证码为：" + verificationCode + "，请在5分钟内使用。";
        jedisClient.setAndExpire("verificationCode:" + phone, verificationCode, 5, TimeUnit.MINUTES);
        if (!PRODUCT)
            return Result.build(200, "发送成功");
        int code = smsPlatform.send(phone, newContent);
        if (code == 200)
            return Result.build(200, "发送成功");

        return Result.build(500, smsPlatform.getSendResult(code));
    }

    public void deleteVerificationCode(String phone) {
        jedisClient.del("verificationCode:" + phone);
    }
}
