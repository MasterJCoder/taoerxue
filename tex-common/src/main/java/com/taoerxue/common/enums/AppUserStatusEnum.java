package com.taoerxue.common.enums;

/**
 * Created by lizhihui on 2017-03-23 10:41.
 */
public enum AppUserStatusEnum {
    //正常
    NORMAL(1),

    //未提交孩子信息
    NO_SUBMIT_CHILDREN_INFO(0);


    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    AppUserStatusEnum(int status) {
        this.status = status;
    }
    }
