package com.csinfotechbd.legal.setup.collateralSecurity;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CollateralSecurityService {

    String save(CollateralSecurity collateralSecurity);

    List<CollateralSecurity> findAll();

    List<CollateralSecurity> findByEnabled(boolean enabled);

    List<CollateralSecurity> findByCaseType(Long caseTypeId);

    CollateralSecurity findById(Long id);

    boolean existsByType(String type);

    void deleteById(Long id);

}
