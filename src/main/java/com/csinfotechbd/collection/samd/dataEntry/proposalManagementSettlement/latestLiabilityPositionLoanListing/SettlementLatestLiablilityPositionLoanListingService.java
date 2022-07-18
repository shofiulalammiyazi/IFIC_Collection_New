package com.csinfotechbd.collection.samd.dataEntry.proposalManagementSettlement.latestLiabilityPositionLoanListing;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SettlementLatestLiablilityPositionLoanListingService {

    private final SettlementLatestLiabilityPositionLoanListingRepository settlementLatestLiabilityPositionLoanListingRepository;

    public SettlementLatestLiabilityPositionLoanListing save(SettlementLatestLiabilityPositionLoanListing latestLiabilityPositionLoanListing) {
        SettlementLatestLiabilityPositionLoanListing loanListing = settlementLatestLiabilityPositionLoanListingRepository.save(latestLiabilityPositionLoanListing);
        return loanListing;
    }

    public List<SettlementLatestLiabilityPositionLoanListing> findByCustomerId(String customerId) {
        List<SettlementLatestLiabilityPositionLoanListing>listings = settlementLatestLiabilityPositionLoanListingRepository.findLatestLiabilityPositionLoanListingByCustomerId(customerId);
        return listings;
    }

    public SettlementLatestLiabilityPositionLoanListing findById(Long id) {
        SettlementLatestLiabilityPositionLoanListing loanListing = settlementLatestLiabilityPositionLoanListingRepository.findLatestLiabilityPositionLoanListingById(id);
        return loanListing;
    }
}
