package com.csinfotechbd.collection.distribution.loan.loanAccountDistribution;

import com.csinfotechbd.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;

import javax.persistence.*;
import java.util.Date;

/*
Created by Monirul Islam at 9/1/2019
*/
@Entity
public class AutoDistributionTemp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String supervisorName;

    private String supervisorPin;

    private String dealerName;

    private String dealerPin;

    private String samAccount;

    private String writeOffAccount;

    @Temporal(TemporalType.DATE)
    private Date statusDate;

    @OneToOne
    private LoanAccountBasicInfo loanAccountBasicInfo;

    private String productGroup;

    private String dpdBucket;

    private String outStanding;

    @Lob
    private String distributionExceptionLoanJson;

    @Transient
    private String dpd;

    public AutoDistributionTemp() {
    }

    public String getDistributionExceptionLoanJson() {
        return distributionExceptionLoanJson;
    }

    public void setDistributionExceptionLoanJson(String distributionExceptionLoanJson) {
        this.distributionExceptionLoanJson = distributionExceptionLoanJson;
    }

    public String getOutStanding() {
        return outStanding;
    }

    public void setOutStanding(String outStanding) {
        this.outStanding = outStanding;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getDpdBucket() {
        return dpdBucket;
    }

    public void setDpdBucket(String dpdBucket) {
        this.dpdBucket = dpdBucket;
    }

    public AutoDistributionTemp(String supervisorName, String supervisorPin, String dealerName, String dealerPin, Date statusDate, LoanAccountBasicInfo loanAccountBasicInfo) {
        this.supervisorName = supervisorName;
        this.supervisorPin = supervisorPin;
        this.dealerName = dealerName;
        this.dealerPin = dealerPin;
        this.statusDate = statusDate;
        this.loanAccountBasicInfo = loanAccountBasicInfo;
    }

    public String getDpd() {
        return dpd;
    }

    public void setDpd(String dpd) {
        this.dpd = dpd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public String getSupervisorPin() {
        return supervisorPin;
    }

    public void setSupervisorPin(String supervisorPin) {
        this.supervisorPin = supervisorPin;
    }

    public String getDealerName() {
        return dealerName;
    }

    public String getSamAccount() {
        return samAccount;
    }

    public void setSamAccount(String samAccount) {
        this.samAccount = samAccount;
    }

    public String getWriteOffAccount() {
        return writeOffAccount;
    }

    public void setWriteOffAccount(String writeOffAccount) {
        this.writeOffAccount = writeOffAccount;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getDealerPin() {
        return dealerPin;
    }

    public void setDealerPin(String dealerPin) {
        this.dealerPin = dealerPin;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public LoanAccountBasicInfo getLoanAccountBasicInfo() {
        return loanAccountBasicInfo;
    }

    public void setLoanAccountBasicInfo(LoanAccountBasicInfo loanAccountBasicInfo) {
        this.loanAccountBasicInfo = loanAccountBasicInfo;
    }
}
