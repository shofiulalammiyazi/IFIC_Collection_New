package com.unisoft.collection.settings.district;
/*
Created by   Islam at 7/3/2019
*/

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
@Transactional
public class DistrictDao {


    @Autowired
    private EntityManager entityManager;

    public List<DistrictEntity> getList() {

        List<DistrictEntity> objList = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);
            objList = session.createCriteria(DistrictEntity.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objList;
    }

    //    Changes made for lawyer table
    public List getByDivision(Long divisionId) {
        List objList = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(DistrictEntity.class)
                    .setProjection(Projections.projectionList()
                            .add(Projections.property("id"), "id")
                            .add(Projections.property("name"), "name"))
                    .setResultTransformer(Transformers.aliasToBean(HashMap.class));
            crt.add(Restrictions.eq("division.id", divisionId));
            objList = crt.list();
            session.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objList;
    }

    public boolean saveNew(DistrictEntity obj) {
        boolean save = false;

        try {
            Session session = entityManager.unwrap(Session.class);
            session.save(obj);
            session.flush();
            save = true;
            session.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return save;
    }

    public DistrictEntity getById(Long id) {
        DistrictEntity obj = new DistrictEntity();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(DistrictEntity.class);
            crt.add(Restrictions.eq("id", id));
            obj = (DistrictEntity) crt.uniqueResult();
            session.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public DistrictEntity getByName(String name) {
        DistrictEntity obj = new DistrictEntity();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(DistrictEntity.class);
            crt.add(Restrictions.eq("name", name));
            List<DistrictEntity> list = (List<DistrictEntity>) crt.list();
            obj = list.isEmpty() ? null : list.get(0);
            session.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public boolean updateObj(DistrictEntity obj) {
        DistrictEntity tempObj = getById(obj.getId());
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
            e.printStackTrace();
        }
        return update;
    }

    public List<DistrictEntity> getActiveOnly() {
        List<DistrictEntity> objList = new ArrayList<>();

        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(DistrictEntity.class);
            crt.add(Restrictions.eq("enabled", true));
            objList = crt.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objList;
    }
}
