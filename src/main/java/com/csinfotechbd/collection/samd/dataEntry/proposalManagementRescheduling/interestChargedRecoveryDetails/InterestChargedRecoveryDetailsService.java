package com.csinfotechbd.collection.samd.dataEntry.proposalManagementRescheduling.interestChargedRecoveryDetails;

import java.util.List;

public interface InterestChargedRecoveryDetailsService {
    InterestChargedRecoveryDetails save(InterestChargedRecoveryDetails interestChargedRecoveryDetails);

    List<InterestChargedRecoveryDetails> findInterestChargedRecoveryDetailsList(String customerId);
}
