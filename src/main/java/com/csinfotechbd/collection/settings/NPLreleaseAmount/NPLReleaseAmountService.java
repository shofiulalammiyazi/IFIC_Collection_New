package com.csinfotechbd.collection.settings.NPLreleaseAmount;
/*
Created by Monirul Islam on 7/9/2019
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NPLReleaseAmountService {

    @Autowired
    private NPLReleaseAmountDao npLreleaseAmountDao;

    public boolean saveNPL(NPLReleaseAmountEntity npLreleaseAmount) {
        return npLreleaseAmountDao.saveNew(npLreleaseAmount);
    }

    public boolean updateNPL(NPLReleaseAmountEntity npLreleaseAmount) {
        return npLreleaseAmountDao.updateObj(npLreleaseAmount);
    }

    public NPLReleaseAmountEntity getNPL() {
        return npLreleaseAmountDao.getNPL();
    }
}
