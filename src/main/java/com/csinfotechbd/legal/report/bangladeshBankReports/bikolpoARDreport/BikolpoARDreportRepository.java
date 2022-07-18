package com.csinfotechbd.legal.report.bangladeshBankReports.bikolpoARDreport;


import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

@Repository
public interface BikolpoARDreportRepository extends JpaRepository<LitigationCaseInfo, Long> {

    /*@Query(value = "" +
            "SELECT COUNT(CASE WHEN LOWER(CS.NAME) = 'pending' THEN 1 END)  AS TOTAL_PENDING, " +
            "       ROUND(COALESCE(SUM(CASE WHEN LOWER(CS.NAME) = 'pending' THEN LCI.SUIT_VALUE ELSE 0 END), 0), " +
            "             2)                                                AS TOTAL_CASE_AMOUNT_PENDING, " +
            "       COUNT(CASE WHEN LOWER(CS.NAME) = 'adjusted' THEN 1 END) AS TOTAL_ADJUSTED, " +
            "       ROUND(COALESCE(SUM(CASE WHEN LOWER(CS.NAME) = 'adjusted' THEN LCI.SUIT_VALUE ELSE 0 END), 0)  " +
            "             , 2)                                        AS TOTAL_CASE_AMOUNT_ADJUSTED, " +
            "       ROUND(COALESCE(SUM(CASE " +
            "                            WHEN LOWER(CS.NAME) = 'adjusted' THEN LCI.RECOVERED_AMOUNT " +
            "                            ELSE 0 END), 0)  " +
            "             , 2)                                        AS TOTAL_RECOVERY_AFTER_CASE " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_TYPE CT on LCI.CASE_TYPE_ID = CT.ID AND CT.NAME IN ('Artharin Suit', 'Artharin Execution') " +
            "       JOIN CASE_STATUS CS on LCI.STATUS_ID = CS.ID AND CS.NAME IN ('Adjusted', 'Pending') " +
            "WHERE LCI.DATE_OF_FILING <= (TRUNC(:endDate) + INTERVAL '1' DAY - INTERVAL '1' SECOND) " +
            "  AND LCI.UNDER_SOLENAMA = 1 " +
            "FETCH FIRST ROW ONLY ", nativeQuery = true)
    Tuple getReport(@Param("endDate") Date endDate);*/


    @Query(value = "" +
            "SELECT COUNT(CASE WHEN LOWER(CS.NAME) = 'pending' THEN 1  WHEN LOWER(CS.NAME) = 'adjusted' THEN 1 END)  AS TOTAL_PENDING, " +
            "       ROUND(COALESCE(SUM(CASE WHEN LOWER(CS.NAME) = 'pending' THEN LCI.SUIT_VALUE WHEN LOWER(CS.NAME) = 'adjusted' THEN LCI.SUIT_VALUE ELSE 0 END), 0), " +
            "             2)                                                AS TOTAL_CASE_AMOUNT_PENDING, " +
            "       COUNT(CASE WHEN LOWER(CS.NAME) = 'adjusted' THEN 1 END) AS TOTAL_ADJUSTED, " +
            "       ROUND(COALESCE(SUM(CASE WHEN LOWER(CS.NAME) = 'adjusted' THEN LCI.SUIT_VALUE ELSE 0 END), 0)  " +
            "             , 2)                                        AS TOTAL_CASE_AMOUNT_ADJUSTED, " +
            "       ROUND(COALESCE(SUM(CASE " +
            "                            WHEN LOWER(CS.NAME) = 'adjusted' THEN LCI.RECOVERED_AMOUNT " +
            "                            ELSE 0 END), 0)  " +
            "             , 2)                                        AS TOTAL_RECOVERY_AFTER_CASE " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_TYPE CT on LCI.CASE_TYPE_ID = CT.ID AND CT.NAME IN ('Artharin Suit', 'Artharin Execution') " +
            "       JOIN CASE_STATUS CS on LCI.STATUS_ID = CS.ID AND CS.NAME IN ('Adjusted', 'Pending') " +
            "WHERE LCI.DATE_OF_FILING <= (TRUNC(:endDate) + INTERVAL '1' DAY - INTERVAL '1' SECOND) " +
            "  AND LCI.UNDER_SOLENAMA = 1 " +
            "FETCH FIRST ROW ONLY ", nativeQuery = true)
    Tuple getReport(@Param("endDate") Date endDate);
}
