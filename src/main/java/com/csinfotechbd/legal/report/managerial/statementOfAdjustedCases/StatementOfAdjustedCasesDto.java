package com.csinfotechbd.legal.report.managerial.statementOfAdjustedCases;

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Objects;
import java.util.Optional;

@Data
public class StatementOfAdjustedCasesDto {

    private String caseType;
    private String branchName;
    private String accountNumber;
    private String caseNumber;
    private String dateOfFiling;
    private double suitValue;
    private String adjustmetDate;
    private double recoveredAmount;

    public StatementOfAdjustedCasesDto() {
    }

    public StatementOfAdjustedCasesDto(Tuple data) {
        caseType = Objects.toString(data.get("CASE_TYPE"), "-");
        branchName = Objects.toString(data.get("BRANCH_NAME"), "-");
        accountNumber = Objects.toString(data.get("ACCOUNT_NUMBER"), "-");
        caseNumber = Objects.toString(data.get("CASE_NUMBER"), "-");
        dateOfFiling = Objects.toString(data.get("DATE_OF_FILING"), "-");
        suitValue = ((Number) Optional.ofNullable(data.get("SUIT_VALUE")).orElse(0)).doubleValue();
        adjustmetDate = Objects.toString(data.get("ADJUSTMENT_DATE"), "-");
        recoveredAmount = ((Number) Optional.ofNullable(data.get("RECOVERED_AMOUNT")).orElse(0)).doubleValue();
    }
}
