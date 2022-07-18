package com.csinfotechbd.legal.report.datasheets.highCourtSuitCaseMonitoringReport;


import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface HighCourtSuitCaseMonitoringRepository extends JpaRepository<LitigationCaseInfo, Long> {

    @Query(value = "SELECT BAR.ROUTING_NO AS branchCode,BAR.BRANCH_NAME AS branchName, LCI.LD_NO AS ldNumber, " +
            "       LCI.DIVISION AS division,LCI.CUSTOMER_CIF_NO as cifNumber,LCI.CUSTOMER_CIF_NO as accountNumber, " +
            "       CBIE.CUSTOMER_NAME as defendantName, CBIE.MOBILE_NUMBER as defendantNumber,LCI.BUSINESS_SEGMENT as segment, " +
            "       LCI.OUTSTANDING as outstanding,CBIE.CL_STATUS as clStatus,LCI.PETITIONER_NAME as petitionerName, " +
            "       LCI.OPPOSITE_PARTY_NAME as oppsitePartyName, LCI.CASE_NUMBER as caseNumber, LCI.DATE_OF_FILING as dateOfFiling, " +
            "       LCI.SUBJECT_MATTER_OF_CASE as subjectMatter,LCI.BY_WHOM_FILED as byWhomFiled, LCI.LEGAL_EXPENSE as legalExpense, " +
            "       LCI.FIRST_ORDER_DATE as firstOrderDate, LCI.AMOUNT_INVOLVED as amountInvolved, CRT.NAME as courtName, LCI.SUBJECT_MATTER_OF_CASE as subjectMatterOfCase, " +
            "       LCI.HEARING_DATE as hearingDate, COA.NAME as courseOfName, " +
            "       LISTAGG(L.NAME, ', ') WITHIN GROUP (ORDER BY L.ID) AS lawyerName, " +
            "       LISTAGG(L.MOBILE_NO, ', ') WITHIN GROUP (ORDER BY L.ID) AS cellNumber, " +
            "       LCI.NAME_OF_OFFICER_EMPLOYEE_OTHER_INVOLVED as nameOfOfficer,LCI.LD_NO as ld, CS.NAME as status, " +
            "       LCI.REMARKS as remarks " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       LEFT JOIN LOS_TB_S_BRANCH BAR ON BAR.BRANCH_NAME= LCI.BRANCH_NAME " +
            "       LEFT JOIN COURTS CRT ON CRT.ID = LCI.COURT_ID " +
            "       LEFT JOIN COURSE_OF_ACTION COA ON COA.ID = LCI.COURSE_OF_ACTION_ID " +
            "       LEFT JOIN LITIGATION_CASE_INFO_LAWYER LCILW ON LCILW.LITIGATION_CASE_INFO_ID = LCI.ID " +
            "       LEFT JOIN LAWYER L ON L.ID = LCILW.LAWYER_ID " +
            "       LEFT JOIN CASE_STATUS CS ON CS.ID = LCI.STATUS_ID " +
            "       LEFT JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE ON CBIE.CUSTOMER_ID = LCI.CUSTOMER_CIF_NO " +
            "GROUP BY BAR.ROUTING_NO,BAR.BRANCH_NAME,LCI.LD_NO,LCI.DIVISION,LCI.CUSTOMER_CIF_NO,CBIE.CUSTOMER_NAME,CBIE.MOBILE_NUMBER, " +
            "    LCI.BUSINESS_SEGMENT,LCI.OUTSTANDING,CBIE.CL_STATUS,LCI.PETITIONER_NAME,LCI.OPPOSITE_PARTY_NAME,LCI.CASE_NUMBER, " +
            "         LCI.DATE_OF_FILING,LCI.SUBJECT_MATTER_OF_CASE,LCI.BY_WHOM_FILED,LCI.LEGAL_EXPENSE,LCI.FIRST_ORDER_DATE, " +
            "         LCI.AMOUNT_INVOLVED,CRT.NAME,LCI.HEARING_DATE,COA.NAME,LCI.NAME_OF_OFFICER_EMPLOYEE_OTHER_INVOLVED,LCI.LD_NO,CS.NAME,LCI.REMARKS", nativeQuery = true)
    List<Tuple> findHighCourtSuitReportData();
}
