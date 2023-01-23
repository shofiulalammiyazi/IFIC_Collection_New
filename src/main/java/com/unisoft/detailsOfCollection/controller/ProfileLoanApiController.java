package com.unisoft.detailsOfCollection.controller;


import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.collection.accountescalation.*;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicInfo;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicRepository;
import com.unisoft.collection.settings.dpdBucket.DPDBucketEntity;
import com.unisoft.collection.settings.dpdBucket.DpdBucketRepository;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeRepository;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.productType.ProductTypeRepository;
import com.unisoft.detailsOfCollection.cardviewmodels.AccountInformation;
import com.unisoft.loanApi.model.LoanAccDetails;
import com.unisoft.loanApi.service.RetailLoanUcbApiService;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository.AccountInformationRepository;
import com.unisoft.user.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/collection/loan/profile/api")
public class ProfileLoanApiController {

    private AccountEscalationRepository accountEscalationRepository;

    private PeopleAllocationLogicRepository peopleAllocationLogicRepository;

    private EmployeeRepository employeeRepository;

    private ProductTypeRepository productTypeRepository;

    private DpdBucketRepository dpdBucketRepository;

    private AuditTrailService auditTrailService;

    private RetailLoanUcbApiService apiService;

    private AccountEscalationNoteService accountEscalationNoteService;

    private AccountEscalationService accountEscalationService;

    @Autowired
    private AccountInformationRepository accountInformationRepository;

    @GetMapping("/list")
    public List<AccountEscalation> getAccountEscalation(@RequestParam(value = "cardAccNumber") String cardNumber) {
        return getByAccountNumberOrderByCreatedDateDesc(cardNumber);
    }

    @PostMapping("/save")
    public List<AccountEscalation> setAccountEscalation(@Valid @RequestBody AccountEscalationPayLoad accountEscalationPayLoad) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employeeInfoEntity = employeeRepository.findByPin(principal.getUsername());
        PeopleAllocationLogicInfo peopleAllocationLogicInfo = peopleAllocationLogicRepository.findByDealerAndUnit(employeeInfoEntity, employeeInfoEntity.getUnit());
        AccountInformationEntity accountInformationEntity = accountInformationRepository.getByLoanAccountNo(accountEscalationPayLoad.getAccount());

//        LoanAccDetails loanAccDetails = null;
//        try {
//             loanAccDetails = apiService.getLoanAccountDetails(accountEscalationPayLoad.getAccount());
//        } catch (NullPointerException e){
//            e.printStackTrace();
//        }


        AccountEscalation accountEscalation = new AccountEscalation();
        accountEscalation.setAccountNumber(accountEscalationPayLoad.getAccount());
//        accountEscalation.setNote(accountEscalationPayLoad.getNotes());
        accountEscalation.setTypeCheck(employeeInfoEntity.getUnit());
        accountEscalation.setCreatedDate(new Date());
        accountEscalation.setStatus("Pending");
        accountEscalation.setFromUserName(principal.getFirstName());
        accountEscalation.setFromUserPin(principal.getUsername());

        long od = Long.valueOf(accountInformationEntity.getOverdue() == null
                ? "0":accountInformationEntity.getOverdue());
        long emi = Long.valueOf(accountInformationEntity.getEmiAmount() == null ? "0":accountInformationEntity.getEmiAmount());
        long bucket = 0;
        if(od == 0 || emi == 0)
            bucket = 0;
        else
            bucket = od/emi;

        accountEscalation.setBucket(Double.valueOf(bucket));
        accountEscalation.setCreatedBy(principal.getUsername());
        accountEscalation.setDealerPin(principal.getUsername());

        accountEscalation.setToUserName(peopleAllocationLogicInfo.getTeamlead().getUser().getLastName());
        accountEscalation.setToUserPin(peopleAllocationLogicInfo.getTeamlead().getPin());

        AccountEscalationHistory accountEscalationHistory = new AccountEscalationHistory();
        BeanUtils.copyProperties(accountEscalation, accountEscalationHistory);

        AccountEscalation escalation = accountEscalationRepository.save(accountEscalation);

        accountEscalationService.saveHistory(accountEscalationHistory);

        AccountEscalationNote accountEscalationNote = new AccountEscalationNote();
        accountEscalationNote.setNote(accountEscalationPayLoad.getNotes());
        accountEscalationNote.setStatus("DealerNote");
        accountEscalationNote.setAccountEscalation(escalation);
        accountEscalationNoteService.save(accountEscalationNote);

        auditTrailService.saveCreatedData("Account Escalation", accountEscalation);

        return getByAccountNumberOrderByCreatedDateDesc(accountEscalationPayLoad.getAccount());
    }

    public List<AccountEscalation> getByAccountNumberOrderByCreatedDateDesc(String cardNumber) {
        System.out.println("test");
        return accountEscalationRepository.findByAccountNumberOrderByCreatedDateDesc(cardNumber);
    }

    @GetMapping("/productdetails")
    public ProductTypeEntity getProductDetails(@RequestParam(value = "schemecode") String schemeCode) {
        return productTypeRepository.findByCode(schemeCode);
    }

    @GetMapping("/dpdbucket")
    public DPDBucketEntity getDpdBucketEntity(@RequestParam(value = "dpd") String dpd) {
        DPDBucketEntity dpdBucket = dpdBucketRepository
                .findFirstByMinDpdLessThanEqualAndMaxDpdGreaterThanEqual(
                        Double.parseDouble(dpd), Double.parseDouble(dpd));
        return dpdBucket;
    }
}
