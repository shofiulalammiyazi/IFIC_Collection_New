package com.csinfotechbd.collection.KPI.Loan.TargetByAmount;

/*
  Created by Md. Monirul Islam on 8/27/2019
*/
import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketEntity;
import com.csinfotechbd.collection.settings.location.LocationEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import com.csinfotechbd.collection.settings.sectorGroup.SectorGroupEntity;
import com.csinfotechbd.collection.settings.zone.ZoneEntity;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "KPI_TARGET_BY_AMOUNT_LOAN")
public class LoanKPITargetByAmountEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    private List<ProductTypeEntity> productType = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    private List<SectorGroupEntity> sectorGroup= new ArrayList<>();

    private Double maxAmount;
    private Double minAmount;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    private List<ZoneEntity> zone= new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    private List<LocationEntity> location= new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    private List<DPDBucketEntity> dpdBucket= new ArrayList<>();


    private Double overDueTaret;
    private Double saveTarget;
    private Double backTarget;
    private Double flowTarget;
    private Double regularTarget;
    private Double PARRelTarget;
    private Double NPLRelTarget;

    @Transient
    List<String> productTypeIds = new ArrayList<>();
    @Transient
    List<String> sectorGroupIds = new ArrayList<>();
    @Transient
    List<String> zoneIds = new ArrayList<>();
    @Transient
    List<String> locationIds = new ArrayList<>();
    @Transient
    List<String> dpdBucketIds = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ProductTypeEntity> getProductType() {
        return productType;
    }

    public void setProductType(List<ProductTypeEntity> productType) {
        this.productType = productType;
    }

    public List<SectorGroupEntity> getSectorGroup() {
        return sectorGroup;
    }

    public void setSectorGroup(List<SectorGroupEntity> sectorGroup) {
        this.sectorGroup = sectorGroup;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Double minAmount) {
        this.minAmount = minAmount;
    }

    public List<ZoneEntity> getZone() {
        return zone;
    }

    public void setZone(List<ZoneEntity> zone) {
        this.zone = zone;
    }

    public List<LocationEntity> getLocation() {
        return location;
    }

    public void setLocation(List<LocationEntity> location) {
        this.location = location;
    }

    public List<DPDBucketEntity> getDpdBucket() {
        return dpdBucket;
    }

    public void setDpdBucket(List<DPDBucketEntity> dpdBucket) {
        this.dpdBucket = dpdBucket;
    }

    public Double getOverDueTaret() {
        return overDueTaret;
    }

    public void setOverDueTaret(Double overDueTaret) {
        this.overDueTaret = overDueTaret;
    }

    public Double getSaveTarget() {
        return saveTarget;
    }

    public void setSaveTarget(Double saveTarget) {
        this.saveTarget = saveTarget;
    }

    public Double getBackTarget() {
        return backTarget;
    }

    public void setBackTarget(Double backTarget) {
        this.backTarget = backTarget;
    }

    public Double getFlowTarget() {
        return flowTarget;
    }

    public void setFlowTarget(Double flowTarget) {
        this.flowTarget = flowTarget;
    }

    public Double getRegularTarget() {
        return regularTarget;
    }

    public void setRegularTarget(Double regularTarget) {
        this.regularTarget = regularTarget;
    }

    public Double getPARRelTarget() {
        return PARRelTarget;
    }

    public void setPARRelTarget(Double PARRelTarget) {
        this.PARRelTarget = PARRelTarget;
    }

    public Double getNPLRelTarget() {
        return NPLRelTarget;
    }

    public void setNPLRelTarget(Double NPLRelTarget) {
        this.NPLRelTarget = NPLRelTarget;
    }

    public List<String> getProductTypeIds() {
        return productTypeIds;
    }

    public void setProductTypeIds(List<String> productTypeIds) {
        this.productTypeIds = productTypeIds;
    }

    public List<String> getSectorGroupIds() {
        return sectorGroupIds;
    }

    public void setSectorGroupIds(List<String> sectorGroupIds) {
        this.sectorGroupIds = sectorGroupIds;
    }

    public List<String> getZoneIds() {
        return zoneIds;
    }

    public void setZoneIds(List<String> zoneIds) {
        this.zoneIds = zoneIds;
    }

    public List<String> getLocationIds() {
        return locationIds;
    }

    public void setLocationIds(List<String> locationIds) {
        this.locationIds = locationIds;
    }

    public List<String> getDpdBucketIds() {
        return dpdBucketIds;
    }

    public void setDpdBucketIds(List<String> dpdBucketIds) {
        this.dpdBucketIds = dpdBucketIds;
    }
}
