package com.taoerxue.app.vo.institution;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by lizhihui on 2017-03-23 11:07.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EducationInstitutionSimple {
    private Integer id;
    private String photo;
    private String name;
    private String distance;
    private String address;
    private String typeName;
    private Integer collections;


    public Integer getCollections() {
        return collections;
    }

    public void setCollections(Integer collections) {
        this.collections = collections;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


}
