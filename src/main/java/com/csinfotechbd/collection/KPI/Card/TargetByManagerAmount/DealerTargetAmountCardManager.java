package com.csinfotechbd.collection.KPI.Card.TargetByManagerAmount;

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.collection.settings.ageCode.AgeCodeEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.location.LocationEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "del_tar_card_man_am")
public class DealerTargetAmountCardManager extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @LazyCollection(value = LazyCollectionOption.FALSE)
    @ManyToMany
    private List<AgeCodeEntity> ageCodeEntityList = new ArrayList<>();
    @LazyCollection(value = LazyCollectionOption.FALSE)
    @ManyToMany
    private List<LocationEntity> locationEntityList = new ArrayList<>();
    @LazyCollection(value = LazyCollectionOption.FALSE)
    @ManyToMany
    private List<EmployeeInfoEntity> employeeInfoEntityList= new ArrayList<>();
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private List<ProductTypeEntity> productType = new ArrayList<>();

    private double regularAmountTarget;
    private double backAmountTarget;
    private double saveAmountTarget;
    private double flowAmountTarget;
    private double minDueAmountTarget;
    private double cashCollectionTarget;
    private double parReleaseAmountTarget;
    private double nplReleaseAmountTarget;

    @Transient
    private List<String> ageCodeIds = new ArrayList<>();
    @Transient
    private List<String> locationIds = new ArrayList<>();
    @Transient
    private List<String> employeeIds = new ArrayList<>();
    @Transient
    private List<String> productTypeIds=new ArrayList<>();

    public DealerTargetAmountCardManager() {
    }

    public DealerTargetAmountCardManager(List<AgeCodeEntity> ageCodeEntityList, List<LocationEntity> locationEntityList, List<EmployeeInfoEntity> employeeInfoEntityList, List<ProductTypeEntity> productType, double regularAmountTarget, double backAmountTarget, double saveAmountTarget, double flowAmountTarget, double minDueAmountTarget, double cashCollectionTarget, double parReleaseAmountTarget, double nplReleaseAmountTarget, List<String> ageCodeIds, List<String> locationIds, List<String> employeeIds, List<String> productTypeIds) {
        this.ageCodeEntityList = ageCodeEntityList;
        this.locationEntityList = locationEntityList;
        this.employeeInfoEntityList = employeeInfoEntityList;
        this.productType = productType;
        this.regularAmountTarget = regularAmountTarget;
        this.backAmountTarget = backAmountTarget;
        this.saveAmountTarget = saveAmountTarget;
        this.flowAmountTarget = flowAmountTarget;
        this.minDueAmountTarget = minDueAmountTarget;
        this.cashCollectionTarget = cashCollectionTarget;
        this.parReleaseAmountTarget = parReleaseAmountTarget;
        this.nplReleaseAmountTarget = nplReleaseAmountTarget;
        this.ageCodeIds = ageCodeIds;
        this.locationIds = locationIds;
        this.employeeIds = employeeIds;
        this.productTypeIds = productTypeIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<AgeCodeEntity> getAgeCodeEntityList() {
        return ageCodeEntityList;
    }

    public void setAgeCodeEntityList(List<AgeCodeEntity> ageCodeEntityList) {
        this.ageCodeEntityList = ageCodeEntityList;
    }

    public List<LocationEntity> getLocationEntityList() {
        return locationEntityList;
    }

    public void setLocationEntityList(List<LocationEntity> locationEntityList) {
        this.locationEntityList = locationEntityList;
    }

    public List<EmployeeInfoEntity> getEmployeeInfoEntityList() {
        return employeeInfoEntityList;
    }

    public void setEmployeeInfoEntityList(List<EmployeeInfoEntity> employeeInfoEntityList) {
        this.employeeInfoEntityList = employeeInfoEntityList;
    }

    public List<ProductTypeEntity> getProductType() {
        return productType;
    }

    public void setProductType(List<ProductTypeEntity> productType) {
        this.productType = productType;
    }

    public double getRegularAmountTarget() {
        return regularAmountTarget;
    }

    public void setRegularAmountTarget(double regularAmountTarget) {
        this.regularAmountTarget = regularAmountTarget;
    }

    public double getBackAmountTarget() {
        return backAmountTarget;
    }

    public void setBackAmountTarget(double backAmountTarget) {
        this.backAmountTarget = backAmountTarget;
    }

    public double getSaveAmountTarget() {
        return saveAmountTarget;
    }

    public void setSaveAmountTarget(double saveAmountTarget) {
        this.saveAmountTarget = saveAmountTarget;
    }

    public double getFlowAmountTarget() {
        return flowAmountTarget;
    }

    public void setFlowAmountTarget(double flowAmountTarget) {
        this.flowAmountTarget = flowAmountTarget;
    }

    public double getMinDueAmountTarget() {
        return minDueAmountTarget;
    }

    public void setMinDueAmountTarget(double minDueAmountTarget) {
        this.minDueAmountTarget = minDueAmountTarget;
    }

    public double getCashCollectionTarget() {
        return cashCollectionTarget;
    }

    public void setCashCollectionTarget(double cashCollectionTarget) {
        this.cashCollectionTarget = cashCollectionTarget;
    }

    public double getParReleaseAmountTarget() {
        return parReleaseAmountTarget;
    }

    public void setParReleaseAmountTarget(double parReleaseAmountTarget) {
        this.parReleaseAmountTarget = parReleaseAmountTarget;
    }

    public double getNplReleaseAmountTarget() {
        return nplReleaseAmountTarget;
    }

    public void setNplReleaseAmountTarget(double nplReleaseAmountTarget) {
        this.nplReleaseAmountTarget = nplReleaseAmountTarget;
    }

    public List<String> getAgeCodeIds() {
        return ageCodeIds;
    }

    public void setAgeCodeIds(List<String> ageCodeIds) {
        this.ageCodeIds = ageCodeIds;
    }

    public List<String> getLocationIds() {
        return locationIds;
    }

    public void setLocationIds(List<String> locationIds) {
        this.locationIds = locationIds;
    }

    public List<String> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<String> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public List<String> getProductTypeIds() {
        return productTypeIds;
    }

    public void setProductTypeIds(List<String> productTypeIds) {
        this.productTypeIds = productTypeIds;
    }
}
