package com.csinfotechbd.legal.report.managerial.caseAgainstBank;

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Objects;
import java.util.Optional;

@Data
public class CaseAgainstBankDto {
    private String caseType;
    private String branchName;
    private String accountNumber;
    private String caseNumber;
    private String dateOfFiling;
    private String courtName;
    private double suitValue;
    private String nextDate;
    private String courseOfAction;

    public CaseAgainstBankDto() {

    }

    public CaseAgainstBankDto(Tuple data) {
        caseType = Objects.toString(data.get("CASE_TYPE"), "-");
        branchName = Objects.toString(data.get("BRANCH_NAME"), "-");
        accountNumber = Objects.toString(data.get("ACCOUNT_NUMBER"), "-");
        caseNumber = Objects.toString(data.get("CASE_NUMBER"), "-");
        dateOfFiling = Objects.toString(data.get("DATE_OF_FILING"), "-");
        courtName = Objects.toString(data.get("COURT_NAME"), "-");
        suitValue = ((Number) Optional.ofNullable(data.get("SUIT_VALUE")).orElse(0)).doubleValue();
        nextDate = Objects.toString(data.get("NEXT_DATE"), "-");
        courseOfAction = Objects.toString(data.get("COURSE_OF_ACTION"), "-");
    }
}
