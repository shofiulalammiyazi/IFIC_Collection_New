package com.csinfotechbd.retail.card.dashboard;

import com.csinfotechbd.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

public interface RetailCardDashBoardRepository extends JpaRepository<CardAccountDistributionInfo,Long> {

    @Query(value = "" +
            "WITH CPCM AS (SELECT CP.CARD_NO  AS CARD_NO, " +
            "                     SUM(CASE " +
            "                         WHEN CP.PAYMENT_DATE BETWEEN :startDate AND :endDate " +
            "                         THEN CP.PAYMENT_AMOUNT " +
            "                         ELSE 0 END)    AS CURRENT_MONTH_TOTAL_PAYMENT, " +
            "                     SUM(CASE " +
            "                         WHEN TRUNC(CP.PAYMENT_DATE, 'DD') = TRUNC(SYSDATE - 1, 'DD') " +
            "                         THEN CP.PAYMENT_AMOUNT " +
            "                         ELSE 0 END)    AS LAST_DATE_TOTAL_PAYMENT, " +
            "                     MAX(CP.PAYMENT_DATE) AS LAST_PAYMENT_DATE, " +
            "                     SUM(CASE " +
            "                         WHEN CP.PAYMENT_DATE = CPMD.PAYMENT_DATE " +
            "                         THEN CP.PAYMENT_AMOUNT " +
            "                         ELSE 0 END)    AS LAST_PAYMENT " +
            "              FROM CARD_PAYMENT CP " +
            "              LEFT JOIN (SELECT CP_IN.CARD_NO, MAX(CP_IN.PAYMENT_DATE) PAYMENT_DATE " +
            "                      FROM CARD_PAYMENT CP_IN " +
            "                      GROUP BY CP_IN.CARD_NO) CPMD " +
            "                  ON CP.CARD_NO = CPMD.CARD_NO " +
            "              GROUP BY CP.CARD_NO), "+
            "NPL AS (SELECT DISTINCT NARDBL.AGE_CODE_LIST_ID     AS BUCKET_ID, " +
            "                        NARPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
            "               FROM NPL_ACCOUNT_RULE_CARD NAR " +
            "               JOIN NPL_ACCOUNT_RULE_CARD_AGE_CODE_LIST NARDBL " +
            "                   ON NAR.ID = NARDBL.NPLACCOUNT_RULE_CARD_ENTITY_ID " +
            "               JOIN NPL_ACCOUNT_RULE_CARD_PRODUCT_TYPE_LIST NARPTL " +
            "                   ON NAR.ID = NARPTL.NPLACCOUNT_RULE_CARD_ENTITY_ID " +
            "               ORDER BY BUCKET_ID, PRODUCT_ID),"+
            " NPLQ AS (SELECT DISTINCT NARQ.MIN_DPD, " +
            "                          NARQDBL.AGE_CODE_LIST_ID   AS BUCKET_ID, " +
            "                          NARQPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
            "                 FROM NPL_QUEUE_ACC_RULE_CARD NARQ " +
            "                 JOIN NPL_QUEUE_ACC_RULE_CARD_AGE_CODE_LIST NARQDBL " +
            "                     ON NARQ.ID = NARQDBL.NPLQUEUE_ACC_RULE_CARD_ENTITY_ID " +
            "                 JOIN NPL_QUEUE_ACC_RULE_CARD_PRODUCT_TYPE_LIST NARQPTL " +
            "                     ON NARQ.ID = NARQPTL.NPLQUEUE_ACC_RULE_CARD_ENTITY_ID " +
            "                 ORDER BY BUCKET_ID, PRODUCT_ID), "+
            "NPLMO AS (SELECT DISTINCT NARDBL.AGE_CODE_LIST_ID AS BUCKET_ID, " +
            "                          NARPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
            "                 FROM NPL_ACCOUNT_RULE_CARD NAR " +
            "                 JOIN NPL_ACCOUNT_RULE_CARD_AGE_CODE_LIST NARDBL " +
            "                     ON NAR.ID = NARDBL.NPLACCOUNT_RULE_CARD_ENTITY_ID " +
            "                 JOIN NPL_ACCOUNT_RULE_CARD_PRODUCT_TYPE_LIST NARPTL " +
            "                     ON NAR.ID = NARPTL.NPLACCOUNT_RULE_CARD_ENTITY_ID " +
            "                 ORDER BY BUCKET_ID, PRODUCT_ID),"+
            "NPLQMO AS (SELECT DISTINCT NARQ.MIN_DPD, " +
            "                           NARQDBL.AGE_CODE_LIST_ID AS BUCKET_ID, " +
            "                           NARQPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
            "                  FROM NPL_QUEUE_ACC_RULE_CARD NARQ " +
            "                  JOIN NPL_QUEUE_ACC_RULE_CARD_AGE_CODE_LIST NARQDBL " +
            "                      ON NARQ.ID = NARQDBL.NPLQUEUE_ACC_RULE_CARD_ENTITY_ID " +
            "                  JOIN NPL_QUEUE_ACC_RULE_CARD_PRODUCT_TYPE_LIST NARQPTL " +
            "                      ON NARQ.ID = NARQPTL.NPLQUEUE_ACC_RULE_CARD_ENTITY_ID " +
            "                  ORDER BY BUCKET_ID, PRODUCT_ID), "+
            "NPL_RULE AS (SELECT CADI_IN.ID               AS ID, " +
            "                    CASE " +
            "                       WHEN TO_NUMBER(CADI_IN.CURRENT_AGE_CODE) > (SELECT MAX(TO_NUMBER(NAME)) FROM AGE_CODE) " +
            "                       THEN 1 " +
            "                       ELSE NPL.BUCKET_ID END AS NPL_ID, " +
            "                    NPLQ.BUCKET_ID           AS NPL_QUEUE_ID, " +
            "                    NPLQMO.BUCKET_ID         AS NPLMO_QUEUE_ID " +
            "                    FROM CARD_ACCOUNT_DISTRIBUTION_INFO CADI_IN " +
            "                    LEFT JOIN AGE_CODE BC ON BC.NAME = CEIL(CADI_IN.AGE_CODE) " +
            "                    LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON CADI_IN.SCHEME_CODE = PTE.CODE " +
            "                    LEFT JOIN  NPL " +
            "                       ON PTE.ID = NPL.PRODUCT_ID AND BC.ID = NPL.BUCKET_ID " +
            "                    LEFT JOIN  NPLQ " +
            "                       ON PTE.ID = NPLQ.PRODUCT_ID AND BC.ID = NPLQ.BUCKET_ID " +
            "                    LEFT JOIN AGE_CODE BMO ON BMO.NAME = CEIL(CADI_IN.AGE_CODE) " +
            "                    LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON CADI_IN.SCHEME_CODE = PTE.CODE " +
            "                    LEFT JOIN NPLMO " +
            "                       ON PTE.ID = NPLMO.PRODUCT_ID AND BMO.ID = NPLMO.BUCKET_ID " +
            "                    LEFT JOIN NPLQMO " +
            "                       ON PTE.ID = NPLQMO.PRODUCT_ID AND BMO.ID = NPLQMO.BUCKET_ID " +
            "                    WHERE CADI_IN.DEALER_PIN IN (:pin) " +
            "                       AND CADI_IN.LATEST = '1' " +
            "                       AND CADI_IN.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                    ORDER BY CADI_IN.ID) "+
            "SELECT CARD_INFO.CATEGORY                             AS category, " +
            "       COUNT(CASE " +
            "               WHEN CARD_INFO.CURRENT_MONTH_TOTAL_PAYMENT > 0.0 " +
            "                       THEN CARD_INFO.CARD_NO END) AS currentMonthTotalAccount, " +
            "       SUM(CARD_INFO.CURRENT_MONTH_TOTAL_PAYMENT)     AS currentMonthTotalPayment, " +
            "       COUNT(CASE " +
            "               WHEN CARD_INFO.LAST_DATE_TOTAL_PAYMENT > 0 " +
            "                       THEN CARD_INFO.CARD_NO END) AS lastDateTotalAccount, " +
            "       SUM(CARD_INFO.LAST_DATE_TOTAL_PAYMENT)         AS lastDateTotalPayment, " +
            "       REPLACE('[' || " +
            "               RTRIM( " +
            "                 XMLAGG( " +
            "                   XMLELEMENT(e, '{' " +
            "                                   || '\"accountNo\":\"' || CARD_INFO.CARD_NO || '\", ' " +
            "                                   || '\"accountName\":\"' || CARD_INFO.ACCOUNT_NAME || '\", ' " +
            "                                   || '\"currentMonthPayment\":\"' || CARD_INFO.CURRENT_MONTH_TOTAL_PAYMENT || '\", ' " +
            "                                   || '\"lastDatePayment\":\"' || CARD_INFO.LAST_DATE_TOTAL_PAYMENT || '\", ' " +
            "                                   || '\"overdue\":\"' || CARD_INFO.OPENING_OVER_DUE || '\", ' " +
            "                                   || '\"outstanding\":\"' || CARD_INFO.OUT_STANDING || '\", ' " +
            "                                   || '\"dealerName\":\"' || CARD_INFO.DEALER_NAME || '\", ' " +
            "                                   || '\"lastPaymentDate\":\"' || TO_CHAR(CARD_INFO.LAST_PAYMENT_DATE, 'DD.MM.YYYY') || " +
            "                                 '\" ' " +
            "                                   || '}', ',') " +
            "                       .extract('//text()') ORDER BY CARD_INFO.LAST_PAYMENT_DATE DESC) " +
            "                     .getclobval(), ', ') " +
            "                 || ']', '&quot;', '\"' )               AS paymentDetails " +
            "FROM (SELECT CABI.CONTRACT_ID                                                                      AS CARD_NO, " +
            "             CABI.CARD_NAME                                                                    AS ACCOUNT_NAME, " +
            "             CADI.DEALER_NAME                                                                     AS DEALER_NAME, " +
            "             COALESCE(CADI.OPENING_OVER_DUE, 0)                                                   AS OPENING_OVER_DUE, " +
            "             COALESCE(CADI.OUT_STANDING, '0')                                                     AS OUT_STANDING, " +
            "             COALESCE(CPCM.CURRENT_MONTH_TOTAL_PAYMENT, 0)                                        AS CURRENT_MONTH_TOTAL_PAYMENT, " +
            "             COALESCE(CPCM.LAST_DATE_TOTAL_PAYMENT, 0)                                            AS LAST_DATE_TOTAL_PAYMENT, " +
            "             DECODE(COALESCE(CPCM.LAST_PAYMENT, 0), 0, CADI.LAST_PAID_AMOUNT, CPCM.LAST_PAYMENT)  AS LAST_PAYMENT, " +
            "             DECODE(CPCM.LAST_PAYMENT_DATE, NULL, CADI.LAST_PAYMENT_DATE, CPCM.LAST_PAYMENT_DATE) AS LAST_PAYMENT_DATE, " +
            "             CASE " +
            "               WHEN COALESCE(CPCM.CURRENT_MONTH_TOTAL_PAYMENT, 0) >= CADI.OPENING_OVER_DUE THEN 'Full amount paid' " +
            "               WHEN COALESCE(CPCM.CURRENT_MONTH_TOTAL_PAYMENT, 0) <> 0 AND CADI.AGE_CODE < CADI.CURRENT_AGE_CODE " +
            "                       THEN 'Partial paid but forward flow to upper bucket' " +
            "               WHEN COALESCE(CPCM.CURRENT_MONTH_TOTAL_PAYMENT, 0) = 0 THEN 'No payment during the month' " +
            "               ELSE END                                                                  AS CATEGORY " +
            "      FROM CARD_ACCOUNT_DISTRIBUTION_INFO CADI " +
            "             JOIN CARD_ACCOUNT_BASIC_INFO CABI ON CABI.ID = CADI.CARD_ACCOUNT_BASIC_INFO_ID " +
            "             LEFT JOIN  CPCM ON CABI.CONTRACT_ID = CPCM.CARD_NO " +
            "             LEFT JOIN  ON CADI.ID = NPL_RULE.ID " +
            "      WHERE CADI.DEALER_PIN IN (:pin) " +
            "        AND CADI.LATEST = '1' " +
            "        AND CADI.CREATED_DATE BETWEEN :startDate AND :endDate) CARD_INFO " +
            "GROUP BY CARD_INFO.CATEGORY", nativeQuery = true)
    List<Tuple> getCategorizedCardPaymentSummary(@Param("pin") List<String> dealerPins, @Param("startDate") Date startDate, @Param("endDate") Date endDate);


//    @Query(value = "" +" WITH CPCM AS (SELECT CP.CONTRACT_ID        AS ACCOUNT_NO, " +
//            "                                   SUM(CASE " +
//            "                                         WHEN CP.PAYMENT_DATE BETWEEN :startDate AND :endDate " +
//            "                                                 THEN CP.PAYMENT_AMOUNT " +
//            "                                         ELSE 0 END)    AS CURRENT_MONTH_TOTAL_PAYMENT, " +
//            "                                   SUM(CASE " +
//            "                                         WHEN TRUNC(CP.PAYMENT_DATE, 'DD') = TRUNC(SYSDATE - 1, 'DD') " +
//            "                                                 THEN CP.PAYMENT_AMOUNT " +
//            "                                         ELSE 0 END)    AS LAST_DATE_TOTAL_PAYMENT, " +
//            "                                   MAX(CP.PAYMENT_DATE) AS LAST_PAYMENT_DATE, " +
//            "                                   SUM(CASE " +
//            "                                         WHEN CP.PAYMENT_DATE = CPMD.PAYMENT_DATE " +
//            "                                                 THEN CP.PAYMENT_AMOUNT " +
//            "                                         ELSE 0 END)    AS LAST_PAYMENT " +
//            "                            FROM CARD_PAYMENT CP " +
//            "                                   LEFT JOIN (SELECT CP_IN.CONTRACT_ID, MAX(CP_IN.PAYMENT_DATE) PAYMENT_DATE " +
//            "                                              FROM CARD_PAYMENT CP_IN " +
//            "                                              GROUP BY CP_IN.CONTRACT_ID) CPMD ON CP.CONTRACT_ID = CPMD.CONTRACT_ID " +
//            "                            GROUP BY CP.CONTRACT_ID" +
//            "                           ), " +
//            "         NPL AS (SELECT DISTINCT NARDBL.AGE_CODE_LIST_ID   AS BUCKET_ID, " +
//            "                                                                 NARPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
//            "                                                 FROM NPL_ACCOUNT_RULE_CARD NAR " +
//            "                                                        JOIN NPL_ACCOUNT_RULE_CARD_AGE_CODE_LIST NARDBL " +
//            "                                                          ON NAR.ID = NARDBL.NPLACCOUNT_RULE_CARD_ENTITY_ID " +
//            "                                                        JOIN NPL_ACCOUNT_RULE_CARD_PRODUCT_TYPE_LIST NARPTL " +
//            "                                                          ON NAR.ID = NARPTL.NPLACCOUNT_RULE_CARD_ENTITY_ID " +
//            "                                                 ORDER BY BUCKET_ID, PRODUCT_ID" +
//            "               ),"+
//            "  NPLQ AS (SELECT DISTINCT NARQ.MIN_DPD, " +
//            "                                                               NARQDBL.AGE_CODE_LIST_ID   AS BUCKET_ID,  " +
//            "                                                               NARQPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID  " +
//            "                                               FROM NPL_QUEUE_ACC_RULE_CARD NARQ  " +
//            "                                                      JOIN NPL_QUEUE_ACC_RULE_CARD_AGE_CODE_LIST NARQDBL  " +
//            "                                                        ON NARQ.ID = NARQDBL.NPLQUEUE_ACC_RULE_CARD_ENTITY_ID  " +
//            "                                                      JOIN NPL_QUEUE_ACC_RULE_CARD_PRODUCT_TYPE_LIST NARQPTL  " +
//            "                                                        ON NARQ.ID = NARQPTL.NPLQUEUE_ACC_RULE_CARD_ENTITY_ID  " +
//            "                                               ORDER BY BUCKET_ID, PRODUCT_ID" +
//            "           ), "+
//            " NPLMO AS  (SELECT DISTINCT NARDBL.AGE_CODE_LIST_ID   AS BUCKET_ID,  " +
//            "                                                                 NARPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID  " +
//            "                                                 FROM NPL_ACCOUNT_RULE_CARD NAR  " +
//            "                                                        JOIN NPL_ACCOUNT_RULE_CARD_AGE_CODE_LIST NARDBL  " +
//            "                                                          ON NAR.ID = NARDBL.NPLACCOUNT_RULE_CARD_ENTITY_ID  " +
//            "                                                        JOIN NPL_ACCOUNT_RULE_CARD_PRODUCT_TYPE_LIST NARPTL  " +
//            "                                                          ON NAR.ID = NARPTL.NPLACCOUNT_RULE_CARD_ENTITY_ID  " +
//            "                                                 ORDER BY BUCKET_ID, PRODUCT_ID" +
//            "       ),"+
//            " NPLQMO AS (SELECT DISTINCT NARQ.MIN_DPD,  " +
//            "                                                                   NARQDBL.AGE_CODE_LIST_ID   AS BUCKET_ID,  " +
//            "                                                                   NARQPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID  " +
//            "                                                   FROM NPL_QUEUE_ACC_RULE_CARD NARQ  " +
//            "                                                          JOIN NPL_QUEUE_ACC_RULE_CARD_AGE_CODE_LIST NARQDBL  " +
//            "                                                            ON NARQ.ID = NARQDBL.NPLQUEUE_ACC_RULE_CARD_ENTITY_ID  " +
//            "                                                          JOIN NPL_QUEUE_ACC_RULE_PRODUCT_TYPE_LIST NARQPTL  " +
//            "                                                            ON NARQ.ID = NARQPTL.NPLQUEUE_ACC_RULE_CARD_ENTITY_ID  " +
//            "                                                   ORDER BY BUCKET_ID, PRODUCT_ID" +
//            "           ), "+
//            " NPL_RULE AS (SELECT LADI_IN.ID               AS ID, " +
//            "                                           CASE " +
//            "                                                  (SELECT MAX(TO_NUMBER(NAME)) FROM AGE_CODE) " +
//            "                                                     THEN 1 " +
//            "                                             ELSE NPL.BUCKET_ID END AS NPL_ID, " +
//            "                                           NPLQ.BUCKET_ID           AS NPL_QUEUE_ID, " +
//            "                                           NPLQMO.BUCKET_ID         AS NPLMO_QUEUE_ID " +
//            "                                           LEFT JOIN NPL " +
//            "                                             ON PTE.ID = NPL.PRODUCT_ID AND BC.ID = NPL.BUCKET_ID " +
//            "                                           LEFT JOIN  NPLQ " +
//            "                                             ON PTE.ID = NPLQ.PRODUCT_ID AND BC.ID = NPLQ.BUCKET_ID " +
//            "                                           LEFT JOIN NPLMO " +
//            "                                             ON PTE.ID = NPLMO.PRODUCT_ID AND BMO.ID = NPLMO.BUCKET_ID " +
//            "                                           LEFT JOIN  NPLQMO " +
//            "                                             ON PTE.ID = NPLQMO.PRODUCT_ID AND BMO.ID = NPLQMO.BUCKET_ID " +
//            "                                    WHERE CADI_IN.DEALER_PIN IN (:pin) " +
//            "                                      AND CADI_IN.LATEST = '1' " +
//            "                                      AND CADI_IN.CREATED_DATE BETWEEN :startDate AND :endDate " +
//            "                                    ORDER BY CADI_IN.ID" +
//            "               ) "+
//            "SELECT CARD_INFO.CATEGORY                             AS category, " +
//            "       COUNT(CASE " +
//            "               WHEN CARD_INFO.CURRENT_MONTH_TOTAL_PAYMENT > 0 " +
//            "                       THEN CARD_INFO.ACCOUNT_NO END) AS currentMonthTotalAccount, " +
//            "       SUM(CARD_INFO.CURRENT_MONTH_TOTAL_PAYMENT)     AS currentMonthTotalPayment, " +
//            "       COUNT(CASE " +
//            "               WHEN CARD_INFO.LAST_DATE_TOTAL_PAYMENT >= 0 " +
//            "                       THEN CARD_INFO.ACCOUNT_NO END) AS lastDateTotalAccount, " +
//            "       SUM(CARD_INFO.LAST_DATE_TOTAL_PAYMENT)         AS lastDateTotalPayment, " +
//            "       REPLACE('[' || " +
//            "               RTRIM( " +
//            "                 XMLAGG( " +
//            "                   XMLELEMENT(e, '{' " +
//            "                                   || '\"accountNo\":\"' || CARD_INFO.ACCOUNT_NO || '\", ' " +
//            "                                   || '\"accountName\":\"' || CARD_INFO.ACCOUNT_NAME || '\", ' " +
//            "                                   || '\"currentMonthPayment\":\"' || CARD_INFO.CURRENT_MONTH_TOTAL_PAYMENT || '\", ' " +
//            "                                   || '\"lastDatePayment\":\"' || CARD_INFO.LAST_DATE_TOTAL_PAYMENT || '\", ' " +
//            "                                   || '\"overdue\":\"' || CARD_INFO.OPENING_OVER_DUE || '\", ' " +
//            "                                   || '\"outstanding\":\"' || CARD_INFO.OUT_STANDING || '\", ' " +
//            "                                   || '\"dealerName\":\"' || CARD_INFO.DEALER_NAME || '\", ' " +
//            "                                   || '\"lastPaymentDate\":\"' || TO_CHAR(CARD_INFO.LAST_PAYMENT_DATE, 'DD.MM.YYYY') || " +
//            "                                 '\" ' " +
//            "                                   || '}', ',') " +
//            "                       .extract('//text()') ORDER BY CARD_INFO.LAST_PAYMENT_DATE DESC) " +
//            "                     .getclobval(), ', ') " +
//            "                 || ']', '&quot;', '\"')               AS paymentDetails " +
//            "FROM (SELECT CABI.CONTRACT_ID                                                                      AS ACCOUNT_NO, " +
//            "             CABI.CARD_NAME                                                                    AS ACCOUNT_NAME, " +
//            "             CADI.DEALER_NAME                                                                     AS DEALER_NAME, " +
//            "             COALESCE(CADI.OPENING_OVER_DUE, 0)                                                   AS OPENING_OVER_DUE, " +
//            "             COALESCE(CADI.OUTSTANDING_AMOUNT, '0')                                                     AS OUT_STANDING, " +
//            "             COALESCE(CPCM.CURRENT_MONTH_TOTAL_PAYMENT, 0)                                        AS CURRENT_MONTH_TOTAL_PAYMENT, " +
//            "             COALESCE(CPCM.LAST_DATE_TOTAL_PAYMENT, 0)                                            AS LAST_DATE_TOTAL_PAYMENT, " +
//            "             DECODE(COALESCE(CPCM.LAST_PAYMENT, 0), 0, CADI.LAST_PAID_AMOUNT, CPCM.LAST_PAYMENT)  AS LAST_PAYMENT, " +
//            "             DECODE(CPCM.LAST_PAYMENT_DATE, NULL, CADI.LAST_PAYMENT_DATE, CPCM.LAST_PAYMENT_DATE) AS LAST_PAYMENT_DATE, " +
//            "             CASE " +
//            "               WHEN COALESCE(CPCM.CURRENT_MONTH_TOTAL_PAYMENT, 0) >= CADI.OPENING_OVER_DUE THEN 'Full amount paid' " +
//            "               WHEN COALESCE(CPCM.CURRENT_MONTH_TOTAL_PAYMENT, 0) <> 0 AND CADI.DPD_BUCKET < CADI.CURRENT_DPD_BUCKET " +
//            "                       THEN 'Partial paid but forward flow to upper bucket' " +
//            "               WHEN COALESCE(CPCM.CURRENT_MONTH_TOTAL_PAYMENT, 0) = 0 THEN 'No payment during the month' " +
//            "               WHEN NPL_RULE.NPL_QUEUE_ID IS NOT NULL THEN 'Probable CL (As on date on Dealer Queue)' " +
//            "               WHEN NPL_RULE.NPLMO_QUEUE_ID IS NOT NULL THEN 'Probable CL list as per month beginning distribution' " +
//            "               WHEN NPL_RULE.NPL_ID IS NOT NULL THEN 'Existing CL' " +
//            "               ELSE 'Others' END                                                                  AS CATEGORY " +
//            "      FROM CARD_ACCOUNT_DISTRIBUTION_INFO CADI " +
//            "             JOIN CARD_ACCOUNT_BASIC_INFO CABI ON CABI.ID = CADI.CARD_ACCOUNT_BASIC_INFO_ID " +
//            "             LEFT JOIN CPCM ON CABI.CONTRACT_ID = CPCM.ACCOUNT_NO " +
//            "             LEFT JOIN NPL_RULE ON CADI.ID = NPL_RULE.ID " +
//            "      WHERE CADI.DEALER_PIN IN (:pin) " +
//            "        AND CADI.LATEST = '1' " +
//            "        AND CADI.CREATED_DATE BETWEEN :startDate AND :endDate) CARD_INFO " +
//            "GROUP BY CARD_INFO.CATEGORY", nativeQuery = true)
//    List<Tuple> getCategorizedPaymentSummary(@Param("pin") List<String> dealerPins, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

//    @Query(value = "" +
//            "SELECT CARD_INFO.CATEGORY                             AS category, " +
//            "       COUNT(CASE " +
//            "               WHEN CARD_INFO.CURRENT_MONTH_TOTAL_PAYMENT > 0 " +
//            "                       THEN CARD_INFO.ACCOUNT_NO END) AS currentMonthTotalAccount, " +
//            "       SUM(CARD_INFO.CURRENT_MONTH_TOTAL_PAYMENT)     AS currentMonthTotalPayment, " +
//            "       COUNT(CASE " +
//            "               WHEN CARD_INFO.LAST_DATE_TOTAL_PAYMENT >= 0 " +
//            "                       THEN CARD_INFO.ACCOUNT_NO END) AS lastDateTotalAccount, " +
//            "       SUM(CARD_INFO.LAST_DATE_TOTAL_PAYMENT)         AS lastDateTotalPayment, " +
//            "       REPLACE('[' || " +
//            "               RTRIM( " +
//            "                 XMLAGG( " +
//            "                   XMLELEMENT(e, '{' " +
//            "                                   || '\"accountNo\":\"' || CARD_INFO.ACCOUNT_NO || '\", ' " +
//            "                                   || '\"accountName\":\"' || CARD_INFO.ACCOUNT_NAME || '\", ' " +
//            "                                   || '\"currentMonthPayment\":\"' || CARD_INFO.CURRENT_MONTH_TOTAL_PAYMENT || '\", ' " +
//            "                                   || '\"lastDatePayment\":\"' || CARD_INFO.LAST_DATE_TOTAL_PAYMENT || '\", ' " +
//            "                                   || '\"overdue\":\"' || CARD_INFO.OPENING_OVER_DUE || '\", ' " +
//            "                                   || '\"outstanding\":\"' || CARD_INFO.OUT_STANDING || '\", ' " +
//            "                                   || '\"dealerName\":\"' || CARD_INFO.DEALER_NAME || '\", ' " +
//            "                                   || '\"lastPaymentDate\":\"' || TO_CHAR(CARD_INFO.LAST_PAYMENT_DATE, 'DD.MM.YYYY') || " +
//            "                                 '\" ' " +
//            "                                   || '}', ',') " +
//            "                       .extract('//text()') ORDER BY CARD_INFO.LAST_PAYMENT_DATE DESC) " +
//            "                     .getclobval(), ', ') " +
//            "                 || ']', '&quot;', '\"')               AS paymentDetails " +
//            "FROM (SELECT CABI.CONTRACT_ID                                                                      AS ACCOUNT_NO, " +
//            "             CABI.CARD_NAME                                                                    AS ACCOUNT_NAME, " +
//            "             CADI.DEALER_NAME                                                                     AS DEALER_NAME, " +
//            "             COALESCE(CADI.OPENING_OVER_DUE, 0)                                                   AS OPENING_OVER_DUE, " +
//            "             COALESCE(CADI.OUTSTANDING_AMOUNT, '0')                                                     AS OUT_STANDING, " +
//            "             COALESCE(CPCM.CURRENT_MONTH_TOTAL_PAYMENT, 0)                                        AS CURRENT_MONTH_TOTAL_PAYMENT, " +
//            "             COALESCE(CPCM.LAST_DATE_TOTAL_PAYMENT, 0)                                            AS LAST_DATE_TOTAL_PAYMENT, " +
//            "             DECODE(COALESCE(CPCM.LAST_PAYMENT, 0), 0, CADI.LAST_PAID_AMOUNT, CPCM.LAST_PAYMENT)  AS LAST_PAYMENT, " +
//            "             DECODE(CPCM.LAST_PAYMENT_DATE, NULL, CADI.LAST_PAYMENT_DATE, CPCM.LAST_PAYMENT_DATE) AS LAST_PAYMENT_DATE, " +
//            "             CASE " +
//            "               WHEN COALESCE(CPCM.CURRENT_MONTH_TOTAL_PAYMENT, 0) >= CADI.OPENING_OVER_DUE THEN 'Full amount paid' " +
//            "               WHEN COALESCE(CPCM.CURRENT_MONTH_TOTAL_PAYMENT, 0) <> 0 AND CADI.DPD_BUCKET < CADI.CURRENT_DPD_BUCKET " +
//            "                       THEN 'Partial paid but forward flow to upper bucket' " +
//            "               WHEN COALESCE(CPCM.CURRENT_MONTH_TOTAL_PAYMENT, 0) = 0 THEN 'No payment during the month' " +
//            "               WHEN NPL_RULE.NPL_QUEUE_ID IS NOT NULL THEN 'Probable CL (As on date on Dealer Queue)' " +
//            "               WHEN NPL_RULE.NPLMO_QUEUE_ID IS NOT NULL THEN 'Probable CL list as per month beginning distribution' " +
//            "               WHEN NPL_RULE.NPL_ID IS NOT NULL THEN 'Existing CL' " +
//            "               ELSE 'Others' END                                                                  AS CATEGORY " +
//            "      FROM CARD_ACCOUNT_DISTRIBUTION_INFO CADI " +
//            "             JOIN CARD_ACCOUNT_BASIC_INFO CABI ON CABI.ID = CADI.CARD_ACCOUNT_BASIC_INFO_ID " +
//            "             LEFT JOIN (SELECT CP.CONTRACT_ID        AS ACCOUNT_NO, " +
//            "                               SUM(CASE " +
//            "                                     WHEN CP.PAYMENT_DATE BETWEEN :startDate AND :endDate " +
//            "                                             THEN CP.PAYMENT_AMOUNT " +
//            "                                     ELSE 0 END)    AS CURRENT_MONTH_TOTAL_PAYMENT, " +
//            "                               SUM(CASE " +
//            "                                     WHEN TRUNC(CP.PAYMENT_DATE, 'DD') = TRUNC(SYSDATE - 1, 'DD') " +
//            "                                             THEN CP.PAYMENT_AMOUNT " +
//            "                                     ELSE 0 END)    AS LAST_DATE_TOTAL_PAYMENT, " +
//            "                               MAX(CP.PAYMENT_DATE) AS LAST_PAYMENT_DATE, " +
//            "                               SUM(CASE " +
//            "                                     WHEN CP.PAYMENT_DATE = CPMD.PAYMENT_DATE " +
//            "                                             THEN CP.PAYMENT_AMOUNT " +
//            "                                     ELSE 0 END)    AS LAST_PAYMENT " +
//            "                        FROM CARD_PAYMENT CP " +
//            "                               LEFT JOIN (SELECT CP_IN.CONTRACT_ID, MAX(CP_IN.PAYMENT_DATE) PAYMENT_DATE " +
//            "                                          FROM CARD_PAYMENT CP_IN " +
//            "                                          GROUP BY CP_IN.CONTRACT_ID) CPMD ON CP.CONTRACT_ID = CPMD.CONTRACT_ID " +
//            "                        GROUP BY CP.CONTRACT_ID) CPCM ON CABI.CONTRACT_ID = CPCM.ACCOUNT_NO " +
//            "             LEFT JOIN (SELECT LADI_IN.ID               AS ID, " +
//            "                               CASE " +
//            "                                      (SELECT MAX(TO_NUMBER(NAME)) FROM AGE_CODE) " +
//            "                                         THEN 1 " +
//            "                                 ELSE NPL.BUCKET_ID END AS NPL_ID, " +
//            "                               NPLQ.BUCKET_ID           AS NPL_QUEUE_ID, " +
//            "                               NPLQMO.BUCKET_ID         AS NPLMO_QUEUE_ID " +
//            "                               LEFT JOIN (SELECT DISTINCT NARDBL.DPD_BUCKET_LIST_ID   AS BUCKET_ID, " +
//            "                                                          NARPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
//            "                                          FROM NPL_ACCOUNT_RULE NAR " +
//            "                                                 JOIN NPL_ACCOUNT_RULE_DPD_BUCKET_LIST NARDBL " +
//            "                                                   ON NAR.ID = NARDBL.NPLACCOUNT_RULE_ENTITY_ID " +
//            "                                                 JOIN NPL_ACCOUNT_RULE_PRODUCT_TYPE_LIST NARPTL " +
//            "                                                   ON NAR.ID = NARPTL.NPLACCOUNT_RULE_ENTITY_ID " +
//            "                                          ORDER BY BUCKET_ID, PRODUCT_ID) NPL " +
//            "                                 ON PTE.ID = NPL.PRODUCT_ID AND BC.ID = NPL.BUCKET_ID " +
//            "                               LEFT JOIN (SELECT DISTINCT NARQ.MIN_DPD, " +
//            "                                                          NARQDBL.DPD_BUCKET_LIST_ID   AS BUCKET_ID, " +
//            "                                                          NARQPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
//            "                                          FROM NPL_QUEUE_ACC_RULE NARQ " +
//            "                                                 JOIN NPL_QUEUE_ACC_RULE_DPD_BUCKET_LIST NARQDBL " +
//            "                                                   ON NARQ.ID = NARQDBL.NPLQUEUE_ACC_RULE_ENTITY_ID " +
//            "                                                 JOIN NPL_QUEUE_ACC_RULE_PRODUCT_TYPE_LIST NARQPTL " +
//            "                                                   ON NARQ.ID = NARQPTL.NPLQUEUE_ACC_RULE_ENTITY_ID " +
//            "                                          ORDER BY BUCKET_ID, PRODUCT_ID) NPLQ " +
//            "                                 ON PTE.ID = NPLQ.PRODUCT_ID AND BC.ID = NPLQ.BUCKET_ID " +
//            "                               LEFT JOIN (SELECT DISTINCT NARDBL.DPD_BUCKET_LIST_ID   AS BUCKET_ID, " +
//            "                                                          NARPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
//            "                                          FROM NPL_ACCOUNT_RULE NAR " +
//            "                                                 JOIN NPL_ACCOUNT_RULE_DPD_BUCKET_LIST NARDBL " +
//            "                                                   ON NAR.ID = NARDBL.NPLACCOUNT_RULE_ENTITY_ID " +
//            "                                                 JOIN NPL_ACCOUNT_RULE_PRODUCT_TYPE_LIST NARPTL " +
//            "                                                   ON NAR.ID = NARPTL.NPLACCOUNT_RULE_ENTITY_ID " +
//            "                                          ORDER BY BUCKET_ID, PRODUCT_ID) NPLMO " +
//            "                                 ON PTE.ID = NPLMO.PRODUCT_ID AND BMO.ID = NPLMO.BUCKET_ID " +
//            "                               LEFT JOIN (SELECT DISTINCT NARQ.MIN_DPD, " +
//            "                                                          NARQDBL.DPD_BUCKET_LIST_ID   AS BUCKET_ID, " +
//            "                                                          NARQPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
//            "                                          FROM NPL_QUEUE_ACC_RULE NARQ " +
//            "                                                 JOIN NPL_QUEUE_ACC_RULE_DPD_BUCKET_LIST NARQDBL " +
//            "                                                   ON NARQ.ID = NARQDBL.NPLQUEUE_ACC_RULE_ENTITY_ID " +
//            "                                                 JOIN NPL_QUEUE_ACC_RULE_PRODUCT_TYPE_LIST NARQPTL " +
//            "                                                   ON NARQ.ID = NARQPTL.NPLQUEUE_ACC_RULE_ENTITY_ID " +
//            "                                          ORDER BY BUCKET_ID, PRODUCT_ID) NPLQMO " +
//            "                                 ON PTE.ID = NPLQMO.PRODUCT_ID AND BMO.ID = NPLQMO.BUCKET_ID " +
//            "                        WHERE CADI_IN.DEALER_PIN IN (:pin) " +
//            "                          AND CADI_IN.LATEST = '1' " +
//            "                          AND CADI_IN.CREATED_DATE BETWEEN :startDate AND :endDate " +
//            "                        ORDER BY CADI_IN.ID) NPL_RULE ON CADI.ID = NPL_RULE.ID " +
//            "      WHERE CADI.DEALER_PIN IN (:pin) " +
//            "        AND CADI.LATEST = '1' " +
//            "        AND CADI.CREATED_DATE BETWEEN :startDate AND :endDate) CARD_INFO " +
//            "GROUP BY CARD_INFO.CATEGORY", nativeQuery = true)
//    List<Tuple> getCategorizedPaymentSummary(@Param("pin") List<String> dealerPins, @Param("startDate") Date startDate, @Param("endDate") Date endDate);


//    @Query(value = "SELECT LOAN_INFO.CATEGORY                             AS category, " +
//            "       COUNT(CASE " +
//            "               WHEN LOAN_INFO.CURRENT_MONTH_TOTAL_PAYMENT > 0.0 " +
//            "                       THEN LOAN_INFO.ACCOUNT_NO END) AS currentMonthTotalAccount, " +
//            "       SUM(LOAN_INFO.CURRENT_MONTH_TOTAL_PAYMENT)     AS currentMonthTotalPayment, " +
//            "       COUNT(CASE " +
//            "               WHEN LOAN_INFO.LAST_DATE_TOTAL_PAYMENT > 0 " +
//            "                       THEN LOAN_INFO.ACCOUNT_NO END) AS lastDateTotalAccount, " +
//            "       SUM(LOAN_INFO.LAST_DATE_TOTAL_PAYMENT)         AS lastDateTotalPayment, " +
//            "       REPLACE('[' || " +
//            "               RTRIM( " +
//            "                 XMLAGG( " +
//            "                   XMLELEMENT(e, '{' " +
//            "                                   || '\\\"accountNo\\\":\\\"' || LOAN_INFO.ACCOUNT_NO || '\\\", ' " +
//            "                                   || '\\\"accountName\\\":\\\"' || LOAN_INFO.ACCOUNT_NAME || '\\\", ' " +
//            "                                   || '\\\"currentMonthPayment\\\":\\\"' || LOAN_INFO.CURRENT_MONTH_TOTAL_PAYMENT || '\\\", ' " +
//            "                                   || '\\\"lastDatePayment\\\":\\\"' || LOAN_INFO.LAST_DATE_TOTAL_PAYMENT || '\\\", ' " +
//            "                                   || '\\\"overdue\\\":\\\"' || LOAN_INFO.OPENING_OVER_DUE || '\\\", ' " +
//            "                                   || '\\\"outstanding\\\":\\\"' || LOAN_INFO.OUT_STANDING || '\\\", ' " +
//            "                                   || '\\\"dealerName\\\":\\\"' || LOAN_INFO.DEALER_NAME || '\\\", ' " +
//            "                                   || '\\\"lastPaymentDate\\\":\\\"' || TO_CHAR(LOAN_INFO.LAST_PAYMENT_DATE, 'DD.MM.YYYY') || " +
//            "                                 '\\\" ' " +
//            "                                   || '}', ',') " +
//            "                       .extract('//text()') ORDER BY LOAN_INFO.LAST_PAYMENT_DATE DESC) " +
//            "                     .getclobval(), ', ') " +
//            "                 || ']', '&quot;', '\\\"')              AS paymentDetails " +
//            "FROM (SELECT LABI.CONTRACT_ID                                                                     AS ACCOUNT_NO, " +
//            "             LABI.CARD_NAME                                                                       AS ACCOUNT_NAME, " +
//            "             LADI.DEALER_NAME                                                                     AS DEALER_NAME, " +
//            "             COALESCE(LADI.OPENING_OVER_DUE, 0)                                                   AS OPENING_OVER_DUE, " +
//            "             COALESCE(LADI.OUT_STANDING, '0')                                                     AS OUT_STANDING, " +
//            "             COALESCE(LPCM.CURRENT_MONTH_TOTAL_PAYMENT, 0)                                        AS CURRENT_MONTH_TOTAL_PAYMENT, " +
//            "             COALESCE(LPCM.LAST_DATE_TOTAL_PAYMENT, 0)                                            AS LAST_DATE_TOTAL_PAYMENT, " +
//            "             DECODE(COALESCE(LPCM.LAST_PAYMENT, 0), 0, LADI.LAST_PAID_AMOUNT, LPCM.LAST_PAYMENT)  AS LAST_PAYMENT, " +
//            "             DECODE(LPCM.LAST_PAYMENT_DATE, NULL, LADI.LAST_PAYMENT_DATE, LPCM.LAST_PAYMENT_DATE) AS LAST_PAYMENT_DATE, " +
//            "             CASE " +
//            "               WHEN COALESCE(LPCM.CURRENT_MONTH_TOTAL_PAYMENT, 0) >= LADI.OPENING_OVER_DUE THEN 'Full amount paid' " +
//            "               WHEN COALESCE(LPCM.CURRENT_MONTH_TOTAL_PAYMENT, 0) <> 0 AND LADI.AGE_CODE < LADI.CURRENT_AGE_CODE " +
//            "                       THEN 'Partial paid but forward flow to upper bucket' " +
//            "               WHEN COALESCE(LPCM.CURRENT_MONTH_TOTAL_PAYMENT, 0) = 0 THEN 'No payment during the month' " +
//            "               WHEN NPL_RULE.NPL_QUEUE_ID IS NOT NULL THEN 'Probable CL (As on date on Dealer Queue)' " +
//            "               WHEN NPL_RULE.NPLMO_QUEUE_ID IS NOT NULL THEN 'Probable CL list as per month beginning distribution' " +
//            "               WHEN NPL_RULE.NPL_ID IS NOT NULL THEN 'Existing CL' " +
//            "               ELSE 'Others' END                                                                  AS CATEGORY " +
//            "      FROM CARD_ACCOUNT_DISTRIBUTION_INFO LADI " +
//            "             JOIN CARD_ACCOUNT_BASIC_INFO LABI ON LABI.ID = LADI.CARD_ACCOUNT_BASIC_INFO_ID " +
//            "             LEFT JOIN (SELECT LP.ACCOUNT_NO        AS ACCOUNT_NO, " +
//            "                               SUM(CASE " +
//            "                                     WHEN LP.PAYMENT_DATE BETWEEN :startDate AND :endDate " +
//            "                                             THEN LP.PAYMENT " +
//            "                                     ELSE 0 END)    AS CURRENT_MONTH_TOTAL_PAYMENT, " +
//            "                               SUM(CASE " +
//            "                                     WHEN TRUNC(LP.PAYMENT_DATE, 'DD') = TRUNC(SYSDATE - 1, 'DD') " +
//            "                                             THEN LP.PAYMENT " +
//            "                                     ELSE 0 END)    AS LAST_DATE_TOTAL_PAYMENT, " +
//            "                               MAX(LP.PAYMENT_DATE) AS LAST_PAYMENT_DATE, " +
//            "                               SUM(CASE " +
//            "                                     WHEN LP.PAYMENT_DATE = LPMD.PAYMENT_DATE " +
//            "                                             THEN LP.PAYMENT " +
//            "                                     ELSE 0 END)    AS LAST_PAYMENT " +
//            "                        FROM LOAN_PAYMENT LP " +
//            "                               LEFT JOIN (SELECT LP_IN.ACCOUNT_NO, MAX(LP_IN.PAYMENT_DATE) PAYMENT_DATE " +
//            "                                          FROM LOAN_PAYMENT LP_IN " +
//            "                                          GROUP BY LP_IN.ACCOUNT_NO) LPMD ON LP.ACCOUNT_NO = LPMD.ACCOUNT_NO " +
//            "                        GROUP BY LP.ACCOUNT_NO) LPCM ON LABI.CONTRACT_ID = LPCM.ACCOUNT_NO " +
//            "             LEFT JOIN (SELECT LADI_IN.ID               AS ID, " +
//            "                               CASE " +
//            "                                 WHEN TO_NUMBER(LADI_IN.CURRENT_AGE_CODE) > " +
//            "                                      (SELECT MAX(TO_NUMBER(NAME)) FROM AGE_CODE) " +
//            "                                         THEN 1 " +
//            "                                 ELSE NPL.BUCKET_ID END AS NPL_ID, " +
//            "                               NPLQ.BUCKET_ID           AS NPL_QUEUE_ID, " +
//            "                               NPLQMO.BUCKET_ID         AS NPLMO_QUEUE_ID " +
//            "                        FROM CARD_ACCOUNT_DISTRIBUTION_INFO LADI_IN " +
//            "                               LEFT JOIN AGE_CODE BC ON BC.NAME = CEIL(LADI_IN.AGE_CODE) " +
//            "                               LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON LADI_IN.SCHEME_CODE = PTE.CODE " +
//            "                               LEFT JOIN (SELECT DISTINCT NARDBL.AGE_CODE_LIST_ID     AS BUCKET_ID, " +
//            "                                                          NARPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
//            "                                          FROM NPL_ACCOUNT_RULE_CARD NAR " +
//            "                                                 JOIN NPL_ACCOUNT_RULE_CARD_AGE_CODE_LIST NARDBL " +
//            "                                                   ON NAR.ID = NARDBL.NPLACCOUNT_RULE_CARD_ENTITY_ID " +
//            "                                                 JOIN NPL_ACCOUNT_RULE_CARD_PRODUCT_TYPE_LIST NARPTL " +
//            "                                                   ON NAR.ID = NARPTL.NPLACCOUNT_RULE_CARD_ENTITY_ID " +
//            "                                          ORDER BY BUCKET_ID, PRODUCT_ID) NPL " +
//            "                                 ON PTE.ID = NPL.PRODUCT_ID AND BC.ID = NPL.BUCKET_ID " +
//            "                               LEFT JOIN (SELECT DISTINCT NARQ.MIN_DPD, " +
//            "                                                          NARQDBL.AGE_CODE_LIST_ID     AS BUCKET_ID, " +
//            "                                                          NARQPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
//            "                                          FROM NPL_QUEUE_ACC_RULE_CARD NARQ " +
//            "                                                 JOIN NPL_QUEUE_ACC_RULE_CARD_AGE_CODE_LIST NARQDBL " +
//            "                                                   ON NARQ.ID = NARQDBL.NPLQUEUE_ACC_RULE_CARD_ENTITY_ID " +
//            "                                                 JOIN NPL_QUEUE_ACC_RULE_CARD_PRODUCT_TYPE_LIST NARQPTL " +
//            "                                                   ON NARQ.ID = NARQPTL.NPLQUEUE_ACC_RULE_CARD_ENTITY_ID " +
//            "                                          ORDER BY BUCKET_ID, PRODUCT_ID) NPLQ " +
//            "                                 ON PTE.ID = NPLQ.PRODUCT_ID AND BC.ID = NPLQ.BUCKET_ID " +
//            "                               LEFT JOIN AGE_CODE BMO ON BMO.NAME = CEIL(LADI_IN.AGE_CODE) " +
//            "                               LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON LADI_IN.SCHEME_CODE = PTE.CODE " +
//            "                               LEFT JOIN (SELECT DISTINCT NARDBL.AGE_CODE_LIST_ID     AS BUCKET_ID, " +
//            "                                                          NARPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
//            "                                          FROM NPL_ACCOUNT_RULE_CARD NAR " +
//            "                                                 JOIN NPL_ACCOUNT_RULE_CARD_AGE_CODE_LIST NARDBL " +
//            "                                                   ON NAR.ID = NARDBL.NPLACCOUNT_RULE_CARD_ENTITY_ID " +
//            "                                                 JOIN NPL_ACCOUNT_RULE_CARD_PRODUCT_TYPE_LIST NARPTL " +
//            "                                                   ON NAR.ID = NARPTL.NPLACCOUNT_RULE_CARD_ENTITY_ID " +
//            "                                          ORDER BY BUCKET_ID, PRODUCT_ID) NPLMO " +
//            "                                 ON PTE.ID = NPLMO.PRODUCT_ID AND BMO.ID = NPLMO.BUCKET_ID " +
//            "                               LEFT JOIN (SELECT DISTINCT NARQ.MIN_DPD, " +
//            "                                                          NARQDBL.AGE_CODE_LIST_ID     AS BUCKET_ID, " +
//            "                                                          NARQPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
//            "                                          FROM NPL_QUEUE_ACC_RULE_CARD NARQ " +
//            "                                                 JOIN NPL_QUEUE_ACC_RULE_CARD_AGE_CODE_LIST NARQDBL " +
//            "                                                   ON NARQ.ID = NARQDBL.NPLQUEUE_ACC_RULE_CARD_ENTITY_ID " +
//            "                                                 JOIN NPL_QUEUE_ACC_RULE_CARD_PRODUCT_TYPE_LIST NARQPTL " +
//            "                                                   ON NARQ.ID = NARQPTL.NPLQUEUE_ACC_RULE_CARD_ENTITY_ID " +
//            "                                          ORDER BY BUCKET_ID, PRODUCT_ID) NPLQMO " +
//            "                                 ON PTE.ID = NPLQMO.PRODUCT_ID AND BMO.ID = NPLQMO.BUCKET_ID " +
//            "                        WHERE LADI_IN.DEALER_PIN IN (:pin) " +
//            "                          AND LADI_IN.LATEST = '1' " +
//            "                          AND LADI_IN.CREATED_DATE BETWEEN :startDate AND :endDate " +
//            "                        ORDER BY LADI_IN.ID) NPL_RULE ON LADI.ID = NPL_RULE.ID " +
//            "      WHERE LADI.DEALER_PIN IN (:pin) " +
//            "        AND LADI.LATEST = '1' " +
//            "        AND LADI.CREATED_DATE BETWEEN :startDate AND :endDate) LOAN_INFO " +
//            "GROUP BY LOAN_INFO.CATEGORY ", nativeQuery = true)

