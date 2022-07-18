package com.csinfotechbd.legal.report.datasheets.casePortfolioAndVintageAnalysisReport;

import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface CasePortfolioVintageAnalysisReportRepository extends JpaRepository<LitigationCaseInfo, Long> {

    @Query(value = "SELECT TO_CHAR(LCI.DATE_OF_FILING,'yyyy') AS fillingYear, " +
            "       COUNT(DISTINCT (CBD.CUSTOMER_ACC_NUM))+COUNT(DISTINCT (SME.CUSTOMER_ACC_NUM))+COUNT(DISTINCT (RBD.CUSTOMER_ACC_NUM))+COUNT(DISTINCT (CARD.CUSTOMER_ACC_NUM)) AS totalAccNo, " +
            "       '-' AS totalWOffAmt, " +
            "       SUM(NVL(CBD.RECOVERED_AMOUNT, '0'))+SUM(NVL(SME.RECOVERED_AMOUNT, '0'))+SUM(NVL(RBD.RECOVERED_AMOUNT, '0'))+SUM(NVL(CARD.RECOVERED_AMOUNT, '0')) AS totalRecovery, " +
            "       '-' AS totalFillingAmt, '-' AS totalSettledAmt, " +
            "       SUM(NVL(CBD.OUTSTANDING, '0'))+SUM(NVL(SME.OUTSTANDING, '0'))+SUM(NVL(RBD.OUTSTANDING, '0'))+SUM(NVL(CARD.OUTSTANDING, '0')) AS totalOutStanding, " +

            "       COUNT(DISTINCT (CBD.CUSTOMER_ACC_NUM)) AS cbdAccNo, '-' AS cbdWOffAmt, SUM(NVL(CBD.RECOVERED_AMOUNT, '0')) AS cbdTotalRecovery, " +
            "       '-' AS cbdFillingAmt, '-' AS cbdSettledAmt, SUM(NVL(CBD.OUTSTANDING, '0')) AS cbdOutStanding, " +
            "       COUNT(DISTINCT (SME.CUSTOMER_ACC_NUM)) AS smeAccNo, '-' AS smeWOffAmt, SUM(NVL(SME.RECOVERED_AMOUNT, '0')) AS smeTotalRecovery, " +
            "       '-' AS smeFillingAmt, '-' AS smeSettledAmt, SUM(NVL(SME.OUTSTANDING, '0')) AS smeOutStanding, " +
            "       COUNT(DISTINCT (RBD.CUSTOMER_ACC_NUM)) AS rbdAccNo, '-' AS rbdWOffAmt, SUM(NVL(RBD.RECOVERED_AMOUNT, '0')) AS rbdTotalRecovery, " +
            "       '-' AS rbdFillingAmt, '-' AS rbdSettledAmt, SUM(NVL(RBD.OUTSTANDING, '0')) AS rbdOutStanding, " +
            "       COUNT(DISTINCT (CARD.CUSTOMER_ACC_NUM)) AS cardAccNo, '-' AS cardWOffAmt, SUM(NVL(CARD.RECOVERED_AMOUNT, '0')) AS cardTotalRecovery, " +
            "       '-' AS cardFillingAmt, '-' AS cardSettledAmt, SUM(NVL(CARD.OUTSTANDING, '0')) AS cardOutStanding " +

            "FROM LITIGATION_CASE_INFO LCI " +
            "LEFT JOIN LITIGATION_CASE_INFO CBD ON TO_CHAR(CBD.DATE_OF_FILING,'yyyy') = TO_CHAR(LCI.DATE_OF_FILING,'yyyy') AND UPPER(CBD.BUSINESS_SEGMENT) = 'CBD' AND CBD.CASE_TYPE_ID IN (SELECT ID FROM CASE_TYPE WHERE NAME = ?1) " +
            "LEFT JOIN LITIGATION_CASE_INFO SME ON TO_CHAR(SME.DATE_OF_FILING,'yyyy') = TO_CHAR(LCI.DATE_OF_FILING,'yyyy') AND UPPER(SME.BUSINESS_SEGMENT) = 'SME' AND SME.CASE_TYPE_ID IN (SELECT ID FROM CASE_TYPE WHERE NAME = ?1) " +
            "LEFT JOIN LITIGATION_CASE_INFO RBD ON TO_CHAR(RBD.DATE_OF_FILING,'yyyy') = TO_CHAR(LCI.DATE_OF_FILING,'yyyy') AND UPPER(RBD.BUSINESS_SEGMENT) = 'RBD' AND RBD.CASE_TYPE_ID IN (SELECT ID FROM CASE_TYPE WHERE NAME = ?1) " +
            "LEFT JOIN LITIGATION_CASE_INFO CARD ON TO_CHAR(CARD.DATE_OF_FILING,'yyyy') = TO_CHAR(LCI.DATE_OF_FILING,'yyyy') AND UPPER(CARD.BUSINESS_SEGMENT) = 'CARD' AND CARD.CASE_TYPE_ID IN (SELECT ID FROM CASE_TYPE WHERE NAME = ?1) " +
            "GROUP BY TO_CHAR(LCI.DATE_OF_FILING,'yyyy') " +
            "ORDER BY TO_CHAR(LCI.DATE_OF_FILING,'yyyy')", nativeQuery = true)
    List<Tuple> findAllByCaseType(String caseTypeName);
}
