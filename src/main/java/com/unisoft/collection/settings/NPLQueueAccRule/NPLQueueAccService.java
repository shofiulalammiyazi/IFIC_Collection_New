package com.unisoft.collection.settings.NPLQueueAccRule;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NPLQueueAccService {

    @Autowired
    private NPLQueueAccRuleDao nplQueueAccRuleDao;

    public List<NPLQueueAccRuleEntity> getAll()
    {
        return nplQueueAccRuleDao.getList();
    }

    public List<NPLQueueAccRuleEntity> getActiveList()
    {
        return nplQueueAccRuleDao.getActiveOnly();
    }

    public boolean saveNew(NPLQueueAccRuleEntity nplAccountRule)
    {
        return nplQueueAccRuleDao.saveNew(nplAccountRule);
    }

    public boolean updateNPLQueu(NPLQueueAccRuleEntity nplAccountRule)
    {
        return nplQueueAccRuleDao.updateObj(nplAccountRule);
    }

    public NPLQueueAccRuleEntity getById(Long Id)
    {
        return nplQueueAccRuleDao.getById(Id);
    }
}
