package com.unisoft.collection.samd.setup.loanLiabilityRecoverability;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanLiabilityRecoverabilityServiceImpl implements LoanLiabilityRecoverabilityService{


    @Autowired
    private LoanLiabilityRecoverabilityRepository loanLiabilityRecoverabilityRepository;


    @Override
    public List<LoanLiabilityRecoverability> findAll() {
        return loanLiabilityRecoverabilityRepository.findAll();
    }

    @Override
    public void save(LoanLiabilityRecoverability loanLiabilityRecoverability) {
            loanLiabilityRecoverabilityRepository.save(loanLiabilityRecoverability);
    }

    @Override
    public LoanLiabilityRecoverability findLoanLiabilityRecoverabilityById(Long id) {
        return loanLiabilityRecoverabilityRepository.findLoanLiabilityRecoverabilityById(id);
    }
}
