package com.csinfotechbd.collection.dashboard;
/*
  Created by Md. Monirul Islam on 2/15/2020
*/

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class AccountWiseSummaryModelDao {


    @Autowired
    private EntityManager entityManager;

    public AccountWiseSummaryModel getByDealerPin(String dealerPin,String productGroup,String ageCode)
    {
        AccountWiseSummaryModel summaryModel=null;
        try{
            Session session=entityManager.unwrap(Session.class);

            Criteria crt=session.createCriteria(AccountWiseSummaryModel.class);

            crt.add(Restrictions.eq("dealerPin",dealerPin));
            crt.add(Restrictions.eq("PG",productGroup));
            crt.add(Restrictions.eq("productAndDpd",ageCode));
            crt.addOrder(Order.desc("updateDate"));
            crt.setMaxResults(1);
            summaryModel=(AccountWiseSummaryModel)crt.uniqueResult();

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return summaryModel;
    }

    public boolean saveOrUpdateData(AccountWiseSummaryModel summaryModel)
    {
        boolean save=false;
        try{
            Session session=entityManager.unwrap(Session.class);
            session.saveOrUpdate(summaryModel);
            session.flush();
            save=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return save;
    }

    public List<AccountWiseSummaryModel> getListByDealerPin(String dealerPin)
    {
        List<AccountWiseSummaryModel> summaryModels=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt= session.createCriteria(AccountWiseSummaryModel.class);
            crt.add(Restrictions.eq("dealerPin",dealerPin));
            summaryModels=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return summaryModels;
    }

}
