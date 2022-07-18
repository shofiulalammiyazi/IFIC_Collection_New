package com.csinfotechbd.collection.settings.manualLetterSetup;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManualLetterSetupService {

    @Autowired
    private ManualLetterSetupDao manualLetterSetupDao;

    public List<ManualLetterSetupInfo> getManualLetterSetupList()
    {
        return manualLetterSetupDao.getList();
    }

    public  boolean saveManualLetterSetup(ManualLetterSetupInfo manualLetterSetupInfo)
    {
        return  manualLetterSetupDao.save(manualLetterSetupInfo);
    }

    public ManualLetterSetupInfo getById(Long id)
    {
        return manualLetterSetupDao.getById(id);
    }

    public boolean updateManualLetterSetup(ManualLetterSetupInfo manualLetterSetupInfo)
    {
        return manualLetterSetupDao.update(manualLetterSetupInfo);
    }

    public List<ManualLetterSetupInfo> getActiveList()
    {
        return  manualLetterSetupDao.getActiveOnly();
    }
}
