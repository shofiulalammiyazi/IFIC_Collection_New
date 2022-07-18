package com.csinfotechbd.legal.report.datasheets.monthlyNewCaseFillingReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/legal/report/monthly-new-case-filing")
public class MonthlyNewCaseFillingReportController {

    @Autowired
    private MonthlyNewCaseFilingReportService monthlyNewCaseFilingReportService;

    @GetMapping("/view")
    public String view(Model model){
        model.addAttribute("reportTitle", "Monthly New Case Filling (ARA, ARA-EX, NI Act, Others)");
        return "legal/report/monthlyNewCaseFilingReport/view";
    }

    @ResponseBody
    @GetMapping("/search")
    public List<MonthlyNewCaseFilingDto> search(@RequestParam(value = "month", required = true) String month) {
        List<MonthlyNewCaseFilingDto> data = monthlyNewCaseFilingReportService.searchData(month);
        return data;
    }
}
