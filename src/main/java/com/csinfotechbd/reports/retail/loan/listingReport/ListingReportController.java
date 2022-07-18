package com.csinfotechbd.reports.retail.loan.listingReport;

import com.csinfotechbd.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.csinfotechbd.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionService;
import com.csinfotechbd.loanApi.model.LoanAccDetails;
import com.csinfotechbd.loanApi.model.LoanAccInfo;
import com.csinfotechbd.loanApi.service.RetailLoanUcbApiService;
import com.csinfotechbd.utillity.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/collection/report/retail/loan/listing")
public class ListingReportController {


    @Autowired
    private LoanAccountDistributionService loanAccountDistributionService;

    @Autowired
    private ListingReportService listingReportService;

    @GetMapping("/view")
    public String view(Model model){
        model.addAttribute("listingReportList", listingReportService.getListingReportData());
        model.addAttribute("reportTitle", "Listing Report");
        return "collection/reports/retail/loan/listingReport/view";
    }
}
