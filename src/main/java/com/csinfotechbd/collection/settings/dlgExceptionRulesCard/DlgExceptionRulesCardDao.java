package com.csinfotechbd.collection.settings.dlgExceptionRulesCard;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class DlgExceptionRulesCardDao {

    @Autowired
    private EntityManager entityManager;

    public DlgExceptionRulesCardInfo getDlgExceptionRulesCardInfo () {
        DlgExceptionRulesCardInfo dlgExceptionRulesCardInfo = new DlgExceptionRulesCardInfo();

        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria criteria = session.createCriteria(DlgExceptionRulesCardInfo.class);
            criteria.setMaxResults(1);
            dlgExceptionRulesCardInfo = (DlgExceptionRulesCardInfo) criteria.uniqueResult();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return dlgExceptionRulesCardInfo;
    }


    public boolean saveDlgExceptionRulesCardInfo(DlgExceptionRulesCardInfo dlgExceptionRulesCardInfo){

        boolean save = false;

        try {
            Session session = entityManager.unwrap(Session.class);
            session.save(dlgExceptionRulesCardInfo);
            session.flush();
            session.clear();
            save = true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return save;

    }

    public boolean updateDlgExceptionRulesCardInfo(DlgExceptionRulesCardInfo dlgExceptionRulesCardInfo)
    {
        boolean update=false;
        try{
//            securedCardCriteriaEntity.setCreatedBy(temp.getCreatedBy());
//            securedCardCriteriaEntity.setCreatedDate(temp.getCreatedDate());
            Session session=entityManager.unwrap(Session.class);
            session.merge(dlgExceptionRulesCardInfo);
            session.flush();
            session.clear();
            update=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return update;
    }
}
