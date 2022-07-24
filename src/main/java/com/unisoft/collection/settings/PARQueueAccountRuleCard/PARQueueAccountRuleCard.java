package com.unisoft.collection.settings.PARQueueAccountRuleCard;

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.producttypecard.ProductTypeCardEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "PAR_QUEUE_ACC_RULE_CARD")
public class PARQueueAccountRuleCard extends BaseInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double minAgeCode; // To calculate PAR queue next day
    
    @ManyToMany(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name = "productTypeList_id")
    private List<ProductTypeCardEntity> productTypeList = new ArrayList<>();
    
    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "ageCodeList_id")
    private List<AgeCodeEntity> ageCodeEntities = new ArrayList<>();
    
    
    @Transient
    private List<Long> productTypeIds;
    
    @Transient
    private List<Long> ageCodeIds;
    
    
    public void setProductTypeIds(List<Long> productTypeIds) {
        this.productTypeList = productTypeIds != null ? productTypeIds.stream().map(ProductTypeCardEntity::new).collect(Collectors.toList()) : null;
    }
    
    public void setAgeCodeIds(List<Long> ageCodeIds) {
        this.ageCodeEntities = ageCodeIds != null ? ageCodeIds.stream().map(AgeCodeEntity::new).collect(Collectors.toList()) : null;
    }
}
