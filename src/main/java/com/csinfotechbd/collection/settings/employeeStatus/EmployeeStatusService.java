package com.csinfotechbd.collection.settings.employeeStatus;
/*
Created by Monirul Islam at 6/25/2019 
*/

import com.csinfotechbd.audittrail.AuditTrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeStatusService {

    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private EmployeeStatusDao employeeStatusDao;


    public List<EmployeeStatusEntity> getAllStatus()
    {
        return employeeStatusDao.getList();
    }

    public EmployeeStatusEntity getById(Long Id)
    {
        return employeeStatusDao.getById(Id);
    }

    public boolean saveStat(EmployeeStatusEntity currentStatus)
    {
        auditTrailService.saveCreatedData("Employee Status", currentStatus);

        return employeeStatusDao.saveNew(currentStatus);
    }

    public boolean updateStat(EmployeeStatusEntity currentStatus)
    {
        EmployeeStatusEntity previousData = new EmployeeStatusEntity();
        auditTrailService.saveUpdatedData("Employee Status", previousData, currentStatus);
        return employeeStatusDao.updateStat(currentStatus);
    }

    public List<EmployeeStatusEntity> getAllActive()
    {
        return employeeStatusDao.getActiveOnly();
    }
}
