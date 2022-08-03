package com.unisoft.customerloanprofile.dailynote;

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
public class DailyNoteDao {
    @Autowired
    private EntityManager entityManager;

    /*public List<DailyNoteEntity> getAllList(Long cusAllId ) {

        List<DailyNoteEntity> dailyNoteListAll = new ArrayList<>();

        try {


        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }*/

    public List<DailyNoteEntity> getByLoanAccountNo(String accountNo){

        List<DailyNoteEntity> dailyNoteEntityList = new ArrayList<>();

        try {

            CustomerBasicInfoEntity customerBasicInfoEntity=new CustomerBasicInfoEntity();
            customerBasicInfoEntity.setAccountNo(accountNo);
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(DailyNoteEntity.class);
            crt.add(Restrictions.eq("customerBasicInfo",customerBasicInfoEntity));
            crt.addOrder(Order.desc("id"));

            crt.setMaxResults(5);
            dailyNoteEntityList = (List<DailyNoteEntity>) crt.list();
            session.clear();
            //System.err.println("DN :"+dailyNoteList);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return dailyNoteEntityList;
    }

    public List<DailyNoteEntity> getList(Long cusId){

        List<DailyNoteEntity> dailyNoteList = new ArrayList<>();

        try {

            CustomerBasicInfoEntity customerBasicInfoEntity=new CustomerBasicInfoEntity();
            customerBasicInfoEntity.setId(cusId);
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(DailyNoteEntity.class);
            crt.add(Restrictions.eq("customerBasicInfo",customerBasicInfoEntity));
            crt.addOrder(Order.desc("id"));

            crt.setMaxResults(5);
            dailyNoteList = (List<DailyNoteEntity>) crt.list();
            session.clear();
            //System.err.println("DN :"+dailyNoteList);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return dailyNoteList;
    }

    public boolean save (DailyNoteEntity dailyNoteEntity) {
        boolean save = false;

        try {
            Session session = entityManager.unwrap(Session.class);
            session.save(dailyNoteEntity);
            session.flush();
            save= true;
            session.clear();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return save;
    }

    public DailyNoteEntity getById(Long id){
        Query query = entityManager.createQuery("FROM DailyNoteEntity WHERE id = :id");
        query.setParameter("id", id);
        DailyNoteEntity entity = (DailyNoteEntity) query.getSingleResult();
        return entity;
    }


}
