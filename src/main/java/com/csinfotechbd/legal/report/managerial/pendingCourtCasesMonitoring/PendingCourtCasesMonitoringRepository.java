package com.csinfotechbd.legal.report.managerial.pendingCourtCasesMonitoring;

import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Tuple;
import java.util.List;

public interface PendingCourtCasesMonitoringRepository extends JpaRepository<LitigationCaseInfo, Long>  {

    @Query(value = "" +
            "SELECT LCI.BRANCH_NAME                                                        AS BRANCH_NAME, " +
            "       COUNT(CASE WHEN CT.NAME = 'Artharin Suit' THEN 1 END)                  AS NOS_OF_ARS_NAME, " +
            "      SUM(CASE WHEN CT.NAME = 'Artharin Suit' THEN NVL(LCI.SUIT_VALUE,0) END)        AS ARTHARIN_RELATED_SUIT_VALUE,   " +
            "       COUNT(CASE WHEN CT.NAME = 'Artharin Execution' THEN 1 END)                  AS NOS_OF_EX_CASE_NAME, " +
            "      SUM(CASE WHEN CT.NAME = 'Artharin Execution' THEN NVL(LCI.SUIT_VALUE,0) END)   AS EX_CASE_RELATED_SUIT_VALUE, " +
            "      COUNT(CASE WHEN CT.NAME = 'Artharin Suit' THEN 1 END)   + COUNT(CASE WHEN CT.NAME = 'Artharin Execution' THEN 1 END)   AS TOTAL_NUMBER_OF_ARTHARIN_EXECUTION_SUIT, " +
            "      SUM(CASE WHEN CT.NAME = 'Artharin Suit' THEN NVL(LCI.SUIT_VALUE,0) END) + SUM(CASE WHEN CT.NAME = 'Artharin Execution' THEN NVL(LCI.SUIT_VALUE,0) END)       AS TOTAL_SUIT_VALUE, " +
            "      COUNT(CASE WHEN CT.NAME = 'NI Act' THEN 1 END) AS NOS_OF_NI_ACT, " +
            "      SUM(NVL(LCI.CHEQUE_AMOUNT,0))        AS CHEQUE_AMOUNT, " +
            "       COUNT(CASE WHEN cft.name = 'Against Bank' THEN 1 END)                  AS NOS_OF_MONEY_SUIT_AGAINST_BANK, " +
            "      COUNT(NVL(LCI.WRIT_PETITION_NUM,0))        AS WRIT_PETITION_NUM " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "      LEFT JOIN CASE_TYPE CT on CT.ID = LCI.CASE_TYPE_ID " +
            "      LEFT JOIN CASE_FILED_TYPE CFT ON CFT.ID = LCI.CASE_FILED_ID " +
            "WHERE LCI.BRANCH_CODE IS NOT NULL " +
            "GROUP BY LCI.BRANCH_NAME, LCI.BRANCH_CODE", nativeQuery = true)
    List<Tuple> getReport();


}
