package com.csinfotechbd.legal.report.datasheets.courtwiseCaseReport;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/legal/report/court-wise-case-report")
public class CourtWiseCaseReportController {

    @Autowired
    private CourtWiseCaseReportService courtWiseCaseReportService;

    @GetMapping("/view")
    public String view(Model model){
        List<CourtWiseCaseReportDto> underAraExecution = courtWiseCaseReportService.searchData("Artharin Execution");
        List<CourtWiseCaseReportDto> underAra = courtWiseCaseReportService.searchData("Artharin Suit");
        List<CourtWiseCaseReportDto> underNIAct = courtWiseCaseReportService.searchData("C.R Case under N.I Act");
        List<CourtWiseCaseReportDto> underOtherCases = courtWiseCaseReportService.searchData("Others");

        model.addAttribute("reportTitle", "Court Wise Case Analysis");
        model.addAttribute("underAraExecution", underAraExecution);
        model.addAttribute("underAra", underAra);
        model.addAttribute("underNIAct", underNIAct);
        model.addAttribute("underOtherCases", underOtherCases);

        model.addAttribute("emptyAraExTotal", new CourtWiseCaseReportDto());
        model.addAttribute("emptyAraTotal", new CourtWiseCaseReportDto());
        model.addAttribute("emptyNIActTotal", new CourtWiseCaseReportDto());
        model.addAttribute("emptyOtherTotal", new CourtWiseCaseReportDto());

        return "legal/report/courtWiseCaseReport/view";
    }
}
