package com.csinfotechbd.customerloanprofile.referenceinfo;

import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ReferenceInfoDao {
    @Autowired
    private EntityManager entityManager;

    public List<ReferenceInfoEntity> getList(){

        List<ReferenceInfoEntity> referenceList = new ArrayList<>();

        try {
            Session session = entityManager.unwrap(Session.class);
            referenceList = session.createCriteria(ReferenceInfoEntity.class).list();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return referenceList;
    }

    public List<ReferenceInfoEntity> findByCustomer(CustomerBasicInfoEntity customer){

        List<ReferenceInfoEntity> referenceList = new ArrayList<>();

        try {
            Session session = entityManager.unwrap(Session.class);
            referenceList = session
                    .createCriteria(ReferenceInfoEntity.class)
                    .add(Restrictions.eq("customerBasicInfo", customer))
                    .list();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return referenceList;
    }

    public boolean save (ReferenceInfoEntity referenceInfoEntity) {
        boolean save = false;

        try {
            Session session = entityManager.unwrap(Session.class);
            session.saveOrUpdate(referenceInfoEntity);
            session.flush();
            save= true;
            session.clear();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return save;
    }

    public ReferenceInfoEntity findById(Long id){
            ReferenceInfoEntity referenceInfoEntity = new ReferenceInfoEntity();
            try {
                Session session = entityManager.unwrap(Session.class);
                 referenceInfoEntity = session.byId(ReferenceInfoEntity.class).getReference(id);
                 session.flush();
                 session.clear();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            return referenceInfoEntity;
    }

    public void deleteReference(ReferenceInfoEntity referenceInfoEntity){
        try {
            Session session = entityManager.unwrap(Session.class);
            session.delete(referenceInfoEntity);
            session.flush();
            session.clear();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


}
