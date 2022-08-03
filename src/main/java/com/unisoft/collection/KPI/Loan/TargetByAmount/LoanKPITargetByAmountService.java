package com.unisoft.collection.KPI.Loan.TargetByAmount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LoanKPITargetByAmountService {

    @Autowired
    private LoanKPITargetByAmountDao loanKPITargetByAmountDao;

    public LoanKPITargetByAmountEntity getData()
    {
        return loanKPITargetByAmountDao.getData();
    }

    public boolean addNew(LoanKPITargetByAmountEntity loanKPITargetByAmountEntity)
    {
        return loanKPITargetByAmountDao.saveNew(loanKPITargetByAmountEntity);
    }

    public List<LoanKPITargetByAmountEntity> getAllData()
    {
        return loanKPITargetByAmountDao.getAllData();
    }

    public boolean updateData(LoanKPITargetByAmountEntity loanKPITargetByAmountEntity)
    {
        return loanKPITargetByAmountDao.updateData(loanKPITargetByAmountEntity);
    }

    public LoanKPITargetByAmountEntity getById(Long id)
    {
        return loanKPITargetByAmountDao.getById(id);
    }

    public List<LoanKPITargetByAmountEntity> getAllDataForDist(Date distDate)
    {
        return loanKPITargetByAmountDao.getAllDataForDist(distDate);
    }
}
