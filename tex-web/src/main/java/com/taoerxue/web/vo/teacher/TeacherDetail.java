package com.taoerxue.web.vo.teacher;

import java.util.List;

/**
 * 教师管理功能中的教师详情
 * Created by lizhihui on 2017-03-18 16:11.
 */
public class TeacherDetail {
    private String name;
    private String typeName;
    private String photo;
    private Integer experienceAge;
    private String description;
    private String parentTypeName;
    private Integer typeId;
    private Integer parentTypeId;
    private List<course> courseList;


    public static class course {
        private String courseName;
        private String typeName;
        private String parentTypeName;

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getParentTypeName() {
            return parentTypeName;
        }

        public void setParentTypeName(String parentTypeName) {
            this.parentTypeName = parentTypeName;
        }
    }

    public String getParentTypeName() {
        return parentTypeName;
    }

    public void setParentTypeName(String parentTypeName) {
        this.parentTypeName = parentTypeName;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getParentTypeId() {
        return parentTypeId;
    }

    public void setParentTypeId(Integer parentTypeId) {
        this.parentTypeId = parentTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getExperienceAge() {
        return experienceAge;
    }

    public void setExperienceAge(Integer experienceAge) {
        this.experienceAge = experienceAge;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<course> courseList) {
        this.courseList = courseList;
    }
}
