package com.taoerxue.mapper;

import com.taoerxue.pojo.EducationInstitution;
import com.taoerxue.qo.EducationInstitutionQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyEducationInstitutionMapper {
    Integer getStatus(Integer id);

    /**
     * 更新教育机构的状态,从老状态变为新状态
     *
     * @param id        教育机构的 id
     * @param newStatus 新的状态
     * @param oldStatus 老的状态
     * @return 更新成功的条数
     */
    int updateStatus(@Param("id") Integer id, @Param("newStatus") Integer newStatus, @Param("oldStatus") Integer oldStatus);

    String getName(Integer id);

    List<EducationInstitution> list(EducationInstitutionQuery query);

    List<String> listNameLike(String like);

    List<EducationInstitution> collectList(Integer id);

    Integer monthlyCount();

}