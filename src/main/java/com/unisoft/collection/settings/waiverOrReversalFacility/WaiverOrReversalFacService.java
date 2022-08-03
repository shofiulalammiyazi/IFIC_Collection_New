package com.unisoft.collection.settings.waiverOrReversalFacility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
Created by   Islam on 7/14/2019
*/

@Service
public class WaiverOrReversalFacService {

    @Autowired
    private WaiverOrReversalFacDao waiverOrReversalFacDao;

    public List<WaiverOrReversalFacilityEntity> getAll()
    {
        return waiverOrReversalFacDao.getList();
    }

    public List<WaiverOrReversalFacilityEntity> getActiveList()
    {
        return waiverOrReversalFacDao.getActiveOnly();
    }

    public boolean saveNew(WaiverOrReversalFacilityEntity nplAccountRule)
    {
        return waiverOrReversalFacDao.saveNew(nplAccountRule);
    }

    public boolean updateWaiver(WaiverOrReversalFacilityEntity nplAccountRule)
    {
        return waiverOrReversalFacDao.updateObj(nplAccountRule);
    }

    public WaiverOrReversalFacilityEntity getById(Long Id)
    {
        return waiverOrReversalFacDao.getById(Id);
    }
}
