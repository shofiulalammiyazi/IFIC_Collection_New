package com.csinfotechbd.collection.settings.esau.card;
/*
  Created by Md. Monirul Islam on 9/11/2019
*/

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ESAUCardDao {

    @Autowired
    private EntityManager entityManager;

    public List<ESAUCardEntity> getList()
    {

        List<ESAUCardEntity> objList=new ArrayList<>();
        try{
            Session session= entityManager.unwrap(Session.class);
            Criteria crt= session.createCriteria(ESAUCardEntity.class);
            crt.add(Restrictions.eq("enabled",true));
            //crt.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            objList=crt.list();

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return  objList;
    }

    public boolean saveNew(ESAUCardEntity obj)
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

    public ESAUCardEntity getById(Long id)
    {
        ESAUCardEntity obj=new ESAUCardEntity();
        try{
            Session session=entityManager.unwrap(Session.class);
            obj=session.get(ESAUCardEntity.class,id);
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public boolean updateObj(ESAUCardEntity obj)
    {
        ESAUCardEntity tempObj=getById(obj.getId());
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

    public List<ESAUCardEntity> getActiveOnly()
    {
        List<ESAUCardEntity> objList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(ESAUCardEntity.class);
            crt.add(Restrictions.eq("enabled",true));
            objList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return objList;
    }

    public ESAUCardEntity getLatest()
    {
        ESAUCardEntity esauCardEntity=null;
        try{
            Session session=entityManager.unwrap(Session.class);

            Criteria crt=session.createCriteria(ESAUCardEntity.class);
            crt.addOrder(Order.desc("createdDate"));
            crt.setMaxResults(1);

            esauCardEntity=(ESAUCardEntity)crt.uniqueResult();

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return esauCardEntity;
    }
}
