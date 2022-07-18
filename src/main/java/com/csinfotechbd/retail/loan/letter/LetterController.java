package com.csinfotechbd.retail.loan.letter;

import com.csinfotechbd.collection.distribution.loan.LoanAccountDistributionRepository;
import com.csinfotechbd.collection.distribution.loan.LoanController;
import com.csinfotechbd.collection.distribution.loan.LoanViewModel;
import com.csinfotechbd.collection.distribution.loan.loanAccount.LoanAccountRepository;
import com.csinfotechbd.collection.settings.agency.AgencyService;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeService;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntityRepository;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/collection/settings/issueletter/")
public class LetterController {
    private LoanController loanController;
    private AgencyService agencyService;
    private EmployeeService employeeService;
    private LoanAccountRepository loanAccountRepository;
    private LoanAccountDistributionRepository loanAccountDistributionRepository;
    private CustomerBasicInfoEntityRepository customerBasicInfoEntityRepository;
    private GeneratedLetterService generatedLetterService;

    @GetMapping("list")
    public String accountList(Model model) {
        List<LoanViewModel> loanViewModels = loanController.getLoanViewModels();
        List<EmployeeInfoEntity> dealerList = employeeService.getDealerList();
        List<EmployeeInfoEntity> superVisorList = employeeService.getSuperVisorList();
        List<EmployeeInfoEntity> teamLeaderList = employeeService.getTeamLeaderList();
        dealerList.addAll(superVisorList);
        dealerList.addAll(teamLeaderList);
        Gson gson = new Gson();
        model.addAttribute("loanviewlist", loanViewModels);
        model.addAttribute("loanviewlistJson", gson.toJson(loanViewModels));
        model.addAttribute("dealerList", dealerList);
        model.addAttribute("agencyList", agencyService.getAll());
        model.addAttribute("letterTypes", getLetterTypes());
        return "retail/loan/letter/allocatedLoanList";
    }

    @GetMapping("create")
    public String generateLetter(@RequestParam(name = "account", required = true) String Account,
                                 @RequestParam(name = "letterType", required = true) String letterType,
                                 Model model) {

        CustomerBasicInfoEntity customer = customerBasicInfoEntityRepository.findByAccountNo(Account);
        model.addAttribute("customer", customer);
//        CustomerLoanProfile customerLoanProfile = getCbsLoanProfile(Account);
//        Gson gson = new Gson();
//        model.addAttribute("customerProfileLoan", gson.toJson(customerLoanProfile));

        Gson gson = new Gson();
        model.addAttribute("customerProfileLoan", gson.toJson(new HashMap<String, String>()));


        return "retail/loan/letter/" + letterType;
    }

    public List<String> getLetterTypes() {
        List<String> letterTypes = new ArrayList<>();
        letterTypes.add("CallUp");
        letterTypes.add("Declined");
        letterTypes.add("Outstanding");
        letterTypes.add("RecoveryAssistance");
        letterTypes.add("Reminder");
        letterTypes.add("Reply Reschedule");
        letterTypes.add("Reschedule");
        letterTypes.add("Settlement");
        letterTypes.add("Settlement Letter");
        return letterTypes;
    }

//    private CustomerLoanProfile getCbsLoanProfile(String accountNo) {
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//        HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
//
//        String baseApiUrl = "http://ws.lankabangla.com:8010/cmis-v1.1/getCustomerProfileLoan.php?" +
//                "username=cmis&password=716260ae41ae6bd27735384bf9ac619d&acc=" + accountNo;
//
//        ResponseEntity<CustomerLoanProfileRoot> customerProfile =
//                restTemplate.exchange(baseApiUrl, HttpMethod.GET, httpEntity, CustomerLoanProfileRoot.class);
//        if (customerProfile.getBody() == null) return null;
//        return customerProfile.getBody().getData().get(0);
//    }

    @PostMapping("generated-letter")
    @ResponseBody
    public List<GeneratedLetter> generatedLetter(@RequestBody List<GeneratedLetter> generatedLetters){
        return generatedLetterService.saveAll(generatedLetters);
    }


}
