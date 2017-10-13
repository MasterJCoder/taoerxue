package com.taoerxue.pojo;

import java.util.Date;

public class Teacher {
    private Integer id;

    private Integer eId;

    private String name;

    private Integer experienceAge;

    private Integer typeId;

    private String typeName;

    private Integer parentTypeId;

    private String parentTypeName;

    private String description;

    private String photo;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer geteId() {
        return eId;
    }

    public void seteId(Integer eId) {
        this.eId = eId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", eId=").append(eId);
        sb.append(", name=").append(name);
        sb.append(", experienceAge=").append(experienceAge);
        sb.append(", typeId=").append(typeId);
        sb.append(", typeName=").append(typeName);
        sb.append(", parentTypeId=").append(parentTypeId);
        sb.append(", parentTypeName=").append(parentTypeName);
        sb.append(", description=").append(description);
        sb.append(", photo=").append(photo);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", status=").append(status);
        sb.append("]");
        return sb.toString();
    }
}