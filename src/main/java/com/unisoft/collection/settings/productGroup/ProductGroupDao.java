package com.unisoft.collection.settings.productGroup;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class ProductGroupDao {

    @Autowired
    private EntityManager entityManager;

    public List<ProductGroupEntity> getList() {
        List<ProductGroupEntity> groupList = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(ProductGroupEntity.class);
            crt.add(Restrictions.eq("cardAccount", "Loan"));
            groupList = session.createCriteria(ProductGroupEntity.class).list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return groupList;
    }

    public boolean saveGroup(ProductGroupEntity group) {
        boolean save = false;
        try {
            Session session = entityManager.unwrap(Session.class);
            session.save(group);
            session.flush();
            save = true;
            session.clear();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return save;
    }

    public ProductGroupEntity getById(Long id) {
        ProductGroupEntity group = new ProductGroupEntity();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(ProductGroupEntity.class);
            crt.add(Restrictions.eq("id", id));
            group = (ProductGroupEntity) crt.uniqueResult();
            session.clear();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return group;
    }

    public boolean updateGrp(ProductGroupEntity group) {
        ProductGroupEntity trmpGrp = getById(group.getId());
        boolean update = false;
        try {
            group.setCreatedBy(trmpGrp.getCreatedBy());
            group.setCreatedDate(trmpGrp.getCreatedDate());
            Session session = entityManager.unwrap(Session.class);
            session.merge(group);
            session.flush();
            session.clear();
            update = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return update;
    }

    public List<ProductGroupEntity> getActiveOnly() {
        List<ProductGroupEntity> groupList = new ArrayList<>();

        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(ProductGroupEntity.class);
            crt.add(Restrictions.eq("enabled", true));
            groupList = crt.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return groupList;
    }
}
