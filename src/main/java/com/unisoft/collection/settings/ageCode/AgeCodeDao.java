package com.unisoft.collection.settings.ageCode;
/*
Created by   Islam on 7/9/2019
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
public class AgeCodeDao {

    @Autowired
    private EntityManager entityManager;

    public List<AgeCodeEntity> getList() {
        List<AgeCodeEntity> objList;
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(AgeCodeEntity.class);
            objList = crt.list();
            return objList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean saveNew(AgeCodeEntity obj) {
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

    public AgeCodeEntity getById(Long id) {
        AgeCodeEntity obj = new AgeCodeEntity();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(AgeCodeEntity.class);
            crt.add(Restrictions.eq("id", id));
            obj = (AgeCodeEntity) crt.uniqueResult();
            session.clear();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public boolean updateObj(AgeCodeEntity obj) {
        AgeCodeEntity tempObj = getById(obj.getId());
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

    public List<AgeCodeEntity> getActiveOnly() {
        List<AgeCodeEntity> objList = new ArrayList<>();

        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(AgeCodeEntity.class);
            crt.add(Restrictions.eq("enabled", true));
            objList = crt.list();
            session.clear();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return objList;
    }
}
