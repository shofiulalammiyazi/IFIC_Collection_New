package com.csinfotechbd.legal.report.managerial.branchWiseLegalExpenses;


import com.csinfotechbd.collection.settings.branch.Branch;
import com.csinfotechbd.collection.settings.branch.BranchService;
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
@RequestMapping("/legal/report/managerial/branch-wise-legal-expenses")
public class BranchWiseLegalExpensesController {

    private final BranchService branchService;
    private final CaseTypeService caseTypeService;
    private final BranchWiseLegalExpensesService service;
    private final JasperReportManager jasperReportManager;
    
    
    @Value("")
    private String jasperFile;
    
    @Value("Branch Wise Legal Expenses")
    private String reportTitle;
    

    @GetMapping("/view")
    public String view(Model model) {
        model.addAttribute("branches", branchService.getBranchesContainingCases());
        model.addAttribute("caseTypes", caseTypeService.getEnabledCaseTypeDtoList());
        model.addAttribute("reportTitle", "Branch wise Legal Expenses");
        return "legal/report/managerial/branchWiseLegalExpenses/view";
    }

    @GetMapping("/report")
    @ResponseBody
    public Map<String, List<BranchWiseLegalExpensesDto>> getReport(Long[] caseTypes, String branchCode) {
        return service.getReport(Arrays.asList(caseTypes), branchCode);
    }

    @GetMapping(value = "/report/excel")
    public void generateExcel(HttpServletResponse response, @RequestParam(value = "caseTypes") List<Long> caseTypes,
                              @RequestParam(value = "branchCode") String branchCode){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("branchCode",branchCode);
        parameters.put("caseTypes",caseTypes);
        jasperReportManager.exportToExcel(response,parameters,reportTitle,jasperFile);
    }
}
