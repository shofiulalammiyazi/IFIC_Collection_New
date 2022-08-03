package com.unisoft.retail.card.dataEntry.contactInfoCard;

import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
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
public class ContactInfoCardDao {

    @Autowired
    private EntityManager entityManager;

    public List<ContactInfoCard> getList(Long cusId){
        List<ContactInfoCard> contactInfoCardList = new ArrayList<>();
        try{
            CustomerBasicInfoEntity customerBasicInfoEntity = new CustomerBasicInfoEntity();
            customerBasicInfoEntity.setId(cusId);
            Session session = entityManager.unwrap(Session.class);
            Criteria criteria = session.createCriteria(ContactInfoCard.class);
            criteria.add(Restrictions.eq("customerBasicInfo",customerBasicInfoEntity));
            criteria.addOrder(Order.desc("id"));

            criteria.setMaxResults(10);
            contactInfoCardList = (List<ContactInfoCard>) criteria.list();
            session.clear();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return contactInfoCardList;
    }

    public boolean save(ContactInfoCard contactInfoCard){

        boolean save = false;

        try{
            Session session = entityManager.unwrap(Session.class);
            session.save(contactInfoCard);
            session.flush();
            save=true;
            session.clear();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return save;
    }

    public ContactInfoCard getById(Long id){
        Query query = entityManager.createQuery(" FROM ContactInfoCard WHERE id = :id");
        query.setParameter("id",id);
        ContactInfoCard contactInfoCard = (ContactInfoCard) query.getSingleResult();
        return contactInfoCard;
    }
}
