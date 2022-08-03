package com.unisoft.collection.dashboard;
/*
  Created by Md.   Islam on 2/1/2020
*/

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThreadPerformance {
    private final DealerPerformanceDataService dealerPerformanceDataService;

    public void loanPerformanceCal(List<ProductWiseSummary> summaryList, String dealerPin, String dealerName) {
        Thread performanceLoanThread = new Thread(new Runnable() {
            @Override
            public void run() {
                dealerPerformanceDataService.updateLoanDealerPerformanceData(summaryList, dealerPin, dealerName);
            }
        });

        performanceLoanThread.start();
    }

    //Thread used here
    public void cardPerformanceCal(List<KpiVsAchDataModel> dataModelList, String dealerPin, String dealerName) {

        Thread performanceCalCard = new Thread(new Runnable() {
            @Override
            public void run() {
                dealerPerformanceDataService.updateCardDealerPerformanceData(dataModelList, dealerPin, dealerName);
            }
        });
        performanceCalCard.start();

    }

}
