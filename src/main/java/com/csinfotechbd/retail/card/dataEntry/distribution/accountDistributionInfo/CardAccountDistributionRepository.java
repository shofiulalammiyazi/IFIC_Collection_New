package com.csinfotechbd.retail.card.dataEntry.distribution.accountDistributionInfo;
/*
Created by Monirul Islam at 7/29/2019
*/

import com.csinfotechbd.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import com.csinfotechbd.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Repository
public interface CardAccountDistributionRepository extends JpaRepository<CardAccountDistributionInfo, Long> {

    @Query("SELECT d " +
            "FROM CardAccountDistributionInfo d " +
            "WHERE d.dealerPin IN(:pin) AND d.enabled = true AND d.createdDate BETWEEN :startDate AND :endDate")
    List<CardAccountDistributionInfo> findDistributionByUserPinAndDateRange(
            @Param("pin") List<String> dealerPin,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

//    @Query(value = "" + "WITH CPCM AS (SELECT CONTRACT_ID, SUM(PAYMENT_AMOUNT) CURRENT_MONTH_PAYMENT " +
//            "                              FROM CARD_PAYMENT " +
//            "                              WHERE PAYMENT_DATE BETWEEN :startDate AND :endDate " +
//            "                              GROUP BY CONTRACT_ID) , " +
//            "                 CPLD AS (SELECT * " +
//            "                              FROM (SELECT CONTRACT_ID, PAYMENT_DATE, PAYMENT_AMOUNT, ROW_NUMBER() " +
//            "                              OVER (PARTITION BY CONTRACT_ID ORDER BY PAYMENT_DATE DESC) AS ROWNUMBER " +
//            "                              FROM CARD_PAYMENT WHERE PAYMENT_DATE >= :startDate ) " +
//            "                              WHERE ROWNUMBER = 1 " +
//            "                         ) , " +
//            "                   FU AS (SELECT CUSTOMER_ID, MIN(FOLLOW_UP_DATE) FOLLOW_UP_DATE " +
//            "                              FROM CARD_FOLLOW_UP " +
//            "                              WHERE TRUNC(FOLLOW_UP_DATE, 'DD') >= TRUNC(SYSDATE, 'DD') " +
//            "                              GROUP BY CUSTOMER_ID " +
//            "                          ) , " +
//            "                  VLE AS (SELECT ACCOUNT_CARD_NUMBER, COUNT(ID) VISIT_COUNT " +
//            "                              FROM VISIT_LEDGER_ENTITY " +
//            "                              WHERE CREATED_DATE BETWEEN :startDate AND :endDate " +
//            "                              GROUP BY ACCOUNT_CARD_NUMBER" +
//            "                          ) , " +
//            "                   CI AS (SELECT CUSTOMER_ID , " +
//            "                                     COUNT(ATTEMPT)                                               TOTAL_CONTACT, " +
//            "                                     COUNT(CASE WHEN LOWER(CATEGORY) = 'right party' THEN 1 END)  RIGHT_PARTY_COUNT, " +
//            "                                     COUNT(CASE WHEN LOWER(CATEGORY) != 'right party' THEN 1 END) OTHER_PARTY_COUNT " +
//            "                              FROM CONTACT_INFO " +
//            "                              WHERE PIN = :pin AND CREATED_DATE BETWEEN :startDate AND :endDate " +
//            "                              GROUP BY CUSTOMER_ID" +
//            "                          ) , " +
//            "                 CPTP AS (SELECT CUSTOMER_ID, " +
//            "                                     COUNT(CARD_PTP_STATUS)                                        TOTAL_COUNT, " +
//            "                                     COUNT(CASE WHEN LOWER(CARD_PTP_STATUS) = 'cured' THEN 1 END)  CURED_COUNT, " +
//            "                                     COUNT(CASE WHEN LOWER(CARD_PTP_STATUS) = 'broken' THEN 1 END) BROKEN_COUNT " +
//            "                              FROM CARD_PTP " +
//            "                              WHERE CREATED_DATE BETWEEN :startDate AND :endDate " +
//            "                              GROUP BY CUSTOMER_ID" +
//            "                          ) " +
//            "SELECT DISTINCT CABI.CONTRACT_ID                                                AS accountNo, " +
//            "                CABI.CLIENT_ID                                                  AS clientId, " +
//
//            "                CBIE.CUSTOMER_NAME                                              AS customerName, " +
//
//            "                COALESCE(CI.TOTAL_CONTACT, 0)                                   AS numberOfContact, " +
//
//            "                COALESCE(CI.RIGHT_PARTY_COUNT, 0)                               AS numberOfRightPartyContact, " +
//
//            "                COALESCE(CI.OTHER_PARTY_COUNT, 0)                               AS numberOfGuarantorOrThirdPartyContact, " +
//
//            "                COALESCE(CPTP.TOTAL_COUNT, 0)                                   AS totalPtp, " +
//
//            "                COALESCE(CPTP.BROKEN_COUNT, 0)                                  AS brokenPtp, " +
//
//            "                COALESCE(CPTP.CURED_COUNT, 0)                                   AS curedPtp, " +
//
//            "                COALESCE(VLE.VISIT_COUNT, 0)                                    AS numberOfVisit, " +
//
//            "                TO_CHAR(FU.FOLLOW_UP_DATE, 'DD.MM.YYYY')                        AS followUpDate, " +
//
//            "                DECODE(CADI.AGE_CODE, NULL, '-', CADI.AGE_CODE)                 AS dpdBucket, " +
//
//            "                COALESCE(COALESCE(CAI.BDT_OUTSTANDING,0) + " +
//            "       CAI.INTERNATIONAL_OUTSTANDING * CADI.BDT_USD_CONVERSION_RATE, 0)       AS outstanding, " +
//            "                COALESCE(CAI.BDT_LIMIT + " +
//            "       CAI.INTERNATIONAL_LIMIT * CADI.BDT_USD_CONVERSION_RATE, 0)             AS totalLimit, "+
//            "                COALESCE(CAI.BDT_LIMIT, 0)                                      AS bdtLimit, "+
//            "                COALESCE(CAI.BDT_MIN_DUE, 0)                                    AS bdtMinDue, "+
//            "                COALESCE(CAI.BDT_OUTSTANDING,0)                                 AS bdtOutstanding, "+
//            "                COALESCE(CAI.BDT_OUTSTANDING / CAI.BDT_LIMIT, 0)                AS bdtEol, "+
//            "                COALESCE(CAI.INTERNATIONAL_LIMIT, 0)                                      AS usdLimit, "+
//            "                COALESCE(CAI.INTERNATIONAL_MIN_DUE, 0)                                    AS usdMinDue, "+
//            "                COALESCE(CAI.INTERNATIONAL_OUTSTANDING,0)                                 AS usdOutstanding, "+
//            "                COALESCE(CAI.INTERNATIONAL_OUTSTANDING / CAI.INTERNATIONAL_LIMIT, 0)                AS usdEol, "+
//
//            "                COALESCE(CADI.OPENING_OVER_DUE, 0)                              AS overdueAmount, " +
//
//            "                COALESCE(CADI.EMI_AMOUNT, 0)                                    AS emiAmount, " +
//
//            "                COALESCE(CPLD.PAYMENT_AMOUNT, 0)                                AS lastPayment, " +
//
//            "                COALESCE(CPCM.CURRENT_MONTH_PAYMENT, 0)                         AS currentMonthPayment, " +
//
//            "                DECODE(B.BRANCH_NAME, NULL, CBIE.BRANCH_NAME, B.BRANCH_NAME)    AS branchName, " +
//            "                CBIE.annive_date                                                AS anniversaryDate, "+
//
//            "                DECODE(RC.RISK_CATEGORY_NAME, NULL, '-', RC.RISK_CATEGORY_NAME) AS riskCategory, " +
//
//            "                TO_CHAR(CADI.CREATED_DATE, 'DD.MM.YYYY')                        AS allocationDate " +
//
//            "FROM (SELECT * " +
//            "      FROM CARD_ACCOUNT_DISTRIBUTION_INFO CADI_IN " +
//
//            "      WHERE CADI_IN.DEALER_PIN = :pin " +
//
//            "        AND CADI_IN.LATEST = '1' " +
//
//            "        AND CADI_IN.CREATED_DATE BETWEEN :startDate AND :endDate) CADI " +
//
//            "       JOIN CARD_ACCOUNT_BASIC_INFO CABI ON CABI.ID = CADI.CARD_ACCOUNT_BASIC_INFO_ID " +
//
//            "       JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE on CABI.CUSTOMER_ID = CBIE.ID " +
//
//            "       LEFT JOIN CARD_ACCOUNT_INFO CAI ON CABI.ID = CAI.CARD_ACCOUNT_BASIC_INFO_ID " +
//
//            "       LEFT JOIN LOS_TB_S_BRANCH B ON CBIE.BRANCH_CODE = B.BRANCH_CODE OR TO_NUMBER(CBIE.BRANCH_CODE) = TO_NUMBER(B.BRANCH_CODE) " +
//
//            "       LEFT JOIN AGE_CODE AC ON AC.NAME = CADI.AGE_CODE " +
//
//            "       LEFT JOIN RISK_CATEGORY_AGE_CODE_ENTITIES RCACE ON RCACE.AGE_CODE_ENTITIES_ID = AC.ID " +
//
//            "       LEFT JOIN RISK_CATEGORY RC ON RC.FID = CBIE.FID OR RCACE.RISK_CATEGORY_ID = RC.ID " +
//
//            "       LEFT JOIN CPTP ON CPTP.CUSTOMER_ID = CABI.CUSTOMER_ID " +
//
//            "       LEFT JOIN CI ON CI.CUSTOMER_ID = CABI.CUSTOMER_ID " +
//
//            "       LEFT JOIN VLE ON CABI.CONTRACT_ID = VLE.ACCOUNT_CARD_NUMBER " +
//
//            "       LEFT JOIN CPCM ON CABI.CONTRACT_ID = CPCM.CONTRACT_ID " +
//
//            "       LEFT JOIN CPLD ON CPLD.CONTRACT_ID = CABI.CONTRACT_ID " +
//
//            "       LEFT JOIN FU ON FU.CUSTOMER_ID = CABI.CUSTOMER_ID " +
//
//            "ORDER BY riskCategory, numberOfContact ASC", nativeQuery = true)
    @Query(value = "WITH CPCM AS (SELECT CONTRACT_ID, SUM(PAYMENT_AMOUNT) CURRENT_MONTH_PAYMENT " +
            "                              FROM CARD_PAYMENT " +
            "                              WHERE PAYMENT_DATE BETWEEN :startDate AND :endDate " +
            "                              GROUP BY CONTRACT_ID) , " +
            "                 CPLD AS (SELECT * " +
            "                              FROM (SELECT CONTRACT_ID, PAYMENT_DATE, PAYMENT_AMOUNT, ROW_NUMBER() " +
            "                              OVER (PARTITION BY CONTRACT_ID ORDER BY PAYMENT_DATE DESC) AS ROWNUMBER " +
            "                              FROM CARD_PAYMENT WHERE PAYMENT_DATE >= :startDate ) " +
            "                              WHERE ROWNUMBER = 1 " +
            "                         ) , " +
            "                   FU AS (SELECT CUSTOMER_ID, MIN(FOLLOW_UP_DATE) FOLLOW_UP_DATE " +
            "                              FROM CARD_FOLLOW_UP " +
            "                              WHERE TRUNC(FOLLOW_UP_DATE, 'DD') >= TRUNC(SYSDATE, 'DD') " +
            "                              GROUP BY CUSTOMER_ID " +
            "                          ) , " +
            "                  VLE AS (SELECT ACCOUNT_CARD_NUMBER, COUNT(ID) VISIT_COUNT " +
            "                              FROM VISIT_LEDGER_ENTITY " +
            "                              WHERE CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                              GROUP BY ACCOUNT_CARD_NUMBER " +
            "                          ) , " +
            "                   CI AS (SELECT CUSTOMER_ID , " +
            "                                     COUNT(ATTEMPT)                                               TOTAL_CONTACT, " +
            "                                     COUNT(CASE WHEN LOWER(CATEGORY) = 'right party' THEN 1 END)  RIGHT_PARTY_COUNT, " +
            "                                     COUNT(CASE WHEN LOWER(CATEGORY) != 'right party' THEN 1 END) OTHER_PARTY_COUNT " +
            "                              FROM CONTACT_INFO_CARD " +
            "                              WHERE PIN = :pin AND CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                              GROUP BY CUSTOMER_ID " +
            "                          ) , " +
            "                 CPTP AS (SELECT CUSTOMER_ID, " +
            "                                     COUNT(CARD_PTP_STATUS)                                        TOTAL_COUNT, " +
            "                                     COUNT(CASE WHEN LOWER(CARD_PTP_STATUS) = 'cured' THEN 1 END)  CURED_COUNT, " +
            "                                     COUNT(CASE WHEN LOWER(CARD_PTP_STATUS) = 'broken' THEN 1 END) BROKEN_COUNT " +
            "                              FROM CARD_PTP " +
            "                              WHERE CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                              GROUP BY CUSTOMER_ID " +
            "                          ) " +
            "SELECT DISTINCT CABI.CONTRACT_ID                                                AS accountNo, " +
            "                CABI.CLIENT_ID                                                  AS clientId, " +
            " " +
            "                CBIE.CUSTOMER_NAME                                              AS customerName, " +
            " " +
            "                COALESCE(CI.TOTAL_CONTACT, 0)                                   AS numberOfContact, " +
            " " +
            "                COALESCE(CI.RIGHT_PARTY_COUNT, 0)                               AS numberOfRightPartyContact, " +
            " " +
            "                COALESCE(CI.OTHER_PARTY_COUNT, 0)                               AS numberOfGuarantorOrThirdPartyContact, " +
            " " +
            "                COALESCE(CPTP.TOTAL_COUNT, 0)                                   AS totalPtp, " +
            " " +
            "                COALESCE(CPTP.BROKEN_COUNT, 0)                                  AS brokenPtp, " +
            " " +
            "                COALESCE(CPTP.CURED_COUNT, 0)                                   AS curedPtp, " +
            " " +
            "                COALESCE(VLE.VISIT_COUNT, 0)                                    AS numberOfVisit, " +
            " " +
            "                TO_CHAR(FU.FOLLOW_UP_DATE, 'DD.MM.YYYY')                        AS followUpDate, " +
            "                CAI.NO_OF_DAYS                                                  AS dpd, " +
            "                DECODE(CADI.AGE_CODE, NULL, '-', CADI.AGE_CODE)                 AS dpdBucket, " +
            "                AACR.CLASSIFICATION_STATUS                                      AS clStatus, " +
            "                ABS(CAI.BDT_OUTSTANDING) AS btd_ot, " +
            "                ABS(CAI.INTERNATIONAL_OUTSTANDING) as int_od, " +
            "                ABS(COALESCE(COALESCE(CAI.BDT_OUTSTANDING,0) + " +
            "                         COALESCE(CAI.INTERNATIONAL_OUTSTANDING,0) * (SELECT EXCHANGE_RATE FROM EXCHANGE_RATE_ENTITY WHERE LATEST = 1), 0))       AS outstanding, " +
            "                ABS(COALESCE(CAI.BDT_LIMIT + " +
            "       CAI.INTERNATIONAL_LIMIT * (SELECT EXCHANGE_RATE FROM EXCHANGE_RATE_ENTITY WHERE LATEST = 1), 0))             AS totalLimit, + " +
            "                ABS(COALESCE(CAI.BDT_LIMIT, 0))                                      AS bdtLimit, + " +
            "                ABS(COALESCE(CAI.BDT_MIN_DUE, 0))                                    AS bdtMinDue, + " +
            "                ABS(COALESCE(CAI.BDT_OUTSTANDING,0))                                 AS bdtOutstanding, + " +
            "                ABS(COALESCE(DECODE(CAI.BDT_LIMIT,0,0,((CAI.BDT_OUTSTANDING/CAI.BDT_LIMIT))),0))              AS bdtEol, + " +
            "                ABS(COALESCE(CAI.INTERNATIONAL_LIMIT, 0))                                      AS usdLimit, + " +
            "                ABS(COALESCE(CAI.INTERNATIONAL_MIN_DUE, 0))                                    AS usdMinDue, + " +
            "                ABS(COALESCE(CAI.INTERNATIONAL_OUTSTANDING,0))                                 AS usdOutstanding, + " +
            "                ABS(COALESCE(DECODE(CAI.INTERNATIONAL_LIMIT,0,0,((CAI.INTERNATIONAL_OUTSTANDING/CAI.INTERNATIONAL_LIMIT))),0))               AS usdEol, + " +
            " " +
            "                ABS(COALESCE(CADI.OPENING_OVER_DUE, 0))                              AS overdueAmount, " +
            " " +
            "                ABS(COALESCE(CADI.EMI_AMOUNT, 0))                                    AS emiAmount, " +
            " " +
            "                ABS(COALESCE(CPLD.PAYMENT_AMOUNT, 0))                                AS lastPayment, " +
            " " +
            "                ABS(COALESCE(CPCM.CURRENT_MONTH_PAYMENT, 0))                         AS currentMonthPayment, " +
            " " +
            "                DECODE(B.BRANCH_NAME, NULL, CBIE.BRANCH_NAME, B.BRANCH_NAME)    AS branchName, " +
            "                TO_CHAR(CBIE.annive_date, 'DD.MM.YYYY')                         AS anniversaryDate, + " +
            " " +
            "                DECODE(RC.RISK_CATEGORY_NAME, NULL, '-', RC.RISK_CATEGORY_NAME) AS riskCategory, " +
            " " +
            "                TO_CHAR(CADI.CREATED_DATE, 'DD.MM.YYYY')                        AS allocationDate " +
            " " +
            "FROM (SELECT * " +
            "      FROM CARD_ACCOUNT_DISTRIBUTION_INFO CADI_IN " +
            " " +
            "      WHERE CADI_IN.DEALER_PIN = :pin " +
            " " +
            "        AND CADI_IN.LATEST = '1' " +
            " " +
            "        AND CADI_IN.CREATED_DATE BETWEEN :startDate AND :endDate) CADI " +
            " " +
            "       JOIN CARD_ACCOUNT_BASIC_INFO CABI ON CABI.ID = CADI.CARD_ACCOUNT_BASIC_INFO_ID " +
            " " +
            "       JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE on CABI.CUSTOMER_ID = CBIE.ID " +
            " " +
            "       LEFT JOIN CARD_ACCOUNT_INFO CAI ON CABI.ID = CAI.CARD_ACCOUNT_BASIC_INFO_ID " +
            " " +
            "       LEFT JOIN LOS_TB_S_BRANCH B ON CBIE.BRANCH_CODE = B.BRANCH_CODE OR TO_NUMBER(CBIE.BRANCH_CODE) = TO_NUMBER(B.BRANCH_CODE) " +
            " " +
            "       LEFT JOIN AGE_CODE AC ON AC.NAME = CADI.AGE_CODE " +
            " " +
            "       LEFT JOIN AGE_AND_CLASSIFICATION_RULE AACR ON AC.ID = AACR.AGE_CODE_ID " +
            " " +
            "       LEFT JOIN RISK_CATEGORY_AGE_CODE_ENTITIES RCACE ON RCACE.AGE_CODE_ENTITIES_ID = AACR.AGE_CODE_ID " +
            " " +
            "       LEFT JOIN RISK_CATEGORY RC ON RC.FID = CBIE.FID OR RCACE.RISK_CATEGORY_ID = RC.ID " +
            " " +
            "       LEFT JOIN CPTP ON CPTP.CUSTOMER_ID = CABI.CUSTOMER_ID " +
            " " +
            "       LEFT JOIN CI ON CI.CUSTOMER_ID = CABI.CUSTOMER_ID " +
            " " +
            "       LEFT JOIN VLE ON CABI.CONTRACT_ID = VLE.ACCOUNT_CARD_NUMBER " +
            " " +
            "       LEFT JOIN CPCM ON CABI.CONTRACT_ID = CPCM.CONTRACT_ID " +
            " " +
            "       LEFT JOIN CPLD ON CPLD.CONTRACT_ID = CABI.CONTRACT_ID " +
            " " +
            "       LEFT JOIN FU ON FU.CUSTOMER_ID = CABI.CUSTOMER_ID " +
            " " +
            "ORDER BY riskCategory, numberOfContact ASC", nativeQuery = true)
    List<Tuple> getCardAccountDistributionSummary(@Param("pin") String dealerPin,  @Param("startDate") Date startdate, @Param("endDate") Date endDate);

    @Query(value = "SELECT DISTINCT CARD_NO " +
            "FROM CARD_ACCOUNT_BASIC_INFO LABI " +
            "       JOIN CARD_ACCOUNT_DISTRIBUTION_INFO LADI " +
            "         ON LABI.ID = LADI.CARD_ACCOUNT_BASIC_INFO_ID AND LADI.CREATED_DATE >= TRUNC(SYSDATE, 'MM') AND " +
            "            LADI.LATEST = '1'", nativeQuery = true)
    HashSet<String> getCurrentMonthDistributedCardNumbers();


    @Modifying
    @Transactional
    @Query(value = "UPDATE CARD_ACCOUNT_DISTRIBUTION_INFO l SET l.latest = '0' WHERE l.CARD_ACCOUNT_BASIC_INFO_ID = :cardBasicId and l.CREATED_DATE >= trunc((sysdate),'MM') and l.latest = '1'", nativeQuery = true)
    int updateLatestStatus(@Param("cardBasicId") Long cardBasicId);

    @Query(value = "SELECT CADI.DEALER_PIN AS dealerId, (DUSR.FIRST_NAME || ' ' || DUSR.LAST_NAME) AS dealerName, " +
            "       CADI.CREATED_BY AS createdById, (CUSR.FIRST_NAME || ' ' || CUSR.LAST_NAME) AS createByName, CADI.CREATED_DATE AS createdDate, " +
            "       (USR_S.FIRST_NAME || ' ' || USR_S.LAST_NAME) AS teamleaderName ,USR_S.EMPLOYEE_ID as teamleaderId, " +
            "       CADI.CURRENT_AGE_CODE AS ageCode,CADI.OUT_STANDING AS outStanding , CADI.MIN_DUE_PAYMENT As minumumDue,CADI.DPD_BUCKET AS dpdBucket "+
            "FROM CARD_ACCOUNT_DISTRIBUTION_INFO CADI " +
            "LEFT JOIN LOS_TB_M_USERS DUSR ON DUSR.USERNAME = CADI.DEALER_PIN " +
            "LEFT JOIN LOS_TB_M_USERS CUSR ON CUSR.USERNAME = CADI.CREATED_BY " +
            "LEFT JOIN EMPLOYEE_INFO_ENTITY D ON D.PIN = CADI.DEALER_PIN " +
            "LEFT JOIN PEOPLE_ALLOCATION_LOGIC PAL ON D.ID = PAL.DEALER_ID " +
            "LEFT JOIN EMPLOYEE_INFO_ENTITY S ON PAL.TEAM_LEAD_ID = S.ID " +
            "LEFT JOIN LOS_TB_M_USERS USR_S ON S.USER_ID = USR_S.USER_ID " +
            " " +
            "WHERE CADI.CARD_ACCOUNT_BASIC_INFO_ID = (SELECT ID FROM CARD_ACCOUNT_BASIC_INFO CABI WHERE CABI.CONTRACT_ID = ?1 and CABI.CLIENT_ID= ?4) " +
            "AND CADI.CREATED_DATE BETWEEN ?2 AND ?3 " +
            "ORDER BY CADI.CREATED_DATE DESC", nativeQuery = true)
    List<Tuple> getCardAccountDealerAllocationHistory(String accountNo, Date startDate, Date endDate,String clientId);
//dd-MMM-yyyy hh:mm aa
    List<CardAccountDistributionInfo> findByCreatedDateIsBetweenAndSamAccountAndWriteOffAccountAndDealerNameNotAndSupervisorNameNot(Date startDate, Date endDate, String b, String s, String dealerName, String supervisorName);

    List<CardAccountDistributionInfo> findByCreatedDateLessThanAndSamAccountAndWriteOffAccountAndProductGroupNotInAndVvipAndSecureCard
            (Date startDate, boolean b, boolean s, List<String> productGroup, String vvip, String secureCard);

    List<CardAccountDistributionInfo> findByCreatedDateLessThanAndSamAccountNotInAndWriteOffAccountNotInAndProductGroupNotInAndVvipNotInAndSecureCardNotIn(
            Date startDate, List<String> samAccount, List<String> writeOff, List<String> productGroup, List<String> vvip, List<String> secureCard
    );

    List<CardAccountDistributionInfo> findByCreatedDateLessThan(Date startDate);

    CardAccountDistributionInfo findFirstByCardAccountBasicInfoOrderByCreatedDateDesc(CardAccountBasicInfo cardAccountBasicInfo);

    List<CardAccountDistributionInfo> findByCreatedDateLessThanAndAgeCodeIn(Date date, List<String> ageCodeList);

    CardAccountDistributionInfo findFirstByCardAccountBasicInfoAndCreatedDateGreaterThanEqualAndLatestOrderByCreatedDateDesc(CardAccountBasicInfo cardAccountBasicInfo, Date startDate, String latest);

    List<CardAccountDistributionInfo> findByCreatedDateIsBetweenAndDealerPinAndSupervisorPin(Date startDate, Date endDate, String dealerPin, String supervisorPin);

    List<CardAccountDistributionInfo>
    findByCreatedDateIsBetweenAndDealerNameAndSupervisorNameAndAgeCodeInAndBillingCycleInAndProductGroupInAndSamAccountNotInAndWriteOffAccountNotInAndVvipNotInAndSecureCardNotInAndCustomerNameNotIn
            (
                    Date startDate,
                    Date endDate,
                    String dealerName,
                    String supervisorName,
                    List<String> ageCode,
                    List<String> billingCycle,
                    List<String> productGroup,
                    List<String> samAccount,
                    List<String> writeOffAccount,
                    List<String> vvip,
                    List<String> secureCard,
                    List<String> customerName);

    List<CardAccountDistributionInfo> findByCardAccountBasicInfoOrderByCreatedDateDesc(CardAccountBasicInfo cardAccountBasicInfo);

    List<CardAccountDistributionInfo> findByCreatedDateIsBetweenAndSamAccountAndWriteOffAccountAndLatestOrderByCreatedDateDesc(Date startDate, Date endDate, String s, String s1, String s2);

    CardAccountDistributionInfo findFirstByCreatedDateIsBetweenAndCardAccountBasicInfoOrderByCreatedDateDesc(Date startDate, Date finalEndDate, CardAccountBasicInfo cardAccountBasicInfo);

    CardAccountDistributionInfo findFirstByCreatedDateIsBetweenAndCardAccountBasicInfoAndLatestOrderByCreatedDateDesc(Date startDate, Date endDate, CardAccountBasicInfo cardAccountBasicInfo, String s);

    List<CardAccountDistributionInfo> findByCreatedDateLessThanAndAgeCodeInAndLatest(Date startDate, List<String> collect, String s);

    CardAccountDistributionInfo findFirstByCreatedDateLessThanAndCardAccountBasicInfoAndLatestOrderByCreatedDateDesc(Date startDate, CardAccountBasicInfo cardAccountBasicInfo, String s);

    CardAccountDistributionInfo findFirstByCreatedDateLessThanAndCardAccountBasicInfoOrderByCreatedDateDesc(Date startDate, CardAccountBasicInfo cardAccountBasicInfo);

    @Query(value = "SELECT DEALER_NAME,  to_char(CREATED_DATE, 'Month') AS months " +
            "         FROM CARD_ACCOUNT_DISTRIBUTION_INFO " +
            "        WHERE CLIENT_ID = ?1 " +
            "          AND EXTRACT(MONTH FROM CREATED_DATE) IN (?2)", nativeQuery = true)
    List<Tuple> findPreviousTwoMonthDealerByClientId(String clientId, List<String>monthNumber);

    //@Query(value = "", nativeQuery = true)
    List<CardAccountDistributionInfo> findByClientIdAndLatest(String clientId, String latest);
}
