package com.csinfotechbd.legal.report.managerial.statementOfMonthlyNewCases;


import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

@Repository
public interface StatementOfMonthlyNewCasesRepository extends JpaRepository<LitigationCaseInfo, Long> {

   /* @Query(value = "" +
            "SELECT DECODE(LOWER(CT.NAME), 'others', LCI.OTHER_CASE_TYPE, CT.NAME)        AS CASE_TYPE, " +
            "       LCI.BRANCH_NAME                                                       AS BRANCH_NAME, " +
            "       LCI.CUSTOMER_ACC_NUM                                                  AS ACCOUNT_NUMBER, " +
            "       LCI.NAME_OF_ACC                                                       AS ACCOUNT_NAME, " +
            "       LCI.CASE_NUMBER                                                       AS CASE_NUMBER, " +
            "       TO_CHAR(LCI.DATE_OF_FILING, 'DD.MM.YYYY')                             AS DATE_OF_FILING, " +
            "       DECODE(LCI.SUIT_VALUE, 0, LCI.CHEQUE_AMOUNT, LCI.SUIT_VALUE) / 100000 AS SUIT_VALUE, " +
            "       TO_CHAR(LCI.NEXT_DATE, 'DD.MM.YYYY')                                  AS NEXT_DATE, " +
            "       CS.NAME                                                               AS CASE_STATUS " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       LEFT JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID " +
            "       LEFT JOIN CASE_STATUS CS ON CS.ID = LCI.STATUS_ID " +
            "WHERE LCI.CASE_TYPE_ID IN(:caseTypeIds) " +
            "  AND ((LCI.DATE_OF_FILING BETWEEN :startDate AND :endDate) OR LCI.DATE_OF_FILING = :startDate OR LCI.DATE_OF_FILING = :endDate)" +
            "ORDER BY CASE_TYPE, BRANCH_NAME, LCI.DATE_OF_FILING ASC", nativeQuery = true)
    List<Tuple> getReport(@Param("caseTypeIds") List<Long> caseTypeIds, @Param("startDate") Date startDate, @Param("endDate") Date endDate);*/

    @Query(value = "" +
            " WITH blaHistory AS (SELECT " +
            " litigation_case_info_id, max(BLA.NEXT_DATE) AS next_date " +
            " from LITIGATION_CASE_INFO_BLA_ATTENDANCE_HISTORY_DETAILS bla " +
            "  group by litigation_case_info_id), " +
            " tableList1 AS( select  TO_CHAR(blaHistory.next_date, 'DD.MM.YYYY') AS NEXT_DATE, " +
            " DECODE(LOWER(CT.NAME), 'others', LCI.OTHER_CASE_TYPE, CT.NAME)        AS CASE_TYPE, " +
            "       LCI.BRANCH_NAME                                                       AS BRANCH_NAME, " +
            "       LCI.CUSTOMER_ACC_NUM                                                  AS ACCOUNT_NUMBER, " +
            "       LCI.NAME_OF_ACC                                                       AS ACCOUNT_NAME, " +
            "       LCI.CASE_NUMBER                                                       AS CASE_NUMBER, " +
            "       TO_CHAR(LCI.DATE_OF_FILING, 'DD.MM.YYYY')                             AS DATE_OF_FILING, " +
            "       DECODE(LCI.SUIT_VALUE, 0, LCI.CHEQUE_AMOUNT, LCI.SUIT_VALUE) / 100000 AS SUIT_VALUE, " +
            "       CS.NAME                                                               AS CASE_STATUS " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       LEFT JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID " +
            "       LEFT JOIN CASE_STATUS CS ON CS.ID = LCI.STATUS_ID " +
            "left join blaHistory on blahistory.litigation_case_info_id = LCI.ID " +
            "WHERE LCI.CASE_TYPE_ID IN(:caseTypeIds) " +
            "  AND ((LCI.DATE_OF_FILING BETWEEN :startDate AND :endDate) OR LCI.DATE_OF_FILING = :startDate OR LCI.DATE_OF_FILING = :endDate) " +
            "ORDER BY CASE_TYPE, BRANCH_NAME, LCI.DATE_OF_FILING ASC) "+
            "select * from tableList1 ", nativeQuery = true)
    List<Tuple> getReport(@Param("caseTypeIds") List<Long> caseTypeIds, @Param("startDate") Date startDate, @Param("endDate") Date endDate);


}
