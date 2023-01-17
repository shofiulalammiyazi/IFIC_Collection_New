package com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation;

import com.unisoft.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class AccountInformationEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;

    private String loanACNo;
    private String loanACNo2;
    private String loanACNo3;
    private String loanACNo4;
    private String linkMotherAccountNo;
    private String LinkAccountStatus;
    private String loanCLStatus;
    private String settlementLinkAccountBalance;
    private String productType;
    private String productCode;
    private String productName;
    private String linkACProductCode;
    private String branchName;
    private String branchCode;
    private String routingNo;
    private String disbursementAmount;
    private String sanctionAmount;
    private String disbursementDate;
    private String expiryDate;
    private String firstInstDueDate;
    private String installmentFrequency;
    private String totalSanctionFacilityLimit;
    private String loanApprovalDate;
    private String gracePeriod;
    private String actualTenor;
    private String firstInstallmentDefaultFID;
    private String latestDisbursementDate;
    private String latestDisbursementAmount;
    private String totalOutstanding;
    private String interestRate;
    private String overdue;
    private String totalNoOfInstallment;
    private String emiAmount;
    private String nextEMIDate;
    private String emiDate;
    private String ate;
    private String dpd;
    private String assetClassification;
    private String dpdAfterExpiryDate;
    private String paymentDuringTheMonth;
    private String lastPaymentAmount;
    private String paidAmountInLastMonth;
    private String lastPaymentDate;
    private String noOfInstallmentDue;
    private String noOfInstallmentPaid;
    private String principlePaid;
    private String interestPaid;
    private String subsidyFromBBCOVID;
    private String waiverOfInterest;
    private String totalEnhancementAmount;
    private String interestSuspense;
    private String noOverdueInstallment;
    private String dstSourceCode;
    private String rmCode;
    private String economicPurposeCode;
    private String economicPurposeName;
    private String sectorCode;
    private String sectorName;
    private String industryScaleID;
    private String securityCode;
    private String smeCodeIndustryScaleID;
    private String smeDesc;
    private String creditRating;
    private String location;
    private String totalAmountPaid;
    private String penalCharge;
    private String natureOfBusiness;
    private String mortgageRIGPADeferralStatus;
    private String rentalPaymentOfMortgagePropertyYear;
    private String mortgagedAmount;
    private String whereNEPFNEEAN;

    private String borrowersName;
    private String profession;
    private String ProfessionSegment;

    private String email;
    private String nid;
    private String fatherName;

    private String customerId;
    private String customerName;
    private String mobile;
    private String profileEmail;
    private String customerType;
    private String dob;
    private String gender;

    private String motherName;
    private String spouse;
    private String ni;
    private String idNumber;
    private String tin;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String address5;

    //new add

    private String contractNo;
    private String contractNoHome;
    private String accountTitle;

    private String branchMnemonic;
    private String dealReference;
    private String docType;
    private String partyId;
    private String dealAcBasic;
    private String dealAcSuffix;
    private String uniqueDealLoan;
    private String loanAccountNew;
    private String isSmsSent = "N";
    private String isDistributed = "N";

    //new field 16/01/2023
    private String division;
    private String district;
    private String scheduleStartDate;
    private String dealBalanceAtStartDate;
    private String calculatedMaturityDate;
    private String calculatedRepaymentDate;
    //private String calculatedRepaymentAmount;
    private String firstRepaymentAmount;
    private String lastRepaymentAmount;
    private String calculatedNoOfRepayment; //Total No. of Installment :
    private String firstEmiDate;
    private String frequencyCode;
    private String jointStatus;

}
