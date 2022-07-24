package com.unisoft.collection.KPI.Card.TargetByManager;
/*
  Created by Md.   Islam on 1/29/2020
*/

import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class DealerTargetCardManagerAccDao {

    @Autowired
    private EntityManager entityManager;

    public DealerTargetCardManager getByProductAndAgeCodeByDealerPin(ProductTypeEntity productTypeEntity, AgeCodeEntity ageCodeEntity, String dealerPin, String location)
    {
        DealerTargetCardManager kpiTargetByAccountEntity=new DealerTargetCardManager();

        try{
            Session session=entityManager.unwrap(Session.class);

            Criteria crt=session.createCriteria(DealerTargetCardManager.class);
            crt.add(Restrictions.eq("enabled",true));

            crt.createAlias("locationEntityList","locationEntityList");
            crt.add(Restrictions.eq("locationEntityList.city",location).ignoreCase());

//            crt.createAlias("productType","productTypeList");
//            crt.add(Restrictions.eq("productTypeList.id",productTypeEntity.getId()));

            crt.createAlias("ageCodeEntityList","ageCodeEntityList");
            crt.add(Restrictions.eq("ageCodeEntityList.id",ageCodeEntity.getId()));

            crt.createAlias("employeeInfoEntityList","employeeInfoEntityList");
            crt.add(Restrictions.eq("employeeInfoEntityList.pin",dealerPin));

            crt.addOrder(Order.desc("id"));
            crt.setMaxResults(1);

            kpiTargetByAccountEntity=(DealerTargetCardManager)crt.uniqueResult();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return kpiTargetByAccountEntity;
    }

}
