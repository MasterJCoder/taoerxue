package com.taoerxue.manager.vo.course;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by lizhihui on 2017-03-23 14:38.
 */
public class CourseDetail {
    private String photo;
    private String name;
    private String parentTypeName;
    private String typeName;
    private String studentLevelName;
    private String studentTypeNames;
    private List<String> teacherList;
    private Integer count;
    private BigDecimal price;
    private Integer duration;
    private String target;
    private String characteristic;
    private List<ClassInfo> classList;
    private EducationInfo educationInfo;


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

    public static class EducationInfo {
        private String name;
        private String typeName;
        private String address;
        private String managerName;
        private String managerPhone;

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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getManagerName() {
            return managerName;
        }

        public void setManagerName(String managerName) {
            this.managerName = managerName;
        }

        public String getManagerPhone() {
            return managerPhone;
        }

        public void setManagerPhone(String managerPhone) {
            this.managerPhone = managerPhone;
        }
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

    public String getStudentLevelName() {
        return studentLevelName;
    }

    public void setStudentLevelName(String studentLevelName) {
        this.studentLevelName = studentLevelName;
    }

    public String getStudentTypeNames() {
        return studentTypeNames;
    }

    public void setStudentTypeNames(String studentTypeNames) {
        this.studentTypeNames = studentTypeNames;
    }

    public List<String> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<String> teacherList) {
        this.teacherList = teacherList;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public List<ClassInfo> getClassList() {
        return classList;
    }

    public void setClassList(List<ClassInfo> classList) {
        this.classList = classList;
    }

    public EducationInfo getEducationInfo() {
        return educationInfo;
    }

    public void setEducationInfo(EducationInfo educationInfo) {
        this.educationInfo = educationInfo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
