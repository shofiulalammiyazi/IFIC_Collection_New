package com.unisoft.collection.KPI.Loan.AccountTargetByManager;
/*
  Created by Md.   Islam on 2/6/2020
*/

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class DealerAccountTargetByManagerDao {

    @Autowired
    private EntityManager entityManager;

    public List<DealerAccountTargetByManager> getActiveList(Date distDate)
    {
        List<DealerAccountTargetByManager> targetByManagerList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);

            Criteria crt=session.createCriteria(DealerAccountTargetByManager.class);
            crt.add(Restrictions.eq("enabled",true));
            crt.add(Restrictions.lt("createdDate",distDate));

            targetByManagerList= crt.list();

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return targetByManagerList;
    }
}
