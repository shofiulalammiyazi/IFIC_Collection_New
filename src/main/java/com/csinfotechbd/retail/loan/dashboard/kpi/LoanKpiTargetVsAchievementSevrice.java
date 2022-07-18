package com.csinfotechbd.retail.loan.dashboard.kpi;

import com.csinfotechbd.collection.distribution.loan.LoanAccountDistributionRepository;
import com.csinfotechbd.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanKpiTargetVsAchievementSevrice {

    private final LoanKpiTargetVsAchievementRepository repository;
    private final LoanAccountDistributionRepository distributionRepository;
    private final DateUtils dateUtils;

    public List<LoanKpiTargetVsAchievement> getKpiAmountWiseSummary(List<String> dealerPins, Date startDate, Date endDate) {
        return repository.getKpiAmountWiseSummary(dealerPins, startDate, endDate)
                .stream().map(LoanKpiTargetVsAchievement::new).collect(Collectors.toList());
    }


    public List<LoanKpiTargetVsAchievement> getKpiAccountWiseSummary(List<String> dealerPins, Date startDate, Date endDate) {
        return repository.getKpiAccountWiseSummary(dealerPins, startDate, endDate)
                .stream().map(LoanKpiTargetVsAchievement::new).collect(Collectors.toList());
    }

    /**
     * Calculate and store dealer performance data. Performance is calculated for the month of yesterday.
     * Concurrent performance calculation ignored for two reasons.
     * 1. Only previous date payment information is available in the system
     * 2. Monthly performance is calculated at next month's first date with a view to getting a whole month data.
     * When calculating performance this way, yesterday means the last date of the previous month.
     * <p>
     * Implemented by Yasir Araphat
     * on August 12, 2021
     */
    public void updateKpiPerformanceStatusForAllDealerTillYesterday() {

        Set<String> dealerPins = distributionRepository.getDistributedDealerPinsTillYesterday();
        Date yesterday = dateUtils.getNextOrPreviousDate(new Date(), -1);
        Date startDate = dateUtils.getMonthStartDate(yesterday);
        Date endDate = dateUtils.getMonthEndDate(yesterday);

        // Clear previous data
        repository.deleteAllByCreatedDateBetween(startDate, endDate);

        // Save data
        List<LoanKpiTargetVsAchievement> allDealerPerformanceData = new LinkedList<>();
        for (String dealerPin : dealerPins) {
            List<LoanKpiTargetVsAchievement> performanceSummaryTillYesterday = getKpiAccountWiseSummary(Arrays.asList(dealerPin), startDate, endDate);
            allDealerPerformanceData.addAll(performanceSummaryTillYesterday);
        }
        allDealerPerformanceData.forEach(kpi -> kpi.setCreatedDate(yesterday));
        repository.saveAll(allDealerPerformanceData);
    }

}
