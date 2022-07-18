package com.csinfotechbd.collection.samd.dataEntry.proposalManagementSettlement.settlementInterestChargeDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettlementInterestChargeDetailsRepository extends JpaRepository<SettlementInterestChargeDetails, Long> {
    List<SettlementInterestChargeDetails> findInterestChargedRecoveryDetailsListByCustomerIdOrderByYearAsc(String customerId);
}
