package com.taoerxue.qo;

/**
 * Created by lizhihui on 2017-04-20 16:26.
 */
public class CourseInfoQuery extends Query {

    private Integer parentTypeId;
    private Integer status;
    private Integer eid;

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getParentTypeId() {
        return parentTypeId;
    }

    public void setParentTypeId(Integer parentTypeId) {
        this.parentTypeId = parentTypeId;
    }
}
