package com.csinfotechbd.collection.settings.assetSubClassificationLoan;
/*
Created by Monirul Islam at 7/16/2019 
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
public class LoanSubClassificationDao {
    @Autowired
    private EntityManager entityManager;

    public List<LoanSubClassification> getList()
    {

        List<LoanSubClassification> objList=new ArrayList<>();
        try{
            Session session= entityManager.unwrap(Session.class);
            objList=session.createCriteria(LoanSubClassification.class).list();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return  objList;
    }

    public boolean saveNew(LoanSubClassification obj)
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
            e.printStackTrace();
        }
        return save;
    }

    public LoanSubClassification getById(Long id)
    {
        LoanSubClassification obj=new LoanSubClassification();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(LoanSubClassification.class);
            crt.add(Restrictions.eq("id",id));
            obj=(LoanSubClassification) crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return obj;
    }

    public boolean updateObj(LoanSubClassification obj)
    {
        LoanSubClassification tempObj=getById(obj.getId());
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
            e.printStackTrace();
        }
        return update;
    }

    public List<LoanSubClassification> getActiveOnly()
    {
        List<LoanSubClassification> objList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(LoanSubClassification.class);
            crt.add(Restrictions.eq("enabled",true));
            objList=crt.list();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return objList;
    }
}
