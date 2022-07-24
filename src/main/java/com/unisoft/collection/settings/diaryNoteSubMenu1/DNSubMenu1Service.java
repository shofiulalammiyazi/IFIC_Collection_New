package com.unisoft.collection.settings.diaryNoteSubMenu1;
/*
Created by   Islam at 6/26/2019
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DNSubMenu1Service {

    @Autowired
    private DNSubMenu1Dao dnSubMenu1Dao;

    public List<DiaryNoteSubMenu1Entity> getAll()
    {
        return  dnSubMenu1Dao.getList();
    }

    public boolean saveSM(DiaryNoteSubMenu1Entity dairyNoteSubMenu1)
    {
        return dnSubMenu1Dao.saveDN(dairyNoteSubMenu1);
    }

    public boolean updateSM(DiaryNoteSubMenu1Entity dairyNoteSubMenu1)
    {
        return dnSubMenu1Dao.updateSM(dairyNoteSubMenu1);
    }

    public DiaryNoteSubMenu1Entity getById(Long Id)
    {
        return dnSubMenu1Dao.getById(Id);
    }

    public List<DiaryNoteSubMenu1Entity> getActiveList()
    {
        return dnSubMenu1Dao.getActiveOnly();
    }
    public List<DiaryNoteSubMenu1Entity> getActiveListbyMenuId( Long id)
    {
        return dnSubMenu1Dao.getActivelistbymenuId(id);
    }
}
