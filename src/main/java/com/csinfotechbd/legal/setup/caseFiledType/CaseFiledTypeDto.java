package com.csinfotechbd.legal.setup.caseFiledType;

import lombok.Data;

@Data
public class CaseFiledTypeDto {
    private Long id;
    private String name;


    public CaseFiledTypeDto() {
    }

    public CaseFiledTypeDto(CaseFiledType caseFiledType) {
        if (caseFiledType == null || caseFiledType.getId() == null) return;

        id = caseFiledType.getId();
        name = caseFiledType.getName();
    }
}
