package com.csinfotechbd.legal.report.datasheets.judgementAwardedAdjustedAccountReport;

import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface JudgementAwardedAdjustmentAccountReportRepository extends JpaRepository<LitigationCaseInfo, Long> {

    @Query(value = "SELECT BRN.ROUTING_NO AS branchCode, BRN.BRANCH_NAME AS branchName, LCI.LD_NO as ldNumber, LCI.CUSTOMER_CIF_NO as cif, LCI.CUSTOMER_ACC_NUM as accountNo, LCI.NAME_OF_ACC as accountName, " +
            "       CBIE.CUSTOMER_NAME AS defendantName, CBIE.MOBILE_NUMBER as defendantMobile, LCI.BUSINESS_SEGMENT as segment, LCI.PLAINTIFF_NAME as plaintiffName, LCI.PLAINTIFF_PHONE_NO as plaintiffMobile, " +
            "       LISTAGG(LY.NAME, ', ') WITHIN GROUP (ORDER BY LY.NAME) AS lawyerName, " +
            "       LISTAGG(LY.MOBILE_NO, ', ') WITHIN GROUP (ORDER BY LY.NAME) AS lawyerMobile, " +
            "       LCI.DISTRICT as districtName, CRT.NAME as courtName, CT.NAME as caseType, LCI.ARTHARIN_SUIT_NO as suitNumber, CBIE.CL_STATUS AS clStatus, LCI.DATE_OF_FILING as dateOfFiling, " +
            "       LCI.RECOVERY_BEFORE_CASE_AMOUNT as caseAmount, NVL(LCI.RECOVERED_AMOUNT, 0.00) as totalRecovery, NVL(LCI.OUTSTANDING, 0.00) as outstanding, CS.NAME as caseStatus " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "LEFT JOIN LOS_TB_S_BRANCH BRN ON BRN.BRANCH_NAME = LCI.BRANCH_NAME " +
            "LEFT JOIN COURTS CRT ON CRT.ID = LCI.COURT_ID " +
            "LEFT JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE ON CBIE.CUSTOMER_ID = LCI.CUSTOMER_CIF_NO " +
            "LEFT JOIN LITIGATION_CASE_INFO_LAWYER LCILC ON LCILC.LITIGATION_CASE_INFO_ID = LCI.ID " +
            "LEFT JOIN LAWYER LY ON LY.ID = LCILC.LAWYER_ID " +
            "LEFT JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID " +
            "LEFT JOIN CASE_STATUS CS ON CS.ID = LCI.STATUS_ID " +
            "WHERE TO_CHAR(LCI.DATE_OF_FILING,'yyyy-mm') = ?1 AND LOWER(CS.NAME) = LOWER('adjusted') " +
            "GROUP BY BRN.ROUTING_NO, BRN.BRANCH_NAME, LCI.LD_NO, LCI.CUSTOMER_CIF_NO, LCI.CUSTOMER_ACC_NUM, LCI.NAME_OF_ACC, " +
            "         CBIE.CUSTOMER_NAME, CBIE.MOBILE_NUMBER, LCI.BUSINESS_SEGMENT, LCI.PLAINTIFF_NAME, LCI.PLAINTIFF_PHONE_NO, " +
            "         LCI.DISTRICT, CRT.NAME, CT.NAME, LCI.ARTHARIN_SUIT_NO, CBIE.CL_STATUS, LCI.DATE_OF_FILING, " +
            "         LCI.RECOVERY_BEFORE_CASE_AMOUNT, LCI.RECOVERED_AMOUNT, LCI.OUTSTANDING, CS.NAME", nativeQuery = true)
    List<Tuple> findAllByMonth(String month);
}
