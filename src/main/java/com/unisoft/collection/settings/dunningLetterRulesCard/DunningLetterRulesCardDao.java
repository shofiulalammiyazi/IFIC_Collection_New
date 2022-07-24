package com.unisoft.collection.settings.dunningLetterRulesCard;


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
public class DunningLetterRulesCardDao {

    @Autowired
    private EntityManager entityManager;

    public List<DunningLetterRulesCardInfo> getList()
    {
        List<DunningLetterRulesCardInfo> dunningLetterRulesCardInfoList=new ArrayList<>();

        try{
            Session session= entityManager.unwrap(Session.class);
            dunningLetterRulesCardInfoList=session.createCriteria(DunningLetterRulesCardInfo.class).list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return dunningLetterRulesCardInfoList;
    }

    public boolean saveDunningLetterCardInfo(DunningLetterRulesCardInfo dunningLetterRulesCardInfo)
    {
        boolean save=false;

        try{
            Session session=entityManager.unwrap(Session.class);
            session.save(dunningLetterRulesCardInfo);
            session.flush();
            save=true;
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return save;
    }

    public DunningLetterRulesCardInfo  getById(Long id)
    {
        DunningLetterRulesCardInfo dunningLetterRulesCardInfo=new DunningLetterRulesCardInfo();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria criteria=session.createCriteria(DunningLetterRulesCardInfo.class);
            criteria.add(Restrictions.eq("id",id));
            dunningLetterRulesCardInfo=(DunningLetterRulesCardInfo) criteria.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return dunningLetterRulesCardInfo;
    }

    public boolean updateDunningLetterCardInfo(DunningLetterRulesCardInfo dunningLetterRulesCardInfo)
    {
        DunningLetterRulesCardInfo tempDunningLetterRulesCardInfo=getById(dunningLetterRulesCardInfo.getId());
        boolean update=false;
        try{
            dunningLetterRulesCardInfo.setCreatedBy(tempDunningLetterRulesCardInfo.getCreatedBy());
            dunningLetterRulesCardInfo.setCreatedDate(tempDunningLetterRulesCardInfo.getCreatedDate());
            Session session=entityManager.unwrap(Session.class);
            session.merge(dunningLetterRulesCardInfo);
            session.flush();
            update=true;
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return update;
    }

    public List<DunningLetterRulesCardInfo> getActiveOnly()
    {
        List<DunningLetterRulesCardInfo> tempDunningLetterRulesCardInfoList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(DunningLetterRulesCardInfo.class);
            crt.add(Restrictions.eq("enabled",true));
            tempDunningLetterRulesCardInfoList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return tempDunningLetterRulesCardInfoList;
    }
}
