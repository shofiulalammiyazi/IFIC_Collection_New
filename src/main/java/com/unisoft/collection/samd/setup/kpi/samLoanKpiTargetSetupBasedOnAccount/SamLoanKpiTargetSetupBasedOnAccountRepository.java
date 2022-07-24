package com.unisoft.collection.samd.setup.kpi.samLoanKpiTargetSetupBasedOnAccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SamLoanKpiTargetSetupBasedOnAccountRepository extends JpaRepository<SamLoanKpiTargetSetupBasedOnAccount, Long> {

    SamLoanKpiTargetSetupBasedOnAccount findSamLoanKpiTargetSetupBasedOnAccountById(Long id);
}
