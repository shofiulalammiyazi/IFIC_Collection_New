package com.csinfotechbd.legal.report.managerial.statementOfMonthlyNewCases;

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Objects;

@Data
public class StatementOfMonthlyNewCasesDto {

    private String caseType;
    private String branchName;
    private String accountNumber;
    private String accountName;
    private String caseNumber;
    private String dateOfFiling;
    private double suitValue;
    private String nextDate;
    private String caseStatus;

    public StatementOfMonthlyNewCasesDto() {
    }

    public StatementOfMonthlyNewCasesDto(Tuple data) {
        caseType = Objects.toString(data.get("CASE_TYPE"), "-");
        branchName = Objects.toString(data.get("BRANCH_NAME"), "-");
        accountNumber = Objects.toString(data.get("ACCOUNT_NUMBER"), "-");
        accountName = Objects.toString(data.get("ACCOUNT_NAME"), "-");
        caseNumber = Objects.toString(data.get("CASE_NUMBER"), "-");
        dateOfFiling = Objects.toString(data.get("DATE_OF_FILING"), "-");
        suitValue = ((Number)data.get("SUIT_VALUE")).doubleValue();
        nextDate = Objects.toString(data.get("NEXT_DATE"), "-");
        caseStatus = Objects.toString(data.get("CASE_STATUS"), "-");
    }
}
