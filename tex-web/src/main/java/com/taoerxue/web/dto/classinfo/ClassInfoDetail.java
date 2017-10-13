package com.taoerxue.web.dto.classinfo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ClassInfoDetail {
    private Integer id;


    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date openClassDate;
    @JsonFormat(pattern = "HH:mm",timezone="GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "HH:mm",timezone="GMT+8")
    private Date endTime;


    private Integer number;

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
        this.name = name == null ? null : name.trim();
    }

    public Date getOpenClassDate() {
        return openClassDate;
    }

    public void setOpenClassDate(Date openClassDate) {
        this.openClassDate = openClassDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", openClassDate=").append(openClassDate);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", number=").append(number);
        sb.append("]");
        return sb.toString();
    }
}