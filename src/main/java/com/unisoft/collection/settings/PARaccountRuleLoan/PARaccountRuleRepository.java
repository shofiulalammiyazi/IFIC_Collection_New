package com.unisoft.collection.settings.PARaccountRuleLoan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PARaccountRuleRepository extends JpaRepository<PARaccountRuleLoanEntity, Long> {
    @Query(value = "SELECT MAX(MAX_DPD) AS bucket fROM PAR_ACC_RULE_LOAN", nativeQuery = true)
    double getMaxBucket();
}
