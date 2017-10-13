package com.taoerxue.web.dto.classinfo;

import com.taoerxue.common.constant.ClassConstant;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by lizhihui on 2017-03-24 10:25.
 */
public class ClassInfoEdit {

    @NotNull(message = ClassConstant.ID_NULL_ERROR)
    private Integer id;

    @Length(min = ClassConstant.NAME_MIN_LENGTH, max = ClassConstant.NAME_MAX_LENGTH, message = ClassConstant.NAME_LENGTH_ERROR)
    private String name;


    @Future(message = ClassConstant.OPEN_DATE_ERROR)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date openClassDate;

    @DateTimeFormat(pattern = "HH:mm")
    private Date startTime;
    @DateTimeFormat(pattern = "HH:mm")
    private Date endTime;

    @Range(min = ClassConstant.MIN_NUMBER_OF_PEOPLE, max = ClassConstant.MAX_NUMBER_OF_PEOPLE, message = ClassConstant.NUMBER_RANGE_ERROR)
    private Integer number;

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

   /* public boolean isNull() {
        return StringUtils.isEmpty(name)&&openClassDate==null&&
    }*/
}
