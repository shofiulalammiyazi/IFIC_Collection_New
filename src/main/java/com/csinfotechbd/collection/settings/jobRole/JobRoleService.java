package com.csinfotechbd.collection.settings.jobRole;
/*
Created by Monirul Islam at 6/25/2019

*/


import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JobRoleService {

    @Autowired
    private JobRoleRepository jobRoleDao;

    @Autowired
    private AuditTrailService auditTrailService;

    public List<JobRoleEntity> getAllRole() {
        return jobRoleDao.findAll();
    }

    public String save(JobRoleEntity jobRole) {
        boolean alreadyExists = alreadyExists(jobRole);
        JobRoleEntity previousEntity = new JobRoleEntity();
        boolean isNewEntity = false;
        if (alreadyExists) return "Job role code already exists";
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (jobRole.getId() == null) {
            jobRole.setCreatedBy(user.getUsername());
            jobRole.setCreatedDate(new Date());
            isNewEntity = true;
        } else {
            JobRoleEntity oldEntity = jobRoleDao.getOne(jobRole.getId());
            BeanUtils.copyProperties(oldEntity, previousEntity);

            jobRole.setModifiedBy(user.getUsername());
            jobRole.setModifiedDate(new Date());
        }
        jobRoleDao.save(jobRole);

        if (isNewEntity)
            auditTrailService.saveCreatedData("Job Role", jobRole);
        else
            auditTrailService.saveUpdatedData("Job Role", previousEntity, jobRole);
        return "1";
    }

    public JobRoleEntity getById(Long Id) {
        return jobRoleDao.findById(Id).orElse(null);
    }

    public List<JobRoleEntity> getActiveList() {
        return jobRoleDao.findByEnabled(true);
    }


    private boolean alreadyExists(JobRoleEntity jobRole){
        if (jobRole.getId() == null){
            return jobRoleDao.existsByCode(jobRole.getCode());
        }else {
            JobRoleEntity oldEntry = jobRoleDao.findById(jobRole.getId()).orElse(new JobRoleEntity());
            return !jobRole.getCode().equals(oldEntry.getCode()) && jobRoleDao.existsByCode(jobRole.getCode());
        }
    }

}
