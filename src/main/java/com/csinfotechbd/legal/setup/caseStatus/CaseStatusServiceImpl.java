package com.csinfotechbd.legal.setup.caseStatus;

import com.csinfotechbd.audittrail.AuditTrailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaseStatusServiceImpl implements CaseStatusService {

    @Autowired
    CaseStatusRepository caseStatusRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    @Override
    public String save(CaseStatus caseStatus) {
        boolean isNewEntity = false;
//        if (alreadyExists(caseStatus)) return "Case status already exists";

        if (caseStatus.getId() == null)
            isNewEntity = true;

        CaseStatus previousEntity = new CaseStatus();
        if (!isNewEntity) {
            CaseStatus oldEntity = caseStatusRepository.getOne(caseStatus.getId());
            BeanUtils.copyProperties(oldEntity, previousEntity);
        }

        caseStatusRepository.save(caseStatus);

        if (isNewEntity)
            auditTrailService.saveCreatedData("Case Status", caseStatus);
        else
            auditTrailService.saveUpdatedData("Case Status", previousEntity, caseStatus);
        return "1";
    }

    @Override
    public CaseStatus findByNameLike(String caseStatusName) {
        CaseStatus caseStatus = caseStatusRepository.findFirstByNameContainsOrderBySequence(caseStatusName);
        return caseStatus != null ? caseStatus : caseStatusRepository.findFirstByNameContainsOrderBySequence("Others");
    }

    @Override
    public List<CaseStatus> findAll() {
        return caseStatusRepository.findAll();
    }

    @Override
    public List<CaseStatus> findByEnabled(boolean enabled) {
        return caseStatusRepository.findByEnabledOrderBySequence(enabled);
    }

    @Override
    public List<CaseStatus> findByCaseType(Long caseTypeId) {
        return caseStatusRepository.findByCaseTypeId(caseTypeId);
    }

    @Override
    public CaseStatus findById(Long id) {
        return caseStatusRepository.findById(id).orElse(new CaseStatus());
    }

    @Override
    public boolean existsByName(String name) {
        return caseStatusRepository.existsByName(name);
    }

    @Override
    public void deleteById(Long id) {
        caseStatusRepository.deleteById(id);
    }

    private boolean alreadyExists(CaseStatus caseStatus) {
        if (caseStatus.getId() == null) {
            return caseStatusRepository.existsByName(caseStatus.getName());
        } else {
            CaseStatus oldEntry = caseStatusRepository.findById(caseStatus.getId()).orElse(new CaseStatus());
            if (caseStatus.getName().equals(oldEntry.getName())) return false;
            else {
                return caseStatusRepository.existsByName(caseStatus.getName());
            }
        }
    }

}
