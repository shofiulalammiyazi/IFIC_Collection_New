package com.unisoft.collection.KPI.Loan.TargetExceptionRule;
/*
  Created by Md.   Islam on 9/4/2019
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanKPITargetExceptionRuleService {

    @Autowired
    private LoanKPITargetExceptionRuleDao loanKPITargetExceptionRuleDao;

    public LoanKPITargetExceptionRuleEntity getData()
    {
        return loanKPITargetExceptionRuleDao.getData();
    }

    public boolean addNew(LoanKPITargetExceptionRuleEntity loanKPITargetWeightByAccountEntity)
    {
        return loanKPITargetExceptionRuleDao.saveNew(loanKPITargetWeightByAccountEntity);
    }

    public List<LoanKPITargetExceptionRuleEntity> getAllData()
    {
        return loanKPITargetExceptionRuleDao.getAllData();
    }

    public boolean updateData(LoanKPITargetExceptionRuleEntity loanKPITargetWeightByAccountEntity)
    {
        return loanKPITargetExceptionRuleDao.updateData(loanKPITargetWeightByAccountEntity);
    }

    public LoanKPITargetExceptionRuleEntity getById(Long id)
    {
        return loanKPITargetExceptionRuleDao.getById(id);
    }
}
