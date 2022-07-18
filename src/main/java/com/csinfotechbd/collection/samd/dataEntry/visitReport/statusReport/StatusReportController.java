package com.csinfotechbd.collection.samd.dataEntry.visitReport.statusReport;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/collection/samd/data-entry/visit-report/status-report")
public class StatusReportController {


    @Autowired
    private StatusReportService statusReportService;


    @PostMapping("/save")
    @ResponseBody
    public StatusReport createStatusReport(StatusReport statusReport){
        StatusReport statusReport1 = statusReportService.save(statusReport);
        System.out.println("test");
        return statusReport1;
    }



    @GetMapping("/list")
    @ResponseBody
    public List<StatusReport>getStatusReportList(@RequestParam String customerId){
        List<StatusReport>statusReports = statusReportService.findStatusReportByCustomerId(customerId);
        return statusReports;
    }


    @GetMapping("/edit")
    @ResponseBody
    public StatusReport editStatusReport(@RequestParam Long id){
        StatusReport statusReport = statusReportService.findStatusReportById(id);
        return statusReport;
    }
}
