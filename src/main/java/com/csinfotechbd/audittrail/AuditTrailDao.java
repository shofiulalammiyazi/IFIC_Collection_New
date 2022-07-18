package com.csinfotechbd.audittrail;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class AuditTrailDao {
    @Autowired
    private EntityManager entityManager;

    public List<AuditTrail> rangeAuditList(Date startDate, Date endDate) {
        List<AuditTrail> tempList = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria criteria = session.createCriteria(AuditTrail.class);
            criteria.add(Restrictions.ge("createdDate", startDate));
            criteria.add(Restrictions.le("createdDate", endDate));
            tempList = criteria.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tempList;
    }

    public AuditTrail getById(Long id) {
        AuditTrail auditTrail = new AuditTrail();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria criteria = session.createCriteria(AuditTrail.class);
            criteria.add(Restrictions.eq("id", id));
            auditTrail = (AuditTrail) criteria.uniqueResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return auditTrail;
    }
}
