package com.unisoft.reports.card.fidCardDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/collection/report/card/fidcarddetails")
public class FidCardDetailController {

//    @Autowired
//    private LoanAccountDistributionService loanAccountDistributionService;

    @Autowired
    private FidCardDetailService fidCardDetailService;

    @GetMapping("/view")
    public String view(Model model){
        //model.addAttribute("dailyDataReportList", dailyDataReportService.getDailyReportData());

        return "collection/reports/card/fidCardDetails/view";
    }

}
