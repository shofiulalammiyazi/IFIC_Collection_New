package com.unisoft.retail.card.dataEntry.distribution.accountInfo;
/*
Created by   Islam at 7/21/2019
*/

import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardAccountService {
    @Autowired
    private CardAccountDao cardAccountDao;

    public List<CardAccountInfo> getAll()
    {
        return cardAccountDao.getList();
    }

    public boolean saveNew(CardAccountInfo agency)
    {
        return cardAccountDao.saveNew(agency);
    }

    public boolean updateAgency(CardAccountInfo agency)
    {
        return cardAccountDao.updateObj(agency);
    }

    public CardAccountInfo getById(Long Id)
    {
        return cardAccountDao.getById(Id);
    }

    public List<CardAccountInfo> getActiveList()
    {
        return cardAccountDao.getActiveOnly();
    }

    public List<CardAccountInfo> findByCardAccountBasicId(CardAccountBasicInfo cardAccountBasicInfo) {
        return cardAccountDao.findByCardAccountBasicId(cardAccountBasicInfo);
    }
}
