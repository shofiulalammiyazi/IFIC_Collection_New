package com.csinfotechbd.legal.report.datasheets.judgementAwardedAdjustedAccountReport;

import lombok.Data;

@Data
public class JudgementAwardedAdjustedAccountDto {
    private Integer serial;
    private Object branchCode;
    private Object branchName;

    private Object ldNumber;
    private Object cif;

    private Object accountNo;
    private Object accountName;

    private Object defendantName;
    private Object defendantMobile;

    private Object segment;
    private Object plaintiffName;
    private Object plaintiffMobile;

    private Object lawyerName;
    private Object lawyerMobileNo;
    private Object districtName;

    private Object courtName;
    private Object caseType;

    private Object suitNumber;
    private Object dateOfFiling;

    private Object caseAmount;
    private Object clStatus;

    private Object totalRecovery;
    private Object outstanding;
    private Object caseStatus;
    private Object adjustedDate;
    private Object remarks;
}
