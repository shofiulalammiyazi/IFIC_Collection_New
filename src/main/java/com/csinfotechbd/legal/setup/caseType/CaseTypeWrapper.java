package com.csinfotechbd.legal.setup.caseType;


import com.csinfotechbd.legal.setup.caseFiledSubType.CaseFiledSubType;
import com.csinfotechbd.legal.setup.caseFiledType.CaseFiledType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaseTypeWrapper {

    private List<CaseType> caseTypeList;
    //private Long caseFiledTypeId;
    //private Long caseFiledSubTypeId;

    private CaseFiledType caseFiledType;

    private CaseFiledSubType caseFiledSubType;


}
