package com.unisoft.retail.loan.dashboard.esau;

import com.unisoft.collection.settings.esau.loan.ESAULoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

@Repository
public interface LoanPerformanceAndEsauTrendRepository extends JpaRepository<ESAULoanEntity, Long> {

    /**
     * ESAU Rating based on dealer performance
     * <p>
     * Implemented by
     * on August 18, 2021
     *
     * @param dealerPins
     * @return List of Tuples
     */
    @Query(value = "" +
            "SELECT MWA.DEALER_PIN                                                           AS DEALER_PIN, " +

            "       MAX(CASE WHEN MWA.MONTH = TO_CHAR(:beginingMonth, 'Month, YYYY') THEN MWA.ACHIEVEMENT END) AS CURRENT_MONTH_ACHIEVEMENT, " +
            "       MAX(CASE WHEN MWA.MONTH = TO_CHAR(:beginingMonth, 'Month, YYYY') THEN ERL.RATING_NAME END) AS CURRENT_MONTH_RATING, " +

            "       MAX(CASE WHEN MWA.MONTH = TO_CHAR(ADD_MONTHS(:beginingMonth, -1), 'Month, YYYY') THEN MWA.ACHIEVEMENT END) AS ONE_MONTH_AGO_ACHIEVEMENT, " +
            "       MAX(CASE WHEN MWA.MONTH = TO_CHAR(ADD_MONTHS(:beginingMonth, -1), 'Month, YYYY') THEN ERL.RATING_NAME END) AS ONE_MONTH_AGO_RATING, " +

            "       MAX(CASE WHEN MWA.MONTH = TO_CHAR(ADD_MONTHS(:beginingMonth, -2), 'Month, YYYY') THEN MWA.ACHIEVEMENT END) AS TWO_MONTH_AGO_ACHIEVEMENT, " +
            "       MAX(CASE WHEN MWA.MONTH = TO_CHAR(ADD_MONTHS(:beginingMonth, -2), 'Month, YYYY') THEN ERL.RATING_NAME END) AS TWO_MONTH_AGO_RATING, " +

            "       MAX(CASE WHEN MWA.MONTH = TO_CHAR(ADD_MONTHS(:beginingMonth, -3), 'Month, YYYY') THEN MWA.ACHIEVEMENT END) AS THREE_MONTH_AGO_ACHIEVEMENT, " +
            "       MAX(CASE WHEN MWA.MONTH = TO_CHAR(ADD_MONTHS(:beginingMonth, -3), 'Month, YYYY') THEN ERL.RATING_NAME END) AS THREE_MONTH_AGO_RATING, " +

            "       MAX(CASE WHEN MWA.MONTH = TO_CHAR(ADD_MONTHS(:beginingMonth, -4), 'Month, YYYY') THEN MWA.ACHIEVEMENT END) AS FOUR_MONTH_AGO_ACHIEVEMENT, " +
            "       MAX(CASE WHEN MWA.MONTH = TO_CHAR(ADD_MONTHS(:beginingMonth, -4), 'Month, YYYY') THEN ERL.RATING_NAME END) AS FOUR_MONTH_AGO_RATING, " +

            "       MAX(CASE WHEN MWA.MONTH = TO_CHAR(ADD_MONTHS(:beginingMonth, -5), 'Month, YYYY') THEN MWA.ACHIEVEMENT END) AS FIVE_MONTH_AGO_ACHIEVEMENT, " +
            "       MAX(CASE WHEN MWA.MONTH = TO_CHAR(ADD_MONTHS(:beginingMonth, -5), 'Month, YYYY') THEN ERL.RATING_NAME END) AS FIVE_MONTH_AGO_RATING, " +

            "       MAX(CASE WHEN MWA.MONTH = TO_CHAR(ADD_MONTHS(:beginingMonth, -6), 'Month, YYYY') THEN MWA.ACHIEVEMENT END) AS SIX_MONTH_AGO_ACHIEVEMENT, " +
            "       MAX(CASE WHEN MWA.MONTH = TO_CHAR(ADD_MONTHS(:beginingMonth, -6), 'Month, YYYY') THEN ERL.RATING_NAME END) AS SIX_MONTH_AGO_RATING, " +

            "       ROUND(AVG(CASE WHEN MWA.CREATED_DATE BETWEEN ADD_MONTHS(TRUNC(:beginingMonth, 'MM'), -3) AND TRUNC(:beginingMonth, 'MM') THEN MWA.ACHIEVEMENT END), 2) AS LAST_THREE_MONTH_ACHIEVEMENT_AVERAGE, " +
            "       ROUND(AVG(CASE WHEN MWA.CREATED_DATE BETWEEN ADD_MONTHS(TRUNC(:beginingMonth, 'MM'), -6) AND TRUNC(:beginingMonth, 'MM') THEN MWA.ACHIEVEMENT END), 2) AS LAST_SIX_MONTH_ACHIEVEMENT_AVERAGE, " +
            "       ROUND(AVG(CASE WHEN MWA.CREATED_DATE BETWEEN ADD_MONTHS(TRUNC(:beginingMonth, 'MM'), -12) AND TRUNC(:beginingMonth, 'MM') THEN MWA.ACHIEVEMENT END), 2) AS LAST_TWELVE_MONTH_ACHIEVEMENT_AVERAGE " +

            "FROM (SELECT DEALER_PIN, " +
            "             TO_CHAR(CREATED_DATE, 'Month, YYYY') AS MONTH, " +
            "             ROUND(AVG(FINAL_PERFORMANCE), 2) AS ACHIEVEMENT, " +
            "             MAX(CREATED_DATE) AS CREATED_DATE " +
            "      FROM LOAN_KPI_TARGET_VS_ACHIEVEMENT LKTVA " +
            "      WHERE LKTVA.DEALER_PIN IN (:pin) " +
            "        AND CREATED_DATE >= ADD_MONTHS(TRUNC(:beginingMonth, 'MM'), -12) " +
            "      GROUP BY DEALER_PIN, TO_CHAR(CREATED_DATE, 'Month, YYYY')) MWA " +
            "       LEFT JOIN ESAU_RATING_LOAN ERL " +
            "         ON MWA.ACHIEVEMENT >= ERL.FINAL_AVG_LOWER_LIMIT / 100 AND MWA.ACHIEVEMENT <= ERL.FINAL_AVG_UPPER_LIMIT / 100 " +
            "GROUP BY DEALER_PIN " +
            "ORDER BY MWA.DEALER_PIN DESC", nativeQuery = true)
    List<Tuple> getPerformanceAndEsauTrendSummary(@Param("pin") List<String> dealerPins, @Param("beginingMonth") Date beginingMonth);





                         @Query(value = "SELECT" +
                                 " ROUND(FINAL_PERFORMANCE,2)  AS ACHIEVEMENT" +
                                 " FROM LOAN_KPI_TARGET_VS_ACHIEVEMENT" +
                                 " WHERE DEALER_PIN = (:pin)\n" +
                                 " AND CREATED_DATE BETWEEN :startDate AND :endDate ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY",nativeQuery = true)
   Tuple  getPerformanceAndAchivment(@Param("pin") String dealerPin, @Param("startDate") Date startDate, @Param("endDate") Date endDate);




}
