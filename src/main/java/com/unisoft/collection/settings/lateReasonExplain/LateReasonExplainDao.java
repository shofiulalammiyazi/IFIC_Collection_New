package com.unisoft.collection.settings.lateReasonExplain;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/*
Created by   Islam at 7/22/2019
*/
@Repository
@Transactional
public class LateReasonExplainDao {
    @Autowired
    private EntityManager entityManager;

    public List<LateReasonExplainInfo> getList()
    {

        List<LateReasonExplainInfo> objList=new ArrayList<>();
        try{
            Session session= entityManager.unwrap(Session.class);
            objList=session.createCriteria(LateReasonExplainInfo.class).list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return  objList;
    }

    public boolean saveNew(LateReasonExplainInfo obj)
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

    public LateReasonExplainInfo getById(Long id)
    {
        LateReasonExplainInfo obj=new LateReasonExplainInfo();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(LateReasonExplainInfo.class);
            crt.add(Restrictions.eq("id",id));
            obj=(LateReasonExplainInfo) crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public boolean updateObj(LateReasonExplainInfo obj)
    {
        LateReasonExplainInfo tempObj=getById(obj.getId());
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

    public List<LateReasonExplainInfo> getActiveOnly()
    {
        List<LateReasonExplainInfo> objList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(LateReasonExplainInfo.class);
            crt.add(Restrictions.eq("enabled",true));
            objList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return objList;
    }
}
