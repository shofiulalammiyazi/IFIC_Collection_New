package com.csinfotechbd.retail.card.dataEntry.distribution.accountDistributionInfo;
/*
 * Created by Yasir Araphat on 17 May, 2021
 */

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Objects;

@Data
public class CardAccountDistributionSummary {
    private String cardNo;
    private String clientId;
    private String cardName;
    private String mobileNumber;
    private double outstandingAmount;
    private double minDuePayment;
    private String ageCode;
    private String stateCode;
    private double saveAmount;
    private double backAmount;
    private String totalLimit;
    private String accountNo;

    private String customerName;

    private String numberOfContact;

    private String numberOfRightPartyContact;

    private String numberOfGuarantorOrThirdPartyContact;

    private String totalPtp;

    private String brokenPtp;

    private String curedPtp;

    private String numberOfVisit;

    private String followUpDate;

    private String dpdBucket;
    private String outstanding;
    private String overdueAmount;
    private String emiAmount;

    private String bdtLimit;
    private String bdtOutstanding;
    private String bdtMinDue;
    private String bdtEol;
    private String usdLimit;
    private String usdOutstanding;
    private String usdMinDue;
    private String usdEol;

    private String lastPayment;

    private String currentMonthPayment;

    private String branchName;

    private String riskCategory;
    private String anniversaryDate;
    private String allocationDate;

    private String noOfDays;
    private String clStatus;
    private String eol;

    public CardAccountDistributionSummary() {
    }

    public CardAccountDistributionSummary(Tuple summary) {
        accountNo = Objects.toString(summary.get("accountNo"), "-");
        clientId = Objects.toString(summary.get("clientId"), "-");
        customerName = Objects.toString(summary.get("customerName"), "-");
        numberOfContact = Objects.toString(summary.get("numberOfContact"), "-");
        numberOfRightPartyContact = Objects.toString(summary.get("numberOfRightPartyContact"), "-");
        numberOfGuarantorOrThirdPartyContact = Objects.toString(summary.get("numberOfGuarantorOrThirdPartyContact"), "-");
        totalPtp = Objects.toString(summary.get("totalPtp"), "-");
        brokenPtp = Objects.toString(summary.get("brokenPtp"), "-");
        curedPtp = Objects.toString(summary.get("curedPtp"), "-");
        numberOfVisit = Objects.toString(summary.get("numberOfVisit"), "-");
        followUpDate = Objects.toString(summary.get("followUpDate"), "-");
        dpdBucket = Objects.toString(summary.get("dpdBucket"), "-");
        noOfDays = Objects.toString(summary.get("dpd"),"-");
        clStatus = Objects.toString(summary.get("CLSTATUS"),"-");
        outstanding = Objects.toString(summary.get("outstanding"), "-");
        totalLimit = Objects.toString(summary.get("totalLimit"), "-");
        bdtLimit = Objects.toString(summary.get("bdtLimit"), "-");
        bdtOutstanding = Objects.toString(summary.get("bdtOutstanding"), "-");
        bdtMinDue = Objects.toString(summary.get("bdtMinDue"), "-");
        bdtEol = Objects.toString(summary.get("bdtEol"), "-");
        usdLimit = Objects.toString(summary.get("usdLimit"), "-");
        usdOutstanding = Objects.toString(summary.get("usdOutstanding"), "-");
        usdMinDue = Objects.toString(summary.get("usdMinDue"), "-");
        usdEol = Objects.toString(summary.get("usdEol"), "-");
        overdueAmount = Objects.toString(summary.get("overdueAmount"), "-");
        emiAmount = Objects.toString(summary.get("emiAmount"), "-");
        lastPayment = Objects.toString(summary.get("lastPayment"), "-");
        currentMonthPayment = Objects.toString(summary.get("currentMonthPayment"), "-");
        branchName = Objects.toString(summary.get("branchName"), "-");
        anniversaryDate = Objects.toString(summary.get("anniversaryDate"), "-");
        riskCategory = Objects.toString(summary.get("riskCategory"), "-");
        allocationDate = Objects.toString(summary.get("allocationDate"), "-");
    }

//    public CardAccountDistributionSummary(Tuple data) {
//        cardNo = Objects.toString(data.get("CARD_NO"), "-");
//        clientId = Objects.toString(data.get("CLIENT_ID"), "-");
//        cardName = Objects.toString(data.get("CARD_NAME"), "-");
//        mobileNumber = Objects.toString(data.get("MOBILE_NUMBER"), "-");
//        outstandingAmount = ((Number) data.get("OUTSTANDING_AMOUNT")).doubleValue();
//        minDuePayment = ((Number) data.get("MIN_DUE_PAYMENT")).doubleValue();
//        ageCode = Objects.toString(data.get("AGE_CODE"), "-");
//        stateCode = Objects.toString(data.get("STATE_CODE"), "-");
//        saveAmount = ((Number) data.get("SAVE_AMOUNT")).doubleValue();
//        backAmount = ((Number) data.get("BACK_AMOUNT ")).doubleValue();
//    }
}
