package com.unisoft.collection.distribution.agencyAllocation.loan;
/*
Created by   Islam at 7/22/2019
*/


import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
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
public class LoanAgencyDistributionDao {

    @Autowired
    private EntityManager entityManager;

    public List<LoanAgencyDistributionInfo> getList() {
        List<LoanAgencyDistributionInfo> objList = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);
            objList = session.createCriteria(LoanAgencyDistributionInfo.class).list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return objList;
    }

    public boolean saveNew(LoanAgencyDistributionInfo obj) {
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

    public LoanAgencyDistributionInfo getById(Long id) {
        LoanAgencyDistributionInfo obj = new LoanAgencyDistributionInfo();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(LoanAgencyDistributionInfo.class);
            crt.add(Restrictions.eq("id", id));
            obj = (LoanAgencyDistributionInfo) crt.uniqueResult();
            session.clear();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public boolean updateObj(LoanAgencyDistributionInfo obj) {
        LoanAgencyDistributionInfo tempObj = getById(obj.getId());
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

    public List<LoanAgencyDistributionInfo> getActiveOnly() {
        List<LoanAgencyDistributionInfo> objList = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(LoanAgencyDistributionInfo.class);
            crt.add(Restrictions.eq("enabled", true));
            objList = crt.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return objList;
    }

    public List<LoanAgencyDistributionInfo> findByLoanAccountBasicInfo(LoanAccountBasicInfo byAccountNo) {
        List<LoanAgencyDistributionInfo> objList = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(LoanAgencyDistributionInfo.class);
            crt.add(Restrictions.eq("loanAccountBasicInfo", byAccountNo));
            objList = crt.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return objList;
    }
}
