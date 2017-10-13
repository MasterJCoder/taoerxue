package com.taoerxue.po;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;


@Document(collection = "courseInfo")
public class CourseInfoMongoPOJO {

    @Id
    private Integer id;

    private String name;

    private Integer eId;

    private String eName;

    private Integer parentTypeId;

    private String parentTypeName;

    private Integer typeId;

    private String typeName;

    private BigDecimal price;

    private Integer count;

    private String photo;

    private String target;

    private Integer duration;

    private Integer studentLevelId;

    private String studentLevelName;

    private String studentTypeNames;

    private Integer studentTypeIds;

    private String characteristic;

    private Integer provinceId;

    private String provinceName;

    private String cityName;

    private Integer cityId;

    private Integer areaId;

    private String areaName;

    private String address;

    private Date updateTime;

    private Date createTime;

    private Integer status;

    private double[] location;

    private Date submitTime;

    private Date throughTime;

    private Integer collections;

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }

    public Integer getCollections() {
        return collections;
    }

    public void setCollections(Integer collections) {
        this.collections = collections;
    }

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
        this.name = name == null ? null : name.trim();
    }

    public Integer geteId() {
        return eId;
    }

    public void seteId(Integer eId) {
        this.eId = eId;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName == null ? null : eName.trim();
    }

    public Integer getParentTypeId() {
        return parentTypeId;
    }

    public void setParentTypeId(Integer parentTypeId) {
        this.parentTypeId = parentTypeId;
    }

    public String getParentTypeName() {
        return parentTypeName;
    }

    public void setParentTypeName(String parentTypeName) {
        this.parentTypeName = parentTypeName == null ? null : parentTypeName.trim();
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
        this.photo = photo == null ? null : photo.trim();
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target == null ? null : target.trim();
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getStudentLevelId() {
        return studentLevelId;
    }

    public void setStudentLevelId(Integer studentLevelId) {
        this.studentLevelId = studentLevelId;
    }

    public String getStudentLevelName() {
        return studentLevelName;
    }

    public void setStudentLevelName(String studentLevelName) {
        this.studentLevelName = studentLevelName == null ? null : studentLevelName.trim();
    }

    public String getStudentTypeNames() {
        return studentTypeNames;
    }

    public void setStudentTypeNames(String studentTypeNames) {
        this.studentTypeNames = studentTypeNames == null ? null : studentTypeNames.trim();
    }

    public Integer getStudentTypeIds() {
        return studentTypeIds;
    }

    public void setStudentTypeIds(Integer studentTypeIds) {
        this.studentTypeIds = studentTypeIds;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic == null ? null : characteristic.trim();
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName == null ? null : provinceName.trim();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Date getThroughTime() {
        return throughTime;
    }

    public void setThroughTime(Date throughTime) {
        this.throughTime = throughTime;
    }

    @Override
    public String toString() {
        return "CourseInfoMongoPOJO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", eId=" + eId +
                ", eName='" + eName + '\'' +
                ", parentTypeId=" + parentTypeId +
                ", parentTypeName='" + parentTypeName + '\'' +
                ", typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", photo='" + photo + '\'' +
                ", target='" + target + '\'' +
                ", duration=" + duration +
                ", studentLevelId=" + studentLevelId +
                ", studentLevelName='" + studentLevelName + '\'' +
                ", studentTypeNames='" + studentTypeNames + '\'' +
                ", studentTypeIds=" + studentTypeIds +
                ", characteristic='" + characteristic + '\'' +
                ", provinceId=" + provinceId +
                ", provinceName='" + provinceName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", cityId=" + cityId +
                ", areaId=" + areaId +
                ", areaName='" + areaName + '\'' +
                ", address='" + address + '\'' +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", status=" + status +
                ", location=" + Arrays.toString(location) +
                ", submitTime=" + submitTime +
                ", throughTime=" + throughTime +
                ", collections=" + collections +
                '}';
    }
}