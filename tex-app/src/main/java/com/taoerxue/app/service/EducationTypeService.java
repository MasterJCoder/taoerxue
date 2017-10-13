package com.taoerxue.app.service;

import com.taoerxue.common.os.StoragePlatform;
import com.taoerxue.mapper.EducationTypeMapper;
import com.taoerxue.pojo.EducationType;
import com.taoerxue.pojo.EducationTypeExample;
import com.taoerxue.app.vo.institution.EducationParentTypeWithPhoto;
import com.taoerxue.app.vo.institution.EducationParentTypeWithoutPhoto;
import com.taoerxue.app.vo.institution.EducationTypes;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhihui on 2017-03-27 09:53.
 */
@Service
public class EducationTypeService {
    @Resource
    private EducationTypeMapper educationTypeMapper;
    @Resource
    private DozerBeanMapper dozerBeanMapper;
    @Resource
    private StoragePlatform storagePlatform;

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

    public List<EducationParentTypeWithPhoto> parentList() {
        EducationTypeExample educationTypeExample = new EducationTypeExample();
        educationTypeExample.createCriteria().andParentIdEqualTo(0).andStatusEqualTo(true);
        List<EducationType> educationTypeList = educationTypeMapper.selectByExample(educationTypeExample);
        List<EducationParentTypeWithPhoto> educationParentTypeWithPhotoList = new ArrayList<>();
        for (EducationType educationType : educationTypeList) {
            EducationParentTypeWithPhoto educationParentTypeWithPhoto = dozerBeanMapper.map(educationType, EducationParentTypeWithPhoto.class);
            educationParentTypeWithPhoto.setPhoto(storagePlatform.getImageURL(educationParentTypeWithPhoto.getPhoto()));
            educationParentTypeWithPhotoList.add(educationParentTypeWithPhoto);
        }
        EducationParentTypeWithPhoto educationParentTypeWithPhoto = new EducationParentTypeWithPhoto();
        educationParentTypeWithPhoto.setId(1000000);
        educationParentTypeWithPhoto.setName("全部");
        educationParentTypeWithPhoto.setPhoto(storagePlatform.getImageURL("all@2x.png"));
        educationParentTypeWithPhotoList.add(educationParentTypeWithPhoto);
        return educationParentTypeWithPhotoList;
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
