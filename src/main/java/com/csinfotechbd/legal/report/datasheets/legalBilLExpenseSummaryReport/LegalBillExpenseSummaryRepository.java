package com.csinfotechbd.legal.report.datasheets.legalBilLExpenseSummaryReport;

import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;


@Repository
public interface LegalBillExpenseSummaryRepository extends JpaRepository<LitigationCaseInfo, Long> {

    @Query(value = "SELECT LCI.BRANCH_CODE as branchCode,LCI.BRANCH_NAME as branchName, L.ID as lawarId, L.NAME as lawarName," +
            "        LCI.DISTRICT as district, CRT.NAME as courtName," +
            "        LISTAGG(CT.NAME, ', ')WITHIN GROUP (ORDER BY CT.ID) as lawyerBillTypeCase," +
            "        (SELECT SUM(NVL(LCIPREV.LEGAL_EXPENSE, '0')) FROM LITIGATION_CASE_INFO LCIPREV WHERE LCIPREV.CUSTOMER_ACC_NUM = LCI.CUSTOMER_ACC_NUM AND TO_CHAR(LCIPREV.DATE_OF_FILING, 'yyyy-mm') = ?4) AS previousMonthLawyersBillAmount," +
            "        SUM(NVL(LCI.LEGAL_EXPENSE, '0')) AS currentMonthLawyersBillAmount,0 AS totalLawyersBillAmount," +
            "        0 as currentMonthOthersBillAmount, 0 as totalOthersBillAmount, 0 AS cumulativeLegalBillAmount," +
            "        SUM(NVL(LCI.RECOVERY_AFTER_CASE_AMOUNT, '0')) as currentMonthLegalBillRecovery," +
            "        SUM(NVL(LCI.RECOVERY_BEFORE_CASE_AMOUNT, '0')) AS recoveryBeforeCaseFiling," +
            "        SUM(NVL(LCI.RECOVERY_AFTER_CASE_AMOUNT, '0')) AS recoveryAfterCaseFiling" +
            " FROM LITIGATION_CASE_INFO LCI" +
            "           LEFT JOIN LOS_TB_S_BRANCH BAR ON BAR.BRANCH_NAME = LCI.BRANCH_NAME" +
            "           LEFT JOIN LITIGATION_CASE_INFO_LAWYER LCILW ON LCILW.LITIGATION_CASE_INFO_ID = LCI.ID" +
            "           LEFT JOIN LAWYER L ON L.ID = LCILW.LAWYER_ID" +
            "           LEFT JOIN COURTS CRT ON CRT.ID = LCI.COURT_ID" +
            "           LEFT JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID" +
            " WHERE TO_CHAR(LCI.DATE_OF_FILING, 'yyyy-mm') = ?1 OR LCI.BRANCH_NAME = ?2 OR L.NAME = ?3" +
            " GROUP BY LCI.BRANCH_CODE, LCI.BRANCH_NAME, L.ID,L.NAME, LCI.DISTRICT, CRT.NAME,LCI.CUSTOMER_ACC_NUM," +
            "          CT.ID", nativeQuery = true)
    List<Tuple> branchAndLawerWiseLegalBillExpense(String date, String branchName, String lawyerName, String previousMonth);


    @Query(value = "SELECT LCI.BUSINESS_SEGMENT as segment, TO_CHAR(LCI.DATE_OF_FILING,'yyyy') as dateOfFiling, 0 as totalWOffAmt , 0 as totalWOffRecovery, " +
            "       SUM(NVL(LCI.RECOVERY_BEFORE_CASE_AMOUNT,'0')) as totalRecoveryBeoreFilling, " +
            "       SUM(NVL(LCI.RECOVERY_AFTER_CASE_AMOUNT, '0')) as TotalRecoveryAfterFilling, " +
            "       SUM(NVL(LCI.OUTSTANDING,'0')) as outstanding, " +
            "       LISTAGG(CT.NAME,', ')WITHIN GROUP (ORDER BY CT.ID) as lawyerBillTypeCase, " +
            "       (SELECT NVL(SUM(NVL(LCIPREV.LEGAL_EXPENSE,'0')), '0') FROM LITIGATION_CASE_INFO LCIPREV WHERE LCIPREV.CUSTOMER_ACC_NUM = LCI.CUSTOMER_ACC_NUM AND TO_CHAR(LCIPREV.DATE_OF_FILING,'yyyy-MM')=?2) AS previousMonthLawyersBillAmount, " +
            "       SUM(NVL(LCI.LEGAL_EXPENSE, '0')) AS currentMonthLawyersBillAmount, 0 AS totalLawyersBillAmount, " +
            "       0 as currentMonthOthersBillAmount , 0 as totalOthersBillAmount,0 AS cumulativeLegalBillAmount, " +
            "       SUM(NVL(LCI.RECOVERY_AFTER_CASE_AMOUNT, '0')) as currentMonthLegalBillRecovery, " +
            "       SUM(NVL(LCI.RECOVERY_BEFORE_CASE_AMOUNT, '0')) AS recoveryBeforeCaseFiling, " +
            "       SUM(NVL(LCI.RECOVERY_AFTER_CASE_AMOUNT, '0')) AS recoveryAfterCaseFiling " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       LEFT JOIN LOS_TB_S_BRANCH BAR ON BAR.BRANCH_NAME = LCI.BRANCH_NAME " +
            "       LEFT JOIN COURTS CRT ON CRT.ID = LCI.COURT_ID " +
            "       LEFT JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID " +
            "WHERE LCI.BUSINESS_SEGMENT = ?1 " +
            "GROUP BY TO_CHAR(LCI.DATE_OF_FILING,'yyyy-MM'),LCI.CUSTOMER_ACC_NUM,LCI.BUSINESS_SEGMENT,TO_CHAR(LCI.DATE_OF_FILING,'yyyy') ", nativeQuery = true)
    List<Tuple> segmentWishLegalBillExpense(String segment, String month);

    @Query(value = "SELECT DISTINCT BRANCH_NAME FROM LITIGATION_CASE_INFO", nativeQuery = true)
    List<String> getBranchName();
    @Query(value = "SELECT DISTINCT NAME FROM LAWYER", nativeQuery = true)
    List<String> getLawyerName();
}
