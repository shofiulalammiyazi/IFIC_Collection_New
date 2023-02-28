package com.unisoft.collection.distribution.loan.loanAccountDistribution;

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.Objects;

@Data
public class LoanAccountDistributionDto {

    private String accountNo;

    private String customerName;

    private String dpdBucket;

    private String supervisorName;

    private String delaerPin;

    private String delaerName;
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
    private String isSmsSent;

    public LoanAccountDistributionDto(Tuple t) {
        this.accountNo = Objects.toString(t.get("LOANACNO"),"-").trim();
        this.customerName = Objects.toString(t.get("CUSTOMER_NAME"),"-").trim();
        this.dpdBucket = Objects.toString(t.get("DPD"),"-").trim();
        this.supervisorName = Objects.toString(t.get("SUPERVISOR_NAME"),"-").trim();
        this.delaerPin = Objects.toString(t.get("DEALER_PIN"),"-").trim();
        this.delaerName = Objects.toString(t.get("DEALER_NAME"),"-").trim();
        this.branchMnemonic = Objects.toString(t.get("BRANCH_MNEMONIC"),"-").trim();
        this.totalOutstanding = Objects.toString(t.get("TOTAL_OUTSTANDING"),"-").trim();
        this.productCode = Objects.toString(t.get("PRODUCT_CODE"),"-").trim();
        this.dealReference = Objects.toString(t.get("DEAL_REFERENCE"),"-").trim();
        this.overdue = Objects.toString(t.get("OVERDUE"),"-").trim();
        this.emiAmount = Objects.toString(t.get("EMI_AMOUNT"),"-").trim();
        this.nextEMIDate = Objects.toString(t.get("NEXTEMIDATE"),"-").trim();
        this.noOfInstallmentDue = Objects.toString(t.get("NO_OF_INSTALLMENT_DUE"),"-").trim();
        this.loanCLStatus = Objects.toString(t.get("LOANCLSTATUS"),"-").trim();
        this.mobile = Objects.toString(t.get("MOBILE"),"-").trim();
        this.isSmsSent = Objects.toString(t.get("IS_SMS_SENT"),"-").trim();
    }
}
