package com.csinfotechbd.legal.report.managerial.statementOfJudgementDisposalDecree;


import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface StatementOfJudgementDisposalDecreeRepository extends JpaRepository<LitigationCaseInfo, Long> {

   /* @Query(value = "" +
            "SELECT DECODE(LOWER(CT.NAME), 'others', LCI.OTHER_CASE_TYPE, CT.NAME)            AS CASE_TYPE, " +
            "       LCI.BRANCH_NAME                                                           AS BRANCH_NAME, " +
            "       DECODE(LCI.CUSTOMER_ACC_NUM, NULL, LCI.NAME_OF_ACC, LCI.CUSTOMER_ACC_NUM) AS ACCOUNT_NUMBER, " +
            "       LCI.CASE_NUMBER                                                           AS CASE_NUMBER, " +
            "       TO_CHAR(LCI.DATE_OF_FILING, 'DD.MM.YYYY')                                 AS DATE_OF_FILING, " +
            "       TO_CHAR(SLT.PREVIOUS_DATE, 'DD.MM.YYYY')                                  AS ORDER_DATE, " +
            "       DECODE(LOWER(COA.NAME), 'others', LCI.OTHER_COURSE_OF_ACTION, COA.NAME)   AS ACTION " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID " +
            "       JOIN COURSE_OF_ACTION COA ON LCI.COURSE_OF_ACTION_ID = COA.ID AND " +
            "                                    (LOWER(COA.NAME) = 'decreed' OR LOWER(COA.NAME) LIKE '%judgment%' OR " +
            "                                     LOWER(COA.NAME) LIKE '%dispos%') " +
            "       LEFT JOIN (SELECT LT.ID, MAX((SELECT NEXT_DATE " +
            "                                     FROM LITIGATION_CASE_INFO_AUD " +
            "                                     WHERE id = LT.ID " +
            "                                     ORDER BY REV DESC " +
            "                                     OFFSET 1 ROW " +
            "                                     FETCH FIRST ROW ONLY)) AS PREVIOUS_DATE " +
            "                  FROM LITIGATION_CASE_INFO_AUD LT " +
            "                  GROUP BY LT.ID)SLT ON SLT.ID = LCI.ID " +
            "WHERE LCI.CASE_TYPE_ID IN (:caseTypeIds) " +
            "  AND ((LCI.DATE_OF_FILING BETWEEN TO_DATE(:startDate, 'YYYY-MM-DD') AND TO_DATE(:endDate, 'YYYY-MM-DD')) OR COALESCE(TO_CHAR(LCI.DATE_OF_FILING, 'YYYY-MM-DD'), '___') = COALESCE(:startDate, '___') OR COALESCE(TO_CHAR(LCI.DATE_OF_FILING, 'YYYY-MM-DD'), '___') = COALESCE(:endDate, '___') )" +
            "ORDER BY CASE_TYPE, BRANCH_NAME, LCI.DATE_OF_FILING ASC", nativeQuery = true)
    List<Tuple> getReport(@Param("caseTypeIds") List<Long> caseTypeIds, @Param("startDate") String startDate, @Param("endDate") String endDate);*/



   /* @Query(value = "" +
            "WITH countCourseOfAction AS (select litigation_case_info_id, max(bla.next_date) AS NEXT_DATE, "+
            "course_of_action_name AS course_of_action_name from LITIGATION_CASE_INFO_BLA_ATTENDANCE_HISTORY_DETAILS bla "+
            "where course_of_action_name = 'Decreed' or course_of_action_name = 'Judgment' "+
            "group by litigation_case_info_id, course_of_action_name), "+
            "dataTable AS (SELECT DECODE(LOWER(CT.NAME), 'others', LCI.OTHER_CASE_TYPE, CT.NAME)            AS CASE_TYPE, " +
            "       LCI.BRANCH_NAME                                                           AS BRANCH_NAME, " +
            "       DECODE(LCI.CUSTOMER_ACC_NUM, NULL, LCI.NAME_OF_ACC, LCI.CUSTOMER_ACC_NUM) AS ACCOUNT_NUMBER, " +
            "       LCI.CASE_NUMBER                                                           AS CASE_NUMBER, " +
            "       TO_CHAR(LCI.DATE_OF_FILING, 'DD.MM.YYYY')                                 AS DATE_OF_FILING, " +
            "       TO_CHAR(coa.NEXT_DATE, 'DD.MM.YYYY')                                  AS ORDER_DATE, " +
            "       coa.course_of_action_name   AS ACTION " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID " +
            "left join countCourseOfAction coa on coa.litigation_case_info_id = LCI.ID "+
            "WHERE LCI.CASE_TYPE_ID IN (:caseTypeIds) " +
            "  AND ((LCI.DATE_OF_FILING BETWEEN TO_DATE(:startDate, 'YYYY-MM-DD') AND TO_DATE(:endDate, 'YYYY-MM-DD')) OR COALESCE(TO_CHAR(LCI.DATE_OF_FILING, 'YYYY-MM-DD'), '___') = COALESCE(:startDate, '___') OR COALESCE(TO_CHAR(LCI.DATE_OF_FILING, 'YYYY-MM-DD'), '___') = COALESCE(:endDate, '___') )" +
            "ORDER BY CASE_TYPE, BRANCH_NAME, LCI.DATE_OF_FILING ASC) "+
            "select * from dataTable", nativeQuery = true)
    List<Tuple> getReport(@Param("caseTypeIds") List<Long> caseTypeIds, @Param("startDate") String startDate, @Param("endDate") String endDate);*/

    @Query(value = "" +
            "WITH countCourseOfAction AS (select litigation_case_info_id, bla.next_date AS NEXT_DATE, "+
            "course_of_action_name AS course_of_action_name from LITIGATION_CASE_INFO_BLA_ATTENDANCE_HISTORY_DETAILS bla "+
            "group by litigation_case_info_id, course_of_action_name, NEXT_DATE), "+
            "dataTable AS (SELECT DECODE(LOWER(CT.NAME), 'others', LCI.OTHER_CASE_TYPE, CT.NAME)            AS CASE_TYPE, " +
            "       LCI.BRANCH_NAME                                                           AS BRANCH_NAME, " +
            "       DECODE(LCI.CUSTOMER_ACC_NUM, NULL, LCI.NAME_OF_ACC, LCI.CUSTOMER_ACC_NUM) AS ACCOUNT_NUMBER, " +
            "       LCI.CASE_NUMBER                                                           AS CASE_NUMBER, " +
            "       TO_CHAR(LCI.DATE_OF_FILING, 'DD.MM.YYYY')                                 AS DATE_OF_FILING, " +
            "       TO_CHAR(coa.NEXT_DATE, 'DD.MM.YYYY')                                  AS ORDER_DATE, " +
            "       coa.course_of_action_name   AS ACTION " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID " +
            "left join countCourseOfAction coa on coa.litigation_case_info_id = LCI.ID "+
            "WHERE LCI.CASE_TYPE_ID IN (:caseTypeIds) " +
            "  AND ((LCI.DATE_OF_FILING BETWEEN TO_DATE(:startDate, 'YYYY-MM-DD') AND TO_DATE(:endDate, 'YYYY-MM-DD')) OR COALESCE(TO_CHAR(LCI.DATE_OF_FILING, 'YYYY-MM-DD'), '___') = COALESCE(:startDate, '___') OR COALESCE(TO_CHAR(LCI.DATE_OF_FILING, 'YYYY-MM-DD'), '___') = COALESCE(:endDate, '___') ) " +
            " AND (coa.course_of_action_name LIKE '%Decreed%' or coa.course_of_action_name LIKE '%Judgment%' or coa.course_of_action_name LIKE '%dispos%' ) "+
            "ORDER BY CASE_TYPE, BRANCH_NAME, LCI.DATE_OF_FILING ASC) "+
            "select * from dataTable", nativeQuery = true)
    List<Tuple> getReport(@Param("caseTypeIds") List<Long> caseTypeIds, @Param("startDate") String startDate, @Param("endDate") String endDate);



}
