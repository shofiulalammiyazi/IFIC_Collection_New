package com.csinfotechbd.legal.report.datasheets.niActCaseMonitoringReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/legal/report/ni-act-case-monitoring")
public class NIActCaseMonitoringReportController {

    @Autowired
    private NIActCaseMonitoringReportService niActCaseMonitoringReportService;

    @GetMapping("/view")
    public String view(Model model){
        model.addAttribute("reportTitle", "Arthorin Suit & Arthorin Execution Case Monitoring\n");
        return "legal/report/niActCaseMonitoringReport/view";
    }

    @ResponseBody
    @GetMapping("/search")
    public List<NIActCaseMonitoringDto> search(@RequestParam(value = "branch") String branch){
        List<NIActCaseMonitoringDto> data = niActCaseMonitoringReportService.searchData(branch);
        return data;
    }
}
