package com.taoerxue.dto;

import com.taoerxue.common.constant.CommonConstant;
import com.taoerxue.common.constant.RegexConstant;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * Created by lizhihui on 2017-03-14 12:56.
 */
public class UserRegister {

    @NotBlank(message = CommonConstant.PHONE_NULL_ERROR)
    @Pattern(regexp = RegexConstant.PHONE, message = CommonConstant.PHONE_FORMAT_ERROR)
    private String phone;
    @NotBlank(message = CommonConstant.PASSWORD_NULL_ERROR)
    @Pattern(regexp = RegexConstant.PASSWORD,message = CommonConstant.PASSWORD_FORMAT_ERROR)
    private String password;

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



    //([a-zA-Z]+[0-9]+|[0-9]+[a-zA-Z]+)

    //              aA12a   a34567a8 a1234 12345

    // A1bstractCollection
}
