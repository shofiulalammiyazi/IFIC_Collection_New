package com.csinfotechbd.collection.samd.dataEntry.loanAccountDistribution;

import lombok.Data;

/*
* created by ~ Hasibul Islam
* 28 Mar, 2021 10:34 AM
* */
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
