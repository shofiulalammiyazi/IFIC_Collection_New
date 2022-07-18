package com.csinfotechbd.collection.settings.employeeStatus;
/*
Created by Monirul Islam at 6/25/2019 
*/

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class EmployeeStatusDao {

    @Autowired
    private EntityManager entityManager;

    public List<EmployeeStatusEntity> getList() {

        List<EmployeeStatusEntity> statList = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);
            statList = session.createCriteria(EmployeeStatusEntity.class).list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return statList;
    }

    public boolean saveNew(EmployeeStatusEntity status) {
        boolean save = false;

        try {
            Session session = entityManager.unwrap(Session.class);
            session.save(status);
            session.flush();
            save = true;
            session.clear();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return save;
    }

    public EmployeeStatusEntity getById(Long id) {
        EmployeeStatusEntity status = new EmployeeStatusEntity();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(EmployeeStatusEntity.class);
            crt.add(Restrictions.eq("id", id));
            status = (EmployeeStatusEntity) crt.uniqueResult();
            session.clear();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return status;
    }

    public boolean updateStat(EmployeeStatusEntity status) {
        EmployeeStatusEntity tempDiv = getById(status.getId());
        boolean update = false;
        try {
            status.setCreatedDate(tempDiv.getCreatedDate());
            status.setCreatedBy(tempDiv.getCreatedBy());
            Session session = entityManager.unwrap(Session.class);
            session.merge(status);
            session.flush();
            session.clear();
            update = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return update;
    }

    public List<EmployeeStatusEntity> getActiveOnly() {
        List<EmployeeStatusEntity> statList = new ArrayList<>();

        try {
            String sql = "SELECT e FROM EmployeeStatusEntity e WHERE e.enabled=true ORDER BY e.loginDisable ASC";
            TypedQuery<EmployeeStatusEntity> query = entityManager.createQuery(sql, EmployeeStatusEntity.class);
            statList = query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return statList;
    }


}
