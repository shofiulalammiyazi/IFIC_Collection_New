package com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationController;

import com.google.gson.Gson;
import com.unisoft.collection.dashboard.AdvanceSearchPayload;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationService.AccountInformationService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/account-information/")
public class AccountInformationController {

    @Autowired
    private AccountInformationService accountInformationService;


    @GetMapping("get-data")
    public void fetchData(){
        accountInformationService.getAccountInformationData();

    }

    @GetMapping("get-by-account-no")
    public AccountInformationEntity getByAccountId(@RequestParam("accountNo") String accountNo){
        AccountInformationEntity entity = accountInformationService.getAccountInformation(accountNo);
        return accountInformationService.getAccountInformation(accountNo);
    }

    @GetMapping("advanced-search")
    public List<AccountInformationEntity> advancedSearch(@RequestParam(value = "accountNo") String accountNo, @RequestParam(value = "cifno")String cif,
                                                   @RequestParam(value = "customerName")String customerName, @RequestParam(value = "motherName")String motherName,
                                                   @RequestParam(value = "mobileNo")String mobileNo, @RequestParam(value = "nid")String nid, @RequestParam(value = "dob")String dob,
                                                   @RequestParam(value = "email")String email, @RequestParam(value = "passportNo")String passportNo, @RequestParam(value = "organization")String organization,
                                                   @RequestParam(value = "linkAccount")String linkAccount,
                                                   @RequestParam(value = "customerId") String customerId, @RequestParam(value = "autoDebit")String autoDebit, @RequestParam(value = "loanId")String loanId,
                                                   @RequestParam(value = "clsFlag")String clsFlag,@RequestParam(value = "active")String active){
        return accountInformationService.advancedSearch(accountNo, cif, customerName, motherName, mobileNo, nid, dob, email, passportNo, organization, linkAccount, customerId, autoDebit, loanId, clsFlag, active);
    }
}
