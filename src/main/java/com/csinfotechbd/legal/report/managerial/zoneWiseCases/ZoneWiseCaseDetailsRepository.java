package com.csinfotechbd.legal.report.managerial.zoneWiseCases;

import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import java.util.List;

public interface ZoneWiseCaseDetailsRepository extends JpaRepository<LitigationCaseInfo, Long> {

    @Query(value = "" +
            "SELECT LCI.BRANCH_NAME                                                                    AS BRANCH_NAME, " +
            "       DECODE(LCI.CUSTOMER_ACC_NUM, NULL, LCI.NAME_OF_ACC, LCI.CUSTOMER_ACC_NUM)          AS ACCOUNT_NUMBER, " +
            "       DECODE(LOWER(CT.NAME), 'others', LCI.OTHER_CASE_TYPE, CT.NAME)                     AS CASE_TYPE, " +
            "       LCI.CASE_NUMBER                                                                    AS CASE_NUMBER, " +
            "       DECODE(LOWER(C.NAME), 'others', LCI.OTHER_COURT, C.NAME)                           AS COURT_NAME, " +
            "       DECODE(COALESCE(LCI.SUIT_VALUE, 0), 0, LCI.CHEQUE_AMOUNT, LCI.SUIT_VALUE) / 100000 AS SUIT_VALUE " +
            "FROM LITIGATION_CASE_INFO LCI " +
            "       JOIN CASE_TYPE CT ON CT.ID = LCI.CASE_TYPE_ID " +
            "       LEFT JOIN COURTS C on LCI.COURT_ID = C.ID " +
            "WHERE LCI.ZONE = :zoneName " +
            "  AND LCI.DISTRICT IN (:districts)  " +
            "ORDER BY BRANCH_NAME, CASE_TYPE, LCI.DATE_OF_FILING ASC", nativeQuery = true)
    List<Tuple> getReport(@Param("zoneName") String zoneName, @Param("districts") List<String> districts);

}
