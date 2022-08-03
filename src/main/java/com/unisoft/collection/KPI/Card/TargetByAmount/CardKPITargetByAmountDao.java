package com.unisoft.collection.KPI.Card.TargetByAmount;
/*
  Created by Md.   Islam on 8/28/2019
*/

import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class CardKPITargetByAmountDao {


    @Autowired
    private EntityManager entityManager;

    public CardKPITargetByAmountEntity getData()
    {
        CardKPITargetByAmountEntity cardKPITargetByAmountEntity =null;
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria criteria=session.createCriteria(CardKPITargetByAmountEntity.class);
            criteria.addOrder(Order.desc("id"));
            criteria.setMaxResults(1);
            cardKPITargetByAmountEntity =(CardKPITargetByAmountEntity)criteria.uniqueResult();
            System.err.println("KPI :: "+cardKPITargetByAmountEntity);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return cardKPITargetByAmountEntity;
    }

    public boolean saveNew(CardKPITargetByAmountEntity cardKPITargetByAmountEntity)
    {
        boolean save=false;
        try{
            Session session=entityManager.unwrap(Session.class);
            session.save(cardKPITargetByAmountEntity);
            session.flush();
            session.clear();
            save=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return save;
    }

    public CardKPITargetByAmountEntity getById(Long id)
    {
        Session session=entityManager.unwrap(Session.class);
        CardKPITargetByAmountEntity exceptionRuleEntity=null;
        try{
            exceptionRuleEntity=session.get(CardKPITargetByAmountEntity.class,id);
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return exceptionRuleEntity;
    }

    public List<CardKPITargetByAmountEntity> getAllData()
    {
        List<CardKPITargetByAmountEntity> kpiTargetByAmountList=new ArrayList<>();
        Session session=entityManager.unwrap(Session.class);

        try{
            Criteria crt=session.createCriteria(CardKPITargetByAmountEntity.class);
            kpiTargetByAmountList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return kpiTargetByAmountList;
    }

    public boolean updateData(CardKPITargetByAmountEntity cardKPITargetByAmountEntity)
    {
        Session session=entityManager.unwrap(Session.class);
        boolean update=false;
        try{
            CardKPITargetByAmountEntity temp=getById(cardKPITargetByAmountEntity.getId());
            cardKPITargetByAmountEntity.setCreatedBy(temp.getCreatedBy());
            cardKPITargetByAmountEntity.setCreatedDate(temp.getCreatedDate());                             //Mijan bro

            session.update(cardKPITargetByAmountEntity);
            session.flush();
            session.clear();
            update=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return update;
    }

    public CardKPITargetByAmountEntity getByProductAgeCodeAndDealerPin(ProductTypeEntity typeEntity, AgeCodeEntity ageCodeEntity, String dealerPin, LocationEntity locationEntity)
    {
        CardKPITargetByAmountEntity cardKPITargetByAmountEntity=null;

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(CardKPITargetByAmountEntity.class);

            crt.add(Restrictions.eq("enabled",true));
            crt.createAlias("location","location");
            crt.add(Restrictions.eq("location.id",locationEntity.getId()));

            crt.createAlias("productType","productType");
            crt.add(Restrictions.eq("productType.id",typeEntity.getId()));

            crt.createAlias("ageCode","ageCode");
            crt.add(Restrictions.eq("ageCode.id",ageCodeEntity.getId()));

            crt.createAlias("dealerName","dealerName");
            crt.add(Restrictions.eq("dealerName.pin",dealerPin));

            crt.addOrder(Order.desc("id"));
            crt.setMaxResults(1);

            cardKPITargetByAmountEntity=(CardKPITargetByAmountEntity)crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
//            System.out.println(e.getMessage());
        }
        return cardKPITargetByAmountEntity;
    }

}
