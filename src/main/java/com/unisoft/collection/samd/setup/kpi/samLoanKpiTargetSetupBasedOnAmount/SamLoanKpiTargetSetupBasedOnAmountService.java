package com.unisoft.collection.samd.setup.kpi.samLoanKpiTargetSetupBasedOnAmount;

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.collection.settings.dpdBucket.DPDBucketEntity;
import com.unisoft.collection.settings.dpdBucket.DPDBucketService;
import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.collection.settings.location.LocationService;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.productType.ProductTypeService;
import com.unisoft.collection.settings.sectorGroup.SectorGroupEntity;
import com.unisoft.collection.settings.sectorGroup.SectorGroupService;
import com.unisoft.collection.settings.zone.ZoneEntity;
import com.unisoft.collection.settings.zone.ZoneService;
import com.unisoft.common.CommonService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SamLoanKpiTargetSetupBasedOnAmountService implements CommonService<SamLoanKpiTargetSetupBasedOnAmount> {

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private DPDBucketService dpdBucketService;

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private SectorGroupService sectorGroupService;

    @Autowired
    private SamLoanKpiTargetSetupBasedOnAmountRepository samLoanKpiTargetSetupBasedOnAmountRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    @Override
    public Map<String, Object> storeData(SamLoanKpiTargetSetupBasedOnAmount data) {
        Map<String, Object> response = new HashMap<>();
        List<String> errors = this.validate(data);

        if (errors.size() == 0){
            this.setMultiselectValues(data);
            samLoanKpiTargetSetupBasedOnAmountRepository.save(data);
            auditTrailService.saveCreatedData("SAM KPI Target Setup (Weight) based on Amount-Loan", data);
        }
        else{
            response.put("outcome", "success");
            response.put("errorMessages", errors);
        }

        return response;
    }

    @Override
    public Map<String, Object> updateData(SamLoanKpiTargetSetupBasedOnAmount data) {
        Map<String, Object> response = new HashMap<>();
        List<String> errors = this.validate(data);

        if (errors.size() == 0){
            SamLoanKpiTargetSetupBasedOnAmount oldEntity = samLoanKpiTargetSetupBasedOnAmountRepository.getOne(data.getId());
            SamLoanKpiTargetSetupBasedOnAmount previousEntity = new SamLoanKpiTargetSetupBasedOnAmount();
            BeanUtils.copyProperties(oldEntity,previousEntity);

            this.setMultiselectValues(data);
            samLoanKpiTargetSetupBasedOnAmountRepository.save(data);
            auditTrailService.saveUpdatedData("SAM KPI Target Setup (Weight) based on Amount-Loan", previousEntity, data);
        }
        else{
            response.put("outcome", "success");
            response.put("errorMessages", errors);
        }

        return response;
    }

    public void setMultiselectValues(SamLoanKpiTargetSetupBasedOnAmount data){
        List<ProductTypeEntity> productTypeEntities = new ArrayList<>();
        for (String id : data.getProductTypeIds()) {
            ProductTypeEntity productTypeEntity = productTypeService.getById(Long.valueOf(id));
            productTypeEntities.add(productTypeEntity);
        }
        data.setProductTypes(productTypeEntities);

        List<LocationEntity> locationEntities = new ArrayList<>();
        for (String id : data.getLocationIds()) {
            LocationEntity locationEntity = locationService.getById(Long.valueOf(id));
            locationEntities.add(locationEntity);
        }
        data.setLocations(locationEntities);

        List<ZoneEntity> zoneEntities = new ArrayList<>();
        for (String id : data.getZoneIds()) {
            ZoneEntity zoneEntity = zoneService.getById(Long.valueOf(id));
            zoneEntities.add(zoneEntity);
        }
        data.setZones(zoneEntities);

        List<DPDBucketEntity> dpdBucketEntities = new ArrayList<>();
        for (String id : data.getDpdBucketIds()) {
            DPDBucketEntity dpdBucketEntity = dpdBucketService.getById(Long.valueOf(id));
            dpdBucketEntities.add(dpdBucketEntity);
        }
        data.setDpdBuckets(dpdBucketEntities);

        List<SectorGroupEntity> sectorGroupEntities = new ArrayList<>();
        for (String id : data.getSectorGroupIds()) {
            SectorGroupEntity sectorGroupEntity = sectorGroupService.getById(Long.valueOf(id));
            sectorGroupEntities.add(sectorGroupEntity);
        }
        data.setSectorGroups(sectorGroupEntities);
    }

    @Override
    public Map<String, Object> deleteData(SamLoanKpiTargetSetupBasedOnAmount data) {
        return null;
    }

    @Override
    public SamLoanKpiTargetSetupBasedOnAmount findDataById(Long id) {
        SamLoanKpiTargetSetupBasedOnAmount targetSetupBasedOnAmount = samLoanKpiTargetSetupBasedOnAmountRepository.findSamLoanKpiTargetSetupBasedOnAmountById(id);

        return targetSetupBasedOnAmount;
    }

    @Override
    public List<SamLoanKpiTargetSetupBasedOnAmount> findAllData() {
        List<SamLoanKpiTargetSetupBasedOnAmount> allData = samLoanKpiTargetSetupBasedOnAmountRepository.findAll();
        return allData;
    }

    @Override
    public List<String> validate(SamLoanKpiTargetSetupBasedOnAmount data) {
        List<String> errors = new ArrayList<>();
        return errors;
    }
}
