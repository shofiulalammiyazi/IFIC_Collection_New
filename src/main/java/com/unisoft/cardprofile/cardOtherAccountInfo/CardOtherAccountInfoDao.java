package com.unisoft.cardprofile.cardOtherAccountInfo;

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
public class CardOtherAccountInfoDao {

    @Autowired
    private EntityManager entityManager;

    public List<CardOtherAccountInfo> getList() {
        List<CardOtherAccountInfo> objList = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);
            objList = session.createCriteria(CardOtherAccountInfo.class).list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return objList;
    }

    public boolean saveNew(CardOtherAccountInfo obj) {
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

    public CardOtherAccountInfo getById(Long id) {
        CardOtherAccountInfo obj = new CardOtherAccountInfo();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(CardOtherAccountInfo.class);
            crt.add(Restrictions.eq("id", id));
            obj = (CardOtherAccountInfo) crt.uniqueResult();
            session.clear();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public boolean updateObj(CardOtherAccountInfo obj) {
        CardOtherAccountInfo tempObj = getById(obj.getId());
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

    public List<CardOtherAccountInfo> getActiveOnly() {
        List<CardOtherAccountInfo> objList = new ArrayList<>();

        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(CardOtherAccountInfo.class);
            crt.add(Restrictions.eq("enabled", true));
            objList = crt.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return objList;
    }

    public boolean delete(long id) {
        Session session = entityManager.unwrap(Session.class);
        CardOtherAccountInfo cardOtherAccountInfo = new CardOtherAccountInfo();
        try {
            cardOtherAccountInfo = getById(id);
            cardOtherAccountInfo.setEnabled(false);
            session.saveOrUpdate(cardOtherAccountInfo);
            session.flush();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
