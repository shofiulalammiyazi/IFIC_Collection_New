package com.unisoft.reports.retail.loan.vintageAnalysisReport;

import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface VintageAnalysisReportRepository extends JpaRepository<LoanAccountBasicInfo, Long> {


    @Query(value = "SELECT LL2.disburseDate         AS disburseDate, " +
            "       SUM(LL2.segWisetotalAcc) AS segWisetotalAcc, " +
            "       SUM(LL2.segWisetotalAcc) AS pivSegWisetotalAcc, " +
            "       SUM(LL2.segWiseOs)       AS segWiseOs, " +
            "       SUM(LL2.segWiseOs)       AS pivSegWiseOs, " +
            "       SUM(LL2.parAccTotal)     AS parAccTotal, " +
            "       SUM(LL2.parOsTotal)      AS parOsTotal, " +
            "       SUM(LL2.clAccTotal)      AS clAccTotal, " +
            "       SUM(LL2.clOsTotal)       AS clOsTotal, " +
            "       SUM(LL2.bl_accNo)        AS blAcc, " +
            "       SUM(LL2.bl_os)           AS blOs, " +
            "       SUM(LL2.df_accNo)        AS dfAcc, " +
            "       SUM(LL2.df_os)           AS dfOs, " +
            "       SUM(LL2.ss_accNo)        AS ssAcc, " +
            "       SUM(LL2.ss_os)           AS ssOs, " +
            "       SUM(LL2.sma_accNo)       AS smaAcc, " +
            "       SUM(LL2.sma_os)          AS smsOs, " +
            "       SUM(LL2.uc_accNo)        AS ucAcc, " +
            "       SUM(LL2.uc_os)           AS ucOs " +
            "FROM ((SELECT TO_CHAR(TO_DATE(LL.FIRST_DISBURSEMENT_DATE,'DD-MON-YY', 'NLS_DATE_LANGUAGE = English'),'YYYY') AS disburseDate, " +
            "              COUNT(LL.ACC_NO)                            AS segWisetotalAcc, " +
            "              COUNT(LL.ACC_NO)                            AS pivSegWisetotalAcc, " +
            "              SUM(LL.OUTSTANDING)                         AS segWiseOs, " +
            "              SUM(LL.OUTSTANDING)                         AS pivSegWiseOs, " +
            "              COUNT(CASE " +
            "                      WHEN (LL.LOAN_ACC_PAR = 'Yes' AND " +
            "                            (LL.CL_STATUS = 'SMA' OR LL.CL_STATUS = 'SS' OR LL.CL_STATUS = 'DF' OR LL.CL_STATUS = 'BL')) " +
            "                              THEN 1 END)                 AS parAccTotal, " +
            "              SUM(CASE " +
            "                    WHEN (LL.LOAN_ACC_PAR = 'Yes' AND " +
            "                          (LL.CL_STATUS = 'SMA' OR LL.CL_STATUS = 'SS' OR LL.CL_STATUS = 'DF' OR LL.CL_STATUS = 'BL')) " +
            "                            THEN LL.OUTSTANDING END)      AS parOsTotal, " +
            "              COUNT(CASE " +
            "                      WHEN (LL.CL_STATUS = 'SS' OR LL.CL_STATUS = 'DF' OR LL.CL_STATUS = 'BL') " +
            "                              THEN LL.ACC_NO END)         AS clAccTotal, " +
            "              SUM(CASE " +
            "                    WHEN (LL.CL_STATUS = 'SS' OR LL.CL_STATUS = 'DF' OR LL.CL_STATUS = 'BL') " +
            "                            THEN LL.OUTSTANDING END)      AS clOsTotal, " +
            "              LL.CL_STATUS                                AS clStatus " +
            "       FROM LOAN_LISTING LL " +
            "       WHERE LL.PRODUCT_NAME = :productName " +
            "       GROUP BY TO_CHAR(TO_DATE(LL.FIRST_DISBURSEMENT_DATE,'DD-MON-YY', 'NLS_DATE_LANGUAGE = English'),'YYYY'), LL.CL_STATUS) " +
            "    PIVOT ( " +
            "      SUM(pivSegWisetotalAcc) AS accNo, " +
            "  SUM(pivSegWiseOs) AS os " +
            "    FOR clStatus IN ('BL' AS bl, 'DF' AS df, 'SS' AS ss, 'SMA' AS sma, 'UC' AS uc) " +
            "    ) " +
            "         )LL2 " +
            "GROUP BY LL2.disburseDate ", nativeQuery = true)
    List<Tuple> getReportData(@Param("productName") String productName);

}
