package com.unisoft.collection.distribution.loan.loanAccountDistribution;
/*
Created by   Islam at 7/17/2019
*/

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.unisoft.retail.loan.dataEntry.ptp.LoanPtp;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.unisoft.utillity.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;


@Data
@Entity
public class LoanAccountDistributionInfo extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String supervisorName;

    private String supervisorPin;

    private String dealerName;

    private String dealerPin;

    private String samAccount;

    private String writeOffAccount;

    private String remarks;
    //@Temporal(TemporalType.DATE)

    private Date statusDate;

    @ManyToOne
    private LoanAccountBasicInfo loanAccountBasicInfo;

    private String productGroup;

    private String dpdBucket; // Month opening bucket

    private String currentDpdBucket; // Latest Bucket from core banking

    private String outStanding;

    private String schemeCode;

    private Double openingOverDue = 0D; // Month opening overdue

    private Double currentOverdue = 0D; // Latest Overdue from core banking

    private Double emiAmount = 0D;

    private String emiDueDate;

    private Double dpd = 0D;

    private Double totalPayment = 0D;

    private String latest;

    private String monitoringStatus = "SINGLE";

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date scheduleDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    private Date lastPaymnetDate;

    private Double lastPaidAmount = 0D;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date endDate;

    @Transient
    private double parRelAmnt = 0;

    @Transient
    private double nplRelAmnt = 0;

    @Transient
    private double saveAmount;

    @Transient
    private double backAmount;

    @Transient
    private double presentOverDue;

    @Transient
    private double liveDpd;

    @Transient
    private double remSaveAmount = 0;

    @Transient
    private double remBackAmount = 0;

    @Transient
    private double remNplRelAmount = 0;

    @Transient
    private double remParRelAmount = 0;

    @Transient
    private String visitDate;

    @Transient
    private LoanPtp loanPtp;

    public LoanAccountDistributionInfo() {
    }

    public LoanAccountDistributionInfo(String supervisorName, String supervisorPin, String dealerName, String dealerPin, Date statusDate, LoanAccountBasicInfo loanAccountBasicInfo) {
        this.supervisorName = supervisorName;
        this.supervisorPin = supervisorPin;
        this.dealerName = dealerName;
        this.dealerPin = dealerPin;
        this.statusDate = statusDate;
        this.loanAccountBasicInfo = loanAccountBasicInfo;
    }


    public LoanAccountDistributionInfo(String schemeCode, Double openingOverDue, Double outStanding, Date statusDate, Double dpd, String dpdBucket, String productGroup, String dealerPin) {
        this.schemeCode = schemeCode;
        this.openingOverDue = Optional.ofNullable(openingOverDue).orElse(0d);
        this.outStanding = Objects.toString(outStanding, "0");
        this.statusDate = statusDate;
        this.dpd = Optional.ofNullable(dpd).orElse(0d);
        this.dpdBucket = dpdBucket;
        this.productGroup = productGroup;
        this.dealerPin = dealerPin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        // Custom modification to avoid unnecessary entry in Set<LoanAccountDistributionInfo>
        if (o == null || getClass() != o.getClass()) return true; // Default was false
        if (!super.equals(o)) return false;
        LoanAccountDistributionInfo that = (LoanAccountDistributionInfo) o;
        // Custom modification to avoid unnecessary entry in Set<LoanAccountDistributionInfo>
        if (loanAccountBasicInfo == null || that.loanAccountBasicInfo == null) return true;
        return Objects.equals(loanAccountBasicInfo.getAccountNo(), that.loanAccountBasicInfo.getAccountNo());
    }

    public String getOutStanding() {
        return StringUtils.hasText(outStanding) ? outStanding : "0";
    }

    public double getNumericOutStanding() {
        return Double.valueOf(getOutStanding());
    }

    public Double getOpeningOverDue() {
        return Optional.ofNullable(openingOverDue).orElse(0d);
    }

    public Double getEmiAmount() {
        return Optional.ofNullable(emiAmount).orElse(0d);
    }

    public Double getDpd() {
        return Optional.ofNullable(dpd).orElse(0d);
    }

    public Double getTotalPayment() {
        return Optional.ofNullable(totalPayment).orElse(0d);
    }

    public void setOutStanding(String outStanding) {
        this.outStanding = StringUtils.hasText(outStanding) ? outStanding : "0";
    }

    public void setOpeningOverDue(Double openingOverDue) {
        this.openingOverDue = Optional.ofNullable(openingOverDue).orElse(0D);
    }

    public void setEmiAmount(Double emiAmount) {
        this.emiAmount = Optional.ofNullable(emiAmount).orElse(0D);
    }

    public void setDpd(Double dpd) {
        this.dpd = Optional.ofNullable(dpd).orElse(0D);
    }

    public void setTotalPayment(Double totalPayment) {
        this.totalPayment = Optional.ofNullable(totalPayment).orElse(0D);
    }

    public void setLastPaidAmount(Double lastPaidAmount) {
        this.lastPaidAmount = Optional.ofNullable(lastPaidAmount).orElse(0D);
    }

    private String branchMnemonic;
    private String productCode;
    private String dealReference;
    private String accountNo;

}
