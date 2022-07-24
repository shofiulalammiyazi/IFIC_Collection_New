package com.unisoft.collection.settings.designation;
/*
Created by   Islam at 6/24/2019
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesignationService {

    @Autowired
    private DesignationDao designationDao;

    public List<DesignationEntity> getAll()
    {
        return  designationDao.getList();
    }

    public DesignationEntity getById(Long id)
    {
        return designationDao.getById(id);
    }

    public DesignationEntity findByName(String name) {
        return designationDao.findByName(name);
    }

    public boolean saveNew(DesignationEntity designation)
    {
        return designationDao.save(designation);
    }

    public boolean updateDes(DesignationEntity designation)
    {
        return designationDao.updateDes(designation);
    }

    public List<DesignationEntity> getActiveList()
    {
        return designationDao.getActiveOnly();
    }

}
