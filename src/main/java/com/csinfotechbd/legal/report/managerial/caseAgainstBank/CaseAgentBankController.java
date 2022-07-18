package com.csinfotechbd.legal.report.managerial.caseAgainstBank;

import com.csinfotechbd.legal.setup.caseType.CaseType;
import com.csinfotechbd.legal.setup.caseType.CaseTypeService;
import com.csinfotechbd.utillity.JasperReportManager;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/legal/report/managerial/cases-against-bank")
public class CaseAgentBankController {

    @Autowired
    private CaseAgainstBankService caseAgainstBankService;

    @Autowired
    private CaseTypeService caseTypeService;
    
    @Autowired
    private JasperReportManager jasperReportManager;
    
    
    @Value("/legalreport/casesAgainstBank/casesAgainstBank.jasper")
    private String jasperFile;
    
    @Value("Case Against Bank")
    private String reportTitle;

    @GetMapping(value = "/view")
    public String view(Model model) {
        List<CaseType> caseTypes = caseTypeService.findByCaseFiledTypeName("Against Bank");
        List<CaseType> supremeCourtCaseTypes = caseTypeService.findByCaseFiledTypeName("The Supreme Court of Bangladesh");
        caseTypes.addAll(supremeCourtCaseTypes);
        model.addAttribute("caseTypes", caseTypes);
        model.addAttribute("reportTitle", "Cases against Bank");
        return "legal/report/managerial/caseAgainstBank/view";
    }

    @GetMapping("/report")
    @ResponseBody
    public List<CaseAgainstBankDto> getAgentBankCases(Long[] caseTypeIds) {
        return caseAgainstBankService.getAgentBankCases(Arrays.asList(caseTypeIds));
    }

    @GetMapping(value = "/report/excel")
    public void generateExcel(HttpServletResponse response,
                              @RequestParam(value = "fileType") String fileType,
                              @RequestParam(value = "caseTypes", required = false) List<Long> caseTypes ){

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("caseTypesList",caseTypes);

        if(fileType.toLowerCase().equals("pdf"))
            jasperReportManager.exportToPdf(response, parameters, reportTitle, jasperFile);
        else if(fileType.toLowerCase().equals("excel"))
            jasperReportManager.exportToExcel(response, parameters, reportTitle, jasperFile);

    }
}
