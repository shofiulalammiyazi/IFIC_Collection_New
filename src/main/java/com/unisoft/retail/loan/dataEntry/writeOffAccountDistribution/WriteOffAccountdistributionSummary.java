package com.unisoft.retail.loan.dataEntry.writeOffAccountDistribution;

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Objects;

@Data
public class WriteOffAccountdistributionSummary {

    private String accountNo;
    private String customerName;
    private String numberOfContact;
    private String numberOfRightPartyContact;
    private String numberOfGuarantorOrThirdPartyContact;
    private String totalPtp;
    private String brokenPtp;
    private String kept;
    private String numberOfVisit;
    private String followUpDate;
    private String dpdBucket;
    private String outstanding;
    private String overdueAmount;
    private String emiAmount;
    private String branchName;
    private String riskCategory;
    private String allocationDate;

    public WriteOffAccountdistributionSummary() {
    }

    public WriteOffAccountdistributionSummary(Tuple tuple) {
        accountNo = Objects.toString(tuple.get("accountNumber"), "-");
        customerName = Objects.toString(tuple.get("accountName"), "-");
        numberOfContact = Objects.toString(tuple.get("noOfAttempt"), "-");
        numberOfRightPartyContact = Objects.toString(tuple.get("rightParty"), "-");
        numberOfGuarantorOrThirdPartyContact = Objects.toString(tuple.get("thirdParty"), "-");
        totalPtp = Objects.toString(tuple.get("noOfPtp"), "-");
        brokenPtp = Objects.toString(tuple.get("broken"), "-");
        kept = Objects.toString(tuple.get("promiseTaken"), "-");
//        numberOfVisit = Objects.toString(tuple.get("numberOfVisit"), "-");
        followUpDate = Objects.toString(tuple.get("followUpDate"), "-");
        dpdBucket = Objects.toString(tuple.get("bucket"), "-");
        outstanding = Objects.toString(tuple.get("outstanding"), "-");
        overdueAmount = Objects.toString(tuple.get("overDue"), "-");
        emiAmount = Objects.toString(tuple.get("emiAmt"), "-");
        branchName = Objects.toString(tuple.get("branchName"), "-");
        riskCategory = Objects.toString(tuple.get("riskCategory"), "-");
        allocationDate = Objects.toString(tuple.get("allocationDate"), "-");
    }


}
