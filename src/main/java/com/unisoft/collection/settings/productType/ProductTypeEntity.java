package com.unisoft.collection.settings.productType;

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.productGroup.ProductGroupEntity;
import com.google.gson.annotations.Expose;
import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class ProductTypeEntity extends BaseInfo {

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

    public ProductTypeEntity() { }

    public ProductTypeEntity(Long id) {
        this.id = id;
    }
}
