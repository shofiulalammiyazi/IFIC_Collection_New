package com.unisoft.collection.settings.visitLedger;


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
public class VisitLedgerDao {
    @Autowired
    private EntityManager entityManager;

    public List<VisitLedgerEntity> getList() {
        List<VisitLedgerEntity> visitLedgerEntityList = new ArrayList<>();

        try {
            Session session = entityManager.unwrap(Session.class);
            visitLedgerEntityList = session.createCriteria(VisitLedgerEntity.class).list();

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return visitLedgerEntityList;
    }

    public boolean save (VisitLedgerEntity visitLedgerEntity) {
        boolean save = false;

        try {
            Session session = entityManager.unwrap(Session.class);
            session.save(visitLedgerEntity);
            session.flush();
            save = true;
            session.clear();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());;
        }

        return save;

    }

    public VisitLedgerEntity getById (Long id) {

        VisitLedgerEntity visitLedgerEntity  = new VisitLedgerEntity();

        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria criteria = session.createCriteria(VisitLedgerEntity.class);
            criteria.add(Restrictions.eq("id",id));
            visitLedgerEntity = (VisitLedgerEntity) criteria.uniqueResult();
            session.clear();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());;
        }

        return visitLedgerEntity;
    }

    public  VisitLedgerEntity getByAcc (String accNo){
        VisitLedgerEntity visitLedgerEntity  = new VisitLedgerEntity();

        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria criteria = session.createCriteria(VisitLedgerEntity.class);
            criteria.add(Restrictions.eq("accountCardNumber",accNo));
            visitLedgerEntity = (VisitLedgerEntity) criteria.uniqueResult();
            session.clear();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());;
        }

        return visitLedgerEntity;
    }

    public boolean updateVisitLedger (VisitLedgerEntity visitLedgerEntity) {

        VisitLedgerEntity tempVisitLedger = getById(visitLedgerEntity.getId());
        boolean update =false;

        try {
//            What??
            visitLedgerEntity.setCreatedBy(tempVisitLedger.getCreatedBy());
            visitLedgerEntity.setCreatedDate(tempVisitLedger.getCreatedDate());
//            What??

            Session session = entityManager.unwrap(Session.class);
            session.merge(visitLedgerEntity);
            session.flush();
            update = true;
            session.clear();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return update;

    }

    public List<VisitLedgerEntity> getActiveOnly() {

        List<VisitLedgerEntity> visitLedgerEntityList = new ArrayList<>();

        try{
            Session session = entityManager.unwrap(Session.class);
            Criteria criteria = session.createCriteria(VisitLedgerEntity.class);
            criteria.add(Restrictions.eq("enabled",true));
            visitLedgerEntityList = criteria.list();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return visitLedgerEntityList;

    }
}