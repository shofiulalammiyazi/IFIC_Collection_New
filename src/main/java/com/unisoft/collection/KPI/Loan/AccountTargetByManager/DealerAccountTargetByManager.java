package com.unisoft.collection.KPI.Loan.AccountTargetByManager;
/*
Created by   Islam at 9/23/2019
*/


import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.dpdBucket.DPDBucketEntity;
import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "kpi_deal_tar_by_man")
public class DealerAccountTargetByManager extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @LazyCollection(value = LazyCollectionOption.FALSE)
    @ManyToMany
    private List<DPDBucketEntity> dpdBucketEntityList = new ArrayList<>();
    @LazyCollection(value = LazyCollectionOption.FALSE)
    @ManyToMany
    private List<LocationEntity> locationEntityList= new ArrayList<>();
    @LazyCollection(value = LazyCollectionOption.FALSE)
    @ManyToMany
    private List<ProductTypeEntity>  productTypeEntityList= new ArrayList<>();

    private double regularAccountTarget;
    private double backAccountTarget;
    private double saveAccountTarget;
    private double flowAccountTarget;
    private double parAccountReleaseTarget;
    private double nplReleaseAccountTarget;
    private double cashCollectionTarget;

    @Transient
    private List<String> dpdBucketIds = new ArrayList<>();
    @Transient
    private List<String> locationIds = new ArrayList<>();
    @Transient
    private List<String> productIds = new ArrayList<>();
}
