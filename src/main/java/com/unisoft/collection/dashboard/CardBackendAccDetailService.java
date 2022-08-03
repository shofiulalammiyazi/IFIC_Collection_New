package com.unisoft.collection.dashboard;
/*
  Created by Md.   Islam on 1/20/2020
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CardBackendAccDetailService {

    @Autowired
    private CardBackendAccDetailDao cardBackendAccDetailDao;


    public boolean saveNew(CardBackendAccDetailsEntity accDetailsEntity)
    {
        return cardBackendAccDetailDao.saveNew(accDetailsEntity);
    }

    public CardBackendAccDetailsEntity getByAccNo(String cardNo, Date startDate, Date endDate)
    {
        return cardBackendAccDetailDao.getByAccNo(cardNo,startDate,endDate);
    }

    public boolean updateBack(CardBackendAccDetailsEntity accDetailsEntity)
    {
        return cardBackendAccDetailDao.updateBack(accDetailsEntity);
    }

}
