package com.csinfotechbd.legal.setup.caseFiledSubType;

/*
Created by Yasir Araphat on 8 March 2021
*/

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaseFiledSubTypeDto {

    private Long id;
    private String name;

    public CaseFiledSubTypeDto() {
    }

    public CaseFiledSubTypeDto(CaseFiledSubType caseFiledSubType) {
        if (caseFiledSubType == null || caseFiledSubType.getId() == null) return;

        id = caseFiledSubType.getId();
        name = caseFiledSubType.getName();
    }

}
