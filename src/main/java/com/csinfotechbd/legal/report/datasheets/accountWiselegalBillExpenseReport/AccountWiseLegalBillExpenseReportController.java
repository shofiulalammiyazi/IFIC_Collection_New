package com.csinfotechbd.legal.report.datasheets.accountWiselegalBillExpenseReport;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/legal/report/account-wise-legal-bill-expense")
public class AccountWiseLegalBillExpenseReportController {

    @Autowired
    private AccountWiseLegalBillExpenseReportService accountWiseLegalBillExpenseReportService;

    @GetMapping("/view")
    public String accountWiseLegalBillExpenseReportView(Model model){
        model.addAttribute("reportTitle","ACCOUNT WISE LEGAL EXPENSE");
        return "legal/report/accountWiseLegalBillExpense/view";
    }

    @ResponseBody
    @GetMapping("/search")
    public List<AccountWiseLegalBillExpenseDto> search(@RequestParam(value = "month") String month) {
        List<AccountWiseLegalBillExpenseDto> data = accountWiseLegalBillExpenseReportService.searchData(month);
        return data;
    }
}
