package com.unisoft.collection.distribution.loan;
/*
Created by   Islam at 9/29/2019
*/

import com.unisoft.collection.distribution.loan.loanAccount.LoanAccountInfo;
import com.unisoft.collection.distribution.loan.loanAccount.LoanAccountService;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.unisoft.collection.settings.agency.AgencyService;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.retail.loan.dataEntry.distribution.auto.LoanAutoDistributionInfo;
import com.unisoft.retail.loan.dataEntry.distribution.auto.LoanAutoDistributionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/distribution/loan/unallocated/")
public class LoanUnallocatedController {

    private final LoanAutoDistributionService loanAutoDistributionService;
    private final EmployeeService employeeService;
    private final AgencyService agencyService;
    private final LoanAccountService loanAccountService;


    @GetMapping("list")
    public String getUnallocatedLoanList(Model model) {
        List<LoanAutoDistributionInfo> unallocatedAccounts = loanAutoDistributionService.findAllData();

        model.addAttribute("loanviewlist", unallocatedAccounts);
        model.addAttribute("dealerList", employeeService.getDealerList());
        model.addAttribute("agencyList", agencyService.getActiveList());
        return "collection/distribution/loan/loanunallocatedlist";
    }

    private LoanAccountInfo getLoanAccountInfo(LoanAccountBasicInfo loanAccountBasicInfo) {
        return loanAccountService.findByLoanAccountBasicId(loanAccountBasicInfo);

    }
}
