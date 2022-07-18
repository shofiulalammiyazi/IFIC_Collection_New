package com.csinfotechbd.collection.samd.setup.bbApprovalRequirement;

import java.util.List;

public interface BBApprovalRequirementService {
    BBApprovalRequirement save(BBApprovalRequirement bbApprovalRequirement);

    List<BBApprovalRequirement> findAll();

    BBApprovalRequirement findBBApprovalRequirementById(Long id);
}
