package com.csinfotechbd.collection.settings.assetMainClassificationLoan;
/*
Created by Monirul Islam at 7/16/2019 
*/

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class LoanMainClassificationService {
    @Autowired
    private LoanMainClassificationDao repository;

    @Autowired
    private AuditTrailService auditTrailService;

    public List<LoanMainClassification> getAll() {
        return repository.findAll();
    }

    public String save(LoanMainClassification entity) {
        if (alreadyExists(entity)) return "Code already exists";
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (entity.getId() == null) {
            entity.setCreatedBy(user.getUsername());
            entity.setCreatedDate(new Date());
            auditTrailService.saveCreatedData("Asset Main Classification Entry ",entity);
        } else {
            LoanMainClassification oldEntity = repository.findById(entity.getId()).orElse(null);
            LoanMainClassification previousEntity = new LoanMainClassification();
            BeanUtils.copyProperties(oldEntity,previousEntity);

            if (oldEntity == null) return "Failed to update. No existing data found";
            entity.setCreatedBy(oldEntity.getCreatedBy());
            entity.setCreatedDate(oldEntity.getCreatedDate());
            entity.setModifiedBy(user.getUsername());
            entity.setModifiedDate(new Date());
            auditTrailService.saveUpdatedData("Asset Main Classification Entry",previousEntity,entity);
        }

        repository.save(entity);
        return "1";
    }

    public LoanMainClassification getById(Long Id) {
        return repository.findById(Id).orElse(null);
    }

    public List<LoanMainClassification> getActiveList() {
        return repository.findByEnabled(true);
    }

    private boolean alreadyExists(LoanMainClassification entity) {
        if (entity.getId() == null) {
            return repository.existsByCode(entity.getCode());
        } else {
            LoanMainClassification oldEntry = repository.findById(entity.getId()).orElse(new LoanMainClassification());
            return !entity.getCode().equals(oldEntry.getCode()) && repository.existsByCode(entity.getCode());
        }
    }
}
