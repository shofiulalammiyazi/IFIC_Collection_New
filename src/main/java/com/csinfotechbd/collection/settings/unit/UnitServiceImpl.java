package com.csinfotechbd.collection.settings.unit;
/*
Created by Yasir Araphat on 15/11/2020
*/

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    private UnitRepository repository;

    @Autowired
    private AuditTrailService auditTrailService;

    @Override
    public List<UnitEntity> getList() {
        return repository.findAll();
    }

    @Override
    public String save(UnitEntity entity) {
        boolean isNewEntity = false;
        UnitEntity previousEntity = new UnitEntity();
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (alreadyExists(entity)) return "Unit code already exists";
        if (entity.getId() == null) {
            entity.setCreatedBy(user.getUsername());
            entity.setCreatedDate(new Date());
            isNewEntity = true;
        } else {
            UnitEntity oldEntity = repository.getOne(entity.getId());
            BeanUtils.copyProperties(oldEntity, previousEntity);

            entity.setModifiedBy(user.getUsername());
            entity.setModifiedDate(new Date());
        }
        repository.save(entity);

        if (isNewEntity)
            auditTrailService.saveCreatedData("Unit", entity);
        else
            auditTrailService.saveUpdatedData("Unit", previousEntity, entity);
        return "1";
    }

    @Override
    public UnitEntity getById(Long id) {
        return repository.findById(id).orElse(new UnitEntity());
    }

    @Override
    public UnitEntity getByName(String name) {
        return repository.findByName(name).orElse(null);
    }

    @Override
    public List<UnitEntity> getByName(String... name) {
        List<String> names = Arrays.stream(name).map(String::toLowerCase).collect(Collectors.toList());
        return repository.findByLowerNamesIn(names);
    }

    @Override
    public List<UnitEntity> getActiveList() {
        return repository.findByEnabled(true);
    }

    private boolean alreadyExists(UnitEntity entity) {
        if (entity.getId() == null) {
            return repository.existsByCode(entity.getCode());
        } else {
            UnitEntity oldEntry = repository.findById(entity.getId()).orElse(new UnitEntity());
            return !entity.getCode().equals(oldEntry.getCode()) && repository.existsByCode(entity.getCode());
        }
    }
}
