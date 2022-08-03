package com.unisoft.collection.KPI.Card.TargetByManagerAmount;
/*
  Created by Md.   Islam on 1/23/2020
*/

import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public class DealerTargetCardManagerDao {

    @Autowired
    private EntityManager entityManager;

    public DealerTargetAmountCardManager getTargetByAgeSupVLoc(AgeCodeEntity codeEntity,String superVisorPin,String locationName)
    {
        DealerTargetAmountCardManager amountCardManager=null;
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(DealerTargetAmountCardManager.class);

            crt.createAlias("locationEntityList","locationEntity");
            crt.add(Restrictions.eq("locationEntity.city",locationName).ignoreCase());

            crt.createAlias("ageCodeEntityList","ageCodeEntity");
            crt.add(Restrictions.eq("ageCodeEntity.id",codeEntity.getId()));

            crt.createAlias("employeeInfoEntityList","employeeInfoEntity");
            crt.add(Restrictions.eq("employeeInfoEntity.pin",superVisorPin));

            crt.addOrder(Order.desc("createdDate"));
            crt.setMaxResults(1);

            amountCardManager=(DealerTargetAmountCardManager)crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            amountCardManager=null;
        }
        return amountCardManager;
    }
}
