package com.csinfotechbd.reports.retail.loan.paymentTrendDailyBasisReport;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/collection/report/retail/loan/daily-basis-payment-trend")
public class DailyBasisPaymentTrendReportController {

    private final DailyBasisPaymentTrendReportService service;

    @GetMapping("/view")
    public String view(Model model) {
        model.addAttribute("reportTitle", "Daily Payment Trend Vs Last Month's Payment");
        return "collection/reports/retail/loan/paymentTrendDailyBasisReport/view";
    }

    @GetMapping("/report/current")
    @ResponseBody
    public Map<String, Object> getDailyReport(String currentDate, String prevDate) {
        return service.getReport(currentDate, prevDate);

    }

//    @GetMapping("/report/previous")
//    @ResponseBody
//    public JSONObject getPreviousMonthReport(String month) {
//        return service.getPreviousMonthReport(month);
//
//    }
}
