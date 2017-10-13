package com.taoerxue.vo;

/**
 * Created by lizhihui on 2017-03-17 13:36.
 */
public class EducationTypeWithStatus {
    private Integer id;
    private String name;
    private Boolean status;

    @Override
    public String toString() {
        return "EducationTypeWithStatus{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }

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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
