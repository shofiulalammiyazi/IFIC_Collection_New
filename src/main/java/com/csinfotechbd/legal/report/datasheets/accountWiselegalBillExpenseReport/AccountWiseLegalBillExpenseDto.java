package com.csinfotechbd.legal.report.datasheets.accountWiselegalBillExpenseReport;

import lombok.Data;

@Data
public class AccountWiseLegalBillExpenseDto {

    private Integer serial;
    private Object branchCode;
    private Object branchName;

    private Object cifId;
    private Object accountNo;
    private Object accountName;
    private Object segment;

    private Object outstanding;
    private Object thisMonthRecoveryBeforeFiling;
    private Object thisMonthRecoveryAfterFiling;
    private Object cumulativeRecovery;

    private Object district;
    private Object courtName;

    private Object lawyersId;
    private Object lawyersName;
    private Object lawyersMobileNo;

    private Object plaintiffId;
    private Object plaintiffName;
    private Object plaintiffMobileNo;

    private Object lawyersBillForTypeOfCase;
    private Object caseStatus;

    private Object previousMonthLawyersBillAmount;
    private Object currentMonthLawyersBillAmount;
    private Object totalLawyersBillAmount;

    private Object currentMonthOthersBillAmount;
    private Object totalOthersBillAmount;
    private Object cumulativeLegalBillAmount;

    private Object currentMonthLegalBillRecovery;
    private Object cumulativeLegalBillRecovery;
    private Object clStatus;

    private Object recoveryBeforeCaseFiling;
    private Object recoveryAfterCaseFiling;
}
