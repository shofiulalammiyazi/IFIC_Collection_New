package com.csinfotechbd.reports.activity;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional
@Repository
public class activityDao1 {

    @Autowired
    private EntityManager entityManager;

    public List<ActivityReport> getData(String sql, Map<String, String> stringMap) {
        List<ActivityReport> reports = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);
            SQLQuery query = session.createSQLQuery(sql);
            for (Map.Entry<String, String> entry : stringMap.entrySet()) query.setParameter(entry.getKey(), entry.getValue());
            List<Object[]> data = query.list();

            for (Object[] obj : data) {
                String customerid = obj[0].toString();
                String acountname = obj[1].toString();
                String accountno = obj[2].toString();
                String ptp = obj[3].toString();
                String dairynote = obj[4].toString();
                String dailynote = obj[5].toString();
                String hotnote = obj[6].toString();
                String followup = obj[7].toString();
                String unit = "Loan";
                reports.add(new ActivityReport(customerid, acountname, accountno, ptp, dairynote, dailynote, hotnote, followup, unit));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return reports;
    }


    public List<ActivityReport> getcarddata(String cardsql, Map<String, String> stringMap) {
        List<ActivityReport> reports = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);

            SQLQuery query = session.createSQLQuery(cardsql);
            for (Map.Entry<String, String> entry : stringMap.entrySet()) query.setParameter(entry.getKey(), entry.getValue());
            List<Object[]> data = query.list();

            for (Object[] obj : data) {
                String customerid = obj[0].toString();
                String acountname = obj[1].toString();
                String accountno = obj[2].toString();
                String ptp = obj[3].toString();
                String dairynote = obj[4].toString();
                String dailynote = obj[5].toString();
                String hotnote = obj[6].toString();
                String followup = obj[7].toString();
                String unit = "Card";
                reports.add(new ActivityReport(customerid, acountname, accountno, ptp, dairynote, dailynote, hotnote, followup, unit));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return reports;
    }
}
