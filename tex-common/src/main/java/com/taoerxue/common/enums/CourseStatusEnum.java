package com.taoerxue.common.enums;

/**
 * Created by lizhihui on 2017-03-23 12:49.
 */
public enum CourseStatusEnum {
    //不存在
    NOT_EXITS(-1),

    //待上架
    NOT_SUBMIT(0),

    //审核中
    AUDITING(1),

    //审核通过
    THROUGH_AUDIT(2),

    //删除
    DELETE(3);


    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    CourseStatusEnum(int status) {
        this.status = status;
    }
}
