package com.unisoft.collection.distribution.loan;
/*
Created by   Islam at 7/28/2019
*/


import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.unisoft.letterGeneration.LetterPayload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.*;

@Repository
public interface LoanAccountDistributionRepository extends JpaRepository<LoanAccountDistributionInfo, Long> {
//    List<LoanAccountDistributionInfo> findByCreatedDateIsBetweenAndSamAccountAndWriteOffAccountAndDealerNameNotAndSupervisorNameNotOrderByCreatedDateDesc(Date startDate, Date endDate, String b, String s, String dealerName, String superVisorName);
//
//    List<LoanAccountDistributionInfo>
//    findByCreatedDateLessThanAndSamAccountNotInAndWriteOffAccountNotInAndProductGroupNotIn
//            (Date startDate, List<String> samIgnore, List<String> writeOffIgnore, List<String> productGroup);
//
//    List<LoanAccountDistributionInfo>
//    findByCreatedDateLessThanAndSamAccountNotInAndWriteOffAccountNotInAndSchemeCodeNotIn
//            (Date startDate, List<String> samIgnore, List<String> writeOffIgnore, List<String> productGroup);

    List<LoanAccountDistributionInfo> findByCreatedDateLessThanAndSchemeCodeInAndDpdBucketIn(Date startDate, List<String> schemaCode, List<String> dpdBucket);

    List<LoanAccountDistributionInfo> findByCreatedDateIsBetweenAndDealerPinAndSupervisorPin(Date startDate, Date endDate, String dealerPin, String supervisorPin);

    List<LoanAccountDistributionInfo> findByCreatedDateIsBetweenAndDealerNameAndSupervisorNameAndSamAccountNotInAndWriteOffAccountNotInAndSchemeCodeNotIn(Date startDate, Date endDate, String dealerName,
                                                                                                                                                          String supervisorName, List<String> samAccount, List<String> writeOffAccount, List<String> schemaCode
    );

    LoanAccountDistributionInfo findFirstByLoanAccountBasicInfoOrderByCreatedDateDesc(LoanAccountBasicInfo loanAccountBasicInfo);

    //    LoanAccountDistributionInfo findFirstByLoanAccountBasicInfoAndCreatedDateGreaterThanEqualAndLatestOrderByCreatedDateDesc(LoanAccountBasicInfo loanAccountBasicInfo, Date startDate, String latest);
//
//    List<LoanAccountDistributionInfo> findByDealerPin(String pin);
//
//    List<LoanAccountDistributionInfo> findByLoanAccountBasicInfoOrderByCreatedDateDesc(LoanAccountBasicInfo loanAccountBasicInfo);
//
    List<LoanAccountDistributionInfo> findByCreatedDateIsBetweenAndDealerPin(Date startDate, Date endDate, String pin);

    List<LoanAccountDistributionInfo> findByCreatedDateIsBetweenAndSamAccountAndWriteOffAccountAndLatestOrderByCreatedDateDesc(Date startDate, Date endDate, String s, String w, String l);

    List<LoanAccountDistributionInfo> findByCreatedDateIsBetweenAndSamAccountAndWriteOffAccountAndLatestOrderByIdDesc(Date startDate, Date endDate, String s, String w, String l);

//    LoanAccountDistributionInfo findByCreatedDateIsBetweenAndLatestAndMonitoringStatus(Date startDate, Date endDate, String latest, String monitoringStatus);

    LoanAccountDistributionInfo findFirstByCreatedDateIsBetweenAndLoanAccountBasicInfoOrderByCreatedDateDesc(Date startDate, Date finalEndDate, LoanAccountBasicInfo loanAccountBasicInfo);

    LoanAccountDistributionInfo findFirstByLoanAccountBasicInfoAndLatestOrderByCreatedDateDesc(LoanAccountBasicInfo loanAccountBasicInfo, String latest);

    LoanAccountDistributionInfo findFirstByCreatedDateIsBetweenAndLoanAccountBasicInfoAndLatestOrderByCreatedDateDesc(Date startDate, Date endDate, LoanAccountBasicInfo loanAccountBasicInfo, String latest);

