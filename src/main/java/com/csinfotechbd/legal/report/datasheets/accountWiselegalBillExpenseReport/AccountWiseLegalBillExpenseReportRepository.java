package com.csinfotechbd.legal.report.datasheets.accountWiselegalBillExpenseReport;

import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface AccountWiseLegalBillExpenseReportRepository extends JpaRepository<LitigationCaseInfo, Long> {

    @Query(value = "SELECT BRNC.ROUTING_NO AS branchCode, BRNC.BRANCH_NAME AS branchName, LCI.CUSTOMER_CIF_NO AS cifNo, " +
            "       LCI.CUSTOMER_ACC_NUM AS accountNo, LCI.NAME_OF_ACC AS accountName, LCI.BUSINESS_SEGMENT AS segment, SUM(NVL(LCI.OUTSTANDING, '0.00')) AS outstanding, " +
            "       SUM(NVL(LCI.RECOVERY_BEFORE_CASE_AMOUNT, '0')) AS thisMonthRecoveryBeforeFiling, SUM(NVL(LCI.RECOVERY_AFTER_CASE_AMOUNT, '0')) AS thisMonthRecoveryAfterFiling, '-' AS cumulativeRecovery, " +
            "       LCI.DISTRICT AS district, C.NAME AS courtName, " +
            "       LISTAGG(L.LAWYER_ID, ', ') WITHIN GROUP (ORDER BY L.ID) AS lawyerId, " +
            "       LISTAGG(L.NAME, ', ') WITHIN GROUP (ORDER BY L.ID) AS lawyerName, " +
            "       LISTAGG(L.MOBILE_NO, ', ') WITHIN GROUP (ORDER BY L.ID) AS lawyerMobileNo, " +
            "       '-' AS plainfiffId, LCI.PLAINTIFF_NAME AS plaintiffName, LCI.PLAINTIFF_PHONE_NO AS plaintiffMobileNo, " +
            "       CT.NAME AS lawyersBillForTypeOfCase, CS.NAME AS caseStatus, " +
            "       (SELECT SUM(NVL(LCIPREV.LEGAL_EXPENSE, '0')) FROM LITIGATION_CASE_INFO LCIPREV " +
            "        WHERE LCIPREV.CUSTOMER_ACC_NUM = LCI.CUSTOMER_ACC_NUM AND TO_CHAR(LCIPREV.DATE_OF_FILING,'yyyy-mm') = ?2) AS previousMonthLawyersBillAmount, " +
            "       SUM(NVL(LCI.LEGAL_EXPENSE, '0')) AS currentMonthLawyersBillAmount, '00' AS totalLawyersBillAmount, " +
            "       '-' AS currentMonthOthersBillAmount, '-' AS totalOthersBillAmount, '-' AS cumulativeLegalBillAmount, " +
            "       SUM(NVL(LCI.RECOVERY_AFTER_CASE_AMOUNT, '0')) AS currentMonthLegalBillRecovery, '-' AS cumulativeLegalBillRecovery, CBIE.CL_STATUS AS clStatus, " +
            "       SUM(NVL(LCI.RECOVERY_BEFORE_CASE_AMOUNT, '0.00')) AS recoveryBeforeCaseFiling, " +
            "       SUM(NVL(LCI.RECOVERY_AFTER_CASE_AMOUNT, '0.00')) AS recoveryAfterCaseFiling " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "    LEFT JOIN LOS_TB_S_BRANCH BRNC ON BRNC.BRANCH_NAME = LCI.BRANCH_NAME " +
            "    LEFT JOIN COURTS C ON C.ID = LCI.COURT_ID " +
            "    LEFT JOIN LITIGATION_CASE_INFO_LAWYER LCILW ON LCILW.LITIGATION_CASE_INFO_ID = LCI.ID " +
            "    LEFT JOIN LAWYER L ON L.ID = LCILW.LAWYER_ID " +
            "    LEFT JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID " +
            "    LEFT JOIN CASE_STATUS CS ON CS.ID = LCI.STATUS_ID " +
            "    LEFT JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE ON CBIE.CUSTOMER_ID = LCI.CUSTOMER_CIF_NO " +
            "WHERE TO_CHAR(LCI.DATE_OF_FILING,'yyyy-mm') = ?1 " +
            "GROUP BY BRNC.ROUTING_NO, BRNC.BRANCH_NAME, LCI.CUSTOMER_CIF_NO, " +
            "    LCI.CUSTOMER_ACC_NUM, LCI.NAME_OF_ACC, LCI.BUSINESS_SEGMENT, '00', " +
            "    LCI.DISTRICT, C.NAME, " +
            "    '-', LCI.PLAINTIFF_NAME, LCI.PLAINTIFF_PHONE_NO, " +
            "    CT.NAME, CS.NAME, '00','-', '-', '-', " +
            "    '-', CBIE.CL_STATUS", nativeQuery = true)
    List<Tuple> findAllByMonth(String currentMonth, String previousMonth);
}
