package com.csinfotechbd.legal.report.managerial.statementOfOwnershipCertificateUnderSection33OfARA;


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
@RequestMapping("/legal/report/managerial/statement-of-ownership-certificate-under-section-33-of-ara")
public class StatementOfOwnershipCertificateUnderSection33OfARAController {

    private final StatementOfOwnershipCertificateUnderSection33OfARAService service;
    private final JasperReportManager jasperReportManager;
    
    
    @Value("")
    private String jasperFile;
    
    @Value("Statement of Ownership Certificate under Section 33 of ARA")
    private String reportTitle;

    @GetMapping("/view")
    public String view(Model model) {
        model.addAttribute("reportTitle", "Statement of Ownership Certificate under Section 33 of ARA");
        return "legal/report/managerial/statementOfOwnershipCertificateUnderSection33OfARA/view";
    }

    @GetMapping("/report")
    @ResponseBody
    public List<StatementOfOwnershipCertificateUnderSection33OfARADto> getReport(String sectionType) {
        return service.getReport(sectionType);
    }

    @GetMapping(value = "/report/excel")
    public void generateExcel(HttpServletResponse response, @RequestParam(value = "sectionType") String sectionType){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("sectionType",sectionType);
        jasperReportManager.exportToExcel(response,parameters,reportTitle,jasperFile);
    }
}
