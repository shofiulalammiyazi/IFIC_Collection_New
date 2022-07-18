package com.csinfotechbd.collection.settings.PARreleaseAmount;
/*
Created by Monirul Islam on 7/9/2019 
*/

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


@Repository
@Transactional
public class PARReleaseAmountDao {

    @Autowired
    private EntityManager entityManager;

//    public List<PARReleaseAmountEntity> getList()
//    {
//
//        List<PARReleaseAmountEntity> objList=new ArrayList<>();
//        try{
//            Session session= entityManager.unwrap(Session.class);
//            objList=session.createCriteria(PARReleaseAmountEntity.class).list();
//        }catch (Exception e)
//        {
//            System.out.println(e.getMessage());
//        }
//        return  objList;
//    }

    public boolean saveNew(PARReleaseAmountEntity obj)
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

    public PARReleaseAmountEntity getPAR()
    {
        PARReleaseAmountEntity obj=new PARReleaseAmountEntity();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(PARReleaseAmountEntity.class);
            //crt.setMaxResults(1);
            obj=(PARReleaseAmountEntity) crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public boolean updateObj(PARReleaseAmountEntity obj)
    {
        PARReleaseAmountEntity tempObj=getPAR();
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
}
