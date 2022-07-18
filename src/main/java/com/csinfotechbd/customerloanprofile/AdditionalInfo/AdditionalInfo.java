package com.csinfotechbd.customerloanprofile.AdditionalInfo;

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class AdditionalInfo extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String branchName;
    private String accountName;
    private String loanAcNumber;
    private String casaAcNumber;
    private String loanLimit;
    private String loanOS;
    private String overDue;
    private String overDueAmount;
    private String emi;
    private String presentEmi;
    private String rateOfInterest;
    private String presentRateInterest;
    private String bucket;
    private String crntScheduleDate;
    private String paymentDate;
    private String paymentAmount;
    private String totalPayment;
    private String disburseDate;
    private String deferredStatus;
    private String expiryDate;

    private String creditCardId;
    private String rmNameId;
    private String rmMobileNo;
    private String dealerName;
    private String dealerMobileNo;
    private String spouseName;
    private String fidStatus;


    //for display
    private String accountNo;
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
