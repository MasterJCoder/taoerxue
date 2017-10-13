package com.taoerxue.web.dto.teacher;

import com.taoerxue.common.constant.TeacherConstant;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * 添加老师的 dto
 * Created by lizhihui on 2017-03-18 12:55.
 */
public class TeacherAdd {

    //教师姓名
    @NotBlank(message = TeacherConstant.NAME_NULL_ERROR)
    @Length(min = TeacherConstant.NAME_MIN_LENGTH, max = TeacherConstant.NAME_MAX_LENGTH, message = TeacherConstant.NAME_LENGTH_ERROR)
    private String name;

    //教龄
    @NotNull(message = TeacherConstant.EXPERIENCE_AGE_NULL_ERROR)
    @Max(value = TeacherConstant.EXPERIENCE_MAX_AGE, message = TeacherConstant.EXPERIENCE_AGE_RANGE_ERROR)
    private Integer experienceAge;

    //课程类型
    @NotNull(message = TeacherConstant.EDUCATION_TYPE_NULL_ERROR)
    private Integer typeId;

    //教师描述
    @NotBlank(message = TeacherConstant.DESCRIPTION_NULL_ERROR)
    @Length(min = TeacherConstant.DESCRIPTION_MIN_LENGTH, max = TeacherConstant.DESCRIPTION_MAX_LENGTH, message = TeacherConstant.DESCRIPTION_LENGTH_ERROR)
    private String description;

    //教师头像
    @NotBlank(message = TeacherConstant.PHOTO_NULL_ERROR)
    private String photo;

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
