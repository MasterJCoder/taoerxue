package com.taoerxue.mapper;

import com.taoerxue.pojo.News;
import com.taoerxue.qo.NewsQuery;

import java.util.List;

public interface MyNewsMapper {

    List<News> list(NewsQuery newsQuery);

    String getUrl(Integer id);

    void incrViews(Integer id);

    String getRiches(Integer id);
}