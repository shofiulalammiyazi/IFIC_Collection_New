package com.unisoft.collection.distribution.loan.loanAccountDistribution;

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Date;

@Data
public class LoanAccountDistributionDto {

    private String customerId;

    private String accountNo;

    private String customerName;

    private Date createdDate;

    private String outstandingAmount;

    private String minimumDueAmount;

    private String location;

    private String dpdBucket;

    private String ageCode;

    private String supervisorName;

    private String delaerPin;

    private String delaerName;

    private String AgencyName;

    private String branchName;

    private String monirotingStatus;
    private String branchMnemonic;
    private String totalOutstanding;
    private String productCode;
    private String dealReference;
    private String overdue;
    private String emiAmount;
    private String nextEMIDate;
    private String noOfInstallmentDue;
    private String loanCLStatus;
    private String mobile;

    public LoanAccountDistributionDto(Tuple t) {
        this.customerId = customerId;
        this.accountNo = accountNo;
        this.customerName = customerName;
        this.createdDate = createdDate;
        this.outstandingAmount = outstandingAmount;
        this.minimumDueAmount = minimumDueAmount;
        this.location = location;
        this.dpdBucket = dpdBucket;
        this.ageCode = ageCode;
        this.supervisorName = supervisorName;
        this.delaerPin = delaerPin;
        this.delaerName = delaerName;
        this.branchName = branchName;
        this.monirotingStatus = monirotingStatus;
        this.branchMnemonic = branchMnemonic;
        this.totalOutstanding = totalOutstanding;
        this.productCode = productCode;
        this.dealReference = dealReference;
        this.overdue = overdue;
        this.emiAmount = emiAmount;
        this.nextEMIDate = nextEMIDate;
        this.noOfInstallmentDue = noOfInstallmentDue;
        this.loanCLStatus = loanCLStatus;
        this.mobile = mobile;
    }
}
