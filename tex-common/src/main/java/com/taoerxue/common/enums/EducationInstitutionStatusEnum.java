package com.taoerxue.common.enums;

/**
 * Created by lizhihui on 2017-03-16 15:28.
 */
public enum EducationInstitutionStatusEnum {

    //审核中
    AUDITING(1),

    //审核通过
    THROUGH_AUDIT(2),

    //审核失败
    AUDIT_FAILURE(3),

    //禁用
    DISABLED(4);

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    EducationInstitutionStatusEnum(int status) {
        this.status = status;
    }
}
