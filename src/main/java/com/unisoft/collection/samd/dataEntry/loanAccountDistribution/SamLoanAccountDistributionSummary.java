package com.unisoft.collection.samd.dataEntry.loanAccountDistribution;

import lombok.Data;

@Data
public class SamLoanAccountDistributionSummary {
    private Object accountNo;
    private Object contact;
    private Object noContact;

    private Object brokenPtp;
    private Object keptPtp;
    private Object curedPtp;

    private Object saveAmount;
    private Object backAmount;
    private Object flowAmount;
    private Object followUpDate;

    private Object currentMonthPayment;
    private Object lastPayment;
    private Object lastPaymentDate;

    private Object overdueAmount;
    private Object branchName;
    private Object dpdBucket;

    private Object linkAccountNo;
    private Object linkAccountBalance;
    private Object riskCategory;
}
