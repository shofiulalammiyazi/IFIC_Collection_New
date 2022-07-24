package com.unisoft.collection.settings.PARaccountRuleLoan;
/*
Created by   Islam on 7/10/2019
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PARaccountRuleLoanService {

    @Autowired
    private PARaccountRuleLoanDao paRaccountRuleLoanDao;
    @Autowired
    private PARaccountRuleRepository paRaccountRuleRepository;

    public List<PARaccountRuleLoanEntity> getAll()
    {
        return paRaccountRuleLoanDao.getList();
    }

    public PARaccountRuleLoanEntity getById(Long id)
    {
        return paRaccountRuleLoanDao.getById(id);
    }

    public boolean saveNew(PARaccountRuleLoanEntity raccountRuleLoanEntity)
    {
        return paRaccountRuleLoanDao.saveNew(raccountRuleLoanEntity);
    }

    public boolean updatePAR(PARaccountRuleLoanEntity raccountRuleLoanEntity)
    {
        return paRaccountRuleLoanDao.updateObj(raccountRuleLoanEntity);
    }

    public List<PARaccountRuleLoanEntity> getActiveList()
    {
        return paRaccountRuleLoanDao.getActiveOnly();
    }

    public double getMaxBucket() {
        return paRaccountRuleRepository.getMaxBucket();
    }
}
