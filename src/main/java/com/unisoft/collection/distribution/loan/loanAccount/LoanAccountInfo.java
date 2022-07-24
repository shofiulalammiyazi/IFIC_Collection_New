package com.unisoft.collection.distribution.loan.loanAccount;

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import lombok.Data;

import javax.persistence.*;

/*
Created by   Islam at 7/17/2019

Unique constraint on LOAN_ACCOUNT_BASIC_INFO_ID added by Yasir Araphat on 25-03-2021
*/
@Data
@Entity
@Table(name = "LOAN_ACCOUNT_INFO",
        uniqueConstraints = @UniqueConstraint(columnNames = {"LOAN_ACCOUNT_BASIC_INFO_ID"})
)
public class LoanAccountInfo extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String schemeCode;

    private double outstandingPrincipal;

    private double installmentAmount;

    private Double ate;

    private String assetClassification;

    private double totalOverdue;

    private Double totalOutstanding;

    private double cumulativePayment;

    private Double dpd;

    private String dpbBucket;

    private String nonPerformingLoan;

    private String emiDueDate;

    private String grossDistributionCriteriaNew;

    private String moDpdBucket;

    private String branchName;

    @OneToOne
    @JoinColumn(name = "LOAN_ACCOUNT_BASIC_INFO_ID")
    private LoanAccountBasicInfo loanAccountBasicInfo;

    public LoanAccountInfo() {
    }

    public LoanAccountInfo(LoanAccountBasicInfo loanAccountBasicInfo) {
        this.loanAccountBasicInfo = loanAccountBasicInfo;
    }

    public LoanAccountInfo(String schemeCode, double outstandingPrincipal, double installmentAmount, double ate, String assetClassification, double totalOverdue, double cumulativePayment, double dpd, String dpbBucket, String nonPerformingLoan, String emiDueDate, String grossDistributionCriteriaNew, String moDpdBucket, LoanAccountBasicInfo loanAccountBasicInfo) {
        this.schemeCode = schemeCode;
        this.outstandingPrincipal = outstandingPrincipal;
        this.installmentAmount = installmentAmount;
        this.ate = ate;
        this.assetClassification = assetClassification;
        this.totalOverdue = totalOverdue;
        this.cumulativePayment = cumulativePayment;
        this.dpd = dpd;
        this.dpbBucket = dpbBucket;
        this.nonPerformingLoan = nonPerformingLoan;
        this.emiDueDate = emiDueDate;
        this.grossDistributionCriteriaNew = grossDistributionCriteriaNew;
        this.moDpdBucket = moDpdBucket;
        this.loanAccountBasicInfo = loanAccountBasicInfo;
    }

}
