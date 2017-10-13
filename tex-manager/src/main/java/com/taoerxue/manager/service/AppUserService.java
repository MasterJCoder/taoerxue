package com.taoerxue.manager.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taoerxue.common.bean.PageResult;
import com.taoerxue.mapper.AppUserMapper;
import com.taoerxue.mapper.MyAppUserMapper;
import com.taoerxue.pojo.AppUser;
import com.taoerxue.pojo.AppUserExample;
import com.taoerxue.qo.AppUserQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lizhihui on 2017-03-23 16:35.
 */
@Service
public class AppUserService {

    @Resource
    private MyAppUserMapper myAppUserMapper;
    @Resource
    private AppUserMapper appUserMapper;

    public PageResult<AppUser> list(AppUserQuery query, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<AppUser> list = myAppUserMapper.list(query);
        PageInfo<AppUser> pageInfo = new PageInfo<>();
        PageResult<AppUser> pageResult = new PageResult<>();
        pageResult.setRows(list);
        pageResult.setTotal(pageInfo.getTotal());
        return pageResult;
    }

    public Integer allCount() {
        return appUserMapper.countByExample(new AppUserExample());
    }

    public Integer monthlyCount() {
        return myAppUserMapper.monthlyCount();
    }
}
