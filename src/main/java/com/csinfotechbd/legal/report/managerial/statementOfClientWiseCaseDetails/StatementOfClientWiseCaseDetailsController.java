package com.csinfotechbd.legal.report.managerial.statementOfClientWiseCaseDetails;


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

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/legal/report/managerial/statement-of-client-wise-case-details")
public class StatementOfClientWiseCaseDetailsController {

    private final BranchService branchService;
    private final StatementOfClientWiseCaseDetailsService service;
    private final JasperReportManager jasperReportManager;
    
    @Value("/legalreport/clientWiseCaseDetails/clientWiseCaseDetails.jasper")
    private String jasperFile;
    
    @Value("Statement of Client-wise Case details")
    private String reportTitle;

    @GetMapping("/view")
    public String view(Model model) {
        model.addAttribute("branches", branchService.getActiveList());
        model.addAttribute("cifList", service.getCifNumbers());
        model.addAttribute("reportTitle", "Statement of Client-wise Case details");
        return "legal/report/managerial/statementOfClientWiseCaseDetails/view";
    }

    @GetMapping("/report")
    @ResponseBody
    public List<StatementOfClientWiseCaseDetailsDto> getReport(String clientId, String[] branches) {
        List<StatementOfClientWiseCaseDetailsDto> list = service.getReport(clientId, branches);
        return list;
    }

    @GetMapping(value = "/report/excel")
    public void generateExcel(HttpServletResponse response,
                              @RequestParam(value = "fileType") String fileType,
                              @RequestParam(value = "clientId") String clientId,
                              @RequestParam(value = "branchCode") List<String> branchCode
                              ){

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("clientId",clientId);
        parameters.put("branchCodeList",branchCode);
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResource("/logo/ucbl_logo_x.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        parameters.put("logo",image);
        if(fileType.toLowerCase().equals("pdf"))
            jasperReportManager.exportToPdf(response, parameters, reportTitle, jasperFile);
        else if(fileType.toLowerCase().equals("excel"))
            jasperReportManager.exportToExcel(response, parameters, reportTitle, jasperFile);

    }

}
