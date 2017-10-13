package com.taoerxue.web.vo.course;

import java.util.Date;

/**
 * Created by lizhihui on 2017-04-11 14:16.
 */
public class CourseThroughAudit {
    private Integer id;
    private String name;
    private String parentTypeName;
    private String typeName;
    private Double price;
    private Integer count;
    private Integer collections;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCollections() {
        return collections;
    }

    public void setCollections(Integer collections) {
        this.collections = collections;
    }

    public Date getThroughTime() {
        return throughTime;
    }

    public void setThroughTime(Date throughTime) {
        this.throughTime = throughTime;
    }
}
