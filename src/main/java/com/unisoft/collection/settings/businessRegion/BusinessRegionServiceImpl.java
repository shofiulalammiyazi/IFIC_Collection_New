package com.unisoft.collection.settings.businessRegion;

import com.unisoft.audittrail.AuditTrailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessRegionServiceImpl implements BusinessRegionService {

    @Autowired
    BusinessRegionRepository businessRegionRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    @Override
    public void save(BusinessRegionManagement businessRegionManagement) {
        boolean isNewEntity = false;
        if (businessRegionManagement.getId() == null)
            isNewEntity = true;

        businessRegionRepository.save(businessRegionManagement);

        if (isNewEntity)
            auditTrailService.saveCreatedData("Business Region", businessRegionManagement);
    }

    @Override
    public List<BusinessRegionManagement> getList() {
        return businessRegionRepository.findAll();
    }

    @Override
    public BusinessRegionManagement getById(Long id) {
        Optional<BusinessRegionManagement> optBusinessRegionManagement = businessRegionRepository.findById(id);
        if(optBusinessRegionManagement.isPresent())
            return optBusinessRegionManagement.get();
        else
            return null;
    }

    @Override
    public void update(BusinessRegionManagement businessRegionManagement) {
        BusinessRegionManagement oldBusinessRegionManagement = getById(businessRegionManagement.getId());
        BusinessRegionManagement previousEntity = new BusinessRegionManagement();
        BeanUtils.copyProperties(oldBusinessRegionManagement, previousEntity);

        businessRegionManagement.setCreatedDate(oldBusinessRegionManagement.getCreatedDate());
        businessRegionManagement.setCreatedBy(oldBusinessRegionManagement.getCreatedBy());
        save(businessRegionManagement);
        auditTrailService.saveUpdatedData("Business Region", previousEntity, businessRegionManagement);
    }
}
