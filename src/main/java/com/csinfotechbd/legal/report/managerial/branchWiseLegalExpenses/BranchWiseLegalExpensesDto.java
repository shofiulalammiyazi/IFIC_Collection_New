package com.csinfotechbd.legal.report.managerial.branchWiseLegalExpenses;

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Objects;
import java.util.Optional;

@Data
public class BranchWiseLegalExpensesDto {

    private String caseType;
    private String accountNumber;
    private String caseNumber;
    private String dateOfFiling;
    private double suitValue;
    private double legalExpense;

    public BranchWiseLegalExpensesDto() {
    }

    public BranchWiseLegalExpensesDto(Tuple data) {
        caseType = Objects.toString(data.get("CASE_TYPE"), "-");
        accountNumber = Objects.toString(data.get("ACCOUNT_NUMBER"), "-");
        caseNumber = Objects.toString(data.get("CASE_NUMBER"), "-");
        dateOfFiling = Objects.toString(data.get("DATE_OF_FILING"), "-");
        suitValue = ((Number) Optional.ofNullable(data.get("SUIT_VALUE")).orElse(0)).doubleValue();
        legalExpense = ((Number) Optional.ofNullable(data.get("LEGAL_EXPENSE")).orElse(0)).doubleValue();
    }
}
