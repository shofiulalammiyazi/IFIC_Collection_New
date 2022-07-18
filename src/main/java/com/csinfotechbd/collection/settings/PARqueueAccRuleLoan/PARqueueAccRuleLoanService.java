package com.csinfotechbd.collection.settings.PARqueueAccRuleLoan;
/*
Created by Monirul Islam on 7/10/2019 
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PARqueueAccRuleLoanService {

    @Autowired
    private PARqueueAccRuleLoanDao paRqueueAccRuleLoanDao;
    @Autowired
    private PARqueueAccRuleLoanRepository accRuleLoanRepository;

    public List<PARqueueAccRuleLoanEntity> getAll()
    {
        return paRqueueAccRuleLoanDao.getList();
    }

    public List<PARqueueAccRuleLoanEntity> getActiveList()
    {
        return paRqueueAccRuleLoanDao.getActiveOnly();
    }

    public boolean saveNew(PARqueueAccRuleLoanEntity paRqueueAccRuleLoan)
    {
        return paRqueueAccRuleLoanDao.saveNew(paRqueueAccRuleLoan);
    }

    public boolean updateRule(PARqueueAccRuleLoanEntity paRqueueAccRuleLoan)
    {
        return paRqueueAccRuleLoanDao.updateObj(paRqueueAccRuleLoan);
    }

    public PARqueueAccRuleLoanEntity getById(Long id)
    {
        return paRqueueAccRuleLoanDao.getById(id);
    }

    public PARqueueAccRuleLoanEntity save(PARqueueAccRuleLoanEntity paRqueueAccRuleLoan) {
        return accRuleLoanRepository.save(paRqueueAccRuleLoan);
    }

}
