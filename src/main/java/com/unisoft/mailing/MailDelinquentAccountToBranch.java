package com.unisoft.mailing;

import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionService;
import com.unisoft.collection.settings.branch.Branch;
import com.unisoft.collection.settings.branch.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mail/delinquent/acc/branch")
public class MailDelinquentAccountToBranch {

    @Autowired
    private BranchService branchService;
    @Autowired
    private LoanAccountDistributionService loanAccountDistributionService;

    @Autowired
    private SendMailService service;

    @GetMapping(value = "/account")
    public String create(Model model){
        model.addAttribute("branches",branchService.getActiveList());
        model.addAttribute("accounts",loanAccountDistributionService.findCurrentMonthAccountDistList());
        return "collection/mailing/mailDelinquentAccToBranch";
    }

    @GetMapping(value = "/delinquent/accounts")
    @ResponseBody
    public List<LoanAccountDistributionInfo> accounts(){
        return loanAccountDistributionService.findCurrentMonthAccountDistList();
    }

    @GetMapping(value = "/branches")
    @ResponseBody
    public List<Branch> getActiveList() {
        return branchService.getActiveList();
    }

    @PostMapping(value = "/send")
    @ResponseBody
    public boolean send(@RequestBody Mail mail){
        return service.sendMail(mail);
    }
}
