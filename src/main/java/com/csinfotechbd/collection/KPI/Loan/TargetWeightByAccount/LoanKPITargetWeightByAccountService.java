package com.csinfotechbd.collection.KPI.Loan.TargetWeightByAccount;
/*
  Created by Md. Monirul Islam on 9/3/2019
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LoanKPITargetWeightByAccountService {

    @Autowired
    private LoanKPITargetWeightByAccountDao loanKPITargetWeightByAccountDao;

    public LoanKPITargetWeightByAccountEntity getData()
    {
        return loanKPITargetWeightByAccountDao.getData();
    }

    public boolean addNew(LoanKPITargetWeightByAccountEntity loanKPITargetWeightByAccountEntity)
    {
        return loanKPITargetWeightByAccountDao.saveNew(loanKPITargetWeightByAccountEntity);
    }

    public List<LoanKPITargetWeightByAccountEntity> getAllData()
    {
        return loanKPITargetWeightByAccountDao.getAllData();
    }

    public boolean updateData(LoanKPITargetWeightByAccountEntity loanKPITargetWeightByAccountEntity)
    {
        return loanKPITargetWeightByAccountDao.updateData(loanKPITargetWeightByAccountEntity);
    }

    public List<LoanKPITargetWeightByAccountEntity> getAllDataDist(Date distDate)
    {
        return loanKPITargetWeightByAccountDao.getAllDataDist(distDate);
    }
    public LoanKPITargetWeightByAccountEntity getById(Long id)
    {
        return loanKPITargetWeightByAccountDao.getById(id);
    }

}