    LoanAccountDistributionInfo findFirstByCreatedDateLessThanAndLoanAccountBasicInfoAndLatestOrderByCreatedDateDesc(Date startDate, LoanAccountBasicInfo loanAccountBasicInfo, String latest);

    List<LoanAccountDistributionInfo> findByLoanAccountBasicInfo(LoanAccountBasicInfo loanAccountBasicInfo);

    List<LoanAccountDistributionInfo> findByEnabled(boolean enabled);

    @Query("SELECT distinct productGroup FROM LoanAccountDistributionInfo WHERE createdDate >= :startDate")
    List<String> findDistributedProductGroups(@Param("startDate") Date startDate);

    @Query("SELECT distinct productGroup FROM LoanAccountDistributionInfo")
    List<String> findDistributedProductGroups();

    @Query(value = "" +
            "SELECT DISTINCT EIE.ID, LADI.DEALER_PIN AS PIN, UPPER(DE.NAME) AS DESIGNATION, UPPER(EIE.UNIT) AS UNIT " +
            "FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI " +
            "       JOIN EMPLOYEE_INFO_ENTITY EIE ON LADI.DEALER_PIN = EIE.PIN " +
            "       LEFT JOIN DESIGNATION_ENTITY DE on EIE.DESIGNATION_ID = DE.ID " +
            "WHERE LADI.LATEST = '1' " +
            "  AND LADI.CREATED_DATE BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Tuple> findDistributedDealerDataWithinDateRange(@Param("startDate") Date startdate, @Param("endDate") Date endDate);

    /**
     * Account wise summary for dealer dashboard.
     * Provides details for PTP, Follow Up and other account and customer related information.
     * <p>
     * Completed by Yasir Araphat
     * At 12 April 2021
     *
     * @param dealerPin
     * @return List of Tuples consisting account summary
     */
    @Query(value = "" +
            "SELECT DISTINCT LABI.ACCOUNT_NO                                                 AS accountNo, " +
            "                CBIE.CUSTOMER_NAME                                              AS customerName, " +
            "                COALESCE(CI.TOTAL_CONTACT, 0)                                   AS numberOfContact, " +
            "                COALESCE(CI.RIGHT_PARTY_COUNT, 0)                               AS numberOfRightPartyContact, " +
            "                COALESCE(CI.OTHER_PARTY_COUNT, 0)                               AS numberOfGuarantorOrThirdPartyContact, " +
            "                COALESCE(LPTP.TOTAL_COUNT, 0)                                   AS totalPtp, " +
            "                COALESCE(LPTP.BROKEN_COUNT, 0)                                  AS brokenPtp, " +
            "                COALESCE(LPTP.CURED_COUNT, 0)                                   AS curedPtp, " +
            "                COALESCE(VLE.VISIT_COUNT, 0)                                    AS numberOfVisit, " +
            "                TO_CHAR(FU.FOLLOW_UP_DATE, 'DD.MM.YYYY')                        AS followUpDate, " +
            "                DECODE(LADI.DPD_BUCKET, NULL, '-', LADI.DPD_BUCKET)             AS dpdBucket, " +
            "                DECODE(LADI.CURRENT_DPD_BUCKET, NULL, '-', LADI.CURRENT_DPD_BUCKET) AS currentDpdBucket, " +
            "                COALESCE(LADI.OUT_STANDING, '0')                                AS outstanding, " +
            "                COALESCE(LADI.OPENING_OVER_DUE, 0)                              AS overdueAmount, " +
            "                COALESCE(LADI.EMI_AMOUNT, 0)                                    AS emiAmount, " +
            "                COALESCE(LPLD.PAYMENT, 0)                                       AS lastPayment, " +
            "                COALESCE(LPCM.CURRENT_MONTH_PAYMENT, 0)                         AS currentMonthPayment, " +
            "                DECODE(B.BRANCH_NAME, NULL, CBIE.BRANCH_NAME, B.BRANCH_NAME)    AS branchName, " +
            "                DECODE(RC.RISK_CATEGORY_NAME, NULL, '-', RC.RISK_CATEGORY_NAME) AS riskCategory, " +
            "                TO_CHAR(LADI.CREATED_DATE, 'DD.MM.YYYY')                        AS allocationDate, " +
            "                PGE.SHORT_NAME                                                  AS productShortName " +
            "      FROM (SELECT * " +
            "      FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI_IN " +
            "      WHERE LADI_IN.DEALER_PIN = :pin " +
            "        AND LADI_IN.LATEST = '1' " +
            "        AND LADI_IN.CREATED_DATE BETWEEN :startDate AND :endDate) LADI " +
            "       JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON LABI.ID = LADI.LOAN_ACCOUNT_BASIC_INFO_ID " +
            "       JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE on LABI.CUSTOMER_ID = CBIE.ID " +
            "       LEFT JOIN LOAN_ACCOUNT_INFO LAI ON LABI.ID = LAI.LOAN_ACCOUNT_BASIC_INFO_ID " +
            "       LEFT JOIN LOS_TB_S_BRANCH B ON CBIE.BRANCH_CODE = B.BRANCH_CODE OR TO_NUMBER(CBIE.BRANCH_CODE) = TO_NUMBER(B.BRANCH_CODE) " +
            "       LEFT JOIN DPDBUCKET_ENTITY DPD ON DPD.BUCKET_NAME = LADI.DPD_BUCKET " +
            "       LEFT JOIN RISK_CATEGORY_DPD_BUCKET_ENTITIES RCDPD ON RCDPD.DPD_BUCKET_ENTITIES_ID = DPD.ID " +
            "       LEFT JOIN RISK_CATEGORY RC ON RC.FID = CBIE.FID OR RCDPD.RISK_CATEGORY_ID = RC.ID " +
            "       LEFT JOIN PRODUCT_TYPE_ENTITY PTE ON LADI.SCHEME_CODE = PTE.CODE " +
            "        LEFT JOIN PRODUCT_GROUP_ENTITY PGE ON PGE.ID = PTE.PRODUCT_GROUP_ENTITY_ID " +
            "       LEFT JOIN (SELECT CUSTOMER_ID, " +
            "                         COUNT(LOAN_PTP_STATUS)                                        TOTAL_COUNT, " +
            "                         COUNT(CASE WHEN LOWER(LOAN_PTP_STATUS) = 'cured' THEN 1 END)  CURED_COUNT, " +
            "                         COUNT(CASE WHEN LOWER(LOAN_PTP_STATUS) = 'broken' THEN 1 END) BROKEN_COUNT " +
            "                  FROM LOAN_PTP " +
            "                  WHERE CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                  GROUP BY CUSTOMER_ID) LPTP ON LPTP.CUSTOMER_ID = LABI.CUSTOMER_ID " +
            "       LEFT JOIN (SELECT CUSTOMER_ID, " +
            "                         COUNT(ATTEMPT)                                               TOTAL_CONTACT, " +
            "                         COUNT(CASE WHEN LOWER(CATEGORY) = 'right party' THEN 1 END)  RIGHT_PARTY_COUNT, " +
            "                         COUNT(CASE WHEN LOWER(CATEGORY) != 'right party' THEN 1 END) OTHER_PARTY_COUNT " +
            "                  FROM CONTACT_INFO " +
            "                  WHERE PIN = :pin " +
            "                    AND CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                  GROUP BY CUSTOMER_ID) CI ON CI.CUSTOMER_ID = LABI.CUSTOMER_ID " +
            "       LEFT JOIN (SELECT ACCOUNT_CARD_NUMBER, COUNT(ID) VISIT_COUNT " +
            "                  FROM VISIT_LEDGER_ENTITY " +
            "                  WHERE CREATED_DATE BETWEEN :startDate AND :endDate " +
            "                  GROUP BY ACCOUNT_CARD_NUMBER) VLE ON LABI.ACCOUNT_NO = VLE.ACCOUNT_CARD_NUMBER " +
            "       LEFT JOIN (SELECT ACCOUNT_NO, SUM(PAYMENT) CURRENT_MONTH_PAYMENT " +
            "                  FROM LOAN_PAYMENT " +
            "                  WHERE PAYMENT_DATE BETWEEN :startDate AND :endDate " +
            "                  GROUP BY ACCOUNT_NO) LPCM ON LABI.ACCOUNT_NO = LPCM.ACCOUNT_NO " +
            "       LEFT JOIN (SELECT * " +
            "                  FROM (SELECT ACCOUNT_NO, PAYMENT_DATE, PAYMENT, ROW_NUMBER() " +
            "                      OVER (PARTITION BY ACCOUNT_NO " +
            "                        ORDER BY PAYMENT_DATE DESC " +
            "                        ) AS ROWNUMBER " +
            "                        FROM LOAN_PAYMENT) " +
            "                  WHERE ROWNUMBER = 1) LPLD ON LPLD.ACCOUNT_NO = LABI.ACCOUNT_NO " +
            "       LEFT JOIN (SELECT CUSTOMER_ID, MIN(FOLLOW_UP_DATE) FOLLOW_UP_DATE " +
            "                  FROM FOLLOW_UP_ENTITY " +
            "                  WHERE TRUNC(FOLLOW_UP_DATE, 'DD') >= TRUNC(SYSDATE, 'DD') " +
            "                  GROUP BY CUSTOMER_ID) FU ON FU.CUSTOMER_ID = LABI.CUSTOMER_ID " +
            "ORDER BY riskCategory, numberOfContact ASC", nativeQuery = true)
    List<Tuple> getLoanAccountDistributionSummary(@Param("pin") String dealerPin, @Param("startDate") Date startdate, @Param("endDate") Date endDate);

    @Modifying
    @Transactional
    @Query(value = "UPDATE LOAN_ACCOUNT_DISTRIBUTION_INFO l SET l.latest = '0' WHERE l.LOAN_ACCOUNT_BASIC_INFO_ID = :loanBasicId and l.CREATED_DATE >= trunc((sysdate),'MM') and l.latest = '1'", nativeQuery = true)
    int updateLatestStatus(@Param("loanBasicId") Long loanBasicId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE LOAN_ACCOUNT_DISTRIBUTION_INFO LADI SET LADI.CURRENT_DPD_BUCKET = :bucket, LADI.CURRENT_OVERDUE = :overdue WHERE LADI.LOAN_ACCOUNT_BASIC_INFO_ID = (SELECT ID FROM LOAN_ACCOUNT_BASIC_INFO WHERE ACCOUNT_NO = :accountNo) and LADI.CREATED_DATE >= trunc((sysdate - 1),'MM') and LADI.latest = '1'", nativeQuery = true)
    int updateCurrentBucketRelatedStatus(@Param("accountNo") String accountNo, @Param("bucket") String bucket, @Param("overdue") double overdue);

    @Query(value = "SELECT DISTINCT ACCOUNT_NO " +
            "FROM LOAN_ACCOUNT_BASIC_INFO LABI " +
            "       JOIN LOAN_ACCOUNT_DISTRIBUTION_INFO LADI " +
            "         ON LABI.ID = LADI.LOAN_ACCOUNT_BASIC_INFO_ID AND LADI.CREATED_DATE >= TRUNC(SYSDATE, 'MM') AND " +
            "            LADI.LATEST = '1'", nativeQuery = true)
    Set<String> getCurrentMonthDistributedAccountNumbers();


    @Query(value = "SELECT DISTINCT DEALER_PIN " +
            "FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI " +
            "         WHERE LADI.CREATED_DATE >= TRUNC(SYSDATE - 1, 'MM') AND " +
            "            LADI.LATEST = '1'", nativeQuery = true)
    Set<String> getDistributedDealerPinsTillYesterday();


    @Query(value = "SELECT LADI.DEALER_PIN AS dealerId, (DUSR.FIRST_NAME || ' ' || DUSR.LAST_NAME) AS dealerName, " +
            "LADI.CREATED_BY AS createdById, (CUSR.FIRST_NAME || ' ' || CUSR.LAST_NAME) AS createByName, LADI.CREATED_DATE AS createdDate, " +
            "(USR_S.FIRST_NAME || ' ' || USR_S.LAST_NAME) AS teamleaderName ,USR_S.EMPLOYEE_ID as teamleaderId " +

            "FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI " +
            "LEFT JOIN LOS_TB_M_USERS DUSR ON DUSR.USERNAME = LADI.DEALER_PIN " +
            "LEFT JOIN LOS_TB_M_USERS CUSR ON CUSR.USERNAME = LADI.CREATED_BY " +
            "LEFT JOIN EMPLOYEE_INFO_ENTITY D ON D.PIN = LADI.DEALER_PIN " +
            "LEFT JOIN PEOPLE_ALLOCATION_LOGIC PAL ON D.ID = PAL.DEALER_ID " +
            "LEFT JOIN EMPLOYEE_INFO_ENTITY S ON PAL.TEAM_LEAD_ID = S.ID " +
            "LEFT JOIN LOS_TB_M_USERS USR_S ON S.USER_ID = USR_S.USER_ID " +

            "WHERE LADI.LOAN_ACCOUNT_BASIC_INFO_ID = (SELECT ID FROM LOAN_ACCOUNT_BASIC_INFO WHERE ACCOUNT_NO = ?1) " +
            "ORDER BY LADI.CREATED_DATE DESC", nativeQuery = true)
    List<Tuple> getLoanAccountDealerAllocationHistory(String accountNo);


    @Query(value = "SELECT * FROM LOAN_ACCOUNT_DISTRIBUTION_INFO WHERE DEALER_PIN = ?1 AND CREATED_DATE BETWEEN cast(?2 as date) AND cast(?3 as date) ", nativeQuery = true)
    List<LoanAccountDistributionInfo> findCurrnetLoanAccountDistributionInfoByDealerPin(String pin, Date startDate, Date endDate);

    @Query(value = "" +
            "SELECT new com.unisoft.letterGeneration.LetterPayload(distribution.currentOverdue,distribution.currentDpdBucket,distribution.emiAmount) FROM LoanAccountDistributionInfo distribution " +
            "INNER JOIN LoanAccountBasicInfo baic ON distribution.loanAccountBasicInfo = baic.id " +
            "WHERE baic.accountNo= :accNo AND distribution.latest = '1' AND distribution.createdDate BETWEEN :startDate AND :endDate")
    LetterPayload getLetterInfo(@Param("accNo") String accNo, @Param("startDate") Date startDate, @Param("endDate") Date endDate);


    @Query(value = " SELECT " +
             "LADI.DEALER_NAME AS Dealer_Name, " +
             "LADI.DEALER_PIN as  Dealer_Id, " +
             "LADI.DPD_BUCKET, " +
             "count(LADI.ID ) AS AC_No, " +
             "sum(TO_NUMBER(LADI.OUT_STANDING)) AS Total_OS, " +
             "sum(LADI.OPENING_OVER_DUE) AS Total_overdue " +

             "FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI " +
             " WHERE LADI.LATEST = '1' AND LADI.CREATED_DATE BETWEEN ?1 and ?2 " +
             "group by LADI.DEALER_PIN,LADI.DEALER_NAME, LADI.DPD_BUCKET " +
             "order by DEALER_NAME ASC", nativeQuery = true)
    List<Tuple> findByCurrentMonthAllocationDistribution(Date startDate, Date endDate);

//    @Query(value = " SELECT new com.unisoft.collection.distribution.loan.AllocationDetails( " +
//            "LADI.dealerName , " +
//            "LADI.dealerPin, " +
//            "LADI.dpdBucket, " +
//            "count(LADI.id ) , " +
//            "sum(to_number(LADI.outStanding)) , " +
//            "sum(LADI.openingOverDue)" +
//
//            "FROM com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo LADI " +
//            " WHERE LADI.latest = '1' AND LADI.createdDate BETWEEN ?1 and ?2 " +
//            "group by LADI.dealerPin,LADI.dealerName, LADI.dpdBucket " +
//            "order by dealerName ASC")
//    List<AllocationDetails> findByCurrentMonthAllocationDistribution(Date startDate, Date endDate);



    @Query(value = "SELECT LABI.ACCOUNT_NO FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI " +
            "LEFT JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON LABI.ID = LADI.LOAN_ACCOUNT_BASIC_INFO_ID " +
            "WHERE LADI.LATEST = 1 AND LADI.CREATED_DATE between :startDate AND :endDate ", nativeQuery = true)
    List<String> getCurrentMonthDistributionAccNo(@Param("startDate") Date startDate,@Param("endDate") Date endDate);


}
