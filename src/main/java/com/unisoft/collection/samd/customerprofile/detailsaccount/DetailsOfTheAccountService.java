package com.unisoft.collection.samd.customerprofile.detailsaccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailsOfTheAccountService {

    @Autowired
    private  DetailsOfTheAccountRepository repository;

    public DetailsOfTheAccount saveDetailsAccount(DetailsOfTheAccount obj){
       return  repository.save(obj);
    }

    public DetailsOfTheAccount viewByAccountNo(String accountNo){

        return repository.findTopByLoanAccountNoOrderByIdDesc(accountNo);
    }

}
