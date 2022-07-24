package com.unisoft.collection.samd.dataEntry.visitReport.summaryStatusBranchPosition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummaryStatusBranchPositionRepository extends JpaRepository<SummaryStatusBranchPosition, Long> {
    SummaryStatusBranchPosition findSummaryStatusBranchPositionByCustomerId(String customerId);
}
