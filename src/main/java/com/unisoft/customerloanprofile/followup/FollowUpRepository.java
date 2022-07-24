package com.unisoft.customerloanprofile.followup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

@Repository
public interface FollowUpRepository extends JpaRepository<FollowUpEntity, Long> {

    List<FollowUpEntity> findByCustomerBasicInfoIdAndFollowUpDateGreaterThanEqualOrderByIdDesc(Long customerId, Date startDate);

    /**
     * Lists down account wise follow ups that should be done today.
     * The list is used to generate notification for dealers.
     *
     *
     * @param dealerPin
     * @return List of Tuple containing today's follow ups
     */
    @Query(value = "" +
            "SELECT LABI.ACCOUNT_NO   ACCOUNT_NO, " +
            "       TO_CHAR(TO_DATE(FOLLOW_UP_TIME, 'HH24:mi'), 'HH12:mi AM') TIME, " +
            "       FUEFUR.REASONS " +
            "FROM FOLLOW_UP_ENTITY F " +
            "       JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON LABI.CUSTOMER_ID = F.CUSTOMER_ID " +
            "       JOIN LOAN_ACCOUNT_DISTRIBUTION_INFO LADI ON LABI.ID = LADI.LOAN_ACCOUNT_BASIC_INFO_ID " +
            "                                                     AND LADI.LATEST = '1' " +
            "                                                     AND LADI.CREATED_DATE >= TRUNC(SYSDATE, 'MM') AND " +
            "                                                   LADI.DEALER_PIN = :pin " +
            "       LEFT JOIN (SELECT FR.FOLLOW_UP_ENTITY_ID                                                            ID, " +
            "                         LISTAGG(FR.FOLLOW_UP_REASON, ', ') WITHIN GROUP (ORDER BY FR.FOLLOW_UP_ENTITY_ID) REASONS " +
            "                  FROM FOLLOW_UP_ENTITY_FOLLOW_UP_REASON FR " +
            "                  GROUP BY FR.FOLLOW_UP_ENTITY_ID) FUEFUR on F.ID = FUEFUR.ID " +
            "WHERE TRUNC(F.FOLLOW_UP_DATE, 'DD') = TRUNC(SYSDATE, 'DD')", nativeQuery = true)
    List<Tuple> findByTodaysFollowupsByDealer(@Param("pin") String dealerPin);


    /**
     * Generates dealer wise summary to present number of accounts and
     * amount of outstanding in those accounts respective to every
     * follow up reason
     *
     *
     * @param dealerPins
     * @return List of Tuple containing today's follow ups
     */
   /* @Query(value = "" +
            "SELECT F.PIN                        DEALER_PIN, " +
            "       MAX(F.USERNAME)              DEALER_NAME, " +
            "       SUM(F.CALL_COUNT)            CALL_COUNT, " +
            "       COALESCE(SUM(F.CALL_SUM), 0) CALL_SUM, " +
            "       SUM(F.EMAIL_COUNT)            EMAIL_COUNT, " +
            "       COALESCE(SUM(F.EMAIL_SUM), 0) EMAIL_SUM, " +
            "       SUM(F.SMS_COUNT)            SMS_COUNT, " +
            "       COALESCE(SUM(F.SMS_SUM), 0) SMS_SUM, " +
            "       SUM(F.VISIT_COUNT)            VISIT_COUNT, " +
            "       COALESCE(SUM(F.VISIT_SUM), 0) VISIT_SUM, " +
            "       SUM(F.CONTACTED_COUNT)            CONTACTED_COUNT, " +
            "       COALESCE(SUM(F.CONTACTED_SUM), 0) CONTACTED_SUM, " +
            "       SUM(F.NOT_CONTACTED_COUNT)            NOT_CONTACTED_COUNT, " +
            "       COALESCE(SUM(F.NOT_CONTACTED_SUM), 0) NOT_CONTACTED_SUM " +
            "FROM (SELECT FUE.CUSTOMER_ID, LADI.OUT_STANDING, LOWER(FR.FOLLOW_UP_REASON) REASON, FUE.PIN, FUE.USERNAME " +
            "      FROM FOLLOW_UP_ENTITY_FOLLOW_UP_REASON FR " +
            "             JOIN FOLLOW_UP_ENTITY FUE ON FR.FOLLOW_UP_ENTITY_ID = FUE.ID " +
            "             JOIN LOAN_ACCOUNT_DISTRIBUTION_INFO LADI " +
            "               ON FUE.PIN = LADI.DEALER_PIN AND LADI.LATEST = '1' AND LADI.CREATED_DATE >= TRUNC(SYSDATE, 'MM') " +
            "      WHERE TRUNC(FUE.FOLLOW_UP_DATE, 'DD') >= TRUNC(SYSDATE, 'MM') " +
            "        AND FUE.PIN IN (:dealerPins)) " +
            "         PIVOT ( " +
            "           COUNT(DISTINCT CUSTOMER_ID) COUNT, SUM(OUT_STANDING) SUM " +
            "         FOR REASON IN ('call' CALL, 'email' EMAIL, 'sms' SMS, 'visit' VISIT, 'contacted' CONTACTED, 'not contacted' NOT_CONTACTED) " +
            "         ) F " +
            "GROUP BY F.PIN", nativeQuery = true)
    List<Tuple> getDealerWiseFollowUpSummary(@Param("dealerPins") List<String> dealerPins);*/

