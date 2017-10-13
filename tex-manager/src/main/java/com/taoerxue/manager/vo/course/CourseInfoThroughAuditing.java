package com.taoerxue.manager.vo.course;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 通过审核的课程信息
 * Created by lizhihui on 2017-03-23 14:37.
 */

public class CourseInfoThroughAuditing {
    private Integer id;
    private String name;
    private String eName;
    private String parentTypeName;
    private String typeName;
    private BigDecimal price;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date throughTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getThroughTime() {
        return throughTime;
    }

    public void setThroughTime(Date throughTime) {
        this.throughTime = throughTime;
    }
}
