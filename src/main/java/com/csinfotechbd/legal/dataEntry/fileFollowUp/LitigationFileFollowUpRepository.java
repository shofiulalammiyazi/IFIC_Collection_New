package com.csinfotechbd.legal.dataEntry.fileFollowUp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface LitigationFileFollowUpRepository extends JpaRepository<LitigationFileFollowUp, Long>, RevisionRepository<LitigationFileFollowUp, Long, Integer> {

    Page<LitigationFileFollowUp> findByLoanAccountNumberAndEnabled(String customerAccountNumber, boolean enabled, Pageable pageable);
    List<LitigationFileFollowUp> findByLoanAccountNumber(String customerAccountNumber);

//    Page<LitigationFileFollowUp> findByCustomerAccountNumberAndLastModifiedEntryAndEnabled(String customerAccountNumber, boolean lastModifiedEntry, boolean enabled, Pageable pageable);
//
//    Page<LitigationFileFollowUp> findByLastModifiedEntryAndEnabled(boolean lastModifiedEntry, boolean enabled, Pageable pageable);
//
//    Optional<LitigationFileFollowUp> findByCustomerAccountNumberAndLastModifiedEntryAndEnabled(String customerAccountNumber, boolean lastModifiedEntry, boolean enabled);
//
//    boolean existsByCustomerAccountNumberAndEnabled(String customerAccountNumber, boolean enabled);

//    @Modifying
//    @Query("UPDATE LitigationFileFollowUp f SET f.lastModifiedEntry = false WHERE f.customerAccountNumber = :accNo AND f.lastModifiedEntry = true")
//    Integer updateTheLastModifiedEntry(@Param("accNo") String customerAccountNumber);

//    @Modifying
//    @Query("UPDATE LitigationFileFollowUp f SET f.enabled = :enabled WHERE f.customerAccountNumber = :accNo")
//    Integer updateEnableState(@Param("accNo") String customerAccountNumber, @Param("enabled") boolean enabled);

    @Query("Select COALESCE(MAX(ldNo), :ldNo) from LitigationFileFollowUp")
    long findMaxLdNo(@Param("ldNo") Long ldNo);


}
