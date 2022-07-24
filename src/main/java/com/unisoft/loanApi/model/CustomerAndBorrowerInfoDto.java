package com.unisoft.loanApi.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CustomerAndBorrowerInfoDto {

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
    // Personal info
    private String occupation;
    private String organizationName;
    private String contactNo;
    private String emailAddress;
    private String spouseName;
    private String spouseOccupation;
    private String fidStatus;

    private String creditCardId;
    private String rmNameId;
    private String rmMobileNo;
    private String dealerName;
    private String dealerMobileNo;
    private String remarks;
}
