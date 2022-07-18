package com.csinfotechbd.collection.KPI.Card.TargetByAmount;
/*
  Created by Md. Monirul Islam on 8/28/2019
  Updated by Nabil on 29 September,2019
*/

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.collection.settings.ageCode.AgeCodeEntity;
import com.csinfotechbd.collection.settings.assetClassificationLoan.AssetClassificationLoanEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.location.LocationEntity;
import com.csinfotechbd.collection.settings.productGroup.ProductGroupEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import com.csinfotechbd.collection.settings.statuscdmanagement.StatusCd;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "KPI_TARGET_BY_AMOUNT_CARD")
public class CardKPITargetByAmountEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.MERGE)
    private List<LocationEntity> location= new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.MERGE)
    private List<ProductTypeEntity> productType = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.MERGE)
    private List<AgeCodeEntity> ageCode = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.MERGE)
    private List<EmployeeInfoEntity> dealerName= new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.MERGE)
    private List<StatusCd> statusCd= new ArrayList<>();
    
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.MERGE)
    private List<AssetClassificationLoanEntity> clStatus= new ArrayList<>();


    private Double minDueTarget;
    private Double saveTarget;
    private Double backTarget;
    private Double flowTarget;
    private Double regularTarget;
    private Double rawCollectionTarget;
    private boolean rawColGtMinDue;
    private Double PARRelTarget;
    private Double NPLRelTarget;
    private boolean writeOff;
    private String legalStatus;
    private String proposal;

    @Transient
    List<String> productTypeIds = new ArrayList<>();
    @Transient
    List<String> locationIds = new ArrayList<>();
    @Transient
    List<String> ageCodeIds = new ArrayList<>();
    @Transient
    List<String> dealerNameIds = new ArrayList<>();
    @Transient
    List<String> statusCdIds = new ArrayList<>();

    public CardKPITargetByAmountEntity() {
    }

    public CardKPITargetByAmountEntity(List<LocationEntity> location, List<ProductTypeEntity> productType, List<AgeCodeEntity> ageCode, List<EmployeeInfoEntity> dealerName, List<StatusCd> statusCd, Double minDueTarget, Double saveTarget, Double backTarget, Double flowTarget, Double regularTarget, Double rawCollectionTarget, boolean rawColGtMinDue, Double PARRelTarget, Double NPLRelTarget, List<String> productTypeIds, List<String> locationIds, List<String> ageCodeIds, List<String> dealerNameIds, List<String> statusCdIds) {
        this.location = location;
        this.productType = productType;
        this.ageCode = ageCode;
        this.dealerName = dealerName;
        this.statusCd = statusCd;
        this.minDueTarget = minDueTarget;
        this.saveTarget = saveTarget;
        this.backTarget = backTarget;
        this.flowTarget = flowTarget;
        this.regularTarget = regularTarget;
        this.rawCollectionTarget = rawCollectionTarget;
        this.rawColGtMinDue = rawColGtMinDue;
        this.PARRelTarget = PARRelTarget;
        this.NPLRelTarget = NPLRelTarget;
        this.productTypeIds = productTypeIds;
        this.locationIds = locationIds;
        this.ageCodeIds = ageCodeIds;
        this.dealerNameIds = dealerNameIds;
        this.statusCdIds = statusCdIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<LocationEntity> getLocation() {
        return location;
    }

    public void setLocation(List<LocationEntity> location) {
        this.location = location;
    }

    public List<ProductTypeEntity> getProductType() {
        return productType;
    }

    public void setProductType(List<ProductTypeEntity> productType) {
        this.productType = productType;
    }

    public List<AgeCodeEntity> getAgeCode() {
        return ageCode;
    }

    public void setAgeCode(List<AgeCodeEntity> ageCode) {
        this.ageCode = ageCode;
    }

    public List<EmployeeInfoEntity> getDealerName() {
        return dealerName;
    }

    public void setDealerName(List<EmployeeInfoEntity> dealerName) {
        this.dealerName = dealerName;
    }

    public List<StatusCd> getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(List<StatusCd> statusCd) {
        this.statusCd = statusCd;
    }

    public Double getMinDueTarget() {
        return minDueTarget;
    }

    public void setMinDueTarget(Double minDueTarget) {
        this.minDueTarget = minDueTarget;
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

    public Double getRawCollectionTarget() {
        return rawCollectionTarget;
    }

    public void setRawCollectionTarget(Double rawCollectionTarget) {
        this.rawCollectionTarget = rawCollectionTarget;
    }

    public boolean isRawColGtMinDue() {
        return rawColGtMinDue;
    }

    public void setRawColGtMinDue(boolean rawColGtMinDue) {
        this.rawColGtMinDue = rawColGtMinDue;
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

    public List<String> getLocationIds() {
        return locationIds;
    }

    public void setLocationIds(List<String> locationIds) {
        this.locationIds = locationIds;
    }

    public List<String> getAgeCodeIds() {
        return ageCodeIds;
    }

    public void setAgeCodeIds(List<String> ageCodeIds) {
        this.ageCodeIds = ageCodeIds;
    }

    public List<String> getDealerNameIds() {
        return dealerNameIds;
    }

    public void setDealerNameIds(List<String> dealerNameIds) {
        this.dealerNameIds = dealerNameIds;
    }

    public List<String> getStatusCdIds() {
        return statusCdIds;
    }

    public void setStatusCdIds(List<String> statusCdIds) {
        this.statusCdIds = statusCdIds;
    }
}
