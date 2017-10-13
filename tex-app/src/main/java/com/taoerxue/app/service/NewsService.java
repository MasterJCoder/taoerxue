package com.taoerxue.app.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taoerxue.app.vo.news.News;
import com.taoerxue.app.vo.news.NewsType;
import com.taoerxue.common.bean.PageResult;
import com.taoerxue.common.os.StoragePlatform;
import com.taoerxue.mapper.MyNewsMapper;
import com.taoerxue.mapper.NewsTypeMapper;
import com.taoerxue.pojo.NewsTypeExample;
import com.taoerxue.qo.NewsQuery;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lizhihui on 2017-04-27 15:31.
 */
@Service
public class NewsService {
    @Resource
    private NewsTypeMapper newsTypeMapper;
    @Resource
    private DozerBeanMapper dozerBeanMapper;
    @Resource
    private MyNewsMapper myNewsMapper;
    @Resource
    private StoragePlatform storagePlatform;

    public List<NewsType> typeList() {

        NewsTypeExample newsTypeExample = new NewsTypeExample();
        newsTypeExample.createCriteria().andStatusEqualTo(true);
        return newsTypeMapper.selectByExample(newsTypeExample)
                .stream()
                .map(newsType -> dozerBeanMapper.map(newsType, NewsType.class))
                .collect(Collectors.toList());

    }

    public PageResult<News> list(NewsQuery newsQuery, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<com.taoerxue.pojo.News> newsList = myNewsMapper.list(newsQuery);
        List<News> newsList1 = newsList
                .stream()
                .map(news -> {
                    News news1 = dozerBeanMapper.map(news, News.class);
                    news1.setPhoto(storagePlatform.getImageURL(news1.getPhoto()));
                    return news1;
                })
                .collect(Collectors.toList());
        PageResult<News> pageResult = new PageResult<>();
        pageResult.setRows(newsList1);
        pageResult.setTotal(new PageInfo<>(newsList).getTotal());
        return pageResult;
    }

    @Transactional
    public String getRiches(Integer id) {
        try {
            myNewsMapper.incrViews(id);
        } catch (Exception e) {

        }
        return myNewsMapper.getRiches(id);
    }
}
