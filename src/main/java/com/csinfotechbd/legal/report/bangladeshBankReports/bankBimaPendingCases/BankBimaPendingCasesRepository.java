package com.csinfotechbd.legal.report.bangladeshBankReports.bankBimaPendingCases;


import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;

@Repository
public interface BankBimaPendingCasesRepository extends JpaRepository<LitigationCaseInfo, Long> {
    @Query(value = "" +
            "SELECT COUNT(CASE WHEN CT.NAME = 'Writ' THEN 1 END)                                                                                     AS TOTAL_WRIT_COUNT, " +
            "       ROUND(COALESCE(SUM(CASE WHEN LOWER(CT.NAME) = 'writ' THEN LCI.SUIT_VALUE ELSE 0 END), 0) / 10000000, 2)                            AS TOTAL_WRIT_CASE_AMOUNT, " +
            "       COUNT(CASE WHEN CT.NAME IN ('Artharin Suit', 'Artharin Execution') THEN 1 END)                                                   AS TOTAL_ARTHARIN_COUNT, " +
            "       ROUND(COALESCE(SUM(CASE WHEN CT.NAME IN ('Artharin Suit', 'Artharin Execution') THEN LCI.SUIT_VALUE ELSE 0 END), 0) / 10000000, 2) AS TOTAL_ARTHARIN_CASE_AMOUNT, " +
            "       COUNT(CASE WHEN LOWER(LCI.OTHER_CASE_TYPE) LIKE '%bank%' THEN 1 END)                                                             AS TOTAL_BANKRUPCY_COUNT, " +
            "       ROUND(COALESCE(SUM(CASE WHEN LOWER(LCI.OTHER_CASE_TYPE) LIKE '%bank%' THEN LCI.SUIT_VALUE ELSE 0 END), 0) / 10000000, 2)           AS TOTAL_BANKRUPCY_CASE_AMOUNT " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_TYPE CT on LCI.CASE_TYPE_ID = CT.ID AND CT.NAME IN ('Artharin Suit', 'Artharin Execution', 'Writ', 'Others') " +
            "       JOIN CASE_STATUS CS on LCI.STATUS_ID = CS.ID AND CS.NAME = 'Pending' " +
            "WHERE LCI.DATE_OF_FILING BETWEEN TO_DATE(:startDate, 'YYYY-MM-DD') AND TO_DATE(:endDate, 'YYYY-MM-DD')", nativeQuery = true)
    Tuple getReport(@Param("startDate") String startDate, @Param("endDate") String endDate);

}
