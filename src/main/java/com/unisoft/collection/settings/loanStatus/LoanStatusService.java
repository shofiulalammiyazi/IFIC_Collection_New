package com.unisoft.collection.settings.loanStatus;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanStatusService {

    @Autowired
    private LoanStatusRepository loanStatusRepository;

    public LoanStatusEntity geByLoanStatusId(Long id){
        return loanStatusRepository.getOne(id);
    }
    public LoanStatusEntity saveNew(LoanStatusEntity loanStatus) {
        return loanStatusRepository.save(loanStatus);
    }

    public List<LoanStatusEntity> getAll(){

        return loanStatusRepository.findAll();
    }




}
