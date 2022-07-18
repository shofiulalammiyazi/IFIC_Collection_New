package com.csinfotechbd.legal.report.datasheets.niActCaseMonitoringReport;

import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface NIActCaseMonitoringReportRepository extends JpaRepository<LitigationCaseInfo, Long> {

    @Query(value = "SELECT BRN.ROUTING_NO AS branchCode, BRN.BRANCH_NAME AS branchName, LCI.LD_NO AS ldNo, LCI.DIVISION AS division, LCI.CUSTOMER_CIF_NO AS cifNumber, LCI.CUSTOMER_ACC_NUM AS accountNo, LCI.NAME_OF_ACC AS accountName, " +
            "       CBIE.CUSTOMER_NAME AS defendantName, CBIE.MOBILE_NUMBER AS defendantMobile, LCI.BUSINESS_SEGMENT AS businessSegment, LCI.OUTSTANDING AS outstanding, CBIE.CL_STATUS AS clStatus, LCI.NI_ACT_CASE_NO AS niActCaseNumer, " +
            "       LCI.DATE_OF_FILING AS filingDate, LCI.RECOVERY_BEFORE_CASE_AMOUNT AS caseAmount, COA.NAME AS courseOfAction, LCI.NEXT_DATE AS nextDate, CRT.NAME AS courtName, " +
            "       LCI.PETITIONER_NAME AS petitionersName, CBIE.CUSTOMER_NAME AS accusedName, CBIE.MOBILE_NUMBER AS accusedCellNumber, " +
            "       LISTAGG(LY.NAME, ', ') WITHIN GROUP (ORDER BY LY.ID) AS lawyersName, " +
            "       LISTAGG(LY.MOBILE_NO, ', ') WITHIN GROUP (ORDER BY LY.ID) AS layersCellNumber, " +
            "       LCI.CASE_NUMBER AS artharinExecutionCaseNumber, LCI.WRIT_PETITION_NUM AS writNumber, LCI.OTHER_SUIT_CASE AS otherCase, LCI.WRITTEN_OFF AS writenOfStatus, LCI.RECOVERED_AMOUNT AS totalRecoveryAmount, " +
            "       LCI.LEGAL_EXPENSE AS legalExpense, LCI.MARKET_VALUE AS marketValue, LCI.FORCED_SALE_VALUE AS forcedSaleValue, " +
            "       LCI.ASSESED_BY AS assessedBy, CS.NAME AS status, LCI.REMARKS as remarks " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "            LEFT JOIN LOS_TB_S_BRANCH BRN ON BRN.BRANCH_NAME = LCI.BRANCH_NAME " +
            "            LEFT JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE ON CBIE.CUSTOMER_ID = LCI.CUSTOMER_CIF_NO " +
            "            LEFT JOIN LITIGATION_CASE_INFO_LAWYER LCILC ON LCILC.LITIGATION_CASE_INFO_ID = LCI.ID " +
            "            LEFT JOIN LAWYER LY ON LY.ID = LCILC.LAWYER_ID " +
            "            LEFT JOIN COURTS CRT ON CRT.ID = LCI.COURT_ID " +
            "            LEFT JOIN CASE_STATUS CS ON CS.ID = LCI.STATUS_ID " +
            "            LEFT JOIN COURSE_OF_ACTION COA ON COA.ID = LCI.COURSE_OF_ACTION_ID " +
            "WHERE LCI.CASE_TYPE_ID = (SELECT ID FROM CASE_TYPE WHERE LOWER(NAME) = LOWER('C.R Case under N.I Act')) " +
            "GROUP BY BRN.ROUTING_NO, BRN.BRANCH_NAME, LCI.LD_NO, LCI.DIVISION, LCI.CUSTOMER_CIF_NO, LCI.CUSTOMER_ACC_NUM, LCI.NAME_OF_ACC, " +
            "    CBIE.CUSTOMER_NAME, CBIE.MOBILE_NUMBER, LCI.BUSINESS_SEGMENT, LCI.OUTSTANDING, CBIE.CL_STATUS, LCI.NI_ACT_CASE_NO, " +
            "    LCI.DATE_OF_FILING, LCI.RECOVERY_BEFORE_CASE_AMOUNT, COA.NAME, LCI.NEXT_DATE, CRT.NAME, " +
            "    LCI.PETITIONER_NAME, CBIE.CUSTOMER_NAME, CBIE.MOBILE_NUMBER, " +
            "    LCI.CASE_NUMBER, LCI.WRIT_PETITION_NUM, LCI.OTHER_SUIT_CASE, LCI.WRITTEN_OFF, LCI.RECOVERED_AMOUNT, " +
            "    LCI.LEGAL_EXPENSE, LCI.MARKET_VALUE, LCI.FORCED_SALE_VALUE, LCI.ASSESED_BY, CS.NAME, LCI.REMARKS", nativeQuery = true)
    List<Tuple> findAllByBranch(String branch);
}
