package com.csinfotechbd.collection.KPI.Card.TargetWeightByAccount;
/*
  Created by Md. Monirul Islam on 9/3/2019
*/

import com.csinfotechbd.collection.settings.ageCode.AgeCodeEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardKPITargetWeightByAccountService {

    @Autowired
    private CardKPITargetWeightByAccountDao cardKPITargetWeightByAccountDao;


    public CardKPITargetWeightByAccountEntity getData()
    {
        return cardKPITargetWeightByAccountDao.getData();
    }

    public boolean addNew(CardKPITargetWeightByAccountEntity cardKPITargetWeightByAccountEntity)
    {
        return cardKPITargetWeightByAccountDao.saveNew(cardKPITargetWeightByAccountEntity);
    }

    public CardKPITargetWeightByAccountEntity getByid(Long id)
    {
        return cardKPITargetWeightByAccountDao.getById(id);
    }

    public List<CardKPITargetWeightByAccountEntity> getAll()
    {
        return  cardKPITargetWeightByAccountDao.getAllData();
    }

    public boolean updateData(CardKPITargetWeightByAccountEntity cardKPITargetWeightByAccountEntity)
    {
        return cardKPITargetWeightByAccountDao.updateData(cardKPITargetWeightByAccountEntity);
    }

    public List<CardKPITargetWeightByAccountEntity> getAllActive()
    {
        return cardKPITargetWeightByAccountDao.getAllActive();
    }

    public CardKPITargetWeightByAccountEntity getByProductAndAgeCode(ProductTypeEntity productTypeEntity, AgeCodeEntity ageCodeEntity,String userPin)
    {
        return cardKPITargetWeightByAccountDao.getByProductAndAgeCode(productTypeEntity,ageCodeEntity,userPin);
    }
}
