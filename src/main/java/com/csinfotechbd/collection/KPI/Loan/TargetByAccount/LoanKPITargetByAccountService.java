package com.csinfotechbd.collection.KPI.Loan.TargetByAccount;
/*
  Created by Md. Monirul Islam on 8/28/2019
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LoanKPITargetByAccountService {

    @Autowired
    private LoanKPITargetByAccountDao loanKPITargetByAccountDao;

    public LoanKPITargetByAccountEntity getData()
    {
        return loanKPITargetByAccountDao.getData();
    }

    public boolean addNew(LoanKPITargetByAccountEntity loanKPITargetByAccountEntity)
    {
        return loanKPITargetByAccountDao.saveNew(loanKPITargetByAccountEntity);
    }

    public List<LoanKPITargetByAccountEntity> getAllData()
    {
        return loanKPITargetByAccountDao.getAllData();
    }

    public boolean updateData(LoanKPITargetByAccountEntity loanKPITargetByAccount)
    {
        return loanKPITargetByAccountDao.updateData(loanKPITargetByAccount);
    }

    public LoanKPITargetByAccountEntity getById(Long id)
    {
        return loanKPITargetByAccountDao.getById(id);
    }

    public List<LoanKPITargetByAccountEntity> getAllDataForDist(Date distDate)
    {
        return loanKPITargetByAccountDao.getAllDataForDist(distDate);
    }
}
