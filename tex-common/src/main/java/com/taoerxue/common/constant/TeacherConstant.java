package com.taoerxue.common.constant;

/**
 * 机构教师常量类
 * Created by lizhihui on 2017-03-18 13:00.
 */
public class TeacherConstant {

    //教师姓名最小长度
    public static final int NAME_MIN_LENGTH = 2;
    //教师姓名最大长度
    public static final int NAME_MAX_LENGTH = 10;
    //教师描述最低长度
    public static final int DESCRIPTION_MIN_LENGTH = 1;
    //教师描述最大长度
    public static final int DESCRIPTION_MAX_LENGTH = 120;
    //教师教龄最大
    public static final int EXPERIENCE_MAX_AGE = 100;

    //教师姓名错误响应产量
    public static final String NAME_NULL_ERROR = "请输入教师名字";
    public static final String NAME_LENGTH_ERROR = "教师姓名长度为" + NAME_MIN_LENGTH + "-" + NAME_MAX_LENGTH + "个字";

    //教师描述错误响应常量
    public static final String DESCRIPTION_NULL_ERROR = "请输入教师简介";
    public static final String DESCRIPTION_LENGTH_ERROR = "教师简介的长度为" + DESCRIPTION_MIN_LENGTH + "-" + DESCRIPTION_MAX_LENGTH + "个字";

    //教师教龄错误响应常量
    public static final String EXPERIENCE_AGE_NULL_ERROR = "请输入教师教龄";
    public static final String EXPERIENCE_AGE_RANGE_ERROR = "教师教龄有误,请认真填写";

    //教师授课类型错误响应常量
    public static final String EDUCATION_TYPE_NULL_ERROR = "请输入教师授课类型";
    public static final String EDUCATION_TYPE_ERROR = "教师授课类型不存在";

    //教师照片错误响应常量
    public static final String PHOTO_NULL_ERROR = "请上传教师图片";
}
