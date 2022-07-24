package com.unisoft.collection.samd.dataEntry.proposalManagementRescheduling.interestChargedRecoveryDetails;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestChargedRecoveryDetailsRepository extends JpaRepository<InterestChargedRecoveryDetails, Long> {

    List<InterestChargedRecoveryDetails> findInterestChargedRecoveryDetailsListByCustomerIdOrderByYearAsc(String customerId);
}
