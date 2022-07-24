package com.unisoft.collection.settings.ageCode;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AgeCodeEntityDto {

    private Long id;
    private String name;

    public AgeCodeEntityDto() {
    }

    public AgeCodeEntityDto(@NotNull AgeCodeEntity ageCodeEntity) {
        this.id = ageCodeEntity.getId();
        this.name = ageCodeEntity.getName();
    }

    public AgeCodeEntityDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
