package com.csinfotechbd.collection.samd.setup.proposalStatus;

import com.csinfotechbd.collection.samd.dataEntry.proposalManagementRescheduling.proposalDetails.ProposalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProposalStatusServiceImpl implements ProposalStatusService {

    @Autowired
    private ProposalStatusRepository proposalStatusRepository;


    @Override
    public ProposalStatus save(ProposalStatus proposalStatus) {
        ProposalStatus proposalStatus1 = proposalStatusRepository.save(proposalStatus);
        return proposalStatus1;
    }

    @Override
    public List<ProposalStatus> getAllProposalStatus() {
        return proposalStatusRepository.findAll();
    }

    @Override
    public ProposalStatus findProposalStatusById(Long id) {
        ProposalStatus proposalStatus = proposalStatusRepository.findProposalStatusById(id);
        return proposalStatus;
    }
}
