package com.unisoft.collection.samd.setup.loanLiabilityRecoverability;

import java.util.List;

public interface LoanLiabilityRecoverabilityService {
    List<LoanLiabilityRecoverability> findAll();

    void save(LoanLiabilityRecoverability loanLiabilityRecoverability);

    LoanLiabilityRecoverability findLoanLiabilityRecoverabilityById(Long id);
}
