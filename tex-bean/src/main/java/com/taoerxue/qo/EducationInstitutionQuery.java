package com.taoerxue.qo;

/**
 * Created by lizhihui on 2017-04-20 16:26.
 */
public class EducationInstitutionQuery extends Query {

    private Integer areaId;
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }
}
