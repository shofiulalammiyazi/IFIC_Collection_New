package com.csinfotechbd.collection.samd.dataEntry.proposalManagementSettlement.proposalDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettlementProposalDetailsFilesRepository extends JpaRepository<SettlementProposalDetailsFiles, Long> {
}
