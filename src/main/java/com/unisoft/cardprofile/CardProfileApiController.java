package com.unisoft.cardprofile;

import com.unisoft.collection.accountescalation.AccountEscalation;
import com.unisoft.collection.accountescalation.AccountEscalationPayLoad;
import com.unisoft.collection.accountescalation.AccountEscalationRepository;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicInfo;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicRepository;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeRepository;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntityRepository;
import com.unisoft.user.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/collection/card/profile/api")
public class CardProfileApiController {

    private AccountEscalationRepository accountEscalationRepository;

    private PeopleAllocationLogicRepository peopleAllocationLogicRepository;

    private EmployeeRepository employeeRepository;

    private CustomerBasicInfoEntityRepository customerBasicInfoEntityRepository;

    @GetMapping("/list")
    public List<AccountEscalation> getAccountEscalation(@RequestParam(value = "cardAccNumber") String cardNumber) {
        return getByAccountNumberOrderByCreatedDateDesc(cardNumber);
    }

    @PostMapping("/save")
    public List<AccountEscalation> setAccountEscalation(@Valid @RequestBody AccountEscalationPayLoad accountEscalationPayLoad) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employeeInfoEntity = employeeRepository.findByPin(principal.getUsername());
        PeopleAllocationLogicInfo peopleAllocationLogicInfo = peopleAllocationLogicRepository.findByDealerAndUnit(employeeInfoEntity, "Card");

        if (peopleAllocationLogicInfo != null) {

            AccountEscalation accountEscalation = new AccountEscalation();
            accountEscalation.setAccountNumber(accountEscalationPayLoad.getAccount());
            accountEscalation.setNote(accountEscalationPayLoad.getNotes());
            accountEscalation.setTypeCheck("CARD");
            accountEscalation.setCreatedDate(new Date());
            accountEscalation.setStatus("Pending");
            accountEscalation.setFromUserName(principal.getFirstName());
            accountEscalation.setFromUserPin(principal.getUsername());
            accountEscalation.setDealerPin(principal.getUsername());

            accountEscalation.setToUserName(peopleAllocationLogicInfo.getTeamlead().getUser().getFirstName());
            accountEscalation.setToUserPin(peopleAllocationLogicInfo.getTeamlead().getPin());
            accountEscalationRepository.save(accountEscalation);
        }
        return getByAccountNumberOrderByCreatedDateDesc(accountEscalationPayLoad.getAccount());
    }

    public List<AccountEscalation> getByAccountNumberOrderByCreatedDateDesc(String cardNumber) {
        return accountEscalationRepository.findByAccountNumberOrderByCreatedDateDesc(cardNumber);
    }

    @PostMapping("upload-letter")
    public ResponseEntity<?> uploadLetter(@RequestParam("customerId") String customerId,
                                          @RequestParam("file")MultipartFile file) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<CustomerBasicInfoEntity> customer = customerBasicInfoEntityRepository.findById(new Long(customerId));

        if (!customer.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        // TODO: save letter file to dms

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
