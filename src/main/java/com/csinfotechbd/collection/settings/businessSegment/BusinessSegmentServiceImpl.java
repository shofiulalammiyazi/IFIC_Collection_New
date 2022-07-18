package com.csinfotechbd.collection.settings.businessSegment;

import com.csinfotechbd.audittrail.AuditTrailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessSegmentServiceImpl implements BusinessSegmentService {

    @Autowired
    BusinessSegmentRepository businessSegmentRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    @Override
    public void save(BusinessSegment BusinessSegment) {
        boolean isNewEntity = false;
        if (BusinessSegment.getId() == null)
            isNewEntity = true;

        businessSegmentRepository.save(BusinessSegment);

        if (isNewEntity)
            auditTrailService.saveCreatedData("Business Segment", BusinessSegment);
    }

    @Override
    public List<BusinessSegment> getList() {
        return businessSegmentRepository.findAll();
    }

    @Override
    public BusinessSegment getById(Long id) {
        Optional<BusinessSegment> optBusinessSegment = businessSegmentRepository.findById(id);
        if(optBusinessSegment.isPresent())
            return optBusinessSegment.get();
        else
            return null;
    }

    @Override
    public void update(BusinessSegment BusinessSegment) {
        BusinessSegment oldBusinessSegment = getById(BusinessSegment.getId());
        BusinessSegment previousBusinessSegment = new BusinessSegment();
        BeanUtils.copyProperties(oldBusinessSegment, previousBusinessSegment);

        BusinessSegment.setCreatedDate(oldBusinessSegment.getCreatedDate());
        BusinessSegment.setCreatedBy(oldBusinessSegment.getCreatedBy());
        save(BusinessSegment);
        auditTrailService.saveUpdatedData("Business Segment", previousBusinessSegment, BusinessSegment);
    }
}
