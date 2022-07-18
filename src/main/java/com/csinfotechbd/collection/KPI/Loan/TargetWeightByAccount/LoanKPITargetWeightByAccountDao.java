package com.csinfotechbd.collection.KPI.Loan.TargetWeightByAccount;
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
public class LoanKPITargetWeightByAccountDao {

    @Autowired
    private EntityManager entityManager;


    public LoanKPITargetWeightByAccountEntity getData()
    {
        LoanKPITargetWeightByAccountEntity loanKPITargetWeightByAccountEntity =null;
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria criteria=session.createCriteria(LoanKPITargetWeightByAccountEntity.class);
            criteria.addOrder(Order.desc("id"));
            criteria.setMaxResults(1);
            loanKPITargetWeightByAccountEntity =(LoanKPITargetWeightByAccountEntity)criteria.uniqueResult();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return loanKPITargetWeightByAccountEntity;
    }

    public boolean saveNew(LoanKPITargetWeightByAccountEntity loanKPITargetWeightByAccountEntity)
    {
        boolean save=false;
        try{
            Session session=entityManager.unwrap(Session.class);
            session.save(loanKPITargetWeightByAccountEntity);
            session.flush();
            session.clear();
            save=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return save;
    }


    public LoanKPITargetWeightByAccountEntity getById(Long id)
    {
        Session session=entityManager.unwrap(Session.class);
        LoanKPITargetWeightByAccountEntity kpiTargetWeightByAccountEntity=null;
        try{
            kpiTargetWeightByAccountEntity=session.get(LoanKPITargetWeightByAccountEntity.class,id);
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return kpiTargetWeightByAccountEntity;
    }

    public List<LoanKPITargetWeightByAccountEntity> getAllData()
    {
        List<LoanKPITargetWeightByAccountEntity> loanKPITargetWeightByAccountList=new ArrayList<>();
        Session session=entityManager.unwrap(Session.class);
        try{
            Criteria crt=session.createCriteria(LoanKPITargetWeightByAccountEntity.class);
            crt.add(Restrictions.eq("enabled",true));
            loanKPITargetWeightByAccountList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return loanKPITargetWeightByAccountList;
    }

    public List<LoanKPITargetWeightByAccountEntity> getAllDataDist(Date distDate)
    {
        List<LoanKPITargetWeightByAccountEntity> loanKPITargetWeightByAccountList=new ArrayList<>();
        Session session=entityManager.unwrap(Session.class);
        try{
            Criteria crt=session.createCriteria(LoanKPITargetWeightByAccountEntity.class);
            crt.add(Restrictions.eq("enabled",true));
            crt.add(Restrictions.lt("createdDate",distDate));
            crt.addOrder(Order.desc("createdDate"));
            //crt.setMaxResults(1);
            loanKPITargetWeightByAccountList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return loanKPITargetWeightByAccountList;
    }

    public boolean updateData(LoanKPITargetWeightByAccountEntity loanKPITargetWeightByAccountEntity)
    {
        Session session=entityManager.unwrap(Session.class);
        boolean update=false;
        try{
            LoanKPITargetWeightByAccountEntity temp=getById(loanKPITargetWeightByAccountEntity.getId());
            loanKPITargetWeightByAccountEntity.setCreatedBy(temp.getCreatedBy());
            loanKPITargetWeightByAccountEntity.setCreatedDate(temp.getCreatedDate());

            session.update(loanKPITargetWeightByAccountEntity);
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
