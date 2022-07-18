package com.csinfotechbd.collection.KPI.Loan.TargetByAccount;
/*
  Created by Md. Monirul Islam on 8/28/2019
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

@Entity
@Data
@Table(name = "KPI_TARGET_BY_ACCOUNT_LOAN")
public class LoanKPITargetByAccountEntity extends BaseInfo {

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

    private Double overDueTarget;
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

}
