package com.unisoft.retail.card.dataEntry.distribution.accountInfo;
/*
Created by   Islam at 7/21/2019
*/

import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
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
public class CardAccountDao {
    @Autowired
    private EntityManager entityManager;

    public List<CardAccountInfo> getList()
    {

        List<CardAccountInfo> objList=new ArrayList<>();
        try{
            Session session= entityManager.unwrap(Session.class);
            objList=session.createCriteria(CardAccountInfo.class).list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return  objList;
    }

    public boolean saveNew(CardAccountInfo obj)
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

    public CardAccountInfo getById(Long id)
    {
        CardAccountInfo obj=new CardAccountInfo();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(CardAccountInfo.class);
            crt.add(Restrictions.eq("id",id));
            obj=(CardAccountInfo) crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public boolean updateObj(CardAccountInfo obj)
    {
        CardAccountInfo tempObj=getById(obj.getId());
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

    public List<CardAccountInfo> getActiveOnly()
    {
        List<CardAccountInfo> objList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(CardAccountInfo.class);
            crt.add(Restrictions.eq("enabled",true));
            objList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return objList;
    }

    public List<CardAccountInfo> findByCardAccountBasicId(CardAccountBasicInfo cardAccountBasicInfo) {
        List<CardAccountInfo> obj = new ArrayList<>();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(CardAccountInfo.class);
            crt.add(Restrictions.eq("cardAccountBasicInfo",cardAccountBasicInfo));
            obj= crt.list();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return obj;
    }
}
