package com.unisoft.collection.settings.nplAccountRuleCard;
/*
Created by   Islam on 7/14/2019
*/

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "NPL_ACCOUNT_RULE_CARD")
@Data
public class NPLAccountRuleCardEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToMany(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<AgeCodeEntity> ageCodeList;
    
    @ManyToMany(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<ProductTypeEntity> productTypeList;
    
    private double minSanctionAmount;
    private double minDisburseAmount;
    private double minOutstanding;
    private double minATE;
}
