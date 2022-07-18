package com.csinfotechbd.retail.loan.dataEntry.distribution.auto;

import com.csinfotechbd.common.CommonService;

import java.util.List;
import java.util.Map;

public interface LoanAutoDistributionService extends CommonService<LoanAutoDistributionInfo> {
    Map<String, Object> distributeAccounts(List<LoanAutoDistributionInfo> delinquentAccountList);

    List<LoanAutoDistributionInfo> getCurrentMonthDelinquentAccountsFromClientApi();

    List<LoanAutoDistributionDto> getCurrentMonthUnallocatedList();

    List<LoanAutoDistributionDto> getCurrentMonthAllocatedList();

    List<LoanAutoDistributionViewModel> getCurrentMonthAutoDistributionSummary();

    void temporarilyDistributeDelinquentAccounts();

//    void saveAll(List<LoanAutoDistributionInfo> delinquentAccounts);
//
//    void deleteAll();
}
