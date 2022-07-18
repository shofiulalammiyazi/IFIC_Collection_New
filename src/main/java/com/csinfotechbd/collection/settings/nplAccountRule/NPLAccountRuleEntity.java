package com.csinfotechbd.collection.settings.nplAccountRule;
/*
Created by Monirul Islam on 7/9/2019 
*/

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name ="NPL_ACCOUNT_RULE")
public class NPLAccountRuleEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<DPDBucketEntity> dpdBucketList;

    @ManyToMany(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<ProductTypeEntity> productTypeList;

    private double minSanctionAmount;
    private double minDisbursAmount;
    private double minOutstanding;
    private double minATE;
}
