package com.csinfotechbd.collection.distribution.loan.loanAccount;
/*
Created by Monirul Islam at 7/17/2019
*/

import com.csinfotechbd.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
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
public class LoanAccountDao {
    @Autowired
    private EntityManager entityManager;

    public List<LoanAccountInfo> getList() {
        List<LoanAccountInfo> objList = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);
            objList = session.createCriteria(LoanAccountInfo.class).list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return objList;
    }

    public boolean saveNew(LoanAccountInfo obj) {
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

    public LoanAccountInfo getById(Long id) {
        LoanAccountInfo obj = new LoanAccountInfo();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(LoanAccountInfo.class);
            crt.add(Restrictions.eq("id", id));
            obj = (LoanAccountInfo) crt.uniqueResult();
            session.clear();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public boolean updateObj(LoanAccountInfo obj) {
        LoanAccountInfo tempObj = getById(obj.getId());
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

    public List<LoanAccountInfo> getActiveOnly() {
        List<LoanAccountInfo> objList = new ArrayList<>();

        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(LoanAccountInfo.class);
            crt.add(Restrictions.eq("enabled", true));
            objList = crt.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return objList;
    }

    public List<LoanAccountInfo> findByLoanAccountBasicId(LoanAccountBasicInfo loanAccountBasicInfo) {
        List<LoanAccountInfo> obj = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(LoanAccountInfo.class);
            crt.add(Restrictions.eq("loanAccountBasicInfo", loanAccountBasicInfo));
            obj = crt.list();
            session.clear();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }
}
