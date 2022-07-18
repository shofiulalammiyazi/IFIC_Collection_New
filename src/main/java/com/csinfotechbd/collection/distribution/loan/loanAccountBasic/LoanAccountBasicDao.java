package com.csinfotechbd.collection.distribution.loan.loanAccountBasic;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/*
Created by Monirul Islam at 7/17/2019
*/
@Repository
@Transactional
public class LoanAccountBasicDao {
    @Autowired
    private EntityManager entityManager;

    public List<LoanAccountBasicInfo> getList() {
        List<LoanAccountBasicInfo> objList = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);
            objList = session.createCriteria(LoanAccountBasicInfo.class).list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return objList;
    }

    public boolean saveNew(LoanAccountBasicInfo obj) {
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

    public LoanAccountBasicInfo getById(Long id) {
        LoanAccountBasicInfo obj = new LoanAccountBasicInfo();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(LoanAccountBasicInfo.class);
            crt.add(Restrictions.eq("id", id));
            obj = (LoanAccountBasicInfo) crt.uniqueResult();
            session.clear();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public boolean updateObj(LoanAccountBasicInfo obj) {
        LoanAccountBasicInfo tempObj = getById(obj.getId());
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

    public List<LoanAccountBasicInfo> getActiveOnly() {
        List<LoanAccountBasicInfo> objList = new ArrayList<>();

        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(LoanAccountBasicInfo.class);
            crt.add(Restrictions.eq("enabled", true));
            objList = crt.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return objList;
    }

    public LoanAccountBasicInfo findByAccountNo(String number) {
        LoanAccountBasicInfo loanAccountBasicInfo = new LoanAccountBasicInfo();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(LoanAccountBasicInfo.class);
            crt.add(Restrictions.eq("accountNo", number));
            loanAccountBasicInfo = (LoanAccountBasicInfo) crt.uniqueResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return loanAccountBasicInfo;
    }
}
