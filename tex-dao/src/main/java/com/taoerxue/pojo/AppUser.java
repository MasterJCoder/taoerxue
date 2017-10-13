package com.taoerxue.pojo;

import java.util.Date;

public class AppUser {
    private Integer id;

    private String phone;

    private String password;

    private Date createTime;

    private Date updateTime;

    private String salt;

    private Integer studentTypeId;

    private String nickname;

    private String photo;

    private Integer expectation;

    private Date loginTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public Integer getStudentTypeId() {
        return studentTypeId;
    }

    public void setStudentTypeId(Integer studentTypeId) {
        this.studentTypeId = studentTypeId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    public Integer getExpectation() {
        return expectation;
    }

    public void setExpectation(Integer expectation) {
        this.expectation = expectation;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", phone=").append(phone);
        sb.append(", password=").append(password);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", salt=").append(salt);
        sb.append(", studentTypeId=").append(studentTypeId);
        sb.append(", nickname=").append(nickname);
        sb.append(", photo=").append(photo);
        sb.append(", expectation=").append(expectation);
        sb.append(", loginTime=").append(loginTime);
        sb.append("]");
        return sb.toString();
    }
}