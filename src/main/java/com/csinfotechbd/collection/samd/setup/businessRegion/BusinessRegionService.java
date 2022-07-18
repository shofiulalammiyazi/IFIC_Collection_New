package com.csinfotechbd.collection.samd.setup.businessRegion;

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
public class BusinessRegionService implements CommonService<BusinessRegionSamd> {

    @Autowired
    private LocationService locationService;

    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private BusinessRegionSamdRepository businessRegionSamdRepository;

    @Override
    public Map<String, Object> storeData(BusinessRegionSamd data){
        Map<String,Object> response = new HashMap<>();
        List<String> errors = this.validate(data);

        if(errors.size() == 0){
            this.setMultiselectValues(data);
            businessRegionSamdRepository.save(data);
            auditTrailService.saveCreatedData("Business Region Management", data);
        }
        else{
            response.put("outcome", "success");
            response.put("errorMessages", errors);
        }

        return response;
    }

    @Override
    public Map<String, Object> updateData(BusinessRegionSamd data) {
        Map<String, Object> response = new HashMap<>();
        List<String> errors = this.validate(data);

        if (errors.size() == 0){
            BusinessRegionSamd oldEntity = businessRegionSamdRepository.getOne(data.getId());
            BusinessRegionSamd previousEntity = new BusinessRegionSamd();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            this.setMultiselectValues(data);
            businessRegionSamdRepository.save(data);
            auditTrailService.saveUpdatedData("Business Region Management", previousEntity, data);

        }
        else{
            response.put("outcome", "success");
            response.put("errorMessages", errors);
        }

        return response;
    }

    public void setMultiselectValues(BusinessRegionSamd businessRegionSamd){

        List<LocationEntity> locationEntities = new ArrayList<>();
        for (String id : businessRegionSamd.getLocationIds()) {
            LocationEntity locationEntity = locationService.getById(Long.valueOf(id));
            locationEntities.add(locationEntity);
        }
        businessRegionSamd.setLocations(locationEntities);

    }

    @Override
    public Map<String, Object> deleteData(BusinessRegionSamd data) {
        return null;
    }

    @Override
    public BusinessRegionSamd findDataById(Long id) {
        BusinessRegionSamd businessRegionSamd = businessRegionSamdRepository.findBusinessRegionById(id);
        return businessRegionSamd;
    }

    @Override
    public List<BusinessRegionSamd> findAllData() {
        List<BusinessRegionSamd> allData = businessRegionSamdRepository.findAll();
        return allData;
    }

    @Override
    public List<String> validate(BusinessRegionSamd data) {
        List<String> errors = new ArrayList<>();
        return errors;
    }


}
