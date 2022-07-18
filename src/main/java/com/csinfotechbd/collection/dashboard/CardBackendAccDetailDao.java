package com.csinfotechbd.collection.dashboard;
/*
  Created by Md. Monirul Islam on 1/20/2020
*/

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;

@Transactional
@Repository
public class CardBackendAccDetailDao {

    @Autowired
    private EntityManager entityManager;

    public boolean saveNew(CardBackendAccDetailsEntity accDetailsEntity) {
        boolean save;
        try{
//            Session session=entityManager.unwrap(Session.class);
//
//            session.save(accDetailsEntity);
//            session.flush();
//            session.clear();
            entityManager.persist(accDetailsEntity);
            entityManager.flush();
            entityManager.close();
            save=true;
        }catch (Exception e) {
            save=false;
            System.out.println(e.getMessage());
        }
        return save;
    }

    public CardBackendAccDetailsEntity getByAccNo(String cardNo, Date startDate,Date endDate) {
        CardBackendAccDetailsEntity cardBackendAccDetailsEntity=null;

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt= session.createCriteria(CardBackendAccDetailsEntity.class);
            crt.add(Restrictions.eq("cardNo",cardNo));
            crt.add(Restrictions.between("createdDate",startDate,endDate));
            crt.addOrder(Order.desc("id"));
            crt.setMaxResults(1);
            cardBackendAccDetailsEntity=(CardBackendAccDetailsEntity)crt.uniqueResult();
            session.flush();
            session.clear();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return cardBackendAccDetailsEntity;
    }

    public boolean updateBack(CardBackendAccDetailsEntity accDetailsEntity) {
        boolean update=false;
        try{
            Session session=entityManager.unwrap(Session.class);
            session.update(accDetailsEntity);
            session.flush();
            session.clear();
//            entityManager.merge(accDetailsEntity);
//            entityManager.flush();
//            update=true;
        }catch (Exception e) {
//            System.out.println(e.getMessage());
            update=false;
        }
        return update;
    }
}
