package com.unisoft.retail.card.dataEntry.distribution.agency;
/*
Created by   Islam at 7/22/2019
*/

import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
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
public class CardAgencyDistributionDao {
    @Autowired
    private EntityManager entityManager;

    public List<CardAgencyDistributionInfo> getList()
    {

        List<CardAgencyDistributionInfo> objList=new ArrayList<>();
        try{
            Session session= entityManager.unwrap(Session.class);
            objList=session.createCriteria(CardAgencyDistributionInfo.class).list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return  objList;
    }

    public boolean saveNew(CardAgencyDistributionInfo obj)
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

    public CardAgencyDistributionInfo getById(Long id)
    {
        CardAgencyDistributionInfo obj=new CardAgencyDistributionInfo();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(CardAgencyDistributionInfo.class);
            crt.add(Restrictions.eq("id",id));
            obj=(CardAgencyDistributionInfo) crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public boolean updateObj(CardAgencyDistributionInfo obj)
    {
        CardAgencyDistributionInfo tempObj=getById(obj.getId());
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

    public List<CardAgencyDistributionInfo> getActiveOnly()
    {
        List<CardAgencyDistributionInfo> objList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(CardAgencyDistributionInfo.class);
            crt.add(Restrictions.eq("enabled",true));
            objList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return objList;
    }

    public List<CardAgencyDistributionInfo> findByLoanAccountBasicInfo(CardAccountBasicInfo byAccountNo) {
        List<CardAgencyDistributionInfo> objList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(CardAgencyDistributionInfo.class);
            crt.add(Restrictions.eq("cardAccountBasicInfo",byAccountNo));
            objList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return objList;
    }
}
