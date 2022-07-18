package com.csinfotechbd.reports.retail.loan.fidMonthBasisReport;

import com.csinfotechbd.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/collection/report/retail/loan/month-basis-fid")
public class MonthBasisFIDReportController {

    private final DateUtils dateUtils;
    private final MonthBasisFIDReportService service;


    @GetMapping("/view")
    public String view(Model model) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Date date1 = dateUtils.getFormattedDate(date, "yyyy-MM-dd");
        date1 = dateUtils.getEndingPointOfDay(date1);

        MonthBasisFIDReportDto data = service.getReport(date1);

        model.addAttribute("data",data);
        model.addAttribute("reportDate", date1);

        model.addAttribute("reportTitle", "Month Basis FID Report");
        return "collection/reports/retail/loan/fidMonthBasisReport/view";
    }

    @GetMapping("/report")
    public String getReport(String endDate, Model model) {
        Date date = dateUtils.getFormattedDate(endDate, "yyyy-MM-dd");
        date = dateUtils.getEndingPointOfDay(date);

        MonthBasisFIDReportDto data = service.getReport(date);

        model.addAttribute("data",data);
        model.addAttribute("reportDate", date);

        return "collection/reports/retail/loan/fidMonthBasisReport/view";
    }
}