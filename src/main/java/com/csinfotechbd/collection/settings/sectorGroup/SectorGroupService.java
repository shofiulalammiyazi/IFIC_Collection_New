package com.csinfotechbd.collection.settings.sectorGroup;
/*
Created by Monirul Islam at 7/2/2019 
*/


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectorGroupService {

    @Autowired
    private SectorGroupDao sectorGroupDao;

    public List<SectorGroupEntity> getAll()
    {
        return sectorGroupDao.getAll();
    }

    public boolean saveNew(SectorGroupEntity sectorGroup)
    {
        return sectorGroupDao.saveSectGrp(sectorGroup);
    }

    public boolean updateSectGrp(SectorGroupEntity sectorGroup)
    {
        return  sectorGroupDao.updateSectGrp(sectorGroup);
    }

    public SectorGroupEntity getById(Long Id)
    {
        return  sectorGroupDao.getById(Id);
    }

    public List<SectorGroupEntity> getActiveList()
    {
        return sectorGroupDao.getActiveList();
    }
}
