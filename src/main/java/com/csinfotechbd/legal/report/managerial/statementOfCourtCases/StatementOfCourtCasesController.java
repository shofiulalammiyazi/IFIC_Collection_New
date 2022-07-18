package com.csinfotechbd.legal.report.managerial.statementOfCourtCases;


import com.csinfotechbd.collection.settings.branch.Branch;
import com.csinfotechbd.collection.settings.branch.BranchDao;
import com.csinfotechbd.collection.settings.branch.BranchService;
import com.csinfotechbd.utillity.JasperReportManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/legal/report/managerial/statement-of-court-cases")
public class StatementOfCourtCasesController {

    private final BranchService branchService;
    private final StatementOfCourtCasesService service;
    private final JasperReportManager jasperReportManager;
    private final BranchDao branchDao;
    
    @Value("/legalreport/branchwiseCourtCase/branchWiseCourtCases.jasper")
    private String jasperFile;
    
    @Value("Statement of Court Cases")
    private String reportTitle;
    
    @GetMapping("/view")
    public String view(Model model) {
        model.addAttribute("branches", branchService.getBranchesContainingCases());
        model.addAttribute("reportTitle", "Statement of Court Cases");
        return "legal/report/managerial/statementOfCourtCases/view";
    }

    @GetMapping("/report")
    @ResponseBody
    public List<StatementOfCourtCasesDto> getReport(String branchCode) {
        return service.getReport(branchCode);
    }

    @GetMapping(value = "/report/excel")
    public void generateReport(HttpServletResponse response,@RequestParam(value = "fileType") String fileType, @RequestParam(value = "branchCode") String branchCode){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("branchCode", branchCode);

        if(fileType.toLowerCase().equals("pdf"))
            jasperReportManager.exportToPdf(response, parameters, reportTitle, jasperFile);
        else if(fileType.toLowerCase().equals("excel"))
            jasperReportManager.exportToExcel(response, parameters, reportTitle, jasperFile);
    }
}
