package com.csinfotechbd.collection.settings.diaryNoteSubMenu2;
/*
Created by Monirul Islam at 6/27/2019 
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DNSubMenu2Service {

    @Autowired
    private DNSubMenu2Dao dnSubMenu1Dao;

    public List<DiaryNoteSubMenu2Entity> getAll()
    {
        return  dnSubMenu1Dao.getList();
    }

    public boolean saveSM(DiaryNoteSubMenu2Entity dairyNoteSubMenu1)
    {
        return dnSubMenu1Dao.saveDN(dairyNoteSubMenu1);
    }

    public boolean updateSM(DiaryNoteSubMenu2Entity dairyNoteSubMenu1)
    {
        return dnSubMenu1Dao.updateSM(dairyNoteSubMenu1);
    }

    public DiaryNoteSubMenu2Entity getById(Long Id)
    {
        return dnSubMenu1Dao.getById(Id);
    }

    public List<DiaryNoteSubMenu2Entity> getActiveList()
    {
        return dnSubMenu1Dao.getActiveOnly();
    }


    public List<DiaryNoteSubMenu2Entity> getActiveListbysubMenu1Id( Long id,Long mid)
    {
        return dnSubMenu1Dao.getActivelistbybmenu1Id(id,mid);
    }
}
