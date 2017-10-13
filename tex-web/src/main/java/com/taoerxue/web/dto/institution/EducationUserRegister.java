package com.taoerxue.web.dto.institution;

import com.taoerxue.common.constant.CommonConstant;
import com.taoerxue.dto.UserRegister;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by lizhihui on 2017-03-14 12:35.
 */
public class EducationUserRegister extends UserRegister {
    @NotBlank(message = CommonConstant.VERIFICATION_CODE_NULL_ERROR)
    private String verificationCode;

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
