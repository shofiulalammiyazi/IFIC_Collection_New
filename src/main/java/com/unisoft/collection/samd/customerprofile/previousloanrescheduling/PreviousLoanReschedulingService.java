package com.unisoft.collection.samd.customerprofile.previousloanrescheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreviousLoanReschedulingService {

    @Autowired
    private PreviousLoanReschedulingRepository repository;

    public PreviousLoanRescheduling saveDetailsAccount(PreviousLoanRescheduling obj){
       return  repository.save(obj);
    }

    public List<PreviousLoanRescheduling> viewByAccountNo(String accountNo){

        return repository.findAllByLoanAccountNo(accountNo);
    }

}
