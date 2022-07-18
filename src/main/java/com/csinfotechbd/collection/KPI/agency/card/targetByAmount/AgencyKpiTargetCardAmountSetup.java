package com.csinfotechbd.collection.KPI.agency.card.targetByAmount;
/*
Created by Monirul Islam at 9/22/2019 
*/

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.collection.settings.ageCode.AgeCodeEntity;
import com.csinfotechbd.collection.settings.agency.AgencyEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.location.LocationEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import com.csinfotechbd.collection.settings.statuscdmanagement.StatusCd;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "ag_kpi_tar_card_set")
public class AgencyKpiTargetCardAmountSetup extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private List<ProductTypeEntity> productTypeEntityList = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private List<LocationEntity> locationEntityList = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private List<AgeCodeEntity> ageCodeEntityList= new ArrayList<>();

    /*@LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private List<EmployeeInfoEntity> employeeInfoEntityList = new ArrayList<>();*/

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private List<StatusCd> statusCdList= new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private List<AgencyEntity> agencyEntityList= new ArrayList<>();

    private double outstandingTarget;
    private double minDueAmount;
    private double saveTarget;
    private double backTarget;
    private double flowTarget;
    private double regularTarget;
    private double rawCollectionTarget;
    private String rawCAndDueAmount;
    private double parReleaseTarget;
    private double nplReleaseTarget;

    @Transient
    private List<String> productIds = new ArrayList<>();
    @Transient
    private List<String> locationIds = new ArrayList<>();
    @Transient
    private List<String> agencyIds = new ArrayList<>();
    @Transient
    private List<String> ageCodeIds = new ArrayList<>();
    @Transient
    private List<String> statusCdIds = new ArrayList<>();
}
