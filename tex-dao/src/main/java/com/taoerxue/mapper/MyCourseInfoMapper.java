package com.taoerxue.mapper;

import com.taoerxue.pojo.CourseInfo;
import com.taoerxue.qo.CourseInfoQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyCourseInfoMapper {
    int updateStatus(@Param("id") Integer id, @Param("oldStatus") Integer oldStatus, @Param("newStatus") Integer newStatus);

    List<CourseInfo> listByTeacherId(Integer id);

    Integer countByTeacherId(Integer id);

    List<CourseInfo> list(CourseInfoQuery query);

    List<String> listNameLike(String like);

    List<CourseInfo> collectList(Integer id);

    Integer monthlyCount();
}