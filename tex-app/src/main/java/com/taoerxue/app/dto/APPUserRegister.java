package com.taoerxue.app.dto;

import com.taoerxue.common.constant.CommonConstant;
import com.taoerxue.dto.UserRegister;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by lizhihui on 2017-03-23 09:34.
 */
public class APPUserRegister extends UserRegister {
    @NotBlank(message = CommonConstant.VERIFICATION_CODE_NULL_ERROR)
    private String verificationCode;

//    @NotNull(message = "请选择您孩子的年龄段")
//    private Integer studentTypeId;


    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
