package com.csinfotechbd.legal.report.datasheets.writFileMonitoringReport;

import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface WritFileMonitoringReportRepository extends JpaRepository<LitigationCaseInfo, Long> {

    @Query(value = "SELECT BRN.ROUTING_NO AS branchCode, BRN.BRANCH_NAME AS branchName, LCI.CUSTOMER_CIF_NO AS cif, LCI.CUSTOMER_ACC_NUM AS accountNo, LCI.NAME_OF_ACC AS accountName, " +
            "       CBIE.CUSTOMER_NAME AS defendantName, CBIE.MOBILE_NUMBER AS defendantMobile, LCI.BUSINESS_SEGMENT AS segment, LCI.PETITIONER_NAME AS petitionerName, LCI.WRIT_PETITION_NUM as writNumber, " +
            "       LCI.DATE_OF_FILING AS dateOfFiling, LCI.OPPOSITE_PARTY_NAME AS respondantsName, LCI.AMOUNT_INVOLVED AS amountInvolved, LCI.SUBJECT_MATTER_OF_CASE AS subjectMatterOfWrit, " +
            "       LCI.FIRST_ORDER_DATE AS firstOrderDate, LCI.BY_WHOM_FILED AS byWhomFiled, " +
            "       LISTAGG(LY.NAME, ', ') WITHIN GROUP (ORDER BY LY.NAME) AS lawyers, " +
            "       LISTAGG(LY.MOBILE_NO, ', ') WITHIN GROUP (ORDER BY LY.NAME) AS layersCellNum, " +
            "       CRT.NAME AS courtName, LCI.LEGAL_EXPENSE as legalExpense, LCI.ARTHARIN_SUIT_NO as artharinSuitNo, LCI.NI_ACT_CASE_NO AS niActCaseNumber, " +
            "       LCI.OTHER_SUIT_CASE AS otherSuitCase, LCI.SUIT_VALUE AS arExSuitValue, LCI.CHEQUE_AMOUNT AS niActChequeAmount, CS.NAME AS caseStatus " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       LEFT JOIN LOS_TB_S_BRANCH BRN ON BRN.BRANCH_NAME = LCI.BRANCH_NAME " +
            "       LEFT JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE ON CBIE.CUSTOMER_ID = LCI.CUSTOMER_CIF_NO " +
            "       LEFT JOIN COURTS CRT ON CRT.ID = LCI.COURT_ID " +
            "       LEFT JOIN LITIGATION_CASE_INFO_LAWYER LCILC ON LCILC.LITIGATION_CASE_INFO_ID = LCI.ID " +
            "       LEFT JOIN LAWYER LY ON LY.ID = LCILC.LAWYER_ID " +
            "       LEFT JOIN CASE_STATUS CS ON CS.ID = LCI.STATUS_ID " +
            "WHERE LCI.CASE_TYPE_ID = (SELECT ID FROM CASE_TYPE WHERE LOWER(NAME) = LOWER('writ')) " +
            "GROUP BY BRN.ROUTING_NO, BRN.BRANCH_NAME, LCI.CUSTOMER_CIF_NO, LCI.CUSTOMER_ACC_NUM, LCI.NAME_OF_ACC, " +
            "    CBIE.CUSTOMER_NAME, CBIE.MOBILE_NUMBER, LCI.BUSINESS_SEGMENT, LCI.PETITIONER_NAME, LCI.WRIT_PETITION_NUM, " +
            "    LCI.DATE_OF_FILING, LCI.OPPOSITE_PARTY_NAME, LCI.AMOUNT_INVOLVED, LCI.SUBJECT_MATTER_OF_CASE, " +
            "    LCI.FIRST_ORDER_DATE, LCI.BY_WHOM_FILED, CRT.NAME, LCI.LEGAL_EXPENSE, LCI.ARTHARIN_SUIT_NO, LCI.NI_ACT_CASE_NO, " +
            "    LCI.OTHER_SUIT_CASE, LCI.SUIT_VALUE, LCI.CHEQUE_AMOUNT, CS.NAME", nativeQuery = true)
    List<Tuple> findAllByBranch(String branch);
}
