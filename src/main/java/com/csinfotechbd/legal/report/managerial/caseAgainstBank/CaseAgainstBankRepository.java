package com.csinfotechbd.legal.report.managerial.caseAgainstBank;

import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import java.util.List;

public interface CaseAgainstBankRepository extends JpaRepository<LitigationCaseInfo,Long> {
    
    /*@Query(value = ""+
            "SELECT DECODE(LOWER(CT.NAME), 'others', LCI.OTHER_CASE_TYPE, CT.NAME)                     AS CASE_TYPE, " +
            "       LCI.BRANCH_NAME                                                                    AS BRANCH_NAME, " +
            "       DECODE(LCI.CUSTOMER_ACC_NUM, NULL, LCI.NAME_OF_ACC, LCI.CUSTOMER_ACC_NUM)          AS ACCOUNT_NUMBER, " +
            "       LCI.CASE_NUMBER                                                                    AS CASE_NUMBER, " +
            "       TO_CHAR(LCI.DATE_OF_FILING, 'DD.MM.YYYY')                                          AS DATE_OF_FILING, " +
            "       DECODE(LOWER(C.NAME), 'others', LCI.OTHER_COURT, C.NAME)                           AS COURT_NAME, " +
            "       DECODE(COALESCE(LCI.SUIT_VALUE, 0), 0, LCI.CHEQUE_AMOUNT, LCI.SUIT_VALUE) / 100000 AS SUIT_VALUE, " +
            "       TO_CHAR(LCI.NEXT_DATE, 'DD.MM.YYYY')                                               AS NEXT_DATE, " +
            "       DECODE(LOWER(COA.NAME), 'others', LCI.OTHER_COURSE_OF_ACTION, COA.NAME)            AS COURSE_OF_ACTION " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID " +
            "       JOIN CASE_FILED_TYPE CFT ON CT.CASE_FILED_TYPE_ID = CFT.ID " +
            "       LEFT JOIN COURSE_OF_ACTION COA ON LCI.COURSE_OF_ACTION_ID = COA.ID " +
            "       LEFT JOIN COURTS C on LCI.COURT_ID = C.ID " +
            "WHERE LCI.CASE_TYPE_ID IN (:caseTypeIds) " +
            "  AND (CFT.NAME = 'Against Bank' OR LCI.BY_WHOM_FILED = 'Against Bank') " +
            "ORDER BY BRANCH_NAME, CASE_TYPE, LCI.DATE_OF_FILING ASC",nativeQuery = true)
     List<Tuple> getReport(@Param("caseTypeIds") List<Long> caseTypeIds);*/

    /*@Query(value = ""+
            "with blaHistory AS( "+
            "select litigation_case_info_id, max(bla.next_date) AS next_date, bla.course_of_action_name as course_of_action_name "+
            "from bla_attendance_history bla "+
            "group by litigation_case_info_id,course_of_action_name), "+
            "tableList1 AS (SELECT DECODE(LOWER(CT.NAME), 'others', LCI.OTHER_CASE_TYPE, CT.NAME)                     AS CASE_TYPE, " +
            "       LCI.BRANCH_NAME                                                                    AS BRANCH_NAME, " +
            "       DECODE(LCI.CUSTOMER_ACC_NUM, NULL, LCI.NAME_OF_ACC, LCI.CUSTOMER_ACC_NUM)          AS ACCOUNT_NUMBER, " +
            "       LCI.CASE_NUMBER                                                                    AS CASE_NUMBER, " +
            "       TO_CHAR(LCI.DATE_OF_FILING, 'DD.MM.YYYY')                                          AS DATE_OF_FILING, " +
            "       DECODE(LOWER(C.NAME), 'others', LCI.OTHER_COURT, C.NAME)                           AS COURT_NAME, " +
            "       DECODE(COALESCE(LCI.SUIT_VALUE, 0), 0, LCI.CHEQUE_AMOUNT, LCI.SUIT_VALUE) / 100000 AS SUIT_VALUE, " +
            "       TO_CHAR(blaHistory.next_date, 'DD.MM.YYYY')                                               AS NEXT_DATE, " +
            "        blaHistory.course_of_action_name           AS COURSE_OF_ACTION " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID " +
            "       JOIN CASE_FILED_TYPE CFT ON CT.CASE_FILED_TYPE_ID = CFT.ID " +
            "       left join blaHistory on blahistory.litigation_case_info_id = LCI.ID " +
            "       LEFT JOIN COURTS C on LCI.COURT_ID = C.ID " +
            "WHERE LCI.CASE_TYPE_ID IN (:caseTypeIds) " +
            "  AND (CFT.NAME = 'Against Bank' OR LCI.BY_WHOM_FILED = 'Against Bank') " +
            "ORDER BY BRANCH_NAME, CASE_TYPE, LCI.DATE_OF_FILING ASC) " +
            "select * from tableList1 ",nativeQuery = true)
    List<Tuple> getReport(@Param("caseTypeIds") List<Long> caseTypeIds);*/


@Query(value = ""+
            "SELECT DECODE(LOWER(CT.NAME), 'others', LCI.OTHER_CASE_TYPE, CT.NAME)                     AS CASE_TYPE, " +
            "       LCI.BRANCH_NAME                                                                    AS BRANCH_NAME, " +
            "       DECODE(LCI.CUSTOMER_ACC_NUM, NULL, LCI.NAME_OF_ACC, LCI.CUSTOMER_ACC_NUM)          AS ACCOUNT_NUMBER, " +
            "       LCI.CASE_NUMBER                                                                    AS CASE_NUMBER, " +
            "       TO_CHAR(LCI.DATE_OF_FILING, 'DD.MM.YYYY')                                          AS DATE_OF_FILING, " +
            "       DECODE(LOWER(C.NAME), 'others', LCI.OTHER_COURT, C.NAME)                           AS COURT_NAME, " +
            "       DECODE(COALESCE(LCI.SUIT_VALUE, 0), 0, LCI.CHEQUE_AMOUNT, LCI.SUIT_VALUE) / 100000 AS SUIT_VALUE, " +
            "       TO_CHAR(LCI.NEXT_DATE, 'DD.MM.YYYY')                                               AS NEXT_DATE, " +
            "       LCI.COURSE_OF_ACTION_NAMES            AS COURSE_OF_ACTION " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID " +
            "       JOIN CASE_FILED_TYPE CFT ON CT.CASE_FILED_TYPE_ID = CFT.ID " +
            "       LEFT JOIN COURTS C on LCI.COURT_ID = C.ID " +
            "WHERE LCI.CASE_TYPE_ID IN (:caseTypeIds) " +
            "  AND (CFT.NAME = 'Against Bank' OR LCI.BY_WHOM_FILED = 'Against Bank') " +
            "ORDER BY BRANCH_NAME, CASE_TYPE, LCI.DATE_OF_FILING ASC",nativeQuery = true)
     List<Tuple> getReport(@Param("caseTypeIds") List<Long> caseTypeIds);





}
