package com.unisoft.collection.samd.dataEntry.visitReport.summaryStatusBranchPosition;

public interface SummaryStatusBranchPositionService {
    SummaryStatusBranchPosition save(SummaryStatusBranchPosition position);

    SummaryStatusBranchPosition findSummaryStatusBranchPositionByCustomerId(String customerId);
}
