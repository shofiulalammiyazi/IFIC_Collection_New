package com.csinfotechbd.collection.samd.setup.businessSegment;

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.collection.settings.location.LocationEntity;
import com.csinfotechbd.collection.settings.location.LocationService;
import com.csinfotechbd.common.CommonService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BusinessSegmentSamdService implements CommonService<BusinessSegmentSamd> {

    @Autowired
    private LocationService locationService;

    @Autowired
    private BusinessSegmentSamdRepository businessSegmentSamdRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    @Override
    public Map<String, Object> storeData(BusinessSegmentSamd data){
        Map<String, Object> response = new HashMap<>();
        List<String> errors = this.validate(data);

        if(errors.size() == 0){
            this.setMultiselectValues(data);
            businessSegmentSamdRepository.save(data);
            auditTrailService.saveCreatedData("Business Segment ", data);

        }
        else{
            response.put("outcome","success");
            response.put("errorMessages",errors);
        }

        return response;
    }

    @Override
    public Map<String, Object> updateData(BusinessSegmentSamd data){
        Map<String, Object> response = new HashMap<>();
        List<String> errors = this.validate(data);

        if (errors.size() == 0){
            BusinessSegmentSamd oldEntity = businessSegmentSamdRepository.getOne(data.getId());
            BusinessSegmentSamd previousEntity = new BusinessSegmentSamd();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            this.setMultiselectValues(data);
            businessSegmentSamdRepository.save(data);
            auditTrailService.saveUpdatedData("Business Segment Management", previousEntity, data);

        }
        else{
            response.put("outcome", "success");
            response.put("errorMessages", errors);
        }

        return response;
    }

    public void setMultiselectValues(BusinessSegmentSamd businessSegmentSamd){

        List<LocationEntity> locationEntities = new ArrayList<>();
        for (String id : businessSegmentSamd.getLocationIds()) {
            LocationEntity locationEntity = locationService.getById(Long.valueOf(id));
            locationEntities.add(locationEntity);
        }
        businessSegmentSamd.setLocations(locationEntities);

    }

    @Override
    public Map<String, Object> deleteData(BusinessSegmentSamd data) {
        return null;
    }

    @Override
    public BusinessSegmentSamd findDataById(Long id) {
        BusinessSegmentSamd businessSegmentSamd = businessSegmentSamdRepository.findBusinessSegmentById(id);
        return businessSegmentSamd;
    }

    @Override
    public List<BusinessSegmentSamd> findAllData() {
        List<BusinessSegmentSamd> allData = businessSegmentSamdRepository.findAll();
        return allData;
    }

    @Override
    public List<String> validate(BusinessSegmentSamd data) {
        List<String> errors = new ArrayList<>();
        return errors;
    }

}
