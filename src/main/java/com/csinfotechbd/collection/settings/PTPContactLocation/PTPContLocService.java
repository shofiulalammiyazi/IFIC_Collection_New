package com.csinfotechbd.collection.settings.PTPContactLocation;
/*
Created by Monirul Islam at 6/30/2019 
*/

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PTPContLocService {

    @Autowired
    private PTPContLocRepository repository;

    @Autowired
    private AuditTrailService auditTrailService;

    public List<PTPContactLocationEntity> getList()
    {
        return repository.findAll();
    }

    public String save(PTPContactLocationEntity locationEntity)
    {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (locationEntity.getId() == null) {
            locationEntity.setCreatedBy(user.getUsername());
            locationEntity.setCreatedDate(new Date());
            auditTrailService.saveCreatedData("PTP Contact Location",locationEntity);
        } else {
            PTPContactLocationEntity oldEntity = repository.findById(locationEntity.getId()).orElse(null);
            PTPContactLocationEntity previousEntity = new PTPContactLocationEntity();
            BeanUtils.copyProperties(oldEntity,previousEntity);

            if (oldEntity == null) return "Failed to update. No existing data found";
            locationEntity.setCreatedBy(oldEntity.getCreatedBy());
            locationEntity.setCreatedDate(oldEntity.getCreatedDate());
            locationEntity.setModifiedBy(user.getUsername());
            locationEntity.setModifiedDate(new Date());
            auditTrailService.saveUpdatedData("PTP Contact Location",previousEntity,locationEntity);
        }
        repository.save(locationEntity);
        return "1";
    }

    public PTPContactLocationEntity getById(Long id)
    {
        repository.getOne(id);
        return repository.findById(id).orElse(new PTPContactLocationEntity());
    }

    public List<PTPContactLocationEntity> getActiveList()
    {
        return repository.findByEnabled(true);
    }
}
