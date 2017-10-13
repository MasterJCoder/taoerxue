package com.taoerxue.app.vo.user;

import java.util.List;

/**
 * Created by lizhihui on 2017-03-30 11:19.
 */
public class UserInfo {
    private String photo;
    private String nickname;
    private String studentTypeName;
    private List<Integer> expectations;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

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

    public List<Integer> getExpectations() {
        return expectations;
    }

    public void setExpectations(List<Integer> expectations) {
        this.expectations = expectations;
    }
}
