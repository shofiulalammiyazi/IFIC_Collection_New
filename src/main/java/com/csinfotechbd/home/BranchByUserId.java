package com.csinfotechbd.home;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class BranchByUserId {

    @Autowired
    private EntityManager entityManager;

    public String branchByUserId(String uid) {
        Session session = entityManager.unwrap(Session.class);
        try {
            String sql = "select branch_id from los_tb_m_users where user_id=" + uid;
            SQLQuery query = session.createSQLQuery(sql);
            String branchId = query.uniqueResult().toString();
            return branchId;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
