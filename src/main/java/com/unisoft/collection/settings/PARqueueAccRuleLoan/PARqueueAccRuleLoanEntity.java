package com.unisoft.collection.settings.PARqueueAccRuleLoan;

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.dpdBucket.DPDBucketEntity;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "PAR_QUEUE_ACC_RULE_LOAN")
public class PARqueueAccRuleLoanEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double minDpd; // To calculate PAR queue next day

    @ManyToMany(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name = "productTypeList_id")
    private List<ProductTypeEntity> productTypeList = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "dpdBucketList_id")
    private List<DPDBucketEntity> dpdBucketEntityList = new ArrayList<>();


    @Transient
    private List<Long> productTypeIds;

    @Transient
    private List<Long> dpdIds;


    public void setProductTypeIds(List<Long> productTypeIds) {
        this.productTypeList = productTypeIds != null ? productTypeIds.stream().map(ProductTypeEntity::new).collect(Collectors.toList()) : null;
    }

    public void setDpdIds(List<Long> dpdIds) {
        this.dpdBucketEntityList = dpdIds != null ? dpdIds.stream().map(DPDBucketEntity::new).collect(Collectors.toList()) : null;
    }
}
