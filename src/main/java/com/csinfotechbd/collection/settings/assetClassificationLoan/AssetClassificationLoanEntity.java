package com.csinfotechbd.collection.settings.assetClassificationLoan;

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.collection.settings.assetMainClassificationLoan.LoanMainClassification;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import com.csinfotechbd.legal.setup.caseFiledType.CaseFiledType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

/*
Created by Monirul Islam on 7/11/2019 
*/

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
