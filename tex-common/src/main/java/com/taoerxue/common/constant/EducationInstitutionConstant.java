package com.taoerxue.common.constant;

/**
 * 教育机构的常量
 * Created by lizhihui on 2017-03-15 14:17.
 */
public class EducationInstitutionConstant {

    /**
     * 机构参数常量
     */

    //机构名称长度常亮
    public static final int NAME_MIN_LENGTH = 1;
    public static final int NAME_MAX_LENGTH = 50;

    //机构所属企业名称长度常量
    public static final int COMPANY_NAME_MAX_LENGTH = 50;
    public static final int COMPANY_NAME_MIN_LENGTH = 1;

    //机构管理者名字长度常亮
    public static final int MANAGER_NAME_MIN_LENGTH = 2;
    public static final int MANAGER_NAME_MAX_LENGTH = 10;

    //机构管理者居住地地址长度常量
    public static final int MANAGER_ADDRESS_MIN_LENGTH = 1;
    public static final int MANAGER_ADDRESS_MAX_LENGTH = 100;

    //机构地址长度常量
    public static final int ADDRESS_MIN_LENGTH = 1;
    public static final int ADDRESS_MAX_LENGTH = 100;

    //图片大小常量
    public static final int PHOTO_MAX_SIZE = 500 * 1024;

    //机构简介长度常量
    public static final int DESCRIPTION_MIN_LENGTH = 1;
    public static final int DESCRIPTION_MAX_LENGTH = 10000;

    /**
     * 机构错误信息响应常量
     */

    //机构名称错误响应常量
    public static final String NAME_NULL_ERROR = "请输入机构名称";
    public static final String NAME_LENGTH_ERROR = "机构名称的长度为" + NAME_MIN_LENGTH + "到" + NAME_MAX_LENGTH + "个字符";

    //机构所属企业错误响应常量
    public static final String COMPANY_NAME_NULL_ERROR = "请输入企业名称";
    public static final String COMPANY_NAME_LENGTH_ERROR = "企业名称的长度为" + COMPANY_NAME_MIN_LENGTH + "到" + COMPANY_NAME_MAX_LENGTH + "个字符";

    //机构课程类型错误响应常量
    public static final String EDUCATION_TYPE_NULL_ERROR = "请选择课程类型";

    //机构地址错误响应常量
    public static final String ADDRESS_NULL_ERROR = "请输入机构具体地址";
    public static final String ADDRESS_LENGTH_ERROR = " 机构地址的长度为" + ADDRESS_MIN_LENGTH + "到" + ADDRESS_MAX_LENGTH + "个字符";

    //机构联系方式错误响应常量
    public static final String TELEPHONE_NULL_ERROR = "请输入机构的联系方式";

    //机构营业执照错误响应常量
    public static final String LICENSE_NUMBER_NULL_ERROR = "输入营业执照号码或统一社会信用代码";
    public static final String LICENSE_NUMBER_LENGTH_ERROR = "营业执照号码或统一社会信用代码的长度为15到18位";

    //机构管理者姓名错误响应常量
    public static final String MANAGER_NAME_NULL_ERROR = "请输入管理者姓名";
    public static final String MANAGER_NAME_LENGTH_ERROR = "管理者的姓名长度为" + MANAGER_NAME_MIN_LENGTH + "到" + MANAGER_NAME_MAX_LENGTH + "个字符";

    //机构管理者现居住地错误响应常量
    public static final String MANAGER_ADDRESS_NULL_ERROR = "请输入管理者现居住地";
    public static final String MANAGER_ADDRESS_LENGTH_ERROR = "地址的长度为" + MANAGER_ADDRESS_MIN_LENGTH + "到" + MANAGER_ADDRESS_MAX_LENGTH + "个字符";

    //机构管理者手机号码错误响应常量
    public static final String MANAGER_PHONE_NULL_ERROR = "请输入管理者的手机号码";
    public static final String MANAGER_PHONE_FORMAT_ERROR = "管理者手机号码格式错误";

    //机构图片错误响应常量
    public static final String PHOTO_NULL_ERROR = "请上传机构图片";
    public static final String PHOTO_SIZE_ERROR = "机构图片大小不能超过500KB";

    //机构简介错误响应常量
    public static final String DESCRIPTION_NULL_ERROR = " 请输入机构简介信息";
    public static final String DESCRIPTION_LENGTH_ERROR = "机构信息的长度为" + DESCRIPTION_MIN_LENGTH + "到" + DESCRIPTION_MAX_LENGTH + "个字符";

}
