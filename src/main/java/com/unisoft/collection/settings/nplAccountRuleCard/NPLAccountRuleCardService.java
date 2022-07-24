package com.unisoft.collection.settings.nplAccountRuleCard;
/*
Created by   Islam on 7/14/2019
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NPLAccountRuleCardService {

    @Autowired
    private NPLAccountRuleCardDao NPLAccountRuleCardDao;
    
    @Autowired
    private NPLAccountRuleCardRepo nplAccountRuleCardRepo;

    public boolean saveNew(NPLAccountRuleCardEntity parLogicSetUp)
    {
        return NPLAccountRuleCardDao.saveNew(parLogicSetUp);
    }

    public boolean updatePAR(NPLAccountRuleCardEntity parLogicSetUp)
    {
        return NPLAccountRuleCardDao.updateObj(parLogicSetUp);
    }

    public NPLAccountRuleCardEntity getNPL()
    {
        return NPLAccountRuleCardDao.getNPL();
    }

//    public List<PARAccountRuleCardEntity> getActiveList()
//    {
//        return parLogicSetupDao.getActiveOnly();
//    }
    
    public List<NPLAccountRuleCardEntity> allActive(){
        return nplAccountRuleCardRepo.findByEnabled(true);
    }
    public NPLAccountRuleCardEntity save(NPLAccountRuleCardEntity nplAccountRuleCardEntity){
        return nplAccountRuleCardRepo.save(nplAccountRuleCardEntity);
    }
    public NPLAccountRuleCardEntity findById(long id){
        return nplAccountRuleCardRepo.findById(id);
    }
}
