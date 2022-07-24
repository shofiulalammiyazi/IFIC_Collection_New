package com.unisoft.collection.settings.diaryNoteSubMenu2;
/*
Created by   Islam at 6/27/2019
*/

import com.unisoft.collection.settings.diaryNoteMenu.DiaryNoteMenuEntity;
import com.unisoft.collection.settings.diaryNoteSubMenu1.DiaryNoteSubMenu1Entity;
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
public class DNSubMenu2Dao {

    @Autowired
    private EntityManager entityManager;

    public List<DiaryNoteSubMenu2Entity> getList()
    {
        List<DiaryNoteSubMenu2Entity> subMenuList=new ArrayList<>();

        try{
            Session session= entityManager.unwrap(Session.class);
            subMenuList=session.createCriteria(DiaryNoteSubMenu2Entity.class).list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return subMenuList;
    }

    public boolean saveDN(DiaryNoteSubMenu2Entity dairyNoteSubMenu)
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

    public DiaryNoteSubMenu2Entity getById(Long id)
    {
        DiaryNoteSubMenu2Entity subMenu=new DiaryNoteSubMenu2Entity();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(DiaryNoteSubMenu2Entity.class);
            crt.add(Restrictions.eq("id",id));
            subMenu=(DiaryNoteSubMenu2Entity) crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return subMenu;
    }

    public boolean updateSM(DiaryNoteSubMenu2Entity subMenu)
    {
        DiaryNoteSubMenu2Entity tempSM=getById(subMenu.getId());
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

    public List<DiaryNoteSubMenu2Entity> getActiveOnly()
    {
        List<DiaryNoteSubMenu2Entity> subMenuList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(DiaryNoteSubMenu2Entity.class);
            crt.add(Restrictions.eq("enabled",true));
            subMenuList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return subMenuList;
    }



    public List<DiaryNoteSubMenu2Entity> getActivelistbybmenu1Id( Long id,Long mid) {
        List<DiaryNoteSubMenu2Entity> subMenu2List=new ArrayList<>();
        DiaryNoteSubMenu1Entity dairynotesubmenu1 = new DiaryNoteSubMenu1Entity();
        DiaryNoteMenuEntity menuEntity = new DiaryNoteMenuEntity();
        dairynotesubmenu1.setId(id);
        menuEntity.setId(mid);
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(DiaryNoteSubMenu2Entity.class);
            crt.add(Restrictions.eq("enabled",true));
            crt.add(Restrictions.eq("diaryNoteSubMenu1",dairynotesubmenu1));
            crt.add(Restrictions.eq("diaryNoteMenu",menuEntity));
            subMenu2List=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return subMenu2List;
    }
}
