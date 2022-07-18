package com.csinfotechbd.legal.report.datasheets.supremeCourtBangladeshCaseReport;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/legal/report/the-supreme-court-bangladesh-case")
public class SupremeCourtBangladeshCaseReportController {

    @Autowired
    private SupremeCourtBangladeshCaseReportService supremeCourtBangladeshCaseReportService;

    @GetMapping("/view")
    public String SupremeCourtBangladeshCaseReportView(Model model){
        List<SupremeCourtBangladeshCaseReportDto> typeOfCaseUnderHighCourtDivision = supremeCourtBangladeshCaseReportService.searchData("High Court Division", "Type of Case");
        List<SupremeCourtBangladeshCaseReportDto> courseOfActionUnderHighCourtDivision = supremeCourtBangladeshCaseReportService.searchData("High Court Division", "Course of Action");
        List<SupremeCourtBangladeshCaseReportDto> caseStatusUnderHighCourtDivision = supremeCourtBangladeshCaseReportService.searchData("High Court Division", "Case Status");

        List<SupremeCourtBangladeshCaseReportDto> typeOfCaseUnderAppellateDivision = supremeCourtBangladeshCaseReportService.searchData("Appellate Division", "Type of Case");
        List<SupremeCourtBangladeshCaseReportDto> courseOfActionUnderAppellateDivision = supremeCourtBangladeshCaseReportService.searchData("Appellate Division", "Course of Action");
        List<SupremeCourtBangladeshCaseReportDto> caseStatusUnderAppellateDivision = supremeCourtBangladeshCaseReportService.searchData("Appellate Division", "Case Status");

        model.addAttribute("reportTitle","The Supreme Court of Bangladesh Case Report");

        model.addAttribute("typeOfCaseUnderHighCourtDivision", typeOfCaseUnderHighCourtDivision);
        model.addAttribute("courseOfActionUnderHighCourtDivision", courseOfActionUnderHighCourtDivision);
        model.addAttribute("caseStatusUnderHighCourtDivision", caseStatusUnderHighCourtDivision);

        model.addAttribute("typeOfCaseUnderAppellateDivision", typeOfCaseUnderAppellateDivision);
        model.addAttribute("courseOfActionUnderAppellateDivision", courseOfActionUnderAppellateDivision);
        model.addAttribute("caseStatusUnderAppellateDivision", caseStatusUnderAppellateDivision);

        /* ----------------------- For Calculation of Subtotal ---------------------------------------------------------------- */
        model.addAttribute("typeOfCaseUnderHighCourtDivisionTotal", new SupremeCourtBangladeshCaseReportDto("Sub Total"));
        model.addAttribute("courseOfActionUnderHighCourtDivisionTotal", new SupremeCourtBangladeshCaseReportDto("Sub Total"));
        model.addAttribute("caseStatusUnderHighCourtDivisionTotal", new SupremeCourtBangladeshCaseReportDto("Sub Total"));

        model.addAttribute("typeOfCaseUnderAppellateDivisionTotal", new SupremeCourtBangladeshCaseReportDto("Sub Total"));
        model.addAttribute("courseOfActionUnderAppellateDivisionTotal", new SupremeCourtBangladeshCaseReportDto("Sub Total"));
        model.addAttribute("caseStatusUnderAppellateDivisionTotal", new SupremeCourtBangladeshCaseReportDto("Sub Total"));

        return "legal/report/supremeCourtBangladeshCaseReport/view";
    }
}
