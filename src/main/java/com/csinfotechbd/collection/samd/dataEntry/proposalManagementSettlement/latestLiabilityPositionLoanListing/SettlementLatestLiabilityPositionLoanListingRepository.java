package com.csinfotechbd.collection.samd.dataEntry.proposalManagementSettlement.latestLiabilityPositionLoanListing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettlementLatestLiabilityPositionLoanListingRepository extends JpaRepository<SettlementLatestLiabilityPositionLoanListing, Long> {

    List<SettlementLatestLiabilityPositionLoanListing> findLatestLiabilityPositionLoanListingByCustomerId(String customerId);
    SettlementLatestLiabilityPositionLoanListing findLatestLiabilityPositionLoanListingById(Long id);

}
