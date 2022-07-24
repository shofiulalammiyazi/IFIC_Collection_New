package com.unisoft.collection.samd.dashboard.performance;

import com.unisoft.customerloanprofile.loanpayment.LoanPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.Date;

@Repository
public interface SamdDashboardPerformanceRepository extends JpaRepository<LoanPayment, Long> {

    @Query(value = "" +
            "SELECT LADI.DEALER_PIN              AS dealerPin, " +
            "       0                            AS performanceAchievement, " +
            "       '-'                          AS lastMonthRating, " +
            "       COUNT(PAR_RULE.PAR_ID)       AS totalParRemainingAccount, " +
            "       SUM(CASE " +
            "             WHEN PAR_RULE.PAR_ID IS NOT NULL THEN PAR_RULE.OUTSTANDING " +
            "             ELSE 0 END)            AS totalParRemainingAmount, " +
            "       COUNT(PAR_RULE.PAR_QUEUE_ID) AS totalParQueueAccount, " +
            "       SUM(CASE " +
            "             WHEN PAR_RULE.PAR_QUEUE_ID IS NOT NULL THEN PAR_RULE.OUTSTANDING " +
            "             ELSE 0 END)            AS totalParQueueAmount, " +
            "       COUNT(NPL_RULE.NPL_ID)       AS totalNplRemainingAccount, " +
            "       SUM(CASE " +
            "             WHEN NPL_RULE.NPL_ID IS NOT NULL THEN NPL_RULE.OUTSTANDING " +
            "             ELSE 0 END)            AS totalNplRemainingAmount, " +
            "       COUNT(NPL_RULE.NPL_QUEUE_ID) AS totalNplQueueAccount, " +
            "       SUM(CASE " +
            "             WHEN NPL_RULE.NPL_QUEUE_ID IS NOT NULL THEN NPL_RULE.OUTSTANDING " +
            "             ELSE 0 END)            AS totalNplQueueAmount " +
            "FROM (SELECT ID, " +
            "             DEALER_PIN, " +
            "             CASE " +
            "               WHEN CEIL(TO_NUMBER(CURRENT_DPD_BUCKET)) > " +
            "                    (SELECT MAX(CEIL(TO_NUMBER(BUCKET_NAME))) FROM DPDBUCKET_ENTITY) " +
            "                       THEN (SELECT MAX(CEIL(TO_NUMBER(BUCKET_NAME))) FROM DPDBUCKET_ENTITY) || '+' " +
            "               ELSE CEIL(TO_NUMBER(CURRENT_DPD_BUCKET)) || '' END CURRENT_DPD_BUCKET, " +
            "             LOAN_ACCOUNT_BASIC_INFO_ID, " +
            "             SCHEME_CODE " +
            "      FROM LMS_COLLECTION_SAM_LOAN_ACCOUNT_DISTRIBUTIONS LADI_IN " +
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
            "       LEFT JOIN (SELECT LADI_IN.ID           AS ID, " +
            "                         LADI_IN.OUT_STANDING AS OUTSTANDING, " +
            "                         BUCKET.BUCKET_NAME   AS DPD_BUCKET, " +
            "                         PTE.NAME             AS PRODUCT_TYPE, " +
            "                         PAR.BUCKET_ID        AS PAR_ID, " +
            "                         PARQ.BUCKET_ID       AS PAR_QUEUE_ID " +
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
            "       LEFT JOIN (SELECT LADI_IN.ID                    AS ID, " +
            "                         LADI_IN.OUT_STANDING          AS OUTSTANDING, " +
            "                         CASE " +
            "                           WHEN TO_NUMBER(LADI_IN.CURRENT_DPD_BUCKET) > " +
            "                                (SELECT MAX(TO_NUMBER(BUCKET_NAME)) FROM DPDBUCKET_ENTITY) " +
            "                                   THEN (SELECT MAX(TO_NUMBER(BUCKET_NAME)) FROM DPDBUCKET_ENTITY) || '+' " +
            "                           ELSE BUCKET.BUCKET_NAME END AS DPD_BUCKET, " +
            "                         PTE.NAME                      AS PRODUCT_TYPE, " +
            "                         CASE " +
            "                           WHEN TO_NUMBER(LADI_IN.CURRENT_DPD_BUCKET) > " +
            "                                (SELECT MAX(TO_NUMBER(BUCKET_NAME)) FROM DPDBUCKET_ENTITY) " +
            "                                   THEN 1 " +
            "                           ELSE NPL.BUCKET_ID END      AS NPL_ID, " +
            "                         NPLQ.BUCKET_ID                AS NPL_QUEUE_ID " +
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
    Tuple getPerformanceData(@Param("pin") String dealerPin, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
