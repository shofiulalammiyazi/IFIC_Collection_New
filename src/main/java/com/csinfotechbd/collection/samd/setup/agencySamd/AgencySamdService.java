package com.csinfotechbd.collection.samd.setup.agencySamd;

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgencySamdService {

    private final AgencySamdRepository repository;
    private final AuditTrailService auditTrailService;

    public List<AgencySamdEntity> getList() {
        return repository.findAll();
    }

    public String save(AgencySamdEntity entity) {
        boolean isNewEntity = false;
        AgencySamdEntity previousEntity = new AgencySamdEntity();

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (entity.getId() == null) {
            entity.setCreatedBy(user.getUsername());
            entity.setCreatedDate(new Date());
            isNewEntity = true;
        } else {
            AgencySamdEntity oldEntity = repository.getOne(entity.getId());
            BeanUtils.copyProperties(oldEntity, previousEntity);

            entity.setModifiedBy(user.getUsername());
            entity.setModifiedDate(new Date());
        }
        repository.save(entity);

        if (isNewEntity)
            auditTrailService.saveCreatedData("Agency", entity);
        else
            auditTrailService.saveUpdatedData("Agency", previousEntity, entity);

        return "1";
    }

    public AgencySamdEntity getById(Long id) {
        return repository.getOne(id);
    }

    public List<AgencySamdEntity> getActiveList() {
        return repository.findByEnabled(true);
    }

    public List<AgencySamdEntity> getAll() {
        return repository.findAll();
    }

    public AgencySamdEntityDto findAgencySamdEntityById(Long id) {
        Tuple tuple =  repository.findAgencySamdEntityById(id);

        AgencySamdEntityDto agencyEntityDto = new AgencySamdEntityDto();

        agencyEntityDto.setId(tuple.get("id"));
        agencyEntityDto.setName(tuple.get("name"));
        agencyEntityDto.setContactPerson(tuple.get("contactPerson"));
        agencyEntityDto.setContactNo(tuple.get("contactNo"));
        agencyEntityDto.setRemarks(tuple.get("remarks"));
        agencyEntityDto.setDmsFileId(tuple.get("dmsFileId"));
        agencyEntityDto.setDmsFileType(tuple.get("dmsFileType"));
        agencyEntityDto.setFileName(tuple.get("fileName"));
        agencyEntityDto.setEnabled(tuple.get("enabled"));

        return agencyEntityDto;
    }

    public AgencySamdEntity findAgencySamdEntityByName(String agencyName) {
        return repository.findAgencySamdEntityByName(agencyName);
    }
}
