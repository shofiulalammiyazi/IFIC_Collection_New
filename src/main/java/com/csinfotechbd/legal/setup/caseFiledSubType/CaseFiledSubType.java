package com.csinfotechbd.legal.setup.caseFiledSubType;

import com.csinfotechbd.common.CommonEntity;
import com.csinfotechbd.legal.setup.caseFiledType.CaseFiledType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
public class CaseFiledSubType extends CommonEntity {

    @NotBlank(message = "Please provide a name")
    private String name;

    @Min(value = 1, message = "Please specify the sequence number")
    private Long sequence;

    @ManyToOne
    private CaseFiledType caseFiledType;
}
