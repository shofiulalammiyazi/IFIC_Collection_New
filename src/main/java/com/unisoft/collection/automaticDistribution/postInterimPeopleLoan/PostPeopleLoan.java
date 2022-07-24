package com.unisoft.collection.automaticDistribution.postInterimPeopleLoan;


import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class PostPeopleLoan extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String samIgnore;

    private String writeOffIgnore;

    @LazyCollection(value = LazyCollectionOption.FALSE)
    @OneToMany
    private List<ProductTypeEntity> productGroupEntityList = new ArrayList<>();

    @Transient
    private List<String> productGroupId = new ArrayList<>();

}
