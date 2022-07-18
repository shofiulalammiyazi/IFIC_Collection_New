package com.csinfotechbd.collection.KPI.Card.TargetExceptionRule;
/*
  Created by Md. Monirul Islam on 9/4/2019
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardKPITargetExceptionRuleService {

    @Autowired
    private CardKPITargetExceptionRuleDao cardKPITargetExceptionRuleDao;


    public CardKPITargetExceptionRuleEntity getData()
    {
        return cardKPITargetExceptionRuleDao.getData();
    }

    public boolean addNew(CardKPITargetExceptionRuleEntity exceptionRule)
    {
        return cardKPITargetExceptionRuleDao.saveNew(exceptionRule);
    }

    public CardKPITargetExceptionRuleEntity getByid(Long id)
    {
        return cardKPITargetExceptionRuleDao.getById(id);
    }

    public List<CardKPITargetExceptionRuleEntity> getAll()
    {
        return  cardKPITargetExceptionRuleDao.getAllData();
    }

    public boolean updateData(CardKPITargetExceptionRuleEntity exceptionRuleEntity)
    {
        return cardKPITargetExceptionRuleDao.updateData(exceptionRuleEntity);
    }
}
