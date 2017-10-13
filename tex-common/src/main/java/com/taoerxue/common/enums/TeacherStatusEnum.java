package com.taoerxue.common.enums;

/**
 * Created by lizhihui on 2017-03-18 16:05.
 */
public enum  TeacherStatusEnum {
    DELETE(0),
    NORMAL(1),
    DISABLED(2);
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    TeacherStatusEnum(int status) {
        this.status = status;
    }
}
