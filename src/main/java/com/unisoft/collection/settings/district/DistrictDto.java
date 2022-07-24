package com.unisoft.collection.settings.district;
/*
Created by Yasir Araphat on 9 March 2021
*/

import lombok.Data;

@Data
public class DistrictDto {

    private Long id;
    private String name;
    private Long divisionId;
    private String divisionName;

    public DistrictDto() {
    }

    public DistrictDto(DistrictEntity district) {
        if (district == null || district.getId() == null) return;

        id = district.getId();
        name = district.getName();
        if (district.getDivision() != null) {
            divisionId = district.getDivision().getDivId();
            divisionName = district.getDivision().getDivName();
        }
    }


}
