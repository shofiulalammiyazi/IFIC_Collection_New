package com.csinfotechbd.collection.samd.setup.kpi.samLoanKpiTargetSetupBasedOnAccount;

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.collection.samd.setup.kpi.samLoanKpiTargetSetupBasedOnAmount.SamLoanKpiTargetSetupBasedOnAmount;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketEntity;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketService;
import com.csinfotechbd.collection.settings.location.LocationEntity;
import com.csinfotechbd.collection.settings.location.LocationService;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeService;
import com.csinfotechbd.collection.settings.sectorGroup.SectorGroupEntity;
import com.csinfotechbd.collection.settings.sectorGroup.SectorGroupService;
import com.csinfotechbd.collection.settings.zone.ZoneEntity;
import com.csinfotechbd.collection.settings.zone.ZoneService;
import com.csinfotechbd.common.CommonService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SamLoanKpiTargetSetupBasedOnAccountService implements CommonService<SamLoanKpiTargetSetupBasedOnAccount> {

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
    private AuditTrailService auditTrailService;

    @Autowired
    private SamLoanKpiTargetSetupBasedOnAccountRepository samLoanKpiTargetSetupBasedOnAccountRepository;

    @Override
    public Map<String, Object> storeData(SamLoanKpiTargetSetupBasedOnAccount data) {
        Map<String, Object> response = new HashMap<>();
        List<String> errors = this.validate(data);

        if (errors.size() == 0){
            this.setMultiselectValues(data);
            samLoanKpiTargetSetupBasedOnAccountRepository.save(data);
            auditTrailService.saveCreatedData("SAM KPI Target Setup Based On Account", data);
        }
        else{
            response.put("outcome", "success");
            response.put("errorMessages", errors);
        }

        return response;
    }

    @Override
    public Map<String, Object> updateData(SamLoanKpiTargetSetupBasedOnAccount data) {
        Map<String, Object> response = new HashMap<>();
        List<String> errors = this.validate(data);

        if (errors.size() == 0){
            SamLoanKpiTargetSetupBasedOnAccount oldEntity = samLoanKpiTargetSetupBasedOnAccountRepository.getOne(data.getId());
            SamLoanKpiTargetSetupBasedOnAccount previousEntity = new SamLoanKpiTargetSetupBasedOnAccount();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            this.setMultiselectValues(data);
            samLoanKpiTargetSetupBasedOnAccountRepository.save(data);
            auditTrailService.saveUpdatedData("SAM KPI Target Setup Based On Account", previousEntity, data);

        }
        else{
            response.put("outcome", "success");
            response.put("errorMessages", errors);
        }

        return response;
    }

    public void setMultiselectValues(SamLoanKpiTargetSetupBasedOnAccount samLoanKpiTargetSetupBasedOnAccount){
        List<ProductTypeEntity> productTypeEntities = new ArrayList<>();
        for (String id : samLoanKpiTargetSetupBasedOnAccount.getProductTypeIds()) {
            ProductTypeEntity productTypeEntity = productTypeService.getById(Long.valueOf(id));
            productTypeEntities.add(productTypeEntity);
        }
        samLoanKpiTargetSetupBasedOnAccount.setProductTypes(productTypeEntities);

        List<LocationEntity> locationEntities = new ArrayList<>();
        for (String id : samLoanKpiTargetSetupBasedOnAccount.getLocationIds()) {
            LocationEntity locationEntity = locationService.getById(Long.valueOf(id));
            locationEntities.add(locationEntity);
        }
        samLoanKpiTargetSetupBasedOnAccount.setLocations(locationEntities);

        List<ZoneEntity> zoneEntities = new ArrayList<>();
        for (String id : samLoanKpiTargetSetupBasedOnAccount.getZoneIds()) {
            ZoneEntity zoneEntity = zoneService.getById(Long.valueOf(id));
            zoneEntities.add(zoneEntity);
        }
        samLoanKpiTargetSetupBasedOnAccount.setZones(zoneEntities);

        List<DPDBucketEntity> dpdBucketEntities = new ArrayList<>();
        for (String id : samLoanKpiTargetSetupBasedOnAccount.getDpdBucketIds()) {
            DPDBucketEntity dpdBucketEntity = dpdBucketService.getById(Long.valueOf(id));
            dpdBucketEntities.add(dpdBucketEntity);
        }
        samLoanKpiTargetSetupBasedOnAccount.setDpdBuckets(dpdBucketEntities);


        List<SectorGroupEntity> sectorGroupEntities = new ArrayList<>();
        for (String id : samLoanKpiTargetSetupBasedOnAccount.getSectorGroupIds()) {
            SectorGroupEntity sectorGroupEntity = sectorGroupService.getById(Long.valueOf(id));
            sectorGroupEntities.add(sectorGroupEntity);
        }
        samLoanKpiTargetSetupBasedOnAccount.setSectorGroups(sectorGroupEntities);
    }

    @Override
    public Map<String, Object> deleteData(SamLoanKpiTargetSetupBasedOnAccount data) {
        return null;
    }

    @Override
    public SamLoanKpiTargetSetupBasedOnAccount findDataById(Long id) {
        SamLoanKpiTargetSetupBasedOnAccount targetSetupBasedOnAccount = samLoanKpiTargetSetupBasedOnAccountRepository.findSamLoanKpiTargetSetupBasedOnAccountById(id);
        return targetSetupBasedOnAccount;
    }

    @Override
    public List<SamLoanKpiTargetSetupBasedOnAccount> findAllData() {
        List<SamLoanKpiTargetSetupBasedOnAccount> allData = samLoanKpiTargetSetupBasedOnAccountRepository.findAll();
        return allData;
    }

    @Override
    public List<String> validate(SamLoanKpiTargetSetupBasedOnAccount data) {
        List<String> errors = new ArrayList<>();
        return errors;
    }
}
