package com.csinfotechbd.legal.setup.caseStatus;

import com.csinfotechbd.common.CommonEntity;
import com.csinfotechbd.legal.setup.caseType.CaseType;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
public class CaseStatus extends CommonEntity {

    @NotBlank(message = "Please enter a case status")
    private String name;

    @Min(value = 1, message = "Please specify the sequence number")
    private Long sequence;

    @NotEmpty(message = "Please select a case type")
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<CaseType> caseTypes = new ArrayList<>();

    @Transient
    private List<Long> caseTypesIdList = new ArrayList<>();

    public void setCaseTypesIdList(List<Long> caseTypesIdList) {
        this.caseTypes = caseTypesIdList.stream().map(CaseType::new).collect(Collectors.toList());
    }


}
