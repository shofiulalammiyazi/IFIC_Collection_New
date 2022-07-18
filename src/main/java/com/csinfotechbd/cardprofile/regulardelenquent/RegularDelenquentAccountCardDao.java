package com.csinfotechbd.cardprofile.regulardelenquent;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class RegularDelenquentAccountCardDao {
    private EntityManager entityManager;

    public List<RgularDelenquentAccountCard> getRgularDelenquentAccountCardCard() {
        Session session = entityManager.unwrap(Session.class);
        List<RgularDelenquentAccountCard> delenquentAccounts = new ArrayList<>();
        try {
            String sql = "SELECT a.CB_CARDHOLDER_NO, c.CB_CARDHOLDER_NAME, b.CB_LAST_AGE_CD, b.CB_FIN_ACCTNO, a.CB_IDNO, d.CB_OUTSTD_BAL, b.CB_MIN_PMT,a.CB_CARD_PRDCT_GROUP, c.CB_ALIAS_NAME\n" +
                    "  FROM CP_CRDTBL a, CP_FINTBL b, CP_CSTTBL c, CP_INDACC d\n" +
                    " WHERE     a.CB_INDIVIDUAL_ACCTNO = b.CB_FIN_ACCTNO and a.CB_IDNO = c.CB_CUSTOMER_IDNO\n" +
                    "       AND a.CB_BASIC_SUPP_IND = 'B'\n" +
                    "       AND b.CB_LAST_AGE_CD > '00'\n" +
                    "       AND b.CB_LAST_AGE_CD <> 'CR';";

            SQLQuery query = session.createSQLQuery(sql);
            List<Object[]> objects = query.getResultList();
            for (Object[] obj : objects) {
                String cardNumber = obj[0].toString();
                String customerName = obj[1].toString();
                String ageCode = obj[2].toString();
                String finAccount = obj[3].toString();
                String customerId = obj[4].toString();
                String outstanding = obj[5].toString();
                String minDue = obj[6].toString();
                String productType = obj[7].toString();
                String securedFlag = obj[8].toString();
                delenquentAccounts.add(new RgularDelenquentAccountCard(cardNumber, customerName, ageCode, finAccount, customerId, outstanding, minDue, productType, securedFlag));
            }
            return delenquentAccounts;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return delenquentAccounts;
    }

    public void deletePreviousMonthUnallocatedData() {
        Session session = entityManager.unwrap(Session.class);
        String sql = "delete from UNALLOCATED_CARD_LIST";
        SQLQuery query = session.createSQLQuery(sql);
        query.executeUpdate();
    }
}
