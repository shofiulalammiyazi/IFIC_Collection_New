package com.unisoft.retail.card.dataEntry.distribution.agency;
/*
Created by   Islam at 7/22/2019
*/

import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardAgencyDistributionService {
    @Autowired
    private CardAgencyDistributionDao cardAgencyDistributionDao;

    public List<CardAgencyDistributionInfo> getAll()
    {
        return cardAgencyDistributionDao.getList();
    }

    public boolean saveNew(CardAgencyDistributionInfo agency)
    {
        return cardAgencyDistributionDao.saveNew(agency);
    }

    public boolean updateAgency(CardAgencyDistributionInfo agency)
    {
        return cardAgencyDistributionDao.updateObj(agency);
    }

    public CardAgencyDistributionInfo getById(Long Id)
    {
        return cardAgencyDistributionDao.getById(Id);
    }

    public List<CardAgencyDistributionInfo> getActiveList()
    {
        return cardAgencyDistributionDao.getActiveOnly();
    }

    public List<CardAgencyDistributionInfo> findByLoanAccountBasicInfo(CardAccountBasicInfo byAccountNo) {
        return cardAgencyDistributionDao.findByLoanAccountBasicInfo(byAccountNo);
    }
}
