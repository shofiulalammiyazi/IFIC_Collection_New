package com.csinfotechbd.collection.settings.location;
/*
Created by Monirul Islam at 6/23/2019
*/

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.user.UserPrincipal;
import org.codehaus.jackson.map.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    public List<LocationEntity> getLocList() {
        return locationRepository.findAll();
    }

    public String save(LocationEntity locationEntity) {
        boolean isNewEntity = false;
        LocationEntity previousEntity = new LocationEntity();

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (locationEntity.getId() == null) {
            locationEntity.setCreatedBy(user.getUsername());
            locationEntity.setCreatedDate(new Date());
            isNewEntity = true;
        } else {
            LocationEntity oldEntity = locationRepository.getOne(locationEntity.getId());
            BeanUtils.copyProperties(oldEntity, previousEntity);

            locationEntity.setModifiedBy(user.getUsername());
            locationEntity.setModifiedDate(new Date());
        }
        locationRepository.save(locationEntity);

        if (isNewEntity)
            auditTrailService.saveCreatedData("Location", locationEntity);
        else
            auditTrailService.saveUpdatedData("Location", previousEntity, locationEntity);
        return "1";
    }

    public LocationEntity getById(Long id) {
        return locationRepository.findById(id).orElse(new LocationEntity());
    }

    public List<LocationEntity> getActiveList() {
        return locationRepository.findByEnabled(true);
    }
}
