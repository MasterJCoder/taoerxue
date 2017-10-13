package com.taoerxue.web.dto.institution;

import com.taoerxue.common.constant.CommonConstant;
import com.taoerxue.common.constant.RegexConstant;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * Created by lizhihui on 2017-03-14 12:35.
 */
public class EducationUserAdd {

    @NotBlank(message = "请输入账户名")
    @Length(min = 1, max = 10, message = "账户名的长度位1-10个字符")
    private String alias;

    @NotBlank(message = CommonConstant.PLEASE_INPUT_PHONE)
    @Pattern(regexp = RegexConstant.PHONE, message = CommonConstant.PHONE_FORMAT_ERROR)
    private String phone;

    @NotBlank(message = CommonConstant.PASSWORD_NULL_ERROR)
    @Pattern(regexp = RegexConstant.PASSWORD, message = CommonConstant.PASSWORD_FORMAT_ERROR)
    private String password;

  /*  @NotBlank(message = "缺少添加凭证参数")
    private String addToken;*/

    @NotBlank(message = CommonConstant.VERIFICATION_CODE_NULL_ERROR)
    private String verificationCode;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

 /*   public String getAddToken() {
        return addToken;
    }

    public void setAddToken(String addToken) {
        this.addToken = addToken;
    }*/

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
