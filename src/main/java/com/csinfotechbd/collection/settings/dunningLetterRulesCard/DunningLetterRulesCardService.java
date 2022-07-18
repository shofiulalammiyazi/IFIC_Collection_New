package com.csinfotechbd.collection.settings.dunningLetterRulesCard;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DunningLetterRulesCardService {

    @Autowired
    private DunningLetterRulesCardDao dunningLetterRulesCardDao;

    public List<DunningLetterRulesCardInfo> getDunningLetterRulesCardInfoList(){
        return dunningLetterRulesCardDao.getList();
    }

    public boolean saveDunningLetterRulesCard(DunningLetterRulesCardInfo dunningLetterRulesCardInfo){
        return dunningLetterRulesCardDao.saveDunningLetterCardInfo(dunningLetterRulesCardInfo);
    }

    public boolean updateDunningLetterRulesCard(DunningLetterRulesCardInfo dunningLetterRulesCardInfo){
        return dunningLetterRulesCardDao.updateDunningLetterCardInfo(dunningLetterRulesCardInfo);
    }

    public DunningLetterRulesCardInfo getById(Long id)
    {
        return dunningLetterRulesCardDao.getById(id);
    }

    public List<DunningLetterRulesCardInfo> getActiveList(){
        return dunningLetterRulesCardDao.getActiveOnly();
    }
}
