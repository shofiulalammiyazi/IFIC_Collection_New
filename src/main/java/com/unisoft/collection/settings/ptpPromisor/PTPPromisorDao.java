package com.unisoft.collection.settings.ptpPromisor;


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
public class PTPPromisorDao {

    @Autowired
    private EntityManager entityManager;

    public List<PTPPromisorEntity> getAll()
    {
        List<PTPPromisorEntity> ptpProList=new ArrayList<>();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(PTPPromisorEntity.class);
            ptpProList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return ptpProList;
    }

    public PTPPromisorEntity getById(Long Id)
    {
        PTPPromisorEntity ptpPro=new PTPPromisorEntity();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(PTPPromisorEntity.class);
            crt.add(Restrictions.eq("id",Id));
            ptpPro=(PTPPromisorEntity) crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return ptpPro;
    }

    public boolean savePTPPro(PTPPromisorEntity ptpPro)
    {
        boolean save=false;
        try{
            Session session=entityManager.unwrap(Session.class);
            session.save(ptpPro);
            session.flush();
            session.clear();
            save=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return save;
    }

    public boolean updatePtpPro(PTPPromisorEntity ptpPro)
    {
        PTPPromisorEntity temp=getById(ptpPro.getId());
        boolean update=false;
        try{
            ptpPro.setCreatedBy(temp.getCreatedBy());
            ptpPro.setCreatedDate(temp.getCreatedDate());
            Session session=entityManager.unwrap(Session.class);
            session.merge(ptpPro);
            session.flush();
            session.clear();
            update=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return update;
    }

    public List<PTPPromisorEntity> getActiveList()
    {
        List<PTPPromisorEntity> ptpProList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(PTPPromisorEntity.class);
            crt.add(Restrictions.eq("enabled",true));
            ptpProList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return ptpProList;
    }
}
