package com.csinfotechbd.collection.settings.assetMainClassificationLoan;

import lombok.Data;

@Data
public class LoanMainClassificationDto {

    private Long id;
    private String type;


    public LoanMainClassificationDto() {
    }

    public LoanMainClassificationDto(LoanMainClassification type) {
        if (type == null || type.getId() == null) return;

        this.id = type.getId();
        this.type = type.getName();
    }
}
