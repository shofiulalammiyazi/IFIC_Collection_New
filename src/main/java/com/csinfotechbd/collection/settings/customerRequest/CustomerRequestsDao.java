package com.csinfotechbd.collection.settings.customerRequest;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
@Repository
@Transactional
public class CustomerRequestsDao {

    @Autowired
    private EntityManager entityManager;

    public List<CustomerRequestsEntity> getList(){

        List<CustomerRequestsEntity> customerRequestsEntityList = new ArrayList<>();

        try {
            Session session = entityManager.unwrap(Session.class);
            customerRequestsEntityList = session.createCriteria(CustomerRequestsEntity.class).list();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return customerRequestsEntityList;
    }

    public boolean save (CustomerRequestsEntity customerRequestsEntity) {
        boolean save = false;

        try {
            Session session = entityManager.unwrap(Session.class);
            session.save(customerRequestsEntity);
            session.flush();
            save= true;
            session.clear();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return save;
    }

}
