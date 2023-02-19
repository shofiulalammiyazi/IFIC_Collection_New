package com.unisoft.collection.settings.loanType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanTypeService {
    @Autowired
    private LoanTypeRepository loanTypeRepository;


    public LoanTypeEntity geByLoanTypeId(Long id){
        return loanTypeRepository.getOne(id);
    }
    public LoanTypeEntity saveNew(LoanTypeEntity loanTypeEntity) {
        return loanTypeRepository.save(loanTypeEntity);
    }

    public List<LoanTypeEntity> getAll(){

        return loanTypeRepository.findAll();
    }
}
