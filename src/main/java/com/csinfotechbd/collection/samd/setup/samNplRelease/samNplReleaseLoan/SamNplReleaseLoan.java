package com.csinfotechbd.collection.samd.setup.samNplRelease.samNplReleaseLoan;


import com.csinfotechbd.collection.settings.assetClassificationLoan.AssetClassificationLoanEntity;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import com.csinfotechbd.common.CommonEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Data
@Setter
@Getter
public class SamNplReleaseLoan extends CommonEntity {

    private double outstanding;
    private double recovery;
    private double interestSuspense;
    private String provision;

    private double minDpd;
    private double maxDpd;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    List<DPDBucketEntity>dpdBucketEntities = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    List<AssetClassificationLoanEntity>assetClassificationLoanEntities = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    List<ProductTypeEntity>productTypeEntities = new ArrayList<>();


    @Transient
    private List<Long>dpdBucketIds;


    @Transient
    private List<Long>assetClassificationLoanIds;

    @Transient
    private List<Long> productTypeIds;


    public void setDpdBucketIds(List<Long> dpdBucketIds) {
        this.dpdBucketEntities = dpdBucketIds !=null ? dpdBucketIds.stream().map(DPDBucketEntity::new).collect(Collectors.toList()) : null;
    }

    public void setAssetClassificationLoanIds(List<Long> assetClassificationLoanIds) {
        this.assetClassificationLoanEntities =assetClassificationLoanIds !=null ? assetClassificationLoanIds.stream().map(AssetClassificationLoanEntity::new).collect(Collectors.toList()) : null;
    }

    public void setProductTypeIds(List<Long> productTypeIds) {
        this.productTypeEntities =productTypeIds !=null ? productTypeIds.stream().map(ProductTypeEntity::new).collect(Collectors.toList()) : null;
    }
}
