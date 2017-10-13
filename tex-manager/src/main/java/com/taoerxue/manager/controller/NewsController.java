package com.taoerxue.manager.controller;

import com.taoerxue.common.bean.PageResult;
import com.taoerxue.common.bean.Result;
import com.taoerxue.common.os.StoragePlatform;
import com.taoerxue.manager.dto.NewsAdd;
import com.taoerxue.manager.dto.NewsEdit;
import com.taoerxue.manager.service.NewsService;
import com.taoerxue.pojo.AdminUser;
import com.taoerxue.pojo.News;
import com.taoerxue.pojo.NewsType;
import com.taoerxue.qo.NewsQuery;
import com.taoerxue.util.StringUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by lizhihui on 2017-04-27 13:40.
 */
@RequestMapping("/news")
@RestController
public class NewsController extends BaseController {


    @Resource
    private NewsService newsService;
    @Resource
    private DozerBeanMapper dozerBeanMapper;

    @Resource
    private StoragePlatform storagePlatform;

    @RequestMapping("/typeList")
    public PageResult typeList(Integer pageSize, Integer pageNum) {
        return newsService.typeList(pageNum, pageSize);
    }

    @RequestMapping("/addType")
    public Result addType(String name, HttpServletRequest request) {
        AdminUser user = (AdminUser) request.getSession().getAttribute("user");
        if (!StringUtils.lengthRangeIn(name, 1, 6))
            return Result.build(500, "板块名称的长度位1-6个字符");
        newsService.addType(user, name);
        return Result.ok();
    }

    @RequestMapping("/deleteType")
    public Result deleteType(Integer id) {
        newsService.deleteType(id);
        return Result.ok();
    }

    @RequestMapping("/updateType")
    public Result updateType(String name, Integer id) {
        if (id == null)
            return Result.build(500, "请选择你要编辑的板块");
        if (!StringUtils.lengthRangeIn(name, 1, 6))
            return Result.build(500, "板块名称的长度位1-6个字符");
        NewsType type = newsService.getType(id);
        if (type == null)
            return Result.build(500, "你要修改的板块不存在");
        newsService.updateType(id, name);
        return Result.ok();
    }

    @RequestMapping("/list")
    public PageResult list(NewsQuery newsQuery, Integer pageSize, Integer pageNum) {
        return newsService.list(newsQuery, pageNum, pageSize);
    }

    @RequestMapping("/add")
    public Result add(@Valid NewsAdd newsAdd, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return Result.build(500, getErrors(bindingResult));

        if (!storagePlatform.exists(newsAdd.getPhoto()))
            return Result.build(500, "文章素材图片不存在,请重新上传");

        NewsType newsType = newsService.getType(newsAdd.getTypeId());
        if (newsType == null)
            return Result.build(500, "不存在的文章板块");
        News news = dozerBeanMapper.map(newsAdd, News.class);
        news.setTypeName(newsType.getName());
        news.setUrl("");
        newsService.add(news);
        return Result.ok();
    }

    @RequestMapping("/delete")
    public Result delete(Integer id) {
        if (id == null)
            return Result.build(500, "请选择你要删除的文章");
        newsService.delete(id);
        return Result.ok();
    }

    @RequestMapping("/update")
    public Result update(NewsEdit edit) {
        Integer id = edit.getId();
        if (id == null)
            return Result.build(500, "请选择你要修改的文章");
        News oldNews = newsService.get(id);
        if (oldNews == null)
            return Result.build(500, "你要修改的文章不存在");
        if (edit.getPhoto() != null && !edit.getPhoto().equals(oldNews.getPhoto())) {
            if (!storagePlatform.exists(edit.getPhoto())) {
                return Result.build(500, "上传的图片不存在");
            }
        }
        News news = dozerBeanMapper.map(edit, News.class);
        news.setStatus(true);
        newsService.update(news);
        return Result.ok();
    }


}
