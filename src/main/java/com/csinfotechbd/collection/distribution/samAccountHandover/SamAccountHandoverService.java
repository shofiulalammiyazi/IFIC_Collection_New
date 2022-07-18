package com.csinfotechbd.collection.distribution.samAccountHandover;
/*
Created by Monirul Islam at 8/1/2019
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SamAccountHandoverService {

    @Autowired
    private SamAccountHandoverDao samAccountHandoverDao;

    public List<SamAccountHandoverInfo> getAll() {
        return samAccountHandoverDao.getList();
    }

    public boolean saveNew(SamAccountHandoverInfo agency) {
        return samAccountHandoverDao.saveNew(agency);
    }

    public boolean updateAgency(SamAccountHandoverInfo agency) {
        return samAccountHandoverDao.updateObj(agency);
    }

    public SamAccountHandoverInfo getById(Long Id) {
        return samAccountHandoverDao.getById(Id);
    }

    public List<SamAccountHandoverInfo> getActiveList() {
        return samAccountHandoverDao.getActiveOnly();
    }

    public List<SamAccountHandoverInfo> getSamList(boolean b) {
        return samAccountHandoverDao.getSamList(b);
    }
}
