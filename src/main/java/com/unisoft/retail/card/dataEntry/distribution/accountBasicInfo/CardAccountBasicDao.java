package com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo;
/*
Created by   Islam at 7/21/2019
*/

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class CardAccountBasicDao {
    @Autowired
    private EntityManager entityManager;

    public List<CardAccountBasicInfo> getList()
    {

        List<CardAccountBasicInfo> objList=new ArrayList<>();
        try{
            Session session= entityManager.unwrap(Session.class);
            objList=session.createCriteria(CardAccountBasicInfo.class).list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return  objList;
    }

    public boolean saveNew(CardAccountBasicInfo obj)
    {
        boolean save=false;

        try{
            Session session= entityManager.unwrap(Session.class);
            session.save(obj);
            session.flush();
            save=true;
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return save;
    }

    public CardAccountBasicInfo getById(Long id)
    {
        CardAccountBasicInfo obj=new CardAccountBasicInfo();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(CardAccountBasicInfo.class);
            crt.add(Restrictions.eq("id",id));
            obj=(CardAccountBasicInfo) crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public CardAccountBasicInfo getByContractId(String id){
        CardAccountBasicInfo obj = new CardAccountBasicInfo();
        try{
            Session session = entityManager.unwrap(Session.class);
            Criteria criteria = session.createCriteria(CardAccountBasicInfo.class);
            criteria.add(Restrictions.eq("contractId",id));
            criteria.add(Restrictions.eq("cardType","BASIC"));
            obj = (CardAccountBasicInfo) criteria.uniqueResult();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return obj;
    }

    public CardAccountBasicInfo getByClientId(String id){
        CardAccountBasicInfo obj = new CardAccountBasicInfo();
        try{
            Session session = entityManager.unwrap(Session.class);
            Criteria criteria = session.createCriteria(CardAccountBasicInfo.class);
            criteria.add(Restrictions.eq("clientId",id));
            obj = (CardAccountBasicInfo) criteria.uniqueResult();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return obj;
    }

    public boolean updateObj(CardAccountBasicInfo obj)
    {
        CardAccountBasicInfo tempObj=getById(obj.getId());
        boolean update=false;
        try{
            obj.setCreatedDate(tempObj.getCreatedDate());
            obj.setCreatedBy(tempObj.getCreatedBy());
            Session session=entityManager.unwrap(Session.class);
            session.merge(obj);
            session.flush();
            session.clear();
            update=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return update;
    }

    public List<CardAccountBasicInfo> getActiveOnly()
    {
        List<CardAccountBasicInfo> objList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(CardAccountBasicInfo.class);
            crt.add(Restrictions.eq("enabled",true));
            objList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return objList;
    }

    public CardAccountBasicInfo findByAccountNo(String number){
        CardAccountBasicInfo loanAccountBasicInfo = new CardAccountBasicInfo();
        try {
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(CardAccountBasicInfo.class);
            crt.add(Restrictions.eq("contractId", number));
            loanAccountBasicInfo =(CardAccountBasicInfo) crt.uniqueResult();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return loanAccountBasicInfo;
    }
}
