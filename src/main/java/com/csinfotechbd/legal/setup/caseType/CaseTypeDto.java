package com.csinfotechbd.legal.setup.caseType;


import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class CaseTypeDto {
    private Long id;
    private String name;
    private Long sequence;
    private Long caseFiledTypeId;
    private String caseFiledTypeName;
    private Long caseFiledSubTypeId;
    private String caseFiledSubTypeName;
    private boolean enabled;

    public CaseTypeDto() {
    }

    public CaseTypeDto(@NonNull CaseType caseType) {
        this.id = caseType.getId();
        this.name = caseType.getName();
        sequence = caseType.getSequence();
//        CaseFiledType caseFiledType = caseType.getCaseFiledType();
//        if (caseFiledType != null) {
//            this.caseFiledTypeId = caseFiledType.getId();
//            this.caseFiledTypeName = caseFiledType.getName();
//        }
//        CaseFiledSubType subType = caseType.getCaseFiledSubType();
//        if (subType != null) {
//            this.caseFiledSubTypeId = subType.getId();
//            this.caseFiledSubTypeName = subType.getName();
//        }
        enabled = caseType.isEnabled();
    }

    public CaseTypeDto(Long id, String name, Long sequence, Long caseFiledTypeId, Long caseFiledSubTypeId) {
        this.id = id;
        this.name = name;
        this.sequence = sequence;
        this.caseFiledTypeId = caseFiledTypeId;
        this.caseFiledSubTypeId = caseFiledSubTypeId;
    }
}
