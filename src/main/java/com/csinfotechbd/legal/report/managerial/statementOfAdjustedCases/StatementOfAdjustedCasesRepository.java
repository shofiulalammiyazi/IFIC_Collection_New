package com.csinfotechbd.legal.report.managerial.statementOfAdjustedCases;


import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

@Repository
public interface StatementOfAdjustedCasesRepository extends JpaRepository<LitigationCaseInfo, Long> {

    /*@Query(value = "" +
            "SELECT DECODE(LOWER(CT.NAME), 'others', LCI.OTHER_CASE_TYPE, CT.NAME)            AS CASE_TYPE, " +
            "       LCI.BRANCH_NAME                                                           AS BRANCH_NAME, " +
            "       DECODE(LCI.CUSTOMER_ACC_NUM, NULL, LCI.NAME_OF_ACC, LCI.CUSTOMER_ACC_NUM) AS ACCOUNT_NUMBER, " +
            "       LCI.CASE_NUMBER                                                           AS CASE_NUMBER, " +
            "       TO_CHAR(LCI.DATE_OF_FILING, 'DD.MM.YYYY')                                 AS DATE_OF_FILING, " +
            "       DECODE(LCI.SUIT_VALUE, 0, LCI.CHEQUE_AMOUNT, LCI.SUIT_VALUE) / 100000     AS SUIT_VALUE, " +
            "       TO_CHAR(LCI.NEXT_DATE, 'DD.MM.YYYY')                                      AS ADJUSTMENT_DATE, " +
            "       LCI.RECOVERED_AMOUNT / 100000                                             AS RECOVERED_AMOUNT " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID " +
            "       LEFT JOIN CASE_STATUS CS ON CS.ID = LCI.STATUS_ID " +
            "       LEFT JOIN COURSE_OF_ACTION COA ON COA.ID = LCI.COURSE_OF_ACTION_ID " +
            "WHERE LCI.CASE_TYPE_ID IN(:caseTypeIds) " +
            "  AND ((LCI.DATE_OF_FILING BETWEEN TO_DATE(:startDate, 'YYYY-MM-DD') AND TO_DATE(:endDate, 'YYYY-MM-DD')) OR COALESCE(TO_CHAR(LCI.DATE_OF_FILING, 'YYYY-MM-DD'), '___') = COALESCE(:startDate, '___') OR COALESCE(TO_CHAR(LCI.DATE_OF_FILING, 'YYYY-MM-DD'), '___') = COALESCE(:endDate, '___') )" +
            "  AND (LOWER(CS.NAME) = 'adjusted' OR LOWER(COA.NAME) = 'adjusted') " +
            "ORDER BY CASE_TYPE, BRANCH_NAME, LCI.DATE_OF_FILING ASC", nativeQuery = true)
    List<Tuple> getReport(@Param("caseTypeIds") List<Long> caseTypeIds, @Param("startDate") String startDate, @Param("endDate") String endDate);*/

    @Query(value = "" +
            "WITH blaHistory AS (SELECT "+
            " litigation_case_info_id, "+
            " max(BLA.NEXT_DATE) AS next_date "+
            " from LITIGATION_CASE_INFO_BLA_ATTENDANCE_HISTORY_DETAILS bla "+
            " group by litigation_case_info_id ), "+
            "TEMP AS (SELECT DECODE(LOWER(CT.NAME), 'others', LCI.OTHER_CASE_TYPE, CT.NAME)            AS CASE_TYPE, " +
            "       LCI.BRANCH_NAME                                                           AS BRANCH_NAME, " +
            "       DECODE(LCI.CUSTOMER_ACC_NUM, NULL, LCI.NAME_OF_ACC, LCI.CUSTOMER_ACC_NUM) AS ACCOUNT_NUMBER, " +
            "       LCI.CASE_NUMBER                                                           AS CASE_NUMBER, " +
            "       TO_CHAR(LCI.DATE_OF_FILING, 'DD.MM.YYYY')                                 AS DATE_OF_FILING, " +
            "       DECODE(LCI.SUIT_VALUE, 0, LCI.CHEQUE_AMOUNT, LCI.SUIT_VALUE) / 100000     AS SUIT_VALUE, " +
            "       TO_CHAR(blaHistory.next_date, 'DD.MM.YYYY')                                      AS ADJUSTMENT_DATE, " +
            "       LCI.RECOVERED_AMOUNT / 100000                                             AS RECOVERED_AMOUNT " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID " +
            "       LEFT JOIN CASE_STATUS CS ON CS.ID = LCI.STATUS_ID " +
            "       left join blaHistory on blahistory.litigation_case_info_id = LCI.ID " +
            "WHERE LCI.CASE_TYPE_ID IN(:caseTypeIds) " +
            "  AND ((LCI.DATE_OF_FILING BETWEEN TO_DATE(:startDate, 'YYYY-MM-DD') AND TO_DATE(:endDate, 'YYYY-MM-DD'))) " +
            "  AND (LOWER(CS.NAME) = 'adjusted') " +
            "ORDER BY CASE_TYPE, BRANCH_NAME, LCI.DATE_OF_FILING ASC) " +
            "SELECT * FROM TEMP ", nativeQuery = true)
    List<Tuple> getReport(@Param("caseTypeIds") List<Long> caseTypeIds, @Param("startDate") String startDate, @Param("endDate") String endDate);





}
