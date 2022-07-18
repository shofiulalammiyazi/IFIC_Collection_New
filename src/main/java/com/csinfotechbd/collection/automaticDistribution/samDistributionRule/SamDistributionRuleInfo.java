package com.csinfotechbd.collection.automaticDistribution.samDistributionRule;
/*
Created by Monirul Islam at 8/8/2019
*/

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.collection.settings.ageCode.AgeCodeEntity;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class SamDistributionRuleInfo extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(cascade = CascadeType.REFRESH)
    private List<ProductTypeEntity> productTypeEntityList;

    @ManyToMany(cascade = CascadeType.REFRESH)
    private List<DPDBucketEntity> dpdBucketEntities;

    private Boolean applicable = false; /// false =  DPD ; true = ATE

    @Column(name = "MAX_ATE")
    private Double maxATE;

    @Column(name = "MIN_ATE")
    private Double minATE;

    @Transient
    private List<Long> selectedProduct = new ArrayList<>();

    @Transient
    private List<Long> dpdBucketId = new ArrayList<>();

    public void setApplicable(Boolean applicable) {
        this.applicable = applicable != null && applicable;
    }

    public void setMaxATE(Double maxATE) {
        this.maxATE = maxATE == null ? 0.0 : maxATE;
    }

    public void setMinATE(Double minATE) {
        this.minATE = minATE == null ? 0.0 : minATE;
    }

}
