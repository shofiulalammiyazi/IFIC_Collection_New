package com.csinfotechbd.reports.retail.loan.writeOffPaymentReport;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/collection/report/retail/loan/write-off-payment")
public class WriteOffPaymentReportController {

    @GetMapping("/view")
    public String view(Model model){
        model.addAttribute("reportTitle", "Write Off Payment Report");
        return "collection/reports/retail/loan/writeOffPaymentReport/view";
    }
}
