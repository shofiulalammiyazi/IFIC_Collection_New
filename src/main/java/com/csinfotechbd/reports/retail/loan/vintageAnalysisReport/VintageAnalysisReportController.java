package com.csinfotechbd.reports.retail.loan.vintageAnalysisReport;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/collection/report/retail/loan/vintage-analysis")
public class VintageAnalysisReportController {

    private final VintageAnalysisReportService service;

    @GetMapping("/view")
    public String view(Model model) {
        List<String> products = service.findDistributedProductGroups();

        model.addAttribute("productList", products);
        model.addAttribute("reportTitle", "Vintage Analysis");
        return "collection/reports/retail/loan/vintageAnalysisReport/view";
    }


    @GetMapping("/report")
    @ResponseBody
    public List<VintageAnalysisReportDto> getReport(String productGroup) {
        return service.getReportData(productGroup);

    }

}
