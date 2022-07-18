package com.csinfotechbd.collection.settings.designation;
/*
Created by Monirul Islam at 6/24/2019 
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
public class DesignationDao {

    @Autowired
    private EntityManager entityManager;

    public List<DesignationEntity> getList()
    {
        List<DesignationEntity> desList=new ArrayList<>();

        try{
            Session session= entityManager.unwrap(Session.class);
            desList=session.createCriteria(DesignationEntity.class).list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return desList;
    }

    public boolean save(DesignationEntity designation)
    {
        boolean save=false;

        try{
            Session session=entityManager.unwrap(Session.class);
            session.save(designation);
            session.flush();
            save=true;
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return save;
    }

    public DesignationEntity getById(Long id)
    {
        DesignationEntity designation=new DesignationEntity();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(DesignationEntity.class);
            crt.add(Restrictions.eq("id",id));
            designation=(DesignationEntity) crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return designation;
    }

    public DesignationEntity findByName(String name) {
        DesignationEntity designation=new DesignationEntity();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(DesignationEntity.class);
            crt.add(Restrictions.eq("name",name));
            designation=(DesignationEntity) crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return designation;
    }

    public boolean updateDes(DesignationEntity designation)
    {
        DesignationEntity tempDes=getById(designation.getId());
        boolean update=false;
        try{
            designation.setCreatedBy(tempDes.getCreatedBy());
            designation.setCreatedDate(tempDes.getCreatedDate());
            Session session=entityManager.unwrap(Session.class);
            session.merge(designation);
            session.flush();
            session.clear();
            update=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return update;
    }

    public List<DesignationEntity> getActiveOnly()
    {
        List<DesignationEntity> statList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(DesignationEntity.class);
            crt.add(Restrictions.eq("enabled",true));
            statList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return statList;
    }
}
