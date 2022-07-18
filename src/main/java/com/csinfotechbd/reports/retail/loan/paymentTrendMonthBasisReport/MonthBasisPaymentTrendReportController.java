package com.csinfotechbd.reports.retail.loan.paymentTrendMonthBasisReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/collection/report/retail/loan/month-basis-payment-trend")
public class MonthBasisPaymentTrendReportController {

    @Autowired
    private MonthBasisPaymentTrendReportService monthBasisPaymentTrendReportService;

    @GetMapping("/view")
    public String view(Model model){
        model.addAttribute("reportTitle", "Month on month basis payment trend");
        return "collection/reports/retail/loan/paymentTrendMonthBasisReport/view";
    }

    @ResponseBody
    @GetMapping("/search")
    public MonthBasisPaymentTrendReportDto search(@RequestParam double lowerLimit, @RequestParam double upperLimit, @RequestParam String month){
        MonthBasisPaymentTrendReportDto data = monthBasisPaymentTrendReportService.searchData(lowerLimit, upperLimit, month);
        return data;
    }
}
