package com.taoerxue.vo;

/**
 * Created by lizhihui on 2017-03-17 13:36.
 */
public class EducationTypeWithoutStatus {
    private Integer id;
    private String name;

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

    @Override
    public String toString() {
        return "EducationTypeWithoutStatus{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
