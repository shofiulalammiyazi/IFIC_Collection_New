package com.csinfotechbd.collection.settings.ageAndClassificationRule;
/*
Created by Monirul Islam on 7/9/2019
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgeAndClassificationRuleService {

    @Autowired
    private AgeAndClassificationRuleDao ageAndClassificationRuleDao;

    public List<AgeAndClassifiactionRuleEntity> getAll() {
        return ageAndClassificationRuleDao.getList();
    }

    public List<AgeAndClassifiactionRuleEntity> getActiveList() {
        return ageAndClassificationRuleDao.getActiveOnly();
    }

    public boolean saveNew(AgeAndClassifiactionRuleEntity ageAndClassifiactionRule) {
        return ageAndClassificationRuleDao.saveNew(ageAndClassifiactionRule);
    }

    public boolean updateRule(AgeAndClassifiactionRuleEntity ageAndClassifiactionRuleEntity) {
        return ageAndClassificationRuleDao.updateObj(ageAndClassifiactionRuleEntity);
    }

    public AgeAndClassifiactionRuleEntity getById(Long Id) {
        return ageAndClassificationRuleDao.getById(Id);
    }
}
