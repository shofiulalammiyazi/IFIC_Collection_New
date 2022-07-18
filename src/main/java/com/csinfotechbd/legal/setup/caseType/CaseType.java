package com.csinfotechbd.legal.setup.caseType;


import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.legal.setup.caseFiledSubType.CaseFiledSubType;
import com.csinfotechbd.legal.setup.caseFiledType.CaseFiledType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Getter
@Setter
@Entity
public class CaseType extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @Min(value = 1, message = "Please specify the sequence number")
    private Long sequence;

    private String description;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private CaseFiledType caseFiledType;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private CaseFiledSubType caseFiledSubType;

    public CaseType() {
    }

    public CaseType(Long id) {
        this.id = id;
    }

}
