package com.csinfotechbd.collection.KPI.Card.TargetByAccount;
/*
  Created by Md. Monirul Islam on 9/2/2019
*/

import com.csinfotechbd.collection.settings.ageCode.AgeCodeEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
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
public class CardKPITargetByAccountDao {

    @Autowired
    private EntityManager entityManager;

    public CardKPITargetByAccountEntity getData()
    {
        CardKPITargetByAccountEntity cardKPITargetByAccountEntity =null;
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria criteria=session.createCriteria(CardKPITargetByAccountEntity.class);
            criteria.addOrder(Order.desc("id"));
            criteria.setMaxResults(1);
            cardKPITargetByAccountEntity =(CardKPITargetByAccountEntity)criteria.uniqueResult();
            //System.err.println("KPI :: "+cardKPITargetByAccountEntity);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return cardKPITargetByAccountEntity;
    }

    public boolean saveNew(CardKPITargetByAccountEntity cardKPITargetByAccountEntity)
    {
        boolean save=false;
        try{
            Session session=entityManager.unwrap(Session.class);
            session.save(cardKPITargetByAccountEntity);
            session.flush();
            session.clear();
            save=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return save;
    }


    public CardKPITargetByAccountEntity getById(Long id)
    {
        Session session=entityManager.unwrap(Session.class);
        CardKPITargetByAccountEntity kpiTargetByAccountEntity=null;
        try{
            kpiTargetByAccountEntity=session.get(CardKPITargetByAccountEntity.class,id);
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return kpiTargetByAccountEntity;
    }

    public List<CardKPITargetByAccountEntity> getAllData()
    {
        List<CardKPITargetByAccountEntity> kpiTargetByAccountList=new ArrayList<>();
        Session session=entityManager.unwrap(Session.class);

        try{
            Criteria crt=session.createCriteria(CardKPITargetByAccountEntity.class);
            kpiTargetByAccountList=crt.list();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return kpiTargetByAccountList;
    }

    public List<CardKPITargetByAccountEntity> getAllActive()
    {
        List<CardKPITargetByAccountEntity> kpiTargetByAccountList=new ArrayList<>();
        Session session=entityManager.unwrap(Session.class);

        try{
            Criteria crt=session.createCriteria(CardKPITargetByAccountEntity.class);
            crt.add(Restrictions.eq("enabled",true));
            kpiTargetByAccountList=crt.list();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return kpiTargetByAccountList;
    }

//    public List<CardKPITargetByAccountEntity> getTargetByUser

    public boolean updateData(CardKPITargetByAccountEntity cardKPITargetByAccountEntity)
    {
        Session session=entityManager.unwrap(Session.class);
        boolean update=false;
        try{
            CardKPITargetByAccountEntity temp=getById(cardKPITargetByAccountEntity.getId());
            cardKPITargetByAccountEntity.setCreatedBy(temp.getCreatedBy());
            cardKPITargetByAccountEntity.setCreatedDate(temp.getCreatedDate());

            session.update(cardKPITargetByAccountEntity);
            session.flush();
            session.clear();
            update=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return update;
    }

    public CardKPITargetByAccountEntity getByProductAndAgeCodeByDealerPin(ProductTypeEntity productTypeEntity, AgeCodeEntity ageCodeEntity,String dealerPin,String location)
    {
        CardKPITargetByAccountEntity kpiTargetByAccountEntity=new CardKPITargetByAccountEntity();

        try{
            Session session=entityManager.unwrap(Session.class);

            Criteria crt=session.createCriteria(CardKPITargetByAccountEntity.class);
            crt.add(Restrictions.eq("enabled",true));

            crt.createAlias("location","location");
            crt.add(Restrictions.eq("location.city",location));

            crt.createAlias("productType","productTypeList");
            crt.add(Restrictions.eq("productTypeList.id",productTypeEntity.getId()));

            crt.createAlias("ageCode","ageCodeList");
            crt.add(Restrictions.eq("ageCodeList.id",ageCodeEntity.getId()));

            crt.createAlias("dealerName","dealerList");
            crt.add(Restrictions.eq("dealerList.pin",dealerPin));

            crt.addOrder(Order.desc("id"));
            crt.setMaxResults(1);

            kpiTargetByAccountEntity=(CardKPITargetByAccountEntity)crt.uniqueResult();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return kpiTargetByAccountEntity;
    }
}
