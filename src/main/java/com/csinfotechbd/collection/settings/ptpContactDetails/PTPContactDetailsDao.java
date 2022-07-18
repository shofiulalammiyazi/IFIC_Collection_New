package com.csinfotechbd.collection.settings.ptpContactDetails;
/*
Created by Monirul Islam at 7/2/2019 
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
public class PTPContactDetailsDao {

    @Autowired
    private EntityManager entityManager;

    public List<PTPContactDetailsEntity> getAll()
    {
        List<PTPContactDetailsEntity> ptpProList=new ArrayList<>();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(PTPContactDetailsEntity.class);
            ptpProList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return ptpProList;
    }


    public PTPContactDetailsEntity getById(Long Id)
    {
        PTPContactDetailsEntity ptpPro=new PTPContactDetailsEntity();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(PTPContactDetailsEntity.class);
            crt.add(Restrictions.eq("id",Id));
            ptpPro=(PTPContactDetailsEntity) crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return ptpPro;
    }

    public boolean savePTP(PTPContactDetailsEntity contactDetails)
    {
        boolean save=false;
        try{
            Session session=entityManager.unwrap(Session.class);
            session.save(contactDetails);
            session.flush();
            session.clear();
            save=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return save;
    }

    public boolean updatePtp(PTPContactDetailsEntity contactDetails)
    {
        PTPContactDetailsEntity temp=getById(contactDetails.getId());
        boolean update=false;
        try{
            contactDetails.setCreatedBy(temp.getCreatedBy());
            contactDetails.setCreatedDate(temp.getCreatedDate());
            Session session=entityManager.unwrap(Session.class);
            session.merge(contactDetails);
            session.flush();
            session.clear();
            update=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return update;
    }

    public List<PTPContactDetailsEntity> getActiveList()
    {
        List<PTPContactDetailsEntity> ptpProList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(PTPContactDetailsEntity.class);
            crt.add(Restrictions.eq("enabled",true));
            ptpProList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return ptpProList;
    }
}
