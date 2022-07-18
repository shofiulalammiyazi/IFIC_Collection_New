package com.csinfotechbd.legal.report.managerial.statementOfCourtCases;

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Objects;
import java.util.Optional;

@Data
public class StatementOfCourtCasesDto {

    private String dateOfFiling;
    private String accountName;
    private String defendentName;
    private String ldNo;
    private String caseType;
    private String caseNumber;
    private double caseAmount;
    private String previousDate;
    private String nextDate;
    private String courseOfAction;

    public StatementOfCourtCasesDto() {
    }

    public StatementOfCourtCasesDto(Tuple data) {
        dateOfFiling = Objects.toString(data.get("DATE_OF_FILING"), "-");
        accountName = Objects.toString(data.get("ACCOUNT_NAME"), "-");
        defendentName = Objects.toString(data.get("DEFENDENT_NAME"), "-");
        ldNo = Objects.toString(data.get("LD_NO"), "-");
        caseType = Objects.toString(data.get("CASE_TYPE"), "-");
        caseNumber = Objects.toString(data.get("CASE_NUMBER"), "-");
        caseAmount = (Optional.ofNullable((Number) (data.get("CASE_AMOUNT"))).orElse(0)).doubleValue() / 100000; // Figure in lac
        previousDate = Objects.toString(data.get("PREVIOUS_DATE"), "-");
        nextDate = Objects.toString(data.get("NEXT_DATE"), "-");
        courseOfAction = Objects.toString(data.get("COURSE_OF_ACTION"), "-");
    }
}
