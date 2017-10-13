package com.taoerxue.manager.vo.course;

import java.util.Date;

/**
 * 正在审核的课程信息
 * Created by lizhihui on 2017-03-23 14:37.
 */
public class CourseInfoNewest {
    private String name;
    private String eName;
    private String parentTypeName;
    private String typeName;
    private Date submitTime;

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public String getParentTypeName() {
        return parentTypeName;
    }

    public void setParentTypeName(String parentTypeName) {
        this.parentTypeName = parentTypeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

}
