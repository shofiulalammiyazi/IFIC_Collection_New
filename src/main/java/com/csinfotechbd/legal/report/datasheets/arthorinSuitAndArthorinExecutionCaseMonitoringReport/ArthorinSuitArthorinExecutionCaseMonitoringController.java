package com.csinfotechbd.legal.report.datasheets.arthorinSuitAndArthorinExecutionCaseMonitoringReport;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/legal/report/arthorin-suit-arthorin-execution-case-monitoring")
public class ArthorinSuitArthorinExecutionCaseMonitoringController {

    @Autowired
    private ArthorinSuitArthorinExecutionCaseMonitoringReportService arthorinSuitArthorinExecutionCaseMonitoringReportService;

    @GetMapping("/view")
    public String view(Model model){
        model.addAttribute("reportTitle", "Arthorin Suit & Arthorin Execution Case Monitoring");
        return "legal/report/arthorinSuitArthorinExecutionCaseMonitoringReport/view";
    }

    @ResponseBody
    @GetMapping("/search")
    public List<ArthorinSuitArthorinExecutionCaseMonitoringDto> search(@RequestParam(value = "branch") String branch){
        List<ArthorinSuitArthorinExecutionCaseMonitoringDto> data = arthorinSuitArthorinExecutionCaseMonitoringReportService.searchData(branch);
        return data;
    }
}
