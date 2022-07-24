package com.unisoft.collection.samd.dataEntry.loanAccountDistribution;

import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

@Repository
public interface SamLoanAccountDistributionRepository extends JpaRepository<SamLoanAccountDistribution, Long> {

    @Query(value = "FROM SamLoanAccountDistribution WHERE latest = '1'")
    List<SamLoanAccountDistribution> findAllLatest();

    @Query("FROM SamLoanAccountDistribution WHERE id in ?1")
    List<SamLoanAccountDistribution> findAllByIds(List<Long> ids);

    @Query(value = "SELECT * FROM LMS_COLLECTION_SAM_LOAN_ACCOUNT_DISTRIBUTIONS " +
            "WHERE LOAN_ACCOUNT_BASIC_INFO_ID IN (SELECT id FROM LOAN_ACCOUNT_BASIC_INFO WHERE account_no IN ?1) " +
            "AND latest = '1'", nativeQuery = true)
    List<SamLoanAccountDistribution> findAllByAccountNo(List<String> ids);

    SamLoanAccountDistribution findFirstByLoanAccountBasicInfoOrderByCreatedDateDesc(LoanAccountBasicInfo loanAccountBasicInfo);

    @Query(value = "SELECT LADI.DEALER_PIN AS dealerId, (DUSR.FIRST_NAME || ' ' || DUSR.LAST_NAME) AS dealerName, " +
            "LADI.CREATED_BY AS createdById, (CUSR.FIRST_NAME || ' ' || CUSR.LAST_NAME) AS createByName, LADI.CREATED_DATE AS createdDate " +

            "FROM LMS_COLLECTION_SAM_LOAN_ACCOUNT_DISTRIBUTIONS LADI " +
            "LEFT JOIN LOS_TB_M_USERS DUSR ON DUSR.USERNAME = LADI.DEALER_PIN " +
            "LEFT JOIN LOS_TB_M_USERS CUSR ON CUSR.USERNAME = LADI.CREATED_BY " +

            "WHERE LADI.LOAN_ACCOUNT_BASIC_INFO_ID = (SELECT ID FROM LOAN_ACCOUNT_BASIC_INFO WHERE ACCOUNT_NO = ?1) " +
            "AND LADI.CREATED_DATE BETWEEN ?2 AND ?3 " +
            "ORDER BY LADI.CREATED_DATE DESC", nativeQuery = true)
    List<Tuple> getLoanAccountDealerAllocationHistory(String accountNo, Date startDate, Date endDate);


