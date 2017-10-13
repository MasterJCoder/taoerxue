package com.taoerxue.web.dto.institution;

import com.taoerxue.common.constant.EducationInstitutionConstant;
import com.taoerxue.common.constant.RegexConstant;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 机构注册传输类
 * Created by lizhihui on 2017-03-14 12:35.
 */
public class EducationInstitutionRegister {

    /*//手机号
    @NotBlank(message = CommonConstant.PHONE_NULL_ERROR)
    @Pattern(regexp = RegexConstant.PHONE, message = CommonConstant.PHONE_FORMAT_ERROR)
    private String phone;*/

    //机构名称
    @Length(min = EducationInstitutionConstant.NAME_MIN_LENGTH, max = EducationInstitutionConstant.NAME_MAX_LENGTH, message = EducationInstitutionConstant.NAME_LENGTH_ERROR)
    @NotBlank(message = EducationInstitutionConstant.NAME_NULL_ERROR)
    private String name;

    //机构课程类型
    @NotNull(message = EducationInstitutionConstant.EDUCATION_TYPE_NULL_ERROR)
    private Integer typeId;

    //机构地址
    @NotBlank(message = EducationInstitutionConstant.ADDRESS_NULL_ERROR)
    @Length(min = EducationInstitutionConstant.ADDRESS_MIN_LENGTH, max = EducationInstitutionConstant.ADDRESS_MAX_LENGTH, message = EducationInstitutionConstant.ADDRESS_LENGTH_ERROR)
    private String address;

    //机构联系方式
    @NotBlank(message = EducationInstitutionConstant.TELEPHONE_NULL_ERROR)
    private String telephone;

    //机构简介
    @NotBlank(message = EducationInstitutionConstant.DESCRIPTION_NULL_ERROR)
    private String description;

    //企业名称
    @NotBlank(message = EducationInstitutionConstant.COMPANY_NAME_NULL_ERROR)
    @Length(min = EducationInstitutionConstant.COMPANY_NAME_MIN_LENGTH, max = EducationInstitutionConstant.COMPANY_NAME_MAX_LENGTH, message = EducationInstitutionConstant.COMPANY_NAME_LENGTH_ERROR)
    private String companyName;

    //营业执照
    @NotBlank(message = EducationInstitutionConstant.LICENSE_NUMBER_NULL_ERROR)
    @Pattern(regexp = RegexConstant.LICENSE_NUMBER, message = EducationInstitutionConstant.LICENSE_NUMBER_LENGTH_ERROR)
    private String licenseNumber;

    //管理者姓名
    @NotBlank(message = EducationInstitutionConstant.MANAGER_NAME_NULL_ERROR)
    @Length(min = EducationInstitutionConstant.MANAGER_NAME_MIN_LENGTH, max = EducationInstitutionConstant.MANAGER_NAME_MAX_LENGTH, message = EducationInstitutionConstant.MANAGER_NAME_LENGTH_ERROR)
    private String managerName;

    //管理者居住地
    @NotBlank(message = EducationInstitutionConstant.MANAGER_ADDRESS_NULL_ERROR)
    @Length(min = EducationInstitutionConstant.MANAGER_ADDRESS_MIN_LENGTH, max = EducationInstitutionConstant.MANAGER_ADDRESS_MAX_LENGTH, message = EducationInstitutionConstant.MANAGER_ADDRESS_LENGTH_ERROR)
    private String managerAddress;

    //管理者联系电话
    @NotBlank(message = EducationInstitutionConstant.MANAGER_PHONE_NULL_ERROR)
    @Pattern(regexp = RegexConstant.PHONE, message = EducationInstitutionConstant.MANAGER_PHONE_FORMAT_ERROR)
    private String managerPhone;

    //机构图片
    @NotBlank(message = EducationInstitutionConstant.PHOTO_NULL_ERROR)
    private String photo;

    /*  public String getPhone() {
          return phone;
      }

      public void setPhone(String phone) {
          this.phone = phone;
      }*/

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerAddress() {
        return managerAddress;
    }

    public void setManagerAddress(String managerAddress) {
        this.managerAddress = managerAddress;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

}
