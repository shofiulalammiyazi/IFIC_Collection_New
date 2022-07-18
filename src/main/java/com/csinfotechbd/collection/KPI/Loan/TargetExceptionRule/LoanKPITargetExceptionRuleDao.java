package com.csinfotechbd.collection.KPI.Loan.TargetExceptionRule;
/*
  Created by Md. Monirul Islam on 9/4/2019
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
public class LoanKPITargetExceptionRuleDao {

    @Autowired
    private EntityManager entityManager;

    public LoanKPITargetExceptionRuleEntity getData()
    {
        LoanKPITargetExceptionRuleEntity exceptionRule =null;
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria criteria=session.createCriteria(LoanKPITargetExceptionRuleEntity.class);
            criteria.addOrder(Order.desc("id"));
            criteria.setMaxResults(1);
            exceptionRule =(LoanKPITargetExceptionRuleEntity)criteria.uniqueResult();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return exceptionRule;
    }

    public boolean saveNew(LoanKPITargetExceptionRuleEntity exceptionRule)
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

    public LoanKPITargetExceptionRuleEntity getById(Long id)
    {
        Session session=entityManager.unwrap(Session.class);
        LoanKPITargetExceptionRuleEntity loanKPITargetExceptionRule=null;
        try{
            loanKPITargetExceptionRule=session.get(LoanKPITargetExceptionRuleEntity.class,id);
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return loanKPITargetExceptionRule;
    }

    public List<LoanKPITargetExceptionRuleEntity> getAllData()
    {
        List<LoanKPITargetExceptionRuleEntity> loanKPITargetExceptionRuleList=new ArrayList<>();
        Session session=entityManager.unwrap(Session.class);

        try{
            Criteria crt=session.createCriteria(LoanKPITargetExceptionRuleEntity.class);
            loanKPITargetExceptionRuleList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return loanKPITargetExceptionRuleList;
    }

    public boolean updateData(LoanKPITargetExceptionRuleEntity exceptionRuleEntity)
    {
        Session session=entityManager.unwrap(Session.class);
        boolean update=false;
        try{
            LoanKPITargetExceptionRuleEntity temp=getById(exceptionRuleEntity.getId());
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
