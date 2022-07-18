package com.csinfotechbd.collection.settings.sectorGroup;
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
public class SectorGroupDao {

    @Autowired
    private EntityManager entityManager;

    public List<SectorGroupEntity> getAll()
    {
        List<SectorGroupEntity> sectGrpList=new ArrayList<>();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(SectorGroupEntity.class);
            sectGrpList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return sectGrpList;
    }

    public SectorGroupEntity getById(Long Id)
    {
        SectorGroupEntity sectGrp=new SectorGroupEntity();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(SectorGroupEntity.class);
            crt.add(Restrictions.eq("id",Id));
            sectGrp=(SectorGroupEntity) crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return sectGrp;
    }

    public boolean saveSectGrp(SectorGroupEntity sectorGroup)
    {
        boolean save=false;
        try{
            Session session=entityManager.unwrap(Session.class);
            session.save(sectorGroup);
            session.flush();
            session.clear();
            save=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return save;
    }

    public boolean updateSectGrp(SectorGroupEntity sectorGroup)
    {
        SectorGroupEntity temp=getById(sectorGroup.getId());
        boolean update=false;
        try{
            sectorGroup.setCreatedBy(temp.getCreatedBy());
            sectorGroup.setCreatedDate(temp.getCreatedDate());
            Session session=entityManager.unwrap(Session.class);
            session.merge(sectorGroup);
            session.flush();
            session.clear();
            update=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return update;
    }

    public List<SectorGroupEntity> getActiveList()
    {
        List<SectorGroupEntity> sectGrpList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(SectorGroupEntity.class);
            crt.add(Restrictions.eq("enabled",true));
            sectGrpList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return sectGrpList;
    }
}
