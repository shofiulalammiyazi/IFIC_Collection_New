package com.unisoft.collection.settings.dealerGroupBasedOnCollectorAreaForAutoDistribution;


import com.unisoft.collection.settings.ageCode.AgeCodeEntityDto;
import com.unisoft.collection.settings.dpdBucket.DPDBucketEntityDto;
import com.unisoft.collection.settings.employee.EmployeeInfoDto;
import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.collection.settings.productGroup.ProductGroupEntity;
import com.unisoft.collection.settings.productType.ProductTypeDto;
import com.unisoft.collection.settings.unit.UnitEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class DealerGroupBasedOnCollectorAreaForAutoDistributionDto {

    private Long id;
    private UnitEntity unitEntity;

    private List<DPDBucketEntityDto> dpdBucketEntities = new ArrayList<>();
    private List<AgeCodeEntityDto> ageCodeEntities = new ArrayList<>();

    private List<EmployeeInfoDto> dealer = new ArrayList<>();
    private List<LocationEntity> location = new ArrayList<>();
    private List<ProductGroupEntity> productGroup = new ArrayList<>();
    private List<ProductTypeDto> productType = new ArrayList<>();

    public DealerGroupBasedOnCollectorAreaForAutoDistributionDto() {
    }

    public DealerGroupBasedOnCollectorAreaForAutoDistributionDto(DealerGroupBasedOnCollectorAreaForAutoDistribution entity) {
        id = entity.getId();
        unitEntity = entity.getUnitEntity();
        dpdBucketEntities = entity.getDpdBucketEntities().stream().map(DPDBucketEntityDto::new).collect(Collectors.toList());
        ageCodeEntities = entity.getAgeCodeEntities().stream().map(AgeCodeEntityDto::new).collect(Collectors.toList());
        dealer = entity.getDealer().stream().map(EmployeeInfoDto::new).collect(Collectors.toList());
        location = entity.getLocation();
        productGroup = entity.getProductGroup();
        productType = entity.getProductType().stream().map(ProductTypeDto::new).collect(Collectors.toList());

    }

}
