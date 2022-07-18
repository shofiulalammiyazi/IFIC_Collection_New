package com.csinfotechbd.loanApi.model;

import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

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
