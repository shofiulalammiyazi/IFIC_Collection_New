package com.csinfotechbd.collection.settings.zone;
/*
Created by Monirul Islam at 7/3/2019 
*/

import com.csinfotechbd.collection.settings.location.LocationEntity;
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
public class ZoneDao {

    @Autowired
    private EntityManager entityManager;

    public List<ZoneEntity> getAll()
    {
        List<ZoneEntity> zoneList=new ArrayList<>();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(ZoneEntity.class);
            zoneList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return zoneList;
    }

    public ZoneEntity getById(Long Id)
    {
        ZoneEntity sectGrp=new ZoneEntity();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(ZoneEntity.class);
            crt.add(Restrictions.eq("id",Id));
            sectGrp=(ZoneEntity) crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return sectGrp;
    }

    public boolean saveZone(ZoneEntity zone)
    {
        boolean save=false;
        try{
            Session session=entityManager.unwrap(Session.class);
            session.save(zone);
            session.flush();
            session.clear();
            save=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return save;
    }

    public boolean updateZone(ZoneEntity zone)
    {
        ZoneEntity temp=getById(zone.getId());
        boolean update=false;
        try{
            zone.setCreatedBy(temp.getCreatedBy());
            zone.setCreatedDate(temp.getCreatedDate());
            Session session=entityManager.unwrap(Session.class);
            session.merge(zone);
            session.flush();
            session.clear();
            update=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return update;
    }

    public List<ZoneEntity> getActiveList()
    {
        List<ZoneEntity> zoneList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(ZoneEntity.class);
            crt.add(Restrictions.eq("enabled",true));
            zoneList=crt.list();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return zoneList;
    }

    public List<ZoneEntity> getByLoc(Long locId)
    {
        List<ZoneEntity> zoneList=new ArrayList<>();
        LocationEntity location=new LocationEntity();
        location.setId(locId);
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt= session.createCriteria(ZoneEntity.class);
            crt.add(Restrictions.eq("location",location));
            crt.add(Restrictions.eq("enabled",true));
            zoneList=(List<ZoneEntity>)crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return zoneList;
    }
}
