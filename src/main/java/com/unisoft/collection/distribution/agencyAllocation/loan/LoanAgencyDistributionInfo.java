package com.unisoft.collection.distribution.agencyAllocation.loan;


import com.unisoft.base.BaseInfo;
import com.unisoft.collection.distribution.loan.LoanDualEnum;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class LoanAgencyDistributionInfo extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String outstanding;

    private String schemeCode;

    private Double installmentAmount;

    private String overdue;

    private String dpdBucket;

    private String productGroup;

    private String cumulativeRepayment;

    private String agencyName;

    private String agentName;

    private String agencyPin;

    private String mobileNo;

    private String handoverDate;

    private String expireDate;

    private Date statusDate;

    private String status;

    private String monitorStatus;

    private String branchName;

    private Double totalOutstanding;

    private String latest;

    private double openingOverdue;
    private double currentOverdue;
    private double emiAmount;

    private String currentDpdBucket;
    private String emiDueDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    private Date lastPaymnetDate;

    private Double lastPaidAmount = 0D;
    private Double totalPayment = 0D;
    private Double dpd = 0D;
    @Enumerated(EnumType.STRING)
    private LoanDualEnum loanDualEnum;

    @OneToOne
    private LoanAccountBasicInfo loanAccountBasicInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(String outstanding) {
        this.outstanding = outstanding;
    }

    public String getOverdue() {
        return overdue;
    }

    public void setOverdue(String overdue) {
        this.overdue = overdue;
    }

    public String getDpdBucket() {
        return dpdBucket;
    }

    public void setDpdBucket(String dpdBucket) {
        this.dpdBucket = dpdBucket;
    }

    public String getCumulativeRepayment() {
        return cumulativeRepayment;
    }

    public void setCumulativeRepayment(String cumulativeRepayment) {
        this.cumulativeRepayment = cumulativeRepayment;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getHandoverDate() {
        return handoverDate;
    }

    public void setHandoverDate(String handoverDate) {
        this.handoverDate = handoverDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getMonitorStatus() {
        return monitorStatus;
    }

    public void setMonitorStatus(String monitorStatus) {
        this.monitorStatus = monitorStatus;
    }

    public LoanDualEnum getLoanDualEnum() {
        return loanDualEnum;
    }

    public void setLoanDualEnum(LoanDualEnum loanDualEnum) {
        this.loanDualEnum = loanDualEnum;
    }



    public LoanAccountBasicInfo getLoanAccountBasicInfo() {
        return loanAccountBasicInfo;
    }

    public void setLoanAccountBasicInfo(LoanAccountBasicInfo loanAccountBasicInfo) {
        this.loanAccountBasicInfo = loanAccountBasicInfo;
    }
}
