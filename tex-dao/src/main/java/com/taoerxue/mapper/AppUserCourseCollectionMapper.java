package com.taoerxue.mapper;

import com.taoerxue.pojo.AppUserCourseCollection;
import com.taoerxue.pojo.AppUserCourseCollectionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppUserCourseCollectionMapper {
    int countByExample(AppUserCourseCollectionExample example);

    int deleteByExample(AppUserCourseCollectionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AppUserCourseCollection record);

    int insertSelective(AppUserCourseCollection record);

    List<AppUserCourseCollection> selectByExample(AppUserCourseCollectionExample example);

    AppUserCourseCollection selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AppUserCourseCollection record, @Param("example") AppUserCourseCollectionExample example);

    int updateByExample(@Param("record") AppUserCourseCollection record, @Param("example") AppUserCourseCollectionExample example);

    int updateByPrimaryKeySelective(AppUserCourseCollection record);

    int updateByPrimaryKey(AppUserCourseCollection record);
}