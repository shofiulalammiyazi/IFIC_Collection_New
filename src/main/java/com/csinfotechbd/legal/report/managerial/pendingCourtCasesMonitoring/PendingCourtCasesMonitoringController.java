package com.csinfotechbd.legal.report.managerial.pendingCourtCasesMonitoring;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/legal/report/managerial/pending-court-cases-monitoring")
public class PendingCourtCasesMonitoringController {

    private final PendingCourtCasesMonitoringService service;

    @GetMapping("/view")
    public String view(Model model) {

        List<PendingCourtCasesMonitoringDto> reports = service.getReport();

        model.addAttribute("report",reports);
        model.addAttribute("reportTitle", "Pending Court Cases Monitoring");
        return "legal/report/managerial/pendingCourtCasesMonitoring/view";
    }

}
