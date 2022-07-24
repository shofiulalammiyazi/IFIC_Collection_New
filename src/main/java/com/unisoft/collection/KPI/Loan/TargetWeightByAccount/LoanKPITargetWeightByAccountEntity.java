package com.unisoft.collection.KPI.Loan.TargetWeightByAccount;
/*
  Created by Md.   Islam on 9/3/2019
*/

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.dpdBucket.DPDBucketEntity;
import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.sectorGroup.SectorGroupEntity;
import com.unisoft.collection.settings.zone.ZoneEntity;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "KPI_TARGET_WEIGHT_ACCOUNT_LOAN")
public class LoanKPITargetWeightByAccountEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    private List<ProductTypeEntity> productType = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    private List<SectorGroupEntity> sectorGroup= new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    private List<ZoneEntity> zone= new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    private List<LocationEntity> location= new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    private List<DPDBucketEntity> dpdBucket= new ArrayList<>();

    private Double overDueWeight;
    private Double saveWeight;
    private Double backWeight;
    private Double flowWeight;
    private Double regularWeight;
    private Double PARRelWeight;
    private Double NPLRelWeight;

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

    public Double getOverDueWeight() {
        return overDueWeight;
    }

    public void setOverDueWeight(Double overDueWeight) {
        this.overDueWeight = overDueWeight;
    }

    public Double getSaveWeight() {
        return saveWeight;
    }

    public void setSaveWeight(Double saveWeight) {
        this.saveWeight = saveWeight;
    }

    public Double getBackWeight() {
        return backWeight;
    }

    public void setBackWeight(Double backWeight) {
        this.backWeight = backWeight;
    }

    public Double getFlowWeight() {
        return flowWeight;
    }

    public void setFlowWeight(Double flowWeight) {
        this.flowWeight = flowWeight;
    }

    public Double getRegularWeight() {
        return regularWeight;
    }

    public void setRegularWeight(Double regularWeight) {
        this.regularWeight = regularWeight;
    }

    public Double getPARRelWeight() {
        return PARRelWeight;
    }

    public void setPARRelWeight(Double PARRelWeight) {
        this.PARRelWeight = PARRelWeight;
    }

    public Double getNPLRelWeight() {
        return NPLRelWeight;
    }

    public void setNPLRelWeight(Double NPLRelWeight) {
        this.NPLRelWeight = NPLRelWeight;
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
