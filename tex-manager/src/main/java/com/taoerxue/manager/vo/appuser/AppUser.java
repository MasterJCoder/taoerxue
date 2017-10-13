package com.taoerxue.manager.vo.appuser;

import java.util.Date;

/**
 * Created by lizhihui on 2017-03-23 16:23.
 */
public class AppUser {
    private String nickname;
    private String studentTypeName;
    private String expectation;
    private String phone;
    private Date createdTime;
    private Date loginTime;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getStudentTypeName() {
        return studentTypeName;
    }

    public void setStudentTypeName(String studentTypeName) {
        this.studentTypeName = studentTypeName;
    }

    public String getExpectation() {
        return expectation;
    }

    public void setExpectation(String expectation) {
        this.expectation = expectation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}
