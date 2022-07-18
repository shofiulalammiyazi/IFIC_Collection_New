package com.csinfotechbd.legal.report.datasheets.pendingCourtCasesMonitoring;

import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseMonitoringPendingCourtCasesReportRepository extends JpaRepository<LitigationCaseInfo, Long> {

    @Query(value = "SELECT LCI.BRANCH_NAME, " +
            "       COUNT(LCIARS.CASE_TYPE_ID), SUM(TO_NUMBER(NVL(LCIARS.SUIT_VALUE_AR, 0))), " +
            "       COUNT(LCIEX.CASE_TYPE_ID), SUM(TO_NUMBER(NVL(LCIEX.SUIT_VALUE_EX, 0))), " +
            "       COUNT(LCIARS.CASE_TYPE_ID) + COUNT(LCIEX.CASE_TYPE_ID), " +
            "       SUM(TO_NUMBER(NVL(LCIARS.SUIT_VALUE_AR, 0))) + SUM(TO_NUMBER(NVL(LCIEX.SUIT_VALUE_EX, 0))), " +
            "       COUNT(LCINIACT.CASE_TYPE_ID), SUM(TO_NUMBER(NVL(LCI.CHEQUE_AMOUNT, 0))), " +
            "       COUNT(LCIAB.CASE_FILED_ID), SUM(TO_NUMBER(NVL(LCIAB.SUIT_VALUE, 0))), " +
            "       COUNT(LCIWP.CASE_TYPE_ID), SUM(TO_NUMBER(NVL(LCIWP.AMOUNT_INVOLVED, 0)))" +
            "FROM LITIGATION_CASE_INFO LCI " +
            "LEFT JOIN LITIGATION_CASE_INFO LCIARS ON LCIARS.BRANCH_NAME = LCI.BRANCH_NAME AND LCIARS.CASE_TYPE_ID = (SELECT ID FROM CASE_TYPE WHERE LOWER(NAME) = LOWER('Artharin Suit')) " +
            "LEFT JOIN LITIGATION_CASE_INFO LCIEX ON LCIEX.BRANCH_NAME = LCI.BRANCH_NAME AND LCIEX.CASE_TYPE_ID = (SELECT ID FROM CASE_TYPE WHERE LOWER(NAME) = LOWER('Artharing Execution')) " +
            "LEFT JOIN LITIGATION_CASE_INFO LCINIACT ON LCINIACT.BRANCH_NAME = LCI.BRANCH_NAME AND LCINIACT.CASE_TYPE_ID = (SELECT ID FROM CASE_TYPE WHERE LOWER(NAME) = LOWER('C.R Case under N.I Act')) " +
            "LEFT JOIN LITIGATION_CASE_INFO LCIAB  ON LCIAB.BRANCH_NAME = LCI.BRANCH_NAME AND LCIAB.CASE_FILED_ID = (SELECT ID FROM CASE_FILED_TYPE WHERE LOWER(NAME) = LOWER('Against Bank')) " +
            "LEFT JOIN LITIGATION_CASE_INFO LCIWP ON LCIWP.BRANCH_NAME = LCI.BRANCH_NAME AND LCIWP.CASE_TYPE_ID = (SELECT ID FROM CASE_TYPE WHERE LOWER(NAME) = LOWER('Writ')) " +
            "WHERE LCI.STATUS_ID = (SELECT ID FROM CASE_STATUS where LOWER(NAME) = 'pending') " +
            "AND LCI.CASE_TYPE_ID IN (SELECT ID FROM CASE_TYPE WHERE LOWER(NAME) IN ('artharin suit', 'artharing execution', 'c.r case under n.i act', 'against bank', 'writ')) " +
            "GROUP BY LCI.BRANCH_NAME " +
            "ORDER BY LCI.BRANCH_NAME", nativeQuery = true)
    public Object[][] findAllByCaseType();
}
