package com.csinfotechbd.legal.setup.courts;

import com.csinfotechbd.audittrail.AuditTrailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourtsServiceImpl implements CourtsService {

    private final CourtsRepository repository;
    private final AuditTrailService auditTrailService;

    @Override
    public String save(Courts courts) {
//        if (existsByName(courts.getName()))
//            return "Court name already exists";

        boolean isNewEntity = false;
        if (courts.getId() == null) {
            isNewEntity = true;
        }

        Courts oldEndity = null;
        Courts previousEntity = new Courts();
        if (!isNewEntity) {
            oldEndity = repository.getOne(courts.getId());
            BeanUtils.copyProperties(oldEndity, previousEntity);
        }

        repository.save(courts);
        if (isNewEntity)
            auditTrailService.saveCreatedData("Courts", courts);
        else
            auditTrailService.saveUpdatedData("Courts", previousEntity, courts);
        return "1";
    }

//    @Override
//    public Courts findOrSave(Courts courts) {
//        if (courts.getName() != null && courts.getCaseTypes() != null && !courts.getCaseTypes().isEmpty()) {
//            return repository.findByName(courts.getName()).orElseGet(() -> {
//                save(courts);
//                return courts;
//            });
//        }
//        return null;
//    }

    @Override
    public List<Courts> findAll() {
        return repository.findAll();
    }

//    @Override
//    public List<String> findCourtNamesByCaseType(Long caseTypeId) {
//        return repository.findCourtNamesByCaseTypeId(caseTypeId);
//    }

    @Override
    public List<Courts> findByNames(List<String> courtNames) {
        return repository.findByNameInOrderByCreatedDateAsc(courtNames);
    }

    @Override
    public Courts findByCaseTypeAndName(Long caseTypeId, String courtName) {
        List<Courts> courts = repository.findByCaseTypeAndName(caseTypeId, courtName);
        return courts.isEmpty() ? null : courts.get(0);
    }

    @Override
    public List<Courts> findByEnabled(boolean enabled) {
        return repository.findByEnabledOrderBySequence(enabled);
    }

    @Override
    public List<Courts> findByCaseType(Long caseTypeId) {
        return repository.findByCaseTypeId(caseTypeId);
    }

    @Override
    public Courts findById(Long id) {
        return repository.findById(id).orElse(new Courts());
    }

    @Override
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
