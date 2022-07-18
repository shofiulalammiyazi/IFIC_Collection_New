package com.csinfotechbd.collection.agency.agencyStatus;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AgencyStatusDao {

    @Autowired
    private EntityManager entityManager;

    public List<AgencyStatusEntity> getList() {

        List<AgencyStatusEntity> statList = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);
            statList = session.createCriteria(AgencyStatusEntity.class).list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return statList;
    }

    public boolean saveNew(AgencyStatusEntity status) {
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

    public AgencyStatusEntity getById(Long id) {
        AgencyStatusEntity status = new AgencyStatusEntity();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(AgencyStatusEntity.class);
            crt.add(Restrictions.eq("id", id));
            status = (AgencyStatusEntity) crt.uniqueResult();
            session.clear();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return status;
    }

    public boolean updateStat(AgencyStatusEntity status) {
        AgencyStatusEntity tempDiv = getById(status.getId());
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

    public List<AgencyStatusEntity> getActiveOnly() {
        List<AgencyStatusEntity> statList = new ArrayList<>();

        try {
            String sql = "SELECT  e FROM AgencyStatusEntity  e WHERE e.enabled=true ORDER BY e.loginDisable ASC";
            TypedQuery<AgencyStatusEntity> query = entityManager.createQuery(sql, AgencyStatusEntity.class);
            statList = query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return statList;
    }
}
