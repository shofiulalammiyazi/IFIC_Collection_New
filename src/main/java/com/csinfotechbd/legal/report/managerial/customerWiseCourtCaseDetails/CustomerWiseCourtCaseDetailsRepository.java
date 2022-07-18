package com.csinfotechbd.legal.report.managerial.customerWiseCourtCaseDetails;

import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface CustomerWiseCourtCaseDetailsRepository extends JpaRepository<LitigationCaseInfo, Long> {

   /* @Query(value = "" +
            "SELECT LCI.NAME_OF_ACC                                                                       AS ACCOUNT_NAME, " +
            "       DECODE(LCI.DEFENDANT_NAME, NULL, LCI.ACCUSED_NAME, LCI.DEFENDANT_NAME)                AS DEFENDENT_NAME, " +
            "       CFT.NAME                                                                              AS CASE_FILED_TYPE, " +
            "       LCI.BRANCH_NAME                                                                       AS BRANCH, " +
            "       DECODE(LOWER(CT.NAME), 'others', LCI.OTHER_CASE_TYPE, CT.NAME)                        AS CASE_TYPE, " +
            "       DECODE(LOWER(COURT.NAME), 'others', LCI.OTHER_COURT, COURT.NAME)                      AS COURT, " +
            "       LCI.CASE_NUMBER                                                                       AS CASE_NUMBER, " +
            "       TO_CHAR(LCI.DATE_OF_FILING, 'DD.MM.YYYY')                                             AS DATE_OF_FILING, " +
            "       COALESCE(DECODE(LCI.SUIT_VALUE, NULL, LCI.CHEQUE_AMOUNT, 0, LCI.CHEQUE_AMOUNT, LCI.SUIT_VALUE), 0) / 100000 AS CASE_AMOUNT, " +
            "       LAWYER.NAME                                                                           AS LAWYER_NAME, " +
            "       DECODE(LAWYER.MOBILE_NO, NULL, LAWYER.PHONE_NO, LAWYER.MOBILE_NO)                     AS LAWYER_PHONE, " +
            "       COALESCE(LCI.MARKET_VALUE                                               , 0) /100000                       AS MARKET_VALUE, " +
            "       COALESCE(LCI.FORCED_SALE_VALUE                                          , 0) /100000                       AS FORCED_SALE_VALUE, " +
            "       COALESCE(LCI.LEGAL_EXPENSE                                              , 0) /100000                       AS LEGAL_EXPENSE, " +
            "       COALESCE(LCI.RECOVERED_AMOUNT                                           , 0) /100000                       AS TOTAL_RECOVERY, " +
            "       HISTORY.MODIFICATIONS                                                                 AS MODIFICATION_HISTORY " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_FILED_TYPE CFT ON CFT.ID = LCI.CASE_FILED_ID " +
            "       JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID " +
            "       LEFT JOIN LOS_TB_S_BRANCH BRANCH ON BRANCH.BRANCH_NAME = LCI.BRANCH_NAME " +
            "       LEFT JOIN COURTS COURT ON COURT.ID = LCI.COURT_ID " +
            "       LEFT JOIN LITIGATION_CASE_INFO_LAWYER LCIL ON LCIL.LITIGATION_CASE_INFO_ID = LCI.ID " +
            "       LEFT JOIN LAWYER ON LAWYER.ID = LCIL.LAWYER_ID " +
            "       JOIN (SELECT LCIA.ID                                                                            AS ID, " +
            "                    REPLACE('[' || " +
            "                      RTRIM( " +
            "                        XMLAGG( " +
            "                          XMLELEMENT(e, '{' " +
            "                                          || '\"caseDate\":\"' || TO_CHAR(NEXT_DATE, 'DD.MM.YYYY') || '\", ' " +
            "                                          || '\"courseOfAction\":\"' || DECODE(LOWER(COA.NAME), 'others', LCIA.OTHER_COURSE_OF_ACTION, COA.NAME) || '\", ' " +
            "                                          || '\"blaAttendance\":\"' || (CASE WHEN LCIA.BLA_ATTENDANCE = 1 THEN 'YES' ELSE 'NO' END) || '\"' " +
            "                                          || '}', ',') " +
            "                              .extract('//text()') ORDER BY LCIA.NEXT_DATE DESC) " +
            "                            .getclobval(), ', ') " +
            "                        || ']', '&quot;', " +
            "                      '\"')                                                                            AS MODIFICATIONS " +
            "             FROM LITIGATION_CASE_INFO_AUD LCIA " +
            "                    LEFT JOIN COURSE_OF_ACTION COA ON COA.ID = LCIA.COURSE_OF_ACTION_ID " +
            "             GROUP BY LCIA.ID) HISTORY ON HISTORY.ID = LCI.ID " +
            "WHERE LOWER(LCI.NAME_OF_ACC) LIKE '%' || LOWER(:accountName) || '%' " +
            "  AND LCI.CASE_FILED_ID IN(:caseFiledTypes) " +
            "  AND LCI.CASE_TYPE_ID IN(:caseTypes) " +
            "  AND LCI.BRANCH_NAME IN(:branches) " +
            "ORDER BY BRANCH, CASE_TYPE, LCI.DATE_OF_FILING ASC", nativeQuery = true)
    List<Tuple> getCustomerWiseCaseList(@Param("accountName") String accountName,
                                               @Param("caseFiledTypes") List<Long> caseFiledTypes,
                                               @Param("caseTypes") List<Long> caseTypes,
                                               @Param("branches") List<String> branches);*/


    @Query(value = "" +
            "SELECT LCI.NAME_OF_ACC                                                                       AS ACCOUNT_NAME, " +
            "       DECODE(LCI.DEFENDANT_NAME, NULL, LCI.ACCUSED_NAME, LCI.DEFENDANT_NAME)                AS DEFENDENT_NAME, " +
            "       CFT.NAME                                                                              AS CASE_FILED_TYPE, " +
            "       LCI.BRANCH_NAME                                                                       AS BRANCH, " +
            "       DECODE(LOWER(CT.NAME), 'others', LCI.OTHER_CASE_TYPE, CT.NAME)                        AS CASE_TYPE, " +
            "       DECODE(LOWER(COURT.NAME), 'others', LCI.OTHER_COURT, COURT.NAME)                      AS COURT, " +
            "       LCI.CASE_NUMBER                                                                       AS CASE_NUMBER, " +
            "       TO_CHAR(LCI.DATE_OF_FILING, 'DD.MM.YYYY')                                             AS DATE_OF_FILING, " +
            "       COALESCE(DECODE(LCI.SUIT_VALUE, NULL, LCI.CHEQUE_AMOUNT, 0, LCI.CHEQUE_AMOUNT, LCI.SUIT_VALUE), 0) / 100000 AS CASE_AMOUNT, " +
            "       LAWYER.NAME                                                                           AS LAWYER_NAME, " +
            "       DECODE(LAWYER.MOBILE_NO, NULL, LAWYER.PHONE_NO, LAWYER.MOBILE_NO)                     AS LAWYER_PHONE, " +
            "       COALESCE(LCI.TOTAL_MARKET_VALUE                                               , 0) /100000                       AS MARKET_VALUE, " +
            "       COALESCE(LCI.TOTAL_FORCED_SALE_VALUE                                          , 0) /100000                       AS FORCED_SALE_VALUE, " +
            "       COALESCE(LCI.TOTAL_LEGAL_EXPENSE_AMOUNT                                              , 0) /100000                       AS LEGAL_EXPENSE, " +
            "       COALESCE(LCI.RECOVERED_AMOUNT                                           , 0) /100000                       AS TOTAL_RECOVERY, " +
            "       HISTORY.MODIFICATIONS                                                                 AS MODIFICATION_HISTORY " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_FILED_TYPE CFT ON CFT.ID = LCI.CASE_FILED_ID " +
            "       JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID " +
            "       LEFT JOIN LOS_TB_S_BRANCH BRANCH ON BRANCH.BRANCH_NAME = LCI.BRANCH_NAME " +
            "       LEFT JOIN COURTS COURT ON COURT.ID = LCI.COURT_ID " +
            "       LEFT JOIN LITIGATION_CASE_INFO_LAWYER LCIL ON LCIL.LITIGATION_CASE_INFO_ID = LCI.ID " +
            "       LEFT JOIN LAWYER ON LAWYER.ID = LCIL.LAWYER_ID " +
            "       JOIN (SELECT  BLA.LITIGATION_CASE_INFO_ID                     AS ID, " +
            "                    REPLACE('[' || " +
            "                      RTRIM( " +
            "                        XMLAGG( " +
            "                          XMLELEMENT(e, '{' " +
            "                                          || '\"caseDate\":\"' || TO_CHAR(BLA.NEXT_DATE, 'DD.MM.YYYY') || '\", ' " +
            "                                          || '\"courseOfAction\":\"' || BLA.COURSE_OF_ACTION_NAME || '\", ' " +
            "                                          || '\"blaAttendance\":\"' || (CASE WHEN BLA.BLA_ATTENDANCE = 1 THEN 'YES' ELSE 'NO' END) || '\"' " +
            "                                          || '}', ',') " +
            "                              .extract('//text()') ORDER BY BLA.NEXT_DATE DESC) " +
            "                            .getclobval(), ', ') " +
            "                        || ']', '&quot;', " +
            "                      '\"')                                                                            AS MODIFICATIONS " +
            "             FROM LITIGATION_CASE_INFO_BLA_ATTENDANCE_HISTORY_DETAILS BLA " +
            "             GROUP BY BLA.LITIGATION_CASE_INFO_ID) HISTORY ON HISTORY.ID = LCI.ID " +
            "WHERE LOWER(LCI.NAME_OF_ACC) LIKE '%' || LOWER(:accountName) || '%' " +
            "  AND LCI.CASE_FILED_ID IN(:caseFiledTypes) " +
            "  AND LCI.CASE_TYPE_ID IN(:caseTypes) " +
            "  AND LCI.BRANCH_NAME IN(:branches) " +
            "ORDER BY BRANCH, CASE_TYPE, LCI.DATE_OF_FILING ASC", nativeQuery = true)
    List<Tuple> getCustomerWiseCaseList(@Param("accountName") String accountName,
                                        @Param("caseFiledTypes") List<Long> caseFiledTypes,
                                        @Param("caseTypes") List<Long> caseTypes,
                                        @Param("branches") List<String> branches);
}
