package com.csinfotechbd.collection.samd.setup.kpi.samLoanKpiTargetSetupBasedOnAccount;

import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketEntity;
import com.csinfotechbd.collection.settings.location.LocationEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import com.csinfotechbd.collection.settings.sectorGroup.SectorGroupEntity;
import com.csinfotechbd.collection.settings.zone.ZoneEntity;
import com.csinfotechbd.common.CommonEntity;
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
@Entity(name = "sam_loan_kpi_target_setup_based_on_account")
public class SamLoanKpiTargetSetupBasedOnAccount extends CommonEntity {

    @Transient
    private List<String> productTypeIds = new ArrayList<>();

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ProductTypeEntity> productTypes = new ArrayList<>();

    @Transient
    private List<String> sectorGroupIds = new ArrayList<>();

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<SectorGroupEntity> sectorGroups = new ArrayList<>();

    @Transient
    private List<String> locationIds = new ArrayList<>();

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<LocationEntity> locations = new ArrayList<>();

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
    private Double overdueTarget;
    @Column(nullable = true)
    private Double saveTarget;
    @Column(nullable = true)
    private Double backTarget;
    @Column(nullable = true)
    private Double flowTarget;
    @Column(nullable = true)
    private Double regularTarget;
    @Column(nullable = true)
    private Double parReleaseTarget;
    @Column(nullable = true)
    private Double nplReleaseTarget;

}
