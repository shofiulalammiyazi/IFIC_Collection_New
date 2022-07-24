package com.unisoft.collection.KPI.Loan.TargetExceptionRule;
/*
  Created by Md.   Islam on 9/4/2019
*/

import com.unisoft.base.BaseInfo;
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
@Table(name = "KPI_TARGET_EXCEPTION_LOAN")
public class LoanKPITargetExceptionRuleEntity extends BaseInfo {

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

    private Double maxAmount;
    private Double minAmount;

    @Transient
    List<String> productTypeIds = new ArrayList<>();
    @Transient
    List<String> sectorGroupIds = new ArrayList<>();
    @Transient
    List<String> zoneIds = new ArrayList<>();
    @Transient
    List<String> locationIds = new ArrayList<>();


}
