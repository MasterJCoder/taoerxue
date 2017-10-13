package com.taoerxue.common.enums;

/**
 * Created by lizhihui on 2017-03-16 15:28.
 */
public enum EducationUserStatusEnum {


    DISABLED(0),
    NO_SUBMIT(1),
    SUBMITTED(2),
    SUCCESS(3),
    FAILED(4);

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    EducationUserStatusEnum(int status) {
        this.status = status;
    }
}
