package com.unisoft.collection.settings.ageAndClassificationRule;
/*
Created by   Islam on 7/9/2019
*/

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.producttypecard.ProductTypeCardEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "AGE_AND_CLASSIFICATION_RULE")
@Data
public class AgeAndClassifiactionRuleEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "AGE_CODE_ID")
    private AgeCodeEntity ageCode;
    
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "PRODUCT_TYPE_CARD_ID")
    private ProductTypeCardEntity productTypeCardEntity;

    private String classificationStatus;
}
