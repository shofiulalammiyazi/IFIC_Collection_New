package com.csinfotechbd.legal.report.datasheets.moneySuitFiledAgainstTheBankReport;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/legal/report/money-suit-filed-against-the-bank")
public class MoneySuitFiledAgainstTheBankReportController {

    private final MoneySuitFiledAgainstTheBankReportService service;

    @GetMapping("/view")
    public String moneySuitFiledAgainsTheBankReportView(Model model){
        model.addAttribute("reportTitle","Money Suit filed Against The Bank");
        return "legal/report/moneySuitFiledAgainstTheBank/view";
    }

    @GetMapping("/report")
    @ResponseBody
    public List<MoneySuitFiledAgainstTheBankReportDto> getReport(){
        return service.getReport();
    }
}
