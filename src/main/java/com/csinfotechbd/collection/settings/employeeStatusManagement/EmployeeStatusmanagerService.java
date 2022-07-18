package com.csinfotechbd.collection.settings.employeeStatusManagement;
/*
Created by Monirul Islam on 7/11/2019 
*/


import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeStatusmanagerService {

    private final EmployeeStatusManagerDao employeeStatusManagerDao;
    private final EmployeeStatusManagerRepository repository;
    private final AuditTrailService auditTrailService;

    public List<EmployeeStatusManagerEntity> getAll() {
        return employeeStatusManagerDao.getList();
    }

    public List<EmployeeStatusManagerEntity> getActiveList() {
        return employeeStatusManagerDao.getActiveOnly();
    }

    public boolean saveNew(EmployeeStatusManagerEntity entity) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        entity.setCreatedBy(user.getUsername());
        entity.setCreatedDate(new Date());
        boolean savedSuccessfully = employeeStatusManagerDao.saveNew(entity);
        if (savedSuccessfully)
            auditTrailService.saveCreatedData("Employee Status Management", entity);
        return savedSuccessfully;
    }

    public boolean updateSts(EmployeeStatusManagerEntity entity) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeStatusManagerEntity oldEntity = getById(entity.getId());
        EmployeeStatusManagerEntity previousEntity = new EmployeeStatusManagerEntity();
        BeanUtils.copyProperties(oldEntity, previousEntity);

        entity.setModifiedBy(user.getUsername());
        entity.setModifiedDate(new Date());
        boolean updatedSuccessfully = employeeStatusManagerDao.updateObj(entity);
        if (updatedSuccessfully)
            auditTrailService.saveUpdatedData("Employee Status Management", previousEntity, entity);
        return updatedSuccessfully;
    }

    public EmployeeStatusManagerEntity getById(Long id) {
        return employeeStatusManagerDao.getById(id);
    }

    public List<EmployeeStatusManagerEntity> getEmpLastStatList(List<Long> empIdList) throws Exception {
        List<EmployeeStatusManagerEntity> empList = new ArrayList<>();
        for (Long empId : empIdList) {
            EmployeeStatusManagerEntity empStat = employeeStatusManagerDao.getEmpLastStatById(empId);

            if (empStat.getEndDate() != null && empStat.getStartDate() != null) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date dateBefore = format.parse(empStat.getStartDate().toString());
                Date dateAfter = format.parse(empStat.getEndDate().toString());

                long difference = dateAfter.getTime() - dateBefore.getTime();
                int daysBetween = (int) (difference / (1000 * 60 * 60 * 24));
                empStat.setTotalDays(String.valueOf(daysBetween));

            }
            empList.add(empStat);
        }

        return empList;
    }

    public EmployeeStatusManagerEntity getByUserId(Long userId) {
        return repository.findFirstByUserIdOrderByIdDesc(userId);
    }

}
