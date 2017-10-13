package com.taoerxue.manager.vo.institution;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by lizhihui on 2017-03-23 16:01.
 */
public class EducationInstitutionThroughAudit {
    private Integer id;
    private String name;
    private String companyName;
    private String typeName;
    private String address;
    private String managerName;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date updateTime;


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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
