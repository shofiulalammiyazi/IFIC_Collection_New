package com.csinfotechbd.legal.report.managerial.statementOfMonthlyNewCases;


import com.csinfotechbd.legal.setup.caseType.CaseTypeService;
import com.csinfotechbd.utillity.DateUtils;
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
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/legal/report/managerial/statement-of-monthly-new-cases")
public class StatementOfMonthlyNewCasesController {

    private final CaseTypeService caseTypeService;
    private final StatementOfMonthlyNewCasesService service;
    private final DateUtils dateUtils;
    private final JasperReportManager jasperReportManager;
    
    @Value("/legalreport/monthlyNewCases/monthlyNewCases.jasper")
    private String jasperFile;
    
    @Value("Statement of Monthly New Cases")
    private String reportTitle;

    @GetMapping("/view")
    public String view(Model model) {
        model.addAttribute("caseTypes", caseTypeService.getEnabledCaseTypeDtoList());
        model.addAttribute("reportTitle", "Statement of Monthly New Cases");
        return "legal/report/managerial/statementOfMonthlyNewCases/view";
    }

    @GetMapping("/report")
    @ResponseBody
    public Map<String, List<StatementOfMonthlyNewCasesDto>> getReport(Long[] caseTypes, String month) {
        Date startDate = dateUtils.getFormattedDate(month + "-01", "yyyy-MM-dd");
        Date endDate = dateUtils.getMonthEndDate(startDate);
        return service.getReport(Arrays.asList(caseTypes), startDate, endDate);
    }

    @GetMapping(value = "/report/excel")
    public void generateExcel(HttpServletResponse response,
                              @RequestParam(value = "fileType") String fileType,
                              @RequestParam(value = "caseTypes", required = false) List<Long> caseTypes,
                              @RequestParam(value = "month") String month){

        Map<String, Object> parameters = new HashMap<>();
        Date startDate = dateUtils.getFormattedDate(month + "-01", "yyyy-MM-dd");
        Date endDate = dateUtils.getMonthEndDate(startDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yy");

        String startDate2 = null;
        String endDate2 = null;

        startDate2 = simpleDateFormat.format(startDate);
        endDate2 = simpleDateFormat.format(endDate);

        parameters.put("startDate", startDate2);
        parameters.put("endDate", endDate2);
        parameters.put("caseTypes", caseTypes);

        if(fileType.toLowerCase().equals("pdf"))
            jasperReportManager.exportToPdf(response, parameters, reportTitle, jasperFile);
        else if(fileType.toLowerCase().equals("excel"))
            jasperReportManager.exportToExcel(response, parameters, reportTitle, jasperFile);

    }

}
