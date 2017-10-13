package com.taoerxue.app.service;

import com.taoerxue.app.vo.location.Area;
import com.taoerxue.mapper.AreaMapper;
import com.taoerxue.pojo.AreaExample;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lizhihui on 2017-04-25 10:57.
 */
@Service
public class LocationService {


    @Resource
    private AreaMapper areaMapper;
    @Resource
    private DozerBeanMapper dozerBeanMapper;

    public List<Area> areaList(Integer cityId) {
        AreaExample areaExample = new AreaExample();
        areaExample.createCriteria().andCityIdEqualTo(cityId);
        return areaMapper.selectByExample(areaExample)
                .stream()
                .map(area -> dozerBeanMapper.map(area, Area.class))
                .collect(Collectors.toList());
    }
}