    @Query(value = "SELECT LOAN_INFO.CATEGORY                             AS category, " +
            "       COUNT(CASE " +
            "               WHEN LOAN_INFO.CURRENT_MONTH_TOTAL_PAYMENT = 0 " +
            "                       THEN LOAN_INFO.ACCOUNT_NO END) AS currentMonthTotalAccount, " +
            "       SUM(LOAN_INFO.CURRENT_MONTH_TOTAL_PAYMENT)     AS currentMonthTotalPayment, " +
            "       COUNT(CASE " +
            "               WHEN LOAN_INFO.LAST_DATE_TOTAL_PAYMENT > 0 " +
            "                       THEN LOAN_INFO.ACCOUNT_NO END) AS lastDateTotalAccount, " +
            "       SUM(LOAN_INFO.LAST_DATE_TOTAL_PAYMENT)         AS lastDateTotalPayment, " +
            "       REPLACE('[' || " +
            "               RTRIM( " +
            "                 XMLAGG( " +
            "                   XMLELEMENT(e, '{' " +
            "                                   || '\\\\\\\"accountNo\\\\\\\":\\\\\\\"' || LOAN_INFO.ACCOUNT_NO || '\\\\\\\", ' " +
            "                                   || '\\\\\\\"accountName\\\\\\\":\\\\\\\"' || LOAN_INFO.ACCOUNT_NAME || '\\\\\\\", ' " +
            "                                   || '\\\\\\\"currentMonthPayment\\\\\\\":\\\\\\\"' || LOAN_INFO.CURRENT_MONTH_TOTAL_PAYMENT || '\\\\\\\", ' " +
            "                                   || '\\\\\\\"lastDatePayment\\\\\\\":\\\\\\\"' || LOAN_INFO.LAST_DATE_TOTAL_PAYMENT || '\\\\\\\", ' " +
            "                                   || '\\\\\\\"overdue\\\\\\\":\\\\\\\"' || LOAN_INFO.OPENING_OVER_DUE || '\\\\\\\", ' " +
            "                                   || '\\\\\\\"outstanding\\\\\\\":\\\\\\\"' || LOAN_INFO.OUT_STANDING || '\\\\\\\", ' " +
            "                                   || '\\\\\\\"dealerName\\\\\\\":\\\\\\\"' || LOAN_INFO.DEALER_NAME || '\\\\\\\", ' " +
            "                                   || '\\\\\\\"lastPaymentDate\\\\\\\":\\\\\\\"' || TO_CHAR(LOAN_INFO.LAST_PAYMENT_DATE, 'DD.MM.YYYY') || " +
            "                                 '\\\\\\\" ' " +
            "                                   || '}', ',') " +
            "                       .extract('//text()') ORDER BY LOAN_INFO.LAST_PAYMENT_DATE DESC) " +
            "                     .getclobval(), ', ') " +
            "                 || ']', '&quot;', '\\\\\\\"')              AS paymentDetails " +
            "FROM (SELECT CABI.CONTRACT_ID                                                                     AS ACCOUNT_NO, " +
            "       CABI.CARD_NAME                                                                       AS ACCOUNT_NAME, " +
            "       CADI.DEALER_NAME                                                                     AS DEALER_NAME, " +
            "       COALESCE(CADI.OPENING_OVER_DUE, 0)                                                   AS OPENING_OVER_DUE, " +
            "       COALESCE(CADI.OUT_STANDING, '0')                                                     AS OUT_STANDING, " +
            "       COALESCE(LPCM.CURRENT_MONTH_TOTAL_PAYMENT, 0)                                        AS CURRENT_MONTH_TOTAL_PAYMENT, " +
            "       COALESCE(LPCM.LAST_DATE_TOTAL_PAYMENT, 0)                                            AS LAST_DATE_TOTAL_PAYMENT, " +
            "       DECODE(COALESCE(LPCM.LAST_PAYMENT, 0), 0, CADI.LAST_PAID_AMOUNT, LPCM.LAST_PAYMENT)  AS LAST_PAYMENT, " +
            "       DECODE(LPCM.LAST_PAYMENT_DATE, NULL, CADI.LAST_PAYMENT_DATE, LPCM.LAST_PAYMENT_DATE) AS LAST_PAYMENT_DATE, " +
            "       CASE " +
            "         WHEN COALESCE(LPCM.CURRENT_MONTH_TOTAL_PAYMENT, 0) >= CADI.OPENING_OVER_DUE THEN 'Full amount paid' " +
            "         WHEN COALESCE(LPCM.CURRENT_MONTH_TOTAL_PAYMENT, 0) <> 0 AND CADI.AGE_CODE < CADI.CURRENT_AGE_CODE " +
            "                 THEN 'Partial paid but forward flow to upper bucket' " +
            "         WHEN (COALESCE(LPCM.CURRENT_MONTH_TOTAL_PAYMENT, 0) > 0 AND LPCM.CURRENT_MONTH_TOTAL_PAYMENT < CADI.OPENING_OVER_DUE)AND CADI.AGE_CODE = CADI.CURRENT_AGE_CODE " +
            "           THEN 'Partial Paid And Stable' " +
            "         WHEN COALESCE(LPCM.CURRENT_MONTH_TOTAL_PAYMENT, 0) = 0 THEN 'No payment during the month' " +
            "         WHEN NPL_RULE.NPL_QUEUE_ID IS NOT NULL THEN 'Probable CL (As on date on Dealer Queue)' " +
            "         WHEN NPL_RULE.NPLMO_QUEUE_ID IS NOT NULL THEN 'Probable CL list as per month beginning distribution' " +
            "         WHEN NPL_RULE.NPL_ID IS NOT NULL THEN 'Existing CL' " +
            "         ELSE 'Others' END                                                                  AS CATEGORY " +
            "FROM CARD_ACCOUNT_DISTRIBUTION_INFO CADI " +
            "                JOIN CARD_ACCOUNT_BASIC_INFO CABI ON CABI.ID = CADI.CARD_ACCOUNT_BASIC_INFO_ID " +
            "    LEFT JOIN (SELECT LP.CONTRACT_NO AS contracNo, " +
            "                      SUM(TO_NUMBER(LP.BILLING_AMOUNT)) AS CURRENT_MONTH_TOTAL_PAYMENT, " +
            "                      SUM(CASE " +
            "                            WHEN TO_CHAR(TO_DATE(POST_DATE),'DD-MM-YYYY') = TO_CHAR(SYSDATE-1,'DD-MM-YYYY') " +
            "                                    THEN TO_NUMBER(LP.BILLING_AMOUNT) " +
            "                            ELSE 0 END)    AS LAST_DATE_TOTAL_PAYMENT, " +
            "                      MAX(LP.POST_DATE) AS LAST_PAYMENT_DATE, " +
            "                      SUM(CASE " +
            "                            WHEN LP.POST_DATE = LPMD.PAYMENT_DATE " +
            "                                    THEN TO_NUMBER(LP.BILLING_AMOUNT) " +
            "                            ELSE 0 END)    AS LAST_PAYMENT " +
            "               FROM APPROVE_TRANSACTIONS_UNBILLED_TRANSACTIONS_PAYMENT LP " +
            "                      LEFT JOIN (SELECT LP_IN.CONTRACT_NO, MAX(LP_IN.POST_DATE) PAYMENT_DATE " +
            "                                 FROM APPROVE_TRANSACTIONS_UNBILLED_TRANSACTIONS_PAYMENT LP_IN " +
            "                                 GROUP BY LP_IN.CONTRACT_NO) LPMD ON LP.CONTRACT_NO = LPMD.CONTRACT_NO " +
            "               WHERE LP.POST_DATE BETWEEN :startDate AND :endDate " +
            "               GROUP BY LP.CONTRACT_NO) LPCM ON LPCM.contracNo = CABI.CONTRACT_ID " +
            "LEFT JOIN (SELECT CADI_IN.ID AS  id, " +
            "                  CASE " +
            "                    WHEN TO_NUMBER(CADI_IN.CURRENT_AGE_CODE) > " +
            "                         (SELECT MAX(TO_NUMBER(NAME)) FROM AGE_CODE) " +
            "                            THEN 1 " +
            "                    ELSE NPL.BUCKET_ID END AS NPL_ID, " +
            "                  NPLQ.BUCKET_ID           AS NPL_QUEUE_ID, " +
            "                  NPLQMO.BUCKET_ID         AS NPLMO_QUEUE_ID " +
            "           FROM CARD_ACCOUNT_DISTRIBUTION_INFO CADI_IN " +
            "                  LEFT JOIN AGE_CODE BC ON BC.NAME = CADI_IN.AGE_CODE " +
            "                  LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON CADI_IN.SCHEME_CODE = PTE.CODE " +
            "                  LEFT JOIN (SELECT DISTINCT NARDBL.AGE_CODE_LIST_ID     AS BUCKET_ID, " +
            "                                             NARPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
            "                             FROM NPL_ACCOUNT_RULE_CARD NAR " +
            "                                    JOIN NPL_ACCOUNT_RULE_CARD_AGE_CODE_LIST NARDBL " +
            "                                      ON NAR.ID = NARDBL.NPLACCOUNT_RULE_CARD_ENTITY_ID " +
            "                                    JOIN NPL_ACCOUNT_RULE_CARD_PRODUCT_TYPE_LIST NARPTL " +
            "                                      ON NAR.ID = NARPTL.NPLACCOUNT_RULE_CARD_ENTITY_ID " +
            "                             ORDER BY BUCKET_ID, PRODUCT_ID) NPL " +
            "                    ON PTE.ID = NPL.PRODUCT_ID AND BC.ID = NPL.BUCKET_ID " +
            "                  LEFT JOIN (SELECT DISTINCT NARQ.MIN_DPD, " +
            "                                             NARQDBL.AGE_CODE_LIST_ID     AS BUCKET_ID, " +
            "                                             NARQPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
            "                             FROM NPL_QUEUE_ACC_RULE_CARD NARQ " +
            "                                    JOIN NPL_QUEUE_ACC_RULE_CARD_AGE_CODE_LIST NARQDBL " +
            "                                      ON NARQ.ID = NARQDBL.NPLQUEUE_ACC_RULE_CARD_ENTITY_ID " +
            "                                    JOIN NPL_QUEUE_ACC_RULE_CARD_PRODUCT_TYPE_LIST NARQPTL " +
            "                                      ON NARQ.ID = NARQPTL.NPLQUEUE_ACC_RULE_CARD_ENTITY_ID " +
            "                             ORDER BY BUCKET_ID, PRODUCT_ID) NPLQ " +
            "                    ON PTE.ID = NPLQ.PRODUCT_ID AND BC.ID = NPLQ.BUCKET_ID " +
            "                  LEFT JOIN AGE_CODE BMO ON BMO.NAME = CADI_IN.AGE_CODE " +
            "                  LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON CADI_IN.SCHEME_CODE = PTE.CODE " +
            "                  LEFT JOIN (SELECT DISTINCT NARDBL.AGE_CODE_LIST_ID     AS BUCKET_ID, " +
            "                                             NARPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
            "                             FROM NPL_ACCOUNT_RULE_CARD NAR " +
            "                                    JOIN NPL_ACCOUNT_RULE_CARD_AGE_CODE_LIST NARDBL " +
            "                                      ON NAR.ID = NARDBL.NPLACCOUNT_RULE_CARD_ENTITY_ID " +
            "                                    JOIN NPL_ACCOUNT_RULE_CARD_PRODUCT_TYPE_LIST NARPTL " +
            "                                      ON NAR.ID = NARPTL.NPLACCOUNT_RULE_CARD_ENTITY_ID " +
            "                             ORDER BY BUCKET_ID, PRODUCT_ID) NPLMO " +
            "                    ON PTE.ID = NPLMO.PRODUCT_ID AND BMO.ID = NPLMO.BUCKET_ID " +
            "                  LEFT JOIN (SELECT DISTINCT NARQ.MIN_DPD, " +
            "                                             NARQDBL.AGE_CODE_LIST_ID     AS BUCKET_ID, " +
            "                                             NARQPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
            "                             FROM NPL_QUEUE_ACC_RULE_CARD NARQ " +
            "                                    JOIN NPL_QUEUE_ACC_RULE_CARD_AGE_CODE_LIST NARQDBL " +
            "                                      ON NARQ.ID = NARQDBL.NPLQUEUE_ACC_RULE_CARD_ENTITY_ID " +
            "                                    JOIN NPL_QUEUE_ACC_RULE_CARD_PRODUCT_TYPE_LIST NARQPTL " +
            "                                      ON NARQ.ID = NARQPTL.NPLQUEUE_ACC_RULE_CARD_ENTITY_ID " +
            "                             ORDER BY BUCKET_ID, PRODUCT_ID) NPLQMO " +
            "                    ON PTE.ID = NPLQMO.PRODUCT_ID AND BMO.ID = NPLQMO.BUCKET_ID " +
            "           WHERE CADI_IN.DEALER_PIN IN (:pin) AND CADI_IN.CREATED_DATE BETWEEN :startDate AND :endDate AND CADI_IN.LATEST = 1) " +
            "          NPL_RULE ON CADI.ID = NPL_RULE.ID " +
            "WHERE CADI.DEALER_PIN IN (:pin) AND CADI.CREATED_DATE BETWEEN :startDate AND :endDate) LOAN_INFO " +
            "GROUP BY LOAN_INFO.CATEGORY ", nativeQuery = true)
    List<Tuple> getCategorizedPaymentSummary(@Param("pin") List<String> dealerPins, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "" + "WITH MAX_AGE_CODE AS (SELECT MAX(CEIL(TO_NUMBER(NAME))) AGE_CODE FROM AGE_CODE),\n" +
            "      PAR AS (SELECT DISTINCT PARDBL.AGE_CODE_LIST_ID AS AGE_CODE_ID\n" +
            "              FROM PAR_ACC_RULE_CARD PAR\n" +
            "                     JOIN PAR_ACC_RULE_CARD_AGE_CODE_LIST PARDBL ON PAR.ID = PARDBL.PARACCOUNT_RULE_CARD_ENTITY_ID\n" +
            "              ORDER BY AGE_CODE_ID),\n" +
            "      PARQ AS (SELECT DISTINCT PARQ.MIN_AGE_CODE,\n" +
            "                               PARQDBL.AGE_CODE_ENTITIES_ID AS AGE_CODE_ID,\n" +
            "                               PARQPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID\n" +
            "               FROM PAR_QUEUE_ACC_RULE_CARD PARQ\n" +
            "                      JOIN PAR_QUEUE_ACC_RULE_CARD_AGE_CODE_ENTITIES PARQDBL\n" +
            "                        ON PARQ.ID = PARQDBL.PARQUEUE_ACCOUNT_RULE_CARD_ID\n" +
            "                      JOIN PAR_QUEUE_ACC_RULE_CARD_PRODUCT_TYPE_LIST PARQPTL\n" +
            "                        ON PARQ.ID = PARQPTL.PARQUEUE_ACCOUNT_RULE_CARD_ID\n" +
            "               ORDER BY AGE_CODE_ID, PRODUCT_ID),\n" +
            "      TOUCH_COUNTS AS (SELECT CADI.ID, COUNT(CI.ID) + COUNT(FUE.ID) + COUNT(PTP.ID) +\n" +
            "                                       COUNT(VLE.ID) + COUNT(DIARY.ID) + COUNT(DAILY.ID) AS TOTAL_TOUCHED\n" +
            "                       FROM CARD_ACCOUNT_DISTRIBUTION_INFO CADI\n" +
            "                              JOIN CARD_ACCOUNT_BASIC_INFO CABI ON CADI.CARD_ACCOUNT_BASIC_INFO_ID = CABI.ID\n" +
            "                              LEFT JOIN CONTACT_INFO CI ON CABI.CUSTOMER_ID = CI.CUSTOMER_ID AND\n" +
            "                                                           CI.CREATED_DATE BETWEEN :startDate AND :endDate\n" +
            "                              LEFT JOIN CARD_FOLLOW_UP FUE ON CABI.CUSTOMER_ID = FUE.CUSTOMER_ID AND\n" +
            "                                                              FUE.CREATED_DATE BETWEEN :startDate AND :endDate\n" +
            "                              LEFT JOIN CARD_PTP PTP ON CABI.CUSTOMER_ID = PTP.CUSTOMER_ID AND\n" +
            "                                                        PTP.CREATED_DATE BETWEEN :startDate AND :endDate\n" +
            "                              LEFT JOIN VISIT_LEDGER_ENTITY VLE ON CABI.CONTRACT_ID = VLE.ACCOUNT_CARD_NUMBER AND\n" +
            "                                                                   VLE.CREATED_DATE BETWEEN :startDate AND :endDate\n" +
            "                              LEFT JOIN DAIRY_NOTES DIARY ON CABI.CUSTOMER_ID = DIARY.CUSTOMER_ID AND\n" +
            "                                                             DIARY.CREATED_DATE BETWEEN :startDate AND :endDate\n" +
            "                              LEFT JOIN DAILY_NOTES_CARD DAILY ON CABI.CUSTOMER_ID = DAILY.CUSTOMER_ID AND\n" +
            "                                                                  DAILY.CREATED_DATE BETWEEN :startDate AND :endDate\n" +
            "                       WHERE CADI.CREATED_DATE BETWEEN :startDate AND :endDate\n" +
            "                         AND DEALER_PIN IN(:pin)\n" +
            "                         AND CADI.LATEST = '1'\n" +
            "                       GROUP BY CADI.ID),\n" +
            "      PAR_RULE AS (SELECT CADI_IN.ID                                   AS ID,\n" +
            "                          ACODE.NAME                                   AS AGE_CODE,\n" +
            "                          PTE.NAME                                     AS PRODUCT_TYPE,\n" +
            "                          CASE\n" +
            "                            WHEN CADI_IN.AGE_CODE <> CADI_IN.CURRENT_AGE_CODE\n" +
            "                                    THEN ACODE.PAR_RELEASE_CARD_ID END AS PAR_RELEASE_ID,\n" +
            "                          PAR.AGE_CODE_ID                              AS PAR_ID,\n" +
            "                          PARQ.AGE_CODE_ID                             AS PAR_QUEUE_ID,\n" +
            "                          CASE\n" +
            "                            WHEN TO_NUMBER(CADI_IN.AGE_CODE) + EXTRACT(DAY FROM SYSDATE) + 1 = PARQ.MIN_AGE_CODE\n" +
            "                                    THEN 1 ELSE 0 END                         AS PAR_QUE_NEXT_DAY\n" +
            "                   FROM CARD_ACCOUNT_DISTRIBUTION_INFO CADI_IN\n" +
            "                          LEFT JOIN AGE_CODE ACODE ON ACODE.NAME = CEIL(CADI_IN.CURRENT_AGE_CODE)\n" +
            "                          LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON CADI_IN.SCHEME_CODE = PTE.CODE\n" +
            "                          LEFT JOIN PAR ON ACODE.ID = PAR.AGE_CODE_ID\n" +
            "                          LEFT JOIN PARQ ON PTE.ID = PARQ.PRODUCT_ID AND ACODE.ID = PARQ.AGE_CODE_ID\n" +
            "                   WHERE CADI_IN.DEALER_PIN IN (:pin)\n" +
            "                     AND CADI_IN.LATEST = '1'\n" +
            "                     AND CADI_IN.CREATED_DATE BETWEEN :startDate AND :endDate\n" +
            "                   ORDER BY CADI_IN.ID),\n" +
            "      NPL AS (SELECT DISTINCT NARDBL.AGE_CODE_LIST_ID AS AGE_CODE_ID, NARPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID\n" +
            "              FROM NPL_ACCOUNT_RULE_CARD NAR\n" +
            "                     JOIN NPL_ACCOUNT_RULE_CARD_AGE_CODE_LIST NARDBL ON NAR.ID = NARDBL.NPLACCOUNT_RULE_CARD_ENTITY_ID\n" +
            "                     JOIN NPL_ACCOUNT_RULE_CARD_PRODUCT_TYPE_LIST NARPTL ON NAR.ID = NARPTL.NPLACCOUNT_RULE_CARD_ENTITY_ID\n" +
            "              ORDER BY AGE_CODE_ID, PRODUCT_ID),\n" +
            "      NPLQ AS (SELECT DISTINCT NARQ.MIN_AGE_CODE,\n" +
            "                               NARQDBL.AGE_CODE_LIST_ID     AS AGE_CODE_ID,\n" +
            "                               NARQPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID\n" +
            "               FROM NPL_QUEUE_ACC_RULE_CARD NARQ\n" +
            "                      JOIN NPL_QUEUE_ACC_RULE_CARD_AGE_CODE_LIST NARQDBL\n" +
            "                        ON NARQ.ID = NARQDBL.NPLQUEUE_ACC_RULE_CARD_ENTITY_ID\n" +
            "                      JOIN NPL_QUEUE_ACC_RULE_CARD_PRODUCT_TYPE_LIST NARQPTL ON NARQ.ID = NARQPTL.NPLQUEUE_ACC_RULE_CARD_ENTITY_ID\n" +
            "               ORDER BY AGE_CODE_ID, PRODUCT_ID),\n" +
            "      NPL_RULE AS (SELECT CADI_IN.ID                                      AS ID,\n" +
            "                          CASE\n" +
            "                            WHEN TO_NUMBER(CADI_IN.CURRENT_AGE_CODE) >\n" +
            "                                 (SELECT MAX(TO_NUMBER(NAME)) FROM AGE_CODE)\n" +
            "                                    THEN (SELECT MAX(TO_NUMBER(AGE_CODE)) FROM AGE_CODE) || '+'\n" +
            "                            ELSE AGE_CODE.NAME END                        AS AGE_CODE,\n" +
            "                          PTE.NAME                                        AS PRODUCT_TYPE,\n" +
            "                          CASE\n" +
            "                            WHEN CADI_IN.AGE_CODE <> CADI_IN.CURRENT_AGE_CODE\n" +
            "                                    THEN AGE_CODE.NPL_RELEASE_CARD_ID ELSE 0 END AS NPL_RELEASE_ID,\n" +
            "                          CASE\n" +
            "                            WHEN TO_NUMBER(CADI_IN.CURRENT_AGE_CODE) >\n" +
            "                                 (SELECT MAX(TO_NUMBER(NAME)) FROM AGE_CODE)\n" +
            "                                    THEN 1\n" +
            "                            ELSE NPL.AGE_CODE_ID END                      AS NPL_ID,\n" +
            "                          NPLQ.AGE_CODE_ID                                AS NPL_QUEUE_ID,\n" +
            "                          CASE\n" +
            "                            WHEN TO_NUMBER(CADI_IN.AGE_CODE) + EXTRACT(DAY FROM SYSDATE) + 1 = NPLQ.MIN_AGE_CODE\n" +
            "                                    THEN 1 END                            AS NPL_QUE_NEXT_DAY\n" +
            "                   FROM CARD_ACCOUNT_DISTRIBUTION_INFO CADI_IN\n" +
            "                          LEFT JOIN AGE_CODE AGE_CODE ON AGE_CODE.NAME = CEIL(CADI_IN.CURRENT_AGE_CODE)\n" +
            "                          LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON CADI_IN.SCHEME_CODE = PTE.CODE\n" +
            "                          LEFT JOIN NPL ON PTE.ID = NPL.PRODUCT_ID AND AGE_CODE.ID = NPL.AGE_CODE_ID\n" +
            "                          LEFT JOIN NPLQ ON PTE.ID = NPLQ.PRODUCT_ID AND AGE_CODE.ID = NPLQ.AGE_CODE_ID\n" +
            "                   WHERE CADI_IN.DEALER_PIN IN (:pin)\n" +
            "                     AND CADI_IN.LATEST = '1'\n" +
            "                     AND CADI_IN.CREATED_DATE BETWEEN :startDate AND :endDate\n" +
            "                   ORDER BY CADI_IN.ID)\n" +
            "       SELECT CADI.DEALER_PIN                                                    AS dealerPin,\n" +
            "        PGE.NAME                                                           AS product,\n" +
            "        CADI.CURRENT_AGE_CODE                                              AS dpdBucket,\n" +
            "        COUNT(DISTINCT CADI.ID)                                            AS totalAccount,\n" +
            "        COUNT(CASE WHEN TOUCH_COUNTS.TOTAL_TOUCHED > 0 THEN 1 END)         AS totalTouched,\n" +
            "        COUNT(CASE WHEN LOWER(CPTP.CPTP.CARD_PTP_STATUS) = 'broken' THEN 1 END) AS totalBrokenPtp,\n" +
            "        COUNT(PAR_RULE.PAR_RELEASE_ID)                                     AS totalParRelease,\n" +
            "        COUNT(PAR_RULE.PAR_ID)                                             AS totalParRemaining,\n" +
            "        COUNT(PAR_RULE.PAR_QUEUE_ID)                                       AS totalParQueue,\n" +
            "        COUNT(PAR_RULE.PAR_QUE_NEXT_DAY)                                   AS totalParQueueNextDay,\n" +
            "        COUNT(NPL_RULE.NPL_RELEASE_ID)                                     AS totalNplRelease,\n" +
            "        COUNT(NPL_RULE.NPL_ID)                                             AS totalNplRemaining,\n" +
            "        COUNT(NPL_RULE.NPL_QUEUE_ID)                                       AS totalNplQueue,\n" +
            "        COUNT(NPL_RULE.NPL_QUE_NEXT_DAY)                                   AS totalNplQueueNextDay\n" +
            "      FROM (SELECT ID,\n" +
            "              DEALER_PIN,\n" +
            "              CASE\n" +
            "                WHEN CEIL(TO_NUMBER(CURRENT_AGE_CODE)) > (SELECT AGE_CODE FROM MAX_AGE_CODE)\n" +
            "                        THEN (SELECT AGE_CODE FROM MAX_AGE_CODE) || '+'\n" +
            "                ELSE CEIL(TO_NUMBER(CURRENT_AGE_CODE)) || '' END CURRENT_AGE_CODE,\n" +
            "              CARD_ACCOUNT_BASIC_INFO_ID,\n" +
            "              SCHEME_CODE\n" +
            "       FROM CARD_ACCOUNT_DISTRIBUTION_INFO CADI_IN\n" +
            "       WHERE CADI_IN.DEALER_PIN IN (:pin)\n" +
            "         AND CADI_IN.LATEST = '1'\n" +
            "         AND CADI_IN.CREATED_DATE BETWEEN :startDate AND :endDate) CADI\n" +
            "        JOIN CARD_ACCOUNT_BASIC_INFO CABI ON CABI.ID = CADI.CARD_ACCOUNT_BASIC_INFO_ID\n" +
            "        JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE ON CABI.CUSTOMER_ID = CBIE.ID\n" +
            "        LEFT JOIN CARD_ACCOUNT_INFO CAI ON CABI.ID = CAI.CARD_ACCOUNT_BASIC_INFO_ID\n" +
            "        LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON CADI.SCHEME_CODE = PTE.CODE\n" +
            "        LEFT JOIN PRODUCT_GROUP_ENTITY PGE on PTE.PRODUCT_GROUP_ENTITY_ID = PGE.ID\n" +
            "        LEFT JOIN CARD_PTP CPTP\n" +
            "          ON CPTP.CUSTOMER_ID = CABI.ID AND CPTP.CREATED_DATE BETWEEN :startDate AND :endDate\n" +
            "        LEFT JOIN TOUCH_COUNTS ON CADI.ID = TOUCH_COUNTS.ID\n" +
            "        LEFT JOIN PAR_RULE ON CADI.ID = PAR_RULE.ID\n" +
            "        LEFT JOIN NPL_RULE ON CADI.ID = NPL_RULE.ID\n" +
            "     GROUP BY CADI.DEALER_PIN, PGE.NAME, CADI.CURRENT_AGE_CODE\n" +
            "     ORDER BY CADI.DEALER_PIN, PGE.NAME, CADI.CURRENT_AGE_CODE", nativeQuery = true)
    List<Tuple> getAccountWiseDistributionAndStatusSummary(@Param("pin") List<String> dealerPins, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT CBIE.ACCOUNT_NO, CBIE.NAME_ON_CARD, CBIE.DOB, CBIE.MOBILE_NO,CBIE.MOTHER_NAME,CBIE.EMAIL,CBIE.NID FROM CUSTOMER_BASIC_INFO_ENTITY CBIE " +
            " WHERE CBIE.ACCOUNT_NO = :accountNo" +
            " OR lower(CBIE.CUSTOMER_NAME) like '%'||DECODE(:customerName, NULL, 'CBIE.CUSTOMER_NAME', lower(:customerName))||'%' " +
            " OR lower(CBIE.MOTHER_NAME) like '%'||DECODE(:motherName, NULL, 'CBIE.MOTHER_NAME', lower(:motherName))||'%' " +
            " OR CBIE.MOBILE_NO = DECODE(:mobileNo, NULL, 'CBIE.MOBILE_NO', :mobileNo) " +
            " OR CBIE.NID = DECODE(:nid, NULL, 'CBIE.NID', :nid) " +
            " OR TO_CHAR(CBIE.DOB,'DD-MON-YY') = DECODE(:dob, NULL, 'CBIE.DOB', :dob) " +
            " OR CBIE.EMAIL = DECODE(:email, NULL, 'CBIE.EMAIL', :email) "+
            " OR CBIE.PASSPORT_NO = DECODE(:passportNo, NULL, 'CBIE.PASSPORT_NO', :passportNo) "+
            " OR CBIE.TIN = DECODE(:tin, NULL, 'CBIE.TIN', :tin) "+
            " OR CBIE.CLIENT_ID = DECODE(:clientId, NULL, 'CBIE.CLIENT_ID', :clientId) "+
            " OR CBIE.CONTRACT_ID = DECODE(:contractId, NULL, 'CBIE.CONTRACT_ID', :contractId) "
            ,nativeQuery = true)
    List<Tuple> getAdvancedSearchData(
            @Param("accountNo") String accountNo,
            @Param("customerName") String customerName,
            @Param("motherName") String motherName,
            @Param("mobileNo") String mobileNo,
            @Param("nid") String nid,
            @Param("dob") String dob,
            @Param("email") String email,
            @Param("passportNo") String passportNo,
            @Param("tin") String tin,
            @Param("clientId") String clientId,
            @Param("contractId") String contractId
    );
}
