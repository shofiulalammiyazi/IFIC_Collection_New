package com.csinfotechbd.legal.report.datasheets.legalBilLExpenseSummaryReport;


import lombok.Data;

@Data
public class LegalBillExpenseSummaryReportDto {

    private int serial;
    private Object branchCode;
    private Object branchName;
    private Object lawarId;
    private Object lawarName;
    private Object district;
    private Object courtName;
    private Object segment;
    private Object dateOfFiling;
    private Object totalWOffAmt;
    private Object totalWOffRecovery;
    private Object totalRecoveryBeoreFilling;
    private Object TotalRecoveryAfterFilling;
    private Object outstanding;
    private Object lawyerBillTypeCase;
    private Object previousMonthLawyersBillAmount;
    private Object currentMonthLawyersBillAmount;
    private Object totalLawyersBillAmount;
    private Object currentMonthOthersBillAmount;
    private Object totalOthersBillAmount;
    private Object cumulativeLegalBillAmount;
    private Object currentMonthLegalBillRecovery;
    private Object recoveryBeforeCaseFiling;
    private Object recoveryAfterCaseFiling;

}
