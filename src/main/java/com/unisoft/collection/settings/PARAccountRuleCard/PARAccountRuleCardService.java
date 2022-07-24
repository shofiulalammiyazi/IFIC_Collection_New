package com.unisoft.collection.settings.PARAccountRuleCard;
/*
Created by   Islam on 7/14/2019
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PARAccountRuleCardService {

    @Autowired
    private PARAccountRuleCardDao parAccountRuleCardDao;

//    public List<PARAccountRuleCardEntity> getAll()
//    {
//        return parLogicSetupDao.getList();
//    }

//    public PARAccountRuleCardEntity getById(Long id)
//    {
//        return parLogicSetupDao.getById(id);
//    }

    public boolean saveNew(PARAccountRuleCardEntity parLogicSetUp)
    {
        return parAccountRuleCardDao.saveNew(parLogicSetUp);
    }

    public boolean updatePAR(PARAccountRuleCardEntity parLogicSetUp)
    {
        return parAccountRuleCardDao.updateObj(parLogicSetUp);
    }

    public PARAccountRuleCardEntity getPar()
    {
        return parAccountRuleCardDao.getPAR();
    }

//    public List<PARAccountRuleCardEntity> getActiveList()
//    {
//        return parLogicSetupDao.getActiveOnly();
//    }
}
