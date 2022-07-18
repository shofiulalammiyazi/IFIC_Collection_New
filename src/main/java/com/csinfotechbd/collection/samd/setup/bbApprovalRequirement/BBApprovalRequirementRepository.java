package com.csinfotechbd.collection.samd.setup.bbApprovalRequirement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BBApprovalRequirementRepository extends JpaRepository<BBApprovalRequirement, Long> {
    BBApprovalRequirement findBBApprovalRequirementById(Long id);
}
