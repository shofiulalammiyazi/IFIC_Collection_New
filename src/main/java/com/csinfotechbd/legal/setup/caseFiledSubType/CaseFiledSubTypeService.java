package com.csinfotechbd.legal.setup.caseFiledSubType;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CaseFiledSubTypeService {

    String save(CaseFiledSubType courts);

    List<CaseFiledSubType> findAll();

    List<CaseFiledSubType> findByEnabled(boolean enabled);

    CaseFiledSubType findById(Long id);

    boolean existsByName(String name);

    void deleteById(Long id);

}
