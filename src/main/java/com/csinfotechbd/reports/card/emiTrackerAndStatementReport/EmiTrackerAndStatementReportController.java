package com.csinfotechbd.reports.card.emiTrackerAndStatementReport;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/collection/report/card/emitrackerandstatementreport")
public class EmiTrackerAndStatementReportController {
    @GetMapping("/view")
    public String view(Model model){
        return "collection/reports/card/emiTrackerAndStatementReport/view";
    }
}
