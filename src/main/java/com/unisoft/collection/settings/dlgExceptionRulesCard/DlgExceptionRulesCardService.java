package com.unisoft.collection.settings.dlgExceptionRulesCard;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DlgExceptionRulesCardService {

    @Autowired
    private DlgExceptionRulesCardDao dlgExceptionRulesCardDao;

    public DlgExceptionRulesCardInfo getDlgExceptionRulesCardInfo() {
        return dlgExceptionRulesCardDao.getDlgExceptionRulesCardInfo();
    }

    public boolean saveDlgExceptionRulesCard(DlgExceptionRulesCardInfo dlgExceptionRulesCardInfo)
    {
        return dlgExceptionRulesCardDao.saveDlgExceptionRulesCardInfo(dlgExceptionRulesCardInfo);
    }

    public boolean updateDlgExceptionRulesCard(DlgExceptionRulesCardInfo dlgExceptionRulesCardInfo)
    {
        return dlgExceptionRulesCardDao.updateDlgExceptionRulesCardInfo(dlgExceptionRulesCardInfo);
    }



}
