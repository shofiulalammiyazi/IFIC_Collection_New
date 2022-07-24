package com.unisoft.loanApi.model;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class CustomerAndBorrowerInfoDao {

    @Autowired
    private EntityManager entityManager;


    public CustomerAndBorrowerInfo findById(Long id){
        CustomerAndBorrowerInfo customerAndBorrowerInfo = new CustomerAndBorrowerInfo();
        try {
            Session session = entityManager.unwrap(Session.class);
            customerAndBorrowerInfo = session.byId(CustomerAndBorrowerInfo.class).getReference(id);
            session.flush();
            session.clear();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return customerAndBorrowerInfo;
    }
}
