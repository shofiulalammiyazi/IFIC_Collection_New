package com.csinfotechbd.legal.report.managerial.statementOfClientWiseCaseDetails;

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Objects;
import java.util.Optional;

@Data
public class StatementOfClientWiseCaseDetailsDto {

    private String branchName;
    private String accountNumber;
    private String caseType;
    private String caseNumber;
    private String dateOfFiling;
    private double suitValue;
    private String nextDate;
    private String status;

    public StatementOfClientWiseCaseDetailsDto() {
    }

    public StatementOfClientWiseCaseDetailsDto(Tuple data) {
        branchName = Objects.toString(data.get("BRANCH_NAME"), "-");
        accountNumber = Objects.toString(data.get("ACCOUNT_NUMBER"), "-");
        caseType = Objects.toString(data.get("CASE_TYPE"), "-");
        caseNumber = Objects.toString(data.get("CASE_NUMBER"), "-");
        dateOfFiling = Objects.toString(data.get("DATE_OF_FILING"), "-");
        suitValue = ((Number) Optional.ofNullable(data.get("SUIT_VALUE")).orElse(0)).doubleValue();
        nextDate = Objects.toString(data.get("NEXT_DATE"), "-");
        status = Objects.toString(data.get("STATUS"), "-");
    }
}
