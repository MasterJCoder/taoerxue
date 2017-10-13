package com.taoerxue.mapper;

import com.taoerxue.pojo.NewsType;
import com.taoerxue.pojo.NewsTypeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NewsTypeMapper {
    int countByExample(NewsTypeExample example);

    int deleteByExample(NewsTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(NewsType record);

    int insertSelective(NewsType record);

    List<NewsType> selectByExample(NewsTypeExample example);

    NewsType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NewsType record, @Param("example") NewsTypeExample example);

    int updateByExample(@Param("record") NewsType record, @Param("example") NewsTypeExample example);

    int updateByPrimaryKeySelective(NewsType record);

    int updateByPrimaryKey(NewsType record);
}