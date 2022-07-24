package com.unisoft.collection.settings.manualLetterSetup;


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
public class ManualLetterSetupDao {

    @Autowired
    private EntityManager entityManager;

    public List<ManualLetterSetupInfo> getList()
    {
        List<ManualLetterSetupInfo> manualLetterSetupInfoList=new ArrayList<>();

        try{
            Session session= entityManager.unwrap(Session.class);
            manualLetterSetupInfoList=session.createCriteria(ManualLetterSetupInfo.class).list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return manualLetterSetupInfoList;
    }

    public boolean save(ManualLetterSetupInfo manualLetterSetupInfo)
    {
        boolean save=false;

        try{
            Session session=entityManager.unwrap(Session.class);
            session.save(manualLetterSetupInfo);
            session.flush();
            save=true;
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return save;
    }

    public ManualLetterSetupInfo  getById(Long id)
    {
        ManualLetterSetupInfo letterSetupInfo=new ManualLetterSetupInfo();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria criteria=session.createCriteria(ManualLetterSetupInfo.class);
            criteria.add(Restrictions.eq("id",id));
            letterSetupInfo=(ManualLetterSetupInfo) criteria.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return letterSetupInfo;
    }

    public boolean update(ManualLetterSetupInfo manualLetterSetupInfo)
    {
        ManualLetterSetupInfo letterSetupInfo=getById(manualLetterSetupInfo.getId());
        boolean update=false;
        try{
            manualLetterSetupInfo.setCreatedBy(letterSetupInfo.getCreatedBy());
            manualLetterSetupInfo.setCreatedDate(letterSetupInfo.getCreatedDate());
            Session session=entityManager.unwrap(Session.class);
            session.merge(manualLetterSetupInfo);
            session.flush();
            update=true;
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return update;
    }

    public List<ManualLetterSetupInfo> getActiveOnly()
    {
        List<ManualLetterSetupInfo> manualLetterSetupInfoList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(ManualLetterSetupInfo.class);
            crt.add(Restrictions.eq("enabled",true));
            manualLetterSetupInfoList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return manualLetterSetupInfoList;
    }
}
