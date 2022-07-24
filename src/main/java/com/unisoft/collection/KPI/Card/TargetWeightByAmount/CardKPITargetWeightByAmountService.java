package com.unisoft.collection.KPI.Card.TargetWeightByAmount;
/*
  Created by Md.   Islam on 9/3/2019
*/

import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardKPITargetWeightByAmountService {

    @Autowired
    private CardKPITargetWeightByAmountDao cardKPITargetWeightByAmountDao;


    public CardKPITargetWeightByAmountEntity getData()
    {
        return cardKPITargetWeightByAmountDao.getData();
    }

    public boolean addNew(CardKPITargetWeightByAmountEntity cardKPITargetWeightByAmountEntity)
    {
        return cardKPITargetWeightByAmountDao.saveNew(cardKPITargetWeightByAmountEntity);
    }

    public CardKPITargetWeightByAmountEntity getByid(Long id)
    {
        return cardKPITargetWeightByAmountDao.getById(id);
    }

    public List<CardKPITargetWeightByAmountEntity> getAll()
    {
        return  cardKPITargetWeightByAmountDao.getAllData();
    }

    public boolean updateData(CardKPITargetWeightByAmountEntity cardKPITargetWeightByAmountEntity)
    {
        return cardKPITargetWeightByAmountDao.updateData(cardKPITargetWeightByAmountEntity);
    }

    public CardKPITargetWeightByAmountEntity getByProductAgeCodeAndDealerPin(ProductTypeEntity productTypeEntity, AgeCodeEntity ageCodeEntity, String dealerPin)
    {
        return cardKPITargetWeightByAmountDao.getByProductAgeCodeAndDealerPin(productTypeEntity,ageCodeEntity,dealerPin);
    }
}
