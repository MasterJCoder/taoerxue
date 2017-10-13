package com.taoerxue.web.dto.classinfo;

import com.taoerxue.common.constant.ClassConstant;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class ClassInfoAdd {


    @NotNull(message = ClassConstant.COURSE_ID_NULL_ERROR)
    private Integer courseId;

    @NotBlank(message = ClassConstant.NAME_NULL_ERROR)
    @Length(min = ClassConstant.NAME_MIN_LENGTH, max = ClassConstant.NAME_MAX_LENGTH, message = ClassConstant.NAME_LENGTH_ERROR)
    private String name;


    @Future(message = ClassConstant.OPEN_DATE_ERROR)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = ClassConstant.OPEN_DATE_NULL_ERROR)
    private Date openClassDate;

    @DateTimeFormat(pattern = "HH:mm")
    @NotNull(message = ClassConstant.START_TIME_NULL_ERROR)
    private Date startTime;

    @DateTimeFormat(pattern = "HH:mm")
    @NotNull(message = ClassConstant.END_TIME_NULL_ERROR)
    private Date endTime;

    @NotNull(message = ClassConstant.NUMBER_NULL_ERROR)
    @Range(min = ClassConstant.MIN_NUMBER_OF_PEOPLE, max = ClassConstant.MAX_NUMBER_OF_PEOPLE, message = ClassConstant.NUMBER_RANGE_ERROR)
    private Integer number;


    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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