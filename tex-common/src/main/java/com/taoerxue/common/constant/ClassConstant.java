package com.taoerxue.common.constant;

/**
 * Created by lizhihui on 2017-03-14 11:07.
 * 班级信息常量
 */
public class ClassConstant {
    //班级名称最大字符数
    public static final int NAME_MAX_LENGTH = 20;
    //班级名称最小字符数
    public static final int NAME_MIN_LENGTH = 1;
    //班级最大人数
    public static final int MAX_NUMBER_OF_PEOPLE = 999;
    //班级最小人数
    public static final int MIN_NUMBER_OF_PEOPLE = 1;


    public static final String ID_NULL_ERROR = "请选择班级";
    public static final String NOT_BELONG_ERROR = "该班级不属于这个机构";

    public static final String COURSE_ID_NULL_ERROR = "请选择你要添加班级的课程";
    public static final String NAME_NULL_ERROR = "请输入班级名";
    public static final String NAME_LENGTH_ERROR = "班级名的长度为" + NAME_MIN_LENGTH + "到" + NAME_MAX_LENGTH + "个字符";
    public static final String NUMBER_NULL_ERROR = "请输入班级人数";
    public static final String NUMBER_RANGE_ERROR = "班级人数的范围:" + MIN_NUMBER_OF_PEOPLE + "到" + MAX_NUMBER_OF_PEOPLE + "人";

    public static final String OPEN_DATE_ERROR = "开班时间不得早于当前时间";
    public static final String START_TIME_NULL_ERROR = "请输入课程开始时间段";
    public static final String END_TIME_NULL_ERROR = "请输入课程结束时间段";
    public static final String OPEN_DATE_NULL_ERROR = "请选择开班时间";
    public static final String TIME_FORMAT_ERROR = "时间格式错误,例子19:21";
    public static final String DATE_FORMAT_ERROR = "时间格式错误,例子2016-11-11";
    public static final String DATETIME_FORMAT_ERROR = "时间格式错误,例子2016-11-11 19:21";
}
