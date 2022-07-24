package com.unisoft.collection.samd.customerprofile.detailsaccount;

import com.unisoft.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class DetailsOfTheAccount extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String loanAccountNo;
    private String dealerPin;
    private String nameOfTheCompany;
    private String addressOfTheCompany;
    private String constructionLegalStatus;
    private String yearOfEstablishment;
    private String particularOfCdAccount;
    private String accountNumber;
    private String dateOfAccountOpening;
    private String borrowarRelationship;
    private String ownerInformation;
    private String natureOfBusiness;
    private String natureOfCreditFacility;
    private String facilityLimit;
    private String approvalAuthSanctionNoDate;
    private String incumbencyLoanDisbursed;
    private String branchManagerNameNo;
    private String dateOfDisbursement;
    private String dateOfExpiry;
    private String bankOtherEffortRecovery;


}

