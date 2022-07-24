package com.unisoft.collection.samd.setup.bbApprovalRequirement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BBApprovalRequirementServiceImpl implements BBApprovalRequirementService{

    @Autowired
    private BBApprovalRequirementRepository bbApprovalRequirementRepository;


    @Override
    public BBApprovalRequirement save(BBApprovalRequirement bbApprovalRequirement) {
        BBApprovalRequirement bbApprovalRequirement1 = bbApprovalRequirementRepository.save(bbApprovalRequirement);
        return bbApprovalRequirement1;
    }

    @Override
    public List<BBApprovalRequirement> findAll() {
        return bbApprovalRequirementRepository.findAll();
    }

    @Override
    public BBApprovalRequirement findBBApprovalRequirementById(Long id) {
        return bbApprovalRequirementRepository.findBBApprovalRequirementById(id);
    }
}
