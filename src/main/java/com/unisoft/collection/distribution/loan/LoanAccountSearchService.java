package com.unisoft.collection.distribution.loan;

import com.unisoft.collection.distribution.agencyAllocation.loan.LoanAgencyDistributionInfo;
import com.unisoft.collection.distribution.agencyAllocation.loan.LoanAgencyDistributionRepository;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.unisoft.utillity.DateUtils;
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

    public LoanAccountDistributionInfo getByLoanAccountBasicInfoAndLatest(Long loanAccountBasicInfoId) {
        return loanAccountDistributionRepository.findByLoanAccountBasicInfoAndLatest(loanAccountBasicInfoId);
    }

    public LoanAgencyDistributionInfo findFromLoanAgencyDistributionInfo(LoanAccountBasicInfo loanAccountBasicInfo) {
        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthEndDate();
        return loanAgencyDistributionRepository
                .findFirstByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqualAndLoanAccountBasicInfoAndLatestOrderByCreatedDateDesc(
                        startDate, endDate, loanAccountBasicInfo, "1");
    }

}
