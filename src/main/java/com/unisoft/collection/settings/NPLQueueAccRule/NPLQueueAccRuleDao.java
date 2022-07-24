package com.unisoft.collection.settings.NPLQueueAccRule;


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
public class NPLQueueAccRuleDao {

    @Autowired
    private EntityManager entityManager;

    public List<NPLQueueAccRuleEntity> getList()
    {

        List<NPLQueueAccRuleEntity> objList=new ArrayList<>();
        try{
            Session session= entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(NPLQueueAccRuleEntity.class);
            objList=crt.list();
            return  objList;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public boolean saveNew(NPLQueueAccRuleEntity obj)
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

    public NPLQueueAccRuleEntity getById(Long id)
    {
        NPLQueueAccRuleEntity obj=new NPLQueueAccRuleEntity();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(NPLQueueAccRuleEntity.class);
            crt.add(Restrictions.eq("id",id));
            obj=(NPLQueueAccRuleEntity) crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public boolean updateObj(NPLQueueAccRuleEntity obj)
    {
        NPLQueueAccRuleEntity tempObj=getById(obj.getId());
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

    public List<NPLQueueAccRuleEntity> getActiveOnly()
    {
        List<NPLQueueAccRuleEntity> objList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(NPLQueueAccRuleEntity.class);
            crt.add(Restrictions.eq("enabled",true));
            objList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return objList;
    }
}
