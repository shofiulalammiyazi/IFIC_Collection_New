package com.unisoft.reports.retail.loan.productWiseClReport;

import com.unisoft.collection.settings.productGroup.ProductGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductWiseCLReportRepository extends JpaRepository<ProductGroupEntity, Long> {


    @Query(value = " " +
            "WITH final_table AS (SELECT LL2.productName AS productName, " +
            "       SUM(LL2.totalAcc) AS totalAcc, " +
            "       SUM(LL2.totalOs) AS totalOs, " +
            "       SUM(LL2.bl_noAcc) AS blAcc, " +
            "       SUM(LL2.bl_os) AS blOs, " +
            "       SUM(LL2.df_noAcc) AS dfAcc, " +
            "       SUM(LL2.df_os) AS dfOs, " +
            "       SUM(LL2.ss_noAcc) AS ssAcc, " +
            "       SUM(LL2.ss_os) AS ssOs, " +
            "       SUM(LL2.sma_noAcc) AS smaAcc, " +
            "       SUM(LL2.sma_os) AS smaOs, " +
            "       SUM(LL2.uc_noAcc) AS ucAcc, " +
            "       SUM(LL2.uc_os) AS ucOs, " +
            "       LL2.parOs as parOs, " +
            "       COUNT(LL2.parAcc) as parAcc " +
            "FROM (SELECT LL.PRODUCT_NAME     AS productName, " +
            "             COUNT(LL.ACC_NO)    AS totalAcc, " +
            "             COUNT(LL.ACC_NO)    AS pivTotalAcc, " +
            "             SUM(LL.OUTSTANDING) AS totalOs, " +
            "             SUM(LL.OUTSTANDING) AS pivTotalOs, " +
            "             LL.CL_STATUS        AS clStatus, " +
            "             SUM(CASE " +
            "                 WHEN LL.LOAN_ACC_PAR = 'Yes' AND LL.CL_STATUS <> 'UC' THEN LL.OUTSTANDING  ELSE 0 END ) AS parOs, " +
            "             COUNT(CASE " +
            "                 WHEN LL.LOAN_ACC_PAR = 'Yes' AND LL.CL_STATUS <> 'UC' THEN LL.ACC_NO END ) AS parAcc " +
            "      FROM LOAN_LISTING LL " +
            "    WHERE TO_CHAR(LL.LISTING_DOWN_DATE,'YYYY-MM') = ?1 " +
            "      GROUP BY LL.PRODUCT_NAME, LL.CL_STATUS) " +
            "    PIVOT ( " +
            "          SUM(pivTotalAcc) AS noAcc, " +
            "            SUM(pivTotalOs) AS os " +
            "    FOR clStatus IN ('BL' AS bl,'DF' AS df,'SS' AS ss,'SMA' AS sma,'UC' AS uc) " +
            "    ) LL2 " +
            "GROUP BY LL2.productName, LL2.parOs) " +
            "SELECT productName, " +
            "      sum(totalAcc) AS totalAcc, " +
            "      sum(totalOs) AS totalOs, " +
            "      sum(blAcc) AS blAcc, " +
            "      sum(blOs) AS blOs, " +
            "      sum(dfAcc) AS dfAcc, " +
            "      sum(dfOs) AS dfOs, " +
            "      sum(ssAcc) AS ssAcc, " +
            "      sum(ssOs) AS ssOs, " +
            "      sum(smaAcc) AS smaAcc, " +
            "      sum(smaOs) AS smaOs, " +
            "      sum(parAcc) AS parAcc, " +
            "      sum(parOs) AS parOs, " +
            "      sum(ucAcc) AS ucAcc, " +
            "      sum(ucOs) AS ucOs, " +
            "       (SUM(blOs)+SUM(dfOs)+ sum(ssOs)) AS cloutstanding, " +
            "       (SUM(blAcc)+SUM(dfAcc)+ sum(ssAcc)) AS totalClAcc " +
            "FROM final_table ft " +
            "GROUP BY productName " , nativeQuery = true)
    List<ProductWiseClReport> getProductWiseClReport(String month);





//    @Query(value = "" +
//            "SELECT DECODE(LADI_EX.PRODUCT_GROUP, " +
//            "              NULL, " +
//            "              DECODE(CBIE.TYPE_ACCOUNT, NULL, 'TOTAL', 'Sub Total Of Retail ' || CBIE.TYPE_ACCOUNT), " +
//            "              LADI_EX.PRODUCT_GROUP)                                                       AS PRODUCT_GROUP, " +
//            "       COALESCE(SUM(LADI_EX.OUT_STANDING), 0)                                              AS TOTAL_EXPOSURE_OUTSTANDING, " +
//            "       COALESCE(ROUND(100 * RATIO_TO_REPORT(SUM(LADI_EX.OUT_STANDING)) " +
//            "           OVER (PARTITION BY GROUPING(CBIE.TYPE_ACCOUNT), GROUPING(LADI_EX.PRODUCT_GROUP)), 2), " +
//            "                0)                                                                         AS TOTAL_EXPOSURE_PERCENTAGE, " +
//            "       COUNT(DISTINCT LADI_EX.LOAN_ACCOUNT_BASIC_INFO_ID)                                  AS TOTAL_EXPOSURE_ACCOUNT, " +
//            "       COALESCE(SUM(LADI_CL.OUT_STANDING), 0)                                              AS TOTAL_CL_OUTSTANDING, " +
//            "       COUNT(DISTINCT LADI_CL.LOAN_ACCOUNT_BASIC_INFO_ID)                                  AS TOTAL_CL_ACCOUNT, " +
//            "       COALESCE(ROUND(100 * RATIO_TO_REPORT(SUM(LADI_CL.OUT_STANDING)) " +
//            "           OVER (PARTITION BY GROUPING(CBIE.TYPE_ACCOUNT), GROUPING(LADI_EX.PRODUCT_GROUP)), 2), " +
//            "                0)                                                                         AS TOTAL_CL_PERCENTAGE, " +
//            "       COALESCE(SUM(LADI_PAR.OUT_STANDING), 0)                                             AS TOTAL_PAR_OUTSTANDING, " +
//            "       COUNT(DISTINCT LADI_PAR.LOAN_ACCOUNT_BASIC_INFO_ID)                                 AS TOTAL_PAR_ACCOUNT, " +
//            "       COALESCE(ROUND(100 * RATIO_TO_REPORT(SUM(LADI_PAR.OUT_STANDING)) " +
//            "           OVER (PARTITION BY GROUPING(CBIE.TYPE_ACCOUNT), GROUPING(LADI_EX.PRODUCT_GROUP)), 2), " +
//            "                0)                                                                         AS TOTAL_PAR_PERCENTAGE, " +
//            "       COALESCE(SUM(CASE WHEN LADI_CL.BL != 0 THEN LADI_CL.OUT_STANDING ELSE NULL END), 0) AS BL_OUTSTANDING, " +
//            "       COALESCE(SUM(LADI_CL.BL), 0)                                                        AS BL_ACC, " +
//            "       COALESCE(SUM(CASE WHEN LADI_CL.DF != 0 THEN LADI_CL.OUT_STANDING ELSE NULL END), " +
//            "                0)                                                                         AS DF_OUTSTANDING, " +
//            "       COALESCE(SUM(LADI_CL.DF), 0)                                                        AS DF_ACC, " +
//            "       COALESCE(SUM(CASE WHEN LADI_CL.SS != 0 THEN LADI_CL.OUT_STANDING ELSE NULL END), " +
//            "                0)                                                                         AS SS_OUTSTANDING, " +
//            "       COALESCE(SUM(LADI_CL.SS), 0)                                                        AS SS_ACC, " +
//            "       COALESCE(SUM(CASE WHEN LADI_CL.SMA != 0 THEN LADI_CL.OUT_STANDING ELSE NULL END), " +
//            "                0)                                                                         AS SMA_OUTSTANDING, " +
//            "       COALESCE(SUM(LADI_CL.SMA), 0)                                                       AS SMA_ACC, " +
//            "       COALESCE(SUM(CASE WHEN LADI_CL.UC != 0 THEN LADI_CL.OUT_STANDING ELSE NULL END), " +
//            "                0)                                                                         AS UC_OUTSTANDING, " +
//            "       COALESCE(SUM(LADI_CL.UC), 0)                                                        AS UC_ACC " +
//            " " +
//            "FROM CUSTOMER_BASIC_INFO_ENTITY CBIE " +
//            "       JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON CBIE.ID = LABI.CUSTOMER_ID " +
//            "       JOIN LOAN_ACCOUNT_DISTRIBUTION_INFO LADI_EX " +
//            "         ON LABI.ID = LADI_EX.LOAN_ACCOUNT_BASIC_INFO_ID AND LADI_EX.LATEST = '1' AND " +
//            "            TO_CHAR(LADI_EX.CREATED_DATE, 'yyyy-mm') = :monthTxt " +
//            "       LEFT JOIN (SELECT * " +
//            "                  FROM (SELECT LADI.LOAN_ACCOUNT_BASIC_INFO_ID, OUT_STANDING, LAI.ASSET_CLASSIFICATION " +
//            "                        FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI " +
//            "                               JOIN LOAN_ACCOUNT_INFO LAI " +
//            "                                 ON LAI.LOAN_ACCOUNT_BASIC_INFO_ID = LADI.LOAN_ACCOUNT_BASIC_INFO_ID AND " +
//            "                                    LAI.ASSET_CLASSIFICATION IN ('UC', 'SMA', 'SS', 'DF', 'BL') " +
//            "                        WHERE TO_CHAR(LADI.CREATED_DATE, 'yyyy-mm') = :monthTxt " +
//            "                          AND LADI.LATEST = '1') " +
//            "                      PIVOT ( " +
//            "                        COUNT(*) for ASSET_CLASSIFICATION in ('UC' uc, 'SMA' sma, 'SS' ss, 'DF' df, 'BL' bl) " +
//            "                      )) LADI_CL ON LADI_EX.LOAN_ACCOUNT_BASIC_INFO_ID = LADI_CL.LOAN_ACCOUNT_BASIC_INFO_ID " +
//            "       LEFT JOIN (SELECT LADI.LOAN_ACCOUNT_BASIC_INFO_ID, OUT_STANDING " +
//            "                  FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI " +
//            "                         JOIN DPDBUCKET_ENTITY dpd ON dpd.BUCKET_NAME = LADI.DPD_BUCKET " +
//            "                         JOIN PAR_ACC_RULE_LOAN_DPD_BUCKET_ENTITY_LIST pr ON dpd.ID = pr.DPD_BUCKET_ENTITY_LIST_ID " +
//            "                  WHERE LADI.LATEST = '1' " +
//            "                    AND TO_CHAR(LADI.CREATED_DATE, 'yyyy-mm') = :monthTxt) LADI_PAR " +
//            "         ON LADI_EX.LOAN_ACCOUNT_BASIC_INFO_ID = LADI_PAR.LOAN_ACCOUNT_BASIC_INFO_ID " +
//            "GROUP BY ROLLUP (CBIE.TYPE_ACCOUNT, LADI_EX.PRODUCT_GROUP)", nativeQuery = true)
//    Object[][] getLoanReport(@Param("monthTxt") String month);
//
//
//    @Query(value = "" +
//            "SELECT DECODE(PRODUCT_GROUP, " +
//            "              NULL, " +
//            "              DECODE(ACCOUNT_TYPE, NULL, 'GRAND TOTAL', 'Sub Total Of Retail ' || ACCOUNT_TYPE), " +
//            "              PRODUCT_GROUP)                                                           AS PRODUCT_GROUP, " +
//            "       SUM(TOTAL_EXPOSURE_OUTSTANDING), " +
//            "       COALESCE(ROUND(100 * RATIO_TO_REPORT(SUM(TOTAL_EXPOSURE_OUTSTANDING)) " +
//            "           OVER (PARTITION BY GROUPING(ACCOUNT_TYPE), GROUPING(PRODUCT_GROUP)), 2), 0) AS TOTAL_EXPOSURE_PERCENTAGE, " +
//            "       SUM(TOTAL_EXPOSURE_ACCOUNT), " +
//            "       SUM(TOTAL_CL_OUTSTANDING), " +
//            "       SUM(TOTAL_CL_ACCOUNT), " +
//            "       COALESCE(ROUND(100 * RATIO_TO_REPORT(SUM(TOTAL_CL_OUTSTANDING)) " +
//            "           OVER (PARTITION BY GROUPING(ACCOUNT_TYPE), GROUPING(PRODUCT_GROUP)), 2), 0) AS TOTAL_CL_PERCENTAGE, " +
//            "       SUM(TOTAL_PAR_OUTSTANDING), " +
//            "       SUM(TOTAL_PAR_ACCOUNT), " +
//            "       COALESCE(ROUND(100 * RATIO_TO_REPORT(SUM(TOTAL_PAR_OUTSTANDING)) " +
//            "           OVER (PARTITION BY GROUPING(ACCOUNT_TYPE), GROUPING(PRODUCT_GROUP)), 2), 0) AS TOTAL_PAR_PERCENTAGE, " +
//            "       SUM(BL_OUTSTANDING), " +
//            "       SUM(BL_ACC), " +
//            "       SUM(DF_OUTSTANDING), " +
//            "       SUM(DF_ACC), " +
//            "       SUM(SS_OUTSTANDING), " +
//            "       SUM(SS_ACC), " +
//            "       SUM(SMA_OUTSTANDING), " +
//            "       SUM(SMA_ACC), " +
//            "       SUM(UC_OUTSTANDING), " +
//            "       SUM(UC_ACC) " +
//            "FROM (SELECT CBIE.TYPE_ACCOUNT                                                                   AS ACCOUNT_TYPE, " +
//            "             DECODE(LADI_EX.PRODUCT_GROUP, NULL, '-', LADI_EX.PRODUCT_GROUP)                     AS PRODUCT_GROUP, " +
//            "             COALESCE(SUM(LADI_EX.OUT_STANDING), 0)                                              AS TOTAL_EXPOSURE_OUTSTANDING, " +
//            "             COALESCE(ROUND(100 * RATIO_TO_REPORT(SUM(LADI_EX.OUT_STANDING))OVER (), 2), " +
//            "                      0)                                                                         AS TOTAL_EXPOSURE_PERCENTAGE, " +
//            "             COUNT(DISTINCT LADI_EX.LOAN_ACCOUNT_BASIC_INFO_ID)                                  AS TOTAL_EXPOSURE_ACCOUNT, " +
//            "             COALESCE(SUM(LADI_CL.OUT_STANDING), 0)                                              AS TOTAL_CL_OUTSTANDING, " +
//            "             COUNT(DISTINCT LADI_CL.LOAN_ACCOUNT_BASIC_INFO_ID)                                  AS TOTAL_CL_ACCOUNT, " +
//            "             COALESCE(ROUND(100 * RATIO_TO_REPORT(SUM(LADI_CL.OUT_STANDING)) OVER (), 2), 0)     AS TOTAL_CL_PERCENTAGE, " +
//            "             COALESCE(SUM(LADI_PAR.OUT_STANDING), 0)                                             AS TOTAL_PAR_OUTSTANDING, " +
//            "             COUNT(DISTINCT LADI_PAR.LOAN_ACCOUNT_BASIC_INFO_ID)                                 AS TOTAL_PAR_ACCOUNT, " +
//            "             COALESCE(ROUND(100 * RATIO_TO_REPORT(SUM(LADI_PAR.OUT_STANDING)) OVER (), 2), " +
//            "                      0)                                                                         AS TOTAL_PAR_PERCENTAGE, " +
//            "             COALESCE(SUM(CASE WHEN LADI_CL.BL != 0 THEN LADI_CL.OUT_STANDING ELSE NULL END), 0) AS BL_OUTSTANDING, " +
//            "             COALESCE(SUM(LADI_CL.BL), 0)                                                        AS BL_ACC, " +
//            "             COALESCE(SUM(CASE WHEN LADI_CL.DF != 0 THEN LADI_CL.OUT_STANDING ELSE NULL END), " +
//            "                      0)                                                                         AS DF_OUTSTANDING, " +
//            "             COALESCE(SUM(LADI_CL.DF), 0)                                                        AS DF_ACC, " +
//            "             COALESCE(SUM(CASE WHEN LADI_CL.SS != 0 THEN LADI_CL.OUT_STANDING ELSE NULL END), " +
//            "                      0)                                                                         AS SS_OUTSTANDING, " +
//            "             COALESCE(SUM(LADI_CL.SS), 0)                                                        AS SS_ACC, " +
//            "             COALESCE(SUM(CASE WHEN LADI_CL.SMA != 0 THEN LADI_CL.OUT_STANDING ELSE NULL END), " +
//            "                      0)                                                                         AS SMA_OUTSTANDING, " +
//            "             COALESCE(SUM(LADI_CL.SMA), 0)                                                       AS SMA_ACC, " +
//            "             COALESCE(SUM(CASE WHEN LADI_CL.UC != 0 THEN LADI_CL.OUT_STANDING ELSE NULL END), " +
//            "                      0)                                                                         AS UC_OUTSTANDING, " +
//            "             COALESCE(SUM(LADI_CL.UC), 0)                                                        AS UC_ACC " +
//            "      FROM CUSTOMER_BASIC_INFO_ENTITY CBIE " +
//            "             LEFT JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON CBIE.ID = LABI.CUSTOMER_ID " +
//            "             LEFT JOIN LOAN_ACCOUNT_DISTRIBUTION_INFO LADI_EX " +
//            "               ON LABI.ID = LADI_EX.LOAN_ACCOUNT_BASIC_INFO_ID AND LADI_EX.LATEST = '1' AND " +
//            "                  TO_CHAR(LADI_EX.CREATED_DATE, 'yyyy-mm') = :monthTxt " +
//            "             LEFT JOIN (SELECT * " +
//            "                        FROM (SELECT LADI.LOAN_ACCOUNT_BASIC_INFO_ID, OUT_STANDING, LAI.ASSET_CLASSIFICATION " +
//            "                              FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI " +
//            "                                     JOIN LOAN_ACCOUNT_INFO LAI " +
//            "                                       ON LAI.LOAN_ACCOUNT_BASIC_INFO_ID = LADI.LOAN_ACCOUNT_BASIC_INFO_ID AND " +
//            "                                          LAI.ASSET_CLASSIFICATION IN ('UC', 'SMA', 'SS', 'DF', 'BL') " +
//            "                              WHERE TO_CHAR(LADI.CREATED_DATE, 'yyyy-mm') = :monthTxt " +
//            "                                AND LADI.LATEST = '1') " +
//            "                            PIVOT ( " +
//            "                              COUNT(*) for ASSET_CLASSIFICATION in ('UC' uc, 'SMA' sma, 'SS' ss, 'DF' df, 'BL' bl) " +
//            "                            )) LADI_CL ON LADI_EX.LOAN_ACCOUNT_BASIC_INFO_ID = LADI_CL.LOAN_ACCOUNT_BASIC_INFO_ID " +
//            "             LEFT JOIN (SELECT LADI.LOAN_ACCOUNT_BASIC_INFO_ID, OUT_STANDING " +
//            "                        FROM LOAN_ACCOUNT_DISTRIBUTION_INFO LADI " +
//            "                               JOIN DPDBUCKET_ENTITY dpd ON dpd.BUCKET_NAME = LADI.DPD_BUCKET " +
//            "                               JOIN PAR_ACC_RULE_LOAN_DPD_BUCKET_ENTITY_LIST pr ON dpd.ID = pr.DPD_BUCKET_ENTITY_LIST_ID " +
//            "                        WHERE LADI.LATEST = '1' " +
//            "                          AND TO_CHAR(LADI.CREATED_DATE, 'yyyy-mm') = :monthTxt) LADI_PAR " +
//            "               ON LADI_EX.LOAN_ACCOUNT_BASIC_INFO_ID = LADI_PAR.LOAN_ACCOUNT_BASIC_INFO_ID " +
//            "      WHERE CBIE.TYPE_ACCOUNT = 'Loan' " +
//            "      GROUP BY CBIE.TYPE_ACCOUNT, LADI_EX.PRODUCT_GROUP " +
//            "      UNION ALL " +
//            "      SELECT CBIE.TYPE_ACCOUNT                                   AS ACCOUNT_TYPE, " +
//            "             MAX('NULL')                                         AS PRODUCT_GROUP, " +
//            "             COALESCE(SUM(CADI_EX.OUTSTANDING_AMOUNT), 0)        AS TOTAL_EXPOSURE_OUTSTANDING, " +
//            "             COALESCE(ROUND(100 * RATIO_TO_REPORT(SUM(CADI_EX.OUTSTANDING_AMOUNT))OVER (), 2), " +
//            "                      0)                                         AS TOTAL_EXPOSURE_PERCENTAGE, " +
//            "             COUNT(DISTINCT CADI_EX.CARD_ACCOUNT_BASIC_INFO_ID)  AS TOTAL_EXPOSURE_ACCOUNT, " +
//            "             COALESCE(SUM(CADI_CL.OUTSTANDING_AMOUNT), 0)        AS TOTAL_CL_OUTSTANDING, " +
//            "             COUNT(DISTINCT CADI_CL.CARD_ACCOUNT_BASIC_INFO_ID)  AS TOTAL_CL_ACCOUNT, " +
//            "             COALESCE(ROUND(100 * RATIO_TO_REPORT(SUM(CADI_CL.OUTSTANDING_AMOUNT)) OVER (), 2), " +
//            "                      0)                                         AS TOTAL_CL_PERCENTAGE, " +
//            "             COALESCE(SUM(CADI_PAR.OUTSTANDING_AMOUNT), 0)       AS TOTAL_PAR_OUTSTANDING, " +
//            "             COUNT(DISTINCT CADI_PAR.CARD_ACCOUNT_BASIC_INFO_ID) AS TOTAL_PAR_ACCOUNT, " +
//            "             COALESCE(ROUND(100 * RATIO_TO_REPORT(SUM(CADI_PAR.OUTSTANDING_AMOUNT)) OVER (), 2), " +
//            "                      0)                                         AS TOTAL_PAR_PERCENTAGE, " +
//            "             COALESCE(SUM(CASE WHEN CADI_CL.BL != 0 THEN CADI_CL.OUTSTANDING_AMOUNT ELSE NULL END), " +
//            "                      0)                                         AS BL_OUTSTANDING, " +
//            "             COALESCE(SUM(CADI_CL.BL), 0)                        AS BL_ACC, " +
//            "             COALESCE(SUM(CASE WHEN CADI_CL.DF != 0 THEN CADI_CL.OUTSTANDING_AMOUNT ELSE NULL END), " +
//            "                      0)                                         AS DF_OUTSTANDING, " +
//            "             COALESCE(SUM(CADI_CL.DF), 0)                        AS DF_ACC, " +
//            "             COALESCE(SUM(CASE WHEN CADI_CL.SS != 0 THEN CADI_CL.OUTSTANDING_AMOUNT ELSE NULL END), " +
//            "                      0)                                         AS SS_OUTSTANDING, " +
//            "             COALESCE(SUM(CADI_CL.SS), 0)                        AS SS_ACC, " +
//            "             COALESCE(SUM(CASE WHEN CADI_CL.SMA != 0 THEN CADI_CL.OUTSTANDING_AMOUNT ELSE NULL END), " +
//            "                      0)                                         AS SMA_OUTSTANDING, " +
//            "             COALESCE(SUM(CADI_CL.SMA), 0)                       AS SMA_ACC, " +
//            "             COALESCE(SUM(CASE WHEN CADI_CL.UC != 0 THEN CADI_CL.OUTSTANDING_AMOUNT ELSE NULL END), " +
//            "                      0)                                         AS UC_OUTSTANDING, " +
//            "             COALESCE(SUM(CADI_CL.UC), 0)                        AS UC_ACC " +
//            "      FROM CUSTOMER_BASIC_INFO_ENTITY CBIE " +
//            "             LEFT JOIN CARD_ACCOUNT_BASIC_INFO LABI ON CBIE.ID = LABI.CUSTOMER_ID " +
//            "             LEFT JOIN CARD_ACCOUNT_DISTRIBUTION_INFO CADI_EX " +
//            "               ON LABI.ID = CADI_EX.CARD_ACCOUNT_BASIC_INFO_ID AND CADI_EX.LATEST = '1' AND " +
//            "                  TO_CHAR(CADI_EX.CREATED_DATE, 'yyyy-mm') = :monthTxt " +
//            "             LEFT JOIN (SELECT * " +
//            "                        FROM (SELECT CADI.CARD_ACCOUNT_BASIC_INFO_ID, OUTSTANDING_AMOUNT, CBIE_CL.CL_STATUS " +
//            "                              FROM CUSTOMER_BASIC_INFO_ENTITY CBIE_CL " +
//            "                                     LEFT JOIN CARD_ACCOUNT_BASIC_INFO CABI_CL ON CABI_CL.CUSTOMER_ID = CBIE_CL.ID " +
//            "                                     LEFT JOIN CARD_ACCOUNT_DISTRIBUTION_INFO CADI " +
//            "                                       ON CABI_CL.ID = CADI.CARD_ACCOUNT_BASIC_INFO_ID AND CADI.LATEST = '1' " +
//            "                                            AND TO_CHAR(CADI.CREATED_DATE, 'yyyy-mm') = :monthTxt " +
//            "                              WHERE CBIE_CL.CL_STATUS IN ('UC', 'SMA', 'SS', 'DF', 'BL')) " +
//            "                            PIVOT ( " +
//            "                              COUNT(*) for CL_STATUS in ('UC' uc, 'SMA' sma, 'SS' ss, 'DF' df, 'BL' bl) " +
//            "                            )) CADI_CL ON CADI_EX.CARD_ACCOUNT_BASIC_INFO_ID = CADI_CL.CARD_ACCOUNT_BASIC_INFO_ID " +
//            "             LEFT JOIN (SELECT CADI.CARD_ACCOUNT_BASIC_INFO_ID, OUTSTANDING_AMOUNT " +
//            "                        FROM CARD_ACCOUNT_DISTRIBUTION_INFO CADI " +
//            "                               JOIN AGE_CODE dpd ON dpd.NAME = CADI.AGE_CODE " +
//            "                               JOIN PAR_LOGIC_SETUP_AGE_CODE_LIST pr ON dpd.ID = pr.AGE_CODE_LIST_ID " +
//            "                        WHERE CADI.LATEST = '1' " +
//            "                          AND TO_CHAR(CADI.CREATED_DATE, 'yyyy-mm') = :monthTxt) CADI_PAR " +
//            "               ON CADI_EX.CARD_ACCOUNT_BASIC_INFO_ID = CADI_PAR.CARD_ACCOUNT_BASIC_INFO_ID " +
//            "      WHERE CBIE.TYPE_ACCOUNT = 'Card' " +
//            "      GROUP BY CBIE.TYPE_ACCOUNT) " +
//            "GROUP BY ROLLUP (ACCOUNT_TYPE, PRODUCT_GROUP)", nativeQuery = true)
//    Object[][] getLoanAndCardReport(@Param("monthTxt") String month);


}

