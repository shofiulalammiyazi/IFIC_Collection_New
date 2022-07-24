package com.unisoft.collection.samd.setup.proposalStatus;

import java.util.List;

public interface ProposalStatusService {
    ProposalStatus save(ProposalStatus proposalStatus);

    List<ProposalStatus> getAllProposalStatus();

    ProposalStatus findProposalStatusById(Long id);
}
