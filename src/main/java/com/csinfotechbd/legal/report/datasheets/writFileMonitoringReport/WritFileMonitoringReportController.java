package com.csinfotechbd.legal.report.datasheets.writFileMonitoringReport;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/legal/report/writ-file-monitoring")
public class WritFileMonitoringReportController {

    @Autowired
    private WritFileMonitoringReportService writFileMonitoringReportService;

    @GetMapping("/view")
    public String writFileMonitoringReportView(Model model){
        model.addAttribute("reportTitle","Writ File Monitoring");
        return "legal/report/writFileMonitoringReport/view";
    }

    @ResponseBody
    @GetMapping("/search")
    public List<WritFileMonitoringReportDto> search(@RequestParam(value = "branch", required = false) String branch) {
        List<WritFileMonitoringReportDto> data = writFileMonitoringReportService.searchData(branch);
        return data;
    }
}
