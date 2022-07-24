package com.unisoft.collection.dashboard.performanceData;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

/*
  Created by Md.   Islam on 1/1/2020
*/
@Transactional
@Repository
public class PerformanceDao {

    @Autowired
    private EntityManager entityManager;

    private PerformanceData findByUserPin(String userPin,String unit)
    {
        PerformanceData performanceData=new PerformanceData();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(PerformanceData.class);
            crt.add(Restrictions.eq("userPin",userPin));
            crt.add(Restrictions.eq("unit",unit));

            performanceData=(PerformanceData)crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return performanceData;
    }

    private boolean updatePerformanceData(PerformanceData performanceData)
    {
        boolean update=false;
        Session session=entityManager.unwrap(Session.class);
        try{
            PerformanceData temp=findByUserPin(performanceData.getUserPin(),performanceData.getUnit());

            if(temp == null)
            {
                session.save(performanceData);
            }else {
                temp.setBackPerf(performanceData.getBackPerf());
                temp.setFlowPerf(performanceData.getFlowPerf());
                temp.setSavePerf(performanceData.getSavePerf());

                session.update(temp);
            }
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
