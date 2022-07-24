package com.unisoft.collection.samd.dataEntry.proposalManagementRescheduling.latestLiabilityPositionLoanListing;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LatestLiabilityPositionLoanListingRepository extends JpaRepository<LatestLiabilityPositionLoanListing, Long> {

    @Query(value = "SELECT * FROM LATEST_LIABILITY_POSITION_LOAN_LISTING WHERE CUSTOMER_ID = ? ", nativeQuery = true)
    List<LatestLiabilityPositionLoanListing> findLatestLiabilityPositionLoanListingByCustomerId(String customerId);

    LatestLiabilityPositionLoanListing findLatestLiabilityPositionLoanListingById(Long id);
}
