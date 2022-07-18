package com.csinfotechbd.collection.settings.PARqueueAccRuleLoan;
/*
Created by Monirul Islam on 7/10/2019 
*/

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class PARqueueAccRuleLoanDao {

    @Autowired
    private EntityManager entityManager;

    public List<PARqueueAccRuleLoanEntity> getList()
    {

        List<PARqueueAccRuleLoanEntity> objList=new ArrayList<>();
        try{
            Session session= entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(PARqueueAccRuleLoanEntity.class);
            crt.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            objList=crt.list();
            return  objList;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public boolean saveNew(PARqueueAccRuleLoanEntity obj)
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

    public PARqueueAccRuleLoanEntity getById(Long id)
    {
        PARqueueAccRuleLoanEntity obj=new PARqueueAccRuleLoanEntity();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(PARqueueAccRuleLoanEntity.class);
            crt.add(Restrictions.eq("id",id));
            obj=(PARqueueAccRuleLoanEntity) crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public boolean updateObj(PARqueueAccRuleLoanEntity obj)
    {
        PARqueueAccRuleLoanEntity tempObj=getById(obj.getId());
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

    public List<PARqueueAccRuleLoanEntity> getActiveOnly()
    {
        List<PARqueueAccRuleLoanEntity> objList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(PARqueueAccRuleLoanEntity.class);
            crt.add(Restrictions.eq("enabled",true));
            crt.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            objList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return objList;
    }
}
