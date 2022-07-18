package com.csinfotechbd.reports.rfd;

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
public class RfdReportDao {

    @Autowired
    EntityManager entityManager;


public List<RfdReport>rfdReportloanList(String sql, Map<String, String> stringMapLoan){

    Session session=entityManager.unwrap(Session.class);
    List<RfdReport> rfdReportList=new ArrayList<>();
    SQLQuery query = null;
    try {
        query = session.createSQLQuery(sql);
        for(Map.Entry<String, String> entry: stringMapLoan.entrySet()) query.setParameter(entry.getKey(), entry.getValue());
        List<Object[]> objects = query.getResultList();
        for(Object[] obj:objects){
             String date=obj[0].toString();
             String accountno=obj[1].toString();
             String accountname=obj[2].toString();
             String menu=obj[3].toString();
             String submenuone=obj[4].toString();
             String submenutwo=obj[5].toString();
             String submenuthree=obj[6].toString();
             String shortnote=obj[7].toString();
             String unit="loan";
            rfdReportList.add(new RfdReport(date,accountno,accountname,menu,submenuone,submenutwo,submenuthree,shortnote,unit));
        }
        return rfdReportList;
    } catch (Exception e) {
        System.out.println(e.getMessage());
     }
     return rfdReportList;
   }
    public List<RfdReport>rfdReportcardList(String cardsql, Map<String, String> stringMapCard){

        Session session=entityManager.unwrap(Session.class);
        List<RfdReport> rfdReportList=new ArrayList<>();
        SQLQuery query = null;
        try {
            query = session.createSQLQuery(cardsql);
            for(Map.Entry<String, String> entry: stringMapCard.entrySet()) query.setParameter(entry.getKey(), entry.getValue());
            List<Object[]> objects = query.getResultList();
            for(Object[] obj:objects){
                String date=obj[0].toString();
                String accountno=obj[1].toString();
                String accountname=obj[2].toString();
                String menu=obj[3].toString();
                String submenuone=obj[4].toString();
                String submenutwo=obj[5].toString();
                String submenuthree=obj[6].toString();
                String shortnote=obj[7].toString();
                String unit="card";
                rfdReportList.add(new RfdReport(date,accountno,accountname,menu,submenuone,submenutwo,submenuthree,shortnote,unit));
            }
            return rfdReportList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rfdReportList;
    }
}
