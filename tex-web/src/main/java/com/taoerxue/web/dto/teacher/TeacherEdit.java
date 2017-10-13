package com.taoerxue.web.dto.teacher;

/**
 * 添加老师的 dto
 * Created by lizhihui on 2017-03-18 12:55.
 */
public class TeacherEdit {
    //教师 id
    private Integer id;

    //教师姓名
    private String name;

    //教龄
    private Integer experienceAge;

    //课程类型
    private Integer typeId;

    //教师描述
    private String description;

    //教师头像
    private String photo;

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

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
