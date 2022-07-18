package com.csinfotechbd.retail.loan.dataEntry.writeOffAccountDistribution;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

@Repository
public interface WriteOffAccountDistributionRepository extends JpaRepository<WriteOffAccountDistribution, Long> {


//    @Query(value = "SELECT DISTINCT LABI.ACCOUNT_NO                                                 AS accountNo, " +
//            "                CBIE.CUSTOMER_NAME                                              AS customerName, " +
//            "                COALESCE(CI.TOTAL_CONTACT, 0)                                   AS numberOfContact, " +
//            "                COALESCE(CI.RIGHT_PARTY_COUNT, 0)                               AS numberOfRightPartyContact, " +
//            "                COALESCE(CI.OTHER_PARTY_COUNT, 0)                               AS numberOfGuarantorOrThirdPartyContact, " +
//            "                COALESCE(LPTP.TOTAL_COUNT, 0)                                   AS totalPtp, " +
//            "                COALESCE(LPTP.BROKEN_COUNT, 0)                                  AS brokenPtp, " +
//            "                COALESCE(LPTP.CURED_COUNT, 0)                                   AS curedPtp, " +
//            "                COALESCE(VLE.VISIT_COUNT, 0)                                    AS numberOfVisit, " +
//            "                TO_CHAR(FU.FOLLOW_UP_DATE, 'DD.MM.YYYY')                        AS followUpDate, " +
//            "                DECODE(LADI.DPD_BUCKET, NULL, '-', LADI.DPD_BUCKET)             AS dpdBucket, " +
//            "                COALESCE(LADI.OUTSTANDING, '0')                                 AS outstanding, " +
//            "                COALESCE(LADI.OVERDUE_AMOUNT, 0)                                AS overdueAmount, " +
//            "                COALESCE(LADI.EMI_AMOUNT, 0)                                    AS emiAmount, " +
//            "                COALESCE(LPLD.PAYMENT, 0)                                       AS lastPayment, " +
//            "                COALESCE(LPCM.CURRENT_MONTH_PAYMENT, 0)                         AS currentMonthPayment, " +
//            "                DECODE(B.BRANCH_NAME, NULL, CBIE.BRANCH_NAME, B.BRANCH_NAME)    AS branchName, " +
//            "                DECODE(RC.RISK_CATEGORY_NAME, NULL, '-', RC.RISK_CATEGORY_NAME) AS riskCategory, " +
//            "                TO_CHAR(LADI.CREATED_DATE, 'DD.MM.YYYY')                        AS allocationDate " +
//            "FROM (SELECT * " +
//            "      FROM WRITE_OFF_ACCOUNT_DISTRIBUTION LADI_IN " +
//            "      WHERE LADI_IN.DEALER_PIN = :pin " +
//            "        AND LADI_IN.LATEST = '1' " +
//            "        AND LADI_IN.CREATED_DATE BETWEEN :startDate AND :endDate) LADI " +
//            "       JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON LABI.ID = LADI.LOAN_ACCOUNT_BASIC_INFO_ID " +
//            "       JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE on LABI.CUSTOMER_ID = CBIE.ID " +
//            "       LEFT JOIN LOAN_ACCOUNT_INFO LAI ON LABI.ID = LAI.LOAN_ACCOUNT_BASIC_INFO_ID " +
//            "       LEFT JOIN LOS_TB_S_BRANCH B " +
//            "         ON CBIE.BRANCH_CODE = B.BRANCH_CODE OR TO_NUMBER(CBIE.BRANCH_CODE) = TO_NUMBER(B.BRANCH_CODE) " +
//            "       LEFT JOIN DPDBUCKET_ENTITY DPD ON DPD.BUCKET_NAME = LADI.DPD_BUCKET " +
//            "       LEFT JOIN RISK_CATEGORY_DPD_BUCKET_ENTITIES RCDPD ON RCDPD.DPD_BUCKET_ENTITIES_ID = DPD.ID " +
//            "       LEFT JOIN RISK_CATEGORY RC ON RC.FID = CBIE.FID OR RCDPD.RISK_CATEGORY_ID = RC.ID " +
//            "       LEFT JOIN (SELECT CUSTOMER_ID, " +
//            "                         COUNT(LOAN_PTP_STATUS)                                        TOTAL_COUNT, " +
//            "                         COUNT(CASE WHEN LOWER(LOAN_PTP_STATUS) = 'cured' THEN 1 END)  CURED_COUNT, " +
//            "                         COUNT(CASE WHEN LOWER(LOAN_PTP_STATUS) = 'broken' THEN 1 END) BROKEN_COUNT " +
//            "                  FROM LOAN_PTP " +
//            "                  WHERE CREATED_DATE BETWEEN :startDate AND :endDate " +
//            "                  GROUP BY CUSTOMER_ID) LPTP ON LPTP.CUSTOMER_ID = LABI.CUSTOMER_ID " +
//            "       LEFT JOIN (SELECT CUSTOMER_ID, " +
//            "                         COUNT(ATTEMPT)                                               TOTAL_CONTACT, " +
//            "                         COUNT(CASE WHEN LOWER(CATEGORY) = 'right party' THEN 1 END)  RIGHT_PARTY_COUNT, " +
//            "                         COUNT(CASE WHEN LOWER(CATEGORY) != 'right party' THEN 1 END) OTHER_PARTY_COUNT " +
//            "                  FROM CONTACT_INFO " +
//            "                  WHERE PIN = :pin " +
//            "                    AND CREATED_DATE BETWEEN :startDate AND :endDate " +
//            "                  GROUP BY CUSTOMER_ID) CI ON CI.CUSTOMER_ID = LABI.CUSTOMER_ID " +
//            "       LEFT JOIN (SELECT ACCOUNT_CARD_NUMBER, COUNT(ID) VISIT_COUNT " +
//            "                  FROM VISIT_LEDGER_ENTITY " +
//            "                  WHERE CREATED_DATE BETWEEN :startDate AND :endDate " +
//            "                  GROUP BY ACCOUNT_CARD_NUMBER) VLE ON LABI.ACCOUNT_NO = VLE.ACCOUNT_CARD_NUMBER " +
//            "       LEFT JOIN (SELECT ACCOUNT_NO, SUM(PAYMENT) CURRENT_MONTH_PAYMENT " +
//            "                  FROM LOAN_PAYMENT " +
//            "                  WHERE PAYMENT_DATE BETWEEN :startDate AND :endDate " +
//            "                  GROUP BY ACCOUNT_NO) LPCM ON LABI.ACCOUNT_NO = LPCM.ACCOUNT_NO " +
//            "       LEFT JOIN (SELECT * " +
//            "                  FROM (SELECT ACCOUNT_NO, PAYMENT_DATE, PAYMENT, ROW_NUMBER() " +
//            "                      OVER (PARTITION BY ACCOUNT_NO " +
//            "                        ORDER BY PAYMENT_DATE DESC " +
//            "                        ) AS ROWNUMBER " +
//            "                        FROM LOAN_PAYMENT) " +
//            "                  WHERE ROWNUMBER = 1) LPLD ON LPLD.ACCOUNT_NO = LABI.ACCOUNT_NO " +
//            "       LEFT JOIN (SELECT CUSTOMER_ID, MIN(FOLLOW_UP_DATE) FOLLOW_UP_DATE " +
//            "                  FROM FOLLOW_UP_ENTITY " +
//            "                  WHERE TRUNC(FOLLOW_UP_DATE, 'DD') >= TRUNC(SYSDATE, 'DD') " +
//            "                  GROUP BY CUSTOMER_ID) FU ON FU.CUSTOMER_ID = LABI.CUSTOMER_ID " +
//            "ORDER BY riskCategory, numberOfContact ASC ", nativeQuery = true)



