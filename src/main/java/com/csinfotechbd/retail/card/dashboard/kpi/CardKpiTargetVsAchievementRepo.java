package com.csinfotechbd.retail.card.dashboard.kpi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface CardKpiTargetVsAchievementRepo extends JpaRepository<CardKpiTargetVsAchievement,Long> {
    
    @Query(value = "" +
            "SELECT PRIMARY_ACHIEVEMENT.DEALER_PIN                              AS DEALER_PIN, " +
            "       PRIMARY_ACHIEVEMENT.PRODUCT                                 AS PRODUCT, " +
            "       PRIMARY_ACHIEVEMENT.AGE_CODE                                  AS AGE_CODE, " +
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
            "             AGE_CODE, " +
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
            "                     WHEN CEIL(TO_NUMBER(LADI.AGE_CODE)) >= " +
            "                          (SELECT MAX(CEIL(TO_NUMBER(REGEXP_REPLACE(AGE_CODE.NAME, '^\\D+$', '0')))) FROM AGE_CODE) " +
            "                             THEN " +
            "                       (SELECT MAX(CEIL(TO_NUMBER(REGEXP_REPLACE(AGE_CODE.NAME, '^\\D+$', '0')))) FROM AGE_CODE) || " +
            "                       '+' " +
            "                     ELSE CEIL(TO_NUMBER(LADI.AGE_CODE)) || '' END AS AGE_CODE, " +
            "                   CASE " +
            "                     WHEN TO_NUMBER(LADI.CURRENT_AGE_CODE) = 0 THEN 'REGULAR' " +
            "                     WHEN TO_NUMBER(LADI.CURRENT_AGE_CODE) > TO_NUMBER(REGEXP_REPLACE(LADI.AGE_CODE, '^\\D+$', '0')) " +
            "                             THEN 'FLOW' " +
            "                     WHEN TO_NUMBER(LADI.CURRENT_AGE_CODE) < TO_NUMBER(REGEXP_REPLACE(LADI.AGE_CODE, '^\\D+$', '0')) " +
            "                             THEN 'BACK' " +
            "                     WHEN TO_NUMBER(LADI.CURRENT_AGE_CODE) = TO_NUMBER(REGEXP_REPLACE(LADI.AGE_CODE, '^\\D+$', '0')) " +
            "                             THEN 'SAVE' " +
            "                       END                                           AS AGE_CODE_STATUS, " +
            "                   COALESCE(LADI.OPENING_OVER_DUE, 0)                AS OPENING_OVERDUE, " +
            "                   COALESCE(LADI.CURRENT_OVER_DUE, 0)                 AS CURRENT_OVERDUE " +
            "            FROM CARD_ACCOUNT_DISTRIBUTION_INFO LADI " +
            "                   JOIN PRODUCT_TYPE_ENTITY PTE on PTE.CODE = LADI.SCHEME_CODE AND PTE.ENABLED = 1 " +
            "                   JOIN PRODUCT_GROUP_ENTITY PGE ON PTE.PRODUCT_GROUP_ENTITY_ID = PGE.ID AND PGE.ENABLED = 1 " +
            "                   JOIN (SELECT ID, " +
            "                                CASE " +
            "                                  WHEN TO_NUMBER(REGEXP_REPLACE(NAME , '^\\D+$', '0')) = " +
            "                                       (SELECT MAX(TO_NUMBER(REGEXP_REPLACE(NAME, '^\\D+$', '0'))) " +
            "                                        FROM AGE_CODE) " +
            "                                          THEN NAME || '+' " +
            "                                  ELSE NAME END AS AGE_CODE_NAME, " +
            "                                ENABLED " +
            "                         FROM AGE_CODE) DBE on DBE.AGE_CODE_NAME = LADI.AGE_CODE AND DBE.ENABLED = 1 " +
            "                   LEFT JOIN (SELECT ID " +
            "                              FROM AGE_CODE " +
            "                              WHERE TO_NUMBER(REGEXP_REPLACE(NAME, '^\\D+$', '0')) = " +
            "                                    (SELECT MAX(TO_NUMBER(REGEXP_REPLACE(NAME, '^\\D+$', '0'))) " +
            "                                     FROM AGE_CODE)) MAX_AGE_CODE ON DBE.ID = MAX_AGE_CODE.ID " +
            "            WHERE LADI.DEALER_PIN IN (:pin) " +
            "              AND LADI.LATEST = '1' " +
            "              AND LADI.CREATED_DATE BETWEEN :startDate AND :endDate) " +
            "          PIVOT ( " +
            "            COUNT(AGE_CODE_STATUS) FOR AGE_CODE_STATUS IN ('REGULAR' REGULAR, 'FLOW' FLOW, 'BACK' BACK, 'SAVE' SAVE) " +
            "          ) " +
            "      ORDER BY PRODUCT, AGE_CODE) PRIMARY_ACHIEVEMENT " +
            "       LEFT JOIN (SELECT PGE.NAME                        AS PRODUCT, " +
            "                         DBE.AGE_CODE_NAME                 AS AGE_CODE, " +
            "                         MAX(KTAL.MIN_DUE_TARGET) / 100 AS OVERDUE_TARGET, " +
            "                         MAX(KTAL.REGULAR_TARGET) / 100  AS REGULAR_TARGET, " +
            "                         MAX(KTAL.FLOW_TARGET) / 100     AS FLOW_TARGET, " +
            "                         MAX(KTAL.BACK_TARGET) / 100     AS BACK_TARGET, " +
            "                         MAX(KTAL.SAVE_TARGET) / 100     AS SAVE_TARGET, " +
            "                         MAX(KTAL.PARREL_TARGET) / 100   AS PARREL_TARGET, " +
            "                         MAX(KTAL.NPLREL_TARGET) / 100   AS NPLREL_TARGET " +
            "                  FROM KPI_TARGET_BY_ACCOUNT_CARD KTAL " +
            "                         LEFT JOIN KPI_TARGET_BY_ACCOUNT_CARD_AGE_CODE KTALDB " +
            "                           ON KTAL.ID = KTALDB.CARDKPITARGET_BY_ACCOUNT_ENTITY_ID " +
            "                         LEFT JOIN KPI_TARGET_BY_ACCOUNT_CARD_PRODUCT_TYPE KTALPT " +
            "                           ON KTAL.ID = KTALPT.CARDKPITARGET_BY_ACCOUNT_ENTITY_ID " +
            "                         LEFT JOIN (SELECT ID, " +
            "                                           CASE " +
            "                                             WHEN TO_NUMBER(REGEXP_REPLACE(NAME , '^\\D+$', '0')) = " +
            "                                                  (SELECT MAX(TO_NUMBER(REGEXP_REPLACE(NAME, '^\\D+$', '0'))) " +
            "                                                   FROM AGE_CODE) " +
            "                                                     THEN NAME || '+' " +
            "                                             ELSE NAME END AS AGE_CODE_NAME, " +
            "                                           ENABLED " +
            "                                    FROM AGE_CODE) DBE ON KTALDB.AGE_CODE_ID = DBE.ID " +
            "                         LEFT JOIN (SELECT ID " +
            "                                    FROM AGE_CODE " +
            "                                    WHERE TO_NUMBER(REGEXP_REPLACE(NAME, '^\\D+$', '0')) = " +
            "                                          (SELECT MAX(TO_NUMBER(REGEXP_REPLACE(NAME, '^\\D+$', '0'))) " +
            "                                           FROM AGE_CODE)) MAX_AGE_CODE ON DBE.ID = MAX_AGE_CODE.ID " +
            "                         LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON KTALPT.PRODUCT_TYPE_ID = PTE.ID AND PTE.ENABLED = 1 " +
            "                         LEFT JOIN PRODUCT_GROUP_ENTITY PGE on PTE.PRODUCT_GROUP_ENTITY_ID = PGE.ID AND PGE.ENABLED = 1 " +
            "                  WHERE KTAL.ENABLED = 1 " +
            "                  GROUP BY PGE.NAME, DBE.AGE_CODE_NAME) TARGET " +
            "         ON (PRIMARY_ACHIEVEMENT.PRODUCT = TARGET.PRODUCT AND PRIMARY_ACHIEVEMENT.AGE_CODE = TARGET.AGE_CODE) OR " +
            "            PRIMARY_ACHIEVEMENT.AGE_CODE = TARGET.AGE_CODE " +
            "       LEFT JOIN (SELECT PGE.NAME                         AS PRODUCT, " +
            "                         DBE.NAME                  AS AGE_CODE, " +
            "                         MAX(KTWAL.MIN_DUE_TARGET) / 100 AS OVERDUE_WEIGHT, " +
            "                         MAX(KTWAL.REGULAR_TARGET) / 100  AS REGULAR_WEIGHT, " +
            "                         MAX(KTWAL.FLOW_TARGET) / 100     AS FLOW_WEIGHT, " +
            "                         MAX(KTWAL.BACK_TARGET) / 100     AS BACK_WEIGHT, " +
            "                         MAX(KTWAL.SAVE_TARGET) / 100     AS SAVE_WEIGHT, " +
            "                         MAX(KTWAL.PARREL_TARGET) / 100   AS PARREL_WEIGHT, " +
            "                         MAX(KTWAL.NPLREL_TARGET) / 100   AS NPLREL_WEIGHT " +
            "                  FROM KPI_TARGET_WEIGHT_ACCOUNT_CARD KTWAL " +
            "                         LEFT JOIN KPI_TARGET_WEIGHT_ACCOUNT_CARD_AGE_CODE KTWALDB " +
            "                           ON KTWAL.ID = KTWALDB.CARDKPITARGET_WEIGHT_BY_ACCOUNT_ENTITY_ID " +
            "                         LEFT JOIN KPI_TARGET_WEIGHT_ACCOUNT_CARD_PRODUCT_TYPE KTWALPT " +
            "                           ON KTWAL.ID = KTWALPT.CARDKPITARGET_WEIGHT_BY_ACCOUNT_ENTITY_ID " +
            "                         LEFT JOIN (SELECT ID, " +
            "                                           CASE " +
            "                                             WHEN TO_NUMBER(REGEXP_REPLACE(NAME , '^\\D+$', '0')) = " +
            "                                                  (SELECT MAX(TO_NUMBER(REGEXP_REPLACE(NAME, '^\\D+$', '0'))) " +
            "                                                   FROM AGE_CODE) " +
            "                                                     THEN NAME || '+' " +
            "                                             ELSE NAME END AS NAME , " +
            "                                           ENABLED " +
            "                                    FROM AGE_CODE) DBE ON KTWALDB.AGE_CODE_ID = DBE.ID " +
            "                         LEFT JOIN (SELECT ID " +
            "                                    FROM AGE_CODE " +
            "                                    WHERE TO_NUMBER(REGEXP_REPLACE(NAME, '^\\D+$', '0')) = " +
            "                                          (SELECT MAX(TO_NUMBER(REGEXP_REPLACE(NAME, '^\\D+$', '0'))) " +
            "                                           FROM AGE_CODE)) MAX_AGE_CODE ON DBE.ID = MAX_AGE_CODE.ID " +
            "                         LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON KTWALPT.PRODUCT_TYPE_ID = PTE.ID AND PTE.ENABLED = 1 " +
            "                         LEFT JOIN PRODUCT_GROUP_ENTITY PGE on PTE.PRODUCT_GROUP_ENTITY_ID = PGE.ID AND PGE.ENABLED = 1 " +
            "                  WHERE KTWAL.ENABLED = 1 " +
            "                  GROUP BY PGE.NAME, DBE.NAME) WEIGHT " +
            "         ON (PRIMARY_ACHIEVEMENT.PRODUCT = WEIGHT.PRODUCT AND PRIMARY_ACHIEVEMENT.AGE_CODE = WEIGHT.AGE_CODE) OR " +
            "            PRIMARY_ACHIEVEMENT.AGE_CODE = WEIGHT.AGE_CODE " +
            "ORDER BY PRODUCT, AGE_CODE", nativeQuery = true)
    List<Tuple> getKpiAccountWiseSummary(@Param("pin") List<String> dealerPins, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
    @Query(value = "" +
            "SELECT PRIMARY_ACHIEVEMENT.DEALER_PIN                              AS DEALER_PIN, " +
            "       PRIMARY_ACHIEVEMENT.PRODUCT                                 AS PRODUCT, " +
            "       PRIMARY_ACHIEVEMENT.BUCKET                                  AS AGE_CODE, " +
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
            "                     WHEN CEIL(TO_NUMBER(LADI.AGE_CODE)) >= " +
            "                          (SELECT MAX(CEIL(TO_NUMBER(REGEXP_REPLACE(AGE_CODE.NAME, '^\\D+$', '0')))) FROM AGE_CODE) " +
            "                             THEN " +
            "                       (SELECT MAX(CEIL(TO_NUMBER(REGEXP_REPLACE(AGE_CODE.NAME, '^\\D+$', '0')))) FROM AGE_CODE) || " +
            "                       '+' " +
            "                     ELSE CEIL(TO_NUMBER(LADI.AGE_CODE)) || '' END AS BUCKET, " +
            "                   CASE " +
            "                     WHEN TO_NUMBER(LADI.CURRENT_AGE_CODE) = 0 THEN 'REGULAR' " +
            "                     WHEN TO_NUMBER(LADI.CURRENT_AGE_CODE) > TO_NUMBER(REGEXP_REPLACE(LADI.AGE_CODE, '^\\D+$', '0')) " +
            "                             THEN 'FLOW' " +
            "                     WHEN TO_NUMBER(LADI.CURRENT_AGE_CODE) < TO_NUMBER(REGEXP_REPLACE(LADI.AGE_CODE, '^\\D+$', '0')) " +
            "                             THEN 'BACK' " +
            "                     WHEN TO_NUMBER(LADI.CURRENT_AGE_CODE) = TO_NUMBER(REGEXP_REPLACE(LADI.AGE_CODE, '^\\D+$', '0')) " +
            "                             THEN 'SAVE' " +
            "                       END                                           AS AGE_CODE_STATUS, " +
            "                   COALESCE(LADI.OUTSTANDING_AMOUNT, '0')                  AS OUTSTANDING, " +
            "                   COALESCE(LADI.OPENING_OVER_DUE, 0)                AS OPENING_OVERDUE, " +
            "                   COALESCE(LADI.CURRENT_OVER_DUE, 0)                 AS CURRENT_OVERDUE " +
            "            FROM CARD_ACCOUNT_DISTRIBUTION_INFO LADI " +
            "                   JOIN PRODUCT_TYPE_ENTITY PTE on PTE.CODE = LADI.SCHEME_CODE AND PTE.ENABLED = 1 " +
            "                   JOIN PRODUCT_GROUP_ENTITY PGE ON PTE.PRODUCT_GROUP_ENTITY_ID = PGE.ID AND PGE.ENABLED = 1 " +
            "                   JOIN (SELECT ID, " +
            "                                CASE " +
            "                                  WHEN TO_NUMBER(REGEXP_REPLACE(NAME, '^\\D+$', '0')) = " +
            "                                       (SELECT MAX(TO_NUMBER(REGEXP_REPLACE(NAME, '^\\D+$', '0'))) " +
            "                                        FROM AGE_CODE) " +
            "                                          THEN NAME || '+' " +
            "                                  ELSE NAME END AS AGE_CODE_NAME , " +
            "                                ENABLED " +
            "                         FROM AGE_CODE) DBE on DBE.AGE_CODE_NAME = LADI.AGE_CODE AND DBE.ENABLED = 1 " +
            "                   LEFT JOIN (SELECT ID " +
            "                              FROM AGE_CODE " +
            "                              WHERE TO_NUMBER(REGEXP_REPLACE(NAME, '^\\D+$', '0')) = " +
            "                                    (SELECT MAX(TO_NUMBER(REGEXP_REPLACE(NAME, '^\\D+$', '0'))) " +
            "                                     FROM AGE_CODE)) MAX_BUCKET ON DBE.ID = MAX_BUCKET.ID " +
            "            WHERE LADI.DEALER_PIN IN (:pin) " +
            "              AND LADI.LATEST = '1' " +
            "              AND LADI.CREATED_DATE BETWEEN :startDate AND :endDate) " +
            "          PIVOT ( " +
            "            SUM(OUTSTANDING) FOR AGE_CODE_STATUS IN ('REGULAR' REGULAR, 'FLOW' FLOW, 'BACK' BACK, 'SAVE' SAVE) " +
            "          ) " +
            "      ORDER BY PRODUCT, BUCKET) PRIMARY_ACHIEVEMENT " +
            "       LEFT JOIN (SELECT PGE.NAME                       AS PRODUCT, " +
            "                         DBE.AGE_CODE_NAME                AS BUCKET, " +
            "                         MAX(KTAL.MIN_DUE_TARGET) / 100 AS OVERDUE_TARGET, " +
            "                         MAX(KTAL.REGULAR_TARGET) / 100 AS REGULAR_TARGET, " +
            "                         MAX(KTAL.FLOW_TARGET) / 100    AS FLOW_TARGET, " +
            "                         MAX(KTAL.BACK_TARGET) / 100    AS BACK_TARGET, " +
            "                         MAX(KTAL.SAVE_TARGET) / 100    AS SAVE_TARGET, " +
            "                         MAX(KTAL.PARREL_TARGET) / 100  AS PARREL_TARGET, " +
            "                         MAX(KTAL.NPLREL_TARGET) / 100  AS NPLREL_TARGET " +
            "                  FROM KPI_TARGET_BY_AMOUNT_CARD KTAL " +
            "                         LEFT JOIN KPI_TARGET_BY_AMOUNT_CARD_AGE_CODE KTALDB " +
            "                           ON KTAL.ID = KTALDB.CARDKPITARGET_BY_AMOUNT_ENTITY_ID " +
            "                         LEFT JOIN KPI_TARGET_BY_AMOUNT_CARD_PRODUCT_TYPE KTALPT " +
            "                           ON KTAL.ID = KTALPT.CARDKPITARGET_BY_AMOUNT_ENTITY_ID " +
            "                         LEFT JOIN (SELECT ID, " +
            "                                           CASE " +
            "                                             WHEN TO_NUMBER(REGEXP_REPLACE(NAME, '^\\D+$', '0')) = " +
            "                                                  (SELECT MAX(TO_NUMBER(REGEXP_REPLACE(NAME, '^\\D+$', '0'))) " +
            "                                                   FROM AGE_CODE) " +
            "                                                     THEN NAME || '+' " +
            "                                             ELSE NAME END AS AGE_CODE_NAME , " +
            "                                           ENABLED " +
            "                                    FROM AGE_CODE) DBE ON KTALDB.AGE_CODE_ID = DBE.ID " +
            "                         LEFT JOIN (SELECT ID " +
            "                                    FROM AGE_CODE " +
            "                                    WHERE TO_NUMBER(REGEXP_REPLACE(NAME, '^\\D+$', '0')) = " +
            "                                          (SELECT MAX(TO_NUMBER(REGEXP_REPLACE(NAME, '^\\D+$', '0'))) " +
            "                                           FROM AGE_CODE)) MAX_BUCKET ON DBE.ID = MAX_BUCKET.ID " +
            "                         LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON KTALPT.PRODUCT_TYPE_ID = PTE.ID AND PTE.ENABLED = 1 " +
            "                         LEFT JOIN PRODUCT_GROUP_ENTITY PGE on PTE.PRODUCT_GROUP_ENTITY_ID = PGE.ID AND PGE.ENABLED = 1 " +
            "                  WHERE KTAL.ENABLED = 1 " +
            "                  GROUP BY PGE.NAME, DBE.AGE_CODE_NAME) TARGET " +
            "         ON (PRIMARY_ACHIEVEMENT.PRODUCT = TARGET.PRODUCT AND PRIMARY_ACHIEVEMENT.BUCKET = TARGET.BUCKET) OR " +
            "            (TARGET.PRODUCT IS NULL AND PRIMARY_ACHIEVEMENT.BUCKET = TARGET.BUCKET) " +
            "       LEFT JOIN (SELECT PGE.NAME                         AS PRODUCT, " +
            "                         DBE.AGE_CODE_NAME                  AS BUCKET, " +
            "                         MAX(KTWAL.MIN_DUE_TARGET) / 100 AS OVERDUE_WEIGHT, " +
            "                         MAX(KTWAL.REGULAR_TARGET) / 100  AS REGULAR_WEIGHT, " +
            "                         MAX(KTWAL.FLOW_TARGET) / 100     AS FLOW_WEIGHT, " +
            "                         MAX(KTWAL.BACK_TARGET) / 100     AS BACK_WEIGHT, " +
            "                         MAX(KTWAL.SAVE_TARGET) / 100     AS SAVE_WEIGHT, " +
            "                         MAX(KTWAL.PARREL_TARGET) / 100   AS PARREL_WEIGHT, " +
            "                         MAX(KTWAL.NPLREL_TARGET) / 100   AS NPLREL_WEIGHT " +
            "                  FROM KPI_TARGET_WEIGHT_AMOUNT_CARD KTWAL " +
            "                         LEFT JOIN KPI_TARGET_WEIGHT_AMOUNT_CARD_AGE_CODE KTWALDB " +
            "                           ON KTWAL.ID = KTWALDB.CARDKPITARGET_WEIGHT_BY_AMOUNT_ENTITY_ID " +
            "                         LEFT JOIN KPI_TARGET_WEIGHT_AMOUNT_CARD_PRODUCT_TYPE KTWALPT " +
            "                           ON KTWAL.ID = KTWALPT.CARDKPITARGET_WEIGHT_BY_AMOUNT_ENTITY_ID " +
            "                         LEFT JOIN (SELECT ID, " +
            "                                           CASE " +
            "                                             WHEN TO_NUMBER(REGEXP_REPLACE(NAME, '^\\D+$', '0')) = " +
            "                                                  (SELECT MAX(TO_NUMBER(REGEXP_REPLACE(NAME, '^\\D+$', '0'))) " +
            "                                                   FROM AGE_CODE) " +
            "                                                     THEN NAME || '+' " +
            "                                             ELSE NAME END AS AGE_CODE_NAME, " +
            "                                           ENABLED " +
            "                                    FROM AGE_CODE) DBE ON KTWALDB.AGE_CODE_ID = DBE.ID " +
            "                         LEFT JOIN (SELECT ID " +
            "                                    FROM AGE_CODE " +
            "                                    WHERE TO_NUMBER(REGEXP_REPLACE(NAME, '^\\D+$', '0')) = " +
            "                                          (SELECT MAX(TO_NUMBER(REGEXP_REPLACE(NAME, '^\\D+$', '0'))) " +
            "                                           FROM AGE_CODE)) MAX_BUCKET ON DBE.ID = MAX_BUCKET.ID " +
            "                         LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON KTWALPT.PRODUCT_TYPE_ID = PTE.ID AND PTE.ENABLED = 1 " +
            "                         LEFT JOIN PRODUCT_GROUP_ENTITY PGE on PTE.PRODUCT_GROUP_ENTITY_ID = PGE.ID AND PGE.ENABLED = 1 " +
            "                  WHERE KTWAL.ENABLED = 1 " +
            "                  GROUP BY PGE.NAME, DBE.AGE_CODE_NAME) WEIGHT " +
            "         ON (PRIMARY_ACHIEVEMENT.PRODUCT = WEIGHT.PRODUCT AND PRIMARY_ACHIEVEMENT.BUCKET = WEIGHT.BUCKET) OR " +
            "            (WEIGHT.PRODUCT IS NULL AND PRIMARY_ACHIEVEMENT.BUCKET = WEIGHT.BUCKET) " +
            "ORDER BY PRODUCT, BUCKET", nativeQuery = true)
    List<Tuple> getKpiAmountWiseSummary(@Param("pin") List<String> dealerPins, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
