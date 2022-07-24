package com.unisoft.collection.settings.ageAndClassificationRule;
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
public class AgeAndClassificationRuleDao {

    @Autowired
    private EntityManager entityManager;

    public List<AgeAndClassifiactionRuleEntity> getList() {

        List<AgeAndClassifiactionRuleEntity> objList;
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(AgeAndClassifiactionRuleEntity.class);
            objList = crt.list();
            return objList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public boolean saveNew(AgeAndClassifiactionRuleEntity obj) {
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

    public AgeAndClassifiactionRuleEntity getById(Long id) {
        AgeAndClassifiactionRuleEntity obj = new AgeAndClassifiactionRuleEntity();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(AgeAndClassifiactionRuleEntity.class);
            crt.add(Restrictions.eq("id", id));
            obj = (AgeAndClassifiactionRuleEntity) crt.uniqueResult();
            session.clear();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public boolean updateObj(AgeAndClassifiactionRuleEntity obj) {
        AgeAndClassifiactionRuleEntity tempObj = getById(obj.getId());
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

    public List<AgeAndClassifiactionRuleEntity> getActiveOnly() {
        List<AgeAndClassifiactionRuleEntity> objList = new ArrayList<>();

        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(AgeAndClassifiactionRuleEntity.class);
            crt.add(Restrictions.eq("enabled", true));
            objList = crt.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return objList;
    }
}
