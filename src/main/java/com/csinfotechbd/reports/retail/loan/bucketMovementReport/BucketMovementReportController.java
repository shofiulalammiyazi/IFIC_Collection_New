package com.csinfotechbd.reports.retail.loan.bucketMovementReport;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/collection/report/retail/loan/bucket-movement")
public class BucketMovementReportController {

    @GetMapping("/view")
    public String view(Model model){
        model.addAttribute("reportTitle", "Bucket movement Report (Was-IS report)");
        return "collection/reports/retail/loan/bucketMovementReport/view";
    }
}
