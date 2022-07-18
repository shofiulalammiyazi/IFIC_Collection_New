package com.csinfotechbd.collection.KPI.Card.TargetWeightByAmount;

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

/*
  Created by Md. Monirul Islam on 9/3/2019
*/
@Repository
@Transactional
public class CardKPITargetWeightByAmountDao {

    @Autowired
    private EntityManager entityManager;

    public CardKPITargetWeightByAmountEntity getData()
    {
        CardKPITargetWeightByAmountEntity cardKPITargetWeightByAmount =null;
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria criteria=session.createCriteria(CardKPITargetWeightByAmountEntity.class);
            criteria.addOrder(Order.desc("id"));
            criteria.setMaxResults(1);
            cardKPITargetWeightByAmount =(CardKPITargetWeightByAmountEntity)criteria.uniqueResult();
            //System.err.println("KPI :: "+cardKPITargetByAccountEntity);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return cardKPITargetWeightByAmount;
    }

    public boolean saveNew(CardKPITargetWeightByAmountEntity cardKPITargetWeightByAmount)
    {
        boolean save=false;
        try{
            Session session=entityManager.unwrap(Session.class);
            session.save(cardKPITargetWeightByAmount);
            session.flush();
            session.clear();
            save=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return save;
    }


    public CardKPITargetWeightByAmountEntity getById(Long id)
    {
        Session session=entityManager.unwrap(Session.class);
        CardKPITargetWeightByAmountEntity exceptionRuleEntity=null;
        try{
            exceptionRuleEntity=session.get(CardKPITargetWeightByAmountEntity.class,id);
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return exceptionRuleEntity;
    }

    public List<CardKPITargetWeightByAmountEntity> getAllData()
    {
        List<CardKPITargetWeightByAmountEntity> kpiTargetByAmountList=new ArrayList<>();
        Session session=entityManager.unwrap(Session.class);

        try{
            Criteria crt=session.createCriteria(CardKPITargetWeightByAmountEntity.class);
            kpiTargetByAmountList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return kpiTargetByAmountList;
    }

    public boolean updateData(CardKPITargetWeightByAmountEntity cardKPITargetByAmountEntity)
    {
        Session session=entityManager.unwrap(Session.class);
        boolean update=false;
        try{
            CardKPITargetWeightByAmountEntity temp=getById(cardKPITargetByAmountEntity.getId());
            cardKPITargetByAmountEntity.setCreatedBy(temp.getCreatedBy());
            cardKPITargetByAmountEntity.setCreatedDate(temp.getCreatedDate());

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

    public CardKPITargetWeightByAmountEntity getByProductAgeCodeAndDealerPin(ProductTypeEntity productTypeEntity, AgeCodeEntity ageCodeEntity,String dealerPin)
    {
        CardKPITargetWeightByAmountEntity cardKPITargetWeightByAmountEntity=new CardKPITargetWeightByAmountEntity();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(CardKPITargetWeightByAmountEntity.class);
            crt.add(Restrictions.eq("enabled",true));

            crt.createAlias("productType","productType");

            if(productTypeEntity != null)
                if(productTypeEntity.getId() != null)
                    crt.add(Restrictions.eq("productType.id",productTypeEntity.getId()));

            crt.createAlias("ageCode","ageCode");
            crt.add(Restrictions.eq("ageCode.id",ageCodeEntity.getId()));
            crt.createAlias("dealerName","dealerName");
            crt.add(Restrictions.eq("dealerName.pin",dealerPin));

            crt.addOrder(Order.desc("id"));
            crt.setMaxResults(1);

            cardKPITargetWeightByAmountEntity=(CardKPITargetWeightByAmountEntity)crt.uniqueResult();

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return cardKPITargetWeightByAmountEntity;
    }
}
