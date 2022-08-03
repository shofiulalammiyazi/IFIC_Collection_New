package com.unisoft.retail.loan.dataEntry.distribution.auto;

import com.unisoft.collection.settings.agency.AgencyEntity;
import com.unisoft.collection.settings.agency.AgencyService;
import com.unisoft.collection.settings.employee.EmployeeInfoDto;
import com.unisoft.collection.settings.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/retail/loan/data-entry/distribution/auto/")
public class LoanAutoDistributionController {

    private final LoanAutoDistributionService service;
    private final EmployeeService employeeService;
    private final AgencyService agencyService;
    private final LoanAutoDistributionService autoDistributionService;

    @GetMapping("approval")
    public String getDelinquentAccountList(Model model) {

        List<LoanAutoDistributionViewModel> distributionSummary = service.getCurrentMonthAutoDistributionSummary();
        List<LoanAutoDistributionDto> unallocatedList = service.getCurrentMonthUnallocatedList();
        List<String> exceptionUnits = Arrays.asList("SAM", "Write Off", "Legal"); // Avoid employees from SAM, Legal or Write Off
        List<EmployeeInfoDto> dealerList = employeeService.findEmployeePinsByUnitAndDesignation(Arrays.asList("Dealer", "Supervisor"), "Loan", exceptionUnits);

        List<AgencyEntity> agencyList = agencyService.getActiveList();

        model.addAttribute("distributionSummary", distributionSummary);
        model.addAttribute("unallocatedList", unallocatedList);
        model.addAttribute("employeeList", dealerList);
        model.addAttribute("agencyList", agencyList);

        return "retail/loan/dataEntry/distribution/auto/approval";
    }

   /* @GetMapping("list")
    public String getTemporaryDistributionList(Model model) {

        List<LoanAutoDistributionDto> distributionSummary = service.getCurrentMonthAllocatedList();
        List<String> exceptionUnits = Arrays.asList("SAM", "Write Off", "Legal"); // Avoid employees from SAM, Legal or Write Off
        List<EmployeeInfoDto> dealerList = employeeService.findEmployeePinsByUnitAndDesignation(Arrays.asList("Dealer", "Supervisor"), "Loan", exceptionUnits);

        List<AgencyEntity> agencyList = agencyService.getActiveList();

        model.addAttribute("list", distributionSummary);
        model.addAttribute("employeeList", dealerList);
        model.addAttribute("agencyList", agencyList);

        return "retail/loan/dataEntry/distribution/auto/list";
    }*/


    @GetMapping("list")
    public String getTemporaryDistributionList(Model model) {

        List<LoanAutoDistributionDto> distributionSummary = service.getCurrentMonthAllocatedList();
//        List<String> exceptionUnits = Arrays.asList("SAM", "Write Off", "Legal"); // Avoid employees from SAM, Legal or Write Off
//        List<EmployeeInfoDto> dealerList = employeeService.findEmployeePinsByUnitAndDesignation(Arrays.asList("Dealer", "Supervisor"), "Loan", exceptionUnits);
//
//        List<AgencyEntity> agencyList = agencyService.getActiveList();

        model.addAttribute("list", distributionSummary);
//        model.addAttribute("employeeList", dealerList);
//        model.addAttribute("agencyList", agencyList);

//        return "retail/loan/dataEntry/distribution/auto/list";
        return "retail/loan/dataEntry/distribution/auto/distributionlist";
    }

    @GetMapping("redistribute")
    public String redistributeDelinquentAccounts() {
        autoDistributionService.getCurrentMonthDelinquentAccountsFromClientApi();
        autoDistributionService.temporarilyDistributeDelinquentAccounts();
        return "redirect:list";
    }

}
