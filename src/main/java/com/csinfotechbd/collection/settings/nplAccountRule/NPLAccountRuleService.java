package com.csinfotechbd.collection.settings.nplAccountRule;
/*
Created by Monirul Islam on 7/9/2019 
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NPLAccountRuleService {

    @Autowired
    private NPLAccountRuleDao nplAccountRuleDao;

    public List<NPLAccountRuleEntity> getAll()
    {
        return nplAccountRuleDao.getList();
    }

    public List<NPLAccountRuleEntity> getActiveList()
    {
        return nplAccountRuleDao.getActiveOnly();
    }

    public boolean saveNew(NPLAccountRuleEntity nplAccountRule)
    {
        return nplAccountRuleDao.saveNew(nplAccountRule);
    }

    public boolean updateNPLRULE(NPLAccountRuleEntity nplAccountRule)
    {
        return nplAccountRuleDao.updateObj(nplAccountRule);
    }

    public NPLAccountRuleEntity getById(Long Id)
    {
        return nplAccountRuleDao.getById(Id);
    }
}
