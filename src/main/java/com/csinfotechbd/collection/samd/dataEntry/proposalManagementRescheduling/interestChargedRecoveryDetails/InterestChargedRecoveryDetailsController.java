package com.csinfotechbd.collection.samd.dataEntry.proposalManagementRescheduling.interestChargedRecoveryDetails;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/collection/samd/data-entry/proposal-management-rescheduling/interest-charged-recovery-details")
public class InterestChargedRecoveryDetailsController {

    @Autowired
    private InterestChargedRecoveryDetailsService interestChargedRecoveryDetailsService;


    @PostMapping("/save")
    @ResponseBody
    public InterestChargedRecoveryDetails createInterestChargedRecoveryDetails(InterestChargedRecoveryDetails interestChargedRecoveryDetails){
        InterestChargedRecoveryDetails interestChargedRecoveryDetails1 = interestChargedRecoveryDetailsService.save(interestChargedRecoveryDetails);
        return interestChargedRecoveryDetails1;
    }



    @GetMapping("/list")
    @ResponseBody
    public List<InterestChargedRecoveryDetails> getInterestChargedRecoveryDetailsList(@RequestParam String customerId){
        List<InterestChargedRecoveryDetails>chargedRecoveryDetailsList = interestChargedRecoveryDetailsService.findInterestChargedRecoveryDetailsList(customerId);
        return chargedRecoveryDetailsList;
    }
}
