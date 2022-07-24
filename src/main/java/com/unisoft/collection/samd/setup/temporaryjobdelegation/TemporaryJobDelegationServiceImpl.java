package com.unisoft.collection.samd.setup.temporaryjobdelegation;


import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TemporaryJobDelegationServiceImpl implements TemporaryJobDelegationService{

    @Autowired
    private TemporaryJobDelegationRepository temporaryJobDelegationRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    @Override
    public TemporaryJobDelegation saveTemporaryJobDelegation(TemporaryJobDelegation temporaryJobDelegation, UserPrincipal userPrincipal) {
        boolean isNewEntity = false;
        TemporaryJobDelegation previousEntity = new TemporaryJobDelegation();

        if (temporaryJobDelegation.getId() == null)
            isNewEntity = true;
        else {
            TemporaryJobDelegation oldEntity = temporaryJobDelegationRepository.getOne(temporaryJobDelegation.getId());
            BeanUtils.copyProperties(oldEntity, previousEntity);
        }

        temporaryJobDelegation.setCreatedDate(new Date());
        temporaryJobDelegation.setCreatedBy(userPrincipal.getUsername());
        temporaryJobDelegationRepository.save(temporaryJobDelegation);

        if (isNewEntity)
            auditTrailService.saveCreatedData("Temporary Job Delegation", temporaryJobDelegation);
        else
            auditTrailService.saveUpdatedData("Temporary Job Delegation", previousEntity, temporaryJobDelegation);

        return temporaryJobDelegation;
    }

    @Override
    public List<TemporaryJobDelegation> findAllTemporaryJobDelegation() {
        return temporaryJobDelegationRepository.findAll();
    }

    @Override
    public TemporaryJobDelegation findById(Long id) {
        return temporaryJobDelegationRepository.findTemporaryJobDelegationById(id);
    }
}
