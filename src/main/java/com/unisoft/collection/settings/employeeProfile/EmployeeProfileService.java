package com.unisoft.collection.settings.employeeProfile;


import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeProfileService {

    private final EmployeeProfileRepository repository;
    private final AuditTrailService auditTrailService;

    public List<EmployeeProfileInfo> getAll() {
        return repository.findAll();
    }

    public boolean saveNew(EmployeeProfileInfo employeeProfileInfo) {
        return repository.save(employeeProfileInfo).getId() != null;
    }

    public String save(EmployeeProfileInfo employeeProfileInfo) {
        boolean isNewEntity = false;
        EmployeeProfileInfo previousEntity = new EmployeeProfileInfo();
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Set job role entities
        employeeProfileInfo.setJobRoleEntitiesFromSelectedIds();

        if (employeeProfileInfo.getId() == null) {
            employeeProfileInfo.setCreatedBy(user.getUsername());
            employeeProfileInfo.setCreatedDate(new Date());
            isNewEntity = true;
        } else {
            EmployeeProfileInfo oldEntity = repository.getOne(employeeProfileInfo.getId());
            BeanUtils.copyProperties(oldEntity, previousEntity);

            employeeProfileInfo.setModifiedBy(user.getUsername());
            employeeProfileInfo.setModifiedDate(new Date());
        }
        repository.save(employeeProfileInfo).getId();

        if (isNewEntity)
            auditTrailService.saveCreatedData("Employee login profile", employeeProfileInfo);
        else
            auditTrailService.saveUpdatedData("Employee login profile", previousEntity, employeeProfileInfo);
        return "1";
    }

    public boolean updateAgency(EmployeeProfileInfo employeeProfileInfo) {
        return repository.save(employeeProfileInfo).getId() != null;
    }

    public EmployeeProfileInfo getById(Long id) {
        return repository.getOne(id);
    }

    public List<EmployeeProfileInfo> getActiveList() {
        return repository.findByEnabled(true);
    }
}
