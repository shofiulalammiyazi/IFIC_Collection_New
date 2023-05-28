package com.unisoft.collection.distribution.loan.loanAccount;

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import lombok.Data;

import javax.persistence.*;

/*
Created by   Islam at 7/17/2019

Unique constraint on LOAN_ACCOUNT_BASIC_INFO_ID added by    on 25-03-2021
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

    public LoanAccountInfo(AccountInformationEntity accountInformationEntity,
                           LoanAccountBasicInfo loanAccountBasicInfo,
                           LoanAccountInfo loanAccountInfo) {
        this.id = loanAccountInfo.getId();
        this.schemeCode = accountInformationEntity.getProductCode();
        this.outstandingPrincipal = accountInformationEntity.getTotalOutstanding() != null?
                isDouble(accountInformationEntity.getTotalOutstanding()) ?
                        Double.valueOf(accountInformationEntity.getTotalOutstanding()):0.0:0.0;
        this.installmentAmount = isDouble(accountInformationEntity.getEmiAmount()) ?
                Double.valueOf(accountInformationEntity.getEmiAmount()):0.0;
        this.ate = accountInformationEntity.getAte() != null? isDouble(accountInformationEntity.getAte())?Double.valueOf(accountInformationEntity.getAte()):0.0:0.0;
        this.assetClassification = accountInformationEntity.getAssetClassification() == null? "-":accountInformationEntity.getAssetClassification();
        this.totalOverdue = accountInformationEntity.getOverdue()== null?isDouble(accountInformationEntity.getOverdue())?
                Double. valueOf(accountInformationEntity.getOverdue()):0.0:0.0;
        this.cumulativePayment = accountInformationEntity.getPrinciplePaid() != null ? isDouble(accountInformationEntity.getPrinciplePaid())?
                Double.valueOf(accountInformationEntity.getPrinciplePaid()):0.0:0.0;
        this.dpd = accountInformationEntity.getDpd() != null ?
                isDouble(accountInformationEntity.getDpd())?Double.valueOf(accountInformationEntity.getDpd()):0.0:0.0;
        this.dpbBucket = accountInformationEntity.getDpd() == null ?"-":accountInformationEntity.getDpd();
        //this.nonPerformingLoan = accountInformationEntity.get;
        this.emiDueDate = accountInformationEntity.getNextEMIDate() == null ? "-":accountInformationEntity.getNextEMIDate();
        //this.grossDistributionCriteriaNew = loanAccountInfo.;
        this.moDpdBucket = accountInformationEntity.getDpd() == null ? "-":String.valueOf(accountInformationEntity.getDpd());
        this.loanAccountBasicInfo = loanAccountBasicInfo;
    }

    public LoanAccountInfo(AccountInformationEntity accountInformationEntity,
                           LoanAccountBasicInfo loanAccountBasicInfo) {
        this.schemeCode = accountInformationEntity.getProductCode();
        this.outstandingPrincipal = accountInformationEntity.getTotalOutstanding() != null?
                isDouble(accountInformationEntity.getTotalOutstanding()) ?
                Double.valueOf(accountInformationEntity.getTotalOutstanding()):0.0:0.0;
        this.installmentAmount = isDouble(accountInformationEntity.getEmiAmount()) ?
                Double.valueOf(accountInformationEntity.getEmiAmount()):0.0;
        this.ate = accountInformationEntity.getAte() != null? isDouble(accountInformationEntity.getAte())?Double.valueOf(accountInformationEntity.getAte()):0.0:0.0;
        this.assetClassification = accountInformationEntity.getAssetClassification() == null? "-":accountInformationEntity.getAssetClassification();
        this.totalOverdue = accountInformationEntity.getOverdue()== null?isDouble(accountInformationEntity.getOverdue())?
                Double. valueOf(accountInformationEntity.getOverdue()):0.0:0.0;
        this.cumulativePayment = accountInformationEntity.getPrinciplePaid() != null ? isDouble(accountInformationEntity.getPrinciplePaid())?
                Double.valueOf(accountInformationEntity.getPrinciplePaid()):0.0:0.0;
        this.dpd = accountInformationEntity.getDpd() != null ?
                isDouble(accountInformationEntity.getDpd())?Double.valueOf(accountInformationEntity.getDpd()):0.0:0.0;
        this.dpbBucket = accountInformationEntity.getDpd() == null ?"-":accountInformationEntity.getDpd();
        //this.nonPerformingLoan = accountInformationEntity.get;
        this.emiDueDate = accountInformationEntity.getNextEMIDate() == null ? "-":accountInformationEntity.getNextEMIDate();
        //this.grossDistributionCriteriaNew = loanAccountInfo.;
        this.moDpdBucket = accountInformationEntity.getDpd() == null ? "-":String.valueOf(accountInformationEntity.getDpd());
        //this.loanAccountBasicInfo = loanAccountBasicInfo;
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

    boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
