package com.csinfotechbd.legal.report.bangladeshBankReports.cibWritPetition;


import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface CibWritPetitionRepository extends JpaRepository<LitigationCaseInfo, Long> {

   /* @Query(value = "" +
            "SELECT LCI.WRIT_PETITION_NUM                                                   AS WRIT_PETITION_NUMBER, " +
            "       DECODE(LCI.DEFENDANT_NAME, NULL, LCI.ACCUSED_NAME, LCI.DEFENDANT_NAME)  AS DEFENDANT_NAME, " +
            "       LCI.NAME_OF_ACC                                                         AS ACCOUNT_NAME, " +
            "       L.NAME                                                                  AS LAWYER_NAME, " +
            "       DECODE(L.PHONE_NO, NULL, L.MOBILE_NO, L.PHONE_NO)                       AS LAWYER_PHONE, " +
            "       DECODE(LOWER(CS.NAME), 'others', LCI.OTHER_STATUS, CS.NAME)             AS CASE_STATUS, " +
            "       DECODE(LOWER(COA.NAME), 'others', LCI.OTHER_COURSE_OF_ACTION, COA.NAME) AS COURSE_OF_ACTION, " +
            "       LCI.REMARKS                                                             AS REMARKS " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_TYPE CT ON LCI.CASE_TYPE_ID = CT.ID AND LOWER(CT.NAME) = 'writ' " +
            "       LEFT JOIN LITIGATION_CASE_INFO_LAWYER LCIL on LCI.ID = LCIL.LITIGATION_CASE_INFO_ID " +
            "       LEFT JOIN LAWYER L on LCIL.LAWYER_ID = L.ID " +
            "       LEFT JOIN CASE_STATUS CS ON LCI.STATUS_ID = CS.ID " +
            "       LEFT JOIN COURSE_OF_ACTION COA ON LCI.COURSE_OF_ACTION_ID = COA.ID " +
            "WHERE LOWER(LCI.CASE_TYPE_SUB_TYPE) = 'cib' " +
            "  AND LCI.DATE_OF_FILING BETWEEN TO_DATE(:startDate, 'YYYY-MM-DD') AND TO_DATE(:endDate, 'YYYY-MM-DD')", nativeQuery = true)
    List<Tuple> getReport(@Param("startDate") String startDate, @Param("endDate") String endDate);*/


    @Query(value = "" +
            "SELECT concat(LCI.WRIT_PETITION_NUM,CONCAT('/',LCI.CASE_YEAR))                 AS WRIT_PETITION_NUMBER, " +
            "       DECODE(LCI.DEFENDANT_NAME, NULL, LCI.ACCUSED_NAME, LCI.DEFENDANT_NAME)  AS DEFENDANT_NAME, " +
            "       LCI.NAME_OF_ACC                                                         AS ACCOUNT_NAME, " +
            "       L.NAME                                                                  AS LAWYER_NAME, " +
            "       DECODE(L.PHONE_NO, NULL, L.MOBILE_NO, L.PHONE_NO)                       AS LAWYER_PHONE, " +
            "       DECODE(LOWER(CS.NAME), 'others', LCI.OTHER_STATUS, CS.NAME)             AS CASE_STATUS, " +
            "       DECODE(LOWER(CS.NAME), 'pending', null, trunc(LCI.STATUS_CHANGE)) AS COURSE_OF_ACTION, " +
            "       LCI.REMARKS                                                             AS REMARKS " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_TYPE CT ON LCI.CASE_TYPE_ID = CT.ID AND LOWER(CT.NAME) = 'writ' " +
            "       LEFT JOIN LITIGATION_CASE_INFO_LAWYER LCIL on LCI.ID = LCIL.LITIGATION_CASE_INFO_ID " +
            "       LEFT JOIN LAWYER L on LCIL.LAWYER_ID = L.ID " +
            "       LEFT JOIN CASE_STATUS CS ON LCI.STATUS_ID = CS.ID " +
            "WHERE LOWER(LCI.CASE_TYPE_SUB_TYPE) = 'cib' and (LOWER(CS.NAME)='pending' or LOWER(CS.NAME) ='discharged' or LOWER(CS.NAME) = 'disposed off') " +
            "  AND LCI.DATE_OF_FILING BETWEEN TO_DATE(:startDate, 'YYYY-MM-DD') AND TO_DATE(:endDate, 'YYYY-MM-DD')", nativeQuery = true)
    List<Tuple> getReport(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
