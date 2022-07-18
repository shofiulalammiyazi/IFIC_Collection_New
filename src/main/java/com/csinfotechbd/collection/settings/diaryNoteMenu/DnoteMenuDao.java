package com.csinfotechbd.collection.settings.diaryNoteMenu;
/*
Created by Monirul Islam at 6/26/2019 
*/

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class DnoteMenuDao {

    @Autowired
    private EntityManager entityManager;

    public List<DiaryNoteMenuEntity> getList() {
        List<DiaryNoteMenuEntity> dnMenuList = new ArrayList<>();

        try {
            Session session = entityManager.unwrap(Session.class);
            dnMenuList = session.createCriteria(DiaryNoteMenuEntity.class).list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return dnMenuList;
    }



    public boolean saveDNM(DiaryNoteMenuEntity dairyNoteMenu) {
        boolean save = false;

        try {
            Session session = entityManager.unwrap(Session.class);
            session.save(dairyNoteMenu);
            session.flush();
            save = true;
            session.clear();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return save;
    }


    public DiaryNoteMenuEntity createDiaryNoteMen(DiaryNoteMenuEntity diaryNoteMenuEntity){
        Session session = entityManager.unwrap(Session.class);
        session.save(diaryNoteMenuEntity);
        session.flush();
        session.clear();
        return diaryNoteMenuEntity;
    }

    public DiaryNoteMenuEntity getById(Long id) {
        DiaryNoteMenuEntity dairyNoteMenu = new DiaryNoteMenuEntity();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(DiaryNoteMenuEntity.class);
            crt.add(Restrictions.eq("id", id));
            dairyNoteMenu = (DiaryNoteMenuEntity) crt.uniqueResult();
            session.clear();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return dairyNoteMenu;
    }

    public boolean updateDNM(DiaryNoteMenuEntity dairyNoteMenu) {
        DiaryNoteMenuEntity tempDNM = getById(dairyNoteMenu.getId());
        boolean update = false;
        try {
            dairyNoteMenu.setCreatedBy(tempDNM.getCreatedBy());
            dairyNoteMenu.setCreatedDate(tempDNM.getCreatedDate());
            Session session = entityManager.unwrap(Session.class);
            session.merge(dairyNoteMenu);
            session.flush();
            session.clear();
            update = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return update;
    }
    public DiaryNoteMenuEntity updateDiaryNoteMenu(DiaryNoteMenuEntity dairyNoteMenu) {
        DiaryNoteMenuEntity tempDNM = getById(dairyNoteMenu.getId());
        try {
            dairyNoteMenu.setCreatedBy(tempDNM.getCreatedBy());
            dairyNoteMenu.setCreatedDate(tempDNM.getCreatedDate());
            Session session = entityManager.unwrap(Session.class);
            session.merge(dairyNoteMenu);
            session.flush();
            session.clear();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return dairyNoteMenu;
    }

    public List<DiaryNoteMenuEntity> getActiveOnly() {
        List<DiaryNoteMenuEntity> DNMList = new ArrayList<>();

        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(DiaryNoteMenuEntity.class);
            crt.add(Restrictions.eq("enabled", true));
            DNMList = crt.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return DNMList;
    }
}
