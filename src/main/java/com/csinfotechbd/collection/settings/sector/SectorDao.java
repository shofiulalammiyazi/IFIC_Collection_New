package com.csinfotechbd.collection.settings.sector;
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
public class SectorDao {

    @Autowired
    private EntityManager entityManager;

    public List<SectorEntity> getAll()
    {
        List<SectorEntity> sectGrpList=new ArrayList<>();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(SectorEntity.class);
            sectGrpList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return sectGrpList;
    }

    public SectorEntity getById(Long Id)
    {
        SectorEntity sectGrp=new SectorEntity();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(SectorEntity.class);
            crt.add(Restrictions.eq("id",Id));
            sectGrp=(SectorEntity) crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return sectGrp;
    }

    public boolean saveSect(SectorEntity sector)
    {
        boolean save=false;
        try{
            Session session=entityManager.unwrap(Session.class);
            session.save(sector);
            session.flush();
            session.clear();
            save=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return save;
    }

    public boolean updateSect(SectorEntity sector)
    {
        SectorEntity temp=getById(sector.getId());
        boolean update=false;
        try{
            sector.setCreatedBy(temp.getCreatedBy());
            sector.setCreatedDate(temp.getCreatedDate());
            Session session=entityManager.unwrap(Session.class);
            session.merge(sector);
            session.flush();
            session.clear();
            update=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return update;
    }

    public List<SectorEntity> getActiveList()
    {
        List<SectorEntity> sectList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(SectorEntity.class);
            crt.add(Restrictions.eq("enabled",true));
            sectList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return sectList;
    }
}
