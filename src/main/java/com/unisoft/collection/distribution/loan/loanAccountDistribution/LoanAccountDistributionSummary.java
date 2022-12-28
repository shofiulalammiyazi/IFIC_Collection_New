package com.unisoft.collection.distribution.loan.loanAccountDistribution;

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Objects;

/**
 * Summary model for Loan account details
 * <p>
 * Modified By SHAHIN
 * At 28 DEC 2020
 */
@Data
public class LoanAccountDistributionSummary {

    private String accountNo;

    private String customerName;

    private String productShortName;

    private String numberOfContact;

    private String numberOfRightPartyContact;

    private String numberOfGuarantorOrThirdPartyContact;

    private String totalPtp;

    private String brokenPtp;

    private String curedPtp;

    private String numberOfVisit;

    private String followUpDate;

    private String dpdBucket;

    private String currentDpdBucket;

    private String outstanding;

    private String overdueAmount;

    private String emiAmount;

    private String lastPayment;

    private String currentMonthPayment;

    private String branchName;

    private String riskCategory;

    private String allocationDate;


    //new shahin

    private String branchMnemonic;
    private String dealReference;
    private String productCode;

    public LoanAccountDistributionSummary() {
    }

    public LoanAccountDistributionSummary(Tuple summary) {
        accountNo = Objects.toString(summary.get("accountNo"), "-");
        customerName = Objects.toString(summary.get("customerName"), "-");
        productShortName = Objects.toString(summary.get("productShortName"), "-");
        numberOfContact = Objects.toString(summary.get("numberOfContact"), "-");
        numberOfRightPartyContact = Objects.toString(summary.get("numberOfRightPartyContact"), "-");
        numberOfGuarantorOrThirdPartyContact = Objects.toString(summary.get("numberOfGuarantorOrThirdPartyContact"), "-");
        totalPtp = Objects.toString(summary.get("totalPtp"), "-");
        brokenPtp = Objects.toString(summary.get("brokenPtp"), "-");
        curedPtp = Objects.toString(summary.get("curedPtp"), "-");
        numberOfVisit = Objects.toString(summary.get("numberOfVisit"), "-");
        followUpDate = Objects.toString(summary.get("followUpDate"), "-");
        dpdBucket = Objects.toString(summary.get("dpdBucket"), "-");
        currentDpdBucket = Objects.toString(summary.get("currentDpdBucket"), "-");
        outstanding = Objects.toString(summary.get("outstanding"), "-");
        overdueAmount = Objects.toString(summary.get("overdueAmount"), "-");
        emiAmount = Objects.toString(summary.get("emiAmount"), "-");
        lastPayment = Objects.toString(summary.get("lastPayment"), "-");
        currentMonthPayment = Objects.toString(summary.get("currentMonthPayment"), "-");
        branchName = Objects.toString(summary.get("branchName"), "-");
        riskCategory = Objects.toString(summary.get("riskCategory"), "-");
        allocationDate = Objects.toString(summary.get("allocationDate"), "-");
        branchMnemonic = Objects.toString(summary.get("branchMnemonic"), "-");
        dealReference = Objects.toString(summary.get("dealReference"), "-");
        productCode = Objects.toString(summary.get("productCode"), "-");
    }
}
