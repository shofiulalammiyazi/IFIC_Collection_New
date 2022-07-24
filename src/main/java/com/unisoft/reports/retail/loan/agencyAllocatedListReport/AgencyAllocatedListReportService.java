package com.unisoft.reports.retail.loan.agencyAllocatedListReport;


import com.unisoft.collection.distribution.agencyAllocation.loan.LoanAgencyDistributionInfo;
import com.unisoft.collection.distribution.agencyAllocation.loan.LoanAgencyDistributionRepository;
import com.unisoft.utillity.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AgencyAllocatedListReportService {

    @Autowired
    private LoanAgencyDistributionRepository loanAgencyDistributionRepository;
    @Autowired
    private DateUtils dateUtils;

    public List<LoanAgencyDistributionInfo> getAllAgencyDistributionList(){
            Date startDate = dateUtils.getMonthStartDate();
            Date endDate = dateUtils.getMonthEndDate();
        return loanAgencyDistributionRepository.findByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqualAndLatest(startDate,endDate,"1");
    }
}
