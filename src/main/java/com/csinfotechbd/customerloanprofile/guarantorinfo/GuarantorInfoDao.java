package com.csinfotechbd.customerloanprofile.guarantorinfo;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class GuarantorInfoDao {
    @Autowired
    private EntityManager entityManager;

    public List<GuarantorInfoEntity> getList(){

        List<GuarantorInfoEntity> guarantorList = new ArrayList<>();

        try {
            Session session = entityManager.unwrap(Session.class);
            guarantorList = session.createCriteria(GuarantorInfoEntity.class).list();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return guarantorList;
    }

    public boolean save (GuarantorInfoEntity guarantorInfoEntity) {
        boolean save = false;

        try {
            Session session = entityManager.unwrap(Session.class);
            session.save(guarantorInfoEntity);
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
