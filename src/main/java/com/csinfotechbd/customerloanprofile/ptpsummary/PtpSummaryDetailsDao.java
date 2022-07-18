package com.csinfotechbd.customerloanprofile.ptpsummary;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class PtpSummaryDetailsDao {

    @Autowired
    private EntityManager entityManager;

    public List<Double> ptp(String date, String date2, String cardholderNo){
        Session session = entityManager.unwrap(Session.class);
        List<Double> amountList = new ArrayList<>();
        try {
            String sql = "select AO_BILLING_AMT from card_aponus where AO_TRANS_TYPE='Payment' and AO_MATCH_DATE >='"+date+"' and AO_MATCH_DATE <='"+date2+"' and AO_CARDHDR_NO='"+cardholderNo+"'";
            SQLQuery query = session.createSQLQuery(sql);
            List<Object> objects = query.getResultList();
            for(Object obj : objects){
                amountList.add(Double.parseDouble(obj.toString()));
            }
            return amountList;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return amountList;
    }

    public double getApprovedPayment(String date, String accountNo){
        Session session = entityManager.unwrap(Session.class);
        double result = 0;
        try {
            String sql = "select SUM(AO_BILLING_AMT) from card_aponus where AO_TRANS_TYPE='Payment' and AO_MATCH_DATE='"+date+"' " +
                    "and AO_FIN_ACCTNO = (select DISTINCT(c.AO_FIN_ACCTNO) from CARD_APONUS c where c.AO_CARDHDR_NO='"+accountNo+"')";
            SQLQuery query = session.createSQLQuery(sql);
            Object o = query.uniqueResult();
            if(o != null)
                result = Double.parseDouble(o.toString());
            return result;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    public double getApprovedTransaction(String date, String accountNo){
        Session session = entityManager.unwrap(Session.class);
        double result = 0;
        try {
            String sql = "select SUM(AO_BILLING_AMT) from card_aponus where (AO_TRANS_TYPE='Sale' or AO_TRANS_TYPE='E-com') and AO_MATCH_DATE='"+date+"' " +
                    "and AO_FIN_ACCTNO = (select DISTINCT(c.AO_FIN_ACCTNO) from CARD_APONUS c where c.AO_CARDHDR_NO='"+accountNo+"')";
            SQLQuery query = session.createSQLQuery(sql);
            Object o = query.uniqueResult();
            if(o != null)
                result = Double.parseDouble(o.toString());
            return result;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    public double getApprovedCash(String date, String accountNo){
        Session session = entityManager.unwrap(Session.class);
        double result = 0;
        try {
            String sql = "select SUM(AO_BILLING_AMT) from card_aponus where AO_TRANS_TYPE='Cash' and AO_MATCH_DATE='"+date+"' "
                    + "and AO_CARDHDR_NO='"+accountNo+"' and AO_MCC in ('6010', '6011')";
            SQLQuery query = session.createSQLQuery(sql);
            Object o = query.uniqueResult();
            if(o != null)
                result = Double.parseDouble(o.toString());
            return result;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    public double getApprovedIPP(String date, String accountNo){
        Session session = entityManager.unwrap(Session.class);
        double result = 0;
        try {
            String sql = "select SUM(AO_BILLING_AMT) from card_aponus where AO_TRANS_TYPE='8' and AO_MATCH_DATE='"+date+"' "
                    + "and AO_CARDHDR_NO='"+accountNo+"'";
            SQLQuery query = session.createSQLQuery(sql);
            Object o = query.uniqueResult();
            if(o != null)
                result = Double.parseDouble(o.toString());
            return result;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    public String getBillingCycle(String accountNo){
        Session session = entityManager.unwrap(Session.class);
        String result = "";
        try {
            String sql = "select CB_BILL_CYCLE from CP_CRDTBL where CB_CARDHOLDER_NO='"+accountNo+"'";
            SQLQuery query = session.createSQLQuery(sql);
            Object o = query.uniqueResult();
            if(o != null)
                result = o.toString();
            return result;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    public String getStatementDate(String accountNo){
        Session session = entityManager.unwrap(Session.class);
        String result = "";
        try {
            String sql = "select CB_MTH_STMT_DATE from CP_FINHST where CB_FIN_ACCTNO =(select DISTINCT(c.AO_FIN_ACCTNO) from CARD_APONUS c where c.AO_CARDHDR_NO='"+accountNo+"')  order by CB_MTH_STMT_DATE DESC fetch first 1 row only";
            System.out.println(sql);
            SQLQuery query = session.createSQLQuery(sql);
            Object o = query.uniqueResult();
            if(o != null)
                result = o.toString();
            return result;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }

}
