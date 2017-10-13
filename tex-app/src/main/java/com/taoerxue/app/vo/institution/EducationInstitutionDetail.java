package com.taoerxue.app.vo.institution;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lizhihui on 2017-03-23 11:07.
 */
public class EducationInstitutionDetail {
    private Integer id;
    private List<String> photoList;
    private String name;
    private String typeName;
    private String distance;
    private String telephone;
    private String address;
    private String description;
    private List<Course> courseList;
    private long courseCount;
    private List<Teacher> teacherList;
    private long teacherCount;
    private Boolean doesCollect;

    public Boolean getDoesCollect() {
        return doesCollect;
    }

    public void setDoesCollect(Boolean doesCollect) {
        this.doesCollect = doesCollect;
    }

    public static class Teacher {
        private Integer id;
        private String photo;
        private String name;
        private Integer experienceAge;
        private String parentTypeName;
        private String typeName;

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

        public Integer getExperienceAge() {
            return experienceAge;
        }

        public void setExperienceAge(Integer experienceAge) {
            this.experienceAge = experienceAge;
        }

        public String getParentTypeName() {
            return parentTypeName;
        }

        public void setParentTypeName(String parentTypeName) {
            this.parentTypeName = parentTypeName;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }
    }

    public static class Course {
        private Integer id;
        private String name;
        private String parentTypeName;
        private String typeName;
        private BigDecimal price;
        private String photo;
        private Integer collections;

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
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

        public String getParentTypeName() {
            return parentTypeName;
        }

        public void setParentTypeName(String parentTypeName) {
            this.parentTypeName = parentTypeName;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public Integer getCollections() {
            return collections;
        }

        public void setCollections(Integer collections) {
            this.collections = collections;
        }
    }

    public long getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(long courseCount) {
        this.courseCount = courseCount;
    }

    public long getTeacherCount() {
        return teacherCount;
    }

    public void setTeacherCount(long teacherCount) {
        this.teacherCount = teacherCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<String> photoList) {
        this.photoList = photoList;
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Teacher> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }
}
