package com.csinfotechbd.legal.setup.lawyers;


// Created by Yasir Araphat on 09 March, 2021

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Objects;

@Data
public class LawyerDto {

    private Long id;
    //    private String lawyerId;
    private String name;
    private String phoneNo;
    private String mobileNo;

    public LawyerDto() {
    }

    public LawyerDto(Tuple data) {
        id = ((Number) data.get("ID")).longValue();
        name = Objects.toString(data.get("NAME"), "-");
        mobileNo = Objects.toString(data.get("MOBILE_NO"), "-");
        phoneNo = Objects.toString(data.get("MOBILE_NO"), "-");
    }

    public LawyerDto(Lawyer lawyer) {
        if (lawyer == null || lawyer.getId() == null) return;

        id = lawyer.getId();
        name = lawyer.getName();
//        lawyerId = lawyer.getLawyerId();
        if (lawyer.getMobileNo() != null)
            mobileNo = lawyer.getMobileNo();
        if (lawyer.getPhoneNo() != null)
            phoneNo = lawyer.getPhoneNo();
    }

}
