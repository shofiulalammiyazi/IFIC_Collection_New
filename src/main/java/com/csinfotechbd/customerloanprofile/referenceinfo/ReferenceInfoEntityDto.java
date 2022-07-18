package com.csinfotechbd.customerloanprofile.referenceinfo;

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


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
    private String facilityWithUcbl;

}

