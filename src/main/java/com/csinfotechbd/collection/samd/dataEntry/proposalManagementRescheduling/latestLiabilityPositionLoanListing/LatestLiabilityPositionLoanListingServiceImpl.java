package com.csinfotechbd.collection.samd.dataEntry.proposalManagementRescheduling.latestLiabilityPositionLoanListing;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LatestLiabilityPositionLoanListingServiceImpl implements LatestLiabilityPositionLoanListingService{

    @Autowired
    private LatestLiabilityPositionLoanListingRepository repository;


    @Override
    public LatestLiabilityPositionLoanListing save(LatestLiabilityPositionLoanListing latestLiabilityPositionLoanListing) {
        LatestLiabilityPositionLoanListing loanListing = repository.save(latestLiabilityPositionLoanListing);
        return loanListing;
    }

    @Override
    public List<LatestLiabilityPositionLoanListing> findByCustomerId(String customerId) {
        List<LatestLiabilityPositionLoanListing>listings = repository.findLatestLiabilityPositionLoanListingByCustomerId(customerId);
        return listings;
    }

    @Override
    public LatestLiabilityPositionLoanListing findLatestLiabilityPositionLoanListingById(Long id) {
        LatestLiabilityPositionLoanListing loanListing = repository.findLatestLiabilityPositionLoanListingById(id);
        return loanListing;
    }
}
