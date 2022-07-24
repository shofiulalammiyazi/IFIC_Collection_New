package com.unisoft.collection.settings.sector;
/*
Created by   Islam at 7/2/2019
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectorService {

    @Autowired
    private SectorDao sectorDao;

    public List<SectorEntity> getAll()
    {
        return sectorDao.getAll();
    }

    public SectorEntity getById(Long Id)
    {
        return  sectorDao.getById(Id);
    }

    public boolean saveNew(SectorEntity sector)
    {
        return sectorDao.saveSect(sector);
    }

    public boolean updateSect(SectorEntity sector)
    {
        return sectorDao.updateSect(sector);
    }

    public List<SectorEntity> getActiveList()
    {
        return sectorDao.getActiveList();
    }
}
