package com.unisoft.collection.dashboard;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Calendar;
import java.util.Date;

@Repository
@Transactional
public class CardKpiAchDao {

    @Autowired
    private EntityManager entityManager;


    Date getStartDate()
    {
        Calendar c1 = Calendar.getInstance();

        c1.set(Calendar.DAY_OF_MONTH, 1);
        c1.set(Calendar.HOUR_OF_DAY,0);
        c1.set(Calendar.MINUTE,0);
        c1.set(Calendar.SECOND,0);
        c1.set(Calendar.MILLISECOND,0);
        Date startDate=c1.getTime();

        return startDate;
    }

    Date getEndDate()
    {
        Calendar cal = Calendar.getInstance();
        int totalDays = cal.getActualMaximum(Calendar.DATE);

        Calendar c2=Calendar.getInstance();
        c2.set(Calendar.DAY_OF_MONTH,totalDays);
        c2.set(Calendar.HOUR_OF_DAY,23);
        c2.set(Calendar.MINUTE,59);
        c2.set(Calendar.SECOND,59);
        c2.set(Calendar.MILLISECOND,0);
        Date endDate=c2.getTime();

        return endDate;
    }

    public CardKpiAchEntity getByAccNo(String cardAccNo)
    {
        CardKpiAchEntity kpiAchEntity=null;
        try{
            Session  session=entityManager.unwrap(Session.class);

            Criteria crt=session.createCriteria(CardKpiAchEntity.class);
            crt.add(Restrictions.eq("cardAccNo",cardAccNo));
            crt.add(Restrictions.between("updateDate",getStartDate(),getEndDate()));
            crt.addOrder(Order.desc("updateDate"));
            crt.setMaxResults(1);

            kpiAchEntity=(CardKpiAchEntity)crt.uniqueResult();
        }catch (Exception  e)
        {
            System.out.println(e.getMessage());
            kpiAchEntity=null;
        }
        return kpiAchEntity;
    }

    public CardKpiAchManagerEntity getByAccNoMan(String cardAccNo)
    {
        CardKpiAchManagerEntity kpiAchEntity=null;
        try{
            Session  session=entityManager.unwrap(Session.class);

            Criteria crt=session.createCriteria(CardKpiAchManagerEntity.class);
            crt.add(Restrictions.eq("cardAccNo",cardAccNo));
            crt.add(Restrictions.between("updateDate",getStartDate(),getEndDate()));
            crt.addOrder(Order.desc("updateDate"));
            crt.setMaxResults(1);

            kpiAchEntity=(CardKpiAchManagerEntity)crt.uniqueResult();
        }catch (Exception  e)
        {
            System.out.println(e.getMessage());
            kpiAchEntity=null;
        }
        return kpiAchEntity;
    }

    public boolean saveNew(CardKpiAchEntity cardKpiAchEntity)
    {
        boolean save=false;
        try {
//            Session session=entityManager.unwrap(Session.class);
//            session.save(cardKpiAchEntity);
//            session.flush();
//            session.clear();
            entityManager.persist(cardKpiAchEntity);
            entityManager.flush();
//            entityManager.persist(cardKpiAchEntity);
//            entityManager.flush();

            save=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return save;
    }

    public boolean updateKpiAch(CardKpiAchEntity cardKpiAchEntity)
    {
        boolean save=false;
        try {
//            Session session=entityManager.unwrap(Session.class);
//            session.update(cardKpiAchEntity);
//            session.flush();
//            session.clear();
            entityManager.merge(cardKpiAchEntity);
            entityManager.flush();
//            entityManager.merge(cardKpiAchEntity);
//            entityManager.flush();
            save=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return save;
    }

    public boolean saveNewMan(CardKpiAchManagerEntity cardKpiAchManager)
    {
        boolean save=false;
        try{
//            Session session=entityManager.unwrap(Session.class);
//            session.save(cardKpiAchManager);
//            session.flush();
            entityManager.persist(cardKpiAchManager);
            entityManager.flush();
            save=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return save;
    }

    public boolean updateKpiAchMan(CardKpiAchManagerEntity cardKpiAchManager)
    {
        boolean save=false;
        try{
//            Session session=entityManager.unwrap(Session.class);
//            session.update(cardKpiAchManager);
//            session.flush();
            entityManager.merge(cardKpiAchManager);
            entityManager.flush();
            save=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return save;
    }

//    public CardKpiAchEntity getByCardNo(String cardNo)
//    {
//        CardKpiAchEntity kpiAchEntity=null;
//        try{
//            Session session=entityManager.unwrap(Session.class);
//            Criteria crt=session.createCriteria(CardKpiAchEntity.class);
//
//            crt.add(Restrictions.eq("cardAccNo",cardNo));
//            crt.add(Restrictions.between("updateDate",getStartDate(),getEndDate()));
//
//            kpiAchEntity =(CardKpiAchEntity) crt.uniqueResult();
//        }catch (Exception e)
//        {
//            System.out.println(e.getMessage());
//        }
//        return kpiAchEntity;
//    }
}
