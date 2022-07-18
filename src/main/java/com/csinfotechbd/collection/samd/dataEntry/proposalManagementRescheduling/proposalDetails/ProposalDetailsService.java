package com.csinfotechbd.collection.samd.dataEntry.proposalManagementRescheduling.proposalDetails;


public interface ProposalDetailsService {
    ProposalDetails save(ProposalDetails proposalDetails);

    ProposalDetails findProposalDetailsByCustomerId(String customerId);

    ProposalDetailsDto getByCustomerId(String customerId);
}
