package com.unisoft.collection.KPI.Card.TargetByAmount;
/*
  Created by Md.   Islam on 8/28/2019
*/


import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardKPITargetByAmountService {

    @Autowired
    private CardKPITargetByAmountDao cardKPITargetByAmountDao;

    public CardKPITargetByAmountEntity getData()
    {
        return cardKPITargetByAmountDao.getData();
    }

    public boolean addNew(CardKPITargetByAmountEntity cardKPITargetByAmountEntity)
    {
        return cardKPITargetByAmountDao.saveNew(cardKPITargetByAmountEntity);
    }

    public CardKPITargetByAmountEntity getByid(Long id)
    {
        return cardKPITargetByAmountDao.getById(id);
    }

    public List<CardKPITargetByAmountEntity> getAll()
    {
        return  cardKPITargetByAmountDao.getAllData();
    }

    public boolean updateData(CardKPITargetByAmountEntity cardKPITargetByAmountEntity)
    {
        return cardKPITargetByAmountDao.updateData(cardKPITargetByAmountEntity);
    }

    public CardKPITargetByAmountEntity getByProductAgeCodeAndDealerPin(ProductTypeEntity typeEntity, AgeCodeEntity ageCodeEntity, String dealerPin, LocationEntity locationEntity)
    {
        return cardKPITargetByAmountDao.getByProductAgeCodeAndDealerPin(typeEntity,ageCodeEntity,dealerPin,locationEntity);
    }
}
