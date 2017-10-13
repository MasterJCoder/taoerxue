package com.taoerxue.dto;

/**
 * 我也不知道怎么描述
 * 就叫一个课程类型的详细信息,包含父类信息
 * Created by lizhihui on 2017-03-18 13:53.
 */
public class FullEducationType {
    private Integer parentTypeId;
    private String parentTypeName;
    private String typeName;
    private Integer typeId;

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
        this.parentTypeName = parentTypeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
}
