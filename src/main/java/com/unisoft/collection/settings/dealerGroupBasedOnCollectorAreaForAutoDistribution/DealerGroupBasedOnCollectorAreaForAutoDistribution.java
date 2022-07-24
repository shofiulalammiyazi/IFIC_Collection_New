package com.unisoft.collection.settings.dealerGroupBasedOnCollectorAreaForAutoDistribution;


import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.dpdBucket.DPDBucketEntity;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.collection.settings.productGroup.ProductGroupEntity;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.unit.UnitEntity;
import com.unisoft.common.CommonEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Setter
@Getter
@Table(name = "DEALER_GROUP")
public class DealerGroupBasedOnCollectorAreaForAutoDistribution extends CommonEntity {

    @ManyToOne(cascade = CascadeType.REFRESH)
    private UnitEntity unitEntity;


    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<DPDBucketEntity> dpdBucketEntities = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<AgeCodeEntity> ageCodeEntities = new ArrayList<>();


    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<EmployeeInfoEntity> dealer = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<LocationEntity> location = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<ProductGroupEntity> productGroup = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<ProductTypeEntity> productType = new ArrayList<>();

    @Transient
    private List<Long> dpdBucketIds;

    @Transient
    private List<Long> ageCodeIds;

    @Transient
    private List<Long> dealerIds;

    @Transient
    private List<Long> locationIds;

    @Transient
    private List<Long> productGroupIds;

    @Transient
    private List<Long> productTypeIds;

    @Transient
    private Long unitId;


    public UnitEntity getUnitEntity() {
        return unitEntity = (unitEntity != null) ? unitEntity : new UnitEntity();
    }

    public void setUnitId(Long unitId) {
        this.unitEntity = new UnitEntity(unitId);
    }

    public void setDpdBucketIds(List<Long> dpdBucketIds) {
        this.dpdBucketEntities = dpdBucketIds != null ? dpdBucketIds.stream().map(DPDBucketEntity::new).collect(Collectors.toList()) : null;
    }

    public void setAgeCodeIds(List<Long> ageCodeIds) {
        this.ageCodeEntities = ageCodeIds != null ? ageCodeIds.stream().map(AgeCodeEntity::new).collect(Collectors.toList()) : null;
    }


    public void setDealerIds(List<Long> dealerIds) {
        this.dealer = dealerIds != null ? dealerIds.stream().map(EmployeeInfoEntity::new).collect(Collectors.toList()) : null;
    }

    public void setLocationIds(List<Long> locationIds) {
        this.location = locationIds != null ? locationIds.stream().map(LocationEntity::new).collect(Collectors.toList()) : null;
    }

    public void setProductGroupIds(List<Long> productGroupIds) {
        this.productGroup = productGroupIds != null ? productGroupIds.stream().map(ProductGroupEntity::new).collect(Collectors.toList()) : null;
    }

    public void setProductTypeIds(List<Long> productTypeIds) {
        this.productType = productTypeIds != null ? productTypeIds.stream().map(ProductTypeEntity::new).collect(Collectors.toList()) : null;
    }
}
