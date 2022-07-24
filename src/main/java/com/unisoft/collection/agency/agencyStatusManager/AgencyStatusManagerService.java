package com.unisoft.collection.agency.agencyStatusManager;


import com.unisoft.audittrail.AuditTrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgencyStatusManagerService {

    @Autowired
    private AgencyStatusManagerRepository agencyStatusManagerRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    public AgencyStatusManagerEntity findFirstByUserIdOrderByIdDesc(Long userId) {
        return agencyStatusManagerRepository.findFirstByUserIdOrderByIdDesc(userId);
    }

    public List<AgencyStatusManagerEntity> findAll() {
        return agencyStatusManagerRepository.findAll();
    }

    public AgencyStatusManagerEntity save(AgencyStatusManagerEntity agencyStatusManagerEntity) {
        return agencyStatusManagerRepository.save(agencyStatusManagerEntity);
    }

    public AgencyStatusManagerEntity findById(Long id) {
        return agencyStatusManagerRepository.findAgencyStatusManagerEntityById(id);
    }
}
