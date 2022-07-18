package com.csinfotechbd.collection.settings.agency;
/*
Created by Monirul Islam on 7/7/2019
*/

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.user.User;
import com.csinfotechbd.user.UserPrincipal;
import com.csinfotechbd.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgencyService {

    private final AgencyRepository repository;

    private final AuditTrailService auditTrailService;

    private final UserService userService;

    public List<AgencyEntity> getList() {
        return repository.findAll();
    }

    public String save(AgencyEntity entity) {
        boolean isNewEntity = false;
        AgencyEntity previousEntity = new AgencyEntity();

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (entity.getId() == null) {
            entity.setCreatedBy(user.getUsername());
            entity.setCreatedDate(new Date());
            isNewEntity = true;
        } else {
            AgencyEntity oldEntity = repository.getOne(entity.getId());
            BeanUtils.copyProperties(oldEntity, previousEntity);

            entity.setModifiedBy(user.getUsername());
            entity.setModifiedDate(new Date());
        }
        AgencyEntity agencyEntity = repository.save(entity);

        if (isNewEntity)
            auditTrailService.saveCreatedData("Agency", entity);
        else
            auditTrailService.saveUpdatedData("Agency", previousEntity, entity);

        return "1";
    }

    public AgencyEntity getById(Long id) {
        return repository.getOne(id);
    }

    public List<AgencyEntity> getActiveList() {
        return repository.findByEnabled(true);
    }

    public List<AgencyEntity> getAll() {
        return repository.findAll();
    }

    public AgencyEntityDto findAgencyEntityById(Long id) {
        Tuple tuple = repository.findAgencyEntityById(id);

        AgencyEntityDto agencyEntityDto = new AgencyEntityDto();

        agencyEntityDto.setId(tuple.get("id"));
        agencyEntityDto.setName(tuple.get("name"));
        agencyEntityDto.setContactPerson(tuple.get("contactPerson"));
        agencyEntityDto.setContactNo(tuple.get("contactNo"));
        agencyEntityDto.setRegisteredAddress(tuple.get("registeredAddress"));
        agencyEntityDto.setAgreementWithAgency(tuple.get("agreementWithAgency"));
        agencyEntityDto.setDmsFileId(tuple.get("dmsFileId"));
        agencyEntityDto.setDmsFileType(tuple.get("dmsFileType"));
        agencyEntityDto.setFileName(tuple.get("fileName"));
        agencyEntityDto.setEnabled(tuple.get("enabled"));

        return agencyEntityDto;
    }

    public AgencyEntity createAgency(AgencyEntity agencyEntity) {
        AgencyEntity agencyEntity1 = repository.save(agencyEntity);
        return agencyEntity1;
    }

    public AgencyEntity getUserByPin(String username) {
        return repository.findAgencyEntityByPin(username);
    }

    public AgencyDto create(AgencyDto agencyDto) {
        AgencyEntity agencyEntity = new AgencyEntity();
        agencyEntity.setName(agencyDto.getName());
        agencyEntity.setPin(agencyDto.getPin());
        agencyEntity.setContactPerson(agencyDto.getContactPerson());
        agencyEntity.setContactNo(agencyDto.getContactNo());
        agencyEntity.setRegisteredAddress(agencyDto.getRegisteredAddress());
        agencyEntity.setAgreementWithAgency(agencyDto.getAgreementWithAgency());
        AgencyEntity agencyEntity1 = repository.save(agencyEntity);
        userService.saveAgency(agencyDto, agencyEntity1);
        return agencyDto;
    }
}
