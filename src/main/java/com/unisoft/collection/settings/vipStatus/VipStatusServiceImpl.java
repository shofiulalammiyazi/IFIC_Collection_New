package com.unisoft.collection.settings.vipStatus;
/*
Created by Yasir Araphat on 01/02/2021
*/

import com.unisoft.audittrail.AuditTrailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VipStatusServiceImpl implements VipStatusService {

    private final VipStatusRepository repository;
    private final AuditTrailService auditTrailService;

    @Override
    public List<VipStatus> getList() {
        return repository.findAll();
    }

    @Override
    public String save(VipStatus entity) {
        boolean isNewEntity = false;

        if (entity.getId() == null)
            isNewEntity = true;

        VipStatus previousEntity = new VipStatus();
        if (!isNewEntity){
            VipStatus oldEntity = repository.getOne(entity.getId());
            BeanUtils.copyProperties(oldEntity, previousEntity);
        }

        entity.setName(entity.getName().trim());
        if (alreadyExists(entity)) return "Customer type already exists";
        repository.save(entity);

        if (isNewEntity)
            auditTrailService.saveCreatedData("VIP Status", entity);
        else
            auditTrailService.saveUpdatedData("VIP Status", previousEntity, entity);

        return "1";
    }

    @Override
    public VipStatus getById(Long id) {
        return repository.findById(id).orElse(new VipStatus());
    }

    @Override
    public List<VipStatus> getActiveList() {
        return repository.findByEnabled(true);
    }

    @Override
    public List<String> getVipStatusNameList() {
        return repository.findVipStatusNames();
    }

    private boolean alreadyExists(VipStatus entity) {
        if (entity.getId() == null) {
            return repository.existsByName(entity.getName());
        } else {
            VipStatus oldEntry = repository.findById(entity.getId()).orElse(new VipStatus());
            return !entity.getName().equals(oldEntry.getName()) && repository.existsByName(entity.getName());
        }
    }


}
