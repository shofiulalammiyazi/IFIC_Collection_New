package com.csinfotechbd.collection.settings.lateReason;
/*
Created by Monirul Islam on 7/6/2019 
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LateReasonService {

    @Autowired
    private LateReasonDao lateReasonDao;

    public boolean saveLateReas(LateReasonEntity  lateReason)
    {
        return lateReasonDao.saveNew(lateReason);
    }

    public List<LateReasonEntity> getAll()
    {
        return lateReasonDao.getList();
    }

    public List<LateReasonEntity> getActiveList()
    {
        return lateReasonDao.getActiveOnly();
    }

    public boolean updateReas(LateReasonEntity lateReason)
    {
        return lateReasonDao.updateObj(lateReason);
    }

    public LateReasonEntity getById(Long Id)
    {
        return lateReasonDao.getById(Id);
    }


}
