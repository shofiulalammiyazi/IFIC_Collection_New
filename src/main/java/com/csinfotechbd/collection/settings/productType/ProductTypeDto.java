package com.csinfotechbd.collection.settings.productType;
/*
Created by Monirul Islam at 6/25/2019 
*/

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.collection.settings.productGroup.ProductGroupEntity;
import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Data
public class ProductTypeDto {


    private Long id;
    private String name;
    private String code;
    private String cardsCategory;


    public ProductTypeDto() {
    }

    public ProductTypeDto(Long id) {
        this.id = id;
    }

    public ProductTypeDto(@NotNull ProductTypeEntity productTypeEntity) {
        this.id = productTypeEntity.getId();
        this.name = productTypeEntity.getName();
        this.code = productTypeEntity.getCode();
        this.cardsCategory = productTypeEntity.getCardsCategory();
    }

    public ProductTypeDto(Long id, String name, String code, String cardsCategory) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.cardsCategory = cardsCategory;
    }
}
