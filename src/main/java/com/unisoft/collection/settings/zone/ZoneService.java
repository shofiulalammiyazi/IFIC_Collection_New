package com.unisoft.collection.settings.zone;
/*
Created by   Islam at 7/3/2019
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneService {

    @Autowired
    private ZoneDao zoneDao;

    public List<ZoneEntity> getAll()
    {
        return zoneDao.getAll();
    }

    public boolean saveNew(ZoneEntity zone)
    {
        return zoneDao.saveZone(zone);
    }

    public boolean updateZone(ZoneEntity zone)
    {
        return zoneDao.updateZone(zone);
    }

    public ZoneEntity getById(Long Id)
    {
        return zoneDao.getById(Id);
    }

    public List<ZoneEntity> getActiveList()
    {
        return zoneDao.getActiveList();
    }

    public List<ZoneEntity> getZoneByLoc(Long locId)
    {
        return zoneDao.getByLoc(locId);
    }
}
