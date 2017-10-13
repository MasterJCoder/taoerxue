package com.taoerxue.web.service;

import com.taoerxue.dto.FullEducationType;
import com.taoerxue.mapper.EducationTypeMapper;
import com.taoerxue.pojo.EducationType;
import com.taoerxue.pojo.EducationTypeExample;
import com.taoerxue.web.vo.institution.EducationParentTypeWithoutPhoto;
import com.taoerxue.web.vo.institution.EducationTypes;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhihui on 2017-03-21 12:36.
 */
@Service
public class EducationTypeService {
    @Resource
    private EducationTypeMapper educationTypeMapper;
    @Resource
    private DozerBeanMapper dozerBeanMapper;


    public FullEducationType getFullTypeById(Integer typeId) {
        EducationType type = educationTypeMapper.selectByPrimaryKey(typeId);
        if (type == null)
            return null;
        Integer parentId = type.getParentId();
        if (!type.getStatus() || parentId == 0)
            return null;
        EducationType parentType = educationTypeMapper.selectByPrimaryKey(parentId);
        if (parentType == null)
            return null;
        FullEducationType fullEducationType = new FullEducationType();
        //设置一级分类信息
        fullEducationType.setParentTypeId(parentType.getId());
        fullEducationType.setParentTypeName(parentType.getName());
        //设置二级分来信息
        fullEducationType.setTypeName(type.getName());
        fullEducationType.setTypeId(type.getId());

        return fullEducationType;
    }
    public List<EducationTypes> list() {
        EducationTypeExample educationTypeExample = new EducationTypeExample();
        educationTypeExample.createCriteria().andParentIdEqualTo(0).andStatusEqualTo(true);
        List<EducationType> educationTypeList = educationTypeMapper.selectByExample(educationTypeExample);
        List<EducationTypes> educationTypesList = new ArrayList<>();
        for (EducationType educationType : educationTypeList) {
            EducationTypes educationTypes = dozerBeanMapper.map(educationType, EducationTypes.class);
            educationTypeExample.clear();
            educationTypeExample.createCriteria().andParentIdEqualTo(educationType.getId()).andStatusEqualTo(true);
            List<EducationType> educationTypeChildList = educationTypeMapper.selectByExample(educationTypeExample);
            List<EducationTypes> educationTypesChildList = new ArrayList<>();
            for (EducationType educationChildType : educationTypeChildList) {
                EducationTypes educationChildTypes = dozerBeanMapper.map(educationChildType, EducationTypes.class);
                educationTypesChildList.add(educationChildTypes);
            }
            educationTypes.setList(educationTypesChildList);
            educationTypesList.add(educationTypes);

        }
        return educationTypesList;
    }
    public List<EducationParentTypeWithoutPhoto> childList(Integer id) {
        EducationTypeExample educationTypeExample = new EducationTypeExample();
        educationTypeExample.createCriteria().andParentIdEqualTo(id).andStatusEqualTo(true);
        List<EducationType> educationTypeList = educationTypeMapper.selectByExample(educationTypeExample);
        List<EducationParentTypeWithoutPhoto> educationParentTypeWithoutPhotoList = new ArrayList<>();
        for (EducationType educationType : educationTypeList) {
            EducationParentTypeWithoutPhoto educationParentTypeWithoutPhoto = dozerBeanMapper.map(educationType, EducationParentTypeWithoutPhoto.class);
            educationParentTypeWithoutPhotoList.add(educationParentTypeWithoutPhoto);
        }
        return educationParentTypeWithoutPhotoList;
    }

}
