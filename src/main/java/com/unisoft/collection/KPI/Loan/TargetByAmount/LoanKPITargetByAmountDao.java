package com.unisoft.collection.KPI.Loan.TargetByAmount;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
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
public class LoanKPITargetByAmountDao {

    @Autowired
    private EntityManager entityManager;

    public LoanKPITargetByAmountEntity getData()
    {
        LoanKPITargetByAmountEntity loanKPITargetByAmountEntity =null;
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria criteria=session.createCriteria(LoanKPITargetByAmountEntity.class);
            criteria.addOrder(Order.desc("id"));
            criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            criteria.setMaxResults(1);
            loanKPITargetByAmountEntity =(LoanKPITargetByAmountEntity)criteria.uniqueResult();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return loanKPITargetByAmountEntity;
    }

    public boolean saveNew(LoanKPITargetByAmountEntity loanKPITargetByAmountEntity)
    {
        boolean save=false;
        try{
            Session session=entityManager.unwrap(Session.class);
            session.save(loanKPITargetByAmountEntity);
            session.flush();
            session.clear();
            save=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return save;
    }

    public LoanKPITargetByAmountEntity getById(Long id)
    {
        Session session=entityManager.unwrap(Session.class);
        LoanKPITargetByAmountEntity loanKPITargetByAmountEntity=null;
        try{
            loanKPITargetByAmountEntity=session.get(LoanKPITargetByAmountEntity.class,id);
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return loanKPITargetByAmountEntity;
    }

    public List<LoanKPITargetByAmountEntity> getAllDataForDist(Date distDate)
    {
        List<LoanKPITargetByAmountEntity> loanKPITargetByAmountEntity=new ArrayList<>();
        Session session=entityManager.unwrap(Session.class);

        try{
            Criteria crt=session.createCriteria(LoanKPITargetByAmountEntity.class);
            crt.add(Restrictions.eq("enabled",true));
            crt.add(Restrictions.lt("createdDate",distDate));
            crt.addOrder(Order.desc("createdDate"));
            //crt.setMaxResults(1);
            crt.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            loanKPITargetByAmountEntity = crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return loanKPITargetByAmountEntity;
    }

    public List<LoanKPITargetByAmountEntity> getAllData()
    {
        List<LoanKPITargetByAmountEntity> loanKPITargetByAmountEntity=new ArrayList<>();
        Session session=entityManager.unwrap(Session.class);

        try{
            Criteria crt=session.createCriteria(LoanKPITargetByAmountEntity.class);
            crt.add(Restrictions.eq("enabled",true));
            crt.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            loanKPITargetByAmountEntity=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return loanKPITargetByAmountEntity;
    }

    public boolean updateData(LoanKPITargetByAmountEntity loanKPITargetByAmountEntity)
    {
        Session session=entityManager.unwrap(Session.class);
        boolean update=false;
        try{
            LoanKPITargetByAmountEntity temp=getById(loanKPITargetByAmountEntity.getId());
            loanKPITargetByAmountEntity.setCreatedBy(temp.getCreatedBy());
            loanKPITargetByAmountEntity.setCreatedDate(temp.getCreatedDate());

            session.update(loanKPITargetByAmountEntity);
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
