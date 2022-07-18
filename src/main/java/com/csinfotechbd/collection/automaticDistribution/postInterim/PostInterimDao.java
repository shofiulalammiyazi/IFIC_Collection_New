package com.csinfotechbd.collection.automaticDistribution.postInterim;
/*
Created by Monirul Islam at 8/20/2019
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

@Transactional
@Repository
public class PostInterimDao {
    @Autowired
    private EntityManager entityManager;

    public List<PostInterimCondition> getList() {
        List<PostInterimCondition> objList = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);
            objList = session.createCriteria(PostInterimCondition.class).list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return objList;
    }

    public boolean saveNew(PostInterimCondition obj) {
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

    public PostInterimCondition getById(Long id) {
        PostInterimCondition obj = new PostInterimCondition();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(PostInterimCondition.class);
            crt.add(Restrictions.eq("id", id));
            obj = (PostInterimCondition) crt.uniqueResult();
            session.clear();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public boolean updateObj(PostInterimCondition obj) {
        PostInterimCondition tempObj = getById(obj.getId());
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

    public List<PostInterimCondition> getActiveOnly() {
        List<PostInterimCondition> objList = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(PostInterimCondition.class);
            crt.add(Restrictions.eq("enabled", true));
            objList = crt.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return objList;
    }
}
