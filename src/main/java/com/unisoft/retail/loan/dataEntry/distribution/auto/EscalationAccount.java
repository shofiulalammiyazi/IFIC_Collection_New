package com.unisoft.retail.loan.dataEntry.distribution.auto;

import com.unisoft.collection.allocationLogic.PeopleAllocationLogicRepository;
import com.unisoft.collection.distribution.loan.LoanDistributionService;
import com.unisoft.collection.distribution.loan.loanApi.LoanApiPayload;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationService.AccountInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/retail/loan/data-entry/distribution/escalation/")
public class EscalationAccount {

    @Autowired
    private PeopleAllocationLogicRepository peopleAllocationLogicRepository;
    @Autowired
    private AccountInformationService accountInformationService;

    @Autowired
    private LoanDistributionService loanDistributionService;

    @GetMapping("excalationAccount")
    public String getEscalationAccount(Model model) {
        model.addAttribute("dealerList", peopleAllocationLogicRepository.findByUnitAndDistributionType("Loan", "Regular"));


        return "retail/loan/dataEntry/distribution/auto/escalationlist";
    }

    @RequestMapping("get-page-by-issmssent")
    ResponseEntity accountInformationEntitiesByPaginationByIsSmsSent(@RequestParam("page") int page,
                                                                     @RequestParam(required = false) String search,
                                                                     @RequestParam("length") int length, HttpServletRequest request){
        return accountInformationService.findAllEscalationAccount(page, length, search);
    }

    @PostMapping("dealer-wise-distribution")
    public String saveMannualDealerWiseDistribution(@Valid @RequestBody LoanApiPayload loanApiPayload, HttpSession session) {
        Map errors = loanDistributionService.saveMannualDealerWiseDistribution(loanApiPayload);
        session.setAttribute("distributionErrors", errors);
        return "redirect:/distribution/loan/list";
    }


}
