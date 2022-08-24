package com.unisoft.customerloanprofile.referenceinfo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Setter
@Getter
public class ReferenceInfoEntityDto{
    private Long id;
    private String accountNo;
    private String name;
    private String occupation;
    private String companyName;
    private String designation;
    private String homeAddress;
    private String officeAddress;
    private String permanentAddress;
    private String phoneNo;
    private String relationReference;
    private String dob;
    private String nid;
    private String facilityWithBank;

}

