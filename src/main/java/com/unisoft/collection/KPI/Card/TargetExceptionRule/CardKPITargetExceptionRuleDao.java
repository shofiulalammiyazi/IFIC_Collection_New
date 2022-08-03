package com.unisoft.collection.KPI.Card.TargetExceptionRule;
/*
  Created by Md.   Islam on 9/4/2019
*/

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class CardKPITargetExceptionRuleDao {

    @Autowired
    private EntityManager entityManager;

    public CardKPITargetExceptionRuleEntity getData()
    {
        CardKPITargetExceptionRuleEntity exceptionRule =null;
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria criteria=session.createCriteria(CardKPITargetExceptionRuleEntity.class);
            criteria.addOrder(Order.desc("id"));
            criteria.setMaxResults(1);
            exceptionRule =(CardKPITargetExceptionRuleEntity)criteria.uniqueResult();
            //System.err.println("KPI :: "+exceptionRule);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return exceptionRule;
    }


    public boolean saveNew(CardKPITargetExceptionRuleEntity exceptionRule)
    {
        boolean save=false;
        try{
            Session session=entityManager.unwrap(Session.class);
            session.save(exceptionRule);
            session.flush();
            session.clear();
            save=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return save;
    }


    public CardKPITargetExceptionRuleEntity getById(Long id)
    {
        Session session=entityManager.unwrap(Session.class);
        CardKPITargetExceptionRuleEntity exceptionRuleEntity=null;
        try{
            exceptionRuleEntity=session.get(CardKPITargetExceptionRuleEntity.class,id);
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return exceptionRuleEntity;
    }

    public List<CardKPITargetExceptionRuleEntity> getAllData()
    {
        List<CardKPITargetExceptionRuleEntity> exceptionRuleList=new ArrayList<>();
        Session session=entityManager.unwrap(Session.class);

        try{
            Criteria crt=session.createCriteria(CardKPITargetExceptionRuleEntity.class);
            exceptionRuleList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return exceptionRuleList;
    }

    public boolean updateData(CardKPITargetExceptionRuleEntity exceptionRuleEntity)
    {
        Session session=entityManager.unwrap(Session.class);
        boolean update=false;
        try{
            CardKPITargetExceptionRuleEntity temp=getById(exceptionRuleEntity.getId());
            exceptionRuleEntity.setCreatedBy(temp.getCreatedBy());
            exceptionRuleEntity.setCreatedDate(temp.getCreatedDate());

            session.update(exceptionRuleEntity);
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
