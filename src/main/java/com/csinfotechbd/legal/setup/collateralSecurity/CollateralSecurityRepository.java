package com.csinfotechbd.legal.setup.collateralSecurity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollateralSecurityRepository extends JpaRepository<CollateralSecurity, Long> {

    List<CollateralSecurity> findByEnabledOrderBySequence(boolean enabled);

    @Query("SELECT c FROM CollateralSecurity c JOIN c.caseTypes ct WHERE ct.id = :caseTypeId ORDER BY c.sequence")
    List<CollateralSecurity> findByCaseType(@Param("caseTypeId") long caseTypeId);

    boolean existsByName(String type);
}
