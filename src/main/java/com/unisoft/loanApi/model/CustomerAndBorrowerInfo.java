package com.unisoft.loanApi.model;


import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


/**
 * Entity for the customer information which comes from manual input by the employees
 * Created By    on May 30, 2021
 */
@Entity
@Data
public class CustomerAndBorrowerInfo extends CommonEntity {

    Long customerId;
    // Account Info
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
    private String spouseMobileNo;
    private String spouseOccupation;
    private String fidStatus;
    private String homeAddress;
    private String officeAddress;
    private String permanentAddress;

    private String creditCardId;
    private String rmNameId;
    private String rmMobileNo;
    private String dealerName;
    private String dealerMobileNo;
    private String remarks;
    private String dealerPin;

    @Enumerated(EnumType.STRING)
    private CustomerAndBorrowerInfoStatus status;


}
