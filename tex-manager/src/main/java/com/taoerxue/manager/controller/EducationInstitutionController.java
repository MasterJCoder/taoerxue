package com.taoerxue.manager.controller;

import com.taoerxue.common.bean.PageResult;
import com.taoerxue.common.bean.Result;
import com.taoerxue.common.enums.EducationInstitutionStatusEnum;
import com.taoerxue.common.os.StoragePlatform;
import com.taoerxue.common.sms.SMSPlatform;
import com.taoerxue.manager.service.CourseService;
import com.taoerxue.manager.service.EducationInstitutionService;
import com.taoerxue.manager.service.EducationInstitutionUserService;
import com.taoerxue.manager.vo.course.CourseInfoAuditing;
import com.taoerxue.manager.vo.course.CourseInfoThroughAuditing;
import com.taoerxue.manager.vo.institution.EducationInstitutionAuditing;
import com.taoerxue.manager.vo.institution.EducationInstitutionDetail;
import com.taoerxue.manager.vo.institution.EducationInstitutionDisabled;
import com.taoerxue.manager.vo.institution.EducationInstitutionThroughAudit;
import com.taoerxue.po.EducationInstitutionMongoPOJO;
import com.taoerxue.pojo.CourseInfo;
import com.taoerxue.pojo.EducationInstitution;
import com.taoerxue.qo.CourseInfoQuery;
import com.taoerxue.qo.EducationInstitutionQuery;
import org.dozer.DozerBeanMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 教育机构相关的 controller
 * Created by lizhihui on 2017-03-18 09:35.
 */
@RestController
@RequestMapping("/educationInstitution")
public class EducationInstitutionController {
    @Resource
    private EducationInstitutionService educationInstitutionService;
    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private DozerBeanMapper dozerBeanMapper;
    @Resource
    private EducationInstitutionUserService educationInstitutionUserService;
    @Resource
    private StoragePlatform storagePlatform;
    @Resource
    private CourseService courseService;
    @Resource
    private SMSPlatform smsPlatform;

    /**
     * 审核通过
     *
     * @param eid 机构 id
     * @return 审核结果
     */

    @RequestMapping("/throughAudit")
    public Result throughAudit(Integer eid) {

        if (eid == null) {
            return Result.build(500, "请选择你要修改的教育机构");
        }


        EducationInstitution educationInstitution = educationInstitutionService.get(eid);
        if (educationInstitution == null)
            return Result.build(500, "该机构不存在,请刷新重试");
        String phone = educationInstitutionUserService.getAdminPhone(eid);

        //从审核中状态到审核通过这个状态
        educationInstitutionService.throughAudit(eid, phone, educationInstitution.getName());//审核中

        // 把审核通过的机构放入 mongodb 中

        EducationInstitutionMongoPOJO educationInstitutionMongoPOJO = dozerBeanMapper.map(educationInstitution, EducationInstitutionMongoPOJO.class);
        educationInstitutionMongoPOJO.setLocation(new double[]{educationInstitution.getLng().doubleValue(), educationInstitution.getLat().doubleValue()});
        //设置收藏数为0
        educationInstitutionMongoPOJO.setCollections(0);
        //设置课程数为0
        educationInstitutionMongoPOJO.setCourseCount(0);
        mongoTemplate.insert(educationInstitutionMongoPOJO);
        smsPlatform.send(phone, "恭喜您的机构成功入驻淘儿学教育平台，即刻登录web.taoerxue.com开启教育新旅程！如有疑问请联系平台客服0571-28120182");
        return Result.build(200, "审核通过");
    }

    /**
     * 审核不通过
     *
     * @param eid 机构 id
     * @return 审核结果
     */
    @RequestMapping("/failToAudit")
    public Result pass(Integer eid) {

        if (eid == null) {
            return Result.build(500, "请选择你要修改的教育机构");
        }

        EducationInstitution educationInstitution = educationInstitutionService.get(eid);
        if (educationInstitution == null)
            return Result.build(500, "该机构不存在,请刷新重试");


        //从审核中状态到审核通过这个状态
        educationInstitutionService.changeStatus(eid,
                EducationInstitutionStatusEnum.AUDIT_FAILURE.getStatus(),//审核失败
                EducationInstitutionStatusEnum.AUDITING.getStatus());//审核中
        String phone = educationInstitutionUserService.getAdminPhone(eid);
        smsPlatform.send(phone, "恭喜您的机构成功入驻淘儿学教育平台，即刻登录web.taoerxue.com开启教育新旅程！如有疑问请联系平台客服0571-28120182");
        return Result.build(200, "成功");
    }


