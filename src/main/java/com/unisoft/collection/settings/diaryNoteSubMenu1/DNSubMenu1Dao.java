package com.unisoft.collection.settings.diaryNoteSubMenu1;
/*
Created by   Islam at 6/26/2019
*/

import com.unisoft.collection.settings.diaryNoteMenu.DiaryNoteMenuEntity;
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
public class DNSubMenu1Dao {

    @Autowired
    private EntityManager entityManager;

    public List<DiaryNoteSubMenu1Entity> getList()
    {
        List<DiaryNoteSubMenu1Entity> subMenuList=new ArrayList<>();

        try{
            Session session= entityManager.unwrap(Session.class);
            subMenuList=session.createCriteria(DiaryNoteSubMenu1Entity.class).list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return subMenuList;
    }

    public boolean saveDN(DiaryNoteSubMenu1Entity dairyNoteSubMenu)
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

    public DiaryNoteSubMenu1Entity getById(Long id)
    {
        DiaryNoteSubMenu1Entity subMenu=new DiaryNoteSubMenu1Entity();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(DiaryNoteSubMenu1Entity.class);
            crt.add(Restrictions.eq("id",id));
            subMenu=(DiaryNoteSubMenu1Entity) crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return subMenu;
    }

    public boolean updateSM(DiaryNoteSubMenu1Entity subMenu)
    {
        DiaryNoteSubMenu1Entity tempSM=getById(subMenu.getId());
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

    public List<DiaryNoteSubMenu1Entity> getActiveOnly()
    {
        List<DiaryNoteSubMenu1Entity> subMenuList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(DiaryNoteSubMenu1Entity.class);
            crt.add(Restrictions.eq("enabled",true));
            subMenuList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return subMenuList;
    }

    public List<DiaryNoteSubMenu1Entity> getActivelistbymenuId( Long id) {
        List<DiaryNoteSubMenu1Entity> subMenuList=new ArrayList<>();
        DiaryNoteMenuEntity dairynotemenu = new DiaryNoteMenuEntity ();
        dairynotemenu.setId(id);
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(DiaryNoteSubMenu1Entity.class);
            crt.add(Restrictions.eq("enabled",true));
            crt.add(Restrictions.eq("diaryNoteMenu",dairynotemenu));
            subMenuList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return subMenuList;
    }
}
