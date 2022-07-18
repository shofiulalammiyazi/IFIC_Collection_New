package com.csinfotechbd.legal.setup.caseFiledType;

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CaseFiledTypeServiceImpl implements CaseFiledTypeService {

    @Autowired
    CaseFiledTypeRepository caseFiledTypeRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    @Override
    public String save(CaseFiledType caseFiledType) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (caseFiledType.getId() == null) {
            boolean exists = caseFiledTypeRepository.existsByName(caseFiledType.getName());
            if (exists) return "Case filed type already exists";
            caseFiledType.setCreatedBy(user.getUsername());
            caseFiledType.setCreatedDate(new Date());
            caseFiledTypeRepository.save(caseFiledType);
            auditTrailService.saveCreatedData("Case Filed Type", caseFiledType);
        } else {
            CaseFiledType oldEntity = caseFiledTypeRepository.getOne(caseFiledType.getId());
            CaseFiledType previousEntity = new CaseFiledType();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            caseFiledType.setModifiedBy(user.getUsername());
            caseFiledType.setModifiedDate(new Date());
            caseFiledTypeRepository.save(caseFiledType);
            auditTrailService.saveUpdatedData("Case Filed Type", previousEntity, caseFiledType);
        }
        return "1";
    }

    @Override
    public void batchSave(String... caseFiledTypes) {
        Arrays.stream(caseFiledTypes)
                .forEach(type ->
                {
                    if (type != null && !type.isEmpty()) {
                        boolean exists = caseFiledTypeRepository.existsByName(type);
                        if (!exists) {
                            CaseFiledType entity = new CaseFiledType(type);
                            caseFiledTypeRepository.save(entity);
                            auditTrailService.saveCreatedData("Case Filed Type", entity);
                        }
                    }
                });
    }

    @Override
    public List<CaseFiledType> findAll() {
        return caseFiledTypeRepository.findAll();
    }

    @Override
    public List<CaseFiledType> findByEnabled(boolean enabled) {
        return caseFiledTypeRepository.findByEnabledOrderBySequence(true);
    }

    @Override
    public CaseFiledType findById(Long id) {
        return caseFiledTypeRepository.findById(id).orElse(new CaseFiledType());
    }

    @Override
    public boolean existsByName(String name) {
        return caseFiledTypeRepository.existsByName(name);
    }
}
