package com.csinfotechbd.collection.settings.diaryNoteMenu;
/*
Created by Monirul Islam at 6/26/2019 
*/


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DnoteMenuService {

    @Autowired
    private DnoteMenuDao dnoteMenuDao;


    public List<DiaryNoteMenuEntity> getAll()
    {
        return  dnoteMenuDao.getList();
    }

    public DiaryNoteMenuEntity getById(Long Id)
    {
        return dnoteMenuDao.getById(Id);
    }

    public boolean saveDNM(DiaryNoteMenuEntity dairyNoteMenu)
    {
        return dnoteMenuDao.saveDNM(dairyNoteMenu);
    }

    public boolean updateDNM(DiaryNoteMenuEntity dairyNoteMenu)
    {
        return dnoteMenuDao.updateDNM(dairyNoteMenu);
    }

    public List<DiaryNoteMenuEntity> getAllActive()
    {
        return dnoteMenuDao.getActiveOnly();
    }

    public DiaryNoteMenuEntity saveNote(DiaryNoteMenuEntity diaryNoteMenuEntity) {
        return dnoteMenuDao.createDiaryNoteMen(diaryNoteMenuEntity);
    }

    public DiaryNoteMenuEntity updateDiaryNoteMenu(DiaryNoteMenuEntity diaryNoteMenuEntity) {
        return dnoteMenuDao.updateDiaryNoteMenu(diaryNoteMenuEntity);
    }
}
