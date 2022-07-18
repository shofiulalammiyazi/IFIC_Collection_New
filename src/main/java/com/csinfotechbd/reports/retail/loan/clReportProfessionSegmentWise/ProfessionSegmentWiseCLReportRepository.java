package com.csinfotechbd.reports.retail.loan.clReportProfessionSegmentWise;

import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

@Repository
public interface ProfessionSegmentWiseCLReportRepository extends JpaRepository<CustomerBasicInfoEntity, Long> {


    @Query(value = "SELECT LL.SEGMENT   AS segment,  " +
            "      COUNT(LL.ACC_NO)    AS totalAcc,  " +
            "      SUM(LL.OUTSTANDING) AS totalOs,  " +
            "      COUNT(CASE  " +
            "              WHEN (LL.LOAN_ACC_PAR = 'Yes' AND  " +
            "                    (LL.CL_STATUS = 'SMA' OR LL.CL_STATUS = 'SS' OR LL.CL_STATUS = 'DF' OR LL.CL_STATUS = 'BL'))  " +
            "                      THEN 1 END)            AS parAccTotal,  " +
            "      SUM(CASE  " +
            "            WHEN (LL.LOAN_ACC_PAR = 'Yes' AND  " +
            "                  (LL.CL_STATUS = 'SMA' OR LL.CL_STATUS = 'SS' OR LL.CL_STATUS = 'DF' OR LL.CL_STATUS = 'BL'))  " +
            "                    THEN LL.OUTSTANDING END) AS parOsTotal,  " +
            "      COUNT(CASE  " +
            "              WHEN (LL.CL_STATUS = 'SS' OR LL.CL_STATUS = 'DF' OR LL.CL_STATUS = 'BL')  " +
            "                      THEN LL.ACC_NO END)    AS clAccTotal,  " +
            "      SUM(CASE  " +
            "            WHEN (LL.CL_STATUS = 'SS' OR LL.CL_STATUS = 'DF' OR LL.CL_STATUS = 'BL')  " +
            "                    THEN LL.OUTSTANDING END) AS clOsTotal  " +
            "FROM LOAN_LISTING LL  " +
            "LEFT JOIN SECTOR_CODE_ENTITY SCE ON LL.ACC_NO = SCE.ACCOUNT_NO " +
            "WHERE LL.PRODUCT_NAME = ?1  " +
            "GROUP BY LL.SEGMENT  " +
            "ORDER BY LL.SEGMENT", nativeQuery = true)
    List<Tuple> getProfessionSegmentReport(String ProductGroup);


//    @Query(value = "SELECT " +
//            " LL.PRODUCT_NAME, LL.SEGMENT, " +
//            "     (LL.BL_ACC +  LL.DF_ACC + LL.SS_ACC + LL.SMA_ACC + LL.UC_ACC) AS TOTAL_EXPOSURE_AC, " +
//            "     (COALESCE(LL.BL_OS, 0 ) +  COALESCE(LL.DF_OS, 0) + COALESCE(LL.SS_OS, 0) + COALESCE(LL.SMA_OS, 0) + COALESCE(LL.UC_OS, 0)) AS TOTAL_EXPOSURE_OS, " +
//            "     (COALESCE(LL.BL_OS, 0 ) +  COALESCE(LL.DF_OS, 0) + COALESCE(LL.SS_OS, 0)) AS CL_OS, " +
//            "     (LL.BL_ACC +  LL.DF_ACC + LL.SS_ACC) AS CL_ACC,   " +
//            "     ( (COALESCE(LL.BL_OS, 0 ) +  COALESCE(LL.DF_OS, 0) + COALESCE(LL.SS_OS, 0)) / (COALESCE(LL.BL_OS, 0 ) +  COALESCE(LL.DF_OS, 0) + COALESCE(LL.SS_OS, 0) + COALESCE(LL.SMA_OS, 0) + COALESCE(LL.UC_OS, 0)) )*100 AS CL_PERC " +
//            "  FROM " +
//            "  (SELECT * FROM " +
//            "  (   " +
//            "  SELECT PRODUCT_NAME, CL_STATUS, OUTSTANDING , SEGMENT " +
//            "  FROM LOAN_LISTING " +
//            "  WHERE PRODUCT_NAME IN decode(?1,null,PRODUCT_NAME,?1) " +
//            "  )   " +
//            "  PIVOT " +
//            "  (  " +
//            "      COUNT(CL_STATUS) AS ACC, SUM (OUTSTANDING) AS OS " +
//            "      FOR CL_STATUS IN ('BL' AS BL,'DF' AS DF,'SS' AS SS,'SMA' AS SMA,'UC' AS UC) " +
//            "  )) LL", nativeQuery = true)
//    List<ProfessionSegmentReport> getProfessionSegmentReport(List<String>productName);


//    @Query(value = "" +
//            "SELECT cb.OCCUPATION                                      AS SEGMENT, " +
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
//            "GROUP BY cb.OCCUPATION", nativeQuery = true)
//    Object[][] getReportDtos(@Param("productGroup") String productGroup);


}