    @Query(value = "SELECT " +
            "LABI.ACCOUNT_NO                                                                 AS accountNo, " +
            "COALESCE(DNL.CONTACT_COUNT, 0)                                                  AS contact, " +
            "COALESCE(DNL.NO_CONTACT_COUNT, 0)                                               AS noContact, " +
            "COALESCE(LPTP.BROKEN_COUNT, 0)                                                  AS brokenPtp, " +
            "COALESCE(LPTP.KEPT_COUNT, 0)                                                    AS keptPtp, " +
            "COALESCE(LPTP.CURED_COUNT, 0)                                                   AS curedPtp, " +
            "COALESCE(LADI.EMI_AMOUNT, 0)                                                    AS saveAmount, " +
            "COALESCE(LADI.EMI_AMOUNT, 0) + 1                                                AS backAmount, " +
            "COALESCE(LADI.EMI_AMOUNT, 0) - 1                                                AS flowAmount, " +
            "DECODE(NULL, FU.FOLLOW_UP_DATE, '-', FU.FOLLOW_UP_DATE)                         as followUpDate, " +
            "COALESCE(LPCM.CURRENT_MONTH_PAYMENT, 0)                                         AS currentMonthPayment, " +
            "COALESCE(LPLD.PAYMENT, 0)                                                       AS lastPayment, " +
            "DECODE(NULL, LPLD.PAYMENT_DATE, '-', TO_CHAR(LPLD.PAYMENT_DATE, 'DD-MON-YYYY')) AS lastPaymentDate, " +
            "COALESCE(LADI.OPENING_OVER_DUE, 0)                                              AS overdueAmount, " +
            "DECODE(NULL, LAI.BRANCH_NAME, '-', LAI.BRANCH_NAME)                             AS branchName, " +
            "DECODE(NULL, LADI.DPD_BUCKET, '-', LADI.DPD_BUCKET)                             as dpdBucket, " +
            "DECODE(NULL, LABI.LINK_ACCOUNT_NUMBER, '-', LABI.LINK_ACCOUNT_NUMBER)           AS linkAccountNo, " +
            "0                                                                               AS linkAccountBalance, " +
            "DECODE(NULL, RC.RISK_CATEGORY, '-', RC.RISK_CATEGORY)                           AS riskCategory " +
            "FROM LMS_COLLECTION_SAM_LOAN_ACCOUNT_DISTRIBUTIONS LADI " +
            "JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON LABI.ID = LADI.LOAN_ACCOUNT_BASIC_INFO_ID " +
            "LEFT JOIN LOAN_ACCOUNT_INFO LAI ON LABI.ID = LAI.LOAN_ACCOUNT_BASIC_INFO_ID " +
            "LEFT JOIN (SELECT MAX(PAYMENT_DATE) AS PAYMENT_DATE, ACCOUNT_NO FROM LOAN_PAYMENT GROUP BY ACCOUNT_NO) LPDLATEST " +
            "  ON LPDLATEST.ACCOUNT_NO = LABI.ACCOUNT_NO " +
            "LEFT JOIN LOAN_PAYMENT LPD ON LPD.ACCOUNT_NO = LABI.ACCOUNT_NO AND LPD.PAYMENT_DATE = LPDLATEST.PAYMENT_DATE " +
            "LEFT JOIN (SELECT CUSTOMER_ID, " +
            "                  COUNT(CASE WHEN LOWER(LOAN_SHORT_NOTE) = 'contact' THEN 1 END)    CONTACT_COUNT, " +
            "                  COUNT(CASE WHEN LOWER(LOAN_SHORT_NOTE) = 'no contact' THEN 1 END) NO_CONTACT_COUNT " +
            "           FROM DAIRY_NOTES_LOAN " +
            "           WHERE CREATED_DATE >= TRUNC(SYSDATE, 'MM') " +
            "           GROUP BY CUSTOMER_ID) DNL ON DNL.CUSTOMER_ID = LABI.CUSTOMER_ID " +
            "LEFT JOIN (SELECT CUSTOMER_ID, " +
            "                  COUNT(CASE WHEN LOWER(LOAN_PTP_STATUS) = 'kept' THEN 1 END)   KEPT_COUNT, " +
            "                  COUNT(CASE WHEN LOWER(LOAN_PTP_STATUS) = 'cured' THEN 1 END)  CURED_COUNT, " +
            "                  COUNT(CASE WHEN LOWER(LOAN_PTP_STATUS) = 'broken' THEN 1 END) BROKEN_COUNT " +
            "           FROM LOAN_PTP " +
            "           WHERE CREATED_DATE >= TRUNC(SYSDATE, 'MM') " +
            "           GROUP BY CUSTOMER_ID) LPTP ON LPTP.CUSTOMER_ID = LABI.CUSTOMER_ID " +
            "LEFT JOIN (SELECT ACCOUNT_NO, SUM(PAYMENT) CURRENT_MONTH_PAYMENT " +
            "           FROM LOAN_PAYMENT " +
            "           WHERE PAYMENT_DATE >= TRUNC(SYSDATE, 'MM') " +
            "           GROUP BY ACCOUNT_NO) LPCM ON LABI.ACCOUNT_NO = LPCM.ACCOUNT_NO " +
            "LEFT JOIN (SELECT ACCOUNT_NO, PAYMENT_DATE, PAYMENT " +
            "           FROM LOAN_PAYMENT LP " +
            "           WHERE PAYMENT_DATE = " +
            "                 (SELECT MAX(PAYMENT_DATE) FROM LOAN_PAYMENT LPIN WHERE LPIN.ACCOUNT_NO = LP.ACCOUNT_NO)) LPLD " +
            "  ON LPLD.ACCOUNT_NO = LABI.ACCOUNT_NO " +
            "LEFT JOIN (SELECT LADI.LOAN_ACCOUNT_BASIC_INFO_ID AS LOAN_ID, RC.RISK_CATEGORY_NAME AS RISK_CATEGORY " +
            "           FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI " +
            "                  LEFT JOIN DPDBUCKET_ENTITY DPD ON DPD.BUCKET_NAME = LADI.DPD_BUCKET " +
            "                  LEFT JOIN RISK_CATEGORY_DPD_BUCKET_ENTITIES RCDPD ON RCDPD.DPD_BUCKET_ENTITIES_ID = DPD.ID " +
            "                  LEFT JOIN RISK_CATEGORY RC ON RCDPD.RISK_CATEGORY_ID = RC.ID " +
            "           WHERE LADI.DEALER_PIN = :pin " +
            "             AND LADI.LATEST = '1' " +
            "             AND LADI.CREATED_DATE >= TRUNC(SYSDATE, 'MM')) RC ON RC.LOAN_ID = LABI.ID " +
            "LEFT JOIN (SELECT CUSTOMER_ID, TO_CHAR(MIN(FOLLOW_UP_DATE), 'DD-MON-YYYY') FOLLOW_UP_DATE " +
            "           FROM FOLLOW_UP_ENTITY " +
            "           WHERE TRUNC(FOLLOW_UP_DATE, 'DD') >= TRUNC(SYSDATE, 'DD') " +
            "           GROUP BY CUSTOMER_ID) FU ON FU.CUSTOMER_ID = LABI.CUSTOMER_ID " +
            "WHERE LADI.DEALER_PIN = :pin " +
            "AND LADI.LATEST = '1' " +
            "AND LADI.CREATED_DATE >= TRUNC(SYSDATE, 'MM')", nativeQuery = true)
    List<Tuple> getLoanAccountDistributionSummary(@Param("pin") String dealerPin);
}