    @Query(value = "SELECT WOAD.ACCOUNT_NUMBER                                             AS accountNumber, " +
            "       WOAD.ACCOUNT_NAME                                               AS accountName, " +
            "       CI2.noOfAttempt                                                 AS noOfAttempt, " +
            "       CI2.rightParty                                                  AS rightParty, " +
            "       CI2.thirdParty                                                  AS thirdParty, " +
            "       LP2.noOfPtp                                                     AS noOfPtp, " +
            "       LP2.promiseTaken                                                AS promiseTaken, " +
            "       LP2.ptpBroken                                                   AS broken, " +
            "       TO_CHAR(FUE.FOLLOW_UP_DATE, 'DD.MM.YYYY')                       AS followUpDate, " +
            "       WOAD.DPD_BUCKET                                                 AS bucket, " +
            "       WOAD.OUTSTANDING                                                AS outstanding, " +
            "       WOAD.OVERDUE_AMOUNT                                             AS overDue, " +
            "       WOAD.EMI_AMOUNT                                                 AS emiAmt, " +
            "       B.BRANCH_NAME                                                   AS branchName, " +
            "       DECODE(RC.RISK_CATEGORY_NAME, NULL, '-', RC.RISK_CATEGORY_NAME) AS riskCategory, " +
            "       TO_CHAR(WOAD.CREATED_DATE, 'DD.MM.YYYY')                        AS allocationDate " +
            "FROM WRITE_OFF_ACCOUNT_DISTRIBUTION WOAD " +
            "       LEFT JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON LABI.ID = WOAD.LOAN_ACCOUNT_BASIC_INFO_ID " +
            "       LEFT JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE ON CBIE.ID = LABI.CUSTOMER_ID " +
            "       LEFT JOIN (SELECT CI.CUSTOMER_ID, " +
            "                         COUNT(CI.ATTEMPT)                                               AS noOfAttempt, " +
            "                         COUNT(CASE " +
            "                                 WHEN CI.CATEGORY = 'Right Party' THEN CI.CATEGORY END)  AS rightParty, " +
            "                         COUNT(CASE " +
            "                                 WHEN CI.CATEGORY <> 'Right Party' THEN CI.CATEGORY END) AS thirdParty " +
            "                  FROM CONTACT_INFO CI " +
            "                  WHERE CI.PIN = :pin " +
            "                    AND CI.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                  GROUP BY CI.CUSTOMER_ID) CI2 ON CI2.CUSTOMER_ID = LABI.CUSTOMER_ID " +
            "       LEFT JOIN (SELECT LP.CUSTOMER_ID, " +
            "                         COUNT(LP.LOAN_PTP_STATUS)                              AS noOfPtp, " +
            "                         COUNT(CASE " +
            "                                 WHEN LP.LOAN_PTP_STATUS = 'kept' THEN 1 END)   AS promiseTaken, " +
            "                         COUNT(CASE " +
            "                                 WHEN LP.LOAN_PTP_STATUS = 'broken' THEN 1 END) AS ptpBroken " +
            "                  FROM LOAN_PTP LP " +
            "                  WHERE LP.PIN = :pin " +
            "                    AND LP.CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                  GROUP BY LP.CUSTOMER_ID) LP2 ON LP2.CUSTOMER_ID = LABI.CUSTOMER_ID " +
            "       LEFT JOIN FOLLOW_UP_ENTITY FUE ON FUE.CUSTOMER_ID = LABI.CUSTOMER_ID " +
            "       LEFT JOIN LOS_TB_S_BRANCH B ON B.BRANCH_CODE = WOAD.BRANCH_CODE " +
            "       LEFT JOIN DPDBUCKET_ENTITY DE ON DE.BUCKET_NAME = WOAD.DPD_BUCKET " +
            "       LEFT JOIN RISK_CATEGORY_DPD_BUCKET_ENTITIES RCDBE ON RCDBE.DPD_BUCKET_ENTITIES_ID = DE.ID " +
            "       LEFT JOIN RISK_CATEGORY RC ON RC.FID = CBIE.FID OR RCDBE.RISK_CATEGORY_ID = RC.ID " +
            "WHERE WOAD.DEALER_PIN = :pin " +
            "  AND WOAD.LATEST = 1 " +
            "  AND WOAD.CREATED_DATE BETWEEN :startDate AND :endDate ", nativeQuery = true)
    List<Tuple> findWriteOffAccountDistributionByDealerPinAndMonth(@Param("pin") String pin,@Param("startDate") Date startDate,@Param("endDate") Date endDate);







    @Query(value = "SELECT * FROM WRITE_OFF_ACCOUNT_DISTRIBUTION " +
            "WHERE LOAN_ACCOUNT_BASIC_INFO_ID IN (SELECT id FROM LOAN_ACCOUNT_BASIC_INFO WHERE ACCOUNT_NO IN ?1) " +
            "  AND LATEST = '1' ", nativeQuery = true)
    List<WriteOffAccountDistribution> findAllByAccountNo(List<String> accountNos);

    @Query(value = "SELECT * FROM WRITE_OFF_ACCOUNT_DISTRIBUTION WHERE DEALER_PIN = ? AND LATEST = '1' ", nativeQuery = true)
    List<WriteOffAccountDistribution> findByDealerPinAndLatest(String pin);
}
