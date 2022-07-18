package com.csinfotechbd.legal.report.datasheets.judgementAwardedAdjustedAccountReport;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/legal/report/judgement-awarded-adjusted-account")
public class JudgementAwardedAdjustedAccountReportController {

    @Autowired
    private JudgementAwardedAdjustedAccountReportService judgementAwardedAdjustedAccountReportService;

    @GetMapping("/view")
    public String judgementAwardedAdjustedAccountReportView(Model model){
        model.addAttribute("reportTitle","Judgment Awarded/Adjusted Accounts Report");
        return "legal/report/judgementAwardedAdjustedAccountReport/view";
    }

    @ResponseBody
    @GetMapping("/search")
    public List<JudgementAwardedAdjustedAccountDto> search(String month){
        List<JudgementAwardedAdjustedAccountDto> data = judgementAwardedAdjustedAccountReportService.searchData(month);
        return data;
    }
}
