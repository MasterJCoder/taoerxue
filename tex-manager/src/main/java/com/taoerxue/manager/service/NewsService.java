package com.taoerxue.manager.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taoerxue.common.bean.PageResult;
import com.taoerxue.common.exception.SQLException;
import com.taoerxue.mapper.MyNewsMapper;
import com.taoerxue.mapper.MyNewsTypeMapper;
import com.taoerxue.mapper.NewsMapper;
import com.taoerxue.mapper.NewsTypeMapper;
import com.taoerxue.pojo.*;
import com.taoerxue.qo.NewsQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lizhihui on 2017-04-27 13:51.
 */
@Service
public class NewsService {

    @Resource
    private NewsTypeMapper newsTypeMapper;
    @Resource
    private NewsMapper newsMapper;
    @Resource
    private MyNewsMapper myNewsMapper;
    @Resource
    private MyNewsTypeMapper myNewsTypeMapper;


    public PageResult<NewsType> typeList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        NewsTypeExample newsTypeExample = new NewsTypeExample();
        newsTypeExample.createCriteria().andStatusEqualTo(true);

        List<NewsType> newsTypeList = newsTypeMapper.selectByExample(newsTypeExample);
        PageInfo<NewsType> pageInfo = new PageInfo<>(newsTypeList);
        PageResult<NewsType> pageResult = new PageResult<NewsType>();
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setRows(newsTypeList);
        return pageResult;
    }

    @Transactional
    public void addType(AdminUser user, String name) {
        NewsType newsType = new NewsType();
        newsType.setBuilder(user.getUsername());
        newsType.setName(name);
        try {
            newsTypeMapper.insertSelective(newsType);
        } catch (Exception e) {
            newsType = new NewsType();
            newsType.setStatus(true);
            NewsTypeExample newsTypeExample = new NewsTypeExample();
            newsTypeExample.createCriteria().andNameEqualTo(name);
            int i = newsTypeMapper.updateByExampleSelective(newsType, newsTypeExample);
            if (i != 1)
                throw new SQLException("该板块已经存在,请勿重复添加");
        }
    }

    @Transactional
    public void deleteType(Integer id) {
        try {
            NewsType newsType = new NewsType();
            newsType.setId(id);
            newsType.setStatus(false);
            newsTypeMapper.updateByPrimaryKeySelective(newsType);
            News news = new News();
            news.setStatus(false);
            NewsExample newsExample = new NewsExample();
            newsExample.createCriteria().andTypeIdEqualTo(id);
            newsMapper.updateByExampleSelective(news, newsExample);
        } catch (Exception e) {
            throw new SQLException("删除失败");
        }
    }

    public PageResult list(NewsQuery newsQuery, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<News> newsList = myNewsMapper.list(newsQuery);
        PageInfo<News> pageInfo = new PageInfo<>(newsList);
        PageResult<News> pageResult = new PageResult<>();
        pageInfo.setTotal(pageInfo.getTotal());
        pageResult.setRows(newsList);
        return pageResult;
    }

    public NewsType getType(Integer typeId) {
        return newsTypeMapper.selectByPrimaryKey(typeId);
    }

    @Transactional
    public void add(News news) {
        try {
            newsMapper.insertSelective(news);
            myNewsTypeMapper.incrNewsCount(news.getTypeId());
        } catch (Exception e) {
            throw new SQLException("添加文章失败");
        }
    }

    @Transactional
    public void delete(Integer id) {
        News news = newsMapper.selectByPrimaryKey(id);
        if (news == null)
            throw new SQLException("该文章不存在");
        Integer typeId = news.getTypeId();
        news = new News();
        news.setId(id);
        news.setStatus(false);
        try {

            newsMapper.updateByPrimaryKeySelective(news);
            myNewsTypeMapper.decrNewsCount(typeId);
        } catch (Exception e) {
            throw new SQLException("删除文章失败");
        }

    }

    public News get(Integer id) {
        return newsMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public void update(News news) {
        try {

            newsMapper.updateByPrimaryKeySelective(news);
        } catch (Exception e) {
            throw new SQLException("编辑文章失败");
        }
    }

    @Transactional
    public void updateType(Integer id, String name) {
        try {
            NewsType newsType = new NewsType();
            newsType.setId(id);
            newsType.setName(name);
            newsTypeMapper.updateByPrimaryKeySelective(newsType);
            News news = new News();
            news.setTypeName(name);
            NewsExample newsExample = new NewsExample();
            newsExample.createCriteria().andTypeIdEqualTo(id);
            newsMapper.updateByExampleSelective(news, newsExample);
        } catch (Exception e) {
            throw new SQLException("更新失败");
        }
    }
}
