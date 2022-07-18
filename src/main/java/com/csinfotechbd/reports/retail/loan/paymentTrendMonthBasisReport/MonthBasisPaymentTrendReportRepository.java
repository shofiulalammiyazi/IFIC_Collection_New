package com.csinfotechbd.reports.retail.loan.paymentTrendMonthBasisReport;

import com.csinfotechbd.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface MonthBasisPaymentTrendReportRepository extends JpaRepository<LoanAccountBasicInfo, Long> {

    @Query(value = "SELECT " +
            "       SUM(CASE WHEN NVL(SUM(LPD.PAYMENT), '0') != 0 AND (NVL(SUM(LPD.PAYMENT), '0') * 100) / NVL(LADI.OPENING_OVER_DUE, '1') >= 100 THEN 1 ELSE 0 END) AS greaterThanHundred, " +
            "       SUM(CASE WHEN NVL(SUM(LPD.PAYMENT), '0') != 0 AND (NVL(SUM(LPD.PAYMENT), '0') * 100) / NVL(LADI.OPENING_OVER_DUE, '1') BETWEEN 50 AND 99 THEN 1 ELSE 0 END) AS greaterThanFifty, " +
            "       SUM(CASE WHEN NVL(SUM(LPD.PAYMENT), '0') != 0 AND (NVL(SUM(LPD.PAYMENT), '0') * 100) / NVL(LADI.OPENING_OVER_DUE, '1') BETWEEN 1 AND 49 THEN 1 ELSE 0 END) AS lessThanFifty, " +
            "       SUM(CASE WHEN NVL(SUM(LPD.PAYMENT), '0') = 0 THEN 1 ELSE 0 END) AS noPayment, " +
            "       COUNT(LABI.ACCOUNT_NO) AS totalAccount, " +

            "       SUM(CASE WHEN NVL(SUM(LPD.PAYMENT), '0') != 0 AND (NVL(SUM(LPD.PAYMENT), '0') * 100) / NVL(LADI.OPENING_OVER_DUE, '1') >= 100 THEN 1 ELSE 0 END) * 100 / COUNT(LABI.ACCOUNT_NO) AS percentageGreaterThanHundred, " +
            "       SUM(CASE WHEN NVL(SUM(LPD.PAYMENT), '0') != 0 AND (NVL(SUM(LPD.PAYMENT), '0') * 100) / NVL(LADI.OPENING_OVER_DUE, '1') BETWEEN 50 AND 99 THEN 1 ELSE 0 END) * 100 / COUNT(LABI.ACCOUNT_NO) AS percentageGreaterThanFifty, " +
            "       SUM(CASE WHEN NVL(SUM(LPD.PAYMENT), '0') != 0 AND (NVL(SUM(LPD.PAYMENT), '0') * 100) / NVL(LADI.OPENING_OVER_DUE, '1') BETWEEN 1 AND 49 THEN 1 ELSE 0 END) * 100 / COUNT(LABI.ACCOUNT_NO) AS percentageLessThanFifty, " +
            "       SUM(CASE WHEN NVL(SUM(LPD.PAYMENT), '0') = 0 THEN 1 ELSE 0 END) * 100 / COUNT(LABI.ACCOUNT_NO) AS percentageNoPayment " +
            "FROM LOAN_ACCOUNT_BASIC_INFO LABI " +
            "LEFT JOIN LOAN_ACCOUNT_DISTRIBUTION_INFO LADI ON LADI.LOAN_ACCOUNT_BASIC_INFO_ID = LABI.ID AND LADI.LATEST = '1' " +
            "LEFT JOIN LOAN_PAYMENT LPD ON LPD.ACCOUNT_NO = LABI.ACCOUNT_NO " +
            "WHERE LADI.OPENING_OVER_DUE BETWEEN ?1 AND ?2 AND TO_CHAR(LADI.CREATED_DATE,'yyyy-mm') = ?3 " +
            "GROUP BY LABI.ACCOUNT_NO, LADI.OPENING_OVER_DUE", nativeQuery = true)
    public List<Tuple> findAllByMonth(double lowerLimit, double upperLimit, String month);
}
