package com.unisoft.collection.settings.officeTimeSetup;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class OfficeTimeSetupDao {

    @Autowired
    private EntityManager entityManager;

    public OfficeTimeSetupInfo getOfficeTimeSetupInfo() {
        OfficeTimeSetupInfo obj = new OfficeTimeSetupInfo();

        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria criteria = session.createCriteria(OfficeTimeSetupInfo.class);
            criteria.setMaxResults(1);
            obj =(OfficeTimeSetupInfo) criteria.uniqueResult();
            session.clear();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public boolean saveOfficeTime(OfficeTimeSetupInfo officeTimeSetupInfo)
    {
        boolean save=false;
        try{
            Session session=entityManager.unwrap(Session.class);
            session.save(officeTimeSetupInfo);
            session.flush();
            session.clear();
            save=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return save;
    }

    public boolean updateOfficeTime(OfficeTimeSetupInfo officeTimeSetupInfo)
    {
        OfficeTimeSetupInfo temp= getOfficeTimeSetupInfo();  /*getById(securedCardCriteriaEntity.getId());*/
        boolean update=false;
        try{
            officeTimeSetupInfo.setCreatedBy(temp.getCreatedBy());
            officeTimeSetupInfo.setCreatedDate(temp.getCreatedDate());
            Session session=entityManager.unwrap(Session.class);
            session.merge(officeTimeSetupInfo);
            session.flush();
            session.clear();
            update=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return update;
    }
}
