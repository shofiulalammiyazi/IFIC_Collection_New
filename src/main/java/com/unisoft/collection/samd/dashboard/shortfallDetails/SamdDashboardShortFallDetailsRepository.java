package com.unisoft.collection.samd.dashboard.shortfallDetails;

import com.unisoft.customerloanprofile.loanpayment.LoanPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;

@Repository
public interface SamdDashboardShortFallDetailsRepository extends JpaRepository<LoanPayment, Long> {

    @Query(value = "" +
            "SELECT LADI.DEALER_PIN, " +
            "       COUNT(CASE WHEN LADI.CURRENT_OVERDUE > 0 THEN 1 END)                       AS minDueShortFall, " +
            "       COUNT( " +
            "         CASE WHEN LADI.EMI_AMOUNT > LPCM.CURRENT_MONTH_TOTAL_PAYMENT THEN 1 END) AS saveAmountShortFall, " +
            "       COUNT(CASE " +
            "               WHEN (LADI.EMI_AMOUNT + LADI.OPENING_OVER_DUE - (LADI.EMI_AMOUNT * LADI.BACK_BUCKET)) > " +
            "                    LPCM.CURRENT_MONTH_TOTAL_PAYMENT " +
            "                       THEN 1 END)                                                AS backAmountShortFall, " +
            "       SUM(CASE " +
            "             WHEN LADI.EMI_AMOUNT > LPCM.CURRENT_MONTH_TOTAL_PAYMENT " +
            "                     THEN LADI.EMI_AMOUNT - LPCM.CURRENT_MONTH_TOTAL_PAYMENT " +
            "             ELSE 0 END) / " +
            "       (LAST_DAY(SYSDATE) - SYSDATE)                                              AS perDayShortFall " +
            "FROM (SELECT DEALER_PIN, " +
            "             CURRENT_OVERDUE, " +
            "             OPENING_OVER_DUE, " +
            "             EMI_AMOUNT, " +
            "             CEIL(DPD_BUCKET) - 1 AS BACK_BUCKET, " +
            "             LOAN_ACCOUNT_BASIC_INFO_ID " +
            "      FROM LMS_COLLECTION_SAM_LOAN_ACCOUNT_DISTRIBUTIONS LADI_IN " +
            "      WHERE LADI_IN.DEALER_PIN = :pin " +
            "        AND LADI_IN.LATEST = '1' " +
            "        AND LADI_IN.CREATED_DATE >= TRUNC(SYSDATE, 'MM')) LADI " +
            "       JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON LABI.ID = LADI.LOAN_ACCOUNT_BASIC_INFO_ID " +
            "       LEFT JOIN (SELECT LP.ACCOUNT_NO     AS ACCOUNT_NO, " +
            "                         SUM(CASE " +
            "                               WHEN LP.PAYMENT_DATE >= TRUNC(SYSDATE, 'MM') " +
            "                                       THEN LP.PAYMENT " +
            "                               ELSE 0 END) AS CURRENT_MONTH_TOTAL_PAYMENT " +
            "                  FROM LOAN_PAYMENT LP " +
            "                         LEFT JOIN (SELECT LP_IN.ACCOUNT_NO, MAX(LP_IN.PAYMENT_DATE) PAYMENT_DATE " +
            "                                    FROM LOAN_PAYMENT LP_IN " +
            "                                    GROUP BY LP_IN.ACCOUNT_NO) LPMD ON LP.ACCOUNT_NO = LPMD.ACCOUNT_NO " +
            "                  GROUP BY LP.ACCOUNT_NO) LPCM ON LABI.ACCOUNT_NO = LPCM.ACCOUNT_NO " +
            "GROUP BY LADI.DEALER_PIN", nativeQuery = true)
    Tuple getShortFallDetails(@Param("pin") String dealerPin);

}
