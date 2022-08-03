package com.unisoft.collection.settings.division;
/*
Created by    on 3/11/2021
*/

import lombok.Data;

@Data
public class DivisionDto {
    private Long divId;
    private String divName;

    public DivisionDto() {
    }

    public DivisionDto(DivisionEntity division) {
        if (division == null || division.getDivId() == null) return;
        divId = division.getDivId();
        divName = division.getDivName();
    }

}
