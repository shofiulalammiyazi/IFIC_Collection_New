package com.csinfotechbd.collection.samd.setup.bBAuditType;


import com.csinfotechbd.collection.samd.setup.loanLiabilityRecoverability.LoanLiabilityRecoverability;
import com.csinfotechbd.collection.samd.setup.loanLiabilityRecoverability.LoanLiabilityRecoverabilityRepository;
import com.csinfotechbd.collection.samd.setup.loanLiabilityRecoverability.LoanLiabilityRecoverabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BBAuditTypeServiceImpl implements BBAuditTypeService{


    @Autowired
    private BBAuditTypeRepository bbAuditTypeRepository;


    @Override
    public List<BBAuditType> findAll() {
        return bbAuditTypeRepository.findAll();
    }

    @Override
    public void save(BBAuditType bbAuditType) {
            bbAuditTypeRepository.save(bbAuditType);
    }

    @Override
    public BBAuditType findBBAuditTypeById(Long id) {
        return bbAuditTypeRepository.findBBAuditTypeById(id);
    }
}
