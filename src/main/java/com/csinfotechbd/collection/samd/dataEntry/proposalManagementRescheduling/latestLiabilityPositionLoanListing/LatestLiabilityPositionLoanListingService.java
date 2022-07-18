package com.csinfotechbd.collection.samd.dataEntry.proposalManagementRescheduling.latestLiabilityPositionLoanListing;

import java.util.List;

public interface LatestLiabilityPositionLoanListingService {
    LatestLiabilityPositionLoanListing save(LatestLiabilityPositionLoanListing latestLiabilityPositionLoanListing);

    List<LatestLiabilityPositionLoanListing> findByCustomerId(String customerId);

    LatestLiabilityPositionLoanListing findLatestLiabilityPositionLoanListingById(Long id);
}
