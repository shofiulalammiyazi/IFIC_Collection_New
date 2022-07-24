package com.unisoft.collection.samd.dataEntry.detailsOfHeadOfficeApproval;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailsOfHeadOfficeApprovalService {
    @Autowired
    private DetailsOfHeadOfficeRepository repository;

    public DetailsOfHeadOfficeApproval saveDetailsAccount(DetailsOfHeadOfficeApproval obj){
        return  repository.save(obj);
    }

    public List<DetailsOfHeadOfficeApproval> viewByAccountNo(String accountNo){

        return repository.findAllByLoanAccountNo(accountNo);
    }

}
