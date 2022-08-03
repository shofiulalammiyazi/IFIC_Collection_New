package com.unisoft.collection.KPI.Loan.TargetByAccount;
/*
  Created by Md.   Islam on 8/28/2019
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
public class LoanKPITargetByAccountDao {

    @Autowired
    private EntityManager entityManager;

    public LoanKPITargetByAccountEntity getData()
    {
        LoanKPITargetByAccountEntity loanKPITargetByAccountEntity =null;
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria criteria=session.createCriteria(LoanKPITargetByAccountEntity.class);
            criteria.addOrder(Order.desc("id"));
            criteria.setMaxResults(1);
            loanKPITargetByAccountEntity =(LoanKPITargetByAccountEntity)criteria.uniqueResult();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return loanKPITargetByAccountEntity;
    }

    public boolean saveNew(LoanKPITargetByAccountEntity loanKPITargetByAccountEntity)
    {
        boolean save=false;
        try{
            Session session=entityManager.unwrap(Session.class);
            session.save(loanKPITargetByAccountEntity);
            session.flush();
            session.clear();
            save=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return save;
    }

    public LoanKPITargetByAccountEntity getById(Long id)
    {
        Session session=entityManager.unwrap(Session.class);
        LoanKPITargetByAccountEntity loanKPITargetByAccount=null;
        try{
            loanKPITargetByAccount=session.get(LoanKPITargetByAccountEntity.class,id);
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return loanKPITargetByAccount;
    }

    public List<LoanKPITargetByAccountEntity> getAllData()
    {
        List<LoanKPITargetByAccountEntity> loanKPITargetByAccountList=new ArrayList<>();
        Session session=entityManager.unwrap(Session.class);

        try{
            Criteria crt=session.createCriteria(LoanKPITargetByAccountEntity.class);
            crt.add(Restrictions.eq("enabled",true));
            loanKPITargetByAccountList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return loanKPITargetByAccountList;
    }

    public List<LoanKPITargetByAccountEntity> getAllDataForDist(Date distDate)
    {
        List<LoanKPITargetByAccountEntity> loanKPITargetByAccountList=new ArrayList<>();
        Session session=entityManager.unwrap(Session.class);

        try{
            Criteria crt=session.createCriteria(LoanKPITargetByAccountEntity.class);
            crt.add(Restrictions.eq("enabled",true));
            crt.add(Restrictions.lt("createdDate",distDate));
            crt.addOrder(Order.desc("createdDate"));
            //crt.setMaxResults(1);
            loanKPITargetByAccountList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return loanKPITargetByAccountList;
    }

    public boolean updateData(LoanKPITargetByAccountEntity loanKPITargetByAccountEntity)
    {
        Session session=entityManager.unwrap(Session.class);
        boolean update=false;
        try{
            LoanKPITargetByAccountEntity temp=getById(loanKPITargetByAccountEntity.getId());
            loanKPITargetByAccountEntity.setCreatedBy(temp.getCreatedBy());
            loanKPITargetByAccountEntity.setCreatedDate(temp.getCreatedDate());

            session.update(loanKPITargetByAccountEntity);
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
