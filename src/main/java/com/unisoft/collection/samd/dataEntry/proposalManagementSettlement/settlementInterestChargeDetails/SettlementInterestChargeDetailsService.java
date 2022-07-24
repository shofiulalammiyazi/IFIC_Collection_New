package com.unisoft.collection.samd.dataEntry.proposalManagementSettlement.settlementInterestChargeDetails;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SettlementInterestChargeDetailsService {

    private final SettlementInterestChargeDetailsRepository settlementInterestChargeDetailsRepository;

    public SettlementInterestChargeDetails save(SettlementInterestChargeDetails interestChargedRecoveryDetails) {
        SettlementInterestChargeDetails recoveryDetails = settlementInterestChargeDetailsRepository.save(interestChargedRecoveryDetails);
        return recoveryDetails;
    }

    public List<SettlementInterestChargeDetails> findInterestChargedRecoveryDetailsList(String customerId) {
        List<SettlementInterestChargeDetails> chargedRecoveryDetailsList = settlementInterestChargeDetailsRepository.findInterestChargedRecoveryDetailsListByCustomerIdOrderByYearAsc(customerId);
        return chargedRecoveryDetailsList;
    }

}
