package com.csinfotechbd.collection.settings.diaryNoteSubMenu3;
/*
Created by Monirul Islam at 6/30/2019 
*/

import com.csinfotechbd.collection.settings.diaryNoteSubMenu2.DiaryNoteSubMenu2Entity;
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
public class DNSubMenu3Dao {

    @Autowired
    private EntityManager entityManager;

    public List<DiaryNoteSubMenu3Entity> getList()
    {
        List<DiaryNoteSubMenu3Entity> subMenuList=new ArrayList<>();

        try{
            Session session= entityManager.unwrap(Session.class);
            subMenuList=session.createCriteria(DiaryNoteSubMenu3Entity.class).list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return subMenuList;
    }

    public boolean saveDN(DiaryNoteSubMenu3Entity dairyNoteSubMenu)
    {
        boolean save=false;

        try{
            Session session=entityManager.unwrap(Session.class);
            session.save(dairyNoteSubMenu);
            session.flush();
            save=true;
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return save;
    }

    public DiaryNoteSubMenu3Entity getById(Long id)
    {
        DiaryNoteSubMenu3Entity subMenu=new DiaryNoteSubMenu3Entity();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(DiaryNoteSubMenu3Entity.class);
            crt.add(Restrictions.eq("id",id));
            subMenu=(DiaryNoteSubMenu3Entity) crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return subMenu;
    }

    public boolean updateSM(DiaryNoteSubMenu3Entity subMenu)
    {
        DiaryNoteSubMenu3Entity tempSM=getById(subMenu.getId());
        boolean update=false;
        try{
            subMenu.setCreatedBy(tempSM.getCreatedBy());
            subMenu.setCreatedDate(tempSM.getCreatedDate());
            Session session=entityManager.unwrap(Session.class);
            session.merge(subMenu);
            session.flush();
            session.clear();
            update=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return update;
    }

    public List<DiaryNoteSubMenu3Entity> getActiveOnly()
    {
        List<DiaryNoteSubMenu3Entity> subMenuList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(DiaryNoteSubMenu3Entity.class);
            crt.add(Restrictions.eq("enabled",true));
            subMenuList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return subMenuList;
    }


    public List<DiaryNoteSubMenu3Entity> getActivelistbysubmenu3Id( Long id) {
        List<DiaryNoteSubMenu3Entity> subMenu3List=new ArrayList<>();
        DiaryNoteSubMenu2Entity dairynotemenu2 = new DiaryNoteSubMenu2Entity();
        dairynotemenu2.setId(id);
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(DiaryNoteSubMenu3Entity.class);
            crt.add(Restrictions.eq("enabled",true));
            crt.add(Restrictions.eq("diaryNoteSubMenu2",dairynotemenu2));
            subMenu3List=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return subMenu3List;
    }
}
