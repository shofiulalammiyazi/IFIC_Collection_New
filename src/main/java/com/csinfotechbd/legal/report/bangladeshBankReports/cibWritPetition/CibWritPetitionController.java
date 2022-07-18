package com.csinfotechbd.legal.report.bangladeshBankReports.cibWritPetition;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/legal/report/bangladesh-bank-reports/cib-writ-petition")
public class CibWritPetitionController {

    private final CibWritPetitionService service;

    @GetMapping("/view")
    public String view(Model model) {
        model.addAttribute("reportTitle", "সিআইবি সংশ্লিষ্ঠ রীট পিটিশন");

        return "legal/report/bangladeshBankReports/cibWritPetition/view";
    }

    @GetMapping("/report")
    @ResponseBody
    public List<CibWritPetitionDto> getReport(String startDate, String endDate) {
        return service.getReport(startDate, endDate);
    }

}
