package com.unisoft.reports.retail.loan.sourcingChannelWiseReport;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/collection/report/retail/loan/channel-wise-sourcing")
public class SourcingChannelWiseReportController {

    private final SourcingChannelWiseReportService service;

    @GetMapping("/view")
    public String view(Model model) {

        List<String> products = service.findDistributedProductGroups();

        model.addAttribute("reportTitle", "Sourcing Channel Wise Report");
        model.addAttribute("productList", products);

        return "collection/reports/retail/loan/sourcingChannelWiseReport/view";
    }

    @GetMapping("/report")
    @ResponseBody
    public List<SourcingChannelWiseReportDto> getReport(String productGroup, String month) {
        List<SourcingChannelWiseReportDto> list = service.getReportData(productGroup, month);
        return list;
    }

//    @GetMapping("/report/all")
//    @ResponseBody
//    public JSONObject getReport(List<String> productGroups, String month) {
//
//        return service.getReport(productGroups, month);
//
//    }
}
