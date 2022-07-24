package com.unisoft.cardprofile.cardOtherAccountInfo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardOtherAccountInfoService {

    @Autowired CardOtherAccountInfoDao cardOtherAccountInfoDao;

    public List<CardOtherAccountInfo> getAll()
    {
        return cardOtherAccountInfoDao.getList();
    }

    public boolean saveNew(CardOtherAccountInfo agency)
    {
        return cardOtherAccountInfoDao.saveNew(agency);
    }

    public boolean updateAgency(CardOtherAccountInfo agency)
    {
        return cardOtherAccountInfoDao.updateObj(agency);
    }

    public CardOtherAccountInfo getById(Long Id)
    {
        return cardOtherAccountInfoDao.getById(Id);
    }

    public List<CardOtherAccountInfo> getActiveList()
    {
        return cardOtherAccountInfoDao.getActiveOnly();
    }

    public boolean delete(long id){
        return cardOtherAccountInfoDao.delete(id);
    }
}
