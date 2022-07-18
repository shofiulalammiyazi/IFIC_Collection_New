package com.csinfotechbd.legal.setup.caseFiledType;

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.legal.setup.caseFiledSubType.CaseFiledSubType;
import com.csinfotechbd.legal.setup.caseType.CaseType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class  CaseFiledType extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    private String name;

    @Min(value = 1, message = "Please specify the sequence number")
    private Long sequence;

    private String description;

    @Transient
    private List<CaseType> caseTypeList;

    @Transient
    private List<CaseFiledSubType> caseFiledSubTypeList;
}
