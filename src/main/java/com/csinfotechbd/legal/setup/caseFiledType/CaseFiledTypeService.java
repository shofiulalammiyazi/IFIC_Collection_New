package com.csinfotechbd.legal.setup.caseFiledType;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CaseFiledTypeService {

    String save(CaseFiledType caseFiledType);

    void batchSave(String... caseFiledTypes);

    List<CaseFiledType> findAll();

    List<CaseFiledType> findByEnabled(boolean enabled);

    CaseFiledType findById(Long id);

    boolean existsByName(String name);

}
