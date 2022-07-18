package com.csinfotechbd.legal.report.datasheets.accountWiseCaseReportUnderAraEx;

import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AccountWiseCaseReportUnderAraExRepository extends JpaRepository<LitigationCaseInfo, Long> {

    @Query(value = "SELECT lt.BRANCH_CODE, " +
            "       lt.BRANCH_NAME, " +
            "       lt.BUSINESS_SEGMENT, " +
            "       lt.CUSTOMER_CIF_NO, " +
            "       lt.CUSTOMER_ACC_NUM, " +
            "       lt.NAME_OF_ACC, " +
            "       lt.PLAINTIFF_NAME, " +
            "       lt.PLAINTIFF_DESIGNATION, " +
            "       lt.PLAINTIFF_PHONE_NO, " +
            "       c.CUSTOMER_NAME, " +
            "       c.MOBILE_NUMBER, " +
            "       lt.DATE_OF_FILING, " +
            "       lt.CASE_NUMBER, " +
            "       COALESCE(lt.AMOUNT_INVOLVED, 0), " +
            "       crt.NAME AS courtName, " +
            "       MAX((SELECT NEXT_DATE " +
            "            FROM LITIGATION_CASE_INFO_AUD " +
            "            WHERE id = lt.ID " +
            "            ORDER BY REV DESC " +
            "            OFFSET 1 ROW " +
            "            FETCH FIRST ROW ONLY)) AS previousDate, " +
            "       MAX((SELECT NAME FROM CASE_STATUS WHERE ID = (SELECT STATUS_ID " +
            "                                                    FROM LITIGATION_CASE_INFO_AUD " +
            "                                                    WHERE id = lt.ID " +
            "                                                    ORDER BY REV DESC " +
            "                                                    OFFSET 1 ROW " +
            "                                                    FETCH FIRST ROW ONLY)) " +
            "           ) AS previousStatus, " +
            "       lt.NEXT_DATE, " +
            "       cs.NAME AS caseStatus, " +
            "       LISTAGG(lw.NAME, ',') WITHIN GROUP (ORDER BY lw.ID), " +
            "       LISTAGG(lw.MOBILE_NO, ',') WITHIN GROUP (ORDER BY lw.ID), " +
            "       CASE " +
            "         WHEN REPLACE(LOWER(ct.NAME), ' ', '') = LOWER('Writ') THEN cs.NAME " +
            "         ELSE '-' END              AS writ_status, " +
            "       COALESCE(SUM(lt.RECOVERED_AMOUNT), 0), " +
            "       COALESCE(SUM(lty2.RECOVERED_AMOUNT), 0), " +
            "       COALESCE(SUM(lty3.RECOVERED_AMOUNT), 0), " +
            "       COALESCE(SUM(lty4.RECOVERED_AMOUNT), 0), " +
            "       COALESCE(SUM(lty5.RECOVERED_AMOUNT), 0), " +
            "       lt.REMARKS " +
            "FROM LITIGATION_CASE_INFO lt " +
            "       JOIN CASE_TYPE ct " +
            "         ON ct.ID = lt.CASE_TYPE_ID AND REPLACE(LOWER(ct.NAME), ' ', '') = REPLACE(LOWER(:caseType), ' ', '') " +
            "       LEFT JOIN CUSTOMER_BASIC_INFO_ENTITY c ON c.CUSTOMER_ID = lt.CUSTOMER_CIF_NO " +
            "       LEFT JOIN LITIGATION_CASE_INFO_LAWYER ltlw ON ltlw.LITIGATION_CASE_INFO_ID = lt.ID " +
            "       LEFT JOIN LAWYER lw ON lw.id = ltlw.LAWYER_ID " +
            "       LEFT JOIN COURSE_OF_ACTION coa ON coa.ID = lt.COURSE_OF_ACTION_ID " +
            "       LEFT JOIN COURTS crt ON lt.COURT_ID = crt.ID " +
            "       LEFT JOIN CASE_STATUS cs ON lt.STATUS_ID = cs.ID " +
            "       LEFT JOIN LITIGATION_CASE_INFO lty2 " +
            "         ON lt.ID = lty2.ID AND lty2.DATE_OF_FILING BETWEEN ADD_MONTHS(:startDate, -12) AND ADD_MONTHS(:endDate, -12) " +
            "       LEFT JOIN LITIGATION_CASE_INFO lty3 " +
            "         ON lt.ID = lty3.ID AND lty3.DATE_OF_FILING BETWEEN ADD_MONTHS(:startDate, -24) AND ADD_MONTHS(:endDate, -24) " +
            "       LEFT JOIN LITIGATION_CASE_INFO lty4 " +
            "         ON lt.ID = lty4.ID AND lty4.DATE_OF_FILING BETWEEN ADD_MONTHS(:startDate, -36) AND ADD_MONTHS(:endDate, -36) " +
            "       LEFT JOIN LITIGATION_CASE_INFO lty5 " +
            "         ON lt.ID = lty5.ID AND lty5.DATE_OF_FILING BETWEEN ADD_MONTHS(:startDate, -48) AND ADD_MONTHS(:endDate, -48) " +
            "GROUP BY lt.BRANCH_CODE, " +
            "         lt.BRANCH_NAME, " +
            "         lt.BUSINESS_SEGMENT, " +
            "         lt.CUSTOMER_CIF_NO, " +
            "         lt.CUSTOMER_ACC_NUM, " +
            "         lt.NAME_OF_ACC, " +
            "         lt.PLAINTIFF_NAME, " +
            "         lt.PLAINTIFF_DESIGNATION, " +
            "         lt.PLAINTIFF_PHONE_NO, " +
            "         c.CUSTOMER_NAME, " +
            "         c.MOBILE_NUMBER, " +
            "         lt.DATE_OF_FILING, " +
            "         lt.CASE_NUMBER, " +
            "         lt.AMOUNT_INVOLVED, " +
            "         crt.NAME, " +
            "         lt.NEXT_DATE, " +
            "         cs.NAME, " +
            "         ct.NAME, " +
            "         lt.REMARKS", nativeQuery = true)
    Object[][] getReportDtos(@Param("caseType") String caseType,
                             @Param("startDate") Date startDate,
                             @Param("endDate") Date endDate);


}
