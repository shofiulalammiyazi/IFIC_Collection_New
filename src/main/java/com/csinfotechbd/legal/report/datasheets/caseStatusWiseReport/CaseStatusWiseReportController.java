package com.csinfotechbd.legal.report.datasheets.caseStatusWiseReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/legal/report/case-status-wise")
public class CaseStatusWiseReportController {

    @Autowired
    private CaseStatusWiseReportService caseStatusWiseReportService;

    @GetMapping("/view")
    public String view(Model model){
        List<CaseStatusWiseReportDto> underAraExecution = caseStatusWiseReportService.searchData("Artharin Execution");
        List<CaseStatusWiseReportDto> underAra = caseStatusWiseReportService.searchData("Artharin Suit");
        List<CaseStatusWiseReportDto> underNIAct = caseStatusWiseReportService.searchData("C.R Case under N.I Act");
        List<CaseStatusWiseReportDto> underOtherCases = caseStatusWiseReportService.searchData("Others");

        model.addAttribute("reportTitle", "Case Status Analysis");
        model.addAttribute("underAraExecution", underAraExecution);
        model.addAttribute("underAra", underAra);
        model.addAttribute("underNIAct", underNIAct);
        model.addAttribute("underOtherCases", underOtherCases);

        model.addAttribute("emptyAraExTotal", new CaseStatusWiseReportDto());
        model.addAttribute("emptyAraTotal", new CaseStatusWiseReportDto());
        model.addAttribute("emptyNIActTotal", new CaseStatusWiseReportDto());
        model.addAttribute("emptyOtherTotal", new CaseStatusWiseReportDto());

        return "legal/report/caseStatusWiseReport/view";
    }
}
