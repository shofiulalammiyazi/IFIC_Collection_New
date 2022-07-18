package com.csinfotechbd.legal.report.datasheets.supremeCourtBangladeshCaseReport;

import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface SupremeCourtBangladeshCaseReportRepository extends JpaRepository<LitigationCaseInfo, Long> {

    @Query(value = "SELECT CT.NAME AS caseLabel, " +
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

            "FROM CASE_TYPE CT " +
            "LEFT JOIN LITIGATION_CASE_INFO CBD ON CBD.CASE_TYPE_ID = CT.ID AND UPPER(CBD.BUSINESS_SEGMENT) = 'CBD' AND CBD.CASE_FILED_SUB_TYPE_ID = CT.CASE_FILED_SUB_TYPE_ID " +
            "LEFT JOIN LITIGATION_CASE_INFO SME ON SME.CASE_TYPE_ID = CT.ID AND UPPER(SME.BUSINESS_SEGMENT) = 'SME' AND SME.CASE_FILED_SUB_TYPE_ID = CT.CASE_FILED_SUB_TYPE_ID " +
            "LEFT JOIN LITIGATION_CASE_INFO RBD ON RBD.CASE_TYPE_ID = CT.ID AND UPPER(RBD.BUSINESS_SEGMENT) = 'RBD' AND RBD.CASE_FILED_SUB_TYPE_ID = CT.CASE_FILED_SUB_TYPE_ID " +
            "LEFT JOIN LITIGATION_CASE_INFO CARD ON CARD.CASE_TYPE_ID = CT.ID AND UPPER(CARD.BUSINESS_SEGMENT) = 'CARD' AND CARD.CASE_FILED_SUB_TYPE_ID = CT.CASE_FILED_SUB_TYPE_ID " +

            "WHERE CT.CASE_FILED_SUB_TYPE_ID = (SELECT ID FROM CASE_FILED_SUB_TYPE WHERE NAME = ?1) " +
            "GROUP BY CT.NAME ORDER BY CT.NAME", nativeQuery = true)
    List<Tuple> findAllCaseTypeWise(String caseFiledSubType);

    @Query(value = "SELECT COA.NAME AS caseLabel, LCI.OTHER_COURSE_OF_ACTION AS courseOfActionOthers, " +
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

            "FROM COURSE_OF_ACTION COA " +
            "       LEFT JOIN COURSE_OF_ACTION_CASE_TYPES COACT ON COACT.COURSE_OF_ACTION_ID = COA.ID " +
            "       LEFT JOIN CASE_TYPE CT ON CT.ID = COACT.CASE_TYPES_ID " +
            "       LEFT JOIN LITIGATION_CASE_INFO LCI ON LCI.COURSE_OF_ACTION_ID = COA.ID " +
            "       LEFT JOIN LITIGATION_CASE_INFO CBD ON CBD.COURSE_OF_ACTION_ID = COA.ID AND NVL(CBD.OTHER_COURSE_OF_ACTION, '-') = NVL(LCI.OTHER_COURSE_OF_ACTION, '-') AND UPPER(CBD.BUSINESS_SEGMENT) = 'CBD' AND CBD.CASE_FILED_SUB_TYPE_ID = CT.CASE_FILED_SUB_TYPE_ID " +
            "       LEFT JOIN LITIGATION_CASE_INFO SME ON SME.COURSE_OF_ACTION_ID = COA.ID AND NVL(SME.OTHER_COURSE_OF_ACTION, '-') = NVL(LCI.OTHER_COURSE_OF_ACTION, '-') AND UPPER(SME.BUSINESS_SEGMENT) = 'SME' AND SME.CASE_FILED_SUB_TYPE_ID = CT.CASE_FILED_SUB_TYPE_ID " +
            "       LEFT JOIN LITIGATION_CASE_INFO RBD ON RBD.COURSE_OF_ACTION_ID = COA.ID AND NVL(RBD.OTHER_COURSE_OF_ACTION, '-') = NVL(LCI.OTHER_COURSE_OF_ACTION, '-') AND UPPER(RBD.BUSINESS_SEGMENT) = 'RBD' AND RBD.CASE_FILED_SUB_TYPE_ID = CT.CASE_FILED_SUB_TYPE_ID " +
            "       LEFT JOIN LITIGATION_CASE_INFO CARD ON CARD.COURSE_OF_ACTION_ID = COA.ID AND NVL(CARD.OTHER_COURSE_OF_ACTION, '-') = NVL(LCI.OTHER_COURSE_OF_ACTION, '-') AND UPPER(CARD.BUSINESS_SEGMENT) = 'CARD' AND CARD.CASE_FILED_SUB_TYPE_ID = CT.CASE_FILED_SUB_TYPE_ID " +

            "WHERE CT.CASE_FILED_SUB_TYPE_ID = (SELECT ID FROM CASE_FILED_SUB_TYPE WHERE NAME = ?1) " +
            "GROUP BY COA.NAME, LCI.OTHER_COURSE_OF_ACTION ORDER BY COA.NAME, LCI.OTHER_COURSE_OF_ACTION", nativeQuery = true)
    List<Tuple> findAllCourseOfActionWise(String caseFiledSubType);

    @Query(value = "SELECT CS.NAME AS caseLabel, " +
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

            "FROM CASE_STATUS CS " +
            "       LEFT JOIN LITIGATION_CASE_INFO CBD ON CBD.STATUS_ID = CS.ID AND UPPER(CBD.BUSINESS_SEGMENT) = 'CBD' AND CBD.CASE_FILED_SUB_TYPE_ID = (SELECT ID FROM CASE_FILED_SUB_TYPE WHERE NAME = ?1) " +
            "       LEFT JOIN LITIGATION_CASE_INFO SME ON SME.STATUS_ID = CS.ID AND UPPER(SME.BUSINESS_SEGMENT) = 'SME' AND SME.CASE_FILED_SUB_TYPE_ID = (SELECT ID FROM CASE_FILED_SUB_TYPE WHERE NAME = ?1) " +
            "       LEFT JOIN LITIGATION_CASE_INFO RBD ON RBD.STATUS_ID = CS.ID AND UPPER(RBD.BUSINESS_SEGMENT) = 'RBD' AND RBD.CASE_FILED_SUB_TYPE_ID = (SELECT ID FROM CASE_FILED_SUB_TYPE WHERE NAME = ?1) " +
            "       LEFT JOIN LITIGATION_CASE_INFO CARD ON CARD.STATUS_ID = CS.ID AND UPPER(CARD.BUSINESS_SEGMENT) = 'CARD' AND CARD.CASE_FILED_SUB_TYPE_ID = (SELECT ID FROM CASE_FILED_SUB_TYPE WHERE NAME = ?1) " +

            "GROUP BY CS.NAME ORDER BY CS.NAME", nativeQuery = true)
    List<Tuple> fillAllCaseStatusWise(String caseFiledSubType);
}
