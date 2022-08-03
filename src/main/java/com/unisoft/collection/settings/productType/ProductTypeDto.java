package com.unisoft.collection.settings.productType;


import lombok.Data;

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
