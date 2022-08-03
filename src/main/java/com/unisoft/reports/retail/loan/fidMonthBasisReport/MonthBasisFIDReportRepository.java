package com.unisoft.reports.retail.loan.fidMonthBasisReport;

import com.unisoft.collection.settings.loansectorcode.SectorCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

@Repository
public interface MonthBasisFIDReportRepository extends JpaRepository<SectorCodeEntity, Long> {
/*
    SELECT COUNT(*) as TOTAL,
        COUNT(CASE WHEN LOWER(LL.fid) = 'yes' THEN 1 END) AS REPORT_FID,
        ROUND(COALESCE(SUM(CASE WHEN LOWER(LL.fid) = 'yes'  THEN LL.outstanding ELSE 0 END), 0) , 2) as FID_SUM,
        COUNT(CASE WHEN LOWER(SCE.GROUP_NAME) = 'salaried' THEN 1 END) AS SALARIED,
        COUNT(CASE WHEN LOWER(SCE.GROUP_NAME) = 'landlord' THEN 1 END) AS LANDLORD,
        COUNT(CASE WHEN LOWER(SCE.GROUP_NAME) = 'businessman' THEN 1 END) AS BUSINESSMAN
    from LOAN_LISTING LL  LEFT JOIN SECTOR_CODE_ENTITY SCE on  LL.acc_no = SCE.ACCOUNT_NO;*/

    @Query(value = "" +
            " SELECT COUNT(*) as TOTAL, "+
            " COUNT(CASE WHEN LOWER(LL.fid) = 'yes' THEN 1 END) AS REPORT_FID, "+
            " ROUND(COALESCE(SUM(CASE WHEN LOWER(LL.fid) = 'yes'  THEN LL.outstanding ELSE 0 END), 0) , 2) as FID_SUM, "+
            " COUNT(CASE WHEN LOWER(SCE.GROUP_NAME) = 'salaried' THEN 1 END) AS SALARIED, "+
            " COUNT(CASE WHEN LOWER(SCE.GROUP_NAME) = 'landlord' THEN 1 END) AS LANDLORD, "+
            " COUNT(CASE WHEN LOWER(SCE.GROUP_NAME) = 'businessman' THEN 1 END) AS BUSINESSMAN "+
            " FROM LOAN_LISTING LL  LEFT JOIN SECTOR_CODE_ENTITY SCE on  LL.acc_no = SCE.ACCOUNT_NO "+
            " WHERE LL.LISTING_DOWN_DATE <= (TRUNC(:endDate) + INTERVAL '1' DAY - INTERVAL '1' SECOND) ", nativeQuery = true)
    List<Tuple> getReport(@Param("endDate") Date endDate);
}
