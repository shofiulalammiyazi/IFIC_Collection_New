package com.csinfotechbd.legal.report.datasheets.moneySuitFiledAgainstTheBankReport;

import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface MoneySuitFiledAgainstTheBankReportRepository extends JpaRepository<LitigationCaseInfo, Long> {

    @Query(value = "" +
            "SELECT lt.BRANCH_NAME  AS branchName, " +
            "       lt.BRANCH_CODE       AS branchCode, " +
            "       lt.CUSTOMER_CIF_NO  AS cif, " +
            "       lt.CUSTOMER_ACC_NUM    AS accountNo, " +
            "       lt.NAME_OF_ACC      AS accountName, " +
            "       c.CUSTOMER_NAME     AS borrowerName, " +
            "       lt.BUSINESS_SEGMENT AS segment, " +
            "       lt.LD_NO            AS ldNo, " +
            "       lt.PLAINTIFF_NAME   AS plaintiffName, " +
            "       lt.DEFENDANT_NAME   AS defendantName, " +
            "       c.MOBILE_NUMBER     AS defendantMobile, " +
            "       lt.ARTHARIN_SUIT_NO AS suitNumber, " +
            "       lt.DATE_OF_FILING   AS dateOfFiling, " +
            "       COALESCE(SUM(lt.AMOUNT_INVOLVED), 0)  AS caseAmount, " +
            "       c.CL_STATUS         as clStatus, " +
            "       COALESCE(SUM(lt.RECOVERED_AMOUNT), 0) AS totalRecovery, " +
            "       COALESCE(SUM(lt.OUTSTANDING), 0)                   AS outstanding, " +
            "       lt.NEXT_DATE                     AS nextDate, " +
            "       coa.NAME                         AS courseOfAction, " +
            "       lt.COMMENT_IMPACT_ON_BANK        AS commentOnOutcomeAndImpactOnBank, " +
            "       LISTAGG(lw.NAME, ',') WITHIN GROUP (ORDER BY lw.ID) AS lawyers, " +
            "       LISTAGG(lw.PHONE_NO, ',') WITHIN GROUP (ORDER BY lw.ID) AS lawyersPhone, " +
            "       lt.REMARKS                                          AS remarks " +
            "FROM LITIGATION_CASE_INFO lt " +
            "       JOIN CASE_TYPE ct ON ct.ID = lt.CASE_TYPE_ID AND ct.NAME = 'Money Suit' " +
//            "       LEFT JOIN LOS_TB_S_BRANCH b ON b.BRANCH_NAME = lt.BRANCH_NAME " +
            "       LEFT JOIN CUSTOMER_BASIC_INFO_ENTITY c ON c.CUSTOMER_ID = lt.CUSTOMER_CIF_NO " +
            "       LEFT JOIN LITIGATION_CASE_INFO_LAWYER ltlw ON ltlw.LITIGATION_CASE_INFO_ID = lt.ID " +
            "       LEFT JOIN LAWYER lw ON lw.id = ltlw.LAWYER_ID " +
            "       LEFT JOIN COURSE_OF_ACTION coa ON coa.ID = lt.COURSE_OF_ACTION_ID " +
            "GROUP BY lt.BRANCH_NAME, " +
            "         lt.BRANCH_CODE, " +
            "         lt.CUSTOMER_CIF_NO, " +
            "         lt.CUSTOMER_ACC_NUM, " +
            "         lt.NAME_OF_ACC, " +
            "         c.CUSTOMER_NAME, " +
            "         lt.BUSINESS_SEGMENT, " +
            "         lt.LD_NO, " +
            "         lt.PLAINTIFF_NAME, " +
            "         lt.DEFENDANT_NAME, " +
            "         c.MOBILE_NUMBER, " +
            "         lt.ARTHARIN_SUIT_NO, " +
            "         lt.DATE_OF_FILING, " +
            "         c.CL_STATUS, " +
            "         lt.NEXT_DATE, " +
            "         coa.NAME, " +
            "         lt.RECOVERED_AMOUNT, " +
            "         lt.COMMENT_IMPACT_ON_BANK, " +
            "         lt.REMARKS", nativeQuery = true)
    List<Tuple> getReportDtos();


//    @Query(value =
//            "SELECT " +
//                    "lt.branchName  AS branchName, " +
//                    "       lt.branchCode       AS branchCode, " +
//                    "       lt.customerCifNo  AS cif, " +
//                    "       lt.accountNoIf    AS accountNo, " +
//                    "       lt.nameOfAcc      AS accountName, " +
//                    "       lt.borrowerName     AS borrowerName, " +
//                    "       lt.businessSegment AS segment, " +
//                    "       lt.ldNo            AS ldNo, " +
//                    "       lt.plaintiffName   AS plaintiffName, " +
//                    "       lt.defendantName   AS defendantName, " +
//                    "       c.mobileNumber     AS defendantMobile, " +
//                    "       lt.artharinSuitNo AS suitNumber, " +
//                    "       lt.dateOfFiling   AS dateOfFiling, " +
//                    "       lt.amountInvolved  AS caseAmount, " +
//                    "       c.clStatus         as clStatus, " +
//                    "       lt.recoveredAmount AS totalRecovery, " +
//                    "       lt.outstanding                   AS outstanding, " +
//                    "       lt.nextDate                     AS nextDate, " +
//                    "       lt.courseOfAction.name                         AS courseOfAction, " +
//                    "       lt.commentImpactOnBank        AS commentOnOutcomeAndImpactOnBank, " +
//                    "       lt.lawyer                                          AS lawyer, " +
//                    "       lt.remarks                                          AS remarks " +
//                    "FROM LitigationCaseInfo lt " +
//                    "       LEFT JOIN CustomerBasicInfoEntity c ON c.customerId = lt.customerCifNo " +
//                    "WHERE lt.caseType.name = 'Money Suit'")
//    List<LitigationCaseInfo> getReportDtosJpql();


}
