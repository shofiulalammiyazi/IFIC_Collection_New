package com.unisoft.collection.samd.dataEntry.proposalManagementRescheduling.proposalDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposalDetailsFileRepository extends JpaRepository<ProposalDetailsFiles, Long> {
}
