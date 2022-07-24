package com.unisoft.collection.samd.dataEntry.proposalManagementRescheduling.interestChargedRecoveryDetails;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterestChargedRecoveryDetailsServiceImpl implements InterestChargedRecoveryDetailsService{

    @Autowired
    private InterestChargedRecoveryDetailsRepository recoveryDetailsRepository;


    @Override
    public InterestChargedRecoveryDetails save(InterestChargedRecoveryDetails interestChargedRecoveryDetails) {
        InterestChargedRecoveryDetails recoveryDetails = recoveryDetailsRepository.save(interestChargedRecoveryDetails);
        return recoveryDetails;
    }

    @Override
    public List<InterestChargedRecoveryDetails> findInterestChargedRecoveryDetailsList(String customerId) {
        List<InterestChargedRecoveryDetails> chargedRecoveryDetailsList = recoveryDetailsRepository.findInterestChargedRecoveryDetailsListByCustomerIdOrderByYearAsc(customerId);
        return chargedRecoveryDetailsList;
    }
}
