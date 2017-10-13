package com.taoerxue.app.vo.institution;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lizhihui on 2017-03-23 11:07.
 */
public class EducationInstitutionRecommend {
    private Integer id;
    private String photo;
    private String name;
    private String distance;
    private String address;
    private String typeName;
    private List<Course> courseList;
    private long courseCount;
    private Integer collections;


    public static class Course {
        private Integer id;
        private String name;
        private BigDecimal price;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public Integer getCollections() {
        return collections;
    }

    public void setCollections(Integer collections) {
        this.collections = collections;
    }


    public long getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(long courseCount) {
        this.courseCount = courseCount;
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
