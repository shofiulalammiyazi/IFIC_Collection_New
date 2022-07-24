package com.unisoft.retail.loan.dataEntry.distribution.auto;

import com.unisoft.common.CommonRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface LoanAutoDistributionRepository extends CommonRepository<LoanAutoDistributionInfo> {

    @Query("select l from LoanAutoDistributionInfo l " +
            "where l.dealerPin is null and l.enabled = true and l.location in(:locations) and l.productCode in(:productCodes) and l.dpdBucket in(:dpdBuckets) ")
    List<LoanAutoDistributionInfo> findUnallocatedAccountsByLocationAndProductCodeAndDpdBucket(@Param("locations") List<String> locations,
                                                                                               @Param("productCodes") List<String> productCodes,
                                                                                               @Param("dpdBuckets") List<String> dpdBuckets);

    @Query("select l from LoanAutoDistributionInfo l where l.dealerPin is null and l.enabled = true")
    List<LoanAutoDistributionInfo> findUnallocatedAccounts();

    @Query("SELECT a FROM LoanAutoDistributionInfo a ORDER BY a.dealerPin, a.outstanding desc")
    List<LoanAutoDistributionInfo> findAutomaticallyDistributedAccounts();

    @Query(value = "" +
            "SELECT LADI.DEALER_PIN                    AS DEALER_PIN, " +
            "       LADI.DEALER_NAME                   AS DEALER_NAME, " +
            "       TEAM_LEAD.PIN                      AS TEAM_LEAD_PIN, " +
            "       TEAM_LEAD.NAME                     AS TEAM_LEAD_NAME, " +
            "       SUPERVISOR.PIN                     AS SUPERVISOR_PIN, " +
            "       SUPERVISOR.NAME                    AS SUPERVISOR_NAME, " +
            "       MANAGER.PIN                        AS MANAGER_PIN, " +
            "       MANAGER.NAME                       AS MANAGER_NAME, " +
            "       COUNT(LADI.ACCOUNT_NO)             AS TOTAL_ACCOUNT, " +
            "       COALESCE(SUM(LADI.OUTSTANDING), 0) AS TOTAL_OUTSTANDING, " +
            "       REPLACE('[' || " +
            "               RTRIM( " +
            "                 XMLAGG( " +
            "                   XMLELEMENT(e, '{' " +
            "                                   || '\"accountNo\":\"' || LADI.ACCOUNT_NO || '\", ' " +
            "                                   || '\"location\":\"' || LADI.LOCATION || '\", ' " +
            "                                   || '\"productCode\":\"' || LADI.PRODUCT_CODE || '\", ' " +
            "                                   || '\"dpdBucket\":\"' || LADI.DPD_BUCKET || '\", ' " +
            "                                   || '\"outstanding\":' || LADI.OUTSTANDING || '' " +
            "                                   || '}', ',') " +
            "                       .extract('//text()') ORDER BY LADI.OUTSTANDING) " +
            "                     .getclobval(), ', ') " +
            "                 || ']', '&quot;', '\"')  AS ACCOUNT_LIST " +
            "FROM LOAN_AUTO_DISTRIBUTION_INFO LADI " +
            "       LEFT JOIN EMPLOYEE_INFO_ENTITY DEALER ON DEALER.PIN = LADI.DEALER_PIN " +
            "       LEFT JOIN PEOPLE_ALLOCATION_LOGIC PAL ON PAL.DEALER_ID = DEALER.ID " +
            "       LEFT JOIN (SELECT TI.ID, TI.PIN, TU.FIRST_NAME || ' ' || TU.LAST_NAME AS NAME " +
            "                  FROM EMPLOYEE_INFO_ENTITY TI " +
            "                         JOIN LOS_TB_M_USERS TU on TI.USER_ID = TU.USER_ID) TEAM_LEAD on PAL.TEAM_LEAD_ID = TEAM_LEAD.ID " +
            "       LEFT JOIN (SELECT SI.ID, SI.PIN, SU.FIRST_NAME || ' ' || SU.LAST_NAME AS NAME " +
            "                  FROM EMPLOYEE_INFO_ENTITY SI " +
            "                         JOIN LOS_TB_M_USERS SU on SI.USER_ID = SU.USER_ID) SUPERVISOR " +
            "         on PAL.SUPERVISOR_ID = SUPERVISOR.ID " +
            "       LEFT JOIN (SELECT MI.ID, MI.PIN, MU.FIRST_NAME || ' ' || MU.LAST_NAME AS NAME " +
            "                  FROM EMPLOYEE_INFO_ENTITY MI " +
            "                         JOIN LOS_TB_M_USERS MU on MI.USER_ID = MU.USER_ID) MANAGER on PAL.MANAGER_ID = MANAGER.ID " +
            "GROUP BY LADI.DEALER_PIN, " +
            "         LADI.DEALER_NAME, " +
            "         TEAM_LEAD.PIN, " +
            "         TEAM_LEAD.NAME, " +
            "         SUPERVISOR.PIN, " +
            "         SUPERVISOR.NAME, " +
            "         MANAGER.PIN, " +
            "         MANAGER.NAME " +
            "ORDER BY DEALER_PIN, TOTAL_OUTSTANDING DESC", nativeQuery = true)
    List<Tuple> findAutoDistributionSummary();


}
