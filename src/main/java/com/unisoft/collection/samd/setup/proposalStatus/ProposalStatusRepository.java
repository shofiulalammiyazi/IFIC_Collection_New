package com.unisoft.collection.samd.setup.proposalStatus;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposalStatusRepository extends JpaRepository<ProposalStatus, Long> {
    ProposalStatus findProposalStatusById(Long id);
}
