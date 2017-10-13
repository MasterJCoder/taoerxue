package com.taoerxue.manager.vo.institution;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by lizhihui on 2017-03-27 09:55.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EducationTypesWithStatus {
    private Integer id;
    private String name;
    private Boolean status;
    private List<EducationTypesWithStatus> list;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EducationTypesWithStatus> getList() {
        return list;
    }

    public void setList(List<EducationTypesWithStatus> list) {
        this.list = list;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
