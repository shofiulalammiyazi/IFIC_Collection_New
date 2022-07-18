package com.csinfotechbd.legal.setup.courts;

/*
Created by Yasir Araphat on 9 March 2021
*/

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourtsDto {

    private Long id;
    private String name;

    public CourtsDto() {
    }

    public CourtsDto(Courts courts) {
        if (courts == null || courts.getId() == null) return;

        id = courts.getId();
        name = courts.getName();
    }

}
