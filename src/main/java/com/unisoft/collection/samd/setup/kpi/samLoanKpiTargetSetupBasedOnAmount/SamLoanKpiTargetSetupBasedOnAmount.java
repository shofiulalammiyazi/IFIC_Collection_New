package com.unisoft.collection.samd.setup.kpi.samLoanKpiTargetSetupBasedOnAmount;

import com.unisoft.collection.settings.dpdBucket.DPDBucketEntity;
import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.sectorGroup.SectorGroupEntity;
import com.unisoft.collection.settings.zone.ZoneEntity;
import com.unisoft.common.CommonEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity (name = "sam_loan_kpi_target_setup_based_on_amount")
public class SamLoanKpiTargetSetupBasedOnAmount extends CommonEntity {

    @Transient
    private List<String> productTypeIds = new ArrayList<>();

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ProductTypeEntity> productTypes = new ArrayList<>();

    @Transient
    private List<String> locationIds = new ArrayList<>();

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<LocationEntity> locations = new ArrayList<>();

    @Transient
    private List<String> sectorGroupIds = new ArrayList<>();

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<SectorGroupEntity> sectorGroups = new ArrayList<>();

    @Transient
    private List<String> zoneIds = new ArrayList<>();

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ZoneEntity> zones = new ArrayList<>();

    @Transient
    private List<String> dpdBucketIds = new ArrayList<>();

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<DPDBucketEntity> dpdBuckets = new ArrayList<>();

    @Column(nullable = true)
    private Double outstandingAmountMin = 0.0;
    @Column(nullable = true)
    private Double outstandingAmountMax = 0.0;

    @Column(nullable = true)
    private Double overdueTarget = 0.0;
    @Column(nullable = true)
    private Double saveTarget = 0.0;
    @Column(nullable = true)
    private Double backTarget = 0.0;
    @Column(nullable = true)
    private Double flowTarget = 0.0;
    @Column(nullable = true)
    private Double regularTarget = 0.0;
    @Column(nullable = true)
    private Double parReleaseTarget = 0.0;
    @Column(nullable = true)
    private Double nplReleaseTarget = 0.0;

}
