package com.csinfotechbd.legal.report.datasheets.writFileMonitoringReport;

import lombok.Data;

@Data
public class WritFileMonitoringReportDto {

    private Integer serial;
    private Object branchCode;
    private Object branchName;

    private Object cif;
    private Object accountNo;
    private Object accountName;

    private Object defendantName;
    private Object defendantMobile;

    private Object segment;
    private Object petitionersName;
    private Object writNumber;

    private Object dateOfFiling;
    private Object natureOfWrit;
    private Object respondentsName;

    private Object amountInvolved;
    private Object subjectMatterOfWrit;
    private Object firstOrderDate;

    private Object byWhomFiled;
    private Object lawyersName;
    private Object lawyersCellNumber;

    private Object courtsName;
    private Object legalExpense;
    private Object artharinSuitNo;

    private Object niActCaseNo;
    private Object otherSuitCase;
    private Object againstBank;

    private Object arExSuitValue;
    private Object niActChequeValue;

    private Object artharinSuitStayed;
    private Object niActCaseStayed;
    private Object status;
    private Object remarks;
}
