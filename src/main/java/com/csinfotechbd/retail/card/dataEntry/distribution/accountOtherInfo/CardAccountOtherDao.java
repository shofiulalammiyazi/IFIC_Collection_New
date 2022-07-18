package com.csinfotechbd.retail.card.dataEntry.distribution.accountOtherInfo;
/*
Created by Monirul Islam at 7/21/2019 
*/

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class CardAccountOtherDao {
    @Autowired
    private EntityManager entityManager;

    public List<CardAccountOtherInfo> getList()
    {

        List<CardAccountOtherInfo> objList=new ArrayList<>();
        try{
            Session session= entityManager.unwrap(Session.class);
            objList=session.createCriteria(CardAccountOtherInfo.class).list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return  objList;
    }

    public boolean saveNew(CardAccountOtherInfo obj)
    {
        boolean save=false;

        try{
            Session session= entityManager.unwrap(Session.class);
            session.save(obj);
            session.flush();
            save=true;
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return save;
    }

    public CardAccountOtherInfo getById(Long id)
    {
        CardAccountOtherInfo obj=new CardAccountOtherInfo();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(CardAccountOtherInfo.class);
            crt.add(Restrictions.eq("id",id));
            obj=(CardAccountOtherInfo) crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public boolean updateObj(CardAccountOtherInfo obj)
    {
        CardAccountOtherInfo tempObj=getById(obj.getId());
        boolean update=false;
        try{
            obj.setCreatedDate(tempObj.getCreatedDate());
            obj.setCreatedBy(tempObj.getCreatedBy());
            Session session=entityManager.unwrap(Session.class);
            session.merge(obj);
            session.flush();
            session.clear();
            update=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return update;
    }

    public List<CardAccountOtherInfo> getActiveOnly()
    {
        List<CardAccountOtherInfo> objList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(CardAccountOtherInfo.class);
            crt.add(Restrictions.eq("enabled",true));
            objList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return objList;
    }
}
