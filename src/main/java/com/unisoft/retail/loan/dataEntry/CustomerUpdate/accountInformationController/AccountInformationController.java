package com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationController;

import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationService.AccountInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
public class AccountInformationController {

    @Autowired
    private AccountInformationService accountInformationService;


    @GetMapping("/get-data")
    public void fetchData(){
        accountInformationService.getAccountInformationData();

    }




}
