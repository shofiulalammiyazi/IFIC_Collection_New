package com.csinfotechbd.legal.report.managerial.statementOfAdjustedCases;


import com.csinfotechbd.legal.setup.caseType.CaseTypeService;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/legal/report/managerial/statement-of-adjusted-cases")
public class StatementOfAdjustedCasesController {

    private final CaseTypeService caseTypeService;
    private final StatementOfAdjustedCasesService service;
    private final JasperReportManager jasperReportManager;
    
    @Value("")
    private String jasperFile;
    
    @Value("Statement of Adjusted Cases")
    private String reportTitle;

    @GetMapping("/view")
    public String view(Model model) {
        model.addAttribute("caseTypes", caseTypeService.getEnabledCaseTypeDtoList());
        model.addAttribute("reportTitle", "Statement of Adjusted Cases");
        return "legal/report/managerial/statementOfAdjustedCases/view";
    }

    @GetMapping("/report")
    @ResponseBody
    public Map<String, List<StatementOfAdjustedCasesDto>> getReport(Long[] caseTypes, String startDate, String endDate) {
        return service.getReport(Arrays.asList(caseTypes), startDate, endDate);
    }

    @GetMapping(value = "/report/excel")
    public void generateExcel(HttpServletResponse response, @RequestParam(value = "caseTypes") List<Long> caseTypes,@RequestParam(value = "startDate") String startDate,
                              @RequestParam(value = "endDate") String endDate){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("caseTypes",caseTypes);
        parameters.put("startDate",startDate);
        parameters.put("endDate",endDate);
        jasperReportManager.exportToExcel(response,parameters,reportTitle,jasperFile);
    
    }
}
