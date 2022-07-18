package com.csinfotechbd.collection.KPI.Loan.TargetWeightByAmount;
/*
  Created by Md. Monirul Islam on 9/3/2019
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
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class LoanKPITargetWeightByAmountDao {

    @Autowired
    private EntityManager entityManager;

    public LoanKPITargetWeightByAmountEntity getData()
    {
        LoanKPITargetWeightByAmountEntity loanKPITargetWeightByAmount =null;
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria criteria=session.createCriteria(LoanKPITargetWeightByAmountEntity.class);
            criteria.addOrder(Order.desc("id"));
            criteria.setMaxResults(1);
            loanKPITargetWeightByAmount =(LoanKPITargetWeightByAmountEntity)criteria.uniqueResult();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return loanKPITargetWeightByAmount;
    }

    public boolean saveNew(LoanKPITargetWeightByAmountEntity loanKPITargetWeightByAmount)
    {
        boolean save=false;
        try{
            Session session=entityManager.unwrap(Session.class);
            session.save(loanKPITargetWeightByAmount);
            session.flush();
            session.clear();
            save=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return save;
    }

    public LoanKPITargetWeightByAmountEntity getById(Long id)
    {
        Session session=entityManager.unwrap(Session.class);
        LoanKPITargetWeightByAmountEntity loanKPITargetWeightByAmount=null;
        try{
            loanKPITargetWeightByAmount=session.get(LoanKPITargetWeightByAmountEntity.class,id);
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return loanKPITargetWeightByAmount;
    }

    public List<LoanKPITargetWeightByAmountEntity> getAllData(Date distDate)
    {
        List<LoanKPITargetWeightByAmountEntity> loanKPITargetWeightByAmountList=new ArrayList<>();
        Session session=entityManager.unwrap(Session.class);

        try{
            Criteria crt=session.createCriteria(LoanKPITargetWeightByAmountEntity.class);
            crt.add(Restrictions.eq("enabled",true));
            crt.add(Restrictions.le("createdDate",distDate));
            crt.addOrder(Order.desc("createdDate"));
            //crt.setMaxResults(1);

            loanKPITargetWeightByAmountList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return loanKPITargetWeightByAmountList;
    }

    public List<LoanKPITargetWeightByAmountEntity> getAllActiveData(Date distDate)
    {
        List<LoanKPITargetWeightByAmountEntity> loanKPITargetWeightByAmountList=new ArrayList<>();
        Session session=entityManager.unwrap(Session.class);

        try{
            Criteria crt=session.createCriteria(LoanKPITargetWeightByAmountEntity.class);
            crt.add(Restrictions.eq("enabled",true));
            crt.add(Restrictions.lt("createdDate",distDate));
            crt.addOrder(Order.desc("createdDate"));
            //crt.setMaxResults(1);

            loanKPITargetWeightByAmountList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return loanKPITargetWeightByAmountList;
    }

    public boolean updateData(LoanKPITargetWeightByAmountEntity loanKPITargetWeightByAmount)
    {
        Session session=entityManager.unwrap(Session.class);
        boolean update=false;
        try{
            LoanKPITargetWeightByAmountEntity temp=getById(loanKPITargetWeightByAmount.getId());
            loanKPITargetWeightByAmount.setCreatedBy(temp.getCreatedBy());
            loanKPITargetWeightByAmount.setCreatedDate(temp.getCreatedDate());

            session.update(loanKPITargetWeightByAmount);
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
