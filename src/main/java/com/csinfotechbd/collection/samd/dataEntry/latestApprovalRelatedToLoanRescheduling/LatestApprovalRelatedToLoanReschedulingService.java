package com.csinfotechbd.collection.samd.dataEntry.latestApprovalRelatedToLoanRescheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LatestApprovalRelatedToLoanReschedulingService {
    @Autowired
    private LatestApprovalRelatedToLoanReschedulingRepository repository;

    public LatestApprovalRelatedToLoanRescheduling saveDetailsAccount(LatestApprovalRelatedToLoanRescheduling obj){
        return  repository.save(obj);
    }

    public List<LatestApprovalRelatedToLoanRescheduling> viewByAccountNo(String accountNo){

        return repository.findAllByLoanAccountNo(accountNo);
    }

}
