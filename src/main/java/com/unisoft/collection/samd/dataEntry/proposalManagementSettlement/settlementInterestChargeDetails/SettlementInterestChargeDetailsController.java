package com.unisoft.collection.samd.dataEntry.proposalManagementSettlement.settlementInterestChargeDetails;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/collection/samd/data-entry/settlement-interest-charge-details")
public class SettlementInterestChargeDetailsController {

    private final SettlementInterestChargeDetailsService settlementInterestChargeDetailsService;

    @PostMapping("/create")
    public SettlementInterestChargeDetails create(SettlementInterestChargeDetails interestChargedRecoveryDetails){
        interestChargedRecoveryDetails = settlementInterestChargeDetailsService.save(interestChargedRecoveryDetails);
        return interestChargedRecoveryDetails;
    }

    @GetMapping("/get-by-customer-id")
    public List<SettlementInterestChargeDetails> getByCustomerId(@RequestParam String customerId){
        List<SettlementInterestChargeDetails> allData = settlementInterestChargeDetailsService.findInterestChargedRecoveryDetailsList(customerId);
        return allData;
    }
}
