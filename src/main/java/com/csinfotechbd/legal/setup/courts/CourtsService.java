package com.csinfotechbd.legal.setup.courts;

import com.csinfotechbd.legal.setup.caseType.CaseType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourtsService {

    String save(Courts courts);

//    Courts findOrSave(Courts courts);

    List<Courts> findAll();

//    List<String> findCourtNamesByCaseType(Long caseTypeId);

    List<Courts> findByNames(List<String> courtNames);

    List<Courts> findByEnabled(boolean enabled);

    List<Courts> findByCaseType(Long caseTypeId);

    Courts findById(Long id);

    Courts findByCaseTypeAndName(Long caseTypeId, String name);

    boolean existsByName(String name);

    void deleteById(Long id);

}
