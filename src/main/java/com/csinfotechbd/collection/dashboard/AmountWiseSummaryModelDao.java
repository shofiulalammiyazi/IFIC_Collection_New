package com.csinfotechbd.collection.dashboard;
/*
  Created by Md. Monirul Islam on 2/12/2020
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
public class AmountWiseSummaryModelDao {

    @Autowired
    private EntityManager entityManager;

    public AmountWiseSummaryModel getDataByDealerpin(String dealerPin, String productGroup, String ageCode) {
        AmountWiseSummaryModel summaryModel = null;
        try {
            Session session = entityManager.unwrap(Session.class);

            Criteria crt = session.createCriteria(AmountWiseSummaryModel.class);

            crt.add(Restrictions.eq("dealerPin", dealerPin));
            crt.add(Restrictions.eq("PG", productGroup));
            crt.add(Restrictions.eq("productAndDpd", ageCode));
            crt.addOrder(Order.desc("updateDate"));
            crt.setMaxResults(1);
            summaryModel = (AmountWiseSummaryModel) crt.uniqueResult();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return summaryModel;
    }

    public boolean saveOrUpdateData(AmountWiseSummaryModel summaryModel) {
        boolean save = false;
        try {
            Session session = entityManager.unwrap(Session.class);
            session.saveOrUpdate(summaryModel);
            session.flush();
            save = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return save;
    }

    public List<AmountWiseSummaryModel> getListByDealerPin(String dealerPin) {
        List<AmountWiseSummaryModel> summaryModels = new ArrayList<>();

        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(AmountWiseSummaryModel.class);
            crt.add(Restrictions.eq("dealerPin", dealerPin));
            summaryModels = crt.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return summaryModels;
    }
}
