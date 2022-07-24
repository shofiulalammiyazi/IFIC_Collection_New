package com.unisoft.collection.samd.setup.proposalPurpose;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposalPurposeRepository extends JpaRepository<ProposalPurpose, Long> {
}
