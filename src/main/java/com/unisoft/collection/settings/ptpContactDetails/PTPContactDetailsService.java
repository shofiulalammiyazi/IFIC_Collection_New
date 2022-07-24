package com.unisoft.collection.settings.ptpContactDetails;
/*
Created by   Islam at 7/2/2019
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PTPContactDetailsService {

    @Autowired
    private PTPContactDetailsDao ptpContactDetailsDao;

    public List<PTPContactDetailsEntity> getAll()
    {
        return ptpContactDetailsDao.getAll();
    }

    public boolean savePtpCD(PTPContactDetailsEntity contactDetails)
    {
        return ptpContactDetailsDao.savePTP(contactDetails);
    }

    public boolean updatePTPCD(PTPContactDetailsEntity contactDetails)
    {
        return ptpContactDetailsDao.updatePtp(contactDetails);
    }

    public PTPContactDetailsEntity getById(Long id)
    {
        return ptpContactDetailsDao.getById(id);
    }

    public List<PTPContactDetailsEntity> getActiveList()
    {
        return ptpContactDetailsDao.getActiveList();
    }
}
