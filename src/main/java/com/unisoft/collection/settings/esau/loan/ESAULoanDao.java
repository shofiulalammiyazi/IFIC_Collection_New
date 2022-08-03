package com.unisoft.collection.settings.esau.loan;
/*
  Created by Md.   Islam on 9/9/2019
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
public class ESAULoanDao {

    @Autowired
    private EntityManager entityManager;

    public List<ESAULoanEntity> getList()
    {

        List<ESAULoanEntity> objList=new ArrayList<>();
        try{
            Session session= entityManager.unwrap(Session.class);
            Criteria crt= session.createCriteria(ESAULoanEntity.class);
            //crt.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            objList=crt.list();

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return  objList;
    }

    public boolean saveNew(ESAULoanEntity obj)
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

    public ESAULoanEntity getById(Long id)
    {
        ESAULoanEntity obj=new ESAULoanEntity();
        try{
            Session session=entityManager.unwrap(Session.class);
            obj=session.get(ESAULoanEntity.class,id);
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public boolean updateObj(ESAULoanEntity obj)
    {
        ESAULoanEntity tempObj=getById(obj.getId());
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

    public List<ESAULoanEntity> getActiveOnly()
    {
        List<ESAULoanEntity> objList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(ESAULoanEntity.class);
            crt.add(Restrictions.eq("enabled",true));
            objList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return objList;
    }

}
