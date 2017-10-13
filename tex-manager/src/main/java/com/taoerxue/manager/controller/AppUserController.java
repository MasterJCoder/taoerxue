package com.taoerxue.manager.controller;

import com.taoerxue.common.bean.PageResult;
import com.taoerxue.common.os.StoragePlatform;
import com.taoerxue.manager.service.AppUserService;
import com.taoerxue.pojo.AppUser;
import com.taoerxue.qo.AppUserQuery;
import org.dozer.DozerBeanMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhihui on 2017-03-23 16:21.
 */
@RequestMapping("/appUser")
@RestController
public class AppUserController {

    @Resource
    private AppUserService appUserService;
    @Resource
    private DozerBeanMapper dozerBeanMapper;
    @Resource
    private StoragePlatform storagePlatform;

    @RequestMapping("/list")
    public PageResult list(AppUserQuery query, Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum == 0)
            pageNum = 1;
        if (pageSize == null || pageSize == 0)
            pageSize = 10;
        PageResult<AppUser> appUserPageResult = appUserService.list(query, pageNum, pageSize);
        if (appUserPageResult.getTotal() > 0) {
            List<AppUser> rows = appUserPageResult.getRows();
            List<com.taoerxue.manager.vo.appuser.AppUser> appUserList = new ArrayList<>();
            for (AppUser appUser : rows) {
                com.taoerxue.manager.vo.appuser.AppUser appUserVO = dozerBeanMapper.map(appUser, com.taoerxue.manager.vo.appuser.AppUser.class);
                appUserVO.setPhone(storagePlatform.getImageURL(appUserVO.getPhone()));
                appUserList.add(appUserVO);
            }
            PageResult<com.taoerxue.manager.vo.appuser.AppUser> userPageResult = new PageResult<>();
            userPageResult.setTotal(appUserPageResult.getTotal());
            userPageResult.setRows(appUserList);
            return userPageResult;
        }
        return appUserPageResult;
    }
}
