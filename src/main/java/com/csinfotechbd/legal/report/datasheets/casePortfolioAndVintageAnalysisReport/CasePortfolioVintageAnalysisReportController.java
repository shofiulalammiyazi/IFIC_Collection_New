package com.csinfotechbd.legal.report.datasheets.casePortfolioAndVintageAnalysisReport;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/legal/report/case-portfolio-vintage-analysis")
public class CasePortfolioVintageAnalysisReportController {

    @Autowired
    private CasePortfolioVintageAnalysisReportService casePortfolioVintageAnalysisReportService;

    @GetMapping("/view")
    public String view(Model model){
        List<CasePortfolioVintageAnalysisReportDto> underAraExecution = casePortfolioVintageAnalysisReportService.searchData("Artharin Execution");
        List<CasePortfolioVintageAnalysisReportDto> underAra = casePortfolioVintageAnalysisReportService.searchData("Artharin Suit");
        List<CasePortfolioVintageAnalysisReportDto> underNIAct = casePortfolioVintageAnalysisReportService.searchData("C.R Case under N.I Act");
        List<CasePortfolioVintageAnalysisReportDto> underOtherCases = casePortfolioVintageAnalysisReportService.searchData("Others");

        model.addAttribute("reportTitle","Case Portfolio Vintage Analysis");
        model.addAttribute("underAraExecution", underAraExecution);
        model.addAttribute("underAra", underAra);
        model.addAttribute("underNIAct", underNIAct);
        model.addAttribute("underOtherCases", underOtherCases);

        model.addAttribute("emptyAraExTotal", new CasePortfolioVintageAnalysisReportDto());
        model.addAttribute("emptyAraTotal", new CasePortfolioVintageAnalysisReportDto());
        model.addAttribute("emptyNIActTotal", new CasePortfolioVintageAnalysisReportDto());
        model.addAttribute("emptyOtherTotal", new CasePortfolioVintageAnalysisReportDto());

        return "legal/report/casePortfolioVintageAnalysisReport/view";
    }
}
