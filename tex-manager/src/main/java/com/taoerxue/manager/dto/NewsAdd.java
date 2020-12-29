package com.taoerxue.manager.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by lizhihui on 2017-04-27 14:21.
 */
public class NewsAdd {
    @NotBlank(message = "请输入文章标题")
    @Length(min = 1, max = 40, message = "文章标题的长度位1-40个字符")
    private String title;

    @NotNull(message = "请选择文章所属的板块")
    private Integer typeId;
    @NotBlank(message = "请输入文章的作者")
    @Length(min = 1, max = 10, message = "作者名字的长度位1-10个字符")
    private String author;

    @NotBlank(message = "请上传文章图片素材")
    private String photo;
    @NotBlank(message = "文章内容不能为空1")
    private String riches;

    public String getRiches() {
        return riches;
    }

    public void setRiches(String riches) {
        this.riches = riches;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
