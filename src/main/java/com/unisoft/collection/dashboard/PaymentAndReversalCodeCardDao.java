package com.unisoft.collection.dashboard;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Repository
public class PaymentAndReversalCodeCardDao {

    @Autowired
    private EntityManager entityManager;

    public List<PaymentAndReversalCodeCard> getRevList() {
        List<PaymentAndReversalCodeCard> reversalCodeCards = new ArrayList<>();

        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(PaymentAndReversalCodeCard.class);
            crt.add(Restrictions.eq("isReversal",true));
            reversalCodeCards = crt.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return reversalCodeCards;
    }

    public List<RevCodePayment> getRevPayment(String finAccNo, Date startDate,Date endDate)
    {
        List<RevCodePayment> paymentList=new ArrayList<>();

        try{
            SimpleDateFormat formatter= new SimpleDateFormat("dd-MMM-YYYY");
            String sDate=formatter.format(startDate);
            String eDate=formatter.format(endDate);

            Session session=entityManager.unwrap(Session.class);
            String sql="SELECT  CT_SYS_TRXN_CD,CT_BILL_CURR_AMT FROM CP_CRDTRX WHERE     LTRIM (RTRIM (CT_FIN_ACCTNO)) = '"+finAccNo+"' AND TO_DATE (LTRIM (RTRIM ((CT_TRXN_DATE))), 'rrrrmmdd') BETWEEN TO_DATE ('"+sDate+"') " +
                    "AND TO_DATE ('"+eDate+"')";

            SQLQuery query=session.createSQLQuery(sql);

            List<Object []> rows=query.list();

            for(Object [] row : rows)
            {
                RevCodePayment payment=new RevCodePayment();

                if(row[0] != null)
                    payment.setCode(row[0].toString());
                if(row[1] !=null)
                    payment.setAmount(Double.parseDouble(row[1].toString()));

                paymentList.add(payment);
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return paymentList;
    }
}
