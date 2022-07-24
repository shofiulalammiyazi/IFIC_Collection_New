package com.unisoft.reports.retail.loan.productWiseClReport;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/collection/report/retail/loan/product-wise-cl")
public class ProductWiseCLReportController {

    private final ProductWiseCLReportService service;

    @GetMapping("/view")
    public String view(Model model) {
        model.addAttribute("reportTitle", "Product wise CL report");
        return "collection/reports/retail/loan/clReportProductWise/view";
    }

    @GetMapping("/report")
    @ResponseBody
    public List<ProductWiseClReport> getProWiseClReport(@RequestParam (value = "month") String month){

        List<ProductWiseClReport> productWiseClReports = service.getReport(month);
        return productWiseClReports;
    }

//    public JSONObject getReport(String month) {
//
//        List<ProductWiseCLReportDto> data = service.getLoanAndCardReport(month);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("data", data);
//
//        return jsonObject;
//    }
}
