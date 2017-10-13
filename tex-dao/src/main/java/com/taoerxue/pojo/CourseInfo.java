package com.taoerxue.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class CourseInfo {
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

    private BigDecimal lng;

    private BigDecimal lat;

    private Date submitTime;

    private Date throughTime;

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

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
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
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", eId=").append(eId);
        sb.append(", eName=").append(eName);
        sb.append(", parentTypeId=").append(parentTypeId);
        sb.append(", parentTypeName=").append(parentTypeName);
        sb.append(", typeId=").append(typeId);
        sb.append(", typeName=").append(typeName);
        sb.append(", price=").append(price);
        sb.append(", count=").append(count);
        sb.append(", photo=").append(photo);
        sb.append(", target=").append(target);
        sb.append(", duration=").append(duration);
        sb.append(", studentLevelId=").append(studentLevelId);
        sb.append(", studentLevelName=").append(studentLevelName);
        sb.append(", studentTypeNames=").append(studentTypeNames);
        sb.append(", studentTypeIds=").append(studentTypeIds);
        sb.append(", characteristic=").append(characteristic);
        sb.append(", provinceId=").append(provinceId);
        sb.append(", provinceName=").append(provinceName);
        sb.append(", cityName=").append(cityName);
        sb.append(", cityId=").append(cityId);
        sb.append(", areaId=").append(areaId);
        sb.append(", areaName=").append(areaName);
        sb.append(", address=").append(address);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", status=").append(status);
        sb.append(", lng=").append(lng);
        sb.append(", lat=").append(lat);
        sb.append(", submitTime=").append(submitTime);
        sb.append(", throughTime=").append(throughTime);
        sb.append("]");
        return sb.toString();
    }
}