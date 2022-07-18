package com.csinfotechbd.collection.distribution.loan;

import com.csinfotechbd.collection.distribution.agencyAllocation.loan.LoanAgencyDistributionInfo;
import com.csinfotechbd.collection.distribution.agencyAllocation.loan.LoanAgencyDistributionRepository;
import com.csinfotechbd.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.csinfotechbd.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.csinfotechbd.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class LoanAccountSearchService {

    private final LoanAccountDistributionRepository loanAccountDistributionRepository;

    private final LoanAgencyDistributionRepository loanAgencyDistributionRepository;

    private final DateUtils dateUtils;

    public LoanAccountDistributionInfo findFromLoanAccountDistributionInfo(LoanAccountBasicInfo loanAccountBasicInfo) {
        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthEndDate();
        return loanAccountDistributionRepository
                .findFirstByCreatedDateIsBetweenAndLoanAccountBasicInfoAndLatestOrderByCreatedDateDesc(
                        startDate, endDate, loanAccountBasicInfo, "1");
    }

    public LoanAccountDistributionInfo findLatestLoanAccountDistributionInfo(LoanAccountBasicInfo loanAccountBasicInfo) {
        return loanAccountDistributionRepository
                .findFirstByLoanAccountBasicInfoOrderByCreatedDateDesc(loanAccountBasicInfo);
    }

    public LoanAgencyDistributionInfo findFromLoanAgencyDistributionInfo(LoanAccountBasicInfo loanAccountBasicInfo) {
        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthEndDate();
        return loanAgencyDistributionRepository
                .findFirstByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqualAndLoanAccountBasicInfoAndLatestOrderByCreatedDateDesc(
                        startDate, endDate, loanAccountBasicInfo, "1");
    }

}
