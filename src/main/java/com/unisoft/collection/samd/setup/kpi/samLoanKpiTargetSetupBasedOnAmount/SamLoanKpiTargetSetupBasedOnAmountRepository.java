package com.unisoft.collection.samd.setup.kpi.samLoanKpiTargetSetupBasedOnAmount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SamLoanKpiTargetSetupBasedOnAmountRepository extends JpaRepository<SamLoanKpiTargetSetupBasedOnAmount, Long> {

    SamLoanKpiTargetSetupBasedOnAmount findSamLoanKpiTargetSetupBasedOnAmountById(Long id);
}
