package com.unisoft.reports.ptp;

import net.sf.jasperreports.engine.*;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Repository
public class PtpReportsDao {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ResourceLoader resourceLoader;

    public JasperPrint exportPdfFile() throws SQLException, JRException, IOException {
        Connection conn = jdbcTemplate.getDataSource().getConnection();

        BufferedImage image = ImageIO.read(getClass().getResource("/static/images/ific.png"));

        String path = resourceLoader.getResource("classpath:/templates/reports/ptp/ptp.jrxml").getURI().getPath();
        JasperReport jasperReport = JasperCompileManager.compileReport(path);
        jasperReport.setWhenNoDataType(jasperReport.getWhenNoDataTypeValue());

        // Parameters for report
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("logo", image);

        return JasperFillManager.fillReport(jasperReport, parameters, conn);
    }

//    public List<LoanPtpreport> getlonptp( String queries, Map<String, String> stringMapLoan){
//        Session session = entityManager.unwrap(Session.class);
//        List<LoanPtpreport> loanPtpList = new ArrayList<>();
//        try {
////            String sql = queries;
//            SQLQuery query = session.createSQLQuery(queries);
//            for(Map.Entry<String, String> entry: stringMapLoan.entrySet()) query.setParameter(entry.getKey(), entry.getValue());
//
//            List<Object[]> objects = query.getResultList();
//            for(Object[] obj: objects){
//                String accountno = obj[0].toString();
//                String accountname = obj[1].toString();
//                String createddate = obj[2].toString();
//                String loanptpdate = obj[3].toString();
//                String loanptpamount = obj[4].toString();
//                String loanptpstatus = obj[5].toString();
//                String unit = "Loan";
////                if(queryType.equals("Loan")) unit="Loan";else unit="Card";
//
////                System.err.println();
//                loanPtpList.add(new LoanPtpreport(accountno, createddate, loanptpdate, loanptpamount, loanptpstatus, accountname,unit));
//            }
//            return loanPtpList;
//        }catch (Exception e){
//           System.out.println(e.getMessage());
//        }
//        return loanPtpList;
//    }

    public List<LoanPtpreport> getlonptp(String queries, Map<String, String> stringMapLoan) {
        Session session = entityManager.unwrap(Session.class);
        List<LoanPtpreport> loanPtpList = new ArrayList<>();
        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(queries);

            for (int i = 1; i <= stringMapLoan.size(); i++) {
                statement.setString(i, stringMapLoan.get(i - 1));
            }

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                loanPtpList.add(new LoanPtpreport(resultSet.getString(0),
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(0),
                        resultSet.getString(0),
                        resultSet.getString(0),
                        resultSet.getString(0))
                );
            }
            resultSet.close();
            return loanPtpList;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return loanPtpList;
    }


    public List<LoanPtpreport> getcardptp(String queries, Map<String, String> stringMapCard) {
        Session session = entityManager.unwrap(Session.class);
        List<LoanPtpreport> loanPtpList = new ArrayList<>();
        try {
//            String sql = queries;
            SQLQuery query = session.createSQLQuery(queries);
            for (Map.Entry<String, String> entry : stringMapCard.entrySet()) query.setParameter(entry.getKey(), entry.getValue());
            List<Object[]> objects = query.getResultList();
            for (Object[] obj : objects) {
                String accountno = obj[0].toString();
                String accountname = obj[1].toString();
                String createddate = obj[2].toString();
                String loanptpdate = obj[3].toString();
                String loanptpamount = obj[4].toString();
                String loanptpstatus = obj[5].toString();
                String unit = "Card";
//                if(queryType.equals("Loan")) unit="Loan";else unit="Card";

//                System.err.println();
                loanPtpList.add(new LoanPtpreport(accountno, createddate, loanptpdate, loanptpamount, loanptpstatus, accountname, unit));
            }
            return loanPtpList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return loanPtpList;
    }

}
