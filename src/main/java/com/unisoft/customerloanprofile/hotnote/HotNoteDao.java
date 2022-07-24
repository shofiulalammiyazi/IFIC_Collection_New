package com.unisoft.customerloanprofile.hotnote;


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
public class HotNoteDao {
    @Autowired
    private EntityManager entityManager;

    public HotNoteEntity getById(Long id){
        Query query = entityManager.createQuery("FROM HotNoteEntity WHERE id = :id");
        query.setParameter("id", id);
        HotNoteEntity entity = (HotNoteEntity) query.getSingleResult();
        return entity;
    }

    public List<HotNoteEntity> getList(Long cusId){

        List<HotNoteEntity> hotNoteList= new ArrayList<>();

        try {

            CustomerBasicInfoEntity customerBasicInfoEntity=new CustomerBasicInfoEntity();
            customerBasicInfoEntity.setId(cusId);
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(HotNoteEntity.class);
            crt.add(Restrictions.eq("customerBasicInfo",customerBasicInfoEntity));

            crt.addOrder(Order.desc("statusFlag"));
            crt.addOrder(Order.desc("id"));

//            crt.setMaxResults(5);
            hotNoteList = (List<HotNoteEntity>) crt.list();
            session.clear();
            //System.err.println("DN :"+dailyNoteList);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return hotNoteList;
    }

    public boolean save (HotNoteEntity hotNoteEntity) {
        boolean save = false;

        Session session = entityManager.unwrap(Session.class);
        try {

            if(hotNoteEntity.getId() != null){
                session.merge(hotNoteEntity);
            }else {
                session.saveOrUpdate(hotNoteEntity);
            }

            session.flush();
            save= true;
            session.clear();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        }

        return save;
    }
}
