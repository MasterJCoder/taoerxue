package com.taoerxue.manager.controller;

import com.taoerxue.common.bean.PageResult;
import com.taoerxue.common.bean.Result;
import com.taoerxue.common.enums.CourseStatusEnum;
import com.taoerxue.manager.service.CourseService;
import com.taoerxue.manager.vo.course.CourseDetail;
import com.taoerxue.manager.vo.course.CourseInfoAuditing;
import com.taoerxue.manager.vo.course.CourseInfoThroughAuditing;
import com.taoerxue.po.CourseInfoMongoPOJO;
import com.taoerxue.pojo.CourseInfo;
import com.taoerxue.qo.CourseInfoQuery;
import org.dozer.DozerBeanMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lizhihui on 2017-03-23 14:24.
 */
@RequestMapping("/course")
@RestController
public class CourseController {

    @Resource
    private CourseService courseService;
    @Resource
    private DozerBeanMapper dozerBeanMapper;
    @Resource
    private MongoTemplate mongoTemplate;


    @RequestMapping("/auditingList")
    public PageResult auditingList(CourseInfoQuery query) {

        query.setStatus(CourseStatusEnum.AUDITING.getStatus());
        PageResult<CourseInfo> list = courseService.list(query);
        List<CourseInfoAuditing> courseInfoAuditingList = list.getRows().stream().map(courseInfo -> dozerBeanMapper.map(courseInfo, CourseInfoAuditing.class)).collect(Collectors.toList());
        PageResult<CourseInfoAuditing> pageResult = new PageResult<>();
        pageResult.setTotal(list.getTotal());
        pageResult.setRows(courseInfoAuditingList);
        return pageResult;
    }


    @RequestMapping("/throughAuditList")
    public PageResult throughAuditList(CourseInfoQuery query) {

        query.setStatus(CourseStatusEnum.THROUGH_AUDIT.getStatus());
        PageResult<CourseInfo> list = courseService.list(query);
        PageResult<CourseInfoThroughAuditing> result = new PageResult<>();
        List<CourseInfo> rows = list.getRows();
        if (list.getTotal() != 0) {
            List<CourseInfoThroughAuditing> courseInfoThroughAuditingList = rows.stream().map(courseInfo -> dozerBeanMapper.map(courseInfo, CourseInfoThroughAuditing.class)).collect(Collectors.toList());
            result.setRows(courseInfoThroughAuditingList);
        }
        result.setTotal(list.getTotal());
        return result;
    }

    /**
     * @param id 课程 id
     * @return 哦
     */
    @RequestMapping("/throughAudit")
    public Result throughAudit(Integer id) {
        if (id == null)
            return Result.build(500, "请选择你要通过课程");
        if (!courseService.isExists(id))
            return Result.build(500, "你要通过的课程不存在,请刷新列表");
        courseService.throughAudit(id, CourseStatusEnum.AUDITING.getStatus(), CourseStatusEnum.THROUGH_AUDIT.getStatus());
        // TODO 修改通过时间
        //审核通过,把课程信息放入 mongo
        CourseInfo courseInfo = courseService.get(id);
        CourseInfoMongoPOJO courseInfoMongoPOJO = dozerBeanMapper.map(courseInfo, CourseInfoMongoPOJO.class);
        courseInfoMongoPOJO.setLocation(new double[]{courseInfo.getLng().doubleValue(), courseInfo.getLat().doubleValue()});
        courseInfoMongoPOJO.setCollections(0);
        mongoTemplate.insert(courseInfoMongoPOJO);
        return Result.build(200, "审核通过成功");
    }

    @RequestMapping("/failToAudit")
    public Result failToAudit(Integer id) {
        if (id == null)
            return Result.build(500, "请选择课程");
        if (!courseService.isExists(id))
            return Result.build(500, "该课程不存在,请刷新列表");
        courseService.failToAudit(id, CourseStatusEnum.AUDITING.getStatus(), CourseStatusEnum.NOT_SUBMIT.getStatus());
        return Result.build(200, "修改成功");
    }

    @RequestMapping("/detail")
    public Result detail(Integer id) {
        if (id == null)
            return Result.build(500, "请选择你要查看的课程");
        if (!courseService.isExists(id))
            return Result.build(500, "该课程不存在,请刷新列表");
        CourseDetail courseDetail = courseService.getDetail(id);
        return Result.ok(courseDetail);
    }

}
