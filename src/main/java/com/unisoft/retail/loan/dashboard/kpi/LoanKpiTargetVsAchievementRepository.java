package com.unisoft.retail.loan.dashboard.kpi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface LoanKpiTargetVsAchievementRepository extends JpaRepository<LoanKpiTargetVsAchievement, Long> {

    @Modifying
    int deleteAllByCreatedDateBetween(Date startDate, Date endDate);

    /**
     * Performance matrix for dealers based on product group and dpd buckets. Calculations are based on account count
     * in each product and dpd bucket group.
     * <p>
     * Implemented by Yasir Araphat
     * on August 10, 2021
     *
     * @param dealerPins
     * @return List of Tuples
     */
    @Query(value = "" +
            "SELECT PRIMARY_ACHIEVEMENT.DEALER_PIN                              AS DEALER_PIN, " +
            "       PRIMARY_ACHIEVEMENT.PRODUCT                                 AS PRODUCT, " +
            "       PRIMARY_ACHIEVEMENT.BUCKET                                  AS BUCKET, " +
            "       PRIMARY_ACHIEVEMENT.TOTAL                                   AS TOTAL, " +
            "       ROUND(PRIMARY_ACHIEVEMENT.TOTAL * TARGET.FLOW_TARGET, 2)    AS FLOW_TARGET, " +
            "       PRIMARY_ACHIEVEMENT.FLOW_ACHIEVEMENT                        AS FLOW_ACHIEVEMENT, " +
            "       ROUND(PRIMARY_ACHIEVEMENT.TOTAL * WEIGHT.FLOW_WEIGHT, 2)    AS FLOW_WEIGHT, " +
            "       ROUND(PRIMARY_ACHIEVEMENT.TOTAL * TARGET.SAVE_TARGET)       AS SAVE_TARGET, " +
            "       PRIMARY_ACHIEVEMENT.SAVE_ACHIEVEMENT                        AS SAVE_ACHIEVEMENT, " +
            "       ROUND(PRIMARY_ACHIEVEMENT.TOTAL * WEIGHT.SAVE_WEIGHT, 2)    AS SAVE_WEIGHT, " +
            "       ROUND(PRIMARY_ACHIEVEMENT.TOTAL * TARGET.BACK_TARGET, 2)    AS BACK_TARGET, " +
            "       PRIMARY_ACHIEVEMENT.BACK_ACHIEVEMENT                        AS BACK_ACHIEVEMENT, " +
            "       ROUND(PRIMARY_ACHIEVEMENT.TOTAL * WEIGHT.BACK_WEIGHT, 2)    AS BACK_WEIGHT, " +
            "       ROUND(PRIMARY_ACHIEVEMENT.TOTAL * TARGET.OVERDUE_TARGET, 2) AS OVERDUE_TARGET, " +
            "       PRIMARY_ACHIEVEMENT.OVERDUE_ACHIEVEMENT                     AS OVERDUE_ACHIEVEMENT, " +
            "       ROUND(PRIMARY_ACHIEVEMENT.TOTAL * WEIGHT.OVERDUE_WEIGHT, 2) AS OVERDUE_WEIGHT, " +
            "       ROUND(PRIMARY_ACHIEVEMENT.TOTAL * TARGET.REGULAR_TARGET, 2) AS REGULAR_TARGET, " +
            "       PRIMARY_ACHIEVEMENT.REGULAR_ACHIEVEMENT                     AS REGULAR_ACHIEVEMENT, " +
            "       ROUND(PRIMARY_ACHIEVEMENT.TOTAL * WEIGHT.REGULAR_WEIGHT, 2) AS REGULAR_WEIGHT, " +
            "       TARGET.FLOW_TARGET                                          AS FLOW_TARGET_PERCENTAGE, " +
            "       PRIMARY_ACHIEVEMENT.FLOW_ACHIEVEMENT_PERCENTAGE             AS FLOW_ACHIEVEMENT_PERCENTAGE, " +
            "       WEIGHT.FLOW_WEIGHT                                          AS FLOW_WEIGHT_PERCENTAGE, " +
            "       TARGET.SAVE_TARGET                                          AS SAVE_TARGET_PERCENTAGE, " +
            "       PRIMARY_ACHIEVEMENT.SAVE_ACHIEVEMENT_PERCENTAGE             AS SAVE_ACHIEVEMENT_PERCENTAGE, " +
            "       WEIGHT.SAVE_WEIGHT                                          AS SAVE_WEIGHT_PERCENTAGE, " +
            "       TARGET.BACK_TARGET                                          AS BACK_TARGET_PERCENTAGE, " +
            "       PRIMARY_ACHIEVEMENT.BACK_ACHIEVEMENT_PERCENTAGE             AS BACK_ACHIEVEMENT_PERCENTAGE, " +
            "       WEIGHT.BACK_WEIGHT                                          AS BACK_WEIGHT_PERCENTAGE, " +
            "       TARGET.OVERDUE_TARGET                                       AS OVERDUE_TARGET_PERCENTAGE, " +
            "       PRIMARY_ACHIEVEMENT.OVERDUE_ACHIEVEMENT_PERCENTAGE          AS OVERDUE_ACHIEVEMENT_PERCENTAGE, " +
            "       WEIGHT.OVERDUE_WEIGHT                                       AS OVERDUE_WEIGHT_PERCENTAGE, " +
            "       TARGET.REGULAR_TARGET                                       AS REGULAR_TARGET_PERCENTAGE, " +
            "       PRIMARY_ACHIEVEMENT.REGULAR_ACHIEVEMENT_PERCENTAGE          AS REGULAR_ACHIEVEMENT_PERCENTAGE, " +
            "       WEIGHT.REGULAR_WEIGHT                                       AS REGULAR_WEIGHT_PERCENTAGE " +
            "FROM (SELECT DEALER_PIN, " +
            "             PRODUCT, " +
            "             BUCKET, " +
            "             REGULAR + BACK + FLOW + SAVE                       AS TOTAL, " +
            "             FLOW                                               AS FLOW_ACHIEVEMENT, " +
            "             SAVE                                               AS SAVE_ACHIEVEMENT, " +
            "             BACK                                               AS BACK_ACHIEVEMENT, " +
            "             OPENING_OVERDUE - CURRENT_OVERDUE                  AS OVERDUE_ACHIEVEMENT, " +
            "             REGULAR                                            AS REGULAR_ACHIEVEMENT, " +
            "             ROUND(FLOW / DECODE((REGULAR + BACK + FLOW + SAVE), 0, 1, (REGULAR + BACK + FLOW + SAVE)), 2)    AS FLOW_ACHIEVEMENT_PERCENTAGE, " +
            "             ROUND(SAVE / DECODE((REGULAR + BACK + FLOW + SAVE), 0, 1, (REGULAR + BACK + FLOW + SAVE)), 2)    AS SAVE_ACHIEVEMENT_PERCENTAGE, " +
            "             ROUND(BACK / DECODE((REGULAR + BACK + FLOW + SAVE), 0, 1, (REGULAR + BACK + FLOW + SAVE)), 2)    AS BACK_ACHIEVEMENT_PERCENTAGE, " +
            "             ROUND(1 - (OPENING_OVERDUE / DECODE(CURRENT_OVERDUE, 0, 1, CURRENT_OVERDUE)), 2)  AS OVERDUE_ACHIEVEMENT_PERCENTAGE, " +
            "             ROUND(REGULAR / DECODE((REGULAR + BACK + FLOW + SAVE), 0, 1, (REGULAR + BACK + FLOW + SAVE)), 2) AS REGULAR_ACHIEVEMENT_PERCENTAGE " +
            "      FROM (SELECT LADI.DEALER_PIN                                   AS DEALER_PIN, " +
            "                   PGE.NAME                                          AS PRODUCT, " +
            "                   CASE " +
            "                     WHEN CEIL(TO_NUMBER(LADI.DPD_BUCKET)) >= " +
            "                          (SELECT MAX(CEIL(TO_NUMBER(REGEXP_REPLACE(BUCKET_NAME, '^\\D+$', '0')))) FROM DPDBUCKET_ENTITY) " +
            "                             THEN " +
            "                       (SELECT MAX(CEIL(TO_NUMBER(REGEXP_REPLACE(BUCKET_NAME, '^\\D+$', '0')))) FROM DPDBUCKET_ENTITY) || " +
            "                       '+' " +
            "                     ELSE CEIL(TO_NUMBER(LADI.DPD_BUCKET)) || '' END AS BUCKET, " +
            "                   CASE " +
            "                     WHEN TO_NUMBER(LADI.CURRENT_DPD_BUCKET) = 0 THEN 'REGULAR' " +
            "                     WHEN TO_NUMBER(LADI.CURRENT_DPD_BUCKET) > TO_NUMBER(REGEXP_REPLACE(LADI.DPD_BUCKET, '^\\D+$', '0')) " +
            "                             THEN 'FLOW' " +
            "                     WHEN TO_NUMBER(LADI.CURRENT_DPD_BUCKET) < TO_NUMBER(REGEXP_REPLACE(LADI.DPD_BUCKET, '^\\D+$', '0')) " +
            "                             THEN 'BACK' " +
            "                     WHEN TO_NUMBER(LADI.CURRENT_DPD_BUCKET) = TO_NUMBER(REGEXP_REPLACE(LADI.DPD_BUCKET, '^\\D+$', '0')) " +
            "                             THEN 'SAVE' " +
            "                       END                                           AS BUCKET_STATUS, " +
            "                   COALESCE(LADI.OPENING_OVER_DUE, 0)                AS OPENING_OVERDUE, " +
            "                   COALESCE(LADI.CURRENT_OVERDUE, 0)                 AS CURRENT_OVERDUE " +
            "            FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI " +
            "                   JOIN PRODUCT_TYPE_ENTITY PTE on PTE.CODE = LADI.SCHEME_CODE AND PTE.ENABLED = 1 " +
            "                   JOIN PRODUCT_GROUP_ENTITY PGE ON PTE.PRODUCT_GROUP_ENTITY_ID = PGE.ID AND PGE.ENABLED = 1 " +
            "                   JOIN (SELECT ID, " +
            "                                CASE " +
            "                                  WHEN TO_NUMBER(REGEXP_REPLACE(BUCKET_NAME, '^\\D+$', '0')) = " +
            "                                       (SELECT MAX(TO_NUMBER(REGEXP_REPLACE(BUCKET_NAME, '^\\D+$', '0'))) " +
            "                                        FROM DPDBUCKET_ENTITY) " +
            "                                          THEN BUCKET_NAME || '+' " +
            "                                  ELSE BUCKET_NAME END AS BUCKET_NAME, " +
            "                                ENABLED " +
            "                         FROM DPDBUCKET_ENTITY) DBE on DBE.BUCKET_NAME = LADI.DPD_BUCKET AND DBE.ENABLED = 1 " +
            "                   LEFT JOIN (SELECT ID " +
            "                              FROM DPDBUCKET_ENTITY " +
            "                              WHERE TO_NUMBER(REGEXP_REPLACE(BUCKET_NAME, '^\\D+$', '0')) = " +
            "                                    (SELECT MAX(TO_NUMBER(REGEXP_REPLACE(BUCKET_NAME, '^\\D+$', '0'))) " +
            "                                     FROM DPDBUCKET_ENTITY)) MAX_BUCKET ON DBE.ID = MAX_BUCKET.ID " +
            "            WHERE LADI.DEALER_PIN IN (:pin) " +
            "              AND LADI.LATEST = '1' " +
            "              AND LADI.CREATED_DATE BETWEEN :startDate AND :endDate) " +
            "          PIVOT ( " +
            "            COUNT(BUCKET_STATUS) FOR BUCKET_STATUS IN ('REGULAR' REGULAR, 'FLOW' FLOW, 'BACK' BACK, 'SAVE' SAVE) " +
            "          ) " +
            "      ORDER BY PRODUCT, BUCKET) PRIMARY_ACHIEVEMENT " +
            "       LEFT JOIN (SELECT PGE.NAME                        AS PRODUCT, " +
            "                         DBE.BUCKET_NAME                 AS BUCKET, " +
            "                         MAX(KTAL.OVER_DUE_TARGET) / 100 AS OVERDUE_TARGET, " +
            "                         MAX(KTAL.REGULAR_TARGET) / 100  AS REGULAR_TARGET, " +
            "                         MAX(KTAL.FLOW_TARGET) / 100     AS FLOW_TARGET, " +
            "                         MAX(KTAL.BACK_TARGET) / 100     AS BACK_TARGET, " +
            "                         MAX(KTAL.SAVE_TARGET) / 100     AS SAVE_TARGET, " +
            "                         MAX(KTAL.PARREL_TARGET) / 100   AS PARREL_TARGET, " +
            "                         MAX(KTAL.NPLREL_TARGET) / 100   AS NPLREL_TARGET " +
            "                  FROM KPI_TARGET_BY_ACCOUNT_LOAN KTAL " +
            "                         LEFT JOIN KPI_TARGET_BY_ACCOUNT_LOAN_DPD_BUCKET KTALDB " +
            "                           ON KTAL.ID = KTALDB.LOANKPITARGET_BY_ACCOUNT_ENTITY_ID " +
            "                         LEFT JOIN KPI_TARGET_BY_ACCOUNT_LOAN_PRODUCT_TYPE KTALPT " +
            "                           ON KTAL.ID = KTALPT.LOANKPITARGET_BY_ACCOUNT_ENTITY_ID " +
            "                         LEFT JOIN (SELECT ID, " +
            "                                           CASE " +
            "                                             WHEN TO_NUMBER(REGEXP_REPLACE(BUCKET_NAME, '^\\D+$', '0')) = " +
            "                                                  (SELECT MAX(TO_NUMBER(REGEXP_REPLACE(BUCKET_NAME, '^\\D+$', '0'))) " +
            "                                                   FROM DPDBUCKET_ENTITY) " +
            "                                                     THEN BUCKET_NAME || '+' " +
            "                                             ELSE BUCKET_NAME END AS BUCKET_NAME, " +
            "                                           ENABLED " +
            "                                    FROM DPDBUCKET_ENTITY) DBE ON KTALDB.DPD_BUCKET_ID = DBE.ID " +
            "                         LEFT JOIN (SELECT ID " +
            "                                    FROM DPDBUCKET_ENTITY " +
            "                                    WHERE TO_NUMBER(REGEXP_REPLACE(BUCKET_NAME, '^\\D+$', '0')) = " +
            "                                          (SELECT MAX(TO_NUMBER(REGEXP_REPLACE(BUCKET_NAME, '^\\D+$', '0'))) " +
            "                                           FROM DPDBUCKET_ENTITY)) MAX_BUCKET ON DBE.ID = MAX_BUCKET.ID " +
            "                         LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON KTALPT.PRODUCT_TYPE_ID = PTE.ID AND PTE.ENABLED = 1 " +
            "                         LEFT JOIN PRODUCT_GROUP_ENTITY PGE on PTE.PRODUCT_GROUP_ENTITY_ID = PGE.ID AND PGE.ENABLED = 1 " +
            "                  WHERE KTAL.ENABLED = 1 " +
            "                  GROUP BY PGE.NAME, DBE.BUCKET_NAME) TARGET " +
            "         ON (PRIMARY_ACHIEVEMENT.PRODUCT = TARGET.PRODUCT AND PRIMARY_ACHIEVEMENT.BUCKET = TARGET.BUCKET) OR " +
            "            PRIMARY_ACHIEVEMENT.BUCKET = TARGET.BUCKET " +
            "       LEFT JOIN (SELECT PGE.NAME                         AS PRODUCT, " +
            "                         DBE.BUCKET_NAME                  AS BUCKET, " +
            "                         MAX(KTWAL.OVER_DUE_WEIGHT) / 100 AS OVERDUE_WEIGHT, " +
            "                         MAX(KTWAL.REGULAR_WEIGHT) / 100  AS REGULAR_WEIGHT, " +
            "                         MAX(KTWAL.FLOW_WEIGHT) / 100     AS FLOW_WEIGHT, " +
            "                         MAX(KTWAL.BACK_WEIGHT) / 100     AS BACK_WEIGHT, " +
            "                         MAX(KTWAL.SAVE_WEIGHT) / 100     AS SAVE_WEIGHT, " +
            "                         MAX(KTWAL.PARREL_WEIGHT) / 100   AS PARREL_WEIGHT, " +
            "                         MAX(KTWAL.NPLREL_WEIGHT) / 100   AS NPLREL_WEIGHT " +
            "                  FROM KPI_TARGET_WEIGHT_ACCOUNT_LOAN KTWAL " +
            "                         LEFT JOIN KPI_TARGET_WEIGHT_ACCOUNT_LOAN_DPD_BUCKET KTWALDB " +
            "                           ON KTWAL.ID = KTWALDB.LOANKPITARGET_WEIGHT_BY_ACCOUNT_ENTITY_ID " +
            "                         LEFT JOIN KPI_TARGET_WEIGHT_ACCOUNT_LOAN_PRODUCT_TYPE KTWALPT " +
            "                           ON KTWAL.ID = KTWALPT.LOANKPITARGET_WEIGHT_BY_ACCOUNT_ENTITY_ID " +
            "                         LEFT JOIN (SELECT ID, " +
            "                                           CASE " +
            "                                             WHEN TO_NUMBER(REGEXP_REPLACE(BUCKET_NAME, '^\\D+$', '0')) = " +
            "                                                  (SELECT MAX(TO_NUMBER(REGEXP_REPLACE(BUCKET_NAME, '^\\D+$', '0'))) " +
            "                                                   FROM DPDBUCKET_ENTITY) " +
            "                                                     THEN BUCKET_NAME || '+' " +
            "                                             ELSE BUCKET_NAME END AS BUCKET_NAME, " +
            "                                           ENABLED " +
            "                                    FROM DPDBUCKET_ENTITY) DBE ON KTWALDB.DPD_BUCKET_ID = DBE.ID " +
            "                         LEFT JOIN (SELECT ID " +
            "                                    FROM DPDBUCKET_ENTITY " +
            "                                    WHERE TO_NUMBER(REGEXP_REPLACE(BUCKET_NAME, '^\\D+$', '0')) = " +
            "                                          (SELECT MAX(TO_NUMBER(REGEXP_REPLACE(BUCKET_NAME, '^\\D+$', '0'))) " +
            "                                           FROM DPDBUCKET_ENTITY)) MAX_BUCKET ON DBE.ID = MAX_BUCKET.ID " +
            "                         LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON KTWALPT.PRODUCT_TYPE_ID = PTE.ID AND PTE.ENABLED = 1 " +
            "                         LEFT JOIN PRODUCT_GROUP_ENTITY PGE on PTE.PRODUCT_GROUP_ENTITY_ID = PGE.ID AND PGE.ENABLED = 1 " +
            "                  WHERE KTWAL.ENABLED = 1 " +
            "                  GROUP BY PGE.NAME, DBE.BUCKET_NAME) WEIGHT " +
            "         ON (PRIMARY_ACHIEVEMENT.PRODUCT = WEIGHT.PRODUCT AND PRIMARY_ACHIEVEMENT.BUCKET = WEIGHT.BUCKET) OR " +
            "            PRIMARY_ACHIEVEMENT.BUCKET = WEIGHT.BUCKET " +
            "ORDER BY PRODUCT, BUCKET", nativeQuery = true)
    List<Tuple> getKpiAccountWiseSummary(@Param("pin") List<String> dealerPins, @Param("startDate") Date startDate, @Param("endDate") Date endDate);


    /**
     * Performance matrix for dealers based on product group and dpd buckets. Calculations are based on sum of outstanding
     * in each product and dpd bucket group.
     * <p>
     * Implemented by Yasir Araphat
     * on August 10, 2021
     *
     * @param dealerPins
     * @return List of Tuples
     */
    @Query(value = "" +
            "SELECT PRIMARY_ACHIEVEMENT.DEALER_PIN                              AS DEALER_PIN, " +
            "       PRIMARY_ACHIEVEMENT.PRODUCT                                 AS PRODUCT, " +
            "       PRIMARY_ACHIEVEMENT.BUCKET                                  AS BUCKET, " +
            "       PRIMARY_ACHIEVEMENT.TOTAL                                   AS TOTAL, " +
            "       ROUND(PRIMARY_ACHIEVEMENT.TOTAL * TARGET.FLOW_TARGET, 2)    AS FLOW_TARGET, " +
            "       PRIMARY_ACHIEVEMENT.FLOW_ACHIEVEMENT                        AS FLOW_ACHIEVEMENT, " +
            "       ROUND(PRIMARY_ACHIEVEMENT.TOTAL * WEIGHT.FLOW_WEIGHT, 2)    AS FLOW_WEIGHT, " +
            "       ROUND(PRIMARY_ACHIEVEMENT.TOTAL * TARGET.SAVE_TARGET)       AS SAVE_TARGET, " +
            "       PRIMARY_ACHIEVEMENT.SAVE_ACHIEVEMENT                        AS SAVE_ACHIEVEMENT, " +
            "       ROUND(PRIMARY_ACHIEVEMENT.TOTAL * WEIGHT.SAVE_WEIGHT, 2)    AS SAVE_WEIGHT, " +
            "       ROUND(PRIMARY_ACHIEVEMENT.TOTAL * TARGET.BACK_TARGET, 2)    AS BACK_TARGET, " +
            "       PRIMARY_ACHIEVEMENT.BACK_ACHIEVEMENT                        AS BACK_ACHIEVEMENT, " +
            "       ROUND(PRIMARY_ACHIEVEMENT.TOTAL * WEIGHT.BACK_WEIGHT, 2)    AS BACK_WEIGHT, " +
            "       ROUND(PRIMARY_ACHIEVEMENT.TOTAL * TARGET.OVERDUE_TARGET, 2) AS OVERDUE_TARGET, " +
            "       PRIMARY_ACHIEVEMENT.OVERDUE_ACHIEVEMENT                     AS OVERDUE_ACHIEVEMENT, " +
            "       ROUND(PRIMARY_ACHIEVEMENT.TOTAL * WEIGHT.OVERDUE_WEIGHT, 2) AS OVERDUE_WEIGHT, " +
            "       ROUND(PRIMARY_ACHIEVEMENT.TOTAL * TARGET.REGULAR_TARGET, 2) AS REGULAR_TARGET, " +
            "       PRIMARY_ACHIEVEMENT.REGULAR_ACHIEVEMENT                     AS REGULAR_ACHIEVEMENT, " +
            "       ROUND(PRIMARY_ACHIEVEMENT.TOTAL * WEIGHT.REGULAR_WEIGHT, 2) AS REGULAR_WEIGHT, " +
            "       TARGET.FLOW_TARGET                                          AS FLOW_TARGET_PERCENTAGE, " +
            "       PRIMARY_ACHIEVEMENT.FLOW_ACHIEVEMENT_PERCENTAGE             AS FLOW_ACHIEVEMENT_PERCENTAGE, " +
            "       WEIGHT.FLOW_WEIGHT                                          AS FLOW_WEIGHT_PERCENTAGE, " +
            "       TARGET.SAVE_TARGET                                          AS SAVE_TARGET_PERCENTAGE, " +
            "       PRIMARY_ACHIEVEMENT.SAVE_ACHIEVEMENT_PERCENTAGE             AS SAVE_ACHIEVEMENT_PERCENTAGE, " +
            "       WEIGHT.SAVE_WEIGHT                                          AS SAVE_WEIGHT_PERCENTAGE, " +
            "       TARGET.BACK_TARGET                                          AS BACK_TARGET_PERCENTAGE, " +
            "       PRIMARY_ACHIEVEMENT.BACK_ACHIEVEMENT_PERCENTAGE             AS BACK_ACHIEVEMENT_PERCENTAGE, " +
            "       WEIGHT.BACK_WEIGHT                                          AS BACK_WEIGHT_PERCENTAGE, " +
            "       TARGET.OVERDUE_TARGET                                       AS OVERDUE_TARGET_PERCENTAGE, " +
            "       PRIMARY_ACHIEVEMENT.OVERDUE_ACHIEVEMENT_PERCENTAGE          AS OVERDUE_ACHIEVEMENT_PERCENTAGE, " +
            "       WEIGHT.OVERDUE_WEIGHT                                       AS OVERDUE_WEIGHT_PERCENTAGE, " +
            "       TARGET.REGULAR_TARGET                                       AS REGULAR_TARGET_PERCENTAGE, " +
            "       PRIMARY_ACHIEVEMENT.REGULAR_ACHIEVEMENT_PERCENTAGE          AS REGULAR_ACHIEVEMENT_PERCENTAGE, " +
            "       WEIGHT.REGULAR_WEIGHT                                       AS REGULAR_WEIGHT_PERCENTAGE " +
            "FROM (SELECT DEALER_PIN, " +
            "             PRODUCT, " +
            "             BUCKET, " +
            "             REGULAR + BACK + FLOW + SAVE                       AS TOTAL, " +
            "             FLOW                                               AS FLOW_ACHIEVEMENT, " +
            "             SAVE                                               AS SAVE_ACHIEVEMENT, " +
            "             BACK                                               AS BACK_ACHIEVEMENT, " +
            "             OPENING_OVERDUE - CURRENT_OVERDUE                  AS OVERDUE_ACHIEVEMENT, " +
            "             REGULAR                                            AS REGULAR_ACHIEVEMENT, " +
            "             ROUND(FLOW / DECODE((REGULAR + BACK + FLOW + SAVE), 0, 1, (REGULAR + BACK + FLOW + SAVE)), 2)    AS FLOW_ACHIEVEMENT_PERCENTAGE, " +
            "             ROUND(SAVE / DECODE((REGULAR + BACK + FLOW + SAVE), 0, 1, (REGULAR + BACK + FLOW + SAVE)), 2)    AS SAVE_ACHIEVEMENT_PERCENTAGE, " +
            "             ROUND(BACK / DECODE((REGULAR + BACK + FLOW + SAVE), 0, 1, (REGULAR + BACK + FLOW + SAVE)), 2)    AS BACK_ACHIEVEMENT_PERCENTAGE, " +
            "             ROUND(1 - (OPENING_OVERDUE / DECODE(CURRENT_OVERDUE, 0, 1, CURRENT_OVERDUE)), 2)  AS OVERDUE_ACHIEVEMENT_PERCENTAGE, " +
            "             ROUND(REGULAR / DECODE((REGULAR + BACK + FLOW + SAVE), 0, 1, (REGULAR + BACK + FLOW + SAVE)), 2) AS REGULAR_ACHIEVEMENT_PERCENTAGE " +
            "      FROM (SELECT LADI.DEALER_PIN                                   AS DEALER_PIN, " +
            "                   PGE.NAME                                          AS PRODUCT, " +
            "                   CASE " +
            "                     WHEN CEIL(TO_NUMBER(LADI.DPD_BUCKET)) >= " +
            "                          (SELECT MAX(CEIL(TO_NUMBER(REGEXP_REPLACE(BUCKET_NAME, '^\\D+$', '0')))) FROM DPDBUCKET_ENTITY) " +
            "                             THEN " +
            "                       (SELECT MAX(CEIL(TO_NUMBER(REGEXP_REPLACE(BUCKET_NAME, '^\\D+$', '0')))) FROM DPDBUCKET_ENTITY) || " +
            "                       '+' " +
            "                     ELSE CEIL(TO_NUMBER(LADI.DPD_BUCKET)) || '' END AS BUCKET, " +
            "                   CASE " +
            "                     WHEN TO_NUMBER(LADI.CURRENT_DPD_BUCKET) = 0 THEN 'REGULAR' " +
            "                     WHEN TO_NUMBER(LADI.CURRENT_DPD_BUCKET) > TO_NUMBER(REGEXP_REPLACE(LADI.DPD_BUCKET, '^\\D+$', '0')) " +
            "                             THEN 'FLOW' " +
            "                     WHEN TO_NUMBER(LADI.CURRENT_DPD_BUCKET) < TO_NUMBER(REGEXP_REPLACE(LADI.DPD_BUCKET, '^\\D+$', '0')) " +
            "                             THEN 'BACK' " +
            "                     WHEN TO_NUMBER(LADI.CURRENT_DPD_BUCKET) = TO_NUMBER(REGEXP_REPLACE(LADI.DPD_BUCKET, '^\\D+$', '0')) " +
            "                             THEN 'SAVE' " +
            "                       END                                           AS BUCKET_STATUS, " +
            "                   COALESCE(LADI.OUT_STANDING, '0')                  AS OUTSTANDING, " +
            "                   COALESCE(LADI.OPENING_OVER_DUE, 0)                AS OPENING_OVERDUE, " +
            "                   COALESCE(LADI.CURRENT_OVERDUE, 0)                 AS CURRENT_OVERDUE " +
            "            FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI " +
            "                   JOIN PRODUCT_TYPE_ENTITY PTE on PTE.CODE = LADI.SCHEME_CODE AND PTE.ENABLED = 1 " +
            "                   JOIN PRODUCT_GROUP_ENTITY PGE ON PTE.PRODUCT_GROUP_ENTITY_ID = PGE.ID AND PGE.ENABLED = 1 " +
            "                   JOIN (SELECT ID, " +
            "                                CASE " +
            "                                  WHEN TO_NUMBER(REGEXP_REPLACE(BUCKET_NAME, '^\\D+$', '0')) = " +
            "                                       (SELECT MAX(TO_NUMBER(REGEXP_REPLACE(BUCKET_NAME, '^\\D+$', '0'))) " +
            "                                        FROM DPDBUCKET_ENTITY) " +
            "                                          THEN BUCKET_NAME || '+' " +
            "                                  ELSE BUCKET_NAME END AS BUCKET_NAME, " +
            "                                ENABLED " +
            "                         FROM DPDBUCKET_ENTITY) DBE on DBE.BUCKET_NAME = LADI.DPD_BUCKET AND DBE.ENABLED = 1 " +
            "                   LEFT JOIN (SELECT ID " +
            "                              FROM DPDBUCKET_ENTITY " +
            "                              WHERE TO_NUMBER(REGEXP_REPLACE(BUCKET_NAME, '^\\D+$', '0')) = " +
            "                                    (SELECT MAX(TO_NUMBER(REGEXP_REPLACE(BUCKET_NAME, '^\\D+$', '0'))) " +
            "                                     FROM DPDBUCKET_ENTITY)) MAX_BUCKET ON DBE.ID = MAX_BUCKET.ID " +
            "            WHERE LADI.DEALER_PIN IN (:pin) " +
            "              AND LADI.LATEST = '1' " +
            "              AND LADI.CREATED_DATE BETWEEN :startDate AND :endDate) " +
            "          PIVOT ( " +
            "            SUM(OUTSTANDING) FOR BUCKET_STATUS IN ('REGULAR' REGULAR, 'FLOW' FLOW, 'BACK' BACK, 'SAVE' SAVE) " +
            "          ) " +
            "      ORDER BY PRODUCT, BUCKET) PRIMARY_ACHIEVEMENT " +
            "       LEFT JOIN (SELECT PGE.NAME                       AS PRODUCT, " +
            "                         DBE.BUCKET_NAME                AS BUCKET, " +
            "                         MAX(KTAL.OVER_DUE_TARET) / 100 AS OVERDUE_TARGET, " +
            "                         MAX(KTAL.REGULAR_TARGET) / 100 AS REGULAR_TARGET, " +
            "                         MAX(KTAL.FLOW_TARGET) / 100    AS FLOW_TARGET, " +
            "                         MAX(KTAL.BACK_TARGET) / 100    AS BACK_TARGET, " +
            "                         MAX(KTAL.SAVE_TARGET) / 100    AS SAVE_TARGET, " +
            "                         MAX(KTAL.PARREL_TARGET) / 100  AS PARREL_TARGET, " +
            "                         MAX(KTAL.NPLREL_TARGET) / 100  AS NPLREL_TARGET " +
            "                  FROM KPI_TARGET_BY_AMOUNT_LOAN KTAL " +
            "                         LEFT JOIN KPI_TARGET_BY_AMOUNT_LOAN_DPD_BUCKET KTALDB " +
            "                           ON KTAL.ID = KTALDB.KPI_TARGET_BY_AMOUNT_LOAN_ID " +
            "                         LEFT JOIN KPI_TARGET_BY_AMOUNT_LOAN_PRODUCT_TYPE KTALPT " +
            "                           ON KTAL.ID = KTALPT.KPI_TARGET_BY_AMOUNT_LOAN_ID " +
            "                         LEFT JOIN (SELECT ID, " +
            "                                           CASE " +
            "                                             WHEN TO_NUMBER(REGEXP_REPLACE(BUCKET_NAME, '^\\D+$', '0')) = " +
            "                                                  (SELECT MAX(TO_NUMBER(REGEXP_REPLACE(BUCKET_NAME, '^\\D+$', '0'))) " +
            "                                                   FROM DPDBUCKET_ENTITY) " +
            "                                                     THEN BUCKET_NAME || '+' " +
            "                                             ELSE BUCKET_NAME END AS BUCKET_NAME, " +
            "                                           ENABLED " +
            "                                    FROM DPDBUCKET_ENTITY) DBE ON KTALDB.DPD_BUCKET_ID = DBE.ID " +
            "                         LEFT JOIN (SELECT ID " +
            "                                    FROM DPDBUCKET_ENTITY " +
            "                                    WHERE TO_NUMBER(REGEXP_REPLACE(BUCKET_NAME, '^\\D+$', '0')) = " +
            "                                          (SELECT MAX(TO_NUMBER(REGEXP_REPLACE(BUCKET_NAME, '^\\D+$', '0'))) " +
            "                                           FROM DPDBUCKET_ENTITY)) MAX_BUCKET ON DBE.ID = MAX_BUCKET.ID " +
            "                         LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON KTALPT.PRODUCT_TYPE_ID = PTE.ID AND PTE.ENABLED = 1 " +
            "                         LEFT JOIN PRODUCT_GROUP_ENTITY PGE on PTE.PRODUCT_GROUP_ENTITY_ID = PGE.ID AND PGE.ENABLED = 1 " +
            "                  WHERE KTAL.ENABLED = 1 " +
            "                  GROUP BY PGE.NAME, DBE.BUCKET_NAME) TARGET " +
            "         ON (PRIMARY_ACHIEVEMENT.PRODUCT = TARGET.PRODUCT AND PRIMARY_ACHIEVEMENT.BUCKET = TARGET.BUCKET) OR " +
            "            (TARGET.PRODUCT IS NULL AND PRIMARY_ACHIEVEMENT.BUCKET = TARGET.BUCKET) " +
            "       LEFT JOIN (SELECT PGE.NAME                         AS PRODUCT, " +
            "                         DBE.BUCKET_NAME                  AS BUCKET, " +
            "                         MAX(KTWAL.OVER_DUE_WEIGHT) / 100 AS OVERDUE_WEIGHT, " +
            "                         MAX(KTWAL.REGULAR_WEIGHT) / 100  AS REGULAR_WEIGHT, " +
            "                         MAX(KTWAL.FLOW_WEIGHT) / 100     AS FLOW_WEIGHT, " +
            "                         MAX(KTWAL.BACK_WEIGHT) / 100     AS BACK_WEIGHT, " +
            "                         MAX(KTWAL.SAVE_WEIGHT) / 100     AS SAVE_WEIGHT, " +
            "                         MAX(KTWAL.PARREL_WEIGHT) / 100   AS PARREL_WEIGHT, " +
            "                         MAX(KTWAL.NPLREL_WEIGHT) / 100   AS NPLREL_WEIGHT " +
            "                  FROM KPI_TARGET_WEIGHT_AMOUNT_LOAN KTWAL " +
            "                         LEFT JOIN KPI_TARGET_WEIGHT_AMOUNT_LOAN_DPD_BUCKET KTWALDB " +
            "                           ON KTWAL.ID = KTWALDB.LOANKPITARGET_WEIGHT_BY_AMOUNT_ENTITY_ID " +
            "                         LEFT JOIN KPI_TARGET_WEIGHT_AMOUNT_LOAN_PRODUCT_TYPE KTWALPT " +
            "                           ON KTWAL.ID = KTWALPT.LOANKPITARGET_WEIGHT_BY_AMOUNT_ENTITY_ID " +
            "                         LEFT JOIN (SELECT ID, " +
            "                                           CASE " +
            "                                             WHEN TO_NUMBER(REGEXP_REPLACE(BUCKET_NAME, '^\\D+$', '0')) = " +
            "                                                  (SELECT MAX(TO_NUMBER(REGEXP_REPLACE(BUCKET_NAME, '^\\D+$', '0'))) " +
            "                                                   FROM DPDBUCKET_ENTITY) " +
            "                                                     THEN BUCKET_NAME || '+' " +
            "                                             ELSE BUCKET_NAME END AS BUCKET_NAME, " +
            "                                           ENABLED " +
            "                                    FROM DPDBUCKET_ENTITY) DBE ON KTWALDB.DPD_BUCKET_ID = DBE.ID " +
            "                         LEFT JOIN (SELECT ID " +
            "                                    FROM DPDBUCKET_ENTITY " +
            "                                    WHERE TO_NUMBER(REGEXP_REPLACE(BUCKET_NAME, '^\\D+$', '0')) = " +
            "                                          (SELECT MAX(TO_NUMBER(REGEXP_REPLACE(BUCKET_NAME, '^\\D+$', '0'))) " +
            "                                           FROM DPDBUCKET_ENTITY)) MAX_BUCKET ON DBE.ID = MAX_BUCKET.ID " +
            "                         LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON KTWALPT.PRODUCT_TYPE_ID = PTE.ID AND PTE.ENABLED = 1 " +
            "                         LEFT JOIN PRODUCT_GROUP_ENTITY PGE on PTE.PRODUCT_GROUP_ENTITY_ID = PGE.ID AND PGE.ENABLED = 1 " +
            "                  WHERE KTWAL.ENABLED = 1 " +
            "                  GROUP BY PGE.NAME, DBE.BUCKET_NAME) WEIGHT " +
            "         ON (PRIMARY_ACHIEVEMENT.PRODUCT = WEIGHT.PRODUCT AND PRIMARY_ACHIEVEMENT.BUCKET = WEIGHT.BUCKET) OR " +
            "            (WEIGHT.PRODUCT IS NULL AND PRIMARY_ACHIEVEMENT.BUCKET = WEIGHT.BUCKET) " +
            "ORDER BY PRODUCT, BUCKET", nativeQuery = true)
    List<Tuple> getKpiAmountWiseSummary(@Param("pin") List<String> dealerPins, @Param("startDate") Date startDate, @Param("endDate") Date endDate);


}
