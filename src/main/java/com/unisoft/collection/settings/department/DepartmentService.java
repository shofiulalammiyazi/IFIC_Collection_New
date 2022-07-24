package com.unisoft.collection.settings.department;
/*
Created by   Islam at 6/24/2019
*/


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    public List<DepartmentEntity> getAll()
    {
        return departmentDao.getDivList();
    }

    public DepartmentEntity getById(Long Id)
    {
        return departmentDao.getById(Id);
    }

    public boolean updateDept(DepartmentEntity departmentEntity)
    {
        return departmentDao.updateDept(departmentEntity);
    }

    public boolean save(DepartmentEntity departmentEntity)
    {
        return departmentDao.saveNewDept(departmentEntity);
    }

    public List<DepartmentEntity> getActiveList()
    {
        return departmentDao.getActiveOnly();
    }
}
