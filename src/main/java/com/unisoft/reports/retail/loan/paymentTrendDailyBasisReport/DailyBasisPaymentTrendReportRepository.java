package com.unisoft.reports.retail.loan.paymentTrendDailyBasisReport;

import com.unisoft.collection.settings.dpdBucket.DPDBucketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface DailyBasisPaymentTrendReportRepository extends JpaRepository<DPDBucketEntity, Long> {

//    @Query(value = "", nativeQuery = true)
//    List<DailyBasisPaymentTrendReportDto> getReportData(String month);

//    @Query(value = "" +
//            "SELECT DE.BUCKET_NAME, " +
//            "       COALESCE(SUM(LADI.OUT_STANDING), 0)                             AS OUTSTANDING, " +
//            "       COALESCE(SUM(LADI.OPENING_OVER_DUE), 0)                         AS OVERDUE, " +
//            "       COUNT(DISTINCT LADI.LOAN_ACCOUNT_BASIC_INFO_ID)                 AS ACCOUNT_COUNT, " +
//            "       COALESCE(SUM(LPD.PAYMENT), 0)                                   AS PAY_AMOUNT, " +
//            "       COUNT(DISTINCT LPD.ACCOUNT_NO)                                  AS PAY_ACCOUNT_COUNT, " +
//            "       COUNT(CASE WHEN LADI.OPENING_OVER_DUE <= LPD.PAYMENT THEN 1 END) AS ZERO_OVERDUE_COUNT " +
//            "FROM DPDBUCKET_ENTITY DE " +
//            "       LEFT JOIN LOAN_ACCOUNT_DISTRIBUTION_INFO LADI " +
//            "         ON LADI.DPD_BUCKET = DE.BUCKET_NAME AND LADI.LATEST = '1' AND LADI.STATUS_DATE BETWEEN :startDate AND :endDate " +
//            "       LEFT JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON LABI.ID = LADI.LOAN_ACCOUNT_BASIC_INFO_ID " +
//            "       LEFT JOIN LOAN_PAYMENT LPD " +
//            "         ON LABI.ACCOUNT_NO = LPD.ACCOUNT_NO AND LPD.PAYMENT_DATE BETWEEN :startDate AND :endDate " +
//            "GROUP BY DE.BUCKET_NAME " +
//            "ORDER BY DE.BUCKET_NAME", nativeQuery = true)
//    Object[][] getReportData(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


    @Query(value = "SELECT LL.BUCKET                                           AS bucket, " +
            "       SUM(LL.OUTSTANDING)                                 AS os, " +
            "       SUM(LL.OVERDUE_AMT)                                 AS overDue, " +
            "       COUNT(LL.ACC_NO)                                    AS noOfacc, " +
            "       SUM(CASE WHEN LP.PAYMENT <> null THEN LP.PAYMENT ELSE 0 END)   AS payment, " +
            "       COUNT(LP.ACCOUNT_NO)                                AS countPayAcc, " +
            "       COUNT(CASE " +
            "               WHEN LL.OVERDUE_AMT = 0 THEN LL.ACC_NO END) AS zeroOD " +
            "FROM LOAN_LISTING LL " +
            "       LEFT JOIN LOAN_PAYMENT LP ON LP.ACCOUNT_NO = LL.ACC_NO AND TO_CHAR(LP.PAYMENT_DATE, 'YYYY-MM-DD') = :pDate " +
            "WHERE TO_CHAR(LL.LISTING_DOWN_DATE, 'YYYY-MM-DD') = :pDate " +
            "GROUP BY LL.BUCKET " +
            "ORDER BY LL.BUCKET ", nativeQuery = true)
    List<Tuple> getDailyPaymentTreandByDate(@Param("pDate") String pDate);


}
