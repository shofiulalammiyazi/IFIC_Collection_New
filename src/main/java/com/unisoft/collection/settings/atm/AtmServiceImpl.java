package com.unisoft.collection.settings.atm;
/*
Created by Yasir Araphat on 26/11/2020
*/

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
public class AtmServiceImpl implements AtmService {

    private final AtmRepository repository;
    private final AuditTrailService auditTrailService;

    @Override
    public List<AtmEntity> getList() {
        return repository.findAll();
    }

    @Override
    public String save(AtmEntity entity) {
        boolean isNewEntity = false;
        AtmEntity previousEntity = new AtmEntity();

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (entity.getId() == null) {
            entity.setCreatedBy(user.getUsername());
            entity.setCreatedDate(new Date());
            isNewEntity = true;
        } else {
            AtmEntity oldEntity = repository.getOne(entity.getId());
            BeanUtils.copyProperties(oldEntity, previousEntity);

            entity.setModifiedBy(user.getUsername());
            entity.setModifiedDate(new Date());
        }
        repository.save(entity);

        if (isNewEntity)
            auditTrailService.saveCreatedData("ATM", entity);
        else
            auditTrailService.saveUpdatedData("ATM", previousEntity, entity);
        return "1";
    }

    @Override
    public AtmEntity getById(Long id) {
        return repository.findById(id).orElse(new AtmEntity());
    }

    @Override
    public List<AtmEntity> getActiveList() {
        return repository.findByEnabled(true);
    }

}
