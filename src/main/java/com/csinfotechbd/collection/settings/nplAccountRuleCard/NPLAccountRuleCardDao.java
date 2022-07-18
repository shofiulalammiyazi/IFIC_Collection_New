package com.csinfotechbd.collection.settings.nplAccountRuleCard;
/*
Created by Monirul Islam on 7/14/2019 
*/

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;

@Repository
@Transactional
public class NPLAccountRuleCardDao {

    @Autowired
    private EntityManager entityManager;

    public boolean saveNew(NPLAccountRuleCardEntity obj)
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

    public NPLAccountRuleCardEntity getNPL()
    {
        NPLAccountRuleCardEntity obj=new NPLAccountRuleCardEntity();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(NPLAccountRuleCardEntity.class);
            //crt.setMaxResults(1);
            obj=(NPLAccountRuleCardEntity) crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public boolean updateObj(NPLAccountRuleCardEntity obj)
    {
        NPLAccountRuleCardEntity tempObj=getNPL();
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
