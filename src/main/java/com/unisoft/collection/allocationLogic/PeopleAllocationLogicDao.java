package com.unisoft.collection.allocationLogic;
/*
Created by   Islam at 7/18/2019
*/

import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
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
public class PeopleAllocationLogicDao {
    @Autowired
    private EntityManager entityManager;

    public List<PeopleAllocationLogicInfo> getList() {
        List<PeopleAllocationLogicInfo> objList = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);
            objList = session.createCriteria(PeopleAllocationLogicInfo.class).list();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return objList;
    }

    public boolean saveNew(PeopleAllocationLogicInfo obj) {
        boolean save = false;
        try {
            Session session = entityManager.unwrap(Session.class);
            session.save(obj);
            session.flush();
            save = true;
            session.clear();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return save;
    }

    public PeopleAllocationLogicInfo getById(Long id) {
        PeopleAllocationLogicInfo obj = new PeopleAllocationLogicInfo();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(PeopleAllocationLogicInfo.class);
            crt.add(Restrictions.eq("id", id));
            obj = (PeopleAllocationLogicInfo) crt.uniqueResult();
            session.clear();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return obj;
    }

    public boolean updateObj(PeopleAllocationLogicInfo obj) {
        PeopleAllocationLogicInfo tempObj = getById(obj.getId());
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
            System.err.println(e.getMessage());
        }
        return update;
    }

    public List<PeopleAllocationLogicInfo> getActiveOnly() {
        List<PeopleAllocationLogicInfo> objList = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(PeopleAllocationLogicInfo.class);
            crt.add(Restrictions.eq("enabled", true));
            objList = crt.list();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return objList;
    }

    public PeopleAllocationLogicInfo getPeopleAlocationByDealerAndUnit(EmployeeInfoEntity dealer, String unit) {
        PeopleAllocationLogicInfo logicInfo = new PeopleAllocationLogicInfo();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(PeopleAllocationLogicInfo.class);
            crt.add(Restrictions.eq("dealer", dealer));
            crt.add(Restrictions.eq("unit", unit));
            logicInfo = (PeopleAllocationLogicInfo) crt.uniqueResult();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return logicInfo;
    }
}
