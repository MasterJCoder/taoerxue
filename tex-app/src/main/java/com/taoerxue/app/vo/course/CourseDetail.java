package com.taoerxue.app.vo.course;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by lizhihui on 2017-03-29 15:15.
 */
public class CourseDetail {
    private Integer id;
    private List<String> photoList;
    private String name;
    private Integer eId;
    private List<Integer> studentType;
    private String studentLevelName;
    private Integer count;
    private Integer duration;
    private String target;
    private String characteristic;
    private BigDecimal price;
    private String eName;
    private String address;
    private List<ClassInfo> classList;
    private List<Teacher> teacherList;
    private Long teacherCount;
    private Boolean doesCollect;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getDoesCollect() {
        return doesCollect;
    }

    public void setDoesCollect(Boolean doesCollect) {
        this.doesCollect = doesCollect;
    }

    public Long getTeacherCount() {
        return teacherCount;
    }

    public void setTeacherCount(Long teacherCount) {
        this.teacherCount = teacherCount;
    }

    public static class ClassInfo {
        private String name;

        @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
        private Date openClassDate;

        @JsonFormat(pattern = "HH:mm",timezone="GMT+8")
        private Date startTime;

        @JsonFormat(pattern = "HH:mm",timezone="GMT+8")
        private Date endTime;


        private Integer number;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getOpenClassDate() {
            return openClassDate;
        }

        public void setOpenClassDate(Date openClassDate) {
            this.openClassDate = openClassDate;
        }

        public Date getStartTime() {
            return startTime;
        }

        public void setStartTime(Date startTime) {
            this.startTime = startTime;
        }

        public Date getEndTime() {
            return endTime;
        }

        public void setEndTime(Date endTime) {
            this.endTime = endTime;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }
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


    public List<String> getPhotoList() {
        return photoList;
    }

    public List<ClassInfo> getClassList() {
        return classList;
    }

    public void setClassList(List<ClassInfo> classList) {
        this.classList = classList;
    }

    public List<Teacher> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<Teacher> teacherList) {
        this.teacherList = teacherList;
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

    public List<Integer> getStudentType() {
        return studentType;
    }

    public void setStudentType(List<Integer> studentType) {
        this.studentType = studentType;
    }

    public String getStudentLevelName() {
        return studentLevelName;
    }

    public void setStudentLevelName(String studentLevelName) {
        this.studentLevelName = studentLevelName;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public String getAddress() {
        return address;
    }

    public Integer geteId() {
        return eId;
    }

    public void seteId(Integer eId) {
        this.eId = eId;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
