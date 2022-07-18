package com.csinfotechbd.legal.report.bangladeshBankReports.statementOfSuitsFiledByAgainstTheBank;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.csinfotechbd.utillity.DateUtils;

import java.util.Date;

@Controller
@RequiredArgsConstructor
@RequestMapping("/legal/report/bangladesh-bank-reports/statement-of-suits-filed-by-against-the-bank")
public class StatementOfSuitsFiledByAgainstTheBankController {

    private final StatementOfSuitsFiledByAgainstTheBankService service;
    private final DateUtils dateUtils;

    @GetMapping("/view")
    public String view(String endDate, Model model) {
        model.addAttribute("reportTitle", "STATEMENT OF SUITS FILED BY/AGAINST THE BANK");

        return "legal/report/bangladeshBankReports/statementOfSuitsFiledByAgainstTheBank/view";
    }

    @GetMapping("/report")
    public String getReport(String endDate, Model model) {
        Date date = dateUtils.getFormattedDate(endDate, "yyyy-MM-dd");
        date = dateUtils.getEndingPointOfDay(date);

        model.addAttribute("data", service.getReport(date));
        model.addAttribute("reportDate", dateUtils.getBanglaShortDate(date));
        model.addAttribute("reportTitle", "STATEMENT OF SUITS FILED BY/AGAINST THE BANK");

        return "legal/report/bangladeshBankReports/statementOfSuitsFiledByAgainstTheBank/view";
    }

}
