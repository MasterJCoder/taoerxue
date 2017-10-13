package com.taoerxue.app.controller;

import com.taoerxue.app.qo.CourseQuery;
import com.taoerxue.app.service.CourseService;
import com.taoerxue.app.vo.course.CourseDetail;
import com.taoerxue.app.vo.course.CourseRecommend;
import com.taoerxue.app.vo.course.CourseSimple;
import com.taoerxue.common.bean.PageResult;
import com.taoerxue.common.bean.Result;
import com.taoerxue.common.cache.redis.JedisClient;
import com.taoerxue.common.map.MapPlatform;
import com.taoerxue.common.map.gaode.AddressDetailInfo;
import com.taoerxue.po.CourseInfoMongoPOJO;
import com.taoerxue.pojo.AppUser;
import com.taoerxue.util.JsonUtils;
import com.taoerxue.util.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhihui on 2017-03-23 10:10.
 */
@RequestMapping("/course")
@RestController
public class CourseInfoController extends BaseController {

    @Resource
    private CourseService courseService;
    @Resource
    private MapPlatform mapPlatform;
    @Resource
    private JedisClient jedisClient;
    @Resource
    private MongoTemplate mongoTemplate;


    @RequestMapping("/recommendList")
    public Result recommendList(CourseQuery query, HttpServletRequest request) {

        String address = query.getAddress();
        //查看是否传了地址
        if (StringUtils.isEmpty(address))
            return Result.build(500, "缺少地址信息");

        //解析地址
        AddressDetailInfo addressDetailInfo = mapPlatform.addressToLngLat(address);
        if (addressDetailInfo == null)
            return Result.build(500, "地址解析错误");

        Integer cityId = query.getCityId();
        Integer areaId = query.getAreaId();

        //判断是否选择城市或地区
        if (areaId == null && cityId == null)
            return Result.build(500, "请选择城市或者地区");
        if (areaId != null)
            query.setCityId(null);

        //是否显示距离
        Boolean display = displayDistance(cityId, areaId, addressDetailInfo.getCityId());

        query.setDisplay(display);
        if (display) {
            query.setLng(addressDetailInfo.getLng());
            query.setLat(addressDetailInfo.getLat());
        }

        //获取登录信息
        AppUser user = (AppUser) request.getSession().getAttribute("user");


        List<CourseRecommend> recommendList = courseService.recommendList(query, user);
        return Result.ok(recommendList);
    }

    @RequestMapping("/list")
    public Result list(CourseQuery query, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        if (pageNum == null || pageNum == 0)
            pageNum = 1;
        if (pageSize == null || pageSize == 0)
            pageSize = 10;


        //查看是否传了地址
        String address = query.getAddress();
        if (StringUtils.isEmpty(address))
            return Result.build(500, "缺少地址信息");

        //解析地址
        AddressDetailInfo addressDetailInfo = mapPlatform.addressToLngLat(address);
        if (addressDetailInfo == null)
            return Result.build(500, "地址解析错误");


        //判断是否选择城市或地区
        if (query.getAreaId() == null && query.getCityId() == null)
            return Result.build(500, "请选择城市或者地区");
        if (query.getAreaId() != null)
            query.setCityId(null);

        //是否显示距离
        Boolean display = displayDistance(query.getCityId(), query.getAreaId(), addressDetailInfo.getCityId());
        query.setDisplay(display);
        if (display) {
            query.setLng(addressDetailInfo.getLng());
            query.setLat(addressDetailInfo.getLat());
        }

        //获取登录信息
        AppUser user = (AppUser) request.getSession().getAttribute("user");

        PageResult<CourseSimple> simpleList = courseService.list(
                query,//查询信息的封装
                user,//用户信息
                pageNum,//页码
                pageSize);//每页数量
        return Result.ok(simpleList);

    }


    @RequestMapping("/detail")
    public Result detail(Integer id, String token) {
        if (id == null)
            return Result.build(500, "缺少课程 id");
        CourseDetail courseDetail = courseService.detail(id);
        if (courseDetail != null) {
            AppUser appUser = token == null ? null : JsonUtils.jsonToPojo(jedisClient.get(token), AppUser.class);
            courseDetail.setDoesCollect(courseService.doesCollect(appUser, id));
        }
        return Result.ok(courseDetail);
    }

    @RequestMapping("/teacherList")
    public Result teacherList(Integer id) {
        if (id == null)
            return Result.build(500, "缺少课程 id");
        List<CourseDetail.Teacher> teacherDetailList = courseService.teacherList(id);
        return Result.ok(teacherDetailList);
    }

    @RequestMapping("/search")
    public Result search(String like) {
        if (StringUtils.isEmpty(like))
            return Result.ok(new ArrayList<>());
        List<String> nameList = courseService.search(like);
        return Result.ok(nameList);
    }

    @RequestMapping("/collect")
    public Result collect(String token, Integer courseId) {
        AppUser user = JsonUtils.jsonToPojo(jedisClient.get(token == null ? "" : token), AppUser.class);
        if (user == null)
            return Result.build(500, "请先登录");
        if (courseId == null)
            return Result.build(500, "请选择你要收藏的课程");
        courseService.collect(courseId, user.getId());
        mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(courseId)), new Update().inc("collections", 1), CourseInfoMongoPOJO.class);

        return Result.ok();
    }

    @RequestMapping("/unCollect")
    public Result unCollect(String token, Integer courseId) {
        AppUser user = JsonUtils.jsonToPojo(jedisClient.get(token == null ? "" : token), AppUser.class);
        if (user == null)
            return Result.build(500, "请先登录");
        if (courseId == null)
            return Result.build(500, "请选择你要取消收藏的课程");
        courseService.unCollect(courseId, user.getId());
        mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(courseId)), new Update().inc("collections", -1), CourseInfoMongoPOJO.class);

        return Result.ok();
    }

    @RequestMapping("/collectList")
    public Result collectList(String token, Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum == 0)
            pageNum = 1;
        if (pageSize == null || pageSize == 0)
            pageSize = 10;
        AppUser user = JsonUtils.jsonToPojo(jedisClient.get(token == null ? "" : token), AppUser.class);
        if (user == null)
            return Result.build(500, "请先登录");
        return Result.ok(courseService.collectList(user.getId(), pageNum, pageSize));
    }
}
