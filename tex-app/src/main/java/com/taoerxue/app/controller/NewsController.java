package com.taoerxue.app.controller;

import com.taoerxue.app.service.NewsService;
import com.taoerxue.app.vo.news.NewsType;
import com.taoerxue.common.bean.Result;
import com.taoerxue.qo.NewsQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lizhihui on 2017-04-27 15:31.
 */
@RestController
@RequestMapping("/news")
public class NewsController {

    @Resource
    private NewsService newsService;


    @RequestMapping("/typeList")
    public Result typeList() {
        List<NewsType> newsTypes = newsService.typeList();
        List<NewsType> newsTypes1 = new ArrayList<>();
        NewsType newsType = new NewsType();
        newsType.setName("推荐");
        newsType.setId(0);
        newsTypes1.add(newsType);
        newsTypes1.addAll(newsTypes);
        return Result.ok(newsTypes1);
    }

    @RequestMapping("/list")
    public Result list(NewsQuery newsQuery, Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum == 0)
            pageNum = 1;
        if (pageSize == null || pageSize == 0)
            pageSize = 10;
        if (newsQuery.getTypeId()!=null&&newsQuery.getTypeId()==0)
            newsQuery.setTypeId(null);
        return Result.ok(newsService.list(newsQuery, pageNum, pageSize));
    }

    @RequestMapping("/detail")
    public Result detail(Integer id) {
        String riches = newsService.getRiches(id);
        Map<String, String> map = new HashMap<>();
        map.put("riches", riches);
        return Result.ok(map);
    }


}
