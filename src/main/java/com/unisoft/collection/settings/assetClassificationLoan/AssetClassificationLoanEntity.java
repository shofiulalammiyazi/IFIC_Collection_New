package com.unisoft.collection.settings.assetClassificationLoan;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.assetMainClassificationLoan.LoanMainClassification;
import com.unisoft.collection.settings.dpdBucket.DPDBucketEntity;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Data
@Table(name = "ASSET_CLASS_LOAN")
public class AssetClassificationLoanEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /*private String name;
    @Column(unique = true)
    private String code;*/
    //private String type;

    @NotNull(message = "Please select type")
    @OneToOne(cascade = CascadeType.REFRESH)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private LoanMainClassification type;

    @NotEmpty(message = "Please select dpd buckets")
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<DPDBucketEntity> bucketList;

//     TODO: Need to fix bug of redundent product types insertion.
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<ProductTypeEntity> productTypeList;

    private double minSanctionAmount;
    private double minDisburseAmount;
    private double minOutstandingAmount;
//    private double minATE;

//    TODO: Implement vue multiselect. Current implementation is checkbox
//    @Transient
//    List<Long> dpdBucketIds;
//
//    public void setDpdBucketIds(List<Long> dpdBucketIds) {
//        this.bucketList = dpdBucketIds.stream().map(DPDBucketEntity::new).collect(Collectors.toList());
//    }


    public AssetClassificationLoanEntity(Long id) {
        this.id = id;
    }

    public AssetClassificationLoanEntity() {

    }
}
