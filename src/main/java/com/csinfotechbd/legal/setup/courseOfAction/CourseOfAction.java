package com.csinfotechbd.legal.setup.courseOfAction;

import com.csinfotechbd.legal.setup.caseType.CaseType;
import com.csinfotechbd.common.CommonEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
public class CourseOfAction extends CommonEntity {

    @NotBlank(message = "Please enter an action")
    private String name;

    private String contestedType;

    @Min(value = 1, message = "Please specify the sequence number")
    private Long sequence;

    @NotEmpty(message = "Please select a case type")
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<CaseType> caseTypes = new ArrayList<>();

    //  Combination of transient and notempty generates ConstraintViolation error when updating the entity
    @Transient
    private List<Long> caseTypesIdList = new ArrayList<>();

    public void setCaseTypesIdList(List<Long> caseTypesIdList) {
        this.caseTypes = caseTypesIdList.stream().map(CaseType::new).collect(Collectors.toList());
    }

}