    @RequestMapping("/disable")
    public Result disable(Integer eid) {
        if (eid == null) {
            return Result.build(500, "请选择你要禁用的教育机构");
        }

        boolean result = educationInstitutionService.disable(eid);
        if (result) {

            String phone = educationInstitutionUserService.getAdminPhone(eid);
            String name = educationInstitutionService.getName(eid);
            return Result.build(200, "禁用成功");
        }
        return Result.build(500, "禁用失败");
    }


    @RequestMapping("/auditingList")
    public PageResult auditingList(EducationInstitutionQuery query) {
        query.setStatus(EducationInstitutionStatusEnum.AUDITING.getStatus());
        PageResult<EducationInstitution> pageResult = educationInstitutionService.list(query);
        if (pageResult.getTotal() > 0) {

            List<EducationInstitutionAuditing> collect = pageResult.getRows().stream()
                    .map(educationInstitution -> dozerBeanMapper.map(educationInstitution, EducationInstitutionAuditing.class))
                    .collect(Collectors.toList());

            PageResult<EducationInstitutionAuditing> auditingPageResult = new PageResult<>();
            auditingPageResult.setTotal(pageResult.getTotal());
            auditingPageResult.setRows(collect);
            return auditingPageResult;
        }
        return pageResult;
    }


    @RequestMapping("/throughAuditList")
    public PageResult throughAuditList(EducationInstitutionQuery query) {

        query.setStatus(EducationInstitutionStatusEnum.THROUGH_AUDIT.getStatus());
        PageResult<EducationInstitution> pageResult = educationInstitutionService.list(query);
        if (pageResult.getTotal() > 0) {
            List<EducationInstitutionThroughAudit> institutionThroughAuditList = pageResult.getRows().stream()
                    .map(educationInstitution -> dozerBeanMapper.map(educationInstitution, EducationInstitutionThroughAudit.class))
                    .collect(Collectors.toList());
            PageResult<EducationInstitutionThroughAudit> auditingPageResult = new PageResult<>();
            auditingPageResult.setTotal(pageResult.getTotal());
            auditingPageResult.setRows(institutionThroughAuditList);
            return auditingPageResult;
        }
        return pageResult;
    }

    @RequestMapping("/disabledList")
    public PageResult disabledList(EducationInstitutionQuery query) {

        PageResult<EducationInstitution> pageResult = educationInstitutionService.list(query);
        if (pageResult.getTotal() > 0) {
            List<EducationInstitutionDisabled> institutionDisabledList = pageResult.getRows().stream()
                    .map(educationInstitution -> dozerBeanMapper.map(educationInstitution, EducationInstitutionDisabled.class))
                    .collect(Collectors.toList());

            PageResult<EducationInstitutionDisabled> disabledPageResult = new PageResult<>();
            disabledPageResult.setTotal(pageResult.getTotal());
            disabledPageResult.setRows(institutionDisabledList);
            return disabledPageResult;
        }
        return pageResult;
    }

    @RequestMapping("/detail")
    public Result detail(Integer id) {

        EducationInstitution educationInstitution = educationInstitutionService.get(id);
        if (educationInstitution != null) {
            EducationInstitutionDetail educationInstitutionDetail = dozerBeanMapper.map(educationInstitution, EducationInstitutionDetail.class);
            educationInstitutionDetail.setPhoto(storagePlatform.getImageURL(educationInstitutionDetail.getPhoto()));
            return Result.ok(educationInstitutionDetail);
        } else return Result.build(500, "该机构不存在");
    }

    @RequestMapping("/auditingCourseList")
    public PageResult auditingCourseList(CourseInfoQuery query) {
        if (query.getEid() == null)
            return new PageResult();
        PageResult<CourseInfo> list = courseService.list(query);
        if (list.getTotal() > 0) {
            PageResult<CourseInfoAuditing> pageResult = new PageResult<>();
            pageResult.setTotal(list.getTotal());
            pageResult.setRows(list.getRows().stream().map(courseInfo -> dozerBeanMapper.map(courseInfo, CourseInfoAuditing.class)).collect(Collectors.toList()));
            return pageResult;
        } else
            return list;
    }

    @RequestMapping("/throughAuditCourseList")
    public PageResult throughAuditCourseList(CourseInfoQuery query) {
        if (query.getEid() == null)
            return new PageResult();
        PageResult<CourseInfo> list = courseService.list(query);
        if (list.getTotal() > 0) {
            PageResult<CourseInfoThroughAuditing> pageResult = new PageResult<>();
            pageResult.setTotal(list.getTotal());
            pageResult.setRows(list.getRows().stream().map(courseInfo -> dozerBeanMapper.map(courseInfo, CourseInfoThroughAuditing.class)).collect(Collectors.toList()));
            return pageResult;
        } else
            return list;
    }
}
