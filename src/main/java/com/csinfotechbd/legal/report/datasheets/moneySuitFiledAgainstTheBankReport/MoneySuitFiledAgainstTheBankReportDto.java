package com.csinfotechbd.legal.report.datasheets.moneySuitFiledAgainstTheBankReport;

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Date;

@Data
public class MoneySuitFiledAgainstTheBankReportDto {

    private String sl;
    private String branchName;
    private String branchCode;
    private String cif;
    private String accountNo;
    private String accountName;
    private String borrowerName;
    private String segment;
    private String ldNo;
    private String plaintiffName;
    private String defendantName;
    private String defendantMobile;
    private String suitNumber;
    private Date dateOfFiling;
    private double caseAmount;
    private String clStatus;
    private double totalRecovery;
    private double outstanding;
    private Date nextDate;
    private String courseOfAction;
    private String commentOnOutcomeAndImpactOnBank;
    private String lawyers;
    private String lawyersPhone;
    private String remarks;

    public MoneySuitFiledAgainstTheBankReportDto() {
    }

    public MoneySuitFiledAgainstTheBankReportDto(Tuple row) {
        this.branchName = row.get("branchName", String.class);
        this.branchCode = row.get("branchCode", String.class);
        this.cif = row.get("cif", String.class);
        this.accountNo = row.get("accountNo", String.class);
        this.accountName = row.get("accountName", String.class);
        this.borrowerName = row.get("borrowerName", String.class);
        this.segment = row.get("segment", String.class);
        this.ldNo = row.get("ldNo", Number.class).toString();
        this.plaintiffName = row.get("plaintiffName", String.class);
        this.defendantName = row.get("defendantName", String.class);
        this.defendantMobile = row.get("defendantMobile", String.class);
        this.suitNumber = row.get("suitNumber", String.class);
        this.dateOfFiling = row.get("dateOfFiling", Date.class);
        this.caseAmount = row.get("caseAmount", Number.class).doubleValue();
        this.clStatus = row.get("clStatus", String.class);
        this.totalRecovery = row.get("totalRecovery", Number.class).doubleValue();
        this.outstanding = row.get("outstanding", Number.class).doubleValue();
        this.nextDate = row.get("nextDate", Date.class);
        this.courseOfAction = row.get("courseOfAction", String.class);
        this.commentOnOutcomeAndImpactOnBank = row.get("commentOnOutcomeAndImpactOnBank", String.class);
        this.lawyers = row.get("lawyers", String.class);
        this.lawyers = row.get("lawyersPhone", String.class);
        this.remarks = row.get("remarks", String.class);
    }

}
