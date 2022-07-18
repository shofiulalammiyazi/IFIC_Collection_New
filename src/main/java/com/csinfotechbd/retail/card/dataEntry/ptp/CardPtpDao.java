package com.csinfotechbd.retail.card.dataEntry.ptp;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class CardPtpDao {
    @Autowired
    private EntityManager entityManager;

    public List<CardPtp> getCardPtpByCustomer(Date startDate) {
        List<CardPtp> ptpList=new ArrayList<>();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(CardPtp.class);
            crt.add(Restrictions.eq("card_ptp_date", startDate));
//            crt.add(Restrictions.eq("customerBasicInfo",customerBasicInfoEntity));
            crt.addOrder(Order.desc("card_ptp_date"));

            ptpList=crt.list();

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return ptpList;
    }


}
