package com.csinfotechbd.reports.retail.loan.agencyAllocatedListReport;

import com.csinfotechbd.collection.distribution.agencyAllocation.loan.LoanAgencyDistributionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/collection/report/retail/loan/agency-allocated-list")
public class AgencyAllocatedListReportController {

    @Autowired
    private AgencyAllocatedListReportService agencyAllocatedListReportService;

    @GetMapping("/view")
    public String view(Model model){
        List<LoanAgencyDistributionInfo> agencyAllocationlist=agencyAllocatedListReportService.getAllAgencyDistributionList();
        model.addAttribute("reportTitle", "Agency Allocated List");
        model.addAttribute("agencyAllocationlist",agencyAllocationlist);
        return "collection/reports/retail/loan/agencyAllocatedListReport/view";
    }

}
