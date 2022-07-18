package com.csinfotechbd.reports.card.dailyData;

import com.csinfotechbd.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionService;
import com.csinfotechbd.reports.retail.loan.listingReport.ListingReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/collection/report/card/dailydata/")
public class DailyDataReportController {


    @Autowired
    private LoanAccountDistributionService loanAccountDistributionService;

    @Autowired
    private DailyDataReportService dailyDataReportService;

    @GetMapping("view")
    public String view(Model model){
        model.addAttribute("dailyDataReportList", dailyDataReportService.getDailyReportData());

        return "collection/reports/card/dailyData/view";
    }
}
