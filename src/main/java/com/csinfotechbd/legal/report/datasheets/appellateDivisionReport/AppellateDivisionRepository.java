package com.csinfotechbd.legal.report.datasheets.appellateDivisionReport;

import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface AppellateDivisionRepository extends JpaRepository<LitigationCaseInfo, Long> {


    @Query(value = "SELECT BRN.BRANCH_CODE            as branchCode, " +
            "       LCI.LD_NO                  as ld, " +
            "       BRN.BRANCH_NAME            as branchName, " +
            "       LCI.CASE_NUMBER            as caseNo, " +
            "       LCI.CASE_YEAR              as caseYear, " +
            "       LCI.DATE_OF_FILING         as dateOfFiling, " +
            "       LCI.ACCUSED_NAME           as accountName, " +
            "       LCI.PETITIONER_NAME        as petitionerName, " +
            "       LCI.OPPOSITE_PARTY_NAME    as oppositeParty, " +
            "       LCI.AMOUNT_INVOLVED        as amountInvolved, " +
            "       LCI.LEGAL_EXPENSE          as legalExpense, " +
            "       LCI.BLA_ATTENDANCE         as bLAName, " +
            "       CRT.NAME                   as courtName, " +
            "       LCI.BY_WHOM_FILED          as byWhomFiledByBankOrAgainstBank, " +
            "       LCI.FIRST_ORDER_DATE       as firstOrderDate, " +
            "       LCI.HEARING_DATE           as hearingDate, " +
            "       COA.NAME                   as courseOfAction, " +
            "       LCI.SUBJECT_MATTER_OF_CASE as SubjectMatterOfTheCase, " +
            "       CS.NAME                    as Status " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       LEFT JOIN LOS_TB_S_BRANCH BRN ON BRN.BRANCH_NAME = LCI.BRANCH_NAME " +
            "       LEFT JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE ON CBIE.CUSTOMER_ID = LCI.CUSTOMER_CIF_NO " +
            "       LEFT JOIN COURTS CRT ON CRT.ID = LCI.COURT_ID " +
            "       LEFT JOIN LITIGATION_CASE_INFO_LAWYER LCILC ON LCILC.LITIGATION_CASE_INFO_ID = LCI.ID " +
            "       LEFT JOIN LAWYER LY ON LY.ID = LCILC.LAWYER_ID " +
            "       LEFT JOIN CASE_STATUS CS ON CS.ID = LCI.STATUS_ID " +
            "       LEFT JOIN COURSE_OF_ACTION COA ON COA.ID = LCI.COURSE_OF_ACTION_ID " +
            "       LEFT JOIN CASE_FILED_TYPE CFT ON CFT.ID = LCI.CASE_FILED_ID " +
            "       LEFT JOIN COLLATERAL_SECURITY COS ON COS.ID = LCI.COLLATERAL_SECURITY_ID " +
            "WHERE CRT.NAME = 'Appellate Division' ", nativeQuery = true)
    List<Tuple> getCaseList();
}
