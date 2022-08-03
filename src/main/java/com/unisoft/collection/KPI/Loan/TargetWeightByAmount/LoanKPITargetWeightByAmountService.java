package com.unisoft.collection.KPI.Loan.TargetWeightByAmount;
/*
  Created by Md.   Islam on 9/3/2019
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LoanKPITargetWeightByAmountService {

    @Autowired
    private LoanKPITargetWeightByAmountDao loanKPITargetWeightByAmountDao;


    public LoanKPITargetWeightByAmountEntity getData()
    {
        return loanKPITargetWeightByAmountDao.getData();
    }

    public boolean addNew(LoanKPITargetWeightByAmountEntity loanKPITargetWeightByAmountEntity)
    {
        return loanKPITargetWeightByAmountDao.saveNew(loanKPITargetWeightByAmountEntity);
    }

    public List<LoanKPITargetWeightByAmountEntity> getAllData(Date distDate)
    {
        return loanKPITargetWeightByAmountDao.getAllData(distDate);
    }

    public boolean updateData(LoanKPITargetWeightByAmountEntity loanKPITargetWeightByAmountEntity)
    {
        return loanKPITargetWeightByAmountDao.updateData(loanKPITargetWeightByAmountEntity);
    }

    public LoanKPITargetWeightByAmountEntity getById(Long id)
    {
        return loanKPITargetWeightByAmountDao.getById(id);
    }

    public List<LoanKPITargetWeightByAmountEntity> getAllActiveData(Date distDate)
    {
        return loanKPITargetWeightByAmountDao.getAllActiveData(distDate);
    }
}
