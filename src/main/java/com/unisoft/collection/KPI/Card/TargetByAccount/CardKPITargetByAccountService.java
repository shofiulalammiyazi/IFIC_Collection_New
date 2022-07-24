package com.unisoft.collection.KPI.Card.TargetByAccount;
/*
  Created by Md.   Islam on 9/2/2019
*/

import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardKPITargetByAccountService {

    @Autowired
    private CardKPITargetByAccountDao cardKPITargetByAccountDao;

    public CardKPITargetByAccountEntity getData()
    {
        return cardKPITargetByAccountDao.getData();
    }

    public boolean addNew(CardKPITargetByAccountEntity cardKPITargetByAccountEntity)
    {
        return cardKPITargetByAccountDao.saveNew(cardKPITargetByAccountEntity);
    }

    public CardKPITargetByAccountEntity getByid(Long id)
    {
        return cardKPITargetByAccountDao.getById(id);
    }

    public List<CardKPITargetByAccountEntity> getAll()
    {
        return  cardKPITargetByAccountDao.getAllData();
    }

    public boolean updateData(CardKPITargetByAccountEntity cardKPITargetByAccountEntity)
    {
        return cardKPITargetByAccountDao.updateData(cardKPITargetByAccountEntity);
    }

    public List<CardKPITargetByAccountEntity> getAllActive()
    {
        return cardKPITargetByAccountDao.getAllActive();
    }

    public CardKPITargetByAccountEntity getByProductAndAgeCodeByDealerPin(ProductTypeEntity productTypeEntity, AgeCodeEntity ageCodeEntity, String dealerPin,String location)
    {
        return cardKPITargetByAccountDao.getByProductAndAgeCodeByDealerPin(productTypeEntity,ageCodeEntity,dealerPin,location);
    }
}
