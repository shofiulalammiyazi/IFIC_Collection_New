package com.unisoft.dms.logicdoc;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Tanmoy on 4/23/2017.
 * Updated by Tanmoy on 4/23/2017.
 */
@Repository
@Transactional
public class LogiDocDao {

    @Autowired
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }


    public void saveLogidocData(LogiDocSettingsEntity logiDocSettingsEntity) {
        getSession().saveOrUpdate(logiDocSettingsEntity);
    }

    public LogiDocSettingsEntity findLogiDocData() {
        Criteria criteria = getSession().createCriteria(LogiDocSettingsEntity.class);
        return (LogiDocSettingsEntity) criteria.list().get(0);
    }
}
