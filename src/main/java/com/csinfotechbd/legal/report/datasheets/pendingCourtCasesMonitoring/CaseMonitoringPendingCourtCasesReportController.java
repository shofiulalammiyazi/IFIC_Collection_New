package com.csinfotechbd.legal.report.datasheets.pendingCourtCasesMonitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/collection/report/legal/case-monitoring-pending-court-cases")
public class CaseMonitoringPendingCourtCasesReportController {

    @Autowired
    private CaseMonitoringPendingCourtCasesReportService caseMonitoringPendingCourtCasesReportService;

    @RequestMapping("/view")
    public String view(Model model){
        List<CaseMonitoringPendingCourtCasesReportDto> pendingCases = caseMonitoringPendingCourtCasesReportService.searchData();

        model.addAttribute("reportTitle", "Pending Court Cases Monitoring");
        model.addAttribute("pendingCases", pendingCases);
        return "legal/report/caseMonitoringPendingCourtCasesReport/view";
    }
}
