package com.csinfotechbd.legal.report.datasheets.legalBilLExpenseSummaryReport;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/legal/report/legal-bill-expense-summary")
public class LegalBillExpenseSummaryReportController {

    @Autowired
    private LegalBillExpenseSummaryReportService legalBillExpenseSummaryReportService;

    @GetMapping("/view")
    public String view(Model model){
        List<String>list = legalBillExpenseSummaryReportService.findBranchName();
        for (String i: list){
            System.out.println(i);
        }
        model.addAttribute("reportTitle","Legal Bill, Expense Summary");
        model.addAttribute("CBDSegment", new LegalBillExpenseSummaryReportDto());
        model.addAttribute("SMESegment", new LegalBillExpenseSummaryReportDto());
        model.addAttribute("RBDSegment", new LegalBillExpenseSummaryReportDto());
        model.addAttribute("CARDSegment", new LegalBillExpenseSummaryReportDto());
        model.addAttribute("branchList", legalBillExpenseSummaryReportService.findBranchName());
        model.addAttribute("lawyerList", legalBillExpenseSummaryReportService.findLawyerList());
        return "legal/report/legalBillExpenseSummaryReport/view";
    }

    @GetMapping("/search")
    @ResponseBody
    public Map<String, Object> searchLegalBillExpense(@RequestParam(required = true) String date, @RequestParam(required = false) String branchName, @RequestParam(required = false) String lawyerName){

        Map<String, Object> objectMap = new HashMap<>();

        List<LegalBillExpenseSummaryReportDto>branchLawyerWiseExpenseList = legalBillExpenseSummaryReportService.searchBranchLawarWiseData(date, branchName, lawyerName);

        List<LegalBillExpenseSummaryReportDto>CBDSegmentBill = legalBillExpenseSummaryReportService.searchSegmentWiseData("CBD", date);
        LegalBillExpenseSummaryReportDto CBDTotal = legalBillExpenseSummaryReportService.getTotal(CBDSegmentBill);
        List<LegalBillExpenseSummaryReportDto>SMESegmentBill = legalBillExpenseSummaryReportService.searchSegmentWiseData("SME", date);
        LegalBillExpenseSummaryReportDto SMETotal = legalBillExpenseSummaryReportService.getTotal(SMESegmentBill);
        List<LegalBillExpenseSummaryReportDto>RBDSegmentBill = legalBillExpenseSummaryReportService.searchSegmentWiseData("RBD", date);
        LegalBillExpenseSummaryReportDto RBDTotal = legalBillExpenseSummaryReportService.getTotal(RBDSegmentBill);
        List<LegalBillExpenseSummaryReportDto>CARDSegmentBill = legalBillExpenseSummaryReportService.searchSegmentWiseData("CARD", date);
        LegalBillExpenseSummaryReportDto CARDTotal = legalBillExpenseSummaryReportService.getTotal(CARDSegmentBill);

        objectMap.put("branchLawyerWiseExpenseList",branchLawyerWiseExpenseList);

        objectMap.put("CBDSegmentBill",CBDSegmentBill );
        objectMap.put("SMESegmentBill",SMESegmentBill );
        objectMap.put("RBDSegmentBill",RBDSegmentBill );
        objectMap.put("CARDSegmentBill",CARDSegmentBill );
        objectMap.put("CBDTotal",CBDTotal );
        objectMap.put("SMETotal",SMETotal );
        objectMap.put("RBDTotal",RBDTotal );
        objectMap.put("CARDTotal",CARDTotal );
        return objectMap;
    }

}
