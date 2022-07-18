package com.csinfotechbd.collection.settings.PARreleaseAmount;
/*
Created by Monirul Islam on 7/9/2019 
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PARReleaseAmountService {

    @Autowired
    private PARReleaseAmountDao paRreleaseAmountDao;

    public boolean savePar(PARReleaseAmountEntity paRreleaseAmount)
    {
        return paRreleaseAmountDao.saveNew(paRreleaseAmount);
    }

    public boolean updatePAR(PARReleaseAmountEntity paRreleaseAmount)
    {
        return paRreleaseAmountDao.updateObj(paRreleaseAmount);
    }

    public PARReleaseAmountEntity getPAR()
    {
        return paRreleaseAmountDao.getPAR();
    }
}
