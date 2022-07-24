package com.unisoft.customerloanprofile.followup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by
 * Created at 5 April 2021
 */

@Repository
@Transactional
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class FollowUpSummaryDao {

    private final EntityManager entityManager;

    /**
     * Generates summary for current month follow ups associated with a dealer
     *
     * Implemented by
     * At 05 April 2021
     * @param userPin
     * @return List of FollowUpSummaryModel
     */
   /* public List<FollowUpSummaryModel> getMonthlyFollowUpSummaryByDealer(String userPin) {
        String query = "" +
                "SELECT FR.FOLLOW_UP_REASON                                                       AS REASON, " +
                "       REPLACE('[' || " +
                "               RTRIM( " +
                "                 XMLAGG( " +
                "                   XMLELEMENT(e, '{' " +
                "                                   || '\"accountNo\":\"' || LABI.ACCOUNT_NO || '\", ' " +
                "                                   || '\"accountName\":\"' || LABI.ACCOUNT_NAME || '\", ' " +
                "                                   || '\"followupDate\":\"' || TO_CHAR(F.FOLLOW_UP_DATE, 'DD-MON-YYYY') || '\", ' " +
                "                                   || '\"followupTime\":\"' || TO_CHAR(TO_DATE(FOLLOW_UP_TIME, 'HH24:mi'), 'HH12:mi AM') || '\", ' " +
                "                                   || '\"reason\":\"' || FR.FOLLOW_UP_REASON || '\", ' " +
                "                                   || '\"dealerName\":\"' || U.FIRST_NAME || ' ' || U.LAST_NAME || '\", ' " +
                "                                   || '\"remarks\":\"' || F.FOLLOW_UP_REMARKS || '\" ' " +
                "                                   || '}', ',') " +
                "                       .extract('//text()') ORDER BY F.CREATED_DATE DESC) " +
                "                     .getclobval(), ', ') " +
                "                 || ']', '&quot;', '\"')    AS FOLLOW_UP_DATA, " +
                "       COUNT(F.CUSTOMER_ID)                                                      AS TOTAL_ACCOUNT, " +
                "       COALESCE(SUM(LADI.OUT_STANDING), 0)                                       AS OUTSTANDING_AMOUNT " +
                " " +
                "FROM FOLLOW_UP_ENTITY_FOLLOW_UP_REASON FR " +
                "       JOIN FOLLOW_UP_ENTITY F ON FR.FOLLOW_UP_ENTITY_ID = F.ID AND F.FOLLOW_UP_DATE >= TRUNC(SYSDATE, 'MONTH') " +
                "       JOIN CUSTOMER_BASIC_INFO_ENTITY c ON F.CUSTOMER_ID = c.ID " +
                "       JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON c.ACCOUNT_NO = LABI.ACCOUNT_NO " +
                "       JOIN LOAN_ACCOUNT_DISTRIBUTION_INFO LADI ON LADI.LOAN_ACCOUNT_BASIC_INFO_ID = LABI.ID " +
                "       JOIN LOS_TB_M_USERS U on U.USERNAME = LADI.DEALER_PIN " +
                "WHERE LADI.DEALER_PIN = :pin " +
                "  AND LADI.CREATED_DATE >= TRUNC(SYSDATE, 'MONTH') " +
                "  AND LADI.latest = '1' " +
                "GROUP BY FR.FOLLOW_UP_REASON";

        return getSummaryModels(query, userPin);
    }
*/

    public List<FollowUpSummaryModel> getMonthlyFollowUpSummaryByDealer(String userPin) {
        String query = "" +
                "SELECT FR.FOLLOW_UP_REASON                                                       AS REASON, " +
                "       REPLACE('[' || " +
                "               RTRIM( " +
                "                 XMLAGG( " +
                "                   XMLELEMENT(e, '{' " +
                "                                   || '\"accountNo\":\"' || LABI.ACCOUNT_NO || '\", ' " +
                "                                   || '\"accountName\":\"' || LABI.ACCOUNT_NAME || '\", ' " +
                "                                   || '\"followupDate\":\"' || TO_CHAR(F.FOLLOW_UP_DATE, 'DD-MON-YYYY') || '\", ' " +
                "                                   || '\"followupTime\":\"' || rtrim(TO_CHAR(FOLLOW_UP_TIME),'&quot;') || '\", ' " +
                "                                   || '\"reason\":\"' || FR.FOLLOW_UP_REASON || '\", ' " +
                "                                   || '\"dealerName\":\"' || U.FIRST_NAME || ' ' || U.LAST_NAME || '\", ' " +
                "                                   || '\"remarks\":\"' || F.FOLLOW_UP_REMARKS || '\" ' " +
                "                                   || '}', ',') " +
                "                       .extract('//text()') ORDER BY F.CREATED_DATE DESC) " +
                "                     .getclobval(), ', ') " +
                "                 || ']', '&quot;', '\"')    AS FOLLOW_UP_DATA, " +
                "       COUNT(F.CUSTOMER_ID)                                                      AS TOTAL_ACCOUNT, " +
                "       COALESCE(SUM(LADI.OUT_STANDING), 0)                                       AS OUTSTANDING_AMOUNT " +
                " " +
                "FROM FOLLOW_UP_ENTITY_FOLLOW_UP_REASON FR " +
                "       JOIN FOLLOW_UP_ENTITY F ON FR.FOLLOW_UP_ENTITY_ID = F.ID AND F.FOLLOW_UP_DATE >= TRUNC(SYSDATE, 'MONTH') " +
                "       JOIN CUSTOMER_BASIC_INFO_ENTITY c ON F.CUSTOMER_ID = c.ID " +
                "       JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON c.ACCOUNT_NO = LABI.ACCOUNT_NO " +
                "       JOIN LOAN_ACCOUNT_DISTRIBUTION_INFO LADI ON LADI.LOAN_ACCOUNT_BASIC_INFO_ID = LABI.ID " +
                "       JOIN LOS_TB_M_USERS U on U.USERNAME = LADI.DEALER_PIN " +
                "WHERE LADI.DEALER_PIN = :pin " +
                "  AND LADI.CREATED_DATE >= TRUNC(SYSDATE, 'MONTH') " +
                "  AND LADI.latest = '1' " +
                "GROUP BY FR.FOLLOW_UP_REASON";

        return getSummaryModels(query, userPin);
    }


    public List<FollowUpSummaryModel> getSamdMonthlyFollowUpSummaryByDealer(String userPin) {
        String query = "" +
                "SELECT FR.FOLLOW_UP_REASON                                                       AS REASON, " +
                "       REPLACE('[' || " +
                "               RTRIM( " +
                "                 XMLAGG( " +
                "                   XMLELEMENT(e, '{' " +
                "                                   || '\"accountNo\":\"' || LABI.ACCOUNT_NO || '\", ' " +
                "                                   || '\"accountName\":\"' || LABI.ACCOUNT_NAME || '\", ' " +
                "                                   || '\"followupDate\":\"' || TO_CHAR(F.FOLLOW_UP_DATE, 'DD-MON-YYYY') || '\", ' " +
                "                                   || '\"followupTime\":\"' || TO_CHAR(TO_DATE(FOLLOW_UP_TIME, 'HH24:mi'), 'HH12:mi AM') || '\", ' " +
                "                                   || '\"reason\":\"' || FR.FOLLOW_UP_REASON || '\", ' " +
                "                                   || '\"dealerName\":\"' || U.FIRST_NAME || ' ' || U.LAST_NAME || '\", ' " +
                "                                   || '\"remarks\":\"' || F.FOLLOW_UP_REMARKS || '\" ' " +
                "                                   || '}', ',') " +
                "                       .extract('//text()') ORDER BY F.CREATED_DATE DESC) " +
                "                     .getclobval(), ', ') " +
                "                 || ']', '&quot;', '\"')    AS FOLLOW_UP_DATA, " +
                "       COUNT(F.CUSTOMER_ID)                                                      AS TOTAL_ACCOUNT, " +
                "       COALESCE(SUM(LADI.OUT_STANDING), 0)                                       AS OUTSTANDING_AMOUNT " +
                " " +
                "FROM FOLLOW_UP_ENTITY_FOLLOW_UP_REASON FR " +
                "       JOIN FOLLOW_UP_ENTITY F ON FR.FOLLOW_UP_ENTITY_ID = F.ID AND F.FOLLOW_UP_DATE >= TRUNC(SYSDATE, 'MONTH') " +
                "       JOIN CUSTOMER_BASIC_INFO_ENTITY c ON F.CUSTOMER_ID = c.ID " +
                "       JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON c.ACCOUNT_NO = LABI.ACCOUNT_NO " +
                "       JOIN LMS_COLLECTION_SAM_LOAN_ACCOUNT_DISTRIBUTIONS LADI ON LADI.LOAN_ACCOUNT_BASIC_INFO_ID = LABI.ID " +
                "       JOIN LOS_TB_M_USERS U on U.USERNAME = LADI.DEALER_PIN " +
                "WHERE LADI.DEALER_PIN = :pin " +
                "  AND LADI.CREATED_DATE >= TRUNC(SYSDATE, 'MONTH') " +
                "  AND LADI.latest = '1' " +
                "GROUP BY FR.FOLLOW_UP_REASON";

        return getSummaryModels(query, userPin);
    }

    /**
     * Generates summary for current month follow ups associated with a supervisor
     *
     * Modified by
     * At 05 April 2021
     * @param userPin
     * @return List of FollowUpSummaryModel
     */
    public List<FollowUpSummaryModel> getMonthlyFollowUpSummaryBySupervisor(String userPin) {
        String query = "" +
                "SELECT FR.FOLLOW_UP_REASON                                                       AS REASON, " +
                "       REPLACE('[' || " +
                "               RTRIM( " +
                "                 XMLAGG( " +
                "                   XMLELEMENT(e, '{' " +
                "                                   || '\"accountNo\":\"' || LABI.ACCOUNT_NO || '\", ' " +
                "                                   || '\"accountName\":\"' || LABI.ACCOUNT_NAME || '\", ' " +
                "                                   || '\"followupDate\":\"' || TO_CHAR(F.FOLLOW_UP_DATE, 'DD-MON-YYYY') || '\", ' " +
                "                                   || '\"followupTime\":\"' || TO_CHAR(TO_DATE(FOLLOW_UP_TIME, 'HH24:mi'), 'HH12:mi AM') || '\", ' " +
                "                                   || '\"reason\":\"' || FR.FOLLOW_UP_REASON || '\", ' " +
                "                                   || '\"dealerName\":\"' || U.FIRST_NAME || ' ' || U.LAST_NAME || '\", ' " +
                "                                   || '\"remarks\":\"' || F.FOLLOW_UP_REMARKS || '\" ' " +
                "                                   || '}', ',') " +
                "                       .extract('//text()') ORDER BY F.CREATED_DATE DESC) " +
                "                     .getclobval(), ', ') " +
                "                 || ']', '&quot;', '\"')    AS FOLLOW_UP_DATA, " +
                "       COUNT(DISTINCT F.CUSTOMER_ID)                                             AS TOTAL_ACCOUNT, " +
                "       COALESCE(SUM(LADI.OUT_STANDING), 0)                                       AS OUTSTANDING_AMOUNT " +
                "FROM FOLLOW_UP_ENTITY_FOLLOW_UP_REASON FR " +
                "       JOIN FOLLOW_UP_ENTITY F ON FR.FOLLOW_UP_ENTITY_ID = F.ID AND F.FOLLOW_UP_DATE >= TRUNC(SYSDATE, 'MONTH') " +
                "       JOIN CUSTOMER_BASIC_INFO_ENTITY C ON F.CUSTOMER_ID = C.ID " +
                "       JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON C.ACCOUNT_NO = LABI.ACCOUNT_NO " +
                "       JOIN LOAN_ACCOUNT_DISTRIBUTION_INFO LADI ON LADI.LOAN_ACCOUNT_BASIC_INFO_ID = LABI.ID " +
                "       JOIN EMPLOYEE_INFO_ENTITY EMP ON EMP.PIN = LADI.DEALER_PIN " +
                "       JOIN PEOPLE_ALLOCATION_LOGIC PAL ON PAL.DEALER_ID = EMP.ID " +
                "       JOIN EMPLOYEE_INFO_ENTITY EMP ON EMP.PIN = LADI.DEALER_PIN " +
                "       JOIN EMPLOYEE_INFO_ENTITY ESP on ESP.ID = PAL.SUPERVISOR_ID " +
                "       JOIN LOS_TB_M_USERS U on U.USERNAME = LADI.DEALER_PIN " +
                "WHERE ESP.PIN = :pin " +
                "  AND LADI.CREATED_DATE >= TRUNC(SYSDATE, 'MONTH') " +
                "  AND LADI.latest = '1' " +
                "GROUP BY FR.FOLLOW_UP_REASON";

        return getSummaryModels(query, userPin);
    }

    public List<FollowUpSummaryModel> getMonthlyCardFollowUpSummaryBySupervisor(String userPin) {
        String query = "" +
                "SELECT FR.FOLLOW_UP_REASON                                                       AS REASON, " +
                "       REPLACE('[' || " +
                "               RTRIM( " +
                "                 XMLAGG( " +
                "                   XMLELEMENT(e, '{' " +
                "                                   || '\"accountNo\":\"' || LABI.CONTRACT_ID || '\", ' " +
                "                                   || '\"accountName\":\"' || LABI.CARD_NAME || '\", ' " +
                "                                   || '\"followupDate\":\"' || TO_CHAR(F.FOLLOW_UP_DATE, 'DD-MON-YYYY') || '\", ' " +
                "                                   || '\"followupTime\":\"' || TO_CHAR(TO_DATE(FOLLOW_UP_TIME, 'HH24:mi'), 'HH12:mi AM') || '\", ' " +
                "                                   || '\"reason\":\"' || FR.FOLLOW_UP_REASON || '\", ' " +
                "                                   || '\"dealerName\":\"' || U.FIRST_NAME || ' ' || U.LAST_NAME || '\", ' " +
                "                                   || '\"remarks\":\"' || F.FOLLOW_UP_REMARKS || '\" ' " +
                "                                   || '}', ',') " +
                "                       .extract('//text()') ORDER BY F.CREATED_DATE DESC) " +
                "                     .getclobval(), ', ') " +
                "                 || ']', '&quot;', '\"')    AS FOLLOW_UP_DATA, " +
                "       COUNT(DISTINCT F.CUSTOMER_ID)                                             AS TOTAL_ACCOUNT, " +
                "       COALESCE(SUM(LADI.OUT_STANDING), 0)                                       AS OUTSTANDING_AMOUNT " +
                "FROM CARD_FOLLOW_UP_FOLLOW_UP_REASON FR " +
                "       JOIN CARD_FOLLOW_UP F ON FR.CARD_FOLLOW_UP_ID = F.ID AND F.FOLLOW_UP_DATE >= TRUNC(SYSDATE, 'MONTH') " +
                "       JOIN CUSTOMER_BASIC_INFO_ENTITY C ON F.CUSTOMER_ID = C.ID " +
                "       JOIN CARD_ACCOUNT_BASIC_INFO LABI ON C.ACCOUNT_NO = LABI.CONTRACT_ID " +
                "       JOIN CARD_ACCOUNT_DISTRIBUTION_INFO LADI ON LADI.CARD_ACCOUNT_BASIC_INFO_ID = LABI.ID " +
                "       JOIN EMPLOYEE_INFO_ENTITY EMP ON EMP.PIN = LADI.DEALER_PIN " +
                "       JOIN PEOPLE_ALLOCATION_LOGIC PAL ON PAL.DEALER_ID = EMP.ID " +
                "       JOIN EMPLOYEE_INFO_ENTITY EMP ON EMP.PIN = LADI.DEALER_PIN " +
                "       JOIN EMPLOYEE_INFO_ENTITY ESP on ESP.ID = PAL.SUPERVISOR_ID " +
                "       JOIN LOS_TB_M_USERS U on U.USERNAME = LADI.DEALER_PIN " +
                "    WHERE ESP.PIN = :pin " +
                "      AND LADI.CREATED_DATE >= TRUNC(SYSDATE, 'MONTH') " +
                "      AND LADI.latest = '1' " +

                "GROUP BY FR.FOLLOW_UP_REASON";

        return getSummaryModels(query, userPin);
    }

    public List<FollowUpSummaryModel> getSamdMonthlyFollowUpSummaryBySupervisor(String userPin) {
        String query = "" +
                "SELECT FR.FOLLOW_UP_REASON                                                       AS REASON, " +
                "       REPLACE('[' || " +
                "               RTRIM( " +
                "                 XMLAGG( " +
                "                   XMLELEMENT(e, '{' " +
                "                                   || '\"accountNo\":\"' || LABI.ACCOUNT_NO || '\", ' " +
                "                                   || '\"accountName\":\"' || LABI.ACCOUNT_NAME || '\", ' " +
                "                                   || '\"followupDate\":\"' || TO_CHAR(F.FOLLOW_UP_DATE, 'DD-MON-YYYY') || '\", ' " +
                "                                   || '\"followupTime\":\"' || TO_CHAR(TO_DATE(FOLLOW_UP_TIME, 'HH24:mi'), 'HH12:mi AM') || '\", ' " +
                "                                   || '\"reason\":\"' || FR.FOLLOW_UP_REASON || '\", ' " +
                "                                   || '\"dealerName\":\"' || U.FIRST_NAME || ' ' || U.LAST_NAME || '\", ' " +
                "                                   || '\"remarks\":\"' || F.FOLLOW_UP_REMARKS || '\" ' " +
                "                                   || '}', ',') " +
                "                       .extract('//text()') ORDER BY F.CREATED_DATE DESC) " +
                "                     .getclobval(), ', ') " +
                "                 || ']', '&quot;', '\"')    AS FOLLOW_UP_DATA, " +
                "       COUNT(DISTINCT F.CUSTOMER_ID)                                             AS TOTAL_ACCOUNT, " +
                "       COALESCE(SUM(LADI.OUT_STANDING), 0)                                       AS OUTSTANDING_AMOUNT " +
                "FROM FOLLOW_UP_ENTITY_FOLLOW_UP_REASON FR " +
                "       JOIN FOLLOW_UP_ENTITY F ON FR.FOLLOW_UP_ENTITY_ID = F.ID AND F.FOLLOW_UP_DATE >= TRUNC(SYSDATE, 'MONTH') " +
                "       JOIN CUSTOMER_BASIC_INFO_ENTITY C ON F.CUSTOMER_ID = C.ID " +
                "       JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON C.ACCOUNT_NO = LABI.ACCOUNT_NO " +
                "       JOIN LMS_COLLECTION_SAM_LOAN_ACCOUNT_DISTRIBUTIONS LADI ON LADI.LOAN_ACCOUNT_BASIC_INFO_ID = LABI.ID " +
                "       JOIN EMPLOYEE_INFO_ENTITY EMP ON EMP.PIN = LADI.DEALER_PIN " +
                "       JOIN PEOPLE_ALLOCATION_LOGIC PAL ON PAL.DEALER_ID = EMP.ID " +
                "       JOIN EMPLOYEE_INFO_ENTITY EMP ON EMP.PIN = LADI.DEALER_PIN " +
                "       JOIN EMPLOYEE_INFO_ENTITY ESP on ESP.ID = PAL.SUPERVISOR_ID " +
                "       JOIN LOS_TB_M_USERS U on U.USERNAME = LADI.DEALER_PIN " +
                "WHERE ESP.PIN = :pin " +
                "  AND LADI.CREATED_DATE >= TRUNC(SYSDATE, 'MONTH') " +
                "  AND LADI.latest = '1' " +
                "GROUP BY FR.FOLLOW_UP_REASON";

        return getSummaryModels(query, userPin);
    }

    /**
     * Generates summary for current month follow ups associated with a team leader
     *
     * Modified by
     * At 05 April 2021
     * @param userPin
     * @return List of FollowUpSummaryModel
     */
   /* public List<FollowUpSummaryModel> getMonthlyFollowUpSummaryByTeamLeader(String userPin) {
        String query = "" +
                "SELECT FR.FOLLOW_UP_REASON                                                       AS REASON, " +
                "       REPLACE('[' || " +
                "               RTRIM( " +
                "                 XMLAGG( " +
                "                   XMLELEMENT(e, '{' " +
                "                                   || '\"accountNo\":\"' || LABI.ACCOUNT_NO || '\", ' " +
                "                                   || '\"accountName\":\"' || LABI.ACCOUNT_NAME || '\", ' " +
                "                                   || '\"followupDate\":\"' || TO_CHAR(F.FOLLOW_UP_DATE, 'DD-MON-YYYY') || '\", ' " +
                "                                   || '\"followupTime\":\"' || TO_CHAR(TO_DATE(FOLLOW_UP_TIME, 'HH24:mi'), 'HH12:mi AM') || '\", ' " +
                "                                   || '\"reason\":\"' || FR.FOLLOW_UP_REASON || '\", ' " +
                "                                   || '\"dealerName\":\"' || U.FIRST_NAME || ' ' || U.LAST_NAME || '\", ' " +
                "                                   || '\"remarks\":\"' || F.FOLLOW_UP_REMARKS || '\" ' " +
                "                                   || '}', ',') " +
                "                       .extract('//text()') ORDER BY F.CREATED_DATE DESC) " +
                "                     .getclobval(), ', ') " +
                "                 || ']', '&quot;', '\"')    AS FOLLOW_UP_DATA, " +
                "       COUNT(DISTINCT F.CUSTOMER_ID)                                             AS TOTAL_ACCOUNT, " +
                "       COALESCE(SUM(LADI.OUT_STANDING), 0)                                       AS OUTSTANDING_AMOUNT " +
                "FROM FOLLOW_UP_ENTITY_FOLLOW_UP_REASON FR " +
                "       JOIN FOLLOW_UP_ENTITY F ON FR.FOLLOW_UP_ENTITY_ID = F.ID AND F.FOLLOW_UP_DATE >= TRUNC(SYSDATE, 'MONTH') " +
                "       JOIN CUSTOMER_BASIC_INFO_ENTITY C ON F.CUSTOMER_ID = C.ID " +
                "       JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON C.ACCOUNT_NO = LABI.ACCOUNT_NO " +
                "       JOIN LOAN_ACCOUNT_DISTRIBUTION_INFO LADI ON LADI.LOAN_ACCOUNT_BASIC_INFO_ID = LABI.ID " +
                "       JOIN EMPLOYEE_INFO_ENTITY EMP ON EMP.PIN = LADI.DEALER_PIN " +
                "       JOIN PEOPLE_ALLOCATION_LOGIC PAL ON PAL.DEALER_ID = EMP.ID " +
                "       JOIN EMPLOYEE_INFO_ENTITY EMP ON EMP.PIN = LADI.DEALER_PIN " +
                "       JOIN EMPLOYEE_INFO_ENTITY ESP on ESP.ID = PAL.TEAM_LEAD_ID " +
                "       JOIN LOS_TB_M_USERS U on U.USERNAME = LADI.DEALER_PIN " +
                "WHERE ESP.PIN = :pin " +
                "  AND LADI.CREATED_DATE >= TRUNC(SYSDATE, 'MONTH') " +
                "  AND LADI.latest = '1' " +
                "GROUP BY FR.FOLLOW_UP_REASON";

        return getSummaryModels(query, userPin);
    }*/


    public List<FollowUpSummaryModel> getMonthlyFollowUpSummaryByTeamLeader(String userPin) {
        String query = "" +
                "SELECT FR.FOLLOW_UP_REASON                                                       AS REASON, " +
                "       REPLACE('[' || " +
                "               RTRIM( " +
                "                 XMLAGG( " +
                "                   XMLELEMENT(e, '{' " +
                "                                   || '\"accountNo\":\"' || LABI.ACCOUNT_NO || '\", ' " +
                "                                   || '\"accountName\":\"' || LABI.ACCOUNT_NAME || '\", ' " +
                "                                   || '\"followupDate\":\"' || TO_CHAR(F.FOLLOW_UP_DATE, 'DD-MON-YYYY') || '\", ' " +
                "                                   || '\"followupTime\":\"' || TO_CHAR(FOLLOW_UP_TIME) || '\", ' " +
                "                                   || '\"reason\":\"' || FR.FOLLOW_UP_REASON || '\", ' " +
                "                                   || '\"dealerName\":\"' || U.FIRST_NAME || ' ' || U.LAST_NAME || '\", ' " +
                "                                   || '\"remarks\":\"' || F.FOLLOW_UP_REMARKS || '\" ' " +
                "                                   || '}', ',') " +
                "                       .extract('//text()') ORDER BY F.CREATED_DATE DESC) " +
                "                     .getclobval(), ', ') " +
                "                 || ']', '&quot;', '\"')    AS FOLLOW_UP_DATA, " +
                "       COUNT( F.CUSTOMER_ID)                                             AS TOTAL_ACCOUNT, " +
                "       COALESCE(SUM(LADI.OUT_STANDING), 0)                                       AS OUTSTANDING_AMOUNT " +
                "FROM FOLLOW_UP_ENTITY_FOLLOW_UP_REASON FR " +
                "       JOIN FOLLOW_UP_ENTITY F ON FR.FOLLOW_UP_ENTITY_ID = F.ID AND F.FOLLOW_UP_DATE >= TRUNC(SYSDATE, 'MONTH') " +
                "       JOIN CUSTOMER_BASIC_INFO_ENTITY C ON F.CUSTOMER_ID = C.ID " +
                "       JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON C.ACCOUNT_NO = LABI.ACCOUNT_NO " +
                "       JOIN LOAN_ACCOUNT_DISTRIBUTION_INFO LADI ON LADI.LOAN_ACCOUNT_BASIC_INFO_ID = LABI.ID " +
                "       JOIN EMPLOYEE_INFO_ENTITY EMP ON EMP.PIN = LADI.DEALER_PIN " +
                "       JOIN PEOPLE_ALLOCATION_LOGIC PAL ON PAL.DEALER_ID = EMP.ID " +
                "       JOIN EMPLOYEE_INFO_ENTITY EMP ON EMP.PIN = LADI.DEALER_PIN " +
                "       JOIN EMPLOYEE_INFO_ENTITY ESP on ESP.ID = PAL.TEAM_LEAD_ID " +
                "       JOIN LOS_TB_M_USERS U on U.USERNAME = LADI.DEALER_PIN " +
                "WHERE ESP.PIN = :pin " +
                "  AND LADI.CREATED_DATE >= TRUNC(SYSDATE, 'MONTH') " +
                "  AND LADI.latest = '1' " +
                "GROUP BY FR.FOLLOW_UP_REASON";

        return getSummaryModels(query, userPin);
    }

    public List<FollowUpSummaryModel> getSamdMonthlyFollowUpSummaryByTeamLeader(String userPin) {
        String query = "" +
                "SELECT FR.FOLLOW_UP_REASON                                                       AS REASON, " +
                "       REPLACE('[' || " +
                "               RTRIM( " +
                "                 XMLAGG( " +
                "                   XMLELEMENT(e, '{' " +
                "                                   || '\"accountNo\":\"' || LABI.ACCOUNT_NO || '\", ' " +
                "                                   || '\"accountName\":\"' || LABI.ACCOUNT_NAME || '\", ' " +
                "                                   || '\"followupDate\":\"' || TO_CHAR(F.FOLLOW_UP_DATE, 'DD-MON-YYYY') || '\", ' " +
                "                                   || '\"followupTime\":\"' || TO_CHAR(TO_DATE(FOLLOW_UP_TIME, 'HH24:mi'), 'HH12:mi AM') || '\", ' " +
                "                                   || '\"reason\":\"' || FR.FOLLOW_UP_REASON || '\", ' " +
                "                                   || '\"dealerName\":\"' || U.FIRST_NAME || ' ' || U.LAST_NAME || '\", ' " +
                "                                   || '\"remarks\":\"' || F.FOLLOW_UP_REMARKS || '\" ' " +
                "                                   || '}', ',') " +
                "                       .extract('//text()') ORDER BY F.CREATED_DATE DESC) " +
                "                     .getclobval(), ', ') " +
                "                 || ']', '&quot;', '\"')    AS FOLLOW_UP_DATA, " +
                "       COUNT(DISTINCT F.CUSTOMER_ID)                                             AS TOTAL_ACCOUNT, " +
                "       COALESCE(SUM(LADI.OUT_STANDING), 0)                                       AS OUTSTANDING_AMOUNT " +
                "FROM FOLLOW_UP_ENTITY_FOLLOW_UP_REASON FR " +
                "       JOIN FOLLOW_UP_ENTITY F ON FR.FOLLOW_UP_ENTITY_ID = F.ID AND F.FOLLOW_UP_DATE >= TRUNC(SYSDATE, 'MONTH') " +
                "       JOIN CUSTOMER_BASIC_INFO_ENTITY C ON F.CUSTOMER_ID = C.ID " +
                "       JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON C.ACCOUNT_NO = LABI.ACCOUNT_NO " +
                "       JOIN LMS_COLLECTION_SAM_LOAN_ACCOUNT_DISTRIBUTIONS LADI ON LADI.LOAN_ACCOUNT_BASIC_INFO_ID = LABI.ID " +
                "       JOIN EMPLOYEE_INFO_ENTITY EMP ON EMP.PIN = LADI.DEALER_PIN " +
                "       JOIN PEOPLE_ALLOCATION_LOGIC PAL ON PAL.DEALER_ID = EMP.ID " +
                "       JOIN EMPLOYEE_INFO_ENTITY EMP ON EMP.PIN = LADI.DEALER_PIN " +
                "       JOIN EMPLOYEE_INFO_ENTITY ESP on ESP.ID = PAL.TEAM_LEAD_ID " +
                "       JOIN LOS_TB_M_USERS U on U.USERNAME = LADI.DEALER_PIN " +
                "WHERE ESP.PIN = :pin " +
                "  AND LADI.CREATED_DATE >= TRUNC(SYSDATE, 'MONTH') " +
                "  AND LADI.latest = '1' " +
                "GROUP BY FR.FOLLOW_UP_REASON";

        return getSummaryModels(query, userPin);
    }

    /**
     * Generates summary for current month follow ups associated with a manager
     *
     * Modified by
     * At 05 April 2021
     * @param userPin
     * @return List of FollowUpSummaryModel
     */
    /*public List<FollowUpSummaryModel> getMonthlyFollowUpSummaryByManager(String userPin) {
        String query = "" +
                "SELECT FR.FOLLOW_UP_REASON                                                       AS REASON, " +
                "       REPLACE('[' || " +
                "               RTRIM( " +
                "                 XMLAGG( " +
                "                   XMLELEMENT(e, '{' " +
                "                                   || '\"accountNo\":\"' || LABI.ACCOUNT_NO || '\", ' " +
                "                                   || '\"accountName\":\"' || LABI.ACCOUNT_NAME || '\", ' " +
                "                                   || '\"followupDate\":\"' || TO_CHAR(F.FOLLOW_UP_DATE, 'DD-MON-YYYY') || '\", ' " +
                "                                   || '\"followupTime\":\"' || TO_CHAR(TO_DATE(FOLLOW_UP_TIME, 'HH24:mi'), 'HH12:mi AM') || '\", ' " +
                "                                   || '\"reason\":\"' || FR.FOLLOW_UP_REASON || '\", ' " +
                "                                   || '\"dealerName\":\"' || U.FIRST_NAME || ' ' || U.LAST_NAME || '\", ' " +
                "                                   || '\"remarks\":\"' || F.FOLLOW_UP_REMARKS || '\" ' " +
                "                                   || '}', ',') " +
                "                       .extract('//text()') ORDER BY F.CREATED_DATE DESC) " +
                "                     .getclobval(), ', ') " +
                "                 || ']', '&quot;', '\"')    AS FOLLOW_UP_DATA, " +
                "       COUNT(DISTINCT F.CUSTOMER_ID)                                             AS TOTAL_ACCOUNT, " +
                "       COALESCE(SUM(LADI.OUT_STANDING), 0)                                       AS OUTSTANDING_AMOUNT " +
                "FROM FOLLOW_UP_ENTITY_FOLLOW_UP_REASON FR " +
                "       JOIN FOLLOW_UP_ENTITY F ON FR.FOLLOW_UP_ENTITY_ID = F.ID AND F.FOLLOW_UP_DATE >= TRUNC(SYSDATE, 'MONTH') " +
                "       JOIN CUSTOMER_BASIC_INFO_ENTITY C ON F.CUSTOMER_ID = C.ID " +
                "       JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON C.ACCOUNT_NO = LABI.ACCOUNT_NO " +
                "       JOIN LOAN_ACCOUNT_DISTRIBUTION_INFO LADI ON LADI.LOAN_ACCOUNT_BASIC_INFO_ID = LABI.ID " +
                "       JOIN EMPLOYEE_INFO_ENTITY EMP ON EMP.PIN = LADI.DEALER_PIN " +
                "       JOIN PEOPLE_ALLOCATION_LOGIC PAL ON PAL.DEALER_ID = EMP.ID " +
                "       JOIN EMPLOYEE_INFO_ENTITY EMP ON EMP.PIN = LADI.DEALER_PIN " +
                "       JOIN EMPLOYEE_INFO_ENTITY ESP on ESP.ID = PAL.MANAGER_ID " +
                "       JOIN LOS_TB_M_USERS U on U.USERNAME = LADI.DEALER_PIN " +
                "WHERE ESP.PIN = :pin " +
                "  AND LADI.CREATED_DATE >= TRUNC(SYSDATE, 'MONTH') " +
                "  AND LADI.latest = '1' " +
                "GROUP BY FR.FOLLOW_UP_REASON";

        return getSummaryModels(query, userPin);
    }
*/

    public List<FollowUpSummaryModel> getMonthlyFollowUpSummaryByManager(String userPin) {
        String query = "" +
                "SELECT FR.FOLLOW_UP_REASON                                                       AS REASON, " +
                "       REPLACE('[' || " +
                "               RTRIM( " +
                "                 XMLAGG( " +
                "                   XMLELEMENT(e, '{' " +
                "                                   || '\"accountNo\":\"' || LABI.ACCOUNT_NO || '\", ' " +
                "                                   || '\"accountName\":\"' || LABI.ACCOUNT_NAME || '\", ' " +
                "                                   || '\"followupDate\":\"' || TO_CHAR(F.FOLLOW_UP_DATE, 'DD-MON-YYYY') || '\", ' " +
                "                                   || '\"followupTime\":\"' || TO_CHAR(F.FOLLOW_UP_TIME) || '\", ' " +
                "                                   || '\"reason\":\"' || FR.FOLLOW_UP_REASON || '\", ' " +
                "                                   || '\"dealerName\":\"' || U.FIRST_NAME || ' ' || U.LAST_NAME || '\", ' " +
                "                                   || '\"remarks\":\"' || F.FOLLOW_UP_REMARKS || '\" ' " +
                "                                   || '}', ',') " +
                "                       .extract('//text()') ORDER BY F.CREATED_DATE DESC) " +
                "                     .getclobval(), ', ') " +
                "                 || ']', '&quot;', '\"')    AS FOLLOW_UP_DATA, " +
                "       COUNT(DISTINCT F.CUSTOMER_ID)                                             AS TOTAL_ACCOUNT, " +
                "       COALESCE(SUM(LADI.OUT_STANDING), 0)                                       AS OUTSTANDING_AMOUNT " +
                "FROM FOLLOW_UP_ENTITY_FOLLOW_UP_REASON FR " +
                "       JOIN FOLLOW_UP_ENTITY F ON FR.FOLLOW_UP_ENTITY_ID = F.ID AND F.FOLLOW_UP_DATE >= TRUNC(SYSDATE, 'MONTH') " +
                "       JOIN CUSTOMER_BASIC_INFO_ENTITY C ON F.CUSTOMER_ID = C.ID " +
                "       JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON C.ACCOUNT_NO = LABI.ACCOUNT_NO " +
                "       JOIN LOAN_ACCOUNT_DISTRIBUTION_INFO LADI ON LADI.LOAN_ACCOUNT_BASIC_INFO_ID = LABI.ID " +
                "       JOIN EMPLOYEE_INFO_ENTITY EMP ON EMP.PIN = LADI.DEALER_PIN " +
                "       JOIN PEOPLE_ALLOCATION_LOGIC PAL ON PAL.DEALER_ID = EMP.ID " +
                "       JOIN EMPLOYEE_INFO_ENTITY EMP ON EMP.PIN = LADI.DEALER_PIN " +
                "       JOIN EMPLOYEE_INFO_ENTITY ESP on ESP.ID = PAL.MANAGER_ID " +
                "       JOIN LOS_TB_M_USERS U on U.USERNAME = LADI.DEALER_PIN " +
                "WHERE ESP.PIN = :pin " +
                "  AND LADI.CREATED_DATE >= TRUNC(SYSDATE, 'MONTH') " +
                "  AND LADI.latest = '1' " +
                "GROUP BY FR.FOLLOW_UP_REASON";

        return getSummaryModels(query, userPin);
    }

    public List<FollowUpSummaryModel> getSamdMonthlyFollowUpSummaryByManager(String userPin) {
        String query = "" +
                "SELECT FR.FOLLOW_UP_REASON                                                       AS REASON, " +
                "       REPLACE('[' || " +
                "               RTRIM( " +
                "                 XMLAGG( " +
                "                   XMLELEMENT(e, '{' " +
                "                                   || '\"accountNo\":\"' || LABI.ACCOUNT_NO || '\", ' " +
                "                                   || '\"accountName\":\"' || LABI.ACCOUNT_NAME || '\", ' " +
                "                                   || '\"followupDate\":\"' || TO_CHAR(F.FOLLOW_UP_DATE, 'DD-MON-YYYY') || '\", ' " +
                "                                   || '\"followupTime\":\"' || TO_CHAR(TO_DATE(FOLLOW_UP_TIME, 'HH24:mi'), 'HH12:mi AM') || '\", ' " +
                "                                   || '\"reason\":\"' || FR.FOLLOW_UP_REASON || '\", ' " +
                "                                   || '\"dealerName\":\"' || U.FIRST_NAME || ' ' || U.LAST_NAME || '\", ' " +
                "                                   || '\"remarks\":\"' || F.FOLLOW_UP_REMARKS || '\" ' " +
                "                                   || '}', ',') " +
                "                       .extract('//text()') ORDER BY F.CREATED_DATE DESC) " +
                "                     .getclobval(), ', ') " +
                "                 || ']', '&quot;', '\"')    AS FOLLOW_UP_DATA, " +
                "       COUNT(DISTINCT F.CUSTOMER_ID)                                             AS TOTAL_ACCOUNT, " +
                "       COALESCE(SUM(LADI.OUT_STANDING), 0)                                       AS OUTSTANDING_AMOUNT " +
                "FROM FOLLOW_UP_ENTITY_FOLLOW_UP_REASON FR " +
                "       JOIN FOLLOW_UP_ENTITY F ON FR.FOLLOW_UP_ENTITY_ID = F.ID AND F.FOLLOW_UP_DATE >= TRUNC(SYSDATE, 'MONTH') " +
                "       JOIN CUSTOMER_BASIC_INFO_ENTITY C ON F.CUSTOMER_ID = C.ID " +
                "       JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON C.ACCOUNT_NO = LABI.ACCOUNT_NO " +
                "       JOIN LMS_COLLECTION_SAM_LOAN_ACCOUNT_DISTRIBUTIONS LADI ON LADI.LOAN_ACCOUNT_BASIC_INFO_ID = LABI.ID " +
                "       JOIN EMPLOYEE_INFO_ENTITY EMP ON EMP.PIN = LADI.DEALER_PIN " +
                "       JOIN PEOPLE_ALLOCATION_LOGIC PAL ON PAL.DEALER_ID = EMP.ID " +
                "       JOIN EMPLOYEE_INFO_ENTITY EMP ON EMP.PIN = LADI.DEALER_PIN " +
                "       JOIN EMPLOYEE_INFO_ENTITY ESP on ESP.ID = PAL.MANAGER_ID " +
                "       JOIN LOS_TB_M_USERS U on U.USERNAME = LADI.DEALER_PIN " +
                "WHERE ESP.PIN = :pin " +
                "  AND LADI.CREATED_DATE >= TRUNC(SYSDATE, 'MONTH') " +
                "  AND LADI.latest = '1' " +
                "GROUP BY FR.FOLLOW_UP_REASON";

        return getSummaryModels(query, userPin);
    }


//    Loan part starts here

    public List<FollowUpSummaryModel> getMonthlyCardFollowUpSummaryByDealer(String userPin) {
        String query = "" +
                "SELECT FR.FOLLOW_UP_REASON                                                       AS REASON, " +
                "       REPLACE('[' || " +
                "               RTRIM( " +
                "                 XMLAGG( " +
                "                   XMLELEMENT(e, '{' " +
                "                                   || '\"accountNo\":\"' || CABI.CARD_NO || '\", ' " +
                "                                   || '\"accountName\":\"' || CABI.CONTRACT_ID || '\", ' " +
                "                                   || '\"followupDate\":\"' || TO_CHAR(F.FOLLOW_UP_DATE, 'DD-MON-YYYY') || '\", ' " +
                "                                   || '\"followupTime\":\"' || TO_CHAR(FOLLOW_UP_TIME) || '\", ' " +
                "                                   || '\"reason\":\"' || FR.FOLLOW_UP_REASON || '\", ' " +
                "                                   || '\"dealerName\":\"' || U.FIRST_NAME || ' ' || U.LAST_NAME || '\", ' " +
                "                                   || '\"remarks\":\"' || F.FOLLOW_UP_REMARKS || '\" ' " +
                "                                   || '}', ',') " +
                "                       .extract('//text()') ORDER BY F.CREATED_DATE DESC) " +
                "                     .getclobval(), ', ') " +
                "                 || ']', '&quot;', '\"')    AS FOLLOW_UP_DATA, " +
                "       COUNT(F.CUSTOMER_ID)                                                      AS TOTAL_ACCOUNT, " +
                "       COALESCE(SUM(CADI.OUTSTANDING_AMOUNT), 0)                                       AS OUTSTANDING_AMOUNT " +
                " " +
                "FROM CARD_FOLLOW_UP_FOLLOW_UP_REASON FR " +
                "       JOIN CARD_FOLLOW_UP F ON FR.CARD_FOLLOW_UP_ID = F.ID AND F.FOLLOW_UP_DATE >= TRUNC(SYSDATE, 'MONTH') " +
                "       JOIN CUSTOMER_BASIC_INFO_ENTITY c ON F.CUSTOMER_ID = c.ID " +
                "       JOIN CARD_ACCOUNT_BASIC_INFO CABI ON c.ACCOUNT_NO = CABI.CONTRACT_ID " +
                "       JOIN CARD_ACCOUNT_DISTRIBUTION_INFO CADI ON CADI.CARD_ACCOUNT_BASIC_INFO_ID = CABI.ID " +
                "       JOIN LOS_TB_M_USERS U on U.USERNAME = CADI.DEALER_PIN " +
                "WHERE CADI.DEALER_PIN = :pin " +
                "  AND CADI.CREATED_DATE >= TRUNC(SYSDATE, 'MONTH') " +
                "  AND CADI.latest = '1' " +
                "GROUP BY FR.FOLLOW_UP_REASON";

        return getSummaryModels(query, userPin);
    }




    private List<FollowUpSummaryModel> getSummaryModels(String query, String userPin) {
        List<FollowUpSummaryModel> summaryModels = new ArrayList<>();
        try {
            List<Tuple> dataModels = entityManager.createNativeQuery(query, Tuple.class)
                    .setParameter("pin", userPin)
                    .unwrap(org.hibernate.query.NativeQuery.class)
                    .getResultList();

            summaryModels = dataModels.stream().map(FollowUpSummaryModel::new).collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return summaryModels;

    }


}
