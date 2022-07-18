package com.csinfotechbd.legal.report.datasheets.lawyerPerformanceMatrixReport;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/legal/report/lawyer-performance-matrix")
public class LawyerPerformanceMatrixReportController {


    @GetMapping("/view")
    public String lawyerPerformanceMatrixView(Model model){
        model.addAttribute("reportTitle1","Assumptions for different Grading Parameter as follows");
        model.addAttribute("reportTitle2","Lawyer's Monthly Performance: as on Dec-20");
        return "legal/report/lawyerPerformanceMatrixReport/view";
    }
}
