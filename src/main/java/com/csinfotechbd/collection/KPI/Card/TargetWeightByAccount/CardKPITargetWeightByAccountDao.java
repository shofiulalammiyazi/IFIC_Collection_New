package com.csinfotechbd.collection.KPI.Card.TargetWeightByAccount;
/*
  Created by Md. Monirul Islam on 9/3/2019
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
public class CardKPITargetWeightByAccountDao {

    @Autowired
    private EntityManager entityManager;

    public CardKPITargetWeightByAccountEntity getData()
    {
        CardKPITargetWeightByAccountEntity cardKPITargetWeightByAccount =null;
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria criteria=session.createCriteria(CardKPITargetWeightByAccountEntity.class);
            criteria.addOrder(Order.desc("id"));
            criteria.setMaxResults(1);
            cardKPITargetWeightByAccount =(CardKPITargetWeightByAccountEntity)criteria.uniqueResult();
            //System.err.println("KPI :: "+cardKPITargetByAccountEntity);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return cardKPITargetWeightByAccount;
    }

    public boolean saveNew(CardKPITargetWeightByAccountEntity cardKPITargetWeightByAccount)
    {
        boolean save=false;
        try{
            Session session=entityManager.unwrap(Session.class);
            session.save(cardKPITargetWeightByAccount);
            session.flush();
            session.clear();
            save=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return save;
    }

    public CardKPITargetWeightByAccountEntity getById(Long id)
    {
        Session session=entityManager.unwrap(Session.class);
        CardKPITargetWeightByAccountEntity kpiTargetByAccountEntity=null;
        try{
            kpiTargetByAccountEntity=session.get(CardKPITargetWeightByAccountEntity.class,id);
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return kpiTargetByAccountEntity;
    }

    public List<CardKPITargetWeightByAccountEntity> getAllData()
    {
        List<CardKPITargetWeightByAccountEntity> kpiTargetByAccountList=new ArrayList<>();
        Session session=entityManager.unwrap(Session.class);

        try{
            Criteria crt=session.createCriteria(CardKPITargetWeightByAccountEntity.class);
            kpiTargetByAccountList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return kpiTargetByAccountList;
    }

    public List<CardKPITargetWeightByAccountEntity> getAllActive()
    {
        List<CardKPITargetWeightByAccountEntity> kpiTargetByAccountList=new ArrayList<>();
        Session session=entityManager.unwrap(Session.class);

        try{
            Criteria crt=session.createCriteria(CardKPITargetWeightByAccountEntity.class);
            crt.add(Restrictions.eq("enabled",true));
            kpiTargetByAccountList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return kpiTargetByAccountList;
    }

    public boolean updateData(CardKPITargetWeightByAccountEntity cardKPITargetByAccountEntity)
    {
        Session session=entityManager.unwrap(Session.class);
        boolean update=false;
        try{
            CardKPITargetWeightByAccountEntity temp=getById(cardKPITargetByAccountEntity.getId());
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

    public CardKPITargetWeightByAccountEntity getByProductAndAgeCode(ProductTypeEntity productTypeEntity, AgeCodeEntity ageCodeEntity,String userPin)
    {
        CardKPITargetWeightByAccountEntity cardKPITargetWeightByAccount=new CardKPITargetWeightByAccountEntity();

        try {
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(CardKPITargetWeightByAccountEntity.class);
            crt.add(Restrictions.eq("enabled",true));

            crt.createAlias("productType","productTypeList");
            crt.add(Restrictions.eq("productTypeList.id",productTypeEntity.getId()));
            crt.createAlias("ageCode","ageCode");
            crt.add(Restrictions.eq("ageCode.id",ageCodeEntity.getId()));
            crt.createAlias("dealerName","dealer");
            crt.add(Restrictions.eq("dealer.pin",userPin));

            crt.addOrder(Order.desc("id"));
            crt.setMaxResults(1);

            cardKPITargetWeightByAccount=(CardKPITargetWeightByAccountEntity)crt.uniqueResult();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return cardKPITargetWeightByAccount;
    }
}
