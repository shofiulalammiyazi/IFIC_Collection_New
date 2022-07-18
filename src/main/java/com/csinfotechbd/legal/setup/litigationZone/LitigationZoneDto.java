package com.csinfotechbd.legal.setup.litigationZone;


// Created by Yasir Araphat on 24 March, 2021

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Optional;

@Data
public class LitigationZoneDto {


    private long id;
    private String name;
    private String[] branchNames;
    private String[] districtNames;

    public LitigationZoneDto() {
    }

    public LitigationZoneDto(Tuple data) {
        id = ((Number) Optional.ofNullable(data.get("ID")).orElse(0)).longValue();
        name = Optional.ofNullable(data.get("NAME")).orElse("-").toString();
        branchNames = Optional.ofNullable(data.get("BRANCHES")).orElse("").toString().split(",");
        districtNames = Optional.ofNullable(data.get("DISTRICTS")).orElse("").toString().split(",");
    }

}
