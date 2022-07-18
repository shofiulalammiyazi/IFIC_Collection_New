package com.csinfotechbd.legal.setup.caseStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaseStatusRepository extends JpaRepository<CaseStatus, Long> {

    List<CaseStatus> findByEnabledOrderBySequence(boolean enabled);

    @Query("SELECT DISTINCT c FROM CaseStatus c JOIN c.caseTypes ct WHERE ct.id = :caseTypeId ORDER BY c.sequence")
    List<CaseStatus> findByCaseTypeId(@Param("caseTypeId") Long caseTypeId);

    CaseStatus findFirstByNameContainsOrderBySequence(@Param("name") String caseStatusName);

    boolean existsByName(String name);
}
