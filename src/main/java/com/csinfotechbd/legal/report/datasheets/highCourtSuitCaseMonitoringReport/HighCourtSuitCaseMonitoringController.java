package com.csinfotechbd.legal.report.datasheets.highCourtSuitCaseMonitoringReport;


import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/legal/report/case-monitoring-high-court-suit-without-writ-report")
public class HighCourtSuitCaseMonitoringController {

    @Autowired
    private HighCourtSuitCaseMonitoringService highCourtSuitCaseMonitoringService;


    @GetMapping("/view")
    public String highCourtSuitCaseMonitoringReportView(Model model){
        model.addAttribute("reportTitle","High Court Suit (without Writ) Case Monitoring");
        return "legal/report/caseMonitoringHighCourtSuitWithoutWritReport/view";
    }


    @RequestMapping("/data")
    @ResponseBody
    public JSONObject getReportData() {
        JSONObject jsonObject = new JSONObject();
        List<HighCourtSuitCaseMonitoringDto> reportData = highCourtSuitCaseMonitoringService.findReportData();
        jsonObject.put("data", reportData);
        return jsonObject;
    }

}
