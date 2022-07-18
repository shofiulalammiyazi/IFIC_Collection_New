package com.csinfotechbd.legal.report.bangladeshBankReports.artharinAndOtherCourts;


import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

@Repository
public interface ArtharinAndOtherCourtsRepository extends JpaRepository<LitigationCaseInfo, Long> {

    /*@Query(value = "" +
            "SELECT (CASE " +
            "          WHEN C.NAME IS NULL THEN 'Total' " +
            "          WHEN C.NAME = 'Others' THEN DECODE(LOWER(MAX(LCI.OTHER_COURT)), 'bankruptcy court', MAX(LCI.OTHER_COURT), C.NAME) " +
            "          ELSE C.NAME END)                                                                    AS COURT_NAME, " +
            "       COUNT(CASE WHEN LOWER(CS.NAME) = 'adjusted' THEN 1 END)                                AS TOTAL_ADJUSTED_COUNT, " +
            "       COALESCE(SUM(CASE WHEN LOWER(CS.NAME) = 'adjusted' THEN LCI.SUIT_VALUE ELSE 0 END), 0) AS TOTAL_ADJUSTED_AMOUNT, " +
            "       COALESCE(SUM(CASE " +
            "                      WHEN LOWER(CS.NAME) = 'adjusted' THEN LCI.RECOVERED_AMOUNT " +
            "                      ELSE 0 END), " +
            "                0)                                                                            AS TOTAL_ADJUSTED_RECOVERY_AMOUNT, " +
            "       COUNT(CASE WHEN LOWER(CS.NAME) = 'pending' THEN 1 END)                                 AS TOTAL_PENDING_COUNT, " +
            "       COALESCE(SUM(CASE WHEN LOWER(CS.NAME) = 'pending' THEN LCI.SUIT_VALUE ELSE 0 END), 0)  AS TOTAL_PENDING_AMOUNT, " +
            "       COALESCE(SUM(CASE " +
            "                      WHEN LOWER(CS.NAME) = 'pending' THEN LCI.RECOVERY_AFTER_CASE_AMOUNT " +
            "                      ELSE 0 END), " +
            "                0)                                                                            AS TOTAL_PENDING_RECOVERY_AMOUNT " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_TYPE CT on LCI.CASE_TYPE_ID = CT.ID AND " +
            "                            REGEXP_REPLACE(LOWER(CT.NAME), '\\W+', '') IN ('artharinsuit', 'artharinexecution') " +
            "       JOIN COURTS C on LCI.COURT_ID = C.ID AND C.NAME IN ('Artharin Adalat', 'Others', 'bankruptcycourt') " +
            "       JOIN CASE_STATUS CS on LCI.STATUS_ID = CS.ID " +
            "WHERE LCI.DATE_OF_FILING <= (TRUNC(:endDate) + INTERVAL '1' DAY - INTERVAL '1' SECOND) " +
            "GROUP BY ROLLUP (C.NAME)", nativeQuery = true)
    List<Tuple> getReport(@Param("endDate") Date endDate);*/

    /*@Query(value = "" +
            "SELECT (CASE " +
            "          WHEN C.NAME IS NULL THEN 'Total' " +
            "          WHEN C.NAME = 'Others' THEN DECODE(LOWER(MAX(LCI.OTHER_COURT)), 'bankruptcy court', MAX(LCI.OTHER_COURT), C.NAME) " +
            "          ELSE C.NAME END)                                                                    AS COURT_NAME, " +
            "       COUNT(CASE WHEN LOWER(CS.NAME) = 'adjusted' THEN 1 END)                                AS TOTAL_ADJUSTED_COUNT, " +
            "       COALESCE(SUM(CASE WHEN LOWER(CS.NAME) = 'adjusted' THEN LCI.SUIT_VALUE ELSE 0 END), 0) AS TOTAL_ADJUSTED_AMOUNT, " +
            "       COALESCE(SUM(CASE " +
            "                      WHEN LOWER(CS.NAME) = 'adjusted' THEN LCI.RECOVERED_AMOUNT " +
            "                      ELSE 0 END), " +
            "                0)                                                                            AS TOTAL_ADJUSTED_RECOVERY_AMOUNT, " +
            "       COUNT(CASE WHEN LOWER(CS.NAME) = 'pending' THEN 1 END)                                 AS TOTAL_PENDING_COUNT, " +
            "       COALESCE(SUM(CASE WHEN LOWER(CS.NAME) = 'pending' THEN LCI.SUIT_VALUE ELSE 0 END), 0)  AS TOTAL_PENDING_AMOUNT, " +
            "       COALESCE(SUM(CASE " +
            "                      WHEN LOWER(CS.NAME) = 'pending' THEN LCI.RECOVERED_AMOUNT " +
            "                      ELSE 0 END), " +
            "                0)                                                                            AS TOTAL_PENDING_RECOVERY_AMOUNT " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_TYPE CT on LCI.CASE_TYPE_ID = CT.ID AND " +
            "                            REGEXP_REPLACE(LOWER(CT.NAME), '\\W+', '') IN ('artharinsuit', 'artharinexecution') " +
            "       JOIN COURTS C on LCI.COURT_ID = C.ID AND C.NAME IN ('Artharin Adalat', 'Others', 'bankruptcycourt') " +
            "       JOIN CASE_STATUS CS on LCI.STATUS_ID = CS.ID " +
            "WHERE LCI.DATE_OF_FILING <= (TRUNC(:endDate) + INTERVAL '1' DAY - INTERVAL '1' SECOND) " +
            "GROUP BY ROLLUP (C.NAME)", nativeQuery = true)
    List<Tuple> getReport(@Param("endDate") Date endDate);*/


