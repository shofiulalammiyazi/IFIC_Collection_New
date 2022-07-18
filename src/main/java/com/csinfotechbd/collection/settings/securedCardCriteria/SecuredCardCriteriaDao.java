package com.csinfotechbd.collection.settings.securedCardCriteria;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional

public class SecuredCardCriteriaDao {

    @Autowired
    private EntityManager entityManager;

    public List<SecuredCardCriteriaEntity> getAll() {
        List<SecuredCardCriteriaEntity> sectGrpList = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(SecuredCardCriteriaEntity.class);
            crt.addOrder(Order.desc("id"));
            crt.setMaxResults(1);
            sectGrpList = crt.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return sectGrpList;
    }

    public SecuredCardCriteriaEntity getSecuredCardLatest() {
        SecuredCardCriteriaEntity obj = new SecuredCardCriteriaEntity();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria criteria = session.createCriteria(SecuredCardCriteriaEntity.class);
            criteria.setMaxResults(1);
            obj = (SecuredCardCriteriaEntity) criteria.uniqueResult();
            session.clear();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public SecuredCardCriteriaEntity getById(Long Id) {
        SecuredCardCriteriaEntity sectGrp = new SecuredCardCriteriaEntity();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(SecuredCardCriteriaEntity.class);
            crt.add(Restrictions.eq("id", Id));
            sectGrp = (SecuredCardCriteriaEntity) crt.uniqueResult();
            session.clear();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return sectGrp;
    }

    public boolean saveSecuredCard(SecuredCardCriteriaEntity securedCardCriteriaEntity) {
        boolean save = false;
        try {
            Session session = entityManager.unwrap(Session.class);
            session.save(securedCardCriteriaEntity);
            session.flush();
            session.clear();
            save = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return save;
    }

    public boolean updateSecuredCard(SecuredCardCriteriaEntity securedCardCriteriaEntity) {
        SecuredCardCriteriaEntity temp = getSecuredCardLatest();  /*getById(securedCardCriteriaEntity.getId());*/
        boolean update = false;
        try {
            securedCardCriteriaEntity.setCreatedBy(temp.getCreatedBy());
            securedCardCriteriaEntity.setCreatedDate(temp.getCreatedDate());
            Session session = entityManager.unwrap(Session.class);
            session.merge(securedCardCriteriaEntity);
            session.flush();
            session.clear();
            update = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return update;
    }

    public List<SecuredCardCriteriaEntity> getActiveList() {
        List<SecuredCardCriteriaEntity> secCardList = new ArrayList<>();

        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(SecuredCardCriteriaEntity.class);
            crt.add(Restrictions.eq("enabled", true));
            secCardList = crt.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return secCardList;
    }
}
