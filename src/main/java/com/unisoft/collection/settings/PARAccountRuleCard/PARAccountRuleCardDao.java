package com.unisoft.collection.settings.PARAccountRuleCard;
/*
Created by   Islam on 7/14/2019
*/

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public class PARAccountRuleCardDao {

    @Autowired
    private EntityManager entityManager;

    public boolean saveNew(PARAccountRuleCardEntity obj)
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

    public PARAccountRuleCardEntity getPAR()
    {
        PARAccountRuleCardEntity obj=new PARAccountRuleCardEntity();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(PARAccountRuleCardEntity.class);
            //crt.setMaxResults(1);
            obj=(PARAccountRuleCardEntity) crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public boolean updateObj(PARAccountRuleCardEntity obj)
    {
        PARAccountRuleCardEntity tempObj=getPAR();
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

}
