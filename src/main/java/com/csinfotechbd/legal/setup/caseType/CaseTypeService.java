package com.csinfotechbd.legal.setup.caseType;

import com.csinfotechbd.audittrail.AuditTrailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CaseTypeService {

    private final CaseTypeRepository repository;
    private final AuditTrailService auditTrailService;

    public void saveAllCaseType(List<CaseType> caseTypelist) {
        repository.saveAll(caseTypelist);
        auditTrailService.saveCreatedData("Case Type", caseTypelist);
    }

    public List<CaseType> findAllCaseType() {
        return repository.findAll();
    }

    public void updateAll(List<CaseType> caseTypeList) {
        List<Long> ids = new ArrayList<>();
        for (CaseType type : caseTypeList)
            ids.add(type.getId());
        List<CaseType> oldEntities = repository.findAllById(ids);
        List<CaseType> previousEntities = new ArrayList<>(oldEntities.size());
        for (CaseType type : oldEntities) {
            CaseType previousEntity = new CaseType();
            BeanUtils.copyProperties(type, previousEntity);
            previousEntities.add(previousEntity);
        }

        repository.saveAll(caseTypeList);
        auditTrailService.saveUpdatedData("Case Type", previousEntities, caseTypeList);
    }

    public CaseType findCaseType(Long cid) {
        return repository.findById(cid).orElse(new CaseType());
    }

    public CaseType findByNameLike(String name) {
        List<CaseType> caseTypes = repository.findByNameContainsOrderBySequence(name);
        return caseTypes.isEmpty() ? null : caseTypes.get(0);
    }

    public List<CaseType> findByCaseFiledType(Long caseFiledTypeId) {
        return repository.findByCaseFiledTypeIdOrderBySequence(caseFiledTypeId);
    }

    public List<CaseType> findByCaseFiledType(Long caseFiledTypeId, boolean enabled) {
        return repository.findByCaseFiledTypeIdAndEnabledOrderBySequence(caseFiledTypeId, enabled);
    }

    public List<CaseType> findByCaseFiledTypeName(String caseFiledTypeName) {
        return repository.findByCaseFiledTypeNameAndEnabledOrderBySequence(caseFiledTypeName, true);
    }

    public List<CaseType> findByCaseFiledSubTypeName(String caseFiledSubTypeName) {
        return repository.findByCaseFiledSubTypeNameAndEnabledOrderBySequence(caseFiledSubTypeName, true);
    }

    public void edit(CaseType caseType) {
        CaseType oldType = repository.getOne(caseType.getId());
        CaseType previousType = new CaseType();
        BeanUtils.copyProperties(oldType, previousType);

        repository.save(caseType);
        auditTrailService.saveUpdatedData("Case Type", previousType, caseType);
    }

    public List<CaseType> findByCaseFiledTypeAndCaseFiledSubType(Long caseFiledTypeId, Long caseFiledSubType) {
        return repository.findByCaseFiledTypeIdAndCaseFiledSubTypeIdOrderBySequence(caseFiledTypeId, caseFiledSubType);
    }

    public List<CaseType> findByCaseFiledTypeAndCaseFiledSubType(Long caseFiledTypeId, Long caseFiledSubType, boolean enabled) {
        return repository.findByCaseFiledTypeIdAndCaseFiledSubTypeIdAndEnabledOrderBySequence(caseFiledTypeId, caseFiledSubType, enabled);
    }

    public List<CaseTypeDto> getEnabledCaseTypeDtoList() {
        return repository.findEnabledCaseTypes(true);
    }

    public CaseType findByName(String ni_act) {
        return repository.findByName(ni_act);
    }
}
