package com.unisoft.reports.retail.loan.branchClReportWise;

import com.unisoft.collection.settings.branch.BranchService;
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
@RequestMapping("/collection/report/retail/loan/branch-wise-cl")
public class BranchWiseClReportController {

    private final BranchWiseClReportService reportService;
    private final BranchService branchService;


    @GetMapping(value = "/get")
    @ResponseBody
    public List<BranchWiseCLReport> getData(@RequestParam(value ="date" ) String date,
                                            @RequestParam(value = "branchCode") List<String> branchCode){
        List<BranchWiseCLReport> report = reportService.getReport(date, branchCode);
        return report;

    }

    @GetMapping("/view")
    public String view(Model model) {
        model.addAttribute("reportTitle", "Branch Wise Cl Report");
        model.addAttribute("branchList", branchService.getActiveList());
//        model.addAttribute("regionList", reportService.getAvailableRegionList());
//        model.addAttribute("divisionList", reportService.getAvailableDivisionList());
        return "collection/reports/retail/loan/clReportBranchWise/view";
    }

//    @GetMapping("/report")
//    @ResponseBody
//    public JSONObject getReport(String[] regions, String[] branches, String[] divisions, String startDay, String endDay) {
//
//        return reportService.getReport(Arrays.asList(regions), Arrays.asList(branches), Arrays.asList(divisions), startDay, endDay);
//
//    }

}
