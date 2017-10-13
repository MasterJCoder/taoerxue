package com.taoerxue.web.vo.course;

import java.math.BigDecimal;
import java.util.List;

/**
 * 课程详情
 * Created by lizhihui on 2017-03-22 10:45.
 */
public class CourseDetail {
    private Integer id;
    private String name;
    private BigDecimal price;
    private String parentTypeName;
    private String typeName;
    private String photo;
    private List<Integer> studentType;
    private Integer studentLevelId;
    private String studentLevelName;
    private Integer count;
    private Integer duration;
    private String target;
    private String characteristic;
    private List<TeacherInfo> teacherList;


    public static class TeacherInfo {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Integer> getStudentType() {
        return studentType;
    }

    public void setStudentType(List<Integer> studentType) {
        this.studentType = studentType;
    }

    public Integer getStudentLevelId() {
        return studentLevelId;
    }

    public void setStudentLevelId(Integer studentLevelId) {
        this.studentLevelId = studentLevelId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public List<TeacherInfo> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<TeacherInfo> teacherList) {
        this.teacherList = teacherList;
    }

    public String getStudentLevelName() {
        return studentLevelName;
    }

    public void setStudentLevelName(String studentLevelName) {
        this.studentLevelName = studentLevelName;
    }
}
