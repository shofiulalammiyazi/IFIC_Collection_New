package com.unisoft.reports.retail.loan.branchClReportWise;

import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchWiseClReportRepository extends JpaRepository<CustomerBasicInfoEntity, Long> {


    @Query(value = "SELECT  " +
            "LL.BRANCH_NAME, LL.BL_ACC, COALESCE(LL.BL_OS, 0 ) AS BL_OS, " +
            "LL.DF_ACC, COALESCE(LL.DF_OS, 0) AS DF_OS, " +
            "LL.SS_ACC, COALESCE(LL.SS_OS, 0) AS SS_OS, " +
            "LL.SMA_ACC, COALESCE(LL.SMA_OS, 0) AS SMA_OS, " +
            "LL.UC_ACC, COALESCE(LL.UC_OS, 0) AS UC_OS, " +
            "   LL.BL_ACC +  LL.DF_ACC + LL.SS_ACC + LL.SMA_ACC + LL.UC_ACC AS TOTAL_ACC, " +
            "   (COALESCE(LL.BL_OS, 0 ) +  COALESCE(LL.DF_OS, 0) + COALESCE(LL.SS_OS, 0) + COALESCE(LL.SMA_OS, 0) + COALESCE(LL.UC_OS, 0)) AS TOTAL_OS, " +
            "   (LL.BL_ACC +  LL.DF_ACC + LL.SS_ACC) AS CL_ACC, " +
            "   (COALESCE(LL.BL_OS, 0 ) +  COALESCE(LL.DF_OS, 0) + COALESCE(LL.SS_OS, 0)) AS CL_OS, " +
            "   ( (COALESCE(LL.BL_OS, 0 ) +  COALESCE(LL.DF_OS, 0) + COALESCE(LL.SS_OS, 0)) / (COALESCE(LL.BL_OS, 0 ) +  COALESCE(LL.DF_OS, 0) + COALESCE(LL.SS_OS, 0) + COALESCE(LL.SMA_OS, 0) + COALESCE(LL.UC_OS, 0)) )*100 AS CL_PERC " +
            "FROM  " +
            "(SELECT * FROM " +
            "( " +
            "SELECT BRANCH_NAME, CL_STATUS, OUTSTANDING " +
            "FROM LOAN_LISTING   " +
            "WHERE TO_CHAR(LISTING_DOWN_DATE, 'YYYY-mm')= decode(?1, null, TO_CHAR(LISTING_DOWN_DATE, 'YYYY-mm'), ?1) " +
            "AND BRANCH_CODE IN decode(?2, null, BRANCH_CODE, ?2) " +
            ") " +
            "PIVOT " +
            "( " +
            "    COUNT(CL_STATUS) AS ACC, SUM (OUTSTANDING) AS OS " +
            "    FOR CL_STATUS IN ('BL' AS BL,'DF' AS DF,'SS' AS SS,'SMA' AS SMA,'UC' AS UC) " +
            ")) LL", nativeQuery = true)
    List<BranchWiseCLReport>getBWCLReport(String date, List<String> branchList);
//    List<BranchWiseCLReport>getBWCLReport( String date);


//    @Query(value = "" +
//            "SELECT CBIE.REGION, " +
//            "       CBIE.BRANCH_NAME, " +
//            "       COALESCE(SUM(CASE WHEN ld_cl.BL != 0 THEN ld_cl.OUT_STANDING ELSE NULL END), 0) AS BL_OUTSTANDING, " +
//            "       COALESCE(SUM(ld_cl.BL), 0)                                                      AS BL_ACC, " +
//            "       COALESCE(SUM(CASE WHEN ld_cl.DF != 0 THEN ld_cl.OUT_STANDING ELSE NULL END), " +
//            "                0)                                                                     AS DF_OUTSTANDING, " +
//            "       COALESCE(SUM(ld_cl.DF), 0)                                                      AS DF_ACC, " +
//            "       COALESCE(SUM(CASE WHEN ld_cl.SS != 0 THEN ld_cl.OUT_STANDING ELSE NULL END), " +
//            "                0)                                                                     AS SS_OUTSTANDING, " +
//            "       COALESCE(SUM(ld_cl.SS), 0)                                                      AS SS_ACC, " +
//            "       COALESCE(SUM(CASE WHEN ld_cl.SMA != 0 THEN ld_cl.OUT_STANDING ELSE NULL END), " +
//            "                0)                                                                     AS SMA_OUTSTANDING, " +
//            "       COALESCE(SUM(ld_cl.SMA), 0)                                                     AS SMA_ACC, " +
//            "       COALESCE(SUM(CASE WHEN ld_cl.UC != 0 THEN ld_cl.OUT_STANDING ELSE NULL END), " +
//            "                0)                                                                     AS UC_OUTSTANDING, " +
//            "       COALESCE(SUM(ld_cl.UC), 0)                                                      AS UC_ACC, " +
//            "       COALESCE(SUM(ld_cl.OUT_STANDING), 0)                                            AS TOTAL_CL_OUTSTANDING, " +
//            "       count(DISTINCT ld_cl.LOAN_ACCOUNT_BASIC_INFO_ID)                                AS TOTAL_CL_ACCOUNT, " +
//            "       COALESCE(ROUND(100 * RATIO_TO_REPORT(SUM(ld_cl.OUT_STANDING)) " +
//            "           OVER (PARTITION BY GROUPING(CBIE.BRANCH_NAME), GROUPING(CBIE.REGION)), " +
//            "             2), 0)                                                                     AS TOTAL_CL_PERCENTAGE " +
//            "FROM CUSTOMER_BASIC_INFO_ENTITY CBIE " +
//            "       LEFT JOIN LOAN_ACCOUNT_BASIC_INFO LABI ON CBIE.ID = LABI.CUSTOMER_ID " +
//            "       LEFT JOIN (SELECT * " +
//            "                  FROM (SELECT ld.LOAN_ACCOUNT_BASIC_INFO_ID, OUT_STANDING, la.ASSET_CLASSIFICATION " +
//            "                        FROM LOAN_ACCOUNT_DISTRIBUTION_INFO ld " +
//            "                               JOIN LOAN_ACCOUNT_INFO la " +
//            "                                 ON la.LOAN_ACCOUNT_BASIC_INFO_ID = ld.LOAN_ACCOUNT_BASIC_INFO_ID AND " +
//            "                                    la.ASSET_CLASSIFICATION IN ('UC', 'SMA', 'SS', 'DF', 'BL') " +
//            "                        WHERE ld.CREATED_DATE BETWEEN :startDate AND :endDate " +
//            "                          AND ld.LATEST = '1') " +
//            "                      PIVOT ( " +
//            "                        count(*) for ASSET_CLASSIFICATION in ('UC' uc, 'SMA' sma, 'SS' ss, 'DF' df, 'BL' bl) " +
//            "                      )) ld_cl ON LABI.ID = ld_cl.LOAN_ACCOUNT_BASIC_INFO_ID " +
//            "WHERE CBIE.REGION IN(:regions) " +
//            "  AND CBIE.BRANCH_CODE IN(:branchCodes) " +
//            "  AND LABI.LOCATION IN(:divisions) " +
//            "GROUP BY ROLLUP (CBIE.REGION, CBIE.BRANCH_NAME) " +
//            "ORDER BY CBIE.REGION, " +
//            "         CBIE.BRANCH_NAME", nativeQuery = true)
//    Object[][] getReportDtos(@Param("regions") List<String> regions,
//                             @Param("branchCodes") List<String> branchCodes,
//                             @Param("divisions") List<String> divisions,
//                             @Param("startDate") Date startDate,
//                             @Param("endDate") Date endDate);



}
