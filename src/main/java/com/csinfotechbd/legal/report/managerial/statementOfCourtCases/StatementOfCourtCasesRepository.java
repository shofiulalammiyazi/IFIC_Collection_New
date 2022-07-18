package com.csinfotechbd.legal.report.managerial.statementOfCourtCases;


import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface StatementOfCourtCasesRepository extends JpaRepository<LitigationCaseInfo, Long> {

   /* @Query(value = "" +
            "SELECT TO_CHAR(LCI.DATE_OF_FILING, 'DD.MM.YYYY')                              AS DATE_OF_FILING, " +
            "       LCI.NAME_OF_ACC                                                        AS ACCOUNT_NAME, " +
            "       DECODE(LCI.DEFENDANT_NAME, NULL, LCI.ACCUSED_NAME, LCI.DEFENDANT_NAME) AS DEFENDENT_NAME, " +
            "       LCI.LD_NO                                                              AS LD_NO, " +
            "       DECODE(LOWER(CT.NAME), 'others', LCI.OTHER_CASE_TYPE, CT.NAME)         AS CASE_TYPE, " +
            "       LCI.CASE_NUMBER                                                        AS CASE_NUMBER, " +
            "       DECODE(LCI.SUIT_VALUE, 0, CHEQUE_AMOUNT, LCI.SUIT_VALUE)               AS CASE_AMOUNT, " +
            "       TO_CHAR(SLT.PREVIOUS_DATE, 'DD.MM.YYYY')                               AS PREVIOUS_DATE, " +
            "       TO_CHAR(LCI.NEXT_DATE, 'DD.MM.YYYY')                                   AS NEXT_DATE, " +
            "       DECODE(COA.NAME, 'Others', LCI.OTHER_COURSE_OF_ACTION, COA.NAME)       AS COURSE_OF_ACTION " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_TYPE CT on LCI.CASE_TYPE_ID = CT.ID " +
            "       LEFT JOIN COURSE_OF_ACTION COA ON COA.ID = LCI.COURSE_OF_ACTION_ID " +
            "       LEFT JOIN (SELECT LT.ID, MAX((SELECT NEXT_DATE " +
            "                                     FROM LITIGATION_CASE_INFO_AUD " +
            "                                     WHERE id = LT.ID " +
            "                                     ORDER BY REV DESC " +
            "                                     OFFSET 1 ROW " +
            "                                     FETCH FIRST ROW ONLY)) AS PREVIOUS_DATE " +
            "                  FROM LITIGATION_CASE_INFO_AUD LT " +
            "                  GROUP BY LT.ID)SLT ON SLT.ID = LCI.ID " +
            "WHERE LCI.BRANCH_CODE = :branchCode " +
            "ORDER BY CASE_TYPE, LCI.DATE_OF_FILING", nativeQuery = true)
    List<Tuple> getReport(@Param("branchCode") String branchCode);*/

   /* @Query(value = "" +
            " with dateList as (select bla.next_date, ROW_NUMBER() OVER (ORDER BY bla.next_date desc) rowno, TO_CHAR(LCI.DATE_OF_FILING, 'DD.MM.YYYY')                              AS DATE_OF_FILING, " +
            "       LCI.NAME_OF_ACC                                                        AS ACCOUNT_NAME, " +
            "       DECODE(LCI.DEFENDANT_NAME, NULL, LCI.ACCUSED_NAME, LCI.DEFENDANT_NAME) AS DEFENDENT_NAME, " +
            "       LCI.LD_NO                                                              AS LD_NO, " +
            "       DECODE(LOWER(CT.NAME), 'others', LCI.OTHER_CASE_TYPE, CT.NAME)         AS CASE_TYPE, " +
            "       LCI.CASE_NUMBER                                                        AS CASE_NUMBER, " +
            "       DECODE(LCI.SUIT_VALUE, 0, CHEQUE_AMOUNT, LCI.SUIT_VALUE)               AS CASE_AMOUNT, " +

            "       bla.course_of_action_name       AS COURSE_OF_ACTION " +
            "from litigation_case_info LCI " +
            "       JOIN CASE_TYPE CT on LCI.CASE_TYPE_ID = CT.ID " +
            "       left join litigation_case_info_bla_attendance_history_list lci on lci.litigation_case_info_id = LCI.id " +
            "       left join bla_attendance_history bla on bla.id = lci.bla_attendance_history_list_id "+
            "WHERE LCI.BRANCH_CODE = :branchCode) " +
            "select d1.DATE_OF_FILING, d1.ld_no, d1.ACCOUNT_NAME, d1.DEFENDENT_NAME, d1.CASE_TYPE, d1.CASE_NUMBER, d1.CASE_AMOUNT, TO_CHAR(d2.next_date, 'DD.MM.YYYY') as PREVIOUS_DATE, TO_CHAR(d1.next_date, 'DD.MM.YYYY') as NEXT_DATE, d1.COURSE_OF_ACTION from dateList d1 "+
            " left join dateList d2  on d1.rowno+1 = d2.rowno "+
            "  where d1.rowno = 1"
            ,nativeQuery = true)
    List<Tuple> getReport(@Param("branchCode") String branchCode);*/


     @Query(value = "" +
            "SELECT TO_CHAR(LCI.DATE_OF_FILING, 'DD.MM.YYYY')                              AS DATE_OF_FILING, " +
            "       LCI.NAME_OF_ACC                                                        AS ACCOUNT_NAME, " +
            "       DECODE(LCI.DEFENDANT_NAME, NULL, LCI.ACCUSED_NAME, LCI.DEFENDANT_NAME) AS DEFENDENT_NAME, " +
            "       LCI.LD_NO                                                              AS LD_NO, " +
            "       DECODE(LOWER(CT.NAME), 'others', LCI.OTHER_CASE_TYPE, CT.NAME)         AS CASE_TYPE, " +
            "       LCI.CASE_NUMBER                                                        AS CASE_NUMBER, " +
            "       DECODE(LCI.SUIT_VALUE, 0, CHEQUE_AMOUNT, LCI.SUIT_VALUE)               AS CASE_AMOUNT, " +
            "       TO_CHAR(LCI.PREVIOUS_DATE, 'DD.MM.YYYY')                               AS PREVIOUS_DATE, " +
            "       TO_CHAR(LCI.NEXT_DATE, 'DD.MM.YYYY')                                   AS NEXT_DATE, " +
            "       LCI.COURSE_OF_ACTION_NAMES       AS COURSE_OF_ACTION " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_TYPE CT on LCI.CASE_TYPE_ID = CT.ID " +
            "WHERE LCI.BRANCH_CODE = :branchCode " +
            "ORDER BY CASE_TYPE, LCI.DATE_OF_FILING", nativeQuery = true)
    List<Tuple> getReport(@Param("branchCode") String branchCode);


}
