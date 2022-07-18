package com.csinfotechbd.collection.settings.NPLreleaseAmount;
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
public class NPLReleaseAmountDao {
    @Autowired
    private EntityManager entityManager;

    public boolean saveNew(NPLReleaseAmountEntity obj) {
        boolean save = false;

        try {
            Session session = entityManager.unwrap(Session.class);
            session.save(obj);
            session.flush();
            save = true;
            session.clear();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return save;
    }

    public NPLReleaseAmountEntity getNPL() {
        NPLReleaseAmountEntity obj = new NPLReleaseAmountEntity();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(NPLReleaseAmountEntity.class);
            //crt.setMaxResults(1);
            obj = (NPLReleaseAmountEntity) crt.uniqueResult();
            session.clear();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public boolean updateObj(NPLReleaseAmountEntity obj) {
        NPLReleaseAmountEntity tempObj = getNPL();
        boolean update = false;
        try {
            obj.setCreatedDate(tempObj.getCreatedDate());
            obj.setCreatedBy(tempObj.getCreatedBy());
            Session session = entityManager.unwrap(Session.class);
            session.merge(obj);
            session.flush();
            session.clear();
            update = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return update;
    }
}
