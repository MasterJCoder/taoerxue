package com.taoerxue.manager.dto;

import org.hibernate.validator.constraints.Length;

/**
 * Created by lizhihui on 2017-04-27 14:21.
 */
public class NewsEdit {

    private Integer id;
    private String title;

    private Integer typeId;
    @Length(min = 1, max = 10, message ="作者名字的长度位1-10个字符")
    private String author;

    private String url;
    private String photo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
