package com.csinfotechbd.customerloanprofile.contactInfo;

import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ContactInfoDao {

    @Autowired
    private EntityManager entityManager;

    public List<ContactInfo> getList(Long cusId){

        List<ContactInfo> contactInfoList = new ArrayList<>();

        try{

            CustomerBasicInfoEntity customerBasicInfoEntity = new CustomerBasicInfoEntity();
            customerBasicInfoEntity.setId(cusId);
            Session session = entityManager.unwrap(Session.class);
            Criteria criteria = session.createCriteria(ContactInfo.class);
            criteria.add(Restrictions.eq("customerBasicInfo",customerBasicInfoEntity));
            criteria.addOrder(Order.desc("id"));

            criteria.setMaxResults(10);
            contactInfoList = (List<ContactInfo>) criteria.list();
            session.clear();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return contactInfoList;
    }

    public boolean save(ContactInfo contactInfo){
        boolean save = false;

        try {
            Session session = entityManager.unwrap(Session.class);
            session.save(contactInfo);
            session.flush();
            save=true;
            session.clear();
        }

        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return save;
    }

    public ContactInfo getById(Long id){
        Query query = entityManager.createQuery("FROM ContactInfo WHERE id = :id");
        query.setParameter("id", id);
        ContactInfo entity = (ContactInfo) query.getSingleResult();
        return entity;
    }
}
