package com.csinfotechbd.retail.loan.dashboard;


import com.csinfotechbd.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

/**
 * Created by Yasir Araphat
 * Created on 01 August, 2021
 */
@Repository
public interface RetailLoanDashboardRepository extends JpaRepository<LoanAccountDistributionInfo, Long> {

    /**
     * Account wise distribution and status summary for dealer, team leader, supervisor and manager dashboard.
     * Implemented by Yasir Araphat
     * on August 02, 2021
     *
     * @param dealerPins
     * @param startDate
     * @param endDate
     * @return
     */
    /*@Query(value = "WITH MAX_BUCKET AS (SELECT MAX(CEIL(TO_NUMBER(BUCKET_NAME))) BUCKET FROM DPDBUCKET_ENTITY), " +
            "     PAR AS (SELECT DISTINCT PARDBL.DPD_BUCKET_ENTITY_LIST_ID AS BUCKET_ID " +
            "             FROM PAR_ACC_RULE_LOAN PAR " +
            "                    JOIN PAR_ACC_RULE_LOAN_DPD_BUCKET_ENTITY_LIST PARDBL " +
            "                      ON PAR.ID = PARDBL.PARACCOUNT_RULE_LOAN_ENTITY_ID " +
            "             ORDER BY BUCKET_ID), " +
            "     PARQ AS (SELECT DISTINCT PARQ.MIN_DPD, " +
            "                              PARQDBL.DPD_BUCKET_ENTITY_LIST_ID AS BUCKET_ID, " +
            "                              PARQPTL.PRODUCT_TYPE_LIST_ID      AS PRODUCT_ID " +
            "              FROM PAR_QUEUE_ACC_RULE_LOAN PARQ " +
            "                     JOIN PAR_QUEUE_ACC_RULE_LOAN_DPD_BUCKET_ENTITY_LIST PARQDBL " +
            "                       ON PARQ.ID = PARQDBL.PARQUEUE_ACC_RULE_LOAN_ENTITY_ID " +
            "                     JOIN PAR_QUEUE_ACC_RULE_LOAN_PRODUCT_TYPE_LIST PARQPTL " +
            "                       ON PARQ.ID = PARQPTL.PARQUEUE_ACC_RULE_LOAN_ENTITY_ID " +
            "              ORDER BY BUCKET_ID, PRODUCT_ID), " +
            "     TOUCH_COUNTS AS (SELECT LADI.ID, COUNT(CI.ID) + COUNT(FUE.ID) + COUNT(PTP.ID) + " +
            "                                      COUNT(VLE.ID) + COUNT(DIARY.ID) + COUNT(DAILY.ID) AS TOTAL_TOUCHED " +
            "                      FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI " +
            "                             JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON LADI.LOAN_ACCOUNT_BASIC_INFO_ID = LABI.ID " +
            "                             LEFT JOIN CONTACT_INFO CI " +
            "                               ON LABI.CUSTOMER_ID = CI.CUSTOMER_ID AND CI.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                             LEFT JOIN FOLLOW_UP_ENTITY FUE ON LABI.CUSTOMER_ID = FUE.CUSTOMER_ID AND " +
            "                                                               FUE.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                             LEFT JOIN LOAN_PTP PTP ON LABI.CUSTOMER_ID = PTP.CUSTOMER_ID AND " +
            "                                                       PTP.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                             LEFT JOIN VISIT_LEDGER_ENTITY VLE ON LABI.ACCOUNT_NO = VLE.ACCOUNT_CARD_NUMBER AND " +
            "                                                                  VLE.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                             LEFT JOIN DAIRY_NOTES_LOAN DIARY ON LABI.CUSTOMER_ID = DIARY.CUSTOMER_ID AND " +
            "                                                                 DIARY.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                             LEFT JOIN DAILY_NOTE_ENTITY DAILY ON LABI.CUSTOMER_ID = DAILY.CUSTOMER_ID AND " +
            "                                                                  DAILY.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                      WHERE LADI.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                        AND DEALER_PIN IN (:pin) " +
            "                        AND LADI.LATEST = '1' " +
            "                      GROUP BY LADI.ID), " +
            "     PAR_RULE AS (SELECT LADI_IN.ID                                    AS ID, " +
            "                         BUCKET.BUCKET_NAME                            AS DPD_BUCKET, " +
            "                         PTE.NAME                                      AS PRODUCT_TYPE, " +
            "                         CASE " +
            "                           WHEN LADI_IN.DPD_BUCKET <> LADI_IN.CURRENT_DPD_BUCKET " +
            "                                   THEN BUCKET.PAR_RELEASE_LOAN_ID END AS PAR_RELEASE_ID, " +
            "                         PAR.BUCKET_ID                                 AS PAR_ID, " +
            "                         PARQ.BUCKET_ID                                AS PAR_QUEUE_ID, " +
            "                         CASE " +
            "                           WHEN TO_NUMBER(LADI_IN.DPD) + EXTRACT(DAY FROM SYSDATE) + 1 = PARQ.MIN_DPD " +
            "                                   THEN 1 END                          AS PAR_QUE_NEXT_DAY " +
            "                  FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI_IN " +
            "                         LEFT JOIN DPDBUCKET_ENTITY BUCKET ON BUCKET.BUCKET_NAME = CEIL(LADI_IN.CURRENT_DPD_BUCKET) " +
            "                         LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON LADI_IN.SCHEME_CODE = PTE.CODE " +
            "                         LEFT JOIN PAR ON BUCKET.ID = PAR.BUCKET_ID " +
            "                         LEFT JOIN PARQ ON PTE.ID = PARQ.PRODUCT_ID AND BUCKET.ID = PARQ.BUCKET_ID " +
            "                  WHERE LADI_IN.DEALER_PIN IN (:pin) " +
            "                    AND LADI_IN.LATEST = '1' " +
            "                    AND LADI_IN.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                  ORDER BY LADI_IN.ID), " +
            "     NPL AS (SELECT DISTINCT NARDBL.DPD_BUCKET_LIST_ID AS BUCKET_ID, NARPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
            "             FROM NPL_ACCOUNT_RULE NAR " +
            "                    JOIN NPL_ACCOUNT_RULE_DPD_BUCKET_LIST NARDBL ON NAR.ID = NARDBL.NPLACCOUNT_RULE_ENTITY_ID " +
            "                    JOIN NPL_ACCOUNT_RULE_PRODUCT_TYPE_LIST NARPTL ON NAR.ID = NARPTL.NPLACCOUNT_RULE_ENTITY_ID " +
            "             ORDER BY BUCKET_ID, PRODUCT_ID), " +
            "     NPLQ AS (SELECT DISTINCT NARQ.MIN_DPD, " +
            "                              NARQDBL.DPD_BUCKET_LIST_ID   AS BUCKET_ID, " +
            "                              NARQPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
            "              FROM NPL_QUEUE_ACC_RULE NARQ " +
            "                     JOIN NPL_QUEUE_ACC_RULE_DPD_BUCKET_LIST NARQDBL ON NARQ.ID = NARQDBL.NPLQUEUE_ACC_RULE_ENTITY_ID " +
            "                     JOIN NPL_QUEUE_ACC_RULE_PRODUCT_TYPE_LIST NARQPTL ON NARQ.ID = NARQPTL.NPLQUEUE_ACC_RULE_ENTITY_ID " +
            "              ORDER BY BUCKET_ID, PRODUCT_ID), " +
            "     NPL_RULE AS (SELECT LADI_IN.ID                                    AS ID, " +
            "                         CASE " +
            "                           WHEN TO_NUMBER(LADI_IN.CURRENT_DPD_BUCKET) > " +
            "                                (SELECT MAX(TO_NUMBER(BUCKET_NAME)) FROM DPDBUCKET_ENTITY) " +
            "                                   THEN (SELECT MAX(TO_NUMBER(BUCKET_NAME)) FROM DPDBUCKET_ENTITY) || '+' " +
            "                           ELSE BUCKET.BUCKET_NAME END                 AS DPD_BUCKET, " +
            "                         PTE.NAME                                      AS PRODUCT_TYPE, " +
            "                         CASE " +
            "                           WHEN LADI_IN.DPD_BUCKET <> LADI_IN.CURRENT_DPD_BUCKET " +
            "                                   THEN BUCKET.NPL_RELEASE_LOAN_ID END AS NPL_RELEASE_ID, " +
            "                         CASE " +
            "                           WHEN TO_NUMBER(LADI_IN.CURRENT_DPD_BUCKET) > " +
            "                                (SELECT MAX(TO_NUMBER(BUCKET_NAME)) FROM DPDBUCKET_ENTITY) " +
            "                                   THEN 1 " +
            "                           ELSE NPL.BUCKET_ID END                      AS NPL_ID, " +
            "                         NPLQ.BUCKET_ID                                AS NPL_QUEUE_ID, " +
            "                         CASE " +
            "                           WHEN TO_NUMBER(LADI_IN.DPD) + EXTRACT(DAY FROM SYSDATE) + 1 = NPLQ.MIN_DPD " +
            "                                   THEN 1 END                          AS NPL_QUE_NEXT_DAY " +
            "                  FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI_IN " +
            "                         LEFT JOIN DPDBUCKET_ENTITY BUCKET ON BUCKET.BUCKET_NAME = CEIL(LADI_IN.CURRENT_DPD_BUCKET) " +
            "                         LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON LADI_IN.SCHEME_CODE = PTE.CODE " +
            "                         LEFT JOIN NPL ON PTE.ID = NPL.PRODUCT_ID AND BUCKET.ID = NPL.BUCKET_ID " +
            "                         LEFT JOIN NPLQ ON PTE.ID = NPLQ.PRODUCT_ID AND BUCKET.ID = NPLQ.BUCKET_ID " +
            "                  WHERE LADI_IN.DEALER_PIN IN (:pin) " +
            "                    AND LADI_IN.LATEST = '1' " +
            "                    AND LADI_IN.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                  ORDER BY LADI_IN.ID) " +
            "SELECT LADI.DEALER_PIN                                                    AS dealerPin, " +
            "       PGE.NAME                                                           AS product, " +
            "       LADI.CURRENT_DPD_BUCKET                                            AS dpdBucket, " +
            "       COUNT(DISTINCT LADI.ID)                                            AS totalAccount, " +
            "       COUNT(CASE WHEN TOUCH_COUNTS.TOTAL_TOUCHED > 0 THEN 1 END)         AS totalTouched, " +
            "       COUNT(CASE WHEN LOWER(LPTP.LOAN_PTP_STATUS) = 'broken' THEN 1 END) AS totalBrokenPtp, " +
            "       COUNT(PAR_RULE.PAR_RELEASE_ID)                                     AS totalParRelease, " +
            "       COUNT(PAR_RULE.PAR_ID)                                             AS totalParRemaining, " +
            "       COUNT(PAR_RULE.PAR_QUEUE_ID)                                       AS totalParQueue, " +
            "       COUNT(PAR_RULE.PAR_QUE_NEXT_DAY)                                   AS totalParQueueNextDay, " +
            "       COUNT(NPL_RULE.NPL_RELEASE_ID)                                     AS totalNplRelease, " +
            "       COUNT(NPL_RULE.NPL_ID)                                             AS totalNplRemaining, " +
            "       COUNT(NPL_RULE.NPL_QUEUE_ID)                                       AS totalNplQueue, " +
            "       COUNT(NPL_RULE.NPL_QUE_NEXT_DAY)                                   AS totalNplQueueNextDay " +
            "FROM (SELECT ID, " +
            "             DEALER_PIN, " +
            "             CASE " +
            "               WHEN CEIL(TO_NUMBER(CURRENT_DPD_BUCKET)) > (SELECT BUCKET FROM MAX_BUCKET) " +
            "                       THEN (SELECT BUCKET FROM MAX_BUCKET) || '+' " +
            "               ELSE CEIL(TO_NUMBER(CURRENT_DPD_BUCKET)) || '' END CURRENT_DPD_BUCKET, " +
            "             LOAN_ACCOUNT_BASIC_INFO_ID, " +
            "             SCHEME_CODE " +
            "      FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI_IN " +
            "      WHERE LADI_IN.DEALER_PIN IN (:pin) " +
            "        AND LADI_IN.LATEST = '1' " +
            "        AND LADI_IN.CREATED_DATE BETWEEN :startDate AND :endDate) LADI " +
            "       JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON LABI.ID = LADI.LOAN_ACCOUNT_BASIC_INFO_ID " +
            "       JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE ON LABI.CUSTOMER_ID = CBIE.ID " +
            "       LEFT JOIN LOAN_ACCOUNT_INFO LAI ON LABI.ID = LAI.LOAN_ACCOUNT_BASIC_INFO_ID " +
            "       LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON LADI.SCHEME_CODE = PTE.CODE " +
            "       LEFT JOIN PRODUCT_GROUP_ENTITY PGE on PTE.PRODUCT_GROUP_ENTITY_ID = PGE.ID " +
            "       LEFT JOIN LOAN_PTP LPTP " +
            "         ON LPTP.CUSTOMER_ID = LABI.CUSTOMER_ID AND LPTP.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "       LEFT JOIN TOUCH_COUNTS ON LADI.ID = TOUCH_COUNTS.ID " +
            "       LEFT JOIN PAR_RULE ON LADI.ID = PAR_RULE.ID " +
            "       LEFT JOIN NPL_RULE ON LADI.ID = NPL_RULE.ID " +
            "GROUP BY LADI.DEALER_PIN, PGE.NAME, LADI.CURRENT_DPD_BUCKET " +
            "ORDER BY LADI.DEALER_PIN, PGE.NAME, LADI.CURRENT_DPD_BUCKET ", nativeQuery = true)*/
    @Query(value = "" +
            "WITH MAX_BUCKET AS (SELECT MAX(CEIL(TO_NUMBER(BUCKET_NAME))) BUCKET FROM DPDBUCKET_ENTITY), " +
            "         PAR AS (SELECT DISTINCT PARDBL.DPD_BUCKET_ENTITY_LIST_ID AS BUCKET_ID " +
            "                 FROM PAR_ACC_RULE_LOAN PAR " +
            "                        JOIN PAR_ACC_RULE_LOAN_DPD_BUCKET_ENTITY_LIST PARDBL " +
            "                          ON PAR.ID = PARDBL.PARACCOUNT_RULE_LOAN_ENTITY_ID " +
            "                 ORDER BY BUCKET_ID), " +
            "         PARQ AS (SELECT DISTINCT PARQ.MIN_DPD, " +
            "                                  PARQDBL.DPD_BUCKET_ENTITY_LIST_ID AS BUCKET_ID, " +
            "                                  PARQPTL.PRODUCT_TYPE_LIST_ID      AS PRODUCT_ID " +
            "                  FROM PAR_QUEUE_ACC_RULE_LOAN PARQ " +
            "                         JOIN PAR_QUEUE_ACC_RULE_LOAN_DPD_BUCKET_ENTITY_LIST PARQDBL " +
            "                           ON PARQ.ID = PARQDBL.PARQUEUE_ACC_RULE_LOAN_ENTITY_ID " +
            "                         JOIN PAR_QUEUE_ACC_RULE_LOAN_PRODUCT_TYPE_LIST PARQPTL " +
            "                           ON PARQ.ID = PARQPTL.PARQUEUE_ACC_RULE_LOAN_ENTITY_ID " +
            "                  ORDER BY BUCKET_ID, PRODUCT_ID), " +
            "         TOUCH_COUNTS AS (SELECT LADI.ID, COUNT(CI.ID) + COUNT(FUE.ID) + COUNT(PTP.ID) + " +
            "                                          COUNT(VLE.ID) + COUNT(DIARY.ID) + COUNT(DAILY.ID) AS TOTAL_TOUCHED " +
            "                          FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI " +
            "                                 JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON LADI.LOAN_ACCOUNT_BASIC_INFO_ID = LABI.ID " +
            "                                 LEFT JOIN CONTACT_INFO CI " +
            "                                   ON LABI.CUSTOMER_ID = CI.CUSTOMER_ID AND CI.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                                 LEFT JOIN FOLLOW_UP_ENTITY FUE ON LABI.CUSTOMER_ID = FUE.CUSTOMER_ID AND " +
            "                                                                   FUE.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                                 LEFT JOIN LOAN_PTP PTP ON LABI.CUSTOMER_ID = PTP.CUSTOMER_ID AND " +
            "                                                           PTP.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                                 LEFT JOIN VISIT_LEDGER_ENTITY VLE ON LABI.ACCOUNT_NO = VLE.ACCOUNT_CARD_NUMBER AND " +
            "                                                                      VLE.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                                 LEFT JOIN DAIRY_NOTES_LOAN DIARY ON LABI.CUSTOMER_ID = DIARY.CUSTOMER_ID AND " +
            "                                                                     DIARY.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                                 LEFT JOIN DAILY_NOTE_ENTITY DAILY ON LABI.CUSTOMER_ID = DAILY.CUSTOMER_ID AND " +
            "                                                                      DAILY.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                          WHERE LADI.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                            AND DEALER_PIN IN (:pin) " +
            "                            AND LADI.LATEST = '1' " +
            "                          GROUP BY LADI.ID), " +
            "         PAR_RULE AS (SELECT LADI_IN.ID                                    AS ID, " +
            "                             BUCKET.BUCKET_NAME                            AS DPD_BUCKET, " +
            "                             PTE.NAME                                      AS PRODUCT_TYPE, " +
            "                             CASE " +
            "                               WHEN LADI_IN.DPD_BUCKET <> LADI_IN.CURRENT_DPD_BUCKET " +
            "                                       THEN BUCKET.PAR_RELEASE_LOAN_ID END AS PAR_RELEASE_ID, " +
            "                             PAR.BUCKET_ID                                 AS PAR_ID, " +
            "                             PARQ.BUCKET_ID                                AS PAR_QUEUE_ID, " +
            "                             CASE " +
            "                               WHEN TO_NUMBER(LADI_IN.DPD) + EXTRACT(DAY FROM SYSDATE) + 1 = PARQ.MIN_DPD " +
            "                                       THEN 1 END                          AS PAR_QUE_NEXT_DAY " +
            "                      FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI_IN " +
            "                             LEFT JOIN DPDBUCKET_ENTITY BUCKET ON BUCKET.BUCKET_NAME = CEIL(LADI_IN.CURRENT_DPD_BUCKET) " +
            "                             LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON LADI_IN.SCHEME_CODE = PTE.CODE " +
            "                             LEFT JOIN PAR ON BUCKET.ID = PAR.BUCKET_ID " +
            "                             LEFT JOIN PARQ ON PTE.ID = PARQ.PRODUCT_ID AND BUCKET.ID = PARQ.BUCKET_ID " +
            "                      WHERE LADI_IN.DEALER_PIN IN (:pin) " +
            "                        AND LADI_IN.LATEST = '1' " +
            "                        AND LADI_IN.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                      ORDER BY LADI_IN.ID), " +
            "         NPL AS (SELECT DISTINCT NARDBL.DPD_BUCKET_LIST_ID AS BUCKET_ID, NARPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
            "                 FROM NPL_ACCOUNT_RULE NAR " +
            "                        JOIN NPL_ACCOUNT_RULE_DPD_BUCKET_LIST NARDBL ON NAR.ID = NARDBL.NPLACCOUNT_RULE_ENTITY_ID " +
            "                        JOIN NPL_ACCOUNT_RULE_PRODUCT_TYPE_LIST NARPTL ON NAR.ID = NARPTL.NPLACCOUNT_RULE_ENTITY_ID " +
            "                 ORDER BY BUCKET_ID, PRODUCT_ID), " +
            "         NPLQ AS (SELECT DISTINCT NARQ.MIN_DPD, " +
            "                                  NARQDBL.DPD_BUCKET_LIST_ID   AS BUCKET_ID, " +
            "                                  NARQPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
            "                  FROM NPL_QUEUE_ACC_RULE NARQ " +
            "                         JOIN NPL_QUEUE_ACC_RULE_DPD_BUCKET_LIST NARQDBL ON NARQ.ID = NARQDBL.NPLQUEUE_ACC_RULE_ENTITY_ID " +
            "                         JOIN NPL_QUEUE_ACC_RULE_PRODUCT_TYPE_LIST NARQPTL ON NARQ.ID = NARQPTL.NPLQUEUE_ACC_RULE_ENTITY_ID " +
            "                  ORDER BY BUCKET_ID, PRODUCT_ID), " +
            "         NPL_RULE AS (SELECT LADI_IN.ID                                    AS ID, " +
            "                             CASE " +
            "                               WHEN TO_NUMBER(LADI_IN.CURRENT_DPD_BUCKET) > " +
            "                                    (SELECT MAX(TO_NUMBER(BUCKET_NAME)) FROM DPDBUCKET_ENTITY) " +
            "                                       THEN (SELECT MAX(TO_NUMBER(BUCKET_NAME)) FROM DPDBUCKET_ENTITY) || '+' " +
            "                               ELSE BUCKET.BUCKET_NAME END                 AS DPD_BUCKET, " +
            "                             PTE.NAME                                      AS PRODUCT_TYPE, " +
            "                             CASE " +
            "                               WHEN LADI_IN.DPD_BUCKET <> LADI_IN.CURRENT_DPD_BUCKET " +
            "                                       THEN BUCKET.NPL_RELEASE_LOAN_ID END AS NPL_RELEASE_ID, " +
            "                             CASE " +
            "                               WHEN TO_NUMBER(LADI_IN.CURRENT_DPD_BUCKET) > " +
            "                                    (SELECT MAX(TO_NUMBER(BUCKET_NAME)) FROM DPDBUCKET_ENTITY) " +
            "                                       THEN 1 " +
            "                               ELSE NPL.BUCKET_ID END                      AS NPL_ID, " +
            "                             NPLQ.BUCKET_ID                                AS NPL_QUEUE_ID, " +
            "                             CASE " +
            "                               WHEN TO_NUMBER(LADI_IN.DPD) + EXTRACT(DAY FROM SYSDATE) + 1 = NPLQ.MIN_DPD " +
            "                                       THEN 1 END                          AS NPL_QUE_NEXT_DAY " +
            "                      FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI_IN " +
            "                             LEFT JOIN DPDBUCKET_ENTITY BUCKET ON BUCKET.BUCKET_NAME = CEIL(LADI_IN.CURRENT_DPD_BUCKET) " +
            "                             LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON LADI_IN.SCHEME_CODE = PTE.CODE " +
            "                             LEFT JOIN NPL ON PTE.ID = NPL.PRODUCT_ID AND BUCKET.ID = NPL.BUCKET_ID " +
            "                             LEFT JOIN NPLQ ON PTE.ID = NPLQ.PRODUCT_ID AND BUCKET.ID = NPLQ.BUCKET_ID " +
            "                      WHERE LADI_IN.DEALER_PIN IN (:pin) " +
            "                        AND LADI_IN.LATEST = '1' " +
            "                        AND LADI_IN.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                      ORDER BY LADI_IN.ID), " +
            "    CUSTOMER_LIST AS(SELECT DISTINCT CBIE.ID, LADI.DEALER_PIN   FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI " +
            "    LEFT JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON LADI.LOAN_ACCOUNT_BASIC_INFO_ID = LABI.ID " +
            "    LEFT JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE ON LABI.CUSTOMER_ID = CBIE.ID " +
            "    WHERE LADI.DEALER_PIN IN (:pin) " +//:pin
            "    AND LADI.LATEST = '1' " +
            "     AND LADI.CREATED_DATE BETWEEN :startDate AND :endDate), " +
            "     PTP_TAKEN AS(SELECT count(LP.ID) AS PTP_COUNT, LP.CUSTOMER_ID AS CID FROM LOAN_PTP LP WHERE CUSTOMER_ID IN(SELECT ID FROM CUSTOMER_LIST) AND LP.CREATED_DATE BETWEEN :startDate AND :endDate group by LP.CUSTOMER_ID), " +
            "     PTP_KEPT AS(SELECT count(LP.ID) AS PTP_COUNT, LP.CUSTOMER_ID AS CID FROM LOAN_PTP LP WHERE CUSTOMER_ID IN(SELECT ID FROM CUSTOMER_LIST) AND LP.CREATED_DATE BETWEEN :startDate AND :endDate AND LP.LOAN_PTP_STATUS = 'kept' group by LP.CUSTOMER_ID), " +
            "     PTP_BROKEN AS(SELECT count(LP.ID) AS PTP_COUNT, LP.CUSTOMER_ID AS CID FROM LOAN_PTP LP WHERE CUSTOMER_ID IN(SELECT ID FROM CUSTOMER_LIST) AND LP.CREATED_DATE BETWEEN :startDate AND :endDate AND LP.LOAN_PTP_STATUS = 'broken' group by LP.CUSTOMER_ID), " +
            "     PTP_NOT_TAKEN AS(select distinct(select count(*) from CUSTOMER_LIST)-(select count(*) from PTP_TAKEN) AS PTP_NT_COUNT, CUSTOMER_LIST.DEALER_PIN from CUSTOMER_LIST), " +
            "     PTP_KEPT_COUNT AS(select distinct(select count(*) from PTP_KEPT)-(0) AS PTP_NT_COUNT, CUSTOMER_LIST.DEALER_PIN from CUSTOMER_LIST), " +
            "     PTP_NOT_TAKEN AS(select distinct(select count(*) from CUSTOMER_LIST)-(select count(*) from PTP_TAKEN) AS PTP_NT_COUNT, CUSTOMER_LIST.DEALER_PIN from CUSTOMER_LIST), " +
            "     PTP_RESULT AS(select distinct(select count(*) from CUSTOMER_LIST)-(select count(*) from PTP_TAKEN) AS PTP_NT, " +
            "    (select count(*) from PTP_TAKEN) AS PTP_TAKEN, " +
            "    (select count(*) from CUSTOMER_LIST) AS TOTAL_PTP, " +
            "    (SELECT COUNT(*) FROM PTP_KEPT) AS KEPT_PTP, " +
            "    (SELECT COUNT(*) FROM PTP_BROKEN) AS BROKEN_PTP, " +
            "    CUSTOMER_LIST.DEALER_PIN from CUSTOMER_LIST) " +
            "    SELECT LADI.DEALER_PIN                                                    AS dealerPin, " +
            "           PGE.NAME                                                           AS product, " +
            "           LADI.CURRENT_DPD_BUCKET                                            AS dpdBucket, " +
            "           COUNT(DISTINCT LADI.ID)                                            AS totalAccount, " +
            "           COUNT(CASE WHEN TOUCH_COUNTS.TOTAL_TOUCHED > 0 THEN 1 END)         AS totalTouched, " +
            "           COUNT(CASE WHEN LOWER(LPTP.LOAN_PTP_STATUS) = 'broken' THEN 1 END) AS totalBrokenPtp, " +
            "           COUNT(PAR_RULE.PAR_RELEASE_ID)                                     AS totalParRelease, " +
            "           COUNT(PAR_RULE.PAR_ID)                                             AS totalParRemaining, " +
            "           COUNT(PAR_RULE.PAR_QUEUE_ID)                                       AS totalParQueue, " +
            "           COUNT(PAR_RULE.PAR_QUE_NEXT_DAY)                                   AS totalParQueueNextDay, " +
            "           COUNT(NPL_RULE.NPL_RELEASE_ID)                                     AS totalNplRelease, " +
            "           COUNT(NPL_RULE.NPL_ID)                                             AS totalNplRemaining, " +
            "           COUNT(NPL_RULE.NPL_QUEUE_ID)                                       AS totalNplQueue, " +
            "           COUNT(NPL_RULE.NPL_QUE_NEXT_DAY)                                   AS totalNplQueueNextDay, " +
            "           PTP_RESULT.TOTAL_PTP                                               AS TOTAL_PTP, " +
            "           PTP_RESULT.PTP_TAKEN                                               AS TAKEN_PTP, " +
            "           PTP_RESULT.PTP_NT                                                  AS NT_PTP, " +
            "           PTP_RESULT.KEPT_PTP                                                AS KEPT_PTP, " +
            "           PTP_RESULT.BROKEN_PTP                                              AS BROKEN_PTP " +
            "    FROM (SELECT ID, " +
            "                 DEALER_PIN, " +
            "                 CASE " +
            "                   WHEN CEIL(TO_NUMBER(CURRENT_DPD_BUCKET)) > (SELECT BUCKET FROM MAX_BUCKET) " +
            "                           THEN (SELECT BUCKET FROM MAX_BUCKET) || '+' " +
            "                   ELSE CEIL(TO_NUMBER(CURRENT_DPD_BUCKET)) || '' END CURRENT_DPD_BUCKET, " +
            "                 LOAN_ACCOUNT_BASIC_INFO_ID, " +
            "                 SCHEME_CODE " +
            "          FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI_IN " +
            "          WHERE LADI_IN.DEALER_PIN IN (:pin) " +
            "            AND LADI_IN.LATEST = '1' " +
            "            AND LADI_IN.CREATED_DATE BETWEEN :startDate AND :endDate) LADI " +
            "           JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON LABI.ID = LADI.LOAN_ACCOUNT_BASIC_INFO_ID " +
            "           JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE ON LABI.CUSTOMER_ID = CBIE.ID " +
            "           LEFT JOIN LOAN_ACCOUNT_INFO LAI ON LABI.ID = LAI.LOAN_ACCOUNT_BASIC_INFO_ID " +
            "           LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON LADI.SCHEME_CODE = PTE.CODE " +
            "           LEFT JOIN PRODUCT_GROUP_ENTITY PGE on PTE.PRODUCT_GROUP_ENTITY_ID = PGE.ID " +
            "           LEFT JOIN LOAN_PTP LPTP " +
            "             ON LPTP.CUSTOMER_ID = LABI.CUSTOMER_ID AND LPTP.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "           LEFT JOIN TOUCH_COUNTS ON LADI.ID = TOUCH_COUNTS.ID " +
            "           LEFT JOIN PAR_RULE ON LADI.ID = PAR_RULE.ID " +
            "           LEFT JOIN NPL_RULE ON LADI.ID = NPL_RULE.ID " +
            "           LEFT JOIN PTP_RESULT ON LADI.DEALER_PIN = PTP_RESULT.DEALER_PIN " +
            "    GROUP BY LADI.DEALER_PIN, PGE.NAME, LADI.CURRENT_DPD_BUCKET,PTP_RESULT.TOTAL_PTP," +
            "    PTP_RESULT.PTP_TAKEN,PTP_RESULT.PTP_NT,PTP_RESULT.KEPT_PTP,PTP_RESULT.BROKEN_PTP " +
            "    ORDER BY LADI.DEALER_PIN, PGE.NAME, LADI.CURRENT_DPD_BUCKET", nativeQuery = true)
    List<Tuple> getAccountWiseDistributionAndStatusSummary(@Param("pin") List<String> dealerPins, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * Amount wise distribution and status summary for dealer, team leader, supervisor and manager dashboard.
     * Implemented by Yasir Araphat
     * on August 02, 2021
     *
     * @param dealerPins
     * @param startDate
     * @param endDate
     * @return
     */
    @Query(value = "" +
            "SELECT LADI.DEALER_PIN                                                               AS dealerPin, " +
            "       PGE.NAME                                                                      AS product, " +
            "       COUNT(LADI.ID)                                                                AS totalAcc, " +
            "       LISTAGG(CBIE.ACCOUNT_NO||'-'||LADI.OUT_STANDING,',')                            AS accounts, " +
            "       LADI.CURRENT_DPD_BUCKET                                                       AS dpdBucket, " +
            "       SUM(TO_NUMBER(LADI.OUT_STANDING))                                             AS totalAmount, " +
            "       SUM(CASE " +
            "             WHEN TOUCH_COUNTS.TOTAL_TOUCHED > 0 THEN TO_NUMBER(LADI.OUT_STANDING) " +
            "             ELSE 0 END)                                                             AS totalToucheded, " +
            "       SUM(CASE " +
            "             WHEN LOWER(LPTP.LOAN_PTP_STATUS) = 'cured' THEN TO_NUMBER(LADI.OUT_STANDING) " +
            "             ELSE 0 END)                                                             AS keptFromTakenPtp, " +
            "       SUM(CASE " +
            "             WHEN LOWER(LPTP.LOAN_PTP_STATUS) = 'broken' THEN TO_NUMBER(LADI.OUT_STANDING) " +
            "             ELSE 0 END)                                                             AS ptpBroken, " +
            "       SUM(CASE " +
            "             WHEN LOWER(LPTP.LOAN_PTP_STATUS) = 'kept' THEN TO_NUMBER(LADI.OUT_STANDING) " +
            "             ELSE 0 END)                                                             AS PendingAmount, " +
            "       SUM(DECODE(PAR_RULE.PAR_RELEASE_ID, NULL, 0, TO_NUMBER(LADI.OUT_STANDING)))   AS totalParRelease, " +
            "       SUM(DECODE(PAR_RULE.PAR_ID, NULL, 0, TO_NUMBER(LADI.OUT_STANDING)))           AS totalParRemaining, " +
            "       SUM(DECODE(PAR_RULE.PAR_QUEUE_ID, NULL, 0, TO_NUMBER(LADI.OUT_STANDING)))     AS totalParQueue, " +
            "       SUM(DECODE(PAR_RULE.PAR_QUE_NEXT_DAY, NULL, 0, TO_NUMBER(LADI.OUT_STANDING))) AS totalParQueueNextDay, " +
            "       SUM(DECODE(NPL_RULE.NPL_RELEASE_ID, NULL, 0, TO_NUMBER(LADI.OUT_STANDING)))   AS totalNplRelease, " +
            "       SUM(DECODE(NPL_RULE.NPL_ID, NULL, 0, TO_NUMBER(LADI.OUT_STANDING)))           AS totalNplRemaining, " +
            "       SUM(DECODE(NPL_RULE.NPL_QUEUE_ID, NULL, 0, TO_NUMBER(LADI.OUT_STANDING)))     AS totalNplQueue, " +
            "       SUM(DECODE(NPL_RULE.NPL_QUE_NEXT_DAY, NULL, 0, TO_NUMBER(LADI.OUT_STANDING))) AS totalNplQueueNextDay " +
            "FROM (SELECT ID, " +
            "             DEALER_PIN, " +
            "             CASE " +
            "               WHEN CEIL(TO_NUMBER(CURRENT_DPD_BUCKET)) > " +
            "                    (SELECT MAX(CEIL(TO_NUMBER(BUCKET_NAME))) FROM DPDBUCKET_ENTITY) " +
            "                       THEN (SELECT MAX(CEIL(TO_NUMBER(BUCKET_NAME))) FROM DPDBUCKET_ENTITY) || '+' " +
            "               ELSE CEIL(TO_NUMBER(CURRENT_DPD_BUCKET)) || '' END CURRENT_DPD_BUCKET, " +
            "             LOAN_ACCOUNT_BASIC_INFO_ID, " +
            "             SCHEME_CODE, " +
            "             OUT_STANDING " +
            "      FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI_IN " +
            "      WHERE LADI_IN.DEALER_PIN IN (:pin) " +
            "        AND LADI_IN.LATEST = '1' " +
            "        AND LADI_IN.CREATED_DATE BETWEEN :startDate AND :endDate) LADI " +
            "       JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON LABI.ID = LADI.LOAN_ACCOUNT_BASIC_INFO_ID " +
            "       JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE ON LABI.CUSTOMER_ID = CBIE.ID " +
            "       LEFT JOIN LOAN_ACCOUNT_INFO LAI ON LABI.ID = LAI.LOAN_ACCOUNT_BASIC_INFO_ID " +
            "       LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON LADI.SCHEME_CODE = PTE.CODE " +
            "       LEFT JOIN PRODUCT_GROUP_ENTITY PGE on PTE.PRODUCT_GROUP_ENTITY_ID = PGE.ID " +
            "       LEFT JOIN (SELECT * FROM LOAN_PTP LP2 WHERE LP2.PIN IN (:pin) AND LP2.CREATED_DATE BETWEEN :startDate AND :endDate) LPTP " +
            "         ON LPTP.CUSTOMER_ID = LABI.CUSTOMER_ID AND LPTP.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "       LEFT JOIN (SELECT LADI.ID, COUNT(CI.ID) + COUNT(FUE.ID) + COUNT(PTP.ID) + COUNT(VLE.ID) + COUNT(DIARY.ID) + " +
            "                                  COUNT(DAILY.ID) AS TOTAL_TOUCHED " +
            "                  FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI " +
            "                         JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON LADI.LOAN_ACCOUNT_BASIC_INFO_ID = LABI.ID " +
            "                         LEFT JOIN CONTACT_INFO CI " +
            "                           ON LABI.CUSTOMER_ID = CI.CUSTOMER_ID AND CI.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                         LEFT JOIN FOLLOW_UP_ENTITY FUE " +
            "                           ON LABI.CUSTOMER_ID = FUE.CUSTOMER_ID AND FUE.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                         LEFT JOIN LOAN_PTP PTP " +
            "                           ON LABI.CUSTOMER_ID = PTP.CUSTOMER_ID AND PTP.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                         LEFT JOIN VISIT_LEDGER_ENTITY VLE ON LABI.ACCOUNT_NO = VLE.ACCOUNT_CARD_NUMBER AND " +
            "                                                              VLE.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                         LEFT JOIN DAIRY_NOTES_LOAN DIARY ON LABI.CUSTOMER_ID = DIARY.CUSTOMER_ID AND " +
            "                                                             DIARY.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                         LEFT JOIN DAILY_NOTE_ENTITY DAILY ON LABI.CUSTOMER_ID = DAILY.CUSTOMER_ID AND " +
            "                                                              DAILY.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                  WHERE LADI.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                    AND DEALER_PIN IN(:pin) " +
            "                    AND LADI.LATEST = '1' " +
            "                  GROUP BY LADI.ID) TOUCH_COUNTS ON LADI.ID = TOUCH_COUNTS.ID " +
            "       LEFT JOIN (SELECT LADI_IN.ID                                    AS ID, " +
            "                         BUCKET.BUCKET_NAME                            AS DPD_BUCKET, " +
            "                         PTE.NAME                                      AS PRODUCT_TYPE, " +
            "                         CASE " +
            "                           WHEN LADI_IN.DPD_BUCKET <> LADI_IN.CURRENT_DPD_BUCKET " +
            "                                   THEN BUCKET.PAR_RELEASE_LOAN_ID END AS PAR_RELEASE_ID, " +
            "                         PAR.BUCKET_ID                                 AS PAR_ID, " +
            "                         PARQ.BUCKET_ID                                AS PAR_QUEUE_ID, " +
            "                         CASE " +
            "                           WHEN TO_NUMBER(LADI_IN.DPD) + EXTRACT(DAY FROM SYSDATE) + 1 = PARQ.MIN_DPD " +
            "                                   THEN 1 END                          AS PAR_QUE_NEXT_DAY " +
            "                  FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI_IN " +
            "                         LEFT JOIN DPDBUCKET_ENTITY BUCKET ON BUCKET.BUCKET_NAME = CEIL(LADI_IN.CURRENT_DPD_BUCKET) " +
            "                         LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON LADI_IN.SCHEME_CODE = PTE.CODE " +
            "                         LEFT JOIN (SELECT DISTINCT PARDBL.DPD_BUCKET_ENTITY_LIST_ID AS BUCKET_ID " +
            "                                    FROM PAR_ACC_RULE_LOAN PAR " +
            "                                           JOIN PAR_ACC_RULE_LOAN_DPD_BUCKET_ENTITY_LIST PARDBL " +
            "                                             ON PAR.ID = PARDBL.PARACCOUNT_RULE_LOAN_ENTITY_ID " +
            "                                    ORDER BY BUCKET_ID) PAR ON BUCKET.ID = PAR.BUCKET_ID " +
            "                         LEFT JOIN (SELECT DISTINCT PARQ.MIN_DPD, " +
            "                                                    PARQDBL.DPD_BUCKET_ENTITY_LIST_ID AS BUCKET_ID, " +
            "                                                    PARQPTL.PRODUCT_TYPE_LIST_ID      AS PRODUCT_ID " +
            "                                    FROM PAR_QUEUE_ACC_RULE_LOAN PARQ " +
            "                                           JOIN PAR_QUEUE_ACC_RULE_LOAN_DPD_BUCKET_ENTITY_LIST PARQDBL " +
            "                                             ON PARQ.ID = PARQDBL.PARQUEUE_ACC_RULE_LOAN_ENTITY_ID " +
            "                                           JOIN PAR_QUEUE_ACC_RULE_LOAN_PRODUCT_TYPE_LIST PARQPTL " +
            "                                             ON PARQ.ID = PARQPTL.PARQUEUE_ACC_RULE_LOAN_ENTITY_ID " +
            "                                    ORDER BY BUCKET_ID, PRODUCT_ID) PARQ " +
            "                           ON PTE.ID = PARQ.PRODUCT_ID AND BUCKET.ID = PARQ.BUCKET_ID " +
            "                  WHERE LADI_IN.DEALER_PIN IN (:pin) " +
            "                    AND LADI_IN.LATEST = '1' " +
            "                    AND LADI_IN.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                  ORDER BY LADI_IN.ID) PAR_RULE ON LADI.ID = PAR_RULE.ID " +
            "       LEFT JOIN (SELECT LADI_IN.ID                                    AS ID, " +
            "                         CASE " +
            "                           WHEN TO_NUMBER(LADI_IN.CURRENT_DPD_BUCKET) > " +
            "                                (SELECT MAX(TO_NUMBER(BUCKET_NAME)) FROM DPDBUCKET_ENTITY) " +
            "                                   THEN (SELECT MAX(TO_NUMBER(BUCKET_NAME)) FROM DPDBUCKET_ENTITY) || '+' " +
            "                           ELSE BUCKET.BUCKET_NAME END                 AS DPD_BUCKET, " +
            "                         PTE.NAME                                      AS PRODUCT_TYPE, " +
            "                         CASE " +
            "                           WHEN LADI_IN.DPD_BUCKET <> LADI_IN.CURRENT_DPD_BUCKET " +
            "                                   THEN BUCKET.NPL_RELEASE_LOAN_ID END AS NPL_RELEASE_ID, " +
            "                         CASE " +
            "                           WHEN TO_NUMBER(LADI_IN.CURRENT_DPD_BUCKET) > " +
            "                                (SELECT MAX(TO_NUMBER(BUCKET_NAME)) FROM DPDBUCKET_ENTITY) " +
            "                                   THEN 1 " +
            "                           ELSE NPL.BUCKET_ID END                      AS NPL_ID, " +
            "                         NPLQ.BUCKET_ID                                AS NPL_QUEUE_ID, " +
            "                         CASE " +
            "                           WHEN TO_NUMBER(LADI_IN.DPD) + EXTRACT(DAY FROM SYSDATE) + 1 = NPLQ.MIN_DPD " +
            "                                   THEN 1 END                          AS NPL_QUE_NEXT_DAY " +
            "                  FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI_IN " +
            "                         LEFT JOIN DPDBUCKET_ENTITY BUCKET ON BUCKET.BUCKET_NAME = CEIL(LADI_IN.CURRENT_DPD_BUCKET) " +
            "                         LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON LADI_IN.SCHEME_CODE = PTE.CODE " +
            "                         LEFT JOIN (SELECT DISTINCT NARDBL.DPD_BUCKET_LIST_ID   AS BUCKET_ID, " +
            "                                                    NARPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
            "                                    FROM NPL_ACCOUNT_RULE NAR " +
            "                                           JOIN NPL_ACCOUNT_RULE_DPD_BUCKET_LIST NARDBL " +
            "                                             ON NAR.ID = NARDBL.NPLACCOUNT_RULE_ENTITY_ID " +
            "                                           JOIN NPL_ACCOUNT_RULE_PRODUCT_TYPE_LIST NARPTL " +
            "                                             ON NAR.ID = NARPTL.NPLACCOUNT_RULE_ENTITY_ID " +
            "                                    ORDER BY BUCKET_ID, PRODUCT_ID) NPL " +
            "                           ON PTE.ID = NPL.PRODUCT_ID AND BUCKET.ID = NPL.BUCKET_ID " +
            "                         LEFT JOIN (SELECT DISTINCT NARQ.MIN_DPD, " +
            "                                                    NARQDBL.DPD_BUCKET_LIST_ID   AS BUCKET_ID, " +
            "                                                    NARQPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
            "                                    FROM NPL_QUEUE_ACC_RULE NARQ " +
            "                                           JOIN NPL_QUEUE_ACC_RULE_DPD_BUCKET_LIST NARQDBL " +
            "                                             ON NARQ.ID = NARQDBL.NPLQUEUE_ACC_RULE_ENTITY_ID " +
            "                                           JOIN NPL_QUEUE_ACC_RULE_PRODUCT_TYPE_LIST NARQPTL " +
            "                                             ON NARQ.ID = NARQPTL.NPLQUEUE_ACC_RULE_ENTITY_ID " +
            "                                    ORDER BY BUCKET_ID, PRODUCT_ID) NPLQ " +
            "                           ON PTE.ID = NPLQ.PRODUCT_ID AND BUCKET.ID = NPLQ.BUCKET_ID " +
            "                  WHERE LADI_IN.DEALER_PIN IN (:pin) " +
            "                    AND LADI_IN.LATEST = '1' " +
            "                    AND LADI_IN.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                  ORDER BY LADI_IN.ID) NPL_RULE ON LADI.ID = NPL_RULE.ID " +
            "GROUP BY LADI.DEALER_PIN, PGE.NAME, LADI.CURRENT_DPD_BUCKET " +
            "ORDER BY LADI.DEALER_PIN, PGE.NAME, LADI.CURRENT_DPD_BUCKET", nativeQuery = true)
    List<Tuple> getAmountWiseDistributionAndStatusSummary(@Param("pin") List<String> dealerPins, @Param("startDate") Date startDate, @Param("endDate") Date endDate);






//    @Query(value = "WITH dealerIdList AS (SELECT PAL.DEALER_ID " +
//            "                      FROM EMPLOYEE_INFO_ENTITY EIE " +
//            "                             INNER JOIN PEOPLE_ALLOCATION_LOGIC PAL " +
//            "                               ON PAL.DEALER_ID = EIE.ID OR PAL.SUPERVISOR_ID = EIE.ID OR " +
//            "                                  PAL.TEAM_LEAD_ID = EIE.ID OR " +
//            "                                  PAL.MANAGER_ID = EIE.ID " +
//            "                      WHERE EIE.PIN = :pin) " +
//            "SELECT LADI.DEALER_PIN                                                               AS dealerPin, " +
//            "       LADI.PRODUCT_GROUP                                                            AS product, " +
//            "       LADI.CURRENT_DPD_BUCKET                                                       AS dpdBucket, " +
//            "       SUM(TO_NUMBER(LADI.OUT_STANDING))                                             AS totalAmount, " +
//            "       SUM(CASE " +
//            "             WHEN TOUCHED_COUNT.TOTAL_TOUCHED > 0 THEN LADI.OUT_STANDING " +
//            "               END)                                                                  AS totalToucheded, " +
//            "       SUM(CASE " +
//            "             WHEN TOUCHED_COUNT.TOTAL_TOUCHED = 0 THEN LADI.OUT_STANDING " +
//            "               END)                                                                  AS totalUntouched, " +
//            "       SUM(CASE " +
//            "             WHEN LOWER(LP2.LOAN_PTP_STATUS) = 'kept' THEN TO_NUMBER(LADI.OUT_STANDING) " +
//            "             ELSE 0 END)                                                             AS PendingAmount, " +
//            "       SUM(DECODE(PAR_RULE.PAR_RELEASE_ID, NULL, 0, TO_NUMBER(LADI.OUT_STANDING)))   AS totalParRelease, " +
//            "       SUM(DECODE(PAR_RULE.PAR_ID, NULL, 0, TO_NUMBER(LADI.OUT_STANDING)))           AS totalParRemaining, " +
//            "       SUM(DECODE(PAR_RULE.PAR_QUEUE_ID, NULL, 0, TO_NUMBER(LADI.OUT_STANDING)))     AS totalParQueue, " +
//            "       SUM(DECODE(PAR_RULE.PAR_QUE_NEXT_DAY, NULL, 0, TO_NUMBER(LADI.OUT_STANDING))) AS totalParQueueNextDay, " +
//            "       SUM(DECODE(NPL_RULE.NPL_RELEASE_ID, NULL, 0, TO_NUMBER(LADI.OUT_STANDING)))   AS totalNplRelease, " +
//            "       SUM(DECODE(NPL_RULE.NPL_ID, NULL, 0, TO_NUMBER(LADI.OUT_STANDING)))           AS totalNplRemaining, " +
//            "       SUM(DECODE(NPL_RULE.NPL_QUEUE_ID, NULL, 0, TO_NUMBER(LADI.OUT_STANDING)))     AS totalNplQueue, " +
//            "       SUM(DECODE(NPL_RULE.NPL_QUE_NEXT_DAY, NULL, 0, TO_NUMBER(LADI.OUT_STANDING))) AS totalNplQueueNextDay " +
//            "FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI " +
//            "       LEFT JOIN LOAN_ACCOUNT_BASIC_INFO LABI2 ON LABI2.ID = LADI.LOAN_ACCOUNT_BASIC_INFO_ID " +
//            "       LEFT JOIN LOAN_PTP LP2 ON LP2.CUSTOMER_ID = LABI2.CUSTOMER_ID " +
//            "       LEFT JOIN (SELECT ladi.ID, COUNT(CI.ID) + COUNT(FUE.ID) + COUNT(LP.ID) + COUNT(VLE.ID) + COUNT(DIARY.ID) + " +
//            "                                  COUNT(DAILY.ID) AS TOTAL_TOUCHED " +
//            "                  FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI " +
//            "                         LEFT JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON LABI.ID = LADI.LOAN_ACCOUNT_BASIC_INFO_ID " +
//            "                         LEFT JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE ON CBIE.ID = LABI.CUSTOMER_ID " +
//            "                         LEFT JOIN LOAN_PTP LP " +
//            "                           ON LP.CUSTOMER_ID = CBIE.ID AND LP.CREATED_DATE BETWEEN :startDate AND :endDate " +
//            "                         LEFT JOIN CONTACT_INFO CI " +
//            "                           ON CI.CUSTOMER_ID = LABI.CUSTOMER_ID AND CI.CREATED_DATE BETWEEN :startDate AND :endDate " +
//            "                         LEFT JOIN FOLLOW_UP_ENTITY FUE " +
//            "                           ON LABI.CUSTOMER_ID = FUE.CUSTOMER_ID AND FUE.CREATED_DATE BETWEEN :startDate AND :endDate " +
//            "                         LEFT JOIN VISIT_LEDGER_ENTITY VLE ON LABI.ACCOUNT_NO = VLE.ACCOUNT_CARD_NUMBER AND " +
//            "                                                              VLE.CREATED_DATE BETWEEN :startDate AND :endDate " +
//            "                         LEFT JOIN DAIRY_NOTES_LOAN DIARY ON LABI.CUSTOMER_ID = DIARY.CUSTOMER_ID AND " +
//            "                                                             DIARY.CREATED_DATE BETWEEN :startDate AND :endDate " +
//            "                         LEFT JOIN DAILY_NOTE_ENTITY DAILY ON LABI.CUSTOMER_ID = DAILY.CUSTOMER_ID AND " +
//            "                                                              DAILY.CREATED_DATE BETWEEN :startDate AND :endDate " +
//            "                  WHERE LADI.DEALER_PIN In " +
//            "                        (SELECT EIE2.PIN FROM EMPLOYEE_INFO_ENTITY EIE2 WHERE EIE2.ID IN (SELECT * FROM dealerIdList)) " +
//            "                    And LADI.CREATED_DATE BETWEEN :startDate AND :endDate " +
//            "                    AND LATEST = 1 " +
//            "                  GROUP BY LADI.ID) TOUCHED_COUNT ON LADI.ID = TOUCHED_COUNT.ID " +
//            "       LEFT JOIN (SELECT LADI_IN.ID                                    AS ID, " +
//            "                         BUCKET.BUCKET_NAME                            AS DPD_BUCKET, " +
//            "                         PTE.NAME                                      AS PRODUCT_TYPE, " +
//            "                         CASE " +
//            "                           WHEN LADI_IN.DPD_BUCKET <> LADI_IN.CURRENT_DPD_BUCKET " +
//            "                                   THEN BUCKET.PAR_RELEASE_LOAN_ID END AS PAR_RELEASE_ID, " +
//            "                         PAR.BUCKET_ID                                 AS PAR_ID, " +
//            "                         PARQ.BUCKET_ID                                AS PAR_QUEUE_ID, " +
//            "                         CASE " +
//            "                           WHEN TO_NUMBER(LADI_IN.DPD) + EXTRACT(DAY FROM SYSDATE) + 1 = PARQ.MIN_DPD " +
//            "                                   THEN 1 END                          AS PAR_QUE_NEXT_DAY " +
//            "                  FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI_IN " +
//            "                         LEFT JOIN DPDBUCKET_ENTITY BUCKET ON BUCKET.BUCKET_NAME = CEIL(LADI_IN.CURRENT_DPD_BUCKET) " +
//            "                         LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON LADI_IN.SCHEME_CODE = PTE.CODE " +
//            "                         LEFT JOIN (SELECT DISTINCT PARDBL.DPD_BUCKET_ENTITY_LIST_ID AS BUCKET_ID " +
//            "                                    FROM PAR_ACC_RULE_LOAN PAR " +
//            "                                           JOIN PAR_ACC_RULE_LOAN_DPD_BUCKET_ENTITY_LIST PARDBL " +
//            "                                             ON PAR.ID = PARDBL.PARACCOUNT_RULE_LOAN_ENTITY_ID " +
//            "                                    ORDER BY BUCKET_ID) PAR ON BUCKET.ID = PAR.BUCKET_ID " +
//            "                         LEFT JOIN (SELECT DISTINCT PARQ.MIN_DPD, " +
//            "                                                    PARQDBL.DPD_BUCKET_ENTITY_LIST_ID AS BUCKET_ID, " +
//            "                                                    PARQPTL.PRODUCT_TYPE_LIST_ID      AS PRODUCT_ID " +
//            "                                    FROM PAR_QUEUE_ACC_RULE_LOAN PARQ " +
//            "                                           JOIN PAR_QUEUE_ACC_RULE_LOAN_DPD_BUCKET_ENTITY_LIST PARQDBL " +
//            "                                             ON PARQ.ID = PARQDBL.PARQUEUE_ACC_RULE_LOAN_ENTITY_ID " +
//            "                                           JOIN PAR_QUEUE_ACC_RULE_LOAN_PRODUCT_TYPE_LIST PARQPTL " +
//            "                                             ON PARQ.ID = PARQPTL.PARQUEUE_ACC_RULE_LOAN_ENTITY_ID " +
//            "                                    ORDER BY BUCKET_ID, PRODUCT_ID) PARQ " +
//            "                           ON PTE.ID = PARQ.PRODUCT_ID AND BUCKET.ID = PARQ.BUCKET_ID " +
//            "                  WHERE LADI_IN.DEALER_PIN IN " +
//            "                        (SELECT EIE2.PIN FROM EMPLOYEE_INFO_ENTITY EIE2 WHERE EIE2.ID IN (SELECT * FROM dealerIdList)) " +
//            "                    AND LADI_IN.LATEST = '1' " +
//            "                    AND LADI_IN.CREATED_DATE BETWEEN :startDate AND :endDate " +
//            "                  ORDER BY LADI_IN.ID) PAR_RULE ON LADI.ID = PAR_RULE.ID " +
//            "       LEFT JOIN (SELECT LADI_IN.ID                                    AS ID, " +
//            "                         CASE " +
//            "                           WHEN TO_NUMBER(LADI_IN.CURRENT_DPD_BUCKET) > " +
//            "                                (SELECT MAX(TO_NUMBER(BUCKET_NAME)) FROM DPDBUCKET_ENTITY) " +
//            "                                   THEN (SELECT MAX(TO_NUMBER(BUCKET_NAME)) FROM DPDBUCKET_ENTITY) || '+' " +
//            "                           ELSE BUCKET.BUCKET_NAME END                 AS DPD_BUCKET, " +
//            "                         PTE.NAME                                      AS PRODUCT_TYPE, " +
//            "                         CASE " +
//            "                           WHEN LADI_IN.DPD_BUCKET <> LADI_IN.CURRENT_DPD_BUCKET " +
//            "                                   THEN BUCKET.NPL_RELEASE_LOAN_ID END AS NPL_RELEASE_ID, " +
//            "                         CASE " +
//            "                           WHEN TO_NUMBER(LADI_IN.CURRENT_DPD_BUCKET) > " +
//            "                                (SELECT MAX(TO_NUMBER(BUCKET_NAME)) FROM DPDBUCKET_ENTITY) " +
//            "                                   THEN 1 " +
//            "                           ELSE NPL.BUCKET_ID END                      AS NPL_ID, " +
//            "                         NPLQ.BUCKET_ID                                AS NPL_QUEUE_ID, " +
//            "                         CASE " +
//            "                           WHEN TO_NUMBER(LADI_IN.DPD) + EXTRACT(DAY FROM SYSDATE) + 1 = NPLQ.MIN_DPD " +
//            "                                   THEN 1 END                          AS NPL_QUE_NEXT_DAY " +
//            "                  FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI_IN " +
//            "                         LEFT JOIN DPDBUCKET_ENTITY BUCKET ON BUCKET.BUCKET_NAME = CEIL(LADI_IN.CURRENT_DPD_BUCKET) " +
//            "                         LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON LADI_IN.SCHEME_CODE = PTE.CODE " +
//            "                         LEFT JOIN (SELECT DISTINCT NARDBL.DPD_BUCKET_LIST_ID   AS BUCKET_ID, " +
//            "                                                    NARPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
//            "                                    FROM NPL_ACCOUNT_RULE NAR " +
//            "                                           JOIN NPL_ACCOUNT_RULE_DPD_BUCKET_LIST NARDBL " +
//            "                                             ON NAR.ID = NARDBL.NPLACCOUNT_RULE_ENTITY_ID " +
//            "                                           JOIN NPL_ACCOUNT_RULE_PRODUCT_TYPE_LIST NARPTL " +
//            "                                             ON NAR.ID = NARPTL.NPLACCOUNT_RULE_ENTITY_ID " +
//            "                                    ORDER BY BUCKET_ID, PRODUCT_ID) NPL " +
//            "                           ON PTE.ID = NPL.PRODUCT_ID AND BUCKET.ID = NPL.BUCKET_ID " +
//            "                         LEFT JOIN (SELECT DISTINCT NARQ.MIN_DPD, " +
//            "                                                    NARQDBL.DPD_BUCKET_LIST_ID   AS BUCKET_ID, " +
//            "                                                    NARQPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
//            "                                    FROM NPL_QUEUE_ACC_RULE NARQ " +
//            "                                           JOIN NPL_QUEUE_ACC_RULE_DPD_BUCKET_LIST NARQDBL " +
//            "                                             ON NARQ.ID = NARQDBL.NPLQUEUE_ACC_RULE_ENTITY_ID " +
//            "                                           JOIN NPL_QUEUE_ACC_RULE_PRODUCT_TYPE_LIST NARQPTL " +
//            "                                             ON NARQ.ID = NARQPTL.NPLQUEUE_ACC_RULE_ENTITY_ID " +
//            "                                    ORDER BY BUCKET_ID, PRODUCT_ID) NPLQ " +
//            "                           ON PTE.ID = NPLQ.PRODUCT_ID AND BUCKET.ID = NPLQ.BUCKET_ID " +
//            "                  WHERE LADI_IN.DEALER_PIN IN " +
//            "                        (SELECT EIE2.PIN FROM EMPLOYEE_INFO_ENTITY EIE2 WHERE EIE2.ID IN (SELECT * FROM dealerIdList)) " +
//            "                    AND LADI_IN.LATEST = '1' " +
//            "                    AND LADI_IN.CREATED_DATE BETWEEN :startDate AND :endDate " +
//            "                  ORDER BY LADI_IN.ID) NPL_RULE ON LADI.ID = NPL_RULE.ID " +
//            "WHERE LADI.DEALER_PIN IN (SELECT EIE2.PIN FROM EMPLOYEE_INFO_ENTITY EIE2 WHERE EIE2.ID IN (SELECT * FROM dealerIdList)) " +
//            "  AND LADI.CREATED_DATE BETWEEN :startDate AND :endDate " +
//            "  AND LATEST = 1 " +
//            "GROUP BY LADI.DEALER_PIN, LADI.PRODUCT_GROUP, LADI.CURRENT_DPD_BUCKET ", nativeQuery = true)
//    List<Tuple> getAmountWiseDistributionAndStatusSummary(@Param("pin") String pin, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * Categorized payment summary for dealer dashboard.
     * Implemented by Yasir Araphat
     * on August 03, 2021
     *
     * @param dealerPins
     * @param startDate
     * @param endDate
     * @return
     */
    @Query(value = "" +
            "SELECT LOAN_INFO.CATEGORY                             AS category, " +
            "       COUNT(LOAN_INFO.ACCOUNT_NO) AS currentMonthTotalAccount, " +
            "       SUM(LOAN_INFO.CURRENT_MONTH_TOTAL_PAYMENT)     AS currentMonthTotalPayment, " +
            "       COUNT(CASE " +
            "               WHEN LOAN_INFO.LAST_DATE_TOTAL_PAYMENT > 0 " +
            "                       THEN LOAN_INFO.ACCOUNT_NO END) AS lastDateTotalAccount, " +
            "       SUM(LOAN_INFO.LAST_DATE_TOTAL_PAYMENT)         AS lastDateTotalPayment, " +
            "       REPLACE('[' || " +
            "               RTRIM( " +
            "                 XMLAGG( " +
            "                   XMLELEMENT(e, '{' " +
            "                                   || '\"accountNo\":\"' || LOAN_INFO.ACCOUNT_NO || '\", ' " +
            "                                   || '\"accountName\":\"' || LOAN_INFO.ACCOUNT_NAME || '\", ' " +
            "                                   || '\"bucketName\":\"' || LOAN_INFO.BUCKET_NAME || '\", ' " +
            "                                   || '\"currentMonthPayment\":\"' || LOAN_INFO.CURRENT_MONTH_TOTAL_PAYMENT || '\", ' " +
            "                                   || '\"lastDatePayment\":\"' || LOAN_INFO.LAST_DATE_TOTAL_PAYMENT || '\", ' " +
            "                                   || '\"overdue\":\"' || LOAN_INFO.OPENING_OVER_DUE || '\", ' " +
            "                                   || '\"outstanding\":\"' || LOAN_INFO.OUT_STANDING || '\", ' " +
            "                                   || '\"dealerName\":\"' || LOAN_INFO.DEALER_NAME || '\", ' " +
            "                                   || '\"lastPaymentDate\":\"' || TO_CHAR(LOAN_INFO.LAST_PAYMENT_DATE, 'DD.MM.YYYY') || " +
            "                                 '\" ' " +
            "                                   || '}', ',') " +
            "                       .extract('//text()') ORDER BY LOAN_INFO.LAST_PAYMENT_DATE DESC) " +
            "                     .getclobval(), ', ') " +
            "                 || ']', '&quot;', '\"' )               AS paymentDetails " +
            "FROM (SELECT LABI.ACCOUNT_NO                                                                      AS ACCOUNT_NO, " +
            "             LABI.ACCOUNT_NAME                                                                    AS ACCOUNT_NAME, " +
            "             LADI.DEALER_NAME                                                                     AS DEALER_NAME, " +
            "             LADI.DPD_BUCKET                                                                      AS BUCKET_NAME, " +
            "             COALESCE(LADI.OPENING_OVER_DUE, 0)                                                   AS OPENING_OVER_DUE, " +
            "             COALESCE(LADI.OUT_STANDING, '0')                                                     AS OUT_STANDING, " +
            "             COALESCE(LPCM.CURRENT_MONTH_TOTAL_PAYMENT, 0)                                        AS CURRENT_MONTH_TOTAL_PAYMENT, " +
            "             COALESCE(LPCM.LAST_DATE_TOTAL_PAYMENT, 0)                                            AS LAST_DATE_TOTAL_PAYMENT, " +
            "             DECODE(COALESCE(LPCM.LAST_PAYMENT, 0), 0, LADI.LAST_PAID_AMOUNT, LPCM.LAST_PAYMENT)  AS LAST_PAYMENT, " +
            "             DECODE(LPCM.LAST_PAYMENT_DATE, NULL, LADI.LAST_PAYMNET_DATE, LPCM.LAST_PAYMENT_DATE) AS LAST_PAYMENT_DATE, " +
            "             CASE " +
            "               WHEN COALESCE(LPCM.CURRENT_MONTH_TOTAL_PAYMENT, 0) > LADI.OPENING_OVER_DUE THEN 'Full amount paid' " +
            "               WHEN COALESCE(LPCM.CURRENT_MONTH_TOTAL_PAYMENT, 0) <> 0 AND LADI.DPD_BUCKET < LADI.CURRENT_DPD_BUCKET " +
            "                       THEN 'Partial paid but forward flow to upper bucket' " +
            "               WHEN COALESCE(LPCM.CURRENT_MONTH_TOTAL_PAYMENT, 0) = 0 THEN 'No payment during the month' " +
            "               WHEN NPL_RULE.NPL_QUEUE_ID IS NOT NULL THEN 'Probable CL (As on date on Dealer Queue)' " +
            "               WHEN NPL_RULE.NPLMO_QUEUE_ID IS NOT NULL THEN 'Probable CL list as per month beginning distribution' " +
            "               WHEN NPL_RULE.NPL_ID IS NOT NULL THEN 'Existing CL' " +
            "               ELSE 'Others' END                                                                  AS CATEGORY " +
            "      FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI " +
            "             JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON LABI.ID = LADI.LOAN_ACCOUNT_BASIC_INFO_ID " +
            "             LEFT JOIN (SELECT LP.ACCOUNT_NO        AS ACCOUNT_NO, " +
            "                               SUM(CASE " +
            "                                     WHEN LP.PAYMENT_DATE BETWEEN :startDate AND :endDate " +
            "                                             THEN LP.PAYMENT " +
            "                                     ELSE 0 END)    AS CURRENT_MONTH_TOTAL_PAYMENT, " +
            "                               SUM(CASE " +
            "                                     WHEN TRUNC(LP.PAYMENT_DATE, 'DD') = TRUNC(SYSDATE - 1, 'DD') " +
            "                                             THEN LP.PAYMENT " +
            "                                     ELSE 0 END)    AS LAST_DATE_TOTAL_PAYMENT, " +
            "                               MAX(LP.PAYMENT_DATE) AS LAST_PAYMENT_DATE, " +
            "                               SUM(CASE " +
            "                                     WHEN LP.PAYMENT_DATE = LPMD.PAYMENT_DATE " +
            "                                             THEN LP.PAYMENT " +
            "                                     ELSE 0 END)    AS LAST_PAYMENT " +
            "                        FROM LOAN_PAYMENT LP " +
            "                               LEFT JOIN (SELECT LP_IN.ACCOUNT_NO, MAX(LP_IN.PAYMENT_DATE) PAYMENT_DATE " +
            "                                          FROM LOAN_PAYMENT LP_IN " +
            "                                          GROUP BY LP_IN.ACCOUNT_NO) LPMD ON LP.ACCOUNT_NO = LPMD.ACCOUNT_NO " +
            "                        GROUP BY LP.ACCOUNT_NO) LPCM ON LABI.ACCOUNT_NO = LPCM.ACCOUNT_NO " +
            "             LEFT JOIN (SELECT LADI_IN.ID               AS ID, " +
            "                               CASE " +
            "                                 WHEN TO_NUMBER(LADI_IN.CURRENT_DPD_BUCKET) > " +
            "                                      (SELECT MAX(TO_NUMBER(BUCKET_NAME)) FROM DPDBUCKET_ENTITY) " +
            "                                         THEN 1 " +
            "                                 ELSE NPL.BUCKET_ID END AS NPL_ID, " +
            "                               NPLQ.BUCKET_ID           AS NPL_QUEUE_ID, " +
            "                               NPLQMO.BUCKET_ID         AS NPLMO_QUEUE_ID " +
            "                        FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI_IN " +
            "                               LEFT JOIN DPDBUCKET_ENTITY BC ON BC.BUCKET_NAME = CEIL(LADI_IN.CURRENT_DPD_BUCKET) " +
            "                               LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON LADI_IN.SCHEME_CODE = PTE.CODE " +
            "                               LEFT JOIN (SELECT DISTINCT NARDBL.DPD_BUCKET_LIST_ID   AS BUCKET_ID, " +
            "                                                          NARPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
            "                                          FROM NPL_ACCOUNT_RULE NAR " +
            "                                                 JOIN NPL_ACCOUNT_RULE_DPD_BUCKET_LIST NARDBL " +
            "                                                   ON NAR.ID = NARDBL.NPLACCOUNT_RULE_ENTITY_ID " +
            "                                                 JOIN NPL_ACCOUNT_RULE_PRODUCT_TYPE_LIST NARPTL " +
            "                                                   ON NAR.ID = NARPTL.NPLACCOUNT_RULE_ENTITY_ID " +
            "                                          ORDER BY BUCKET_ID, PRODUCT_ID) NPL " +
            "                                 ON PTE.ID = NPL.PRODUCT_ID AND BC.ID = NPL.BUCKET_ID " +
            "                               LEFT JOIN (SELECT DISTINCT NARQ.MIN_DPD, " +
            "                                                          NARQDBL.DPD_BUCKET_LIST_ID   AS BUCKET_ID, " +
            "                                                          NARQPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
            "                                          FROM NPL_QUEUE_ACC_RULE NARQ " +
            "                                                 JOIN NPL_QUEUE_ACC_RULE_DPD_BUCKET_LIST NARQDBL " +
            "                                                   ON NARQ.ID = NARQDBL.NPLQUEUE_ACC_RULE_ENTITY_ID " +
            "                                                 JOIN NPL_QUEUE_ACC_RULE_PRODUCT_TYPE_LIST NARQPTL " +
            "                                                   ON NARQ.ID = NARQPTL.NPLQUEUE_ACC_RULE_ENTITY_ID " +
            "                                          ORDER BY BUCKET_ID, PRODUCT_ID) NPLQ " +
            "                                 ON PTE.ID = NPLQ.PRODUCT_ID AND BC.ID = NPLQ.BUCKET_ID " +
            "                               LEFT JOIN DPDBUCKET_ENTITY BMO ON BMO.BUCKET_NAME = CEIL(LADI_IN.DPD_BUCKET) " +
            "                               LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON LADI_IN.SCHEME_CODE = PTE.CODE " +
            "                               LEFT JOIN (SELECT DISTINCT NARDBL.DPD_BUCKET_LIST_ID   AS BUCKET_ID, " +
            "                                                          NARPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
            "                                          FROM NPL_ACCOUNT_RULE NAR " +
            "                                                 JOIN NPL_ACCOUNT_RULE_DPD_BUCKET_LIST NARDBL " +
            "                                                   ON NAR.ID = NARDBL.NPLACCOUNT_RULE_ENTITY_ID " +
            "                                                 JOIN NPL_ACCOUNT_RULE_PRODUCT_TYPE_LIST NARPTL " +
            "                                                   ON NAR.ID = NARPTL.NPLACCOUNT_RULE_ENTITY_ID " +
            "                                          ORDER BY BUCKET_ID, PRODUCT_ID) NPLMO " +
            "                                 ON PTE.ID = NPLMO.PRODUCT_ID AND BMO.ID = NPLMO.BUCKET_ID " +
            "                               LEFT JOIN (SELECT DISTINCT NARQ.MIN_DPD, " +
            "                                                          NARQDBL.DPD_BUCKET_LIST_ID   AS BUCKET_ID, " +
            "                                                          NARQPTL.PRODUCT_TYPE_LIST_ID AS PRODUCT_ID " +
            "                                          FROM NPL_QUEUE_ACC_RULE NARQ " +
            "                                                 JOIN NPL_QUEUE_ACC_RULE_DPD_BUCKET_LIST NARQDBL " +
            "                                                   ON NARQ.ID = NARQDBL.NPLQUEUE_ACC_RULE_ENTITY_ID " +
            "                                                 JOIN NPL_QUEUE_ACC_RULE_PRODUCT_TYPE_LIST NARQPTL " +
            "                                                   ON NARQ.ID = NARQPTL.NPLQUEUE_ACC_RULE_ENTITY_ID " +
            "                                          ORDER BY BUCKET_ID, PRODUCT_ID) NPLQMO " +
            "                                 ON PTE.ID = NPLQMO.PRODUCT_ID AND BMO.ID = NPLQMO.BUCKET_ID " +
            "                        WHERE LADI_IN.DEALER_PIN IN (:pin) " +
            "                          AND LADI_IN.LATEST = '1' " +
            "                          AND LADI_IN.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                        ORDER BY LADI_IN.ID) NPL_RULE ON LADI.ID = NPL_RULE.ID " +
            "      WHERE LADI.DEALER_PIN IN (:pin) " +
            "        AND LADI.LATEST = '1' " +
            "        AND LADI.CREATED_DATE BETWEEN :startDate AND :endDate) LOAN_INFO " +
            "GROUP BY LOAN_INFO.CATEGORY", nativeQuery = true)
    List<Tuple> getCategorizedPaymentSummary(@Param("pin") List<String> dealerPins, @Param("startDate") Date startDate, @Param("endDate") Date endDate);


    /**
     * Generates payment summary for specific dealers from different date ranges.
     * e.g. LP -> For last payment date of an account,
     * LPCM -> Current month payment summary,
     * LPLD -> Yesterday payment summary.
     * <p>
     * Implemented by Yasir Araphat
     * at 18 April 2021
     *
     * @param dealerPins
     * @return List of Tuples
     */
    @Query(value = "" +
            "SELECT LADI.DEALER_PIN, " +
            "       DECODE(NULL, LADI.DEALER_NAME, '-', LADI.DEALER_NAME) DEALER_NAME, " +
            "       COUNT(LPCM.ACCOUNT_NO)                                                CURRENT_MONTH_PAID_ACCOUNT, " +
            "       COALESCE(SUM(LPCM.TOTAL_PAYMENT), 0)                                  CURRENT_MONTH_TOTAL_PAYMENT, " +
            "       COUNT(LADI.LOAN_ACCOUNT_BASIC_INFO_ID) - COUNT(LPCM.ACCOUNT_NO)       CURRENT_MONTH_UNPAID_ACCOUNT, " +
            "       COALESCE(SUM(LADI.OUT_STANDING), 0)                                   CURRENT_MONTH_UNPAID_OUTSTANDING, " +
            "       COUNT(CASE WHEN LPCM.TOTAL_PAYMENT - LADI.EMI_AMOUNT >= 0 THEN 1 END) CURRENT_MONTH_SAVE_ACCOUNT, " +
            "       SUM(CASE " +
            "             WHEN LPCM.TOTAL_PAYMENT - LADI.EMI_AMOUNT >= 0 THEN TO_NUMBER(LADI.OUT_STANDING) " +
            "             ELSE 0 END)                                                     CURRENT_MONTH_SAVE_OUTSTANDING, " +
            "       COUNT(CASE WHEN LPCM.TOTAL_PAYMENT - LADI.EMI_AMOUNT >= 0 THEN 1 END) CURRENT_MONTH_ONE_EMI_ACCOUNT, " +
            "       SUM(CASE " +
            "             WHEN LPCM.TOTAL_PAYMENT - LADI.EMI_AMOUNT >= 0 THEN TO_NUMBER(LADI.OUT_STANDING) " +
            "             ELSE 0 END)                                                     CURRENT_MONTH_ONE_EMI_OUTSTANDING, " +
            "       COUNT(CASE " +
            "               WHEN LPCM.TOTAL_PAYMENT > 0 AND LPCM.TOTAL_PAYMENT - LADI.EMI_AMOUNT < 0 " +
            "                       THEN 1 END)                                           CURRENT_MONTH_PAID_NOT_SAVE_ACCOUNT, " +
            "       SUM(CASE " +
            "             WHEN LPCM.TOTAL_PAYMENT > 0 AND LPCM.TOTAL_PAYMENT - LADI.EMI_AMOUNT < 0 THEN TO_NUMBER(LADI.OUT_STANDING) " +
            "             ELSE 0 END)                                                     CURRENT_MONTH_PAID_NOT_SAVE_OUTSTANDING, " +
            "       COUNT(LPLD.ACCOUNT_NO)                                                LAST_DATE_TOTAL_ACCOUNT, " +
            "       COALESCE(SUM(LPLD.TOTAL_PAYMENT), 0)                                  LAST_DATE_TOTAL_PAYMENT, " +
            "       COUNT(LADI.LOAN_ACCOUNT_BASIC_INFO_ID) - COUNT(LPLD.ACCOUNT_NO)       LAST_DATE_UNPAID_ACCOUNT, " +
            "       COALESCE(SUM(LADI.OUT_STANDING), 0)                                   LAST_DATE_UNPAID_OUTSTANDING, " +
            "       COUNT(CASE WHEN LPLD.TOTAL_PAYMENT - LADI.EMI_AMOUNT >= 0 THEN 1 END) LAST_DATE_SAVE_ACCOUNT, " +
            "       SUM(CASE " +
            "             WHEN LPLD.TOTAL_PAYMENT - LADI.EMI_AMOUNT >= 0 THEN TO_NUMBER(LADI.OUT_STANDING) " +
            "             ELSE 0 END)                                                     LAST_DATE_SAVE_OUTSTANDING, " +
            "       COUNT(CASE WHEN LPLD.TOTAL_PAYMENT - LADI.EMI_AMOUNT >= 0 THEN 1 END) LAST_DATE_ONE_EMI_ACCOUNT, " +
            "       SUM(CASE " +
            "             WHEN LPLD.TOTAL_PAYMENT - LADI.EMI_AMOUNT >= 0 THEN TO_NUMBER(LADI.OUT_STANDING) " +
            "             ELSE 0 END)                                                     LAST_DATE_ONE_EMI_OUTSTANDING, " +
            "       COUNT(CASE " +
            "               WHEN LPLD.TOTAL_PAYMENT > 0 AND LPLD.TOTAL_PAYMENT - LADI.EMI_AMOUNT < 0 " +
            "                       THEN 1 END)                                           LAST_DATE_PAID_NOT_SAVE_ACCOUNT, " +
            "       SUM(CASE " +
            "             WHEN LPLD.TOTAL_PAYMENT > 0 AND LPLD.TOTAL_PAYMENT - LADI.EMI_AMOUNT < 0 THEN TO_NUMBER(LADI.OUT_STANDING) " +
            "             ELSE 0 END)                                                     LAST_DATE_PAID_NOT_SAVE_OUTSTANDING " +
            "FROM LOAN_ACCOUNT_BASIC_INFO LABI " +
            "       JOIN LOAN_ACCOUNT_DISTRIBUTION_INFO LADI " +
            "         ON LABI.ID = LADI.LOAN_ACCOUNT_BASIC_INFO_ID AND LADI.CREATED_DATE >= TRUNC(SYSDATE, 'MM') " +
            "              AND LADI.LATEST = '1' AND LADI.DEALER_PIN IN(:pin) " +
            "       JOIN LOS_TB_M_USERS U ON LADI.DEALER_PIN = U.USERNAME " +
            "       LEFT JOIN (SELECT ACCOUNT_NO, MAX(PAYMENT_DATE) PAYMENT_DATE FROM LOAN_PAYMENT GROUP BY ACCOUNT_NO) LP " +
            "         ON LABI.ACCOUNT_NO = LP.ACCOUNT_NO " +
            "       LEFT JOIN (SELECT ACCOUNT_NO, SUM(PAYMENT) TOTAL_PAYMENT, MAX(PAYMENT_DATE) LAST_PAYMENT_DATE " +
            "                  FROM LOAN_PAYMENT " +
            "                  WHERE PAYMENT_DATE >= TRUNC(SYSDATE, 'MM') " +
            "                  GROUP BY ACCOUNT_NO) LPCM " +
            "         ON LABI.ACCOUNT_NO = LPCM.ACCOUNT_NO " +
            "       LEFT JOIN (SELECT ACCOUNT_NO, COALESCE(SUM(PAYMENT), 0) TOTAL_PAYMENT " +
            "                  FROM LOAN_PAYMENT " +
            "                  WHERE TRUNC(PAYMENT_DATE, 'DD') = TRUNC(SYSDATE - 1, 'DD') " +
            "                  GROUP BY ACCOUNT_NO) LPLD ON LABI.ACCOUNT_NO = LPLD.ACCOUNT_NO " +
            "GROUP BY LADI.DEALER_PIN, LADI.DEALER_NAME", nativeQuery = true)
    List<Tuple> getTeamPaymentSummary(@Param("pin") List<String> dealerPins);

}
