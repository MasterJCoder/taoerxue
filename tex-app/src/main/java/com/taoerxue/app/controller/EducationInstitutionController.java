package com.taoerxue.app.controller;

import com.taoerxue.app.service.EducationInstitutionService;
import com.taoerxue.app.vo.CustomerServiceStaff;
import com.taoerxue.app.vo.EducationInstitutionQuery;
import com.taoerxue.app.vo.institution.EducationInstitutionDetail;
import com.taoerxue.app.vo.institution.EducationInstitutionRecommend;
import com.taoerxue.common.bean.Result;
import com.taoerxue.common.cache.redis.JedisClient;
import com.taoerxue.common.map.MapPlatform;
import com.taoerxue.common.map.gaode.AddressDetailInfo;
import com.taoerxue.po.EducationInstitutionMongoPOJO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by lizhihui on 2017-03-23 11:01.
 */


@RequestMapping("/educationInstitution")
@RestController
public class EducationInstitutionController extends BaseController {

    @Resource
    private EducationInstitutionService educationInstitutionService;
    @Resource
    private MapPlatform mapPlatform;
    @Resource
    private JedisClient jedisClient;
    @Resource
    private MongoTemplate mongoTemplate;

    @RequestMapping("/detail")
    public Result detail(Integer id, Double lng, Double lat, String token) {
        if (id == null || id == 0)
            return Result.build(500, "请选择你要查看的机构");
        if (lng == null)
            lng = 120D;
        if (lat == null)
            lat = 27D;
        AppUser appUser = StringUtils.isEmpty(token) ? null : JsonUtils.jsonToPojo(jedisClient.get(token), AppUser.class);
        EducationInstitutionDetail detail = educationInstitutionService.getDetail(id, lng, lat);
        if (detail == null) {
            return Result.build(500, "你要查看的机构不存在");
        }
        detail.setDoesCollect(educationInstitutionService.doesCollect(appUser, id));
        return Result.ok(detail);
    }

    @RequestMapping("/teacherList")
    public Result teacherList(Integer eId, Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum == 0)
            pageNum = 1;
        if (pageSize == null || pageSize == 0)
            pageSize = 10;

        if (eId == null)
            return Result.build(500, "缺少机构 id");
        return Result.ok(educationInstitutionService.teacherList(eId, pageNum, pageSize));
    }

    @RequestMapping("/courseList")
    public Result courseList(Integer eId, Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum == 0)
            pageNum = 1;
        if (pageSize == null || pageSize == 0)
            pageSize = 10;
        return Result.ok(educationInstitutionService.courseList(eId, pageNum, pageSize));
    }

    @RequestMapping("/list")
    public Result list(EducationInstitutionQuery query, Integer pageNum, Integer pageSize, String token) {
        if (pageNum == null || pageNum == 0)
            pageNum = 1;
        if (pageSize == null || pageSize == 0)
            pageSize = 10;


        //先判断地址传了没

        String address = query.getAddress();
        if (StringUtils.isEmpty(address))
            return Result.build(500, "缺少地址信息");

        //解析地址
        AddressDetailInfo addressDetailInfo = mapPlatform.addressToLngLat(address);
        if (addressDetailInfo == null)
            return Result.build(500, "地址解析错误");
        //获取登录信息
        AppUser user = JsonUtils.jsonToPojo(jedisClient.get(token == null ? "" : token), AppUser.class);

        if (query.getAreaId() == null && query.getCityId() == null)
            return Result.build(500, "请选择城市或者地区");
        if (query.getAreaId() != null)
            query.setCityId(null);

        return Result.ok(educationInstitutionService.list(
                displayDistance(query.getCityId(), query.getAreaId(), addressDetailInfo.getCityId()),//是否显示距离
                addressDetailInfo.getLng(),//定位经度
                addressDetailInfo.getLat(),//定位纬度
                user,//
                query,
                pageNum,
                pageSize));
    }

    @RequestMapping("/recommendList")
    public Result recommendList(String address, Integer cityId, Integer areaId, String token) {
        if (StringUtils.isEmpty(address))
            return Result.build(500, "缺少地址信息");
        AddressDetailInfo addressDetailInfo = mapPlatform.addressToLngLat(address);
        if (addressDetailInfo == null)
            return Result.build(500, "地址解析错误");

        //获取登录信息
        AppUser user = JsonUtils.jsonToPojo(jedisClient.get(StringUtils.isEmpty(token) ? "" : token), AppUser.class);

        if (cityId == null && areaId == null)
            return Result.build(500, "请选择城市或者地区");
        if (areaId != null)
            cityId = null;

        List<EducationInstitutionRecommend> recommendList = educationInstitutionService.recommendList(
                displayDistance(cityId, areaId, addressDetailInfo.getCityId()),
                cityId,
                areaId,
                addressDetailInfo.getLng(),
                addressDetailInfo.getLat(),
                user);

        return Result.ok(recommendList);
    }

    @RequestMapping("/customList")
    public Result customList(Integer eId) {
        if (eId == null)
            return Result.ok(new ArrayList<>());
        List<CustomerServiceStaff> customerServiceStaffList = educationInstitutionService.customList(eId);
        if (customerServiceStaffList.size() <= 0)
            return Result.ok(new ArrayList<>());

        return Result.ok(customerServiceStaffList.get(new Random().nextInt(customerServiceStaffList.size())));
    }

    @RequestMapping("/search")
    public Result search(String like) {
        if (StringUtils.isEmpty(like))
            return Result.ok(new ArrayList<>());
        List<String> nameList = educationInstitutionService.search(like);
        return Result.ok(nameList);
    }

    @RequestMapping("/collect")
    public Result collect(String token, Integer eId) {
        AppUser user = JsonUtils.jsonToPojo(jedisClient.get(token == null ? "" : token), AppUser.class);
        if (user == null)
            return Result.build(500, "请先登录");
        if (eId == null)
            return Result.build(500, "请选择你要收藏的机构");
        educationInstitutionService.collect(eId, user.getId());
        mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(eId)), new Update().inc("collections", 1), EducationInstitutionMongoPOJO.class);
        return Result.ok();
    }

    @RequestMapping("/unCollect")
    public Result unCollect(String token, Integer eId) {
        AppUser user = JsonUtils.jsonToPojo(jedisClient.get(token == null ? "" : token), AppUser.class);
        if (user == null)
            return Result.build(500, "请先登录");
        if (eId == null)
            return Result.build(500, "请选择你要取消收藏的机构");
        educationInstitutionService.unCollect(eId, user.getId());
        mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(eId)), new Update().inc("collections", -1), EducationInstitutionMongoPOJO.class);

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
        return Result.ok(educationInstitutionService.collectList(user.getId(), pageNum, pageSize));
    }
}
