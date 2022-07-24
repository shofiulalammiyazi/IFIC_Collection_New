package com.unisoft.collection.samd.dataEntry.visitReport.summaryStatusBranchPosition;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SummaryStatusBranchPositionServiceImpl implements SummaryStatusBranchPositionService{

    @Autowired
    private SummaryStatusBranchPositionRepository repository;

    @Override
    public SummaryStatusBranchPosition save(SummaryStatusBranchPosition position) {
        SummaryStatusBranchPosition summaryStatusBranchPosition = repository.save(position);
        return summaryStatusBranchPosition;
    }

    @Override
    public SummaryStatusBranchPosition findSummaryStatusBranchPositionByCustomerId(String customerId) {
        SummaryStatusBranchPosition summaryStatusBranchPosition = repository.findSummaryStatusBranchPositionByCustomerId(customerId);
        return summaryStatusBranchPosition;
    }
}
