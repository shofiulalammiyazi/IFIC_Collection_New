package com.csinfotechbd.legal.setup.caseFiledSubType;


import com.csinfotechbd.audittrail.AuditTrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaseFiledSubTypeServiceImpl implements CaseFiledSubTypeService {

    @Autowired
    CaseFiledSubTypeRepository caseFiledSubTypeRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    @Override
    public String save(CaseFiledSubType subType) {
        boolean exists = caseFiledSubTypeRepository.existsByName(subType.getName());
        if (exists) return "Case sub type already exists";
        caseFiledSubTypeRepository.save(subType);
        auditTrailService.saveCreatedData("Case Filed Sub-Type", subType);
        return "1";
    }

    @Override
    public List<CaseFiledSubType> findAll() {
        return caseFiledSubTypeRepository.findAll();
    }

    @Override
    public List<CaseFiledSubType> findByEnabled(boolean enabled) {
        return caseFiledSubTypeRepository.findByEnabledOrderBySequence(enabled);
    }

    @Override
    public CaseFiledSubType findById(Long id) {
        return caseFiledSubTypeRepository.findById(id).orElse(new CaseFiledSubType());
    }

    @Override
    public boolean existsByName(String name) {
        return caseFiledSubTypeRepository.existsByName(name);
    }

    @Override
    public void deleteById(Long id) {
        caseFiledSubTypeRepository.deleteById(id);
    }
    
}
