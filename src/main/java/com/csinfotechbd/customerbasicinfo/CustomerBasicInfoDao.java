package com.csinfotechbd.customerbasicinfo;

import com.csinfotechbd.collection.settings.agency.AgencyEntity;
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
public class CustomerBasicInfoDao {

    @Autowired
    private EntityManager entityManager;

    public List<CustomerBasicInfoEntity> getList() {
        List<CustomerBasicInfoEntity> customerBasicInfoEntityList = new ArrayList<>();

        try {
            Session session = entityManager.unwrap(Session.class);
            customerBasicInfoEntityList = session.createCriteria(CustomerBasicInfoEntity.class).list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return customerBasicInfoEntityList;
    }

    public boolean save(CustomerBasicInfoEntity customerBasicInfoEntity) {
        boolean save = false;

        try {
            Session session = entityManager.unwrap(Session.class);
            session.save(customerBasicInfoEntity);
            session.flush();
            save = true;
            session.clear();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return save;
    }

    public CustomerBasicInfoEntity findById(Long id) {
        CustomerBasicInfoEntity obj = new CustomerBasicInfoEntity();
        try {
            Session session = entityManager.unwrap(Session.class);

            obj = session.load(CustomerBasicInfoEntity.class, id);
            //session.clear();
            session.clear();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }
}
