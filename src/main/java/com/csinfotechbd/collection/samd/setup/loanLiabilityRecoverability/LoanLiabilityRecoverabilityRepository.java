package com.csinfotechbd.collection.samd.setup.loanLiabilityRecoverability;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanLiabilityRecoverabilityRepository extends JpaRepository<LoanLiabilityRecoverability, Long> {


    LoanLiabilityRecoverability findLoanLiabilityRecoverabilityById(Long id);
}
