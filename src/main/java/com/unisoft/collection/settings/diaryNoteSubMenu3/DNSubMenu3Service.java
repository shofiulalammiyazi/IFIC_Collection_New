package com.unisoft.collection.settings.diaryNoteSubMenu3;
/*
Created by   Islam at 6/30/2019
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DNSubMenu3Service {

    @Autowired
    private DNSubMenu3Dao dnSubMenu3Dao;

    public List<DiaryNoteSubMenu3Entity> getAll()
    {
        return dnSubMenu3Dao.getList();
    }

    public DiaryNoteSubMenu3Entity getById(Long Id)
    {
        return dnSubMenu3Dao.getById(Id);
    }

    public boolean saveDNSM(DiaryNoteSubMenu3Entity diaryNoteSubMenu3)
    {
        return dnSubMenu3Dao.saveDN(diaryNoteSubMenu3);
    }

    public boolean updateDNSM(DiaryNoteSubMenu3Entity diaryNoteSubMenu3)
    {
        return dnSubMenu3Dao.updateSM(diaryNoteSubMenu3);
    }

    public List<DiaryNoteSubMenu3Entity> getActiveList()
    {
        return dnSubMenu3Dao.getActiveOnly();
    }


    public List<DiaryNoteSubMenu3Entity> getActiveListbysub2Id( Long id)
    {
        return dnSubMenu3Dao.getActivelistbysubmenu3Id(id);
    }
}
