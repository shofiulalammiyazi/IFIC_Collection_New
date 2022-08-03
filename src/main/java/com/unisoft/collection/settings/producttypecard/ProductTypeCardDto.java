package com.unisoft.collection.settings.producttypecard;


import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class ProductTypeCardDto {


    private Long id;
    private String name;
    private String code;
    private String cardsCategory;


    public ProductTypeCardDto() {
    }

    public ProductTypeCardDto(Long id) {
        this.id = id;
    }

    public ProductTypeCardDto(@NotNull ProductTypeCardEntity productTypeEntity) {
        this.id = productTypeEntity.getId();
        this.name = productTypeEntity.getName();
        this.code = productTypeEntity.getCode();
        this.cardsCategory = productTypeEntity.getCardsCategory();
    }

    public ProductTypeCardDto(Long id, String name, String code, String cardsCategory) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.cardsCategory = cardsCategory;
    }
}
