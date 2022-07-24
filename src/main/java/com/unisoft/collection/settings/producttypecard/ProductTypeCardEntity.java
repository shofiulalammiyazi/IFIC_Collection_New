package com.unisoft.collection.settings.producttypecard;


import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.productGroup.ProductGroupEntity;
import com.google.gson.annotations.Expose;
import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class ProductTypeCardEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Expose
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String code;
    private String cardsCategory;
    @ManyToOne(cascade = CascadeType.REFRESH)
    private ProductGroupEntity productGroupEntity;

    public ProductTypeCardEntity() {
    }

    public ProductTypeCardEntity(Long id) {
        this.id = id;
    }


}
