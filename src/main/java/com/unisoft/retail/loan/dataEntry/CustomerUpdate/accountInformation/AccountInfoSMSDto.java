package com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation;

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Objects;

@Data
public class AccountInfoSMSDto {

    private String loanACNo;

    private String customerName;

    private String mobile;

    private String branchMnemonic;

    private String productCode;

    private String dealReference;

    private String totalOutstanding;

    private String overdue;

    private String emiAmount;

    private String nextEMIDate;

    private String noOfInstallmentDue;

    private String loanCLStatus;

    public AccountInfoSMSDto(Tuple t) {
        this.loanACNo = Objects.toString(t.get("LOANACNO"),"-");
        this.customerName = Objects.toString(t.get("CUSTOMER_NAME"),"-");
        this.mobile = Objects.toString(t.get("MOBILE"),"-");
        this.branchMnemonic = Objects.toString(t.get("BRANCH_MNEMONIC"),"-");
        this.productCode = Objects.toString(t.get("PRODUCT_CODE"),"-");
        this.dealReference = Objects.toString(t.get("DEAL_REFERENCE"),"-");
        this.totalOutstanding = Objects.toString(t.get("TOTAL_OUTSTANDING"),"-");
        this.overdue = Objects.toString(t.get("OVERDUE"),"-");
        this.emiAmount = Objects.toString(t.get("EMI_AMOUNT"),"-");
        this.nextEMIDate = Objects.toString(t.get("NEXTEMIDATE"),"-");
        this.noOfInstallmentDue = Objects.toString(t.get("NO_OF_INSTALLMENT_DUE"),"-");
        this.loanCLStatus = Objects.toString(t.get("LOANCLSTATUS"),"-");
    }
}
