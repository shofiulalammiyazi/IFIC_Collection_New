package com.csinfotechbd.collection.distribution.loan.loanAccountDistribution;
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
public class LoanAccountDistributionDao {

    @Autowired
    private EntityManager entityManager;

    public List<LoanAccountDistributionInfo> getList() {
        List<LoanAccountDistributionInfo> objList = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);
            objList = session.createCriteria(LoanAccountDistributionInfo.class).list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return objList;
    }

    public boolean saveNew(LoanAccountDistributionInfo obj) {
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

    public LoanAccountDistributionInfo getById(Long id) {
        LoanAccountDistributionInfo obj = new LoanAccountDistributionInfo();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(LoanAccountDistributionInfo.class);
            crt.add(Restrictions.eq("id", id));
            obj = (LoanAccountDistributionInfo) crt.uniqueResult();
            session.clear();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public boolean updateObj(LoanAccountDistributionInfo obj) {
        LoanAccountDistributionInfo tempObj = getById(obj.getId());
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

    public List<LoanAccountDistributionInfo> getActiveOnly() {
        List<LoanAccountDistributionInfo> objList = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(LoanAccountDistributionInfo.class);
            crt.add(Restrictions.eq("enabled", true));
            objList = crt.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return objList;
    }

    public List<LoanAccountDistributionInfo> findByLoanAccountBasicInfo(LoanAccountBasicInfo loanAccountBasicInfo) {
        List<LoanAccountDistributionInfo> objList = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(LoanAccountDistributionInfo.class);
            crt.add(Restrictions.eq("loanAccountBasicInfo", loanAccountBasicInfo));
            objList = crt.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return objList;
    }
}
