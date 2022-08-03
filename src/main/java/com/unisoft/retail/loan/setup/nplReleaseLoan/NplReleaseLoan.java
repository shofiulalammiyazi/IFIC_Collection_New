package com.unisoft.retail.loan.setup.nplReleaseLoan;
/*
Created by   Islam on 7/10/2019
*/

import com.unisoft.collection.settings.dpdBucket.DPDBucketEntity;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "NPL_RELEASE_LOAN")
public class NplReleaseLoan extends CommonEntity {

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "NPL_RELEASE_LOAN_ID") // Creates a column named 'NPL_RELEASE_LOAN_ID' in DPD_BUCKET_ENTITY Table
    private List<DPDBucketEntity> dpdBucketList;

    @NotEmpty(message = "Please select product types")
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "NPL_RELEASE_LOAN_ID")
    // Creates a column named 'NPL_RELEASE_LOAN_ID' in PRODUCT_TYPE_ENTITY Table
    private List<ProductTypeEntity> productTypeList;

    @Transient
    private List<Long> dpdBucketIds;

    @Transient
    private List<Long> productTypeIds;

    public List<Long> getDpdBucketIds() {
        return dpdBucketList == null ? new ArrayList<>() : dpdBucketList.stream().map(DPDBucketEntity::getId).collect(Collectors.toList());
    }

    public void setDpdBucketIds(List<Long> dpdBucketIds) {
        this.dpdBucketList = dpdBucketIds.stream().map(DPDBucketEntity::new).collect(Collectors.toList());
    }

    public List<Long> getProductTypeIds() {
        return productTypeList == null ? new ArrayList<>() : productTypeList.stream().map(ProductTypeEntity::getId).collect(Collectors.toList());
    }

    public void setProductTypeIds(List<Long> productTypeIds) {
        this.productTypeList = productTypeIds.stream().map(ProductTypeEntity::new).collect(Collectors.toList());
    }

    public List<String> getDpdBucketNames() {
        return dpdBucketList == null ? new ArrayList<>() : dpdBucketList.stream().map(DPDBucketEntity::getBucketName).collect(Collectors.toList());
    }

    public List<String> getProductTypeNames() {
        return productTypeList == null ? new ArrayList<>() : productTypeList.stream().map(ProductTypeEntity::getName).collect(Collectors.toList());
    }
}
