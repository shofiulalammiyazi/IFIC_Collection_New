package com.csinfotechbd.retail.card.dataEntry.additionalinfocard;

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.customerloanprofile.AdditionalInfo.AdditionalInfoStatus;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class AdditionalInfoCard extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String contractId;
    private String clientId;
    private String cardType;
    private String occupation;
    private String orgName;
    private String contactNo;
    private String email;
    private String spouseMblNo;
    private String spouseOccupation;
    private String spouseAcNo;
    private String spouseFatherName;
    private String spouseMotherName;
    private String homeAddress;
    private String officeAddress;
    private String permanentAddress;
    private String remarks;
    private String dealerPin;

    @Enumerated(EnumType.STRING)
    private AdditionalInfoStatus status;

    private String customerId;
}
