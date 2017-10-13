package com.taoerxue.app.vo.institution;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by lizhihui on 2017-03-27 09:55.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EducationTypes {
    private Integer id;
    private String name;
    private List<EducationTypes> list;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EducationTypes> getList() {
        return list;
    }

    public void setList(List<EducationTypes> list) {
        this.list = list;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
