package com.unisoft.customerloanprofile.referenceinfo;

import com.unisoft.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class ReferenceInfoEntity extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private String dealerPin;
    private String facilityWithUcbl;
    private String dob;
    private String nidOrPassport;

    @Enumerated(EnumType.STRING)
    private ReferenceInfoStatus status;

//    private String status;
    private String customerId;
    private String loanAccountNo;
    private String fatherName;
    private String motherName;
    private String optimaId;

//    @ManyToOne (cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
//    @JoinColumn(name = "CUSTOMER_ID")
//    @JsonIgnore
//    private CustomerBasicInfoEntity customerBasicInfo;
}

