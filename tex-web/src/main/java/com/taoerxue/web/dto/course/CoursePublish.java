package com.taoerxue.web.dto.course;

import com.taoerxue.common.constant.CourseConstant;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by lizhihui on 2017-03-18 11:14.
 */
public class CoursePublish {


    //课程名
    @NotBlank(message = CourseConstant.NAME_NULL_ERROR)
    @Length(min = CourseConstant.NAME_MIN_LENGTH, max = CourseConstant.NAME_MAX_LENGTH, message = CourseConstant.NAME_LENGTH_ERROR)
    private String name;

    //课程节数
    @NotNull(message = CourseConstant.COUNT_NULL_ERROR)
    @Range(min = CourseConstant.MIN_COUNT, max = CourseConstant.MAX_COUNT, message = CourseConstant.COUNT_NUMBER_ERROR)
    private Integer count;

    //课程图片
    @NotBlank(message = CourseConstant.PHOTO_NULL_ERROR)
    private String photo;

    //学习目标
    @NotBlank(message = CourseConstant.TARGET_NULL_ERROR)
    @Length(min = CourseConstant.TARGET_MIN_LENGTH, max = CourseConstant.TARGET_MAX_LENGTH, message = CourseConstant.TARGET_LENGTH_ERROR)
    private String target;

    //课程特色
    @NotBlank(message = CourseConstant.CHARACTERISTIC_NULL_ERROR)
    @Length(min = CourseConstant.CHARACTERISTIC_MIN_LENGTH, max = CourseConstant.CHARACTERISTIC_MAX_LENGTH, message = CourseConstant.CHARACTERISTIC_LENGTH_ERROR)
    private String characteristic;

    //课程时长
    @NotNull(message = CourseConstant.DURATION_NULL_ERROR)
    @Range(min = CourseConstant.MIN_LENGTH_PER_COURSE, max = CourseConstant.MAX_LENGTH_PER_COURSE, message = CourseConstant.DURATION_LENGTH_ERROR)
    private Integer duration;

    //课程价格
    @NotNull(message = CourseConstant.PRICE_NULL_ERROR)
    @Range(min = CourseConstant.MIN_PRICE, max = CourseConstant.MAX_PRICE, message = CourseConstant.PRICE_RANGE_ERROR)
    private BigDecimal price;

    //课程类型
    @NotNull(message = CourseConstant.TYPE_NULL_ERROR)
    private Integer typeId;

   /* //教师
    @NotNull(message = CourseConstant.TEACHER_NULL_ERROR)
    private Integer[] teachers;

    //学生类型
    @NotNull(message = CourseConstant.STUDENT_TYPE_NULL_ERROR)
    private Integer[] studentType;*/

    //学生基础
    @NotNull(message = CourseConstant.STUDENT_LEVEL_NULL_ERROR)
    @Range(min = 1, max = 4, message = CourseConstant.STUDENT_LEVEL_ERROR)
    private Integer studentLevelId;


    public Integer getStudentLevelId() {
        return studentLevelId;
    }

    /* public Integer[] getStudentType() {
         return studentType;
     }

     public void setStudentType(Integer[] studentType) {
         this.studentType = studentType;
     }

     public Integer[] getTeachers() {
         return teachers;
     }

     public void setTeachers(Integer[] teachers) {
         this.teachers = teachers;
     }
 */

    public void setStudentLevelId(Integer studentLevelId) {
        this.studentLevelId = studentLevelId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