    @Query(value = "" +
            "SELECT F.PIN                        DEALER_PIN, " +
            "       MAX(F.USERNAME)              DEALER_NAME, " +
            "       SUM(F.CALL_COUNT)            CALL_COUNT, " +
            "       COALESCE(SUM(F.CALL_SUM), 0) CALL_SUM, " +
            "       SUM(F.EMAIL_COUNT)            EMAIL_COUNT, " +
            "       COALESCE(SUM(F.EMAIL_SUM), 0) EMAIL_SUM, " +
            "       SUM(F.SMS_COUNT)            SMS_COUNT, " +
            "       COALESCE(SUM(F.SMS_SUM), 0) SMS_SUM, " +
            "       SUM(F.VISIT_COUNT)            VISIT_COUNT, " +
            "       COALESCE(SUM(F.VISIT_SUM), 0) VISIT_SUM, " +
            "       SUM(F.CONTACTED_COUNT)            CONTACTED_COUNT, " +
            "       COALESCE(SUM(F.CONTACTED_SUM), 0) CONTACTED_SUM, " +
            "       SUM(F.NOT_CONTACTED_COUNT)            NOT_CONTACTED_COUNT, " +
            "       COALESCE(SUM(F.NOT_CONTACTED_SUM), 0) NOT_CONTACTED_SUM " +
            "FROM (SELECT FUE.CUSTOMER_ID, LADI.OUT_STANDING, LOWER(FR.FOLLOW_UP_REASON) REASON, FUE.PIN, FUE.USERNAME " +
            "      FROM FOLLOW_UP_ENTITY_FOLLOW_UP_REASON FR " +
            "             JOIN FOLLOW_UP_ENTITY FUE ON FR.FOLLOW_UP_ENTITY_ID = FUE.ID " +
            "             JOIN LOAN_ACCOUNT_DISTRIBUTION_INFO LADI " +
            "               ON FUE.PIN = LADI.DEALER_PIN AND LADI.LATEST = '1' AND LADI.CREATED_DATE >= TRUNC(SYSDATE, 'MM') " +
            "      WHERE TRUNC(FUE.FOLLOW_UP_DATE, 'DD') >= TRUNC(SYSDATE, 'MM') " +
            "        AND FUE.PIN IN (:dealerPins)) " +
            "         PIVOT ( " +
            "           COUNT(DISTINCT CUSTOMER_ID) COUNT, SUM(OUT_STANDING) SUM " +
            "         FOR REASON IN ('call' CALL, 'email' EMAIL, 'sms' SMS, 'visit' VISIT, 'contacted' CONTACTED, 'not contacted' NOT_CONTACTED) " +
            "         ) F " +
            "GROUP BY F.PIN", nativeQuery = true)
    List<Tuple> getDealerWiseFollowUpSummary(@Param("dealerPins") List<String> dealerPins);

    @Query(value = "SELECT F.PIN                        DEALER_PIN,  " +
            "       MAX(F.USERNAME)              DEALER_NAME, " +
            "       SUM(F.CALL_COUNT)            CALL_COUNT, " +
            "       COALESCE(SUM(F.CALL_SUM), 0) CALL_SUM, " +
            "       SUM(F.EMAIL_COUNT)            EMAIL_COUNT, " +
            "       COALESCE(SUM(F.EMAIL_SUM), 0) EMAIL_SUM, " +
            "       SUM(F.SMS_COUNT)            SMS_COUNT, " +
            "       COALESCE(SUM(F.SMS_SUM), 0) SMS_SUM, " +
            "       SUM(F.VISIT_COUNT)            VISIT_COUNT, " +
            "       COALESCE(SUM(F.VISIT_SUM), 0) VISIT_SUM, " +
            "       SUM(F.CONTACTED_COUNT)            CONTACTED_COUNT, " +
            "       COALESCE(SUM(F.CONTACTED_SUM), 0) CONTACTED_SUM, " +
            "       SUM(F.NOT_CONTACTED_COUNT)            NOT_CONTACTED_COUNT, " +
            "       COALESCE(SUM(F.NOT_CONTACTED_SUM), 0) NOT_CONTACTED_SUM " +
            "FROM (SELECT FUE.CUSTOMER_ID, LADI.OUT_STANDING, LOWER(FR.FOLLOW_UP_REASON) REASON, FUE.PIN, FUE.USERNAME " +
            "      FROM CARD_FOLLOW_UP_FOLLOW_UP_REASON FR " +
            "             JOIN CARD_FOLLOW_UP FUE ON FR.CARD_FOLLOW_UP_ID = FUE.ID " +
            "             JOIN CARD_ACCOUNT_DISTRIBUTION_INFO LADI " +
            "               ON FUE.PIN = LADI.DEALER_PIN AND LADI.LATEST = '1' AND LADI.CREATED_DATE >= TRUNC(SYSDATE, 'MM') " +
            "      WHERE TRUNC(FUE.FOLLOW_UP_DATE, 'DD') >= TRUNC(SYSDATE, 'MM') " +
            "        AND FUE.PIN IN (:dealerPins)) " +
            "         PIVOT ( " +
            "           COUNT(DISTINCT CUSTOMER_ID) COUNT, SUM(OUT_STANDING) SUM " +
            "         FOR REASON IN ('call' CALL, 'email' EMAIL, 'sms' SMS, 'visit' VISIT, 'contacted' CONTACTED, 'not contacted' NOT_CONTACTED) " +
            "         ) F " +
            "GROUP BY F.PIN", nativeQuery = true)
    List<Tuple> getCardDealerWiseFollowUpSummary(@Param("dealerPins") List<String> dealerPins);

}
