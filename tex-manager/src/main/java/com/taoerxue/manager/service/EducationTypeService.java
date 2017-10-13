package com.taoerxue.manager.service;

import com.taoerxue.common.exception.SQLException;
import com.taoerxue.dto.FullEducationType;
import com.taoerxue.manager.vo.institution.EducationParentTypeWithoutPhoto;
import com.taoerxue.manager.vo.institution.EducationTypesWithStatus;
import com.taoerxue.mapper.EducationTypeMapper;
import com.taoerxue.pojo.EducationType;
import com.taoerxue.pojo.EducationTypeExample;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<EducationTypesWithStatus> list() {
        EducationTypeExample educationTypeExample = new EducationTypeExample();
        educationTypeExample.createCriteria().andParentIdEqualTo(0).andStatusEqualTo(true);
        List<EducationType> educationTypeList = educationTypeMapper.selectByExample(educationTypeExample);
        List<EducationTypesWithStatus> educationTypesList = new ArrayList<>();
        for (EducationType educationType : educationTypeList) {
            EducationTypesWithStatus educationTypes = dozerBeanMapper.map(educationType, EducationTypesWithStatus.class);
            educationTypeExample.clear();
            educationTypeExample.createCriteria().andParentIdEqualTo(educationType.getId());
            List<EducationType> educationTypeChildList = educationTypeMapper.selectByExample(educationTypeExample);
            List<EducationTypesWithStatus> educationTypesChildList = new ArrayList<>();
            for (EducationType educationChildType : educationTypeChildList) {
                EducationTypesWithStatus educationChildTypes = dozerBeanMapper.map(educationChildType, EducationTypesWithStatus.class);
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

    public EducationType get(Integer parentId) {
        return educationTypeMapper.selectByPrimaryKey(parentId);
    }

    @Transactional
    public void add(String name, Integer parentId) {
        EducationType educationType = new EducationType();
        educationType.setName(name);
        educationType.setParentId(parentId);
        educationType.setStatus(true);
        try {

            educationTypeMapper.insertSelective(educationType);
        } catch (Exception e) {
            throw new SQLException("添加失败");
        }
    }

    public void disable(Integer id) {
        changeStatus(id, false);
    }

    public void enable(Integer id) {
        changeStatus(id, true);
    }

    @Transactional
    private void changeStatus(Integer id, Boolean status) {
        EducationTypeExample educationTypeExample = new EducationTypeExample();
        educationTypeExample.createCriteria().andIdEqualTo(id).andParentIdNotEqualTo(0);
        EducationType educationType = new EducationType();
        educationType.setStatus(status);
        try {

            educationTypeMapper.updateByExampleSelective(educationType, educationTypeExample);
        } catch (Exception e) {
            throw new SQLException("启用用失败");
        }
    }
}
