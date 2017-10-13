package com.taoerxue.qo;

/**
 * Created by lizhihui on 2017-04-27 14:13.
 */
public class NewsQuery {
    private Integer typeId;
    private String like;

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
}
