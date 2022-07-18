package com.csinfotechbd.collection.KPI.Loan.AmountTargetByManager;
/*
Created by Monirul Islam at 9/23/2019 
*/

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketEntity;
import com.csinfotechbd.collection.settings.location.LocationEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "kpi_deal_tar_by_man_amo")
public class DealerAmountTargetByManager extends BaseInfo {
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

    private double regularAmountTarget;
    private double backAmountTarget;
    private double saveAmountTarget;
    private double flowAmountTarget;
    private double parAmountReleaseTarget;
    private double nplReleaseAmountTarget;
    private double cashCollectionTarget;

    @Transient
    private List<String> dpdBucketIds = new ArrayList<>();
    @Transient
    private List<String> locationIds = new ArrayList<>();
    @Transient
    private List<String> productIds = new ArrayList<>();
}
