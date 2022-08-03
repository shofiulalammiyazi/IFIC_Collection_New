package com.unisoft.reports.retail.loan.sourcingChannelWiseReport;

import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface SourcingChannelWiseReportRepository extends JpaRepository<CustomerBasicInfoEntity, Long> {


    @Query(value = "SELECT LL.SOURCE_CHANNEL                      AS sourceChannel, " +
            "       COUNT(LL.ACC_NO)                       AS sourceTotalAcc, " +
            "       SUM(LL.OUTSTANDING)                    AS sourceOs, " +
            "       COUNT(CASE " +
            "               WHEN (LL.LOAN_ACC_PAR = 'Yes' AND " +
            "                     (LL.CL_STATUS = 'SMA' OR LL.CL_STATUS = 'SS' OR LL.CL_STATUS = 'DF' OR LL.CL_STATUS = 'BL')) " +
            "                       THEN 1 END)            AS parAccTotal, " +
            "       SUM(CASE " +
            "             WHEN (LL.LOAN_ACC_PAR = 'Yes' AND " +
            "                   (LL.CL_STATUS = 'SMA' OR LL.CL_STATUS = 'SS' OR LL.CL_STATUS = 'DF' OR LL.CL_STATUS = 'BL')) " +
            "                     THEN LL.OUTSTANDING END) AS parOsTotal, " +
            "       COUNT(CASE " +
            "               WHEN (LL.CL_STATUS = 'SS' OR LL.CL_STATUS = 'DF' OR LL.CL_STATUS = 'BL') " +
            "                       THEN LL.ACC_NO END)    AS clAccTotal, " +
            "       SUM(CASE " +
            "             WHEN (LL.CL_STATUS = 'SS' OR LL.CL_STATUS = 'DF' OR LL.CL_STATUS = 'BL') " +
            "                     THEN LL.OUTSTANDING END) AS clOsTotal " +
            "FROM LOAN_LISTING LL " +
            "WHERE LL.PRODUCT_GROUP = :productGroup " +
            "   OR TO_CHAR(LL.LISTING_DOWN_DATE, 'YYYY-MM') = :pMonth " +
            "GROUP BY LL.SOURCE_CHANNEL " +
            "ORDER BY LL.SOURCE_CHANNEL ", nativeQuery = true)
    List<Tuple> getReportData(@Param("productGroup") String productGroup, @Param("pMonth") String pMonth);

//    @Query(value = "" +
//            "SELECT cb.SOURCING_CHANNEL                                AS SOURCING_CHANNEL, " +
//            "       COALESCE(SUM(ld_ex.OUT_STANDING), 0)               AS TOTAL_EXPOSURE_OUTSTANDING, " +
//            "       count(DISTINCT ld_ex.LOAN_ACCOUNT_BASIC_INFO_ID)   AS TOTAL_EXPOSURE_ACCOUNT, " +
//            "       COALESCE(SUM(ld_cl.OUT_STANDING), 0)               AS TOTAL_CL_OUTSTANDING, " +
//            "       COALESCE(SUM(ld_cl.OUT_STANDING), 0) * 100.0 / " +
//            "       COALESCE(SUM(SUM(ld_cl.OUT_STANDING)) over (), 1)  AS TOTAL_CL_PERCENTAGE, " +
//            "       count(DISTINCT ld_cl.LOAN_ACCOUNT_BASIC_INFO_ID)   AS TOTAL_CL_ACCOUNT, " +
//            "       COALESCE(SUM(ld_par.OUT_STANDING), 0)              AS TOTAL_PAR_OUTSTANDING, " +
//            "       COALESCE(SUM(ld_par.OUT_STANDING), 0) * 100.0 / " +
//            "       COALESCE(SUM(SUM(ld_par.OUT_STANDING)) over (), 1) AS TOTAL_PAR_PERCENTAGE, " +
//            "       count(DISTINCT ld_par.LOAN_ACCOUNT_BASIC_INFO_ID)  AS TOTAL_PAR_ACCOUNT " +
//            "FROM CUSTOMER_BASIC_INFO_ENTITY cb " +
//            "       JOIN LOAN_ACCOUNT_BASIC_INFO lb ON cb.ID = lb.CUSTOMER_ID " +
//            "       JOIN LOAN_ACCOUNT_DISTRIBUTION_INFO ld_ex " +
//            "         ON lb.ID = ld_ex.LOAN_ACCOUNT_BASIC_INFO_ID AND ld_ex.PRODUCT_GROUP = :productGroup AND " +
//            "            ld_ex.LATEST = '1' AND ld_ex.CREATED_DATE BETWEEN :startDate AND :endDate " +
//            "       LEFT JOIN (SELECT ld.LOAN_ACCOUNT_BASIC_INFO_ID, OUT_STANDING " +
//            "                  FROM LOAN_ACCOUNT_DISTRIBUTION_INFO ld " +
//            "                         JOIN LOAN_ACCOUNT_INFO la ON la.LOAN_ACCOUNT_BASIC_INFO_ID = ld.LOAN_ACCOUNT_BASIC_INFO_ID AND " +
//            "                                                      la.ASSET_CLASSIFICATION IN ('UC', 'SMA', 'SS', 'DF', 'BL') " +
//            "                  WHERE ld.PRODUCT_GROUP = :productGroup " +
//            "                    AND ld.LATEST = '1' " +
//            "                    AND ld.CREATED_DATE BETWEEN :startDate AND :endDate) ld_cl " +
//            "         ON lb.ID = ld_cl.LOAN_ACCOUNT_BASIC_INFO_ID " +
//            "       LEFT JOIN (SELECT ld.LOAN_ACCOUNT_BASIC_INFO_ID, OUT_STANDING " +
//            "                  FROM LOAN_ACCOUNT_DISTRIBUTION_INFO ld " +
//            "                         JOIN DPDBUCKET_ENTITY dpd ON dpd.BUCKET_NAME = ld.DPD_BUCKET " +
//            "                         JOIN PAR_ACC_RULE_LOAN_DPD_BUCKET_ENTITY_LIST pr ON dpd.ID = pr.DPD_BUCKET_ENTITY_LIST_ID " +
//            "                  WHERE ld.PRODUCT_GROUP = :productGroup " +
//            "                    AND ld.LATEST = '1' " +
//            "                    AND ld.CREATED_DATE BETWEEN :startDate AND :endDate) ld_par " +
//            "         ON lb.ID = ld_par.LOAN_ACCOUNT_BASIC_INFO_ID " +
//            "GROUP BY cb.SOURCING_CHANNEL", nativeQuery = true)
//    Object[][] getReportDtos(@Param("productGroup") String productGroup, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
