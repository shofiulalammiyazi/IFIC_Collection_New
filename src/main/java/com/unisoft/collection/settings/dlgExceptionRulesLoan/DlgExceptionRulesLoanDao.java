package com.unisoft.collection.settings.dlgExceptionRulesLoan;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class DlgExceptionRulesLoanDao {

    @Autowired
    private EntityManager entityManager;

    public DlgExceptionRulesLoanInfo getDlgExceptionRulesLoanInfo() {
        DlgExceptionRulesLoanInfo dlgExceptionRulesLoanInfo = null;
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria criteria = session.createCriteria(DlgExceptionRulesLoanInfo.class);
            criteria.setMaxResults(1);
            dlgExceptionRulesLoanInfo = (DlgExceptionRulesLoanInfo) criteria.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dlgExceptionRulesLoanInfo == null ? new DlgExceptionRulesLoanInfo() : dlgExceptionRulesLoanInfo;
    }

    public boolean saveDlgExceptionRulesLoanInfo(DlgExceptionRulesLoanInfo dlgExceptionRulesLoanInfo) {
        boolean save = false;
        try {
            Session session = entityManager.unwrap(Session.class);
            session.save(dlgExceptionRulesLoanInfo);
            session.flush();
            session.clear();
            save = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return save;
    }

    public boolean updateDlgExceptionRulesLoanInfo(DlgExceptionRulesLoanInfo dlgExceptionRulesLoanInfo) {
        boolean update = false;
        try {
//            securedCardCriteriaEntity.setCreatedBy(temp.getCreatedBy());
//            securedCardCriteriaEntity.setCreatedDate(temp.getCreatedDate());
            Session session = entityManager.unwrap(Session.class);
            session.merge(dlgExceptionRulesLoanInfo);
            session.flush();
            session.clear();
            update = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return update;
    }
}
