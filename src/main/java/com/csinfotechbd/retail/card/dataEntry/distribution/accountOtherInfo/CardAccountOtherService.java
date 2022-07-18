package com.csinfotechbd.retail.card.dataEntry.distribution.accountOtherInfo;
/*
Created by Monirul Islam at 7/21/2019 
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardAccountOtherService {
    @Autowired
    private CardAccountOtherDao cardAccountOtherDao;

    public List<CardAccountOtherInfo> getAll()
    {
        return cardAccountOtherDao.getList();
    }

    public boolean saveNew(CardAccountOtherInfo agency)
    {
        return cardAccountOtherDao.saveNew(agency);
    }

    public boolean updateAgency(CardAccountOtherInfo agency)
    {
        return cardAccountOtherDao.updateObj(agency);
    }

    public CardAccountOtherInfo getById(Long Id)
    {
        return cardAccountOtherDao.getById(Id);
    }

    public List<CardAccountOtherInfo> getActiveList()
    {
        return cardAccountOtherDao.getActiveOnly();
    }
}
