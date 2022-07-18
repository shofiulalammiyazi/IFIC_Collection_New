package com.csinfotechbd.legal.report.datasheets.niActCaseMonitoringReport;

import lombok.Data;

@Data
public class NIActCaseMonitoringDto {

    private Integer serial;
    private Object branchCode;
    private Object branchName;

    private Object ldNo;
    private Object division;

    private Object cifNumber;
    private Object accountNo;
    private Object accountName;

    private Object defendantName;
    private Object defendantMobile;
    private Object businessSegment;
    private Object outstanding;

    private Object clStatus;
    private Object niActCaseNumber;
    private Object filingDate;
    private Object caseAmount;

    private Object previousDate;
    private Object courseOfAction;
    private Object nextDate;
    private Object courtsName;

    private Object petitionersName;
    private Object petitionersCellNumber;
    private Object accusedName;
    private Object accusedCellNumber;

    private Object lawyersName;
    private Object lawyersCellNumber;

    private Object artharinExecutionCaseNumber;
    private Object writNumber;
    private Object criminalCase;

    private Object otherCase;
    private Object againstBank;

    private Object writtenOffStatus;
    private Object totalRecoveryAmount;

    private Object legalExpense;
    private Object collateralSecurity;

    private Object marketValue;
    private Object forcedCellValue;
    private Object assessedBy;
    private Object status;
    private Object remarks;

}
