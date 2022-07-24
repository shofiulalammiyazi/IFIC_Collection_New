package com.unisoft.collection.automaticDistribution.postInterim;
/*
Created by   Islam at 8/20/2019
*/

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.producttypecard.ProductTypeCardEntity;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class PostInterimCondition extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER)
    private List<AgeCodeEntity> ageCodeEntities = new ArrayList<>();

    private String[] billingCycle;

    @LazyCollection(value = LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL)
    private List<ProductTypeEntity> productGroupEntities = new ArrayList<>();
    
    @LazyCollection(value = LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL)
    private List<ProductTypeCardEntity> productTypeCardEntities= new ArrayList<>();

    @Transient
    private List<String> productGroupId = new ArrayList<>();

    @Transient
    private List<String> ageCodeId = new ArrayList<>();
}
