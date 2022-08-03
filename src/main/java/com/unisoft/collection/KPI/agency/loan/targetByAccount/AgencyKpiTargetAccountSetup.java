package com.unisoft.collection.KPI.agency.loan.targetByAccount;
/*
Created by   Islam at 9/18/2019
*/

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.dpdBucket.DPDBucketEntity;
import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.zone.ZoneEntity;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name ="ag_kpi_tar_set_acc")
public class AgencyKpiTargetAccountSetup extends BaseInfo {
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
    private List<ZoneEntity> zoneEntityList = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private List<DPDBucketEntity> dpdBucketEntityList = new ArrayList<>();

    private double overDueTarget;
    private double saveTarget;
    private double backTarget;
    private double flowTarget;
    private double regularTaget;
    private double parReleaseTarget;
    private double nplReleaseTarget;
    private boolean emiAmmountAdd= true;

    @Transient
    List<String> productIds = new ArrayList<>();
    @Transient
    List<String> locationIds = new ArrayList<>();
    @Transient
    List<String> zoneIds = new ArrayList<>();
    @Transient
    List<String> dpdBucketIds = new ArrayList<>();
}
