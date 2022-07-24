package com.unisoft.collection.settings.branch;


// Created by    on 13 March, 2021

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Objects;
import java.util.Optional;

@Data
public class BranchDto {

    private Integer id;
    private String name;
    private String branchCode;
    private String routingNo;
    private String districtId;
    private String districtName;
    private String divisionId;
    private String divisionName;

    public BranchDto() {
    }

    public BranchDto(Branch branch) {
        if (branch == null || branch.getBranchId() == null) return;

        id = branch.getBranchId();
        name = branch.getBranchName();
        routingNo = branch.getRoutingNo();
        branchCode = branch.getBranchCode();
    }

    public BranchDto(Tuple data) {
        if (data == null) return;

        id = ((Number) Optional.ofNullable(data.get("ID")).orElse(-1)).intValue();
        branchCode = Objects.toString(data.get("CODE"), "");
        name = Objects.toString(data.get("NAME"), "");
        districtId = Objects.toString(data.get("DISTRICT_ID"), "");
        districtName = Objects.toString(data.get("DISTRICT_NAME"), "");
        divisionId = Objects.toString(data.get("DIVISION_ID"), "");
        divisionName = Objects.toString(data.get("DIVISION_NAME"), "");

    }
}
