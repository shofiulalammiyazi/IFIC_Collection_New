package com.csinfotechbd.reports.retail.loan.clReportMonthBasis;

import com.csinfotechbd.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthBasisCLReportRepository extends JpaRepository<LoanAccountDistributionInfo, Long> {

    @Query(value = "SELECT ai.asset_classification, COUNT(bi.account_no), SUM(TO_NUMBER(di.out_standing)), " +
            "    (SELECT SUM(TO_NUMBER(di2.out_standing)) FROM loan_account_distribution_info di2 " +
            "        LEFT JOIN loan_account_basic_info bi2 ON bi2.id = di2.loan_account_basic_info_id " +
            "        LEFT JOIN loan_account_info ai2 ON ai2.loan_account_basic_info_id = bi2.id " +
            "    WHERE TO_CHAR(di2.status_date,'yyyy-mm') = ?2 AND ai2.asset_classification = ai.asset_classification) " +
            "FROM loan_account_distribution_info di " +
            "LEFT JOIN loan_account_basic_info bi ON bi.id = di.loan_account_basic_info_id " +
            "LEFT JOIN loan_account_info ai ON ai.loan_account_basic_info_id = di.loan_account_basic_info_id " +
            "WHERE TO_CHAR(di.status_date,'yyyy-mm') = ?1 " +
            "GROUP BY ai.asset_classification " +
            "ORDER BY ai.asset_classification", nativeQuery = true)
    Object[][] findAllByMonth(String startMonth, String previousMonth);
}
