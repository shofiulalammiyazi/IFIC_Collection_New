package com.csinfotechbd.legal.report.bangladeshBankReports.statementOfSuitsFiledByAgainstTheBank;


import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.Date;

@Repository
public interface StatementOfSuitsFiledByAgainstTheBankRepository extends JpaRepository<LitigationCaseInfo, Long> {

    @Query(value = "" +
            "SELECT COUNT(CASE " +
            "               WHEN CT.NAME IN ('Artharin Suit', 'Artharin Execution') AND " +
            "                    LCI.DATE_OF_FILING <= TRUNC(:endDate) - INTERVAL '5' YEAR THEN 1 END)                  AS TOTAL_ARTHARIN_COUNT_WITHIN_FIVE_YEAR, " +
            "       COUNT(CASE " +
            "               WHEN CT.NAME IN ('Artharin Suit', 'Artharin Execution') AND " +
            "                    LCI.DATE_OF_FILING > TRUNC(:endDate) - INTERVAL '5' YEAR THEN 1 END)                  AS TOTAL_ARTHARIN_COUNT_BEFORE_FIVE_YEAR, " +
            "       ROUND(COALESCE(SUM(CASE " +
            "                            WHEN CT.NAME IN ('Artharin Suit', 'Artharin Execution') AND " +
            "                                 LCI.DATE_OF_FILING <= TRUNC(:endDate) - INTERVAL '5' YEAR THEN LCI.SUIT_VALUE " +
            "                            ELSE 0 END), 0), " +
            "             2)                                                                                    AS TOTAL_ARTHARIN_CASE_AMOUNT_WITHIN_FIVE_YEAR, " +
            "       ROUND(COALESCE(SUM(CASE " +
            "                            WHEN CT.NAME IN ('Artharin Suit', 'Artharin Execution') AND " +
            "                                 LCI.DATE_OF_FILING > TRUNC(:endDate) - INTERVAL '5' YEAR THEN LCI.SUIT_VALUE " +
            "                            ELSE 0 END), 0), " +
            "             2)                                                                                    AS TOTAL_ARTHARIN_CASE_AMOUNT_BFORE_FIVE_YEAR, " +
            "       COUNT(CASE WHEN LOWER(CT.NAME) = 'money suit' THEN 1 END)                                          AS TOTAL_MONEY_SUIT_COUNT, " +
            "       ROUND(COALESCE(SUM(CASE WHEN LOWER(CT.NAME) = 'money suit' THEN LCI.SUIT_VALUE ELSE 0 END), 0), " +
            "             2)                                                                                    AS TOTAL_MONEY_SUIT_CASE_AMOUNT " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_TYPE CT " +
            "         on LCI.CASE_TYPE_ID = CT.ID AND CT.NAME IN ('Artharin Suit', 'Artharin Execution', 'Money Suit') " +
            "       JOIN CASE_STATUS CS on LCI.STATUS_ID = CS.ID AND CS.NAME = 'Pending'", nativeQuery = true)
    Tuple getReport(@Param("endDate") Date endDate);
}
