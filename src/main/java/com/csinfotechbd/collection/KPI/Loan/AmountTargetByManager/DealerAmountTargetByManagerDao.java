package com.csinfotechbd.collection.KPI.Loan.AmountTargetByManager;
/*
  Created by Md. Monirul Islam on 2/5/2020
*/

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class DealerAmountTargetByManagerDao {

    private EntityManager entityManager;

    public List<DealerAmountTargetByManager> getList(Date distDate)
    {
        List<DealerAmountTargetByManager> dealerAmountTargetByManagers=new ArrayList<>();
        try{
            Session session=entityManager.unwrap(Session.class);

            Criteria crt=session.createCriteria(DealerAmountTargetByManager.class);
            crt.add(Restrictions.eq("enabled",true));
            crt.add(Restrictions.lt("createdDate",distDate));

            dealerAmountTargetByManagers= crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return  dealerAmountTargetByManagers;
    }
}
