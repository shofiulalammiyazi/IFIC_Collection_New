package com.csinfotechbd.legal.setup.litigationZone;

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.collection.settings.branch.Branch;
import com.csinfotechbd.common.CommonService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LitigationZoneService implements CommonService<LitigationZoneEntity> {

    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private LitigationZoneRepository litigationZoneRepository;

    @Override
    public Map<String, Object> storeData(LitigationZoneEntity data) {
        return null;
    }

    @Override
    public Map<String, Object> updateData(LitigationZoneEntity data) {
        Map<String, Object> response = new HashMap<>();
        List<String> errors = this.validate(data);

        if (errors.size() == 0) {
            boolean isNewEntity = false;
            LitigationZoneEntity previousEntity = new LitigationZoneEntity();
            if (data.getId() == null)
                isNewEntity = true;
            else {
                LitigationZoneEntity oldEntity = litigationZoneRepository.getOne(data.getId());
                BeanUtils.copyProperties(oldEntity, previousEntity);
            }

            List<Branch> branchEntities = new ArrayList<>();
            for (String id : data.getBranchIds()) {
                Branch branchEntity = new Branch();
                branchEntity.setBranchId(Integer.valueOf(id));
                branchEntities.add(branchEntity);
            }
            data.setBranches(branchEntities);

            litigationZoneRepository.save(data);

            if (isNewEntity)
                auditTrailService.saveCreatedData("Litigation Zone", data);
            else
                auditTrailService.saveUpdatedData("Litigation Zone", previousEntity, data);
            response.put("outcome", "success");
        } else {
            response.put("outcome", "failure");
            response.put("errorMessages", errors);
        }

        return response;
    }

    @Override
    public Map<String, Object> deleteData(LitigationZoneEntity data) {
        return null;
    }

    @Override
    public LitigationZoneEntity findDataById(Long id) {
        LitigationZoneEntity entity = litigationZoneRepository.getOne(id);
        return entity;
    }

    @Override
    public List<LitigationZoneEntity> findAllData() {
        List<LitigationZoneEntity> entities = litigationZoneRepository.findAll();
        return entities;
    }

    public List<LitigationZoneEntity> findByEnabled(boolean enabled) {
        List<LitigationZoneEntity> entities = litigationZoneRepository.findByEnabled(enabled);
        return entities;
    }

    public List<LitigationZoneDto> findActiveListWithBranchNames() {
        List<LitigationZoneDto> entities = litigationZoneRepository.findDtosWithBranchNames()
                .stream().map(LitigationZoneDto::new).collect(Collectors.toList());
        return entities;
    }

    public LitigationZoneEntity findByNameMatches(String name) {

        String baseIdentifier = name.replaceAll("\\D+", "");
        name = name.replaceAll("\\d+", "");

        List<Tuple> list = litigationZoneRepository.findFirstByNameMatches(name);

        if (list.size() == 1) return new LitigationZoneEntity(list.get(0));

        for (Tuple data : list) {
            String dataIdentifier = Objects.toString(data.get("NAME"), "").replaceAll("\\D+", "");
            if (baseIdentifier.equals(dataIdentifier))
                return new LitigationZoneEntity(data);
        }
        return null;
    }

    public List<LitigationZoneDto> getZonesContainingCases() {
        return litigationZoneRepository.getAssignedZonesWithDistrictNames().stream().map(LitigationZoneDto::new).collect(Collectors.toList());
    }

    @Override
    public List<String> validate(LitigationZoneEntity data) {
        ArrayList<String> errors = new ArrayList<>();
        return errors;
    }

}
