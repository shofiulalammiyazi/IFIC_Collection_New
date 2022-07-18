package com.csinfotechbd.legal.report.datasheets.accountWiseCaseReportUnderSupremeCourt;

import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountWiseCaseReportUnderSupremeCourtRepository extends JpaRepository<LitigationCaseInfo, Long> {

    @Query(value = "SELECT lt.LD_NO, " +
            "       lt.BRANCH_CODE, " +
            "       lt.BRANCH_NAME, " +
            "       lt.CUSTOMER_CIF_NO, " +
            "       lt.CUSTOMER_ACC_NUM, " +
            "       lt.NAME_OF_ACC, " +
            "       lt.ACCUSED_NAME, " +
            "       '-'                                                       AS ACCUSED_MOBILE, " +
            "       lt.BUSINESS_SEGMENT, " +
            "       ct.NAME                                                   AS CASE_TYPE, " +
            "       lt.PETITIONER_NAME, " +
            "       lt.OPPOSITE_PARTY_NAME, " +
            "       lt.DISTRICT, " +
            "       lt.CASE_NUMBER, " +
            "       lt.DATE_OF_FILING, " +
            "       u.FIRST_NAME                                              AS BY_WHOM_FILED_BY_BANK, " +
            "       lt.BY_WHOM_FILED, " +
            "       lt.SUBJECT_MATTER_OF_CASE, " +
            "       lt.FIRST_ORDER_DATE, " +
            "       lt.HEARING_DATE, " +
            "       ca.NAME                                                   AS COURSE_OF_ACTION, " +
            "       lt.BLA_ATTENDANCE, " +
            "       COALESCE(lt.AMOUNT_INVOLVED, 0)                           AS AMOUNT_INVOLVED, " +
            "       COALESCE(lt.LEGAL_EXPENSE, 0)                             AS LEGAL_EXPENSE, " +
            "       LISTAGG(lw.NAME, ',') WITHIN GROUP (ORDER BY lw.ID)       AS lawyers, " +
            "       LISTAGG(lw.PHONE_NO, ',') WITHIN GROUP (ORDER BY lw.ID)   AS lawyers_phone, " +
            "       LISTAGG(lwaud.NAME, ',') WITHIN GROUP (ORDER BY lwaud.ID) AS lawyers_change, " +
            "       lt.ARTHARIN_SUIT_NO, " +
            "       lt.NI_ACT_CASE_NO, " +
            "       lt.OTHERS, " +
            "       '-'                                                       AS STATUS, " +
            "       cs.NAME                                                   AS CASE_STATUS, " +
            "       lt.REMARKS " +
            "FROM LITIGATION_CASE_INFO lt " +
            "       JOIN CASE_FILED_TYPE cft on lt.CASE_FILED_ID = cft.ID AND LOWER(cft.NAME) LIKE '%supreme%' " +
            "       JOIN CASE_TYPE ct ON lt.CASE_TYPE_ID = ct.ID " +
            "       JOIN LOS_TB_M_USERS u ON lt.CREATED_BY = u.EMPLOYEE_ID  OR lt.MODIFIED_BY = u.EMPLOYEE_ID " +
            "       JOIN COURSE_OF_ACTION ca ON lt.COURSE_OF_ACTION_ID = ca.ID " +
            "       LEFT JOIN LOS_TB_S_BRANCH br ON br.BRANCH_NAME = lt.BRANCH_NAME " +
            "       LEFT JOIN LITIGATION_CASE_INFO_LAWYER L on lt.ID = L.LITIGATION_CASE_INFO_ID " +
            "       LEFT JOIN LAWYER lw on L.LAWYER_ID = lw.ID " +
            "       LEFT JOIN LITIGATION_CASE_INFO_LAWYER_AUD laud ON laud.LITIGATION_CASE_INFO_ID = lt.ID " +
            "       LEFT JOIN LAWYER lwaud ON lwaud.ID = laud.LAWYER_ID " +
            "       LEFT JOIN CASE_STATUS cs on lt.STATUS_ID = cs.ID " +
            "GROUP BY lt.LD_NO, lt.BRANCH_CODE, lt.BRANCH_NAME, lt.CUSTOMER_CIF_NO, lt.CUSTOMER_ACC_NUM, " +
            "         lt.NAME_OF_ACC, lt.ACCUSED_NAME, lt.BUSINESS_SEGMENT, ct.NAME, " +
            "         lt.PETITIONER_NAME, lt.OPPOSITE_PARTY_NAME, lt.DISTRICT, lt.CASE_NUMBER, " +
            "         lt.DATE_OF_FILING, u.FIRST_NAME, lt.BY_WHOM_FILED, lt.SUBJECT_MATTER_OF_CASE, " +
            "         lt.FIRST_ORDER_DATE, lt.HEARING_DATE, ca.NAME, lt.BLA_ATTENDANCE, lt.AMOUNT_INVOLVED, " +
            "         lt.LEGAL_EXPENSE, lwaud.NAME, lt.ARTHARIN_SUIT_NO, lt.NI_ACT_CASE_NO, STATUS, " +
            "         lt.OTHERS, cs.NAME, lt.REMARKS", nativeQuery = true)
    Object[][] getReport();

    // Not feasible as lawyer change is preserved in litigation_aud and litigation_aud has no entity mapping
//    @Query("SELECT new com.csinfotechbd.reports.legal.accountWiseCaseReportUnderSupremeCourt.AccountWiseCaseReportUnderSupremeCourtDto(" +
//            "lci, u, c " +
//            ")  FROM LitigationCaseInfo lci " +
//            "JOIN User u ON lci.createdBy = u.employeeId " +
//            "LEFT JOIN CustomerBasicInfoEntity c ON lci.customerAccNum = c.accountNo " +
//            "WHERE lower(lci.caseFiled.name) like '%supreme%'")
//    List<AccountWiseCaseReportUnderSupremeCourtDto> getReportDate();

}
