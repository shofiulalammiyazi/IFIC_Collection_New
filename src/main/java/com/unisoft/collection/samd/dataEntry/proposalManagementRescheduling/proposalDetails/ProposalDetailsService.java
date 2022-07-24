package com.unisoft.collection.samd.dataEntry.proposalManagementRescheduling.proposalDetails;


public interface ProposalDetailsService {
    ProposalDetails save(ProposalDetails proposalDetails);

    ProposalDetails findProposalDetailsByCustomerId(String customerId);

    ProposalDetailsDto getByCustomerId(String customerId);
}
