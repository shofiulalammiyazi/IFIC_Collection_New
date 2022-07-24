package com.unisoft.collection.KPI.agency.card.targetByAccount;

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.agency.AgencyEntity;
import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.statuscdmanagement.StatusCd;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
Created by   Islam at 9/22/2019
*/
@Data
@Entity(name = "ag_kpi_tar_card_acc_set")
public class AgencyKpiTargetCardAccountSetup extends BaseInfo {
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

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private List<AgencyEntity> agencyEntityList= new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private List<StatusCd> statusCdList= new ArrayList<>();

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