    @Query(value = "" +
            "SELECT (CASE " +
            "          WHEN C.NAME IS NULL THEN 'Total' " +
            "          WHEN C.NAME = 'Others' THEN DECODE(LOWER(MAX(LCI.OTHER_COURT)), 'bankruptcy court', MAX(LCI.OTHER_COURT), C.NAME) " +
            "          ELSE C.NAME END)                                                                    AS COURT_NAME, " +
            "       COUNT(CASE WHEN LOWER(CS.NAME) = 'adjusted' THEN 1 WHEN LOWER(CS.NAME) = 'withdrawn' THEN 1 WHEN LOWER(CS.NAME) = 'closed' THEN 1 END)                                AS TOTAL_ADJUSTED_COUNT, " +
            "       COALESCE(SUM(CASE WHEN LOWER(CS.NAME) = 'adjusted' THEN LCI.SUIT_VALUE WHEN LOWER(CS.NAME) = 'withdrawn' THEN LCI.SUIT_VALUE WHEN LOWER(CS.NAME) = 'closed' THEN LCI.SUIT_VALUE ELSE 0 END), 0) AS TOTAL_ADJUSTED_AMOUNT, " +
            "       COALESCE(SUM(CASE " +
            "                      WHEN LOWER(CS.NAME) = 'adjusted' THEN LCI.RECOVERED_AMOUNT " +
            "                      ELSE 0 END), " +
            "                0)                                                                            AS TOTAL_ADJUSTED_RECOVERY_AMOUNT, " +
            "       COUNT(CASE WHEN LOWER(CS.NAME) = 'pending' THEN 1 WHEN LOWER(CS.NAME) = 'adjusted' THEN 1 WHEN LOWER(CS.NAME) = 'withdrawn' THEN 1 WHEN LOWER(CS.NAME) = 'closed' THEN 1 END)                                 AS TOTAL_PENDING_COUNT, " +
            "       COALESCE(SUM(CASE WHEN LOWER(CS.NAME) = 'pending' THEN LCI.SUIT_VALUE WHEN LOWER(CS.NAME) = 'adjusted' THEN LCI.SUIT_VALUE WHEN LOWER(CS.NAME) = 'withdrawn' THEN LCI.SUIT_VALUE WHEN LOWER(CS.NAME) = 'closed' THEN LCI.SUIT_VALUE ELSE 0 END), 0)  AS TOTAL_PENDING_AMOUNT, " +
            "       COALESCE(SUM(CASE " +
            "                      WHEN LOWER(CS.NAME) = 'pending' THEN LCI.RECOVERED_AMOUNT " +
            "                      ELSE 0 END), " +
            "                0)                                                                            AS TOTAL_PENDING_RECOVERY_AMOUNT " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_TYPE CT on LCI.CASE_TYPE_ID = CT.ID AND " +
            "                            REGEXP_REPLACE(LOWER(CT.NAME), '\\W+', '') IN ('artharinsuit', 'artharinexecution') " +
            "       JOIN COURTS C on LCI.COURT_ID = C.ID AND C.NAME IN ('Artharin Adalat', 'Others', 'bankruptcycourt') " +
            "       JOIN CASE_STATUS CS on LCI.STATUS_ID = CS.ID " +
            "WHERE LCI.DATE_OF_FILING <= (TRUNC(:endDate) + INTERVAL '1' DAY - INTERVAL '1' SECOND) " +
            "GROUP BY ROLLUP (C.NAME)", nativeQuery = true)
    List<Tuple> getReport(@Param("endDate") Date endDate);

}
