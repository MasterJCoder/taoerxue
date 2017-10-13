package com.taoerxue.common.constant;

/**
 * Created by lizhihui on 2017-03-14 10:59.
 * 课程信息常量
 */
public class CourseConstant {


    private CourseConstant() {

    }

    //课程名称最小长度
    public static final int NAME_MIN_LENGTH = 1;
    //课程名称最大程度
    public static final int NAME_MAX_LENGTH = 50;
    //课程最低价格
    public static final int MIN_PRICE = 1;
    //课程最高价格
    public static final int MAX_PRICE = 1000000;
    //课程展示图片最大尺寸
    public static final int PHOTO_MAX_SIZE = 200 * 1024;
    //课程最大节数
    public static final int MAX_COUNT = 999;
    //课程最小节数
    public static final int MIN_COUNT = 1;
    //学习目标最小字数
    public static final int TARGET_MIN_LENGTH = 1;
    //学习目标最大字数
    public static final int TARGET_MAX_LENGTH = 2000;
    //课程特色最小字数
    public static final int CHARACTERISTIC_MIN_LENGTH = 1;
    //课程特色最大字数
    public static final int CHARACTERISTIC_MAX_LENGTH = 2000;
    //课程每节最大时长
    public static final int MAX_LENGTH_PER_COURSE = 999;
    //课程每节最小时长
    public static final int MIN_LENGTH_PER_COURSE = 1;


    //课程名错误响应常量
    public static final String NAME_NULL_ERROR = "请输入课程名";
    public static final String NAME_LENGTH_ERROR = "课程名称的长度为" + NAME_MIN_LENGTH + "到" + NAME_MAX_LENGTH + "个字符";

    //课程节数错误响应常量
    public static final String COUNT_NULL_ERROR = "请输入课程节数";
    public static final String COUNT_NUMBER_ERROR = "课程节数的范围:" + MIN_COUNT + "到" + MAX_COUNT + "节";

    //课程图片错误响应常量
    public static final String PHOTO_NULL_ERROR = "请添加课程图片";
    public static final String PHOTO_NOT_EXISTS_ERROR = "添加的图片不存在";

    //学习目标错误响应常量
    public static final String TARGET_NULL_ERROR = "请输入课程的学习目标";
    public static final String TARGET_LENGTH_ERROR = "学习目标的长度位" + TARGET_MIN_LENGTH + "到" + TARGET_MAX_LENGTH + "个字符";

    //课程特色错误响应常量
    public static final String CHARACTERISTIC_NULL_ERROR = "请输入课程特色";
    public static final String CHARACTERISTIC_LENGTH_ERROR = "课程特色的长度位" + CHARACTERISTIC_MIN_LENGTH + "到" + CHARACTERISTIC_MAX_LENGTH + "个字符";

    //课程时长错误响应常量
    public static final String DURATION_NULL_ERROR = "请输入课程时长";
    public static final String DURATION_LENGTH_ERROR = "每节课的课程时长范围:" + MIN_LENGTH_PER_COURSE + "-" + MAX_LENGTH_PER_COURSE + "分钟";

    //课程价格错误响应常量
    public static final String PRICE_NULL_ERROR = "请输入价格";
    public static final String PRICE_RANGE_ERROR = "课程的价格范围:" + MIN_PRICE + "-" + MAX_PRICE + "元";

    //课程类型错误响应常量
    public static final String TYPE_NULL_ERROR = "请选择课程类型";
    public static final String TYPE_NOT_EXISTS_ERROR = "该课程类型不存在";

    //课程老师错误响应常量
    public static final String TEACHER_NULL_ERROR = "请添加授课教师";

    //学生类型错误响应常量
    public static final String STUDENT_TYPE_NULL_ERROR = "请选择招生对象";
    public static final String STUDENT_TYPE_ERROR = "招生对象类型错误";

    //学生基础响应错误常量
    public static final String STUDENT_LEVEL_NULL_ERROR = "请选择适合基础";
    public static final String STUDENT_LEVEL_ERROR = "适合基础类型不存在";

}
