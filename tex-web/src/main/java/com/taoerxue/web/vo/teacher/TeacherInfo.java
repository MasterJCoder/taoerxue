package com.taoerxue.web.vo.teacher;

/**
 * Created by lizhihui on 2017-03-23 17:11.
 */
public class TeacherInfo {
    private Integer id;
    private String name;
    private Integer experienceAge;
    private String photo;
    private String typeName;


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

    public Integer getExperienceAge() {
        return experienceAge;
    }

    public void setExperienceAge(Integer experienceAge) {
        this.experienceAge = experienceAge;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
