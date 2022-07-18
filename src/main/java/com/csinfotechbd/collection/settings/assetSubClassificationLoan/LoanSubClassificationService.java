package com.csinfotechbd.collection.settings.assetSubClassificationLoan;
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
public class LoanSubClassificationService {

    @Autowired
    private LoanSubClassificationRepository repository;
    @Autowired
    private AuditTrailService auditTrailService;

    public List<LoanSubClassification> getAll() {
        return repository.findAll();
    }

    public String save(LoanSubClassification entity) {
        if (alreadyExists(entity)) return "Code already exists";
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (entity.getId() == null) {
            entity.setCreatedBy(user.getUsername());
            entity.setCreatedDate(new Date());
            auditTrailService.saveCreatedData("Asset Sub Classification Entry", entity);

        } else {
            LoanSubClassification oldEntity = repository.findById(entity.getId()).orElse(null);
            LoanSubClassification previousEntity = new LoanSubClassification();
            BeanUtils.copyProperties(oldEntity,previousEntity);

            entity.setCreatedBy(oldEntity.getCreatedBy());
            entity.setCreatedDate(oldEntity.getCreatedDate());
            entity.setModifiedBy(user.getUsername());
            entity.setModifiedDate(new Date());
            auditTrailService.saveUpdatedData("Asset Sub Classification Entry",previousEntity,entity);
        }
        repository.save(entity);
        return "1";
    }

    public LoanSubClassification getById(Long id) {
        return repository.findById(id).orElse(new LoanSubClassification());
    }

    public List<LoanSubClassification> getActiveList() {
        return repository.findByEnabled(true);
    }

    private boolean alreadyExists(LoanSubClassification entity) {
        if (entity.getId() == null) {
            return repository.existsByCode(entity.getCode());
        } else {
            LoanSubClassification oldEntry = repository.findById(entity.getId()).orElse(new LoanSubClassification());
            return !entity.getCode().equals(oldEntry.getCode()) && repository.existsByCode(entity.getCode());
        }
    }
}
