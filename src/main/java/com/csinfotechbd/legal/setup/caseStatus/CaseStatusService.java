package com.csinfotechbd.legal.setup.caseStatus;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CaseStatusService {

    String save(CaseStatus caseStatus);

    CaseStatus findByNameLike(String caseStatusName);

    List<CaseStatus> findAll();

    List<CaseStatus> findByEnabled(boolean enabled);

    List<CaseStatus> findByCaseType(Long caseTypeId);

    CaseStatus findById(Long id);

    boolean existsByName(String name);

    void deleteById(Long id);

}
